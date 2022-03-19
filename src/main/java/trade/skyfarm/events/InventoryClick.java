package trade.skyfarm.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
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
                         player.sendMessage(SkyFarm.prefix + "§e상대방에게 줄 금액을 입력하세요!");
                         TargetChest.getreceive(player).setisSetCoin(true);
                         player.closeInventory();
                     } else{
                         Player CurrentPlayer = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                         if(!event.isShiftClick()){ /** 플레이어가 자신의 상자를 열 경우*/
                             if(!player.equals(CurrentPlayer)){
                                player.openInventory(TargetChest.getreceive(CurrentPlayer).getInv());
                                TargetChest.getreceive(CurrentPlayer).setOpen(true);
                                TargetChest.getreceive(player).setOpen(true);

                             } else{  /** 플레이어가 상대방의 상자를 열 경우*/
                                 player.openInventory(TargetChest.getreceive(player).getInv());
                                 TargetChest.getreceive(player).setOpen(true);
                                 TargetChest.getreceive(CurrentPlayer).setOpen(true);

                             }
                         } else{ /** 플레이어가 수락을 누를 경우 */
                             if(player.equals(CurrentPlayer)) {
                                 if(!TargetChest.getreceive(CurrentPlayer).getReady().equals(readytype.Ready) && !TargetChest.getreceive(CurrentPlayer).getReady().equals(readytype.Ready)){
                                     switch (TargetChest.getreceive(CurrentPlayer).getReady()){
                                         case NotReady:
                                             TargetChest.getreceive(CurrentPlayer).setReady(readytype.Ready);
                                             break;
                                         case Ready:
                                             TargetChest.getreceive(CurrentPlayer).setReady(readytype.NotReady);
                                             break;
                                     }
                                 } else{
                                     if(TargetChest.getreceive(CurrentPlayer).getReady().equals(readytype.Ready) && TargetChest.getreceive(TargetChest.GetReqeust(player)).getReady().equals(readytype.Ready)
                                             || TargetChest.getreceive(CurrentPlayer).getReady().equals(readytype.Accept) || TargetChest.getreceive(TargetChest.GetReqeust(player)).getReady().equals(readytype.Accept)){
                                         TargetChest.getreceive(CurrentPlayer).setReady(readytype.Accept);
                                     }
                                 }
                             }
                         }
                     }
                     event.setCancelled(true);
                 }
             } else{ /**상대방 인벤토리를 눌렀을때*/
                 Player CurrentPlayer = Bukkit.getPlayer(title);
                 if(!player.equals(CurrentPlayer)){
                     if(cmds.receive.containsKey(player.getUniqueId())){
                         event.setCancelled(true);
                     }
                 }
             }
        }
    }
}
