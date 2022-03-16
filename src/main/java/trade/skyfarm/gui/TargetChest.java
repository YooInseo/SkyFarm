package trade.skyfarm.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.cmd.cmds;
import trade.skyfarm.data.PlayerData;
import trade.skyfarm.data.readytype;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TargetChest {

    public static String title = SkyFarm.prefix + "§b교환 메뉴 ";

    public static void TradeMenu(Player player,Player request){
        Inventory inv = Bukkit.createInventory(null,27, title);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(cmds.receive.containsKey(player.getUniqueId())){
                    if(getreceive(player).getReady().equals(readytype.Accept) && getreceive(request).getReady().equals(readytype.Accept)){
                        player.sendMessage(SkyFarm.prefix + "§b§l거래를 수락하였습니다!");
                        request.sendMessage(SkyFarm.prefix + "§b§l거래를 수락하였습니다!");
                        player.closeInventory();
                        request.closeInventory();
                        cmds.receive.remove(player.getUniqueId());
                        cmds.receive.remove(request.getUniqueId());
                    } else{
                        itemStack(Material.CHEST, player.getName(), Arrays.asList("","§b(쉬프트+클릭) §7거래 상태 설정  ", "      " +Type(player)   , "", "§c인벤토리보다 많은 아이템은 바구니로 들어갑니다!",  "§7(*바구니 미소지일시 아이템 소멸)*"),1,11,inv);
                        itemStack(Material.GOLD_INGOT, "§e§l거래금", Arrays.asList( ChatColor.WHITE + player.getDisplayName() + ChatColor.GRAY +" > " +ChatColor.GOLD + GetCoin(player) + "냥",
                                ChatColor.WHITE + request.getName() + ChatColor.GRAY +" > " +ChatColor.GOLD + GetCoin(request) + "냥" ,"§7(클릭하여 §e금액 §7설정)"),1,13,inv);
                        itemStack(Material.CHEST, request.getName(),Arrays.asList("","§b(쉬프트+클릭) §7거래 상태 설정" ,"(클릭) §7아이템 확인 or 넣기","      " + Type(request)  ,  "", "§c인벤토리보다 많은 아이템은 바구니로 들어갑니다!", "§7(*바구니 미소지일시 아이템 소멸)*"),1,15,inv);
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
    public static Player GetReqeust(Player player){
        return getreceive(player).getRequest();
    }

}
