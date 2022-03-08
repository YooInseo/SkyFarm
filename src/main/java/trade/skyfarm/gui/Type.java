package trade.skyfarm.gui;

public enum Type {

    Minus("§c-"),Plus("§a+"),Multiply("§b*");

    private String a = "";
    Type(String a){
        this.a = a;
    }

    public String getA() {
        return a;
    }
}
