package trade.skyfarm.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.gui.TargetChest;
import trade.skyfarm.gui.Type;

public class InventoryClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();

         if(title.equalsIgnoreCase("")){
            return;
        } else{
             if(title.equalsIgnoreCase(TargetChest.title)){
                 if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                         || !event.getCurrentItem().hasItemMeta()){
                     event.setCancelled(true);
                 } else{
                     String name = event.getCurrentItem().getItemMeta().getDisplayName();
//                     String chest = TargetChest.playerdata.get(player.getUniqueId()).get;


                     Player Target = TargetChest.getData(player).getPlayer();

                    if(name.equalsIgnoreCase("§f" + player.getName())){
                        TargetChest.getData(Target).openinv(player);
                        TargetChest.getData(player).setMenu(inv);

                    } else if (name.equalsIgnoreCase("§f" + Target.getName())){
                        TargetChest.getData(player).openinv(player);
                    }
                     switch (name){
                         case "§f1,000":
                             Shift(player,event.isShiftClick(),1000);
                             break;
                         case "§f10,000":
                             Shift(player,event.isShiftClick(),10000);
                             break;
                         case "§f100,000":
                             Shift(player,event.isShiftClick(),100000);
                             break;
                         case "§f1,000,000":
                             Shift(player,event.isShiftClick(),1000000);
                             break;
                         case "§d설정 §7(클릭해서 변경)":
                             switch (TargetChest.getData(player).getType()){
                                 case Plus:
                                     TargetChest.getData(player).setType(Type.Minus);
                                     break;
                                 case Minus:
                                     TargetChest.getData(player).setType(Type.Plus);
                                     break;
                                 case Multiply:
                                     TargetChest.getData(player).setType(Type.Multiply);
                                     break;
                             }
                             break;
                     }
                 }
                 event.setCancelled(true);
             }
        }
    }

    public void Shift(Player player, boolean shift, int coin){
        if(!shift){
            TargetChest.getData(player).SetCoins(coin);
        } else{
            TargetChest.getData(player).SetCoins(coin * 64);
        }
    }
}
