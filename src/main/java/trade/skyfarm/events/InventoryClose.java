package trade.skyfarm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.data.readytype;
import trade.skyfarm.gui.TargetChest;

public class InventoryClose implements Listener {
    @EventHandler
    public void close(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();


        if (cmds.receive.containsKey(player.getUniqueId())  ) {
            PlayerData request = TargetChest.getreceive(player);
            if (!request.getReady().equals(readytype.Accept) || !cmds.receive.get(player.getUniqueId()).getReady().equals(readytype.Accept)) {
                player.sendMessage(SkyFarm.prefix + "§c거래를 취소 하였습니다.");
                request.getPlayer().sendMessage(SkyFarm.prefix + player.getDisplayName() + " §c님이 거래를 취소하였습니다.");
                request.getPlayer().closeInventory();
            }
        }

    }
}
