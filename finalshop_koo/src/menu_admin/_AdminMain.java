package menu_admin;

import _mall.MenuCommand;
import controller.MallController;
import util.Util;

public class _AdminMain implements MenuCommand {
    private MallController cont;

    public _AdminMain() {
        cont = MallController.getInstance();
    }

    @Override
    public void init() {
        // 초기화 메서드 (필요하다면 추가)
    }

    @Override
    public boolean update() {
        while (true) {
            System.out.println("=====[ 관리자 메뉴 ]=====");
            System.out.println("[1] 회원 관리\n[2] 상품 관리\n[3] 게시판 관리\n[4] 로그아웃");
            System.out.println("=========================");

            int sel = Util.getValue("메뉴 입력", 1, 4);  // 메뉴 선택 1~4 사이
            if (sel == 1) {
                cont.setNext("AdminMember");
            } else if (sel == 2) {
                cont.setNext("AdminItem");
            } else if (sel == 3) {
                cont.setNext("AdminBoard");
            } else if (sel == 4) {
                System.out.println("[ 관리자 로그아웃 ]");
                cont.setNext(null);
                return true;  // 로그아웃 후 true 반환
            }

            if (cont.getNext() != null) {
                return false;  // 메뉴 변경 후 false 반환
            }
        }
    }
}
