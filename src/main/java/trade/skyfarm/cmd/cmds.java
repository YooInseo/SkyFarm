package trade.skyfarm.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.gui.TargetChest;

import java.util.HashMap;
import java.util.UUID;

public class cmds implements CommandExecutor {

    private HashMap<UUID,Long> cooldown = new HashMap<>();
    private int cooltimes = 5;
    public static HashMap<UUID,Player> recieved = new HashMap<>();
    private int DISTANCE = 10;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(args.length != 0){
            switch (args[0]){
                case "요청":

                    Player target = Bukkit.getPlayer(args[1]);

                    if(target.equals(player)){
                        player.sendMessage(SkyFarm.prefix + "§c자기 자신에게 거래 요청은 불가능 합니다");
                        return false;
                    } else{
                        if(!recieved.containsKey(target.getUniqueId()) || !recieved.containsKey(player.getUniqueId())){
                            if(player.getLocation().distance(target.getLocation()) < DISTANCE){
                                if(cooldown.containsKey(player.getUniqueId())){
                                    long seconds = ((cooldown.get(player.getUniqueId())/ 1000) + cooltimes) - (System.currentTimeMillis() / 1000);
                                    if(seconds > 0){
                                        player.sendMessage(SkyFarm.prefix + "§7" + seconds + "§c초 후에 보내십시오!");
                                    } else{
                                        player.sendMessage(SkyFarm.prefix + target.getName() + " §d님에게 거래 요청을 보냈습니다!");
                                        target.sendMessage(SkyFarm.prefix + player.getName() + " §e님에게 거래 요청을 받았습니다!");
                                        recieved.put(target.getUniqueId(), player);
                                        recieved.put(player.getUniqueId(), target);
                                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                    }
                                } else{
                                    player.sendMessage(SkyFarm.prefix + target.getName() + " §d님에게 거래 요청을 보냈습니다! ");
                                    target.sendMessage(SkyFarm.prefix + player.getName() + " §e님에게 거래 요청을 받았습니다!");
                                    recieved.put(target.getUniqueId(), player);
                                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                                }
                            } else{
                                player.sendMessage(SkyFarm.prefix + "§c거리가 너무 멉니다!");
                                return false;
                            }
                        } else{
                            player.sendMessage(SkyFarm.prefix + "해당 유저는 이미 거래중입니다.");
                            return false;
                        }
                    }
                    break;
                case "수락":
                    Player recieved = Bukkit.getPlayer(args[1]);
                    if(player.getLocation().distance(recieved.getLocation()) < DISTANCE){
                        if(recieved.equals(player)){
                            recieved.sendMessage(SkyFarm.prefix + "§c자기 자신에게 거래 수락은 불가능 합니다");
                            return false;
                        } else{
                            if(this.recieved.containsKey(player.getUniqueId())){
                                player.sendMessage(SkyFarm.prefix + recieved.getName() + " §a님의 거래를 수락 하였습니다!");
                                TargetChest.TargetChest(player, recieved);
                                TargetChest.TargetChest(recieved, player);
                                cooldown.remove(recieved.getUniqueId());
                                this.recieved.remove(player.getUniqueId());
                            } else{
                                player.sendMessage(SkyFarm.prefix  + "§c해당 유저로부터 온 거래가 없습니다!");
                            }
                        }
                    } else{
                        player.sendMessage(SkyFarm.prefix + "§c거리가 너무 멉니다!");
                        return false;
                    }
                    break;
            }
        } else {
            player.sendMessage(SkyFarm.prefix + "§7/거래 요청 <닉네임>");
            player.sendMessage(SkyFarm.prefix + "§7/거래 수락 <닉네임>");
        }
        return false;
    }
}
