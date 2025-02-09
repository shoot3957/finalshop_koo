package menu_member;

import _mall.MenuCommand;
import dao.ItemDAO;
import dto.Item;
import util.Util;

import java.util.*;

public class MemberShopping implements MenuCommand {

    private static List<Item> cart = new ArrayList<>(); 
    private static int totalPrice = 0;

    @Override
    public void init() {
        System.out.println("=====[ 쇼핑몰 ]=====");
        
        ItemDAO itemDAO = ItemDAO.getInstance();
        List<Item> itemList = new ArrayList<>(itemDAO.getAllItems());

        itemList.forEach(item -> {
            System.out.println("상품 번호: " + item.getItemNum() + ", 상품명: " + item.getItemName() + ", 가격: " + item.getPrice());
        });

        System.out.println("===================");
    }

    @Override
    public boolean update() {
        while (true) {
            int itemNum = Integer.parseInt(Util.getValue("구매할 상품 번호 (0 종료)"));
            if (itemNum == 0) break;

            ItemDAO itemDAO = ItemDAO.getInstance();
            Item item = itemDAO.getItemByNum(itemNum);
            
            if (item != null) {
                int quantity = Util.getValue("구매할 수량 (1~100개)", 1, 100);
                totalPrice += item.getPrice() * quantity;
                for (int i = 0; i < quantity; i++) {
                    cart.add(item);
                }
                System.out.println(item.getItemName() + " " + quantity + "개 추가됨!");
            } else {
                System.out.println(" 상품 번호가 잘못되었습니다.");
            }
        }

        // 구매 내역 출력
        System.out.println("=====[ 장바구니 ]=====");
        cart.forEach(item -> {
            System.out.println("상품명: " + item.getItemName() + ", 가격: " + item.getPrice());
        });
        System.out.println("총 가격: " + totalPrice);
        System.out.println("===================");

        return false;  
    }
}
