package trade.skyfarm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.data.readytype;
import trade.skyfarm.gui.TargetChest;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        if(title.equalsIgnoreCase("")){
            return;
        } else{
             if (title.equalsIgnoreCase(TargetChest.title)) {
                 if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                         || !event.getCurrentItem().hasItemMeta()) {
                     return;
                 } else{
                     if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§l거래금")){
                         player.sendMessage(SkyFarm.prefix + "§a상대방에게 줄 금액을 입력하세요!");
                         TargetChest.getreceive(player).setisSetCoin(true);
                         player.closeInventory();
                     } else{
                         Player Target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                         if(!event.isShiftClick()){
                             if(!player.equals(Target)){
                                player.openInventory(TargetChest.getreceive(Target).getInv());
                                TargetChest.getreceive(Target).setOpen(true);
                                TargetChest.getreceive(player).setOpen(true);

                             } else{
                                 player.openInventory(TargetChest.getreceive(player).getInv());
                                 TargetChest.getreceive(player).setOpen(true);
                                 TargetChest.getreceive(Target).setOpen(true);
                             }
                         } else{
                             if(player.equals(Target)){

                                 switch (TargetChest.getreceive(player).getReady()){
                                     case NotReady:
                                         TargetChest.getreceive(player).setReady(readytype.Ready);
                                         break;
                                     case Ready:
                                         TargetChest.getreceive(player).setReady(readytype.NotReady);
                                         break;
                                 }
                                 if(TargetChest.getreceive(TargetChest.GetReqeust(player)).getReady().equals(readytype.Ready) && TargetChest.getreceive(player).getReady().equals(readytype.Ready)){
                                     TargetChest.getreceive(player).setReady(readytype.Accept);
                                     cmds.receive.get(TargetChest.getreceive(player).getPlayer().getUniqueId()).setReady(readytype.Accept);
                                     TargetChest.getreceive(player).getPlayer().closeInventory();
                                     player.closeInventory();
                                 }
                             }
                         }
                     }
                     event.setCancelled(true);
                 }
             } else if(title.equalsIgnoreCase(player.getName())){
                 event.setCancelled(false);
             } else if(title.equalsIgnoreCase(TargetChest.getreceive(player).getRequest().getDisplayName())){
                 event.setCancelled(true);
             }
        }
    }
    public PlayerData getTargetData(Player player){
        return cmds.receive.get(TargetChest.getreceive(player).getPlayer());
    }
}
