package fr.superprof.model;

public class Cell {

    public static final Integer WATER = 0;
    public static final Integer EARTH = 1;

    private Integer row;
    private Integer col;
    private Integer type;
    private Item item;
    private Character character;
    private Island island;

    public Cell(Integer row, Integer col, Island island, Integer type) {
        this.row = row;
        this.col = col;
        this.island = island;
        this.setType(type);
    }

    public Cell(Integer row, Integer col, Island island) {
        this(row, col, island, EARTH);
    }

    public Cell(Integer row, Integer col) {
        this(row, col, null);
    }

    public Boolean canAccess() {
        return this.type.equals(EARTH);
    }

    public Boolean isAdjacent(Cell cell) {
        return (cell.getRow().equals(this.row - 1) && cell.getCol().equals(this.col))
                || (cell.getRow().equals(this.row + 1) && cell.getCol().equals(this.col))
                || (cell.getRow().equals(this.row) && cell.getCol().equals(this.col - 1))
                || (cell.getRow().equals(this.row) && cell.getCol().equals(this.col + 1));
    }

    public Cell getRelativeCell(Integer row, Integer col) {
        return this.island.getCell(this, row, col);
    }

    @Override
    public String toString() {
        return this.col + "-" + this.row;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type.equals(WATER) ? WATER : EARTH;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }
}
