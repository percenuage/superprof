package fr.superprof.model;

import fr.superprof.MonkeyIsland;
import fr.superprof.command.Command;
import fr.superprof.network.Client;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Island extends Observable {

    public static final Integer ROWS = Integer.valueOf(MonkeyIsland.CONFIG.getString("ISLAND_ROWS"));
    public static final Integer COLS = Integer.valueOf(MonkeyIsland.CONFIG.getString("ISLAND_COLS"));

    private Cell[][] cells = new Cell[ROWS][COLS];
    private Map<Integer, Pirate> pirates;
    private Treasure treasure;

    private Island() {
        pirates = new HashMap<>();
    }

    private static class SingletonHolder {
        /** Instance unique non préinitialisée */
        private static final Island instance = new Island();
    }

    public static final Island getInstance() {
        return SingletonHolder.instance;
    }

    public Cell getCell(Integer row, Integer col) {
        try {
            return this.cells[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Cell getCell(Cell relativeCell, Integer row, Integer col) {
        try {
            return this.cells[relativeCell.getRow() + row][relativeCell.getCol() + col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return relativeCell;
        }
    }

    public List<Monkey> getMonkeys(Class<?> cls) {
        if (cls == null) {
            cls = Monkey.class;
        }
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Character character = cells[i][j].getCharacter();
                if (cls.isInstance(character)) {
                    monkeys.add((Monkey)character);
                }
            }
        }
        return monkeys;
    }

    public List<Rhum> getRhums() {
        List<Rhum> rhums = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Item item = cells[i][j].getItem();
                if (item instanceof Rhum) {
                    rhums.add((Rhum) item);
                }
            }
        }
        return rhums;
    }

    public void initializeIsland() {
        String enableMonkeys = MonkeyIsland.CONFIG.getString("ENABLE_MONKEYS");
        this.initializeCells();
        this.initializeElements("ISLAND_TREASURE", Treasure.class);
        this.initializeElements("ISLAND_RHUM", Rhum.class);
        if (Boolean.valueOf(enableMonkeys)) {
            this.initializeElements("ISLAND_CRAZY_MONKEY", CrazyMonkey.class);
            this.initializeElements("ISLAND_HUNTER_MONKEY", HunterMonkey.class);
        }
    }

    private void initializeCells() {
        String shape = MonkeyIsland.CONFIG.getString("ISLAND_SHAPE");
        int[] shapes = Arrays.stream(shape.split("-")).mapToInt(Integer::parseInt).toArray();
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                cells[i][j] = new Cell(i, j, this, shapes[i * COLS + j]);
            }
        }
    }

    private void initializeTreasure() {
        String position = MonkeyIsland.CONFIG.getString("ISLAND_TREASURE");
        int[] treasures = Arrays.stream(position.split("-")).mapToInt(Integer::parseInt).toArray();
        Cell cell = this.getCell(treasures[0], treasures[1]);
        this.treasure = new Treasure(cell);
    }

    private void initializeElements(String configKey, Class<?> cls) {
        String position = MonkeyIsland.CONFIG.getString(configKey);
        int[] elements = Arrays.stream(position.split("-")).mapToInt(Integer::parseInt).toArray();
        for(int i = 0; i < elements.length; i += 2) {
            Cell cell = this.getCell(elements[i + 1], elements[i]);
            try {
                cls.getDeclaredConstructor(Cell.class).newInstance(cell);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeRhum() {
        String position = MonkeyIsland.CONFIG.getString("ISLAND_RHUM");
        int[] rhums = Arrays.stream(position.split("-")).mapToInt(Integer::parseInt).toArray();
        for(int i = 0; i < rhums.length; i += 2) {
            Cell cell = this.getCell(rhums[i], rhums[i + 1]);
            new Rhum(cell);
        }
    }

    public void startMonkeys() {
        for (Monkey monkey : this.getMonkeys(null)) {
            Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate(monkey, 0, monkey.getMoveSpeed(), TimeUnit.SECONDS);
        }
    }

    public Cell getRandomVoidEarth() {
        Random rand = new Random();
        Cell cell = null;
        do {
            Integer row = rand.nextInt(ROWS);
            Integer col = rand.nextInt(COLS);
            cell = this.getCell(row, col);
        } while(!cell.canAccess() || cell.getItem() != null || cell.getCharacter() != null);
        return cell;
    }

    public Pirate addPirate(Pirate pirate) {
        if (this.pirates.containsKey(pirate.getId())) {
            throw new NullPointerException("Pirate already exists from map.");
        }
        pirate.setStatus(PirateStatusEnum.ADD);
        this.notify(pirate);
        this.addObserver(pirate);
        this.pirates.put(pirate.getId(), pirate);
        return pirate;
    }

    public Pirate removePirate(Integer id) {
        Pirate pirate = this.pirates.remove(id);
        if (pirate == null) {
            throw new NullPointerException("Pirate is not found from map.");
        }
        pirate.remove();
        pirate.setStatus(PirateStatusEnum.REMOVE);
        this.deleteObserver(pirate);
        this.notify(pirate);
        return pirate;
    }

    public Pirate movePirate(Pirate pirate, Integer moveX, Integer moveY) throws PirateException {
        Cell cell = pirate.getRelativeCell(moveY, moveX);
        if (!pirate.canMove(cell)) {
            throw new PirateException("Pirate can't move!", pirate.getId());
        }
        pirate.moveTo(cell);
        pirate.setStatus(PirateStatusEnum.MOVE);
        this.notify(pirate);
        return pirate;
    }

    public void newGame() {
        //TODO
    }

    public void notify(Object arg) {
        this.setChanged();
        this.notifyObservers(arg);
    }

    public String getAscii() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            sb.append("|");
            for (int j = 0; j < COLS; j++) {
                Cell cell = cells[i][j];
                sb.append("_");
                if (cell.getType().equals(Cell.WATER)) {
                    sb.append("X|");
                } else if (cell.getItem() instanceof Treasure) {
                    sb.append("T|");
                } else if (cell.getItem() instanceof Rhum) {
                    sb.append("R|");
                } else if (cell.getCharacter() instanceof Pirate) {
                    sb.append("P|");
                } else if (cell.getCharacter() instanceof CrazyMonkey) {
                    sb.append("C|");
                } else if (cell.getCharacter() instanceof HunterMonkey) {
                    sb.append("H|");
                } else {
                    sb.append(" |");
                }
            }
            sb.append("\n");

        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(COLS).append(" ").append(ROWS).append(" ");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                sb.append(this.getCell(i, j).getType()).append("-");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Map<Integer, Pirate> getPirates() {
        return pirates;
    }

    public void setPirates(Map<Integer, Pirate> pirates) {
        this.pirates = pirates;
    }

    public Treasure getTreasure() {return treasure;}

    public void setTreasure(Treasure treasure) { this.treasure = treasure; }
}
