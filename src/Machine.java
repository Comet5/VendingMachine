import java.util.*;
import java.util.stream.Collectors;

public class Machine {

    private List<Product> products;
    private int cash;

    private List<SellingHistory> sellingHistories = new ArrayList<>();

    public Machine() {
        this.products = new ArrayList<>();
        this.cash = 0;
    }

    public void inputCashWithThousand(int count){
        if(count < 1){
            System.out.println(Main.FONT_RED + "한 장 이상의 천원을 투입 해 주세요." + Main.RESET);
            return;
        }else{
            cash = cash + 1000 * count;
            System.out.println("천원 투입되었습니다. 현재 잔액: " + this.cash);
        }
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void sell(Client client, Product product){
        if(cash < product.getPrice()){
            System.out.println(Main.FONT_RED + "잔액이 부족합니다. 지폐를 투입해 주세요." + Main.RESET);
            return;
        }

        if(product.getQuantity() < 1){
            System.out.println(Main.FONT_RED + "품절된 상품입니다." + Main.RESET);
            return;
        }

        product.sell(1);
        cash = cash - product.getPrice();
        sellingHistories.add(new SellingHistory(client, product, 1));
    }

    public void sellingHistory(){
        if(sellingHistories.size() < 1) {
            System.out.println(Main.FONT_RED + "판매기록이 없습니다." + Main.RESET);
            return;
        }

        //이용자 구매기록
        StringBuilder historyStr = new StringBuilder();
        Map<Client, List<Product>> clientToProduct = new HashMap<>();
        for(SellingHistory sellingHistory : sellingHistories){
            historyStr.append(sellingHistory.toString());
            if(!clientToProduct.containsKey(sellingHistory.getClient())) clientToProduct.put(sellingHistory.getClient(), new ArrayList<>());
            clientToProduct.get(sellingHistory.getClient()).add(sellingHistory.getProduct());
        }
        System.out.println("구매기록\n"+historyStr);


        //이용자 구매 순위
        clientToProduct = clientToProduct.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> o.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Map<Product, Integer> productToSellingCount = new HashMap<>();
        historyStr = new StringBuilder();
        int rank = clientToProduct.size();
        for(Client client : clientToProduct.keySet()){
            List<Product> products = clientToProduct.get(client);
            historyStr.insert(0, String.format("%d. %s - %d회 구매\n", rank, client.getName(), products.size()));
            rank--;

            for(Product product : products){
                if(!productToSellingCount.containsKey(product)) productToSellingCount.put(product, 1);
                else productToSellingCount.put(product, productToSellingCount.get(product) + 1);
            }
        }
        System.out.println("구매순위\n" + historyStr);


        //베스트 상품
        StringBuilder highestProductStr = new StringBuilder();
        int max = Collections.max(productToSellingCount.values());
        for(Product product : productToSellingCount.keySet()){
            if(max == productToSellingCount.get(product)){
                this.products.set(this.products.indexOf(product), new Product(product.getName(), product.getPrice() + 100, product.getQuantity()));
                highestProductStr.append(String.format("%s, %d개 판매, %d -> %d 가격인상\n", product.getName(), max, product.getPrice(), product.getPrice() + 100));
            }
        }
        System.out.println("베스트상품\n"+highestProductStr);


        //판매기록 삭제
        sellingHistories.clear();
    }

    public Integer getChanges(){
        if(cash < 1){
            System.out.println(Main.FONT_RED + "잔돈이 없습니다." + Main.RESET);
            return null;
        }
        int changes = cash;
        cash = 0;
        return changes;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getCash() {
        return cash;
    }
}
