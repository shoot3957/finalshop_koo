package menu_member;

import _mall.MenuCommand;
import dao.ItemDAO;
import dto.Item;
import util.Util;

import java.util.*;

public class MemberCart implements MenuCommand {

    private List<Item> cart;  // 장바구니 목록
    private int totalPrice;    // 총 가격

    public MemberCart() {
        this.cart = new ArrayList<>();
        this.totalPrice = 0;
    }

    @Override
    public void init() {
        System.out.println("=====[ 장바구니 메뉴 ]=====");
        System.out.println("[1] 장바구니 보기\n[2] 장바구니에 상품 추가\n[3] 장바구니에서 상품 삭제\n[4] 구매 완료\n[0] 뒤로 가기");
        System.out.println("=========================");
    }

    @Override
    public boolean update() {
        int sel = Integer.parseInt(Util.getValue("메뉴 입력"));
        switch (sel) {
            case 1:
                viewCart();  // 장바구니 보기
                break;
            case 2:
                addItemToCart();  // 장바구니에 상품 추가
                break;
            case 3:
                removeItemFromCart();  // 장바구니에서 상품 삭제
                break;
            case 4:
                checkout();  // 구매 완료
                break;
            case 0:
                return true;  // 뒤로 가기
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return false;
    }

    // 장바구니에 아이템 추가
    void addItemToCart() {
        ItemDAO itemDAO = ItemDAO.getInstance();
        int itemNum = Integer.parseInt(Util.getValue("상품 번호 입력"));
        int quantity = Integer.parseInt(Util.getValue("수량 입력"));

        Item item = itemDAO.getItemByNum(itemNum);
        if (item != null) {
            addToCart(item, quantity);
            System.out.println(item.getItemName() + "이(가) 장바구니에 추가되었습니다.");
        } else {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }

    // 장바구니 내용 출력
    void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        System.out.println("=====[ 장바구니 ]=====");
        cart.forEach(item -> {
            System.out.println("상품명: " + item.getItemName() + ", 가격: " + item.getPrice());
        });
        System.out.println("총 가격: " + totalPrice);
        System.out.println("=====================");
    }

    // 장바구니에서 상품 삭제
    void removeItemFromCart() {
        int itemNum = Integer.parseInt(Util.getValue("삭제할 상품 번호 입력"));
        ItemDAO itemDAO = ItemDAO.getInstance();
        Item item = itemDAO.getItemByNum(itemNum);

        if (item != null) {
            removeFromCart(item);
        } else {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }

    // 구매 완료
    void checkout() {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어 있어 구매할 수 없습니다.");
            return;
        }

        System.out.println("=====[ 구매 내역 ]=====");
        cart.forEach(item -> {
            System.out.println("상품명: " + item.getItemName() + ", 가격: " + item.getPrice());
        });
        System.out.println("총 가격: " + totalPrice);

        // 결제 처리 (단순히 구매 완료 메시지 출력)
        System.out.println("구매가 완료되었습니다. 감사합니다!");

        // 장바구니 초기화
        cart.clear();
        totalPrice = 0;
    }

    // 장바구니에 아이템 추가
    void addToCart(Item item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            cart.add(item);  // 수량만큼 장바구니에 추가
            totalPrice += item.getPrice();  // 가격 합산
        }
    }

    // 장바구니에서 상품 삭제
    void removeFromCart(Item item) {
        if (cart.contains(item)) {
            cart.remove(item);
            totalPrice -= item.getPrice();  // 가격 차감
            System.out.println(item.getItemName() + "이(가) 장바구니에서 삭제되었습니다.");
        } else {
            System.out.println("장바구니에 해당 상품이 없습니다.");
        }
    }

    // 장바구니에 담긴 상품이 있는지 체크
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}
