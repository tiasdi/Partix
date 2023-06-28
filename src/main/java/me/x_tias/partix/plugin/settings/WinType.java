package me.x_tias.partix.plugin.settings;

public enum WinType {


    TIME_2(true,2),
    TIME_3(true,3),
    TIME_5(true,5),
    GOALS_1(false,1),
    GOALS_3(false,3),
    GOALS_5(false,5),
    GOALS_10(false,10),
    GOALS_15(false,15),
    GOALS_21(false,21);




    private WinType(boolean timed, int amount) {
       this.timed = timed;
       this.amount = amount;
    }

    public final boolean timed;
    public final int amount;

}
