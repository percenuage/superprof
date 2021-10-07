package fr.superprof.model;

public abstract class Character {

    private Cell cell;

    public Character(Cell cell) {
        this.cell = cell;
        cell.setCharacter(this);
    }

    abstract public void meetCharacter(Character character);

    abstract public void foundItem(Item item);

    public void moveTo(Cell cell) {
        this.cell.setCharacter(null);
        this.meetCharacter(cell.getCharacter());
        this.foundItem(cell.getItem());
        this.cell = cell;
        this.cell.setCharacter(this);
    }

    public Boolean canMove(Cell cell) {
        return cell.canAccess() && this.cell.isAdjacent(cell);
    }

    public Cell getRelativeCell(Integer row, Integer col) {
        return this.cell.getRelativeCell(row, col);
    }

    public void remove() {
        this.cell.setCharacter(null);
        this.cell = null;
    }

    @Override
    public String toString() {
        return this.cell.toString();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}

