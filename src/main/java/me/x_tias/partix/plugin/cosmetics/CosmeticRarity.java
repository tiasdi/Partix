package me.x_tias.partix.plugin.cosmetics;

public enum CosmeticRarity {

    COMMON("§7§lCOMMON",300),
    RARE("§f§lRARE",800),
    EPIC("§e§lEPIC",1900),
    LEGENDARY("§6§lLEGENDARY",2600);


    private CosmeticRarity(String title, int cost) {
        this.cost = cost;
        this.title = title;
    }

    private final int cost;
    private final String title;
    public int getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }
}
