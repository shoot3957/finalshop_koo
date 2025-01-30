package dto;

public class Cart {
    private static int num;  // 장바구니 번호를 자동으로 증가시키기 위한 변수
    private int cartNum;     // 장바구니 번호
    private String id;       // 회원 ID
    private int itemNum;     // 상품 번호
    private int itemCnt;     // 상품 수량

    public Cart(String id, int itemNum, int itemCnt) {
        this.cartNum = ++num;  // 장바구니 번호는 자동으로 증가
        this.id = id;
        this.itemNum = itemNum;
        this.itemCnt = itemCnt;
    }

    public int getCartNum() {
        return cartNum;
    }

    public String getId() {
        return id;
    }

    public int getItemNum() {
        return itemNum;
    }

    public int getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(int itemCnt) {
        this.itemCnt = itemCnt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartNum=" + cartNum +
                ", id='" + id + '\'' +
                ", itemNum=" + itemNum +
                ", itemCnt=" + itemCnt +
                '}';
    }
}
