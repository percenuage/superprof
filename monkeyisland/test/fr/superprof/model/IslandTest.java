package fr.superprof.model;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IslandTest {

    private Island island;

    @Before
    public void setUp() throws Exception {
        island = Island.getInstance();
        //TODO : CLEAR
    }

    @Test
    public void getInstance() {
        Island islandTest = Island.getInstance();
        assertEquals(island, islandTest);
    }

    @Test
    public void getCell() {
        Integer row = 2;
        Integer col = 3;
        Cell cell = island.getCell(2, 3);
        assertEquals(island.getCells()[2][3], cell);
        assertEquals(row, cell.getRow());
        assertEquals(col, cell.getCol());
        assertNull(island.getCell(100, 0));
    }

    @Test
    public void getMonkeys() {
        List<Monkey> monkeys = new ArrayList<>();
        Cell cellCrazy = island.getCell(0,0);
        Cell cellHunter = island.getCell(0,1);
        monkeys.add(new CrazyMonkey(cellCrazy));
        monkeys.add(new HunterMonkey(cellHunter));
        assertEquals(monkeys, island.getMonkeys(null));
        monkeys.remove(0);
        assertEquals(monkeys, island.getMonkeys(HunterMonkey.class));
    }

    @Test
    public void getRhums() {
        List<Rhum> rhums = new ArrayList<>();
        Cell cellRhum = island.getCell(2,2);
        Rhum rhum = new Rhum(cellRhum);
        rhums.add(rhum);
        assertArrayEquals(rhums.toArray(), island.getRhums().toArray());
    }

    @Test
    public void getRandomVoidEarth() {
        // TODO: Tester 2x2 avec eau puis singe et item /!\ Initialiser avec une loop x3
        Integer ROWS = 2;
        Integer COLS = 2;
        Cell[][] cells = new Cell[ROWS][COLS];
        island.setCells(cells);

        Monkey monkey = new CrazyMonkey(island.getCell(0,1));
        Pirate pirate = new Pirate(island.getCell(1,1),1);
        Treasure item = new Treasure(island.getCell(1,2));

        for (int i = 0; i < ROWS ; i++) {
            for (int j = 0; j < COLS ; j++) {
                Cell cell = new Cell(i,j);
                if(cell.equals(monkey)|| cell.equals(pirate)||cell.equals(item)){
                    assertNull("Need Reroll", cell);
                }else {
                    assertNotNull("Roll ok", cell);
                }
            }
        }
    }

    @Test
    public void asciiTest() {
        System.out.println(island.getAscii());
    }

    @Test
    public void newGame() {
        assertFalse(true);
    }

    @Test
    public void initializeIsland() {
        assertFalse(true);
    }

    @Test
    public void initializeCells() {
        assertFalse(true);
    }

    @Test
    public void initializeTreasure() {
        assertFalse(true);
    }

    @Test
    public void initializeHunterMonkey() {
        assertFalse(true);
    }

    @Test
    public void initializeCrazyMonkey() {
        assertFalse(true);
    }

    @Test
    public void addPirate() {
        assertFalse(true);
    }

    @Test
    public void removePirate() {
        assertFalse(true);
    }

    @Test
    public void movePirate() {
        assertFalse(true);
    }

    @Test
    public void notifyClients() {
        assertFalse(true);
    }

    @Test
    public void toStringTest() {
        assertFalse(true);
    }
}