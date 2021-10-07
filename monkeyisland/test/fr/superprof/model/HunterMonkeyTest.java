package fr.superprof.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class HunterMonkeyTest {

    private Island island;
    private HunterMonkey hunterMonkey;
    private Pirate pirateAxel;
    private Pirate pirateJerome;
    private Cell hunterCell;
    private Cell pirateAxelCell;
    private Cell pirateJeromeCell;
    private Cell north, south, east, west;

    @Before
    public void setUp() throws Exception {
        island = Island.getInstance();
        north = island.getCell(0, 1);
        south = island.getCell(2, 1);
        east = island.getCell(1, 2);
        west = island.getCell(1, 0);
        hunterCell = island.getCell(1, 1);
        hunterMonkey = new HunterMonkey(hunterCell);
        pirateAxelCell = island.getCell(5, 2);
        pirateAxel = new Pirate(pirateAxelCell, 0);
        pirateJeromeCell = island.getCell(1, 3);
        pirateJerome = new Pirate(pirateJeromeCell, 1);
        island.getPirates().put(pirateAxel.getId(), pirateAxel);
        island.getPirates().put(pirateJerome.getId(), pirateJerome);
    }

    @Test
    @Ignore
    public void run() {


    }

    @Test
    @Ignore
    public void behavior() {
        assertTrue(true);
    }

    @Test
    public void moveTo() {
        assertEquals(hunterCell.getCharacter(), hunterMonkey);
        assertEquals(hunterMonkey.getCell(), hunterCell);
        assertNotEquals(hunterMonkey.getCell(), north);
        assertNotEquals(north.getCharacter(), hunterMonkey);
        hunterMonkey.moveTo(north);
        assertNull(hunterCell.getCharacter());
        assertEquals(hunterMonkey.getCell(), north);
        assertEquals(north.getCharacter(), hunterMonkey);
    }

    @Test
    public void canMove() {
        Cell destination = new Cell(5, 8, null);
        assertTrue("Can access to the north", hunterMonkey.canMove(north));
        assertTrue("Can access to the south", hunterMonkey.canMove(south));
        assertTrue("Can access to the east", hunterMonkey.canMove(east));
        assertTrue("Can access to the west", hunterMonkey.canMove(west));
        assertFalse("Access to a far cell", hunterMonkey.canMove(destination));
        assertTrue("Access to earth cell", hunterMonkey.canMove(north));
        north.setType(Cell.WATER);
        assertFalse("Access to Water cell", hunterMonkey.canMove(north));

        destination = island.getCell(1, 2);
        pirateJerome.moveTo(destination);
        assertTrue(hunterMonkey.canMove(destination));
        new HunterMonkey(destination);
        assertFalse(hunterMonkey.canMove(destination));
    }

    @Test
    public void meetCharacter() {
        assertTrue(true);
    }

    @Test
    public void getDistance() {
        assertEquals("Distance is not equal", 5, hunterMonkey.getDistance(pirateAxel).longValue());
    }

    @Test
    public void getCloserTarget() {
        assertEquals("PirateJerome must be the target", pirateJerome, hunterMonkey.getCloserTarget());
    }

    @Test
    public void getCloserPathToTarget() {
        Cell expected = hunterMonkey.getRelativeCell(0,1);
        hunterMonkey.setTarget(pirateJerome);
        assertEquals("Not the correct path to pirate", expected, hunterMonkey.getCloserPathToTarget());
    }

}