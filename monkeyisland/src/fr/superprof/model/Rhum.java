package fr.superprof.model;

import fr.superprof.MonkeyIsland;

public class Rhum extends Item {

    public static final Integer ENERGY_RECOVER = Integer.valueOf(MonkeyIsland.CONFIG.getString("RHUM_ENERGY_RECOVER"));
    public static final Integer RESPAWN_TIMER = Integer.valueOf(MonkeyIsland.CONFIG.getString("RHUM_RESPAWN_TIMER"));

    private static Integer count = 0;
    private Integer id;

    public Rhum(Cell cell) {
        super(cell);
        this.setId();
    }

    public String toStringWithId() {
        return this.id + "-" + this.getVisibility();
    }

    private void setId() {
        this.id = Rhum.count;
        Rhum.count++;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
