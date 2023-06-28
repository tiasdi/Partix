package me.x_tias.partix.plugin.athlete;

public enum RenderType {
    PARTICLE(0),
    SLIME(1),
    REMOVE_SLIME(1);

    public final int db;


    private RenderType(int a) {
        this.db = a;
    }

}
