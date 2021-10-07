package fr.superprof.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CrazyMonkeyTest {

    //TODO: TEST CRAZY LIKE HUNTER

    private Island island;
    private Cell monkeyCell;
    private CrazyMonkey monkey;
    private Cell adjacentCell;

    @Before
    public void setUp() throws Exception {
        island = Island.getInstance();
        monkeyCell = new Cell(2,2);
        monkey = new CrazyMonkey(monkeyCell);
    }

    @Test
    public void getRandomAdjacentCell() {
        //TODO: Refaire (random 1000 fois + 3 singes)

        Random rand = new Random();
        rand.nextInt(4);
        adjacentCell = null;

        if (rand.nextInt() == 0) {
            adjacentCell = new Cell(1, 2);
          //assertEquals("Case 0", monkeyCell.getRelativeCell(-1,0), adjacentCell);
            assertEquals("Case 0", adjacentCell, monkeyCell.getRelativeCell(-1,0));
        }
        if (rand.nextInt() == 1) {
             adjacentCell = new Cell(2, 3);
         // assertEquals("Case 0", monkeyCell.getRelativeCell(0,+1), adjacentCell);
            assertEquals("Case 0", adjacentCell, monkeyCell.getRelativeCell(0,+1));
        }
        if (rand.nextInt() == 2) {
            adjacentCell = new Cell(3, 2);
          //assertEquals("Case 0", monkeyCell.getRelativeCell(+1,0), adjacentCell );
            assertEquals("Case 0", adjacentCell, monkeyCell.getRelativeCell(+1,0));
        }
        if (rand.nextInt() == 3) {
           adjacentCell = new Cell(2, 1);
           //assertEquals("Case 0", monkeyCell.getRelativeCell(0,-1), adjacentCell );
            assertEquals("Case 0", adjacentCell, monkeyCell.getRelativeCell(0,-1));
        }

    }
}