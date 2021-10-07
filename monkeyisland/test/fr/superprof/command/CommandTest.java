package fr.superprof.command;

import fr.superprof.model.*;
import org.junit.Before;
import org.junit.Test;

public class CommandTest {

    private Island island;
    private Pirate pirate;
    private Monkey monkey;
    private Cell cell;

    @Before
    public void setUp() throws Exception {
        island = Island.getInstance();
        Cell cellpirate = new Cell(2,2);
        pirate = new Pirate(cellpirate,1);

    }

    @Test
    public void identifyPirate(){

    }

    @Test
    public void denyMovePirate() {
    }

    @Test
    public void allowMovePirate() {
    }

    @Test
    public void islandPirates() {
    }

    @Test
    public void newPirate() {
    }

    @Test
    public void deletePirate() {
    }

    @Test
    public void movePirate() {
    }

    @Test
    public void islandShape() {
    }

    @Test
    public void identifyCrazyMonkeys() {
    }

    @Test
    public void identifyHunterMonkeys() {
    }

    @Test
    public void identifyRhums() {
    }

    @Test
    public void visibiltyRhum() {
    }

    @Test
    public void foundTreasure() {
    }

    @Test
    public void startNewGame() {
    }
}