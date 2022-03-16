package trade.skyfarm.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.gui.TargetChest;

import java.text.DecimalFormat;

public class PlayerData {

    private int Coin;
    private Player player;
    private Player request;
    private readytype ready = readytype.NotReady;
    private Inventory inv = null;
    private Inventory menu = null;
    private boolean isSetCoin = false;
    private boolean isOpen = false;

    public PlayerData(Player player){
        this.player = player;
    }

    public int getCoin() {
        return Coin;
    }

    public void SetCoins(int coin){
            this.Coin = coin;
    }

    public Player getPlayer() {
        return player;
    }

    public readytype getReady() {
        return ready;
    }

    public void setReady(readytype ready) {
            this.ready = ready;
    }

    public Inventory getInv() {
        return inv;
    }

    public Inventory createInv(Player player){
        if(this.inv == null){
            Inventory inv = Bukkit.createInventory(null,54, player.getDisplayName());
            this.inv = inv;
            return inv;
        }
        return null;
    }

    public Inventory OpenMenu(Player player){
        player.openInventory(menu);
        return menu;
    }

    public void setMenu(Inventory menu) {
        this.menu = menu;
    }

    public boolean isSetCoin() {
        return isSetCoin;
    }

    public void setisSetCoin(boolean setCoin) {
        isSetCoin = setCoin;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setRequest(Player request) {
        this.request = request;
    }

    public Player getRequest() {
        return request;
    }

    public ItemStack addContent(){
        for(ItemStack inven : inv.getContents()){
            try {
                if(!(inven == null)){
                    player.getInventory().addItem(inven);
                    player.sendMessage("거래가 취소되어 아이템을 받았습니다!");
                    return inven;
                }
            }catch (NullPointerException e){
                player.sendMessage("§c아이템이 없습니다");
            }
        }
        return null;
    }

    public String Format(){
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(getCoin());
    }

}

