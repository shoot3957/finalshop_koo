package dto;

public class Item {
    private static int num;  // 상품 번호를 자동으로 증가시키기 위한 변수
    private int itemNum;     // 상품 번호
    private String categoryName; // 카테고리 이름
    private String itemName; // 상품 이름
    private int price; // 상품 가격
    private int sales;

    public Item(int itemNum, String categoryName, String itemName, int price) {
        this.itemNum = itemNum;
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.price = price;
        this.sales = 0;
    }

    public static int getNextItemNum() {
        return ++num;  // 상품 번호를 자동으로 증가
    }

    public int getItemNum() {
        return itemNum;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }
    
    public void addSales(int quantity) {
        this.sales += quantity;
    }

    // 판매 수량 반환
    public int getSales() {
        return sales;
    }
    @Override
    public String toString() {
        return "Item{" +
                "itemNum=" + itemNum +
                ", categoryName='" + categoryName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                '}';
    }
}
