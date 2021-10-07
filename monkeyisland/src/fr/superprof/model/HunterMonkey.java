package fr.superprof.model;

import fr.superprof.MonkeyIsland;

import java.util.Random;

public class HunterMonkey extends Monkey {

    public static final Integer SPEED = Integer.valueOf(MonkeyIsland.CONFIG.getString("HUNTER_MONKEY_SPEED"));

    private Pirate target;

    public HunterMonkey(Cell cell) {
        super(cell);
    }

    public Integer getDistance(Pirate pirate) {
        return Math.abs(this.getCell().getRow() - pirate.getCell().getRow())
                + Math.abs(this.getCell().getCol() - pirate.getCell().getCol());
    }

    public Pirate getCloserTarget() {
        Pirate target = null;
        Integer closerDistance = -1;
        for (Pirate pirate : this.getCell().getIsland().getPirates().values()) {
            Integer distance = this.getDistance(pirate);
            if (distance < closerDistance || closerDistance < 0) {
                closerDistance = distance;
                target = pirate;
            }
        }
        return target;
    }

    public Cell getCloserPathToTarget() {
        Random rand = new Random();

        Integer directionOnRow = Integer.signum(this.target.getCell().getRow() - this.getCell().getRow());
        Integer directionOnCol = Integer.signum(this.target.getCell().getCol() - this.getCell().getCol());

        Cell nextCellOnRow = this.getRelativeCell(directionOnRow, 0);
        Cell nextCellOnCol = this.getRelativeCell(0, directionOnCol);

        Boolean canMoveOnRow = directionOnRow != 0 && canMove(nextCellOnRow);
        Boolean canMoveOnCol = directionOnCol != 0 && canMove(nextCellOnCol);

        if (canMoveOnRow && canMoveOnCol) {
            return rand.nextBoolean() ? nextCellOnRow : nextCellOnCol;
        } else if (canMoveOnRow) {
            return nextCellOnRow;
        } else if (canMoveOnCol) {
            return nextCellOnCol;
        } else {
            return this.getCell();
        }
    }

    public Pirate getTarget() {
        return target;
    }

    public void setTarget(Pirate target) {
        this.target = target;
    }

    @Override
    public void run() {
        this.target = getCloserTarget();
        if (this.target != null) {
            this.moveTo(getCloserPathToTarget());
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
