package net.runelite.client.plugins.clanpileindicators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.Text;

@Singleton
public class ClanPileOverlay extends Overlay {
    private static final int ACTOR_OVERHEAD_TEXT_MARGIN = 40;

    private static final int ACTOR_HORIZONTAL_TEXT_MARGIN = 10;

    private final ClanPileConfig config;

    private final ClanPilePlugin plugin;

    @Inject
    private ClanPileOverlay(ClanPileConfig config, ClanPilePlugin plugin) {
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGHEST);
    }

    public Dimension render(Graphics2D graphics) {
        if (this.config.highlightMage() && this.config.pileNamePosition() != PlayerNameLocation.DISABLED)
            for (Player player : this.plugin.getMageList())
                renderPlayerOverlay(graphics, player, this.config.getMageColor(), this.config.pileNamePosition(), Boolean.valueOf(true), " (MAGE)");
        if (this.config.highlightSpear() && this.config.pileNamePosition() != PlayerNameLocation.DISABLED)
            for (Player player : this.plugin.getSpearList())
                renderPlayerOverlay(graphics, player, this.config.getSpearerColor(), this.config.pileNamePosition(), Boolean.valueOf(true), " (SPEAR)");
        if (this.config.highlightCallersPile() && this.config.pileNamePosition() != PlayerNameLocation.DISABLED)
            for (Player player : this.plugin.getPilesList())
                renderPlayerOverlay(graphics, player, this.config.getCallerPileColor(), this.config.pileNamePosition(), Boolean.valueOf(false), "");
        return null;
    }

    private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color, PlayerNameLocation drawPlayerNamesConfig, Boolean addText, String text) {
        int zOffset;
        String name;
        switch (drawPlayerNamesConfig) {
            case MODEL_CENTER:
            case MODEL_RIGHT:
                zOffset = actor.getLogicalHeight() / 2;
                break;
            default:
                zOffset = actor.getLogicalHeight() + 40;
                break;
        }
        String actorName = actor.getName();
        StringBuilder fullText = new StringBuilder();
        if (actorName == null)
            return;
        if (addText.booleanValue()) {
            fullText.append(Text.sanitize(actor.getName()));
            fullText.append(text);
            name = fullText.toString();
        } else {
            name = Text.sanitize(actor.getName());
        }
        Point textLocation = actor.getCanvasTextLocation(graphics, Text.sanitize(actor.getName()), zOffset);
        if (drawPlayerNamesConfig == PlayerNameLocation.MODEL_RIGHT) {
            textLocation = actor.getCanvasTextLocation(graphics, "", zOffset);
            if (textLocation == null)
                return;
            textLocation = new Point(textLocation.getX() + 10, textLocation.getY());
        }
        if (textLocation == null)
            return;
        OverlayUtil.renderTextLocation(graphics, textLocation, name, color);
    }
}
