package trade.skyfarm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.gui.TargetChest;

public class ChatEvent implements Listener {
    @EventHandler
    public void Chat(PlayerChatEvent event){
        Player player = event.getPlayer();
        if(cmds.receive.containsKey(player.getUniqueId()) && TargetChest.getreceive(player).isSetCoin()){
            try {
                int CurrentCoin = Integer.parseInt(event.getMessage());
                if(CurrentCoin < 0){
                    player.sendMessage(SkyFarm.prefix + "§c다시 입력해주세요! §7(음수 포함X)");
                } else{
                    player.sendMessage(SkyFarm.prefix + "§e당신이 줄 금액은 §6§l" + CurrentCoin + "§e냥 입니다.");
                    TargetChest.getreceive(player).SetCoins(CurrentCoin);
                    TargetChest.getreceive(player).OpenMenu(player);
                    TargetChest.getreceive(player).setisSetCoin(false);
                }
                event.setCancelled(true);

            } catch (NumberFormatException e){
                player.sendMessage(SkyFarm.prefix + "§4정수를 입력하세요! §7(0보다 큰 숫자 입력 필요)");
                event.setCancelled(true);
            }
        }
    }
}
