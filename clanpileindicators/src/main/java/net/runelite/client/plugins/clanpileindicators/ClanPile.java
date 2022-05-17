package net.runelite.client.plugins.clanpileindicators;
//
public class ClanPile {
    public String playerName;
    public int count;

    ClanPile(String x) {
        this.playerName = x;
        this.count = 1;
    }

    public void Inc(){
        this.count = count + 1;
    }
}
