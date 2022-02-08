import java.util.HashMap;
import java.util.Map;

public class Client {

    private String name;
    private Integer cash = 10000;
    private boolean isManager;

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, boolean isManager) {
        this.name = name;
        this.isManager = isManager;
    }

    public Client(String name, Integer cash) {
        this.name = name;
        this.cash = cash == null ? 10000 : cash;
    }

    public void addCash(int quantity){
        this.cash = this.cash + quantity;
    }

    public Integer useCash(int quantity) {
        if(quantity > 0){
            if(cash - quantity < 0){
                System.out.println(Main.FONT_RED + "잔액이 부족합니다." + Main.RESET);
                return null;
            }else{
                cash = cash - quantity;
                return cash;
            }
        }else{
            System.out.println(Main.FONT_RED + "1원 이상 입력 해 주세요." + Main.RESET);
            return null;
        }

    }

    public Integer getCash() {
        return cash;
    }

    public String getName() {
        return name;
    }

    public boolean isManager() {
        return isManager;
    }
}
