package trade.skyfarm.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import trade.skyfarm.SkyFarm;
import trade.skyfarm.gui.Type;
import java.util.ArrayList;

public class PlayerData {

    private Type type = Type.Plus;
    private int Coin;
    private Player player;
    private readytype ready = readytype.NotReady;
    private ArrayList<ItemStack> items = new ArrayList<>();

    public PlayerData(Player player){
        this.player = player;
    }

    public int getCoin() {
        return Coin;
    }

    public Type getType() {
        return type;
    }

    public void addCoin(int Coin){
        this.Coin += Coin;
    }

    public void SetCoins(int coin){
        if(coin != 0){
            switch (type){
                case Plus:
                    Coin += coin;
                    break;
                case Minus:
                    if(!(Coin - coin < 0)){
                        Coin -= coin;
                    }
                    break;
                case Multiply:

                    break;
            }
        } else{
            this.Coin = coin;
        }

    }

    public Player getPlayer() {
        return player;
    }

    public void MinusCoin(int Coin){
        this.Coin -= Coin;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Material Change(){
        if(getCoin() < 1000){
            return Material.GOLD_INGOT;
        } else if (getCoin() < 10000 ) {
            return Material.EMERALD;
        } else if (getCoin() < 100000) {
            return Material.DIAMOND;
        }  else if (getCoin() < 1000000 ) {
            return Material.DIAMOND_BLOCK;
        } else{
            return Material.DIAMOND_BLOCK;
        }

    }

    public Material Ready(){
        switch (ready){
            case Ready:
                return Material.LIME_DYE;
             case NotReady:
                return Material.GRAY_DYE;
            case Accept:
                return Material.LIME_DYE;
            default:
                return Material.GRAY_DYE;
        }
    }

    public readytype getReady() {
        return ready;
    }

    public void setReady(Player player, Player Target, readytype ready) {
        if(this.player == player){
            this.ready = ready;
        } else{
            player.sendMessage(SkyFarm.prefix + " 상대의 상태를 조절 할 수 없습니다.");
        }

    }

    public ItemStack getItems(int i) {
        return items.get(i);
    }

    public void addItem(ItemStack item){
        items.add(item);
    }

    public void removeItem(ItemStack item){
        items.remove(item);
    }
}

