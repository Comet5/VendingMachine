import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {

    private String name;
    private int price;
    private int quantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void sell(int quantity){
        if(quantity > 0 && this.quantity - quantity >= 0){
            System.out.println(name + " 나왔습니다.");
            this.quantity = this.quantity - quantity;
        }else{
            System.out.println(Main.FONT_RED + "수량("+ this.quantity +")이 부족합니다." + Main.RESET);
        }
    }

    public void increasePrice(int quantity){
        this.price = this.price + quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
