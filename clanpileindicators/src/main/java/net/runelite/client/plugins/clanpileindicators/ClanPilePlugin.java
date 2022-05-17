package net.runelite.client.plugins.clanpileindicators;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Player;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(name = "Clan Pile Indicators", description = "Highlight players that your clans caller is hitting", tags = {"highlight", "minimap", "overlay", "players", "clan", "caller", "pile", "rsb", "rsc", "sanlite"}, enabledByDefault = false)
@Singleton
public class ClanPilePlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ClanPileConfig config;

    @Inject
    private ClanPileOverlay clanCallerOverlay;

    @Inject
    private ClanPileTileOverlay clanCallerTileOverlay;

    @Inject
    private ClanPileMinimapOverlay clanCallerMinimapOverlay;

    @Inject
    private Client client;

    private List<Player> callersList = new ArrayList<>();

    public List<Player> getCallersList() {
        return this.callersList;
    }

    private List<Player> pilesList = new ArrayList<>();

    public List<Player> getPilesList() {
        return this.pilesList;
    }

    private List<Player> spearList = new ArrayList<>();

    public List<Player> getSpearList() {
        return this.spearList;
    }

    private List<Player> mageList = new ArrayList<>();

    public List<Player> getMageList() {
        return this.mageList;
    }

    public List<ClanPile> clanList = new ArrayList<>();

    public List<ClanPile> getClanList() {
        return this.clanList;
    }

    public String leader = "";

    public int leaderNum = 0;

    private List<String> callersListString = new ArrayList<>();

    @Provides
    ClanPileConfig provideConfig(ConfigManager configManager) {
        return (ClanPileConfig)configManager.getConfig(ClanPileConfig.class);
    }

    protected void startUp() throws Exception {
        this.overlayManager.add(this.clanCallerOverlay);
        this.overlayManager.add(this.clanCallerTileOverlay);
        this.overlayManager.add(this.clanCallerMinimapOverlay);
    }

    protected void shutDown() throws Exception {
        this.overlayManager.remove(this.clanCallerOverlay);
        this.overlayManager.remove(this.clanCallerTileOverlay);
        this.overlayManager.remove(this.clanCallerMinimapOverlay);
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {
        this.clanList.clear();
        this.pilesList.clear();
        this.leader = "";
        this.leaderNum = 0;
        this.spearList.clear();
        this.mageList.clear();
        for (Player player : this.client.getPlayers()) {
            String name = player.getName();
            if (player.isFriendsChatMember() &&
                    player.getInteracting() != null) {
                String pileName = player.getInteracting().getName();
                Boolean isThere = Boolean.valueOf(false);
                for (ClanPile piled : this.clanList) {
                    if (piled.playerName.equalsIgnoreCase(pileName)) {
                        piled.Inc();
                        if (piled.count > this.leaderNum) {
                            this.leaderNum = piled.count;
                            this.leader = piled.playerName;
                        }
                        isThere = Boolean.valueOf(true);
                    }
                }
                if (!isThere.booleanValue()) {
                    ClanPile x = new ClanPile(pileName);
                    if (x.count > this.leaderNum) {
                        this.leaderNum = x.count;
                        this.leader = x.playerName;
                    }
                    this.clanList.add(x);
                }
            }
            if (this.config.highlightSpear()) {
                int itemID = 0;
                if (!player.isFriendsChatMember()) {
                    itemID = player.getPlayerComposition().getEquipmentId(KitType.WEAPON);
                    if (itemID == 1249 || itemID == 1263 || itemID == 5716 || itemID == 5730)
                        this.spearList.add(player);
                }
            }
            if (this.config.highlightMage()) {
                int itemID1 = 0;
                int itemID2 = 0;
                int itemID3 = 0;
                int itemID4 = 0;
                Boolean robeTop = Boolean.valueOf(false);
                Boolean robeBottom = Boolean.valueOf(false);
                Boolean hasStaff = Boolean.valueOf(false);
                if (!player.isFriendsChatMember() &&
                        player.getCombatLevel() > this.config.minimumCombat()) {
                    itemID1 = player.getPlayerComposition().getEquipmentId(KitType.HANDS);
                    itemID2 = player.getPlayerComposition().getEquipmentId(KitType.WEAPON);
                    itemID3 = player.getPlayerComposition().getEquipmentId(KitType.TORSO);
                    itemID4 = player.getPlayerComposition().getEquipmentId(KitType.LEGS);
                    if (itemID3 == 4091 || itemID3 == 4101 || itemID3 == 4111 || itemID3 == 23050 || itemID3 == 13387 || itemID3 == 9070)
                        robeTop = Boolean.valueOf(true);
                    if (itemID4 == 4093 || itemID4 == 4103 || itemID4 == 4113 || itemID4 == 23053 || itemID4 == 13389 || itemID4 == 9071)
                        robeBottom = Boolean.valueOf(true);
                    if (itemID2 == 12904 || itemID2 == 12902)
                        hasStaff = Boolean.valueOf(true);
                    if ((itemID1 != 1065 && this.config.F2PEnable()) || robeTop.booleanValue() == true || robeBottom.booleanValue() == true || hasStaff.booleanValue() == true)
                        this.mageList.add(player);
                }
            }
        }
        for (Player player : this.client.getPlayers()) {
            if (player.getName().equalsIgnoreCase(this.leader)) {
                this.pilesList.add(player);
                break;
            }
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        if (configChanged.getGroup().equals("clanpileindicators") && configChanged.getKey().equals("callersRsns"))
            this.callersList.clear();
    }

    @Subscribe
    public void onPlayerDespawned(PlayerDespawned playerDespawned) {
        if (this.callersList == null || this.callersList.isEmpty())
            return;
        this.callersList.removeIf(x -> x.equals(playerDespawned.getPlayer()));
    }

    @Subscribe(priority = -1)
    public void onMenuEntryAdded(MenuEntryAdded menuEntryAdded) {
        if (this.config.colorPlayerMenu()) {
            int type = menuEntryAdded.getType();
            if (type >= 2000)
                type -= 2000;
            int identifier = menuEntryAdded.getIdentifier();
            if (type == MenuAction.FOLLOW.getId() || type == MenuAction.TRADE.getId() || type == MenuAction.ITEM_USE_ON_PLAYER.getId() || type == MenuAction.PLAYER_FIRST_OPTION
                    .getId() || type == MenuAction.PLAYER_SECOND_OPTION
                    .getId() || type == MenuAction.PLAYER_THIRD_OPTION
                    .getId() || type == MenuAction.PLAYER_FOURTH_OPTION
                    .getId() || type == MenuAction.PLAYER_FIFTH_OPTION
                    .getId() || type == MenuAction.PLAYER_SIXTH_OPTION
                    .getId() || type == MenuAction.PLAYER_SEVENTH_OPTION
                    .getId() || type == MenuAction.PLAYER_EIGTH_OPTION
                    .getId() || type == MenuAction.RUNELITE
                    .getId()) {
                Player[] players = this.client.getCachedPlayers();
                Player player = null;
                if (identifier >= 0 && identifier < players.length)
                    player = players[identifier];
                if (player == null)
                    return;
                Color color = null;
                if (this.mageList.contains(player) && this.config.highlightMage())
                    color = this.config.getMageColor();
                if (this.spearList.contains(player) && this.config.highlightSpear())
                    color = this.config.getSpearerColor();
                if (this.pilesList.contains(player) && this.config.highlightCallersPile())
                    color = this.config.getCallerPileColor();
                if (color != null) {
                    MenuEntry[] menuEntries = this.client.getMenuEntries();
                    MenuEntry lastEntry = menuEntries[menuEntries.length - 1];
                    String target = lastEntry.getTarget();
                    int idx = target.indexOf('>');
                    if (idx != -1)
                        target = target.substring(idx + 1);
                    lastEntry.setTarget(ColorUtil.prependColorTag(target, color));
                    this.client.setMenuEntries(menuEntries);
                }
            }
        }
    }
}