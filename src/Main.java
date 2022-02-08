import java.util.*;

import static java.util.stream.Collectors.joining;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String FONT_RED = "\u001B[31m";
    public static final String FONT_YELLOW = "\u001B[33m";
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        List<Client> clients = new ArrayList<>();
        Client na = new Client("나사장", true);
        Client yoo = new Client("유사원");
        Client lee = new Client("이대리");
        Client kim = new Client("김과장");
        Client park = new Client("박차장");
        clients.add(na);
        clients.add(yoo);
        clients.add(lee);
        clients.add(kim);
        clients.add(park);
        int clientCount = clients.size();

        StringBuilder clientListStr = new StringBuilder();
        for(int i = 0; i < clientCount; i++){
            Client client = clients.get(i);
            clientListStr.append(String.format("%d.%s\t", i, client.getName()));
        }


        Machine machine = new Machine();


        while (true){
            System.out.println("\n\n\n\n\n\n\n\n=======================================================\n");
            int clientIdx = inputNum("자판기 이용자를 선택해 주세요. \n" + FONT_YELLOW + clientListStr + RESET + "\n:",0, clientCount - 1);

            Client client = clients.get(clientIdx);

            if(machine.getProducts().size() < 1 && !client.isManager()){
                System.out.println(FONT_RED + "현재 등록된 상품이 없습니다. 나사장을 불러와 주세요." + RESET + "\n");
                continue;
            }

            if(client.isManager()){
                boolean escapeMachine = false;
                while (!escapeMachine){
                    System.out.println("\n\n\n\n\n\n=======================================================");
                    System.out.println("관리자 메뉴");
                    int menuNum = inputNum(FONT_YELLOW + "0.상품추가\t1.정산완료\t2.재시작\t3.이용자선택" + RESET + "\n:",0, 3);
                    switch (menuNum){
                        case 0:
                            machine.addProduct(
                                    new Product(inputStr("상품이름: ")
                                            , inputNum("가격: ",1, 1000000000)
                                            , inputNum("수량: ",1, 1000)
                                    )
                            );
                            break;
                        case 1:
                            machine.sellingHistory();
                            break;
                        case 2:
                            System.out.println("이용자에게 10,000원이 지급되었습니다.");
                            clients.stream().forEach(client1 -> client1.addCash(10000));
                            break;
                        case 3:
                            escapeMachine = true;
                            break;
                    }
                }

            }else{
                boolean escapeMachine = false;
                while (!escapeMachine){
                    System.out.println("\n\n\n\n\n\n=======================================================");
                    System.out.println(String.format("자판기잔액: %s | 이용자: %s    잔액: %s\n",machine.getCash(), client.getName(), client.getCash()));
                    int menuNum = inputNum(FONT_YELLOW + "0.상품선택    1.지폐(천원)투입    2.잔돈받기    3.이용자선택" + RESET + "\n:",0, 3);
                    switch (menuNum){
                        case 0:
                            StringBuilder productListStr = new StringBuilder();
                            List<Product> products = machine.getProducts();
                            for(int i = 0; i < products.size(); i++){ // 상품목록 표시
                                Product product = products.get(i);
                                productListStr.append(String.format(FONT_YELLOW + "%d.%s" + RESET, i, product.getName()));
                                if(product.getQuantity() < 1) productListStr.append(FONT_RED + "(SOLDOUT)\n" + RESET);
                                else productListStr.append(String.format("(가격:%d 수량:%d)\n", product.getPrice(), product.getQuantity()));
                            }

                            System.out.println("\n\n구매할 상품을 선택해 주세요.");
                            int productIdx = inputNum(productListStr + ":",0, products.size() - 1);
                            Product product = products.get(productIdx);
                            machine.sell(client, product);

                            break;
                        case 1:
                            Integer cash = client.useCash(1000);
                            if(cash != null){
                                machine.inputCashWithThousand(1);
                            }
                            break;
                        case 2:
                            Integer changes = machine.getChanges();
                            if(changes != null){
                                client.addCash(changes);
                            }
                            break;
                        case 3:
                            escapeMachine = true;
                            break;
                    }
                }


            }

        }

    }

    public static String inputStr(String message){
        System.out.print(message);
        String str = sc.next();
        return str;
    }

    public static int inputNum(String message, int start, int end){
        while (true){
            System.out.print(message);
            try {
                int num = sc.nextInt();
                if(num < start || num > end){
                    System.out.println(FONT_RED + "허용되지 않는 숫자입니다." + RESET + "\n");
                    continue;
                }

                return num;
            }catch (InputMismatchException e){
                System.out.println(FONT_RED + "번호만 입력해 주세요." + RESET + "\n");
                sc.next();
            }
        }

    }

}
