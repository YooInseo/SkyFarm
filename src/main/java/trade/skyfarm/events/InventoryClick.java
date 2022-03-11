package trade.skyfarm.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import trade.skyfarm.data.readytype;
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

             player.sendMessage(event.getCurrentItem().getType().name());

             if(title.equalsIgnoreCase(TargetChest.title +  player.getName())) {
                 if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                         || !event.getCurrentItem().hasItemMeta()) {
                     event.setCancelled(true);
                 } else {
//                     Player Target = TargetChest.getreceive(player).getPlayer();
//                     test(player, Target, event, title);
//                     test(Target,Target, event,title);
//                     event.setCancelled(true);
                  }
             }
        }
    }
    public void Shift(Player player, boolean shift, int coin){
        if(!shift){
            TargetChest.getreceive(player).SetCoins(coin);
        } else{
            TargetChest.getreceive(player).SetCoins(coin * 64);
        }
    }
    public void test(Player player,Player Target, InventoryClickEvent event ,String title){

        String name = event.getCurrentItem().getItemMeta().getDisplayName();
        if(title.equalsIgnoreCase(TargetChest.title +  player.getName())) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
                    || !event.getCurrentItem().hasItemMeta()) {
                event.setCancelled(true);
            } else {
                if (name.equalsIgnoreCase("§f" + Target.getName())){


                } else if (name.equalsIgnoreCase("§f" + player.getName())){


                }else if(name.equalsIgnoreCase("§d설정 §7(클릭해서 변경)")){
                    switch (TargetChest.getreceive(Target).getType()){
                        case Plus:
                            TargetChest.getreceive(Target).setType(Type.Minus);
                            break;
                        case Minus:
                            TargetChest.getreceive(Target).setType(Type.Plus);
                            break;
                        case Multiply:
                            TargetChest.getreceive(Target).setType(Type.Multiply);
                            break;
                    }
                }
                switch (name){
                    case "§f1,000":
                        Shift(Target, event.isShiftClick(),1000);
                        break;
                    case "§f10,000":
                        Shift(Target,event.isShiftClick(),10000);
                        break;
                    case "§f100,000":
                        Shift(Target,event.isShiftClick(),100000);
                        break;
                    case "§f1,000,000":
                        Shift(Target,event.isShiftClick(),1000000);
                        break;
                    case "§c초기화":
                        TargetChest.getreceive(Target).SetCoins(0);
                        break;
                    case "§c준비안됨":
                        TargetChest.getreceive(Target).setReady(player,Target, readytype.Ready);
                        break;
                    case "§a준비됨":
                        TargetChest.getreceive(Target).setReady(player,Target,readytype.Accept);
                        break;
                    case "§b§l수락":
                        TargetChest.AcceptTrade(player, Target);
                        break;
                }
            }
        }
    }

    public boolean set(){
        return false;
    }
}
