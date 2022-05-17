package net.runelite.client.plugins.clanpileindicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("clanpileindicators")
public interface ClanPileConfig extends Config {
    @ConfigSection(name = "Pile highlight", description = "Highlight options for the pile", position = 40)
    public static final String pileSection = "pile";

    @ConfigSection(name = "Spear highlight", description = "Highlight options for the spear", position = 60)
    public static final String spearSection = "spear";

    @ConfigSection(name = "Mage highlight", description = "Highlight options for the mages", position = 80)
    public static final String mageSection = "mage";

    @ConfigItem(position = 1, keyName = "highlightCallersPile", name = "Highlight clan pile", description = "Configures whether or not callers targets characters should be highlighted", section = "pile")
    default boolean highlightCallersPile() {
        return true;
    }

    @ConfigItem(position = 2, keyName = "highlightSpear", name = "Highlight Spear", description = "Configures whether or not targets with spears equipped should be highlighted", section = "spear")
    default boolean highlightSpear() {
        return true;
    }

    @ConfigItem(position = 3, keyName = "colorPlayerMenu", name = "Colorize player menu", description = "Color right click menu for players")
    default boolean colorPlayerMenu() {
        return true;
    }

    @ConfigItem(position = 4, keyName = "pileNamePosition", name = "Name position", description = "Configures the position of drawn pile player names, or if they should be disabled")
    default PlayerNameLocation pileNamePosition() {
        return PlayerNameLocation.ABOVE_HEAD;
    }

    @ConfigItem(position = 5, keyName = "drawPilePlayerTiles", name = "Draw tiles under piles characters", description = "Configures whether or not tiles under piles characters should be drawn", section = "pile")
    default boolean drawPileTiles() {
        return true;
    }

    @ConfigItem(position = 6, keyName = "drawPileMinimapNames", name = "Draw piles names on minimap", description = "Configures whether or not minimap names for piles characters with rendered names should be drawn", section = "pile")
    default boolean drawPileMinimapNames() {
        return false;
    }

    @ConfigItem(position = 7, keyName = "callerPileColor", name = "Clan pile color", description = "Color of callers pile", section = "pile")
    default Color getCallerPileColor() {
        return new Color(244, 0, 0);
    }

    @ConfigItem(position = 8, keyName = "spearPileColor", name = "Spearer color", description = "Color of spear targets", section = "spear")
    default Color getSpearerColor() {
        return new Color(255, 255, 0);
    }

    @ConfigItem(position = 9, keyName = "drawSpearerHull", name = "Draw hull around spearers", description = "Configures whether or not hulls will be drawn around spearers", section = "spear")
    default boolean drawSpearerHull() {
        return false;
    }

    @ConfigItem(position = 10, keyName = "highlightMage", name = "Highlight Mage", description = "Configures whether or not F2P mages should be highlighted", section = "mage")
    default boolean highlightMage() {
        return true;
    }

    @ConfigItem(position = 11, keyName = "MageColor", name = "Mager color", description = "Color of F2P Mages", section = "mage")
    default Color getMageColor() {
        return new Color(255, 105, 180);
    }

    @ConfigItem(position = 12, keyName = "MageCombatMinimum", name = "Minimum Combat Level", description = "Sets the minimum combat level for the mage finder to highlight", section = "mage")
    default int minimumCombat() {
        return 100;
    }

    @ConfigItem(position = 13, keyName = "F2PEnable", name = "Enables F2P Mage Finder", description = "Turns on the mage finder for F2P", section = "mage")
    default boolean F2PEnable() {
        return false;
    }
}