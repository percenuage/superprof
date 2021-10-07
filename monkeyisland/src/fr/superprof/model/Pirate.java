package fr.superprof.model;

import fr.superprof.MonkeyIsland;
import fr.superprof.command.Command;
import fr.superprof.network.Client;
import fr.superprof.network.Server;

import java.security.PublicKey;
import java.util.Observable;
import java.util.Observer;

public class Pirate extends Character implements Observer {

    public static final Integer MAX_ENERGY = Integer.valueOf(MonkeyIsland.CONFIG.getString("PIRATE_MAX_ENERGY"));

    private Integer id;
    private Integer energy;
    private PirateStatusEnum status;
    private Client client;

    public Pirate(Cell cell, Client client) {
        this(cell, client.getSocket().getPort());
        this.client = client;
        client.setPirate(this);
    }

    public Pirate(Cell cell, Integer id) {
        super(cell);
        this.id = id;
        this.energy = MAX_ENERGY;
        this.status = PirateStatusEnum.IDLE;
    }

    public Boolean isDead() {
        return this.energy <= 0;
    }

    public String toStringWithEnergy() {
        return super.toString() + "-" + this.energy;
    }

    public String toStringWithEnergyAndId() {
        return this.toString() + "-" + this.energy;
    }

    @Override
    public String toString() {
        return this.id + "-" + super.toString();
    }

    @Override
    public Boolean canMove(Cell cell) {
        return super.canMove(cell) && cell.getCharacter() == null;
    }

    @Override
    public void moveTo(Cell cell) {
        super.moveTo(cell);
        this.energy--;
        if (this.isDead()) {
            this.getCell().getIsland().removePirate(this.id);
        }
    }

    @Override
    public void foundItem(Item item) {
        if (item instanceof Rhum) {
            this.energy += Rhum.ENERGY_RECOVER;
            if (this.energy > MAX_ENERGY) {
                this.energy = MAX_ENERGY;
            }
            item.setVisibility(false);
        } else if (item instanceof Treasure) {
            item.setVisibility(true);
            item.setFound(true);
        }
        Island.getInstance().notify(item);
    }

    @Override
    public void meetCharacter(Character character) {
        /* DO NOTHING */
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Pirate) {
            Pirate pirate = (Pirate) arg;
            if (pirate != this) {
                switch (pirate.getStatus()) {
                    case ADD:
                        this.client.emit(Command.newPirate(pirate));
                        break;
                    case MOVE:
                        this.client.emit(Command.movePirate(pirate));
                        break;
                    case REMOVE:
                        this.client.emit(Command.deletePirate(pirate));
                        break;
                    default:
                        break;
                }
            }
            pirate.setStatus(PirateStatusEnum.IDLE);
        } else  if (arg instanceof CrazyMonkey) {
            this.client.emit(Command.identifyCrazyMonkeys());
        } else if (arg instanceof HunterMonkey) {
            this.client.emit(Command.identifyHunterMonkeys());
        } else if (arg instanceof Rhum) {
            this.client.emit(Command.visibiltyRhum((Rhum) arg));
        } else if (arg instanceof Treasure) {
            this.client.emit(Command.foundTreasure((Treasure) arg));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public PirateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PirateStatusEnum status) {
        this.status = status;
    }
}
