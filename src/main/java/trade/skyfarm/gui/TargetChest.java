package trade.skyfarm.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.data.readytype;
import java.util.Arrays;
import java.util.List;

public class TargetChest {

    public static String title = SkyFarm.prefix + "§b교환 메뉴 ";

    /**
     * lores[0] = Accept
     * lores[1] = Ready
     * */
    public static String[] lores = {"§b(쉬프트+클릭) §7거래 상태 설정  §6*수락 가능*","§b(쉬프트+클릭) §7거래 상태 설정" , "(클릭) §7아이템 확인 or 넣기"};
    public static void TradeMenu(Player player,Player request){
        Inventory inv = Bukkit.createInventory(null,27, title);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(cmds.receive.containsKey(player.getUniqueId())){
                    if(getreceive(player).getReady().equals(readytype.Accept) && getreceive(request).getReady().equals(readytype.Accept)){
                        player.sendMessage(SkyFarm.prefix + "§b§l거래를 수락하였습니다!");
                        request.sendMessage(SkyFarm.prefix + "§b§l거래를 수락하였습니다!");
                        TargetChest.getreceive(player).giveItemToOther();
                        TargetChest.getreceive(TargetChest.GetReqeust(player)).giveItemToOther();

                        player.closeInventory();
                        request.closeInventory();

                        getreceive(request).addContent(player);
                        getreceive(player).addContent(request);

                        cmds.receive.remove(player.getUniqueId());
                        cmds.receive.remove(request.getUniqueId());
                    } else{
                        if(getreceive(player).getReady().equals(readytype.Ready) && getreceive(request).getReady().equals(readytype.Ready) || getreceive(player).getReady().equals(readytype.Accept) || getreceive(request).getReady().equals(readytype.Accept) ){
                            getreceive(player).playerhead(inv,Arrays.asList("", lores[0], "      " +Type(request)   , "", "§7인벤토리보다 많은 아이템은 바구니로 들어갑니다!",  "§c(*바구니 미소지일시 아이템 소멸*)"), 11);
                            getreceive(request).playerhead(inv,Arrays.asList("",lores[0] ,lores[2],"      " + Type(player)  ,  "", "§7인벤토리보다 많은 아이템은 바구니로 들어갑니다!", "§c(*바구니 미소지일시 아이템 소멸*)"),15);
                        } else{
                            getreceive(player).playerhead(inv,Arrays.asList("",lores[1], "      " +Type(request)   , "", "§c인벤토리보다 많은 아이템은 바구니로 들어갑니다!",  "§7(*바구니 미소지일시 아이템 소멸)*"), 11);
                            getreceive(request).playerhead(inv,Arrays.asList("",lores[1] ,lores[2] ,"      " + Type(player)  ,  "", "§c인벤토리보다 많은 아이템은 바구니로 들어갑니다!", "§7(*바구니 미소지일시 아이템 소멸)*"),15);
                        }
                        itemStack(Material.GOLD_INGOT, "§e§l거래금", Arrays.asList( ChatColor.WHITE + player.getDisplayName() + ChatColor.GRAY +" > " +ChatColor.GOLD + GetCoin(player) + "냥",
                                ChatColor.WHITE + request.getName() + ChatColor.GRAY +" > " +ChatColor.GOLD + GetCoin(request) + "냥" ,"§7(클릭하여 §e금액 §7설정)" , "§c가지고 계신 금액보다 큰 금액은 허용 되지 않습니다! "),1,13,inv);
                     }
                }

            }
        } .runTaskTimer(SkyFarm.plugin, 1, 1);

        getreceive(player).createInv(player);
        getreceive(request).createInv(request);
        getreceive(player).setMenu(inv);
        getreceive(request).setMenu(inv);
        getreceive(player).setRequest(request);
        getreceive(request).setRequest(player);
        request.openInventory(inv);
        player.openInventory(inv);
    }
    public static String Type(Player player) {
        if (cmds.receive.containsKey(player.getUniqueId())) {
            switch (getreceive(player).getReady()) {
                case NotReady:
                    return getreceive(player).getReady().getA();
                case Ready:
                    return getreceive(player).getReady().getA();
                case Accept:
                    return getreceive(player).getReady().getA();
                default:
                    return "";
            }
        }
        return null;
    }
    public static String GetCoin(Player player){
        if (cmds.receive.containsKey(player.getUniqueId())) {
            return getreceive(player).Format();
        }
        return "";
    }
    public static  ItemStack itemStack(Material material, String name, List<String> lore, int stack, int loc, Inventory inv){
        ItemStack item = new ItemStack(material,stack);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(loc,item);
        return item;
    }
    public static PlayerData getreceive(Player player){
        if(cmds.receive.containsKey(player.getUniqueId())){
              return cmds.receive.get(player.getUniqueId());

        } else{
            return cmds.receive.get(player.getUniqueId());
        }
    }

    /**
     * Usage this method
     * TargetChest.getreceive(TargetChest.GetRequest(player)) // This is the result of PlayerDataClass  from the player's request
     *
     * @param player put the player who's from request
     * @return It just return only 'player' Not a Data 'cause It's gonna be use a lot of cases
     */
    public static Player GetReqeust(Player player){
        return getreceive(player).getRequest();
    }

}
