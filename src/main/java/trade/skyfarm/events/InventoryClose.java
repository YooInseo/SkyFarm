package trade.skyfarm.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.readytype;
import trade.skyfarm.gui.TargetChest;

public class InventoryClose implements Listener {

    @EventHandler
    public void close(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String title = event.getView().getTitle();

        /**
         * 인벤토리를 닫을 모든 경우의 수
         * 1. 몇 '냥'을 지정할때 (거래금 설정) isSetCoin
         * 2. 다른 플레이어의 상자를 열때 isOpen
         * 3. 나의 상자를 열때 isOpen
         * 4. 그냥 거래를 취소 하고 싶어 메뉴 창을 닫을때. !(1,2,3)
         */

        Bukkit.getServer().getScheduler().runTask(SkyFarm.plugin, new Runnable() {
            @Override
            public void run() {
                if (cmds.receive.containsKey(player.getUniqueId())) {
                    if(title.equalsIgnoreCase(TargetChest.title)){
                        /**1번과3번의 경우의 수 */
                        if(TargetChest.getreceive(player).isSetCoin() || TargetChest.getreceive(player).isOpen()){} else{
                            if(!TargetChest.getreceive(TargetChest.GetReqeust(player)).getReady().equals(readytype.Accept) && !TargetChest.getreceive(player).getReady().equals(readytype.Accept)) {
                                if(!player.equals(TargetChest.getreceive(player))){
                                    player.sendMessage(SkyFarm.prefix + "§7거래가 취소되었습니다!");
                                    TargetChest.getreceive(player).getRequest().closeInventory();
                                    TargetChest.getreceive(player).getRequest().sendMessage(SkyFarm.prefix + "§7거래가 취소되었습니다!");

                                    
                                    cmds.receive.remove(TargetChest.getreceive(player).getRequest().getUniqueId());
                                    cmds.receive.remove(player.getUniqueId());
                                } else{
                                    TargetChest.getreceive(player).getRequest().sendMessage(SkyFarm.prefix + "§7거래가 취소되었습니다!"); //상대 플레이어 불러오기
                                }
                            }
                        }
                    } else{
                        if(!title.equalsIgnoreCase(TargetChest.title)){
                            if(title.equalsIgnoreCase(player.getDisplayName())){
                                TargetChest.getreceive(player).OpenMenu(player);
                                TargetChest.getreceive(player).setOpen(false);
                            }else if(!TargetChest.getreceive(player).getPlayer().getDisplayName().equalsIgnoreCase("")){/** 2번의 경우의 수일때  Request Player 가 Null 이 아닐경우*/
                                if(title.equalsIgnoreCase( TargetChest.getreceive(player).getPlayer().getDisplayName())){
                                    TargetChest.getreceive(player).OpenMenu(player);
                                    TargetChest.getreceive(player).setOpen(false);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}

