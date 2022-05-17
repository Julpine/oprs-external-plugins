package net.runelite.client.plugins.clanpileindicators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class ClanPileMinimapOverlay extends Overlay {
    private final ClanPileConfig config;

    private final ClanPilePlugin plugin;

    @Inject
    private ClanPileMinimapOverlay(ClanPileConfig config, ClanPilePlugin plugin) {
        this.config = config;
        this.plugin = plugin;
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGHEST);
    }

    public Dimension render(Graphics2D graphics) {
        if (this.config.highlightMage() && this.config.drawPileMinimapNames())
            for (Player player : this.plugin.getMageList())
                renderPlayerOverlay(graphics, player, this.config.getMageColor(), Boolean.valueOf(false));
        if (this.config.highlightSpear() && this.config.drawPileMinimapNames())
            for (Player player : this.plugin.getSpearList())
                renderPlayerOverlay(graphics, player, this.config.getSpearerColor(), Boolean.valueOf(false));
        if (this.config.highlightCallersPile() && this.config.drawPileMinimapNames())
            for (Player player : this.plugin.getPilesList())
                renderPlayerOverlay(graphics, player, this.config.getCallerPileColor(), Boolean.valueOf(false));
        return null;
    }

    private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color, Boolean isCaller) {
        String name;
        if (isCaller.booleanValue()) {
            name = "Caller";
        } else {
            String actorName = actor.getName();
            if (actorName == null)
                return;
            name = actorName.replace(' ', ' ');
        }
        Point minimapLocation = actor.getMinimapLocation();
        if (minimapLocation != null)
            OverlayUtil.renderTextLocation(graphics, minimapLocation, name, color);
    }
}
