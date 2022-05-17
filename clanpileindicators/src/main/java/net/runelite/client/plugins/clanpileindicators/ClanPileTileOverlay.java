package net.runelite.client.plugins.clanpileindicators;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

public class ClanPileTileOverlay extends Overlay {
    private final ClanPileConfig config;

    private final ClanPilePlugin plugin;

    @Inject
    private ClanPileTileOverlay(ClanPileConfig config, ClanPilePlugin plugin) {
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGHEST);
    }

    public Dimension render(Graphics2D graphics) {
        if (this.config.highlightMage() && this.config.drawPileTiles())
            for (Player player : this.plugin.getMageList()) {
                Polygon poly = player.getCanvasTilePoly();
                if (poly != null)
                    OverlayUtil.renderPolygon(graphics, poly, this.config.getMageColor());
            }
        if (this.config.highlightSpear() && this.config.drawPileTiles())
            for (Player player : this.plugin.getSpearList()) {
                if (this.config.drawSpearerHull() && player.getConvexHull() != null) {
                    OverlayUtil.renderPolygon(graphics, player.getConvexHull(), this.config.getSpearerColor());
                    continue;
                }
                if (player.getCanvasTilePoly() != null)
                    OverlayUtil.renderPolygon(graphics, player.getCanvasTilePoly(), this.config.getSpearerColor());
            }
        if (this.config.highlightCallersPile() && this.config.drawPileTiles())
            for (Player player : this.plugin.getPilesList()) {
                Polygon poly = player.getCanvasTilePoly();
                if (poly != null)
                    OverlayUtil.renderPolygon(graphics, poly, this.config.getCallerPileColor());
            }
        return null;
    }
}