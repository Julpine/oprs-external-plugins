package net.runelite.client.plugins.clanpileindicators;

public enum PlayerNameLocation {
    DISABLED("Disabled"),
    ABOVE_HEAD("Above head"),
    MODEL_CENTER("Center of model"),
    MODEL_RIGHT("Right of model");

    PlayerNameLocation(String name) {
        this.name = name;
    }

    private final String name;

    public String toString() {
        return this.name;
    }
    //test
}
