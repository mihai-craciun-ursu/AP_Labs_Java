package knapsack;

public enum WeaponType {
    SWORD("Sword"),
    AXE("Axe"),
    BOW("Bow"),
    STAFF("Magic Staff"),
    DAGGER("Dagger")
    ;

    private final String type;

    WeaponType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
