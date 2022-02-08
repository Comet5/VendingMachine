public class SellingHistory {

    private final Client client;
    private final Product product;
    private final int quantity;

    public SellingHistory(Client client, Product product, int quantity) {
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return  String.format("구매자: %s  상품: %s  수량: %d  합계: %d\n",
                getClient().getName(),
                getProduct().getName(),
                getQuantity(),
                getProduct().getPrice() * getQuantity());
    }
}
