package trade.skyfarm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import trade.skyfarm.gui.TargetChest;

public class InventoryClose implements Listener {
    @EventHandler
    public void close(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();
        if(title.equalsIgnoreCase(player.getName())){

        }
    }


}
