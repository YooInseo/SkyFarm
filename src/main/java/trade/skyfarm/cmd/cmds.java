package trade.skyfarm.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.gui.TargetChest;
import java.util.HashMap;
import java.util.UUID;

public class cmds implements CommandExecutor {


    /**
     *
     *  Received player :: Target from request send player
     *      -> Remove Receive player data when it's done or cancel
     *  Target player :: Return Received player from request send player's command args
     *  InventoryMenu :: Showing inventory
     *
     */
    private HashMap<UUID,Long> cooldown = new HashMap<>();
    private int cooltimes = 5;
    public static HashMap<UUID, PlayerData> receive = new HashMap<>();
    private int DISTANCE = 10;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(args.length != 0){
            switch (args[0]){
                case "요청":

                    /**
                        Request's target = Accept's player

                     */
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target.equals(player)){
                        player.sendMessage(SkyFarm.prefix + "§c자기 자신에게 거래 요청은 불가능 합니다");
                        return false;
                    } else{
                        if(!receive.containsKey(player.getUniqueId())){
                            if(player.getLocation().distance(target.getLocation()) < DISTANCE){
                                if(cooldown.containsKey(player.getUniqueId())){
                                    long seconds = ((cooldown.get(player.getUniqueId())/ 1000) + cooltimes) - (System.currentTimeMillis() / 1000);
                                    if(seconds > 0){
                                        player.sendMessage(SkyFarm.prefix + "§7" + seconds + "§c초 후에 보내십시오!");
                                    } else{
                                        player.sendMessage(SkyFarm.prefix + target.getName() + " §d님에게 거래 요청을 보냈습니다!");
                                        target.sendMessage(SkyFarm.prefix + player.getName() + " §e님에게 거래 요청을 받았습니다!");
                                        receive.put(target.getUniqueId(), new PlayerData(player));
                                        receive.put(player.getUniqueId(), new PlayerData(target));

                                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                    }
                                } else{
                                    player.sendMessage(SkyFarm.prefix + target.getName() + " §d님에게 거래 요청을 보냈습니다! ");
                                    target.sendMessage(SkyFarm.prefix + player.getName() + " §e님에게 거래 요청을 받았습니다!");
                                    receive.put(target.getUniqueId(),new PlayerData(player));
                                    receive.put(player.getUniqueId(), new PlayerData(target));
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                }
                            } else{
                                player.sendMessage(SkyFarm.prefix + "§c거리가 너무 멉니다!");
                                return false;
                            }
                        }
                        /*
                        else{
                            player.sendMessage(SkyFarm.prefix + "해당 유저는 이미 거래중입니다.");
                            return false;
                        }
                         */
                    }
                    break;

                case "수락":
                    Player request = receive.get(player.getUniqueId()).getPlayer();
                    if(this.receive.containsKey(player.getUniqueId())){
                        if(player.getLocation().distance(request.getLocation()) < DISTANCE) {
                            player.sendMessage(SkyFarm.prefix + request.getName() + " §a님의 거래를 수락 하였습니다!");
                            request.sendMessage("test");
                            TargetChest.TradeMenu(player,request);


                            cooldown.remove(request.getUniqueId());
                        }else{
                            player.sendMessage(SkyFarm.prefix + "§c거리가 너무 멉니다!");
                            return false;
                                }
                        }else{
                            player.sendMessage(SkyFarm.prefix  + "§c요청 거래가 없습니다!");
                        }
                    break;
                case "거절":

                    Player deny = Bukkit.getPlayer(args[1]);
                    if(this.receive.containsKey(deny.getUniqueId()) && this.receive.containsKey(player.getUniqueId())){
                        this.receive.remove(deny.getUniqueId());
                        this.receive.remove(player.getUniqueId());
                        deny.sendMessage(SkyFarm.prefix  +  player.getName() + " §7님이 거래를 거절하였습니다.");
                        player.sendMessage(SkyFarm.prefix  +  deny.getName() + " §7님의 거래를 취소하였습니다.");
                    } else{
                        player.sendMessage(SkyFarm.prefix + deny.getName() + " §c해당 유저에게 온 요청이 없습니다.");
                    }
                    break;
            }
        } else {
            player.sendMessage(SkyFarm.prefix + "§7/거래 요청 <닉네임>");
            player.sendMessage(SkyFarm.prefix + "§7/거래 거절 <닉네임>");
            player.sendMessage(SkyFarm.prefix + "§7/거래 수락");
            
        }
        return false;
    }
}
