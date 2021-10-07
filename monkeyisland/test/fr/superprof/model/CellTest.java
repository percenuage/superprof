package fr.superprof.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    private static Cell cell;

    @Before
    public void setUp() throws Exception {
        cell = new Cell(1,1,null);

    }

    @Test
    public void canAccess() {
        assertTrue("Earth cell", cell.canAccess());
        cell.setType(Cell.WATER);
        assertFalse("Water Cell", cell.canAccess());
    }

    @Test
    public void isAdjacent() {
        Cell north = new Cell(cell.getRow() - 1, cell.getCol(), null);
        Cell south = new Cell(cell.getRow() + 1, cell.getCol(), null);
        Cell east = new Cell(cell.getRow(), cell.getCol() - 1, null);
        Cell west = new Cell(cell.getRow(), cell.getCol() + 1, null);
        assertTrue("Is adjacent to the north cell", cell.isAdjacent(north));
        assertTrue("Is adjacent to the south cell", cell.isAdjacent(south));
        assertTrue("Is adjacent to the east cell", cell.isAdjacent(east));
        assertTrue("Is adjacent to the west cell", cell.isAdjacent(west));
        Cell destination = new Cell(cell.getRow() + 5, cell.getCol() + 2, null);
        assertFalse("Access to a far cell", cell.isAdjacent(destination));
    }

    @Test
    @Ignore
    public void toStringTest() {
        assertFalse(true);
    }
}