package menu_admin;

import _mall.MenuCommand;
import dao.ItemDAO;
import util.Util;

public class AdminItem implements MenuCommand {

    @Override
    public void init() {
       
    }

    @Override
    public boolean update() {
        while (true) {
            System.out.println("=====[ 상품 관리 ]=====");
            System.out.println("[1] 아이템 추가\n[2] 아이템 삭제\n[3] 총 매출 및 판매량 높은 아이템 출력\n[0] 뒤로 가기");
            System.out.println("=======================");

            int sel = Util.getValue("메뉴 입력", 0, 3);  // 메뉴 선택 0~3 사이
            if (sel == 1) {
                addItem();
            } else if (sel == 2) {
                deleteItem();
            } else if (sel == 3) {
                showSalesAndItemCount();
            } else if (sel == 0) {
                return false;  // 뒤로가기
            }
        }
    }

    // 아이템 추가
    private void addItem() {
        ItemDAO dao = ItemDAO.getInstance();
        String name = Util.getValue("아이템 이름");
        String category = Util.getValue("아이템 카테고리");
        int price = Integer.parseInt(Util.getValue("아이템 가격"));

        if (dao.getAllItems().stream().anyMatch(item -> item.getItemName().equals(name))) {
            System.out.println("이미 존재하는 아이템입니다.");
            return;
        }

        dao.addItem(category, name, price);
        System.out.println("아이템 추가 완료.");
    }

    // 아이템 삭제
    private void deleteItem() {
        ItemDAO dao = ItemDAO.getInstance();
        int itemNum = Integer.parseInt(Util.getValue("삭제할 아이템 번호"));

        if (dao.getItemByNum(itemNum) != null) {
            dao.deleteItem(itemNum);
            System.out.println("아이템 삭제 완료.");
        } else {
            System.out.println("존재하지 않는 아이템입니다.");
        }
    }

    // 총 매출 및 판매량 높은 아이템 출력
    private void showSalesAndItemCount() {
        ItemDAO dao = ItemDAO.getInstance();
        dao.getAllItems().stream()
            .sorted((item1, item2) -> Integer.compare(item2.getSales(), item1.getSales()))  // 판매량 기준 내림차순
            .forEach(item -> {
                System.out.println("아이템 이름: " + item.getItemName() + ", 판매량: " + item.getSales());
            });
    }
}
