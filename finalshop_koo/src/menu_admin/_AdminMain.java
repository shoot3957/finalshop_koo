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
        
    }

    @Override
    public boolean update() {
        while (true) {
            System.out.println("=====[ 관리자 메뉴 ]=====");
            System.out.println("[1] 회원 관리\n[2] 상품 관리\n[3] 게시판 관리\n[4] 로그아웃");
            System.out.println("=========================");

            int sel = Util.getValue("메뉴 입력", 1, 4);  // 메뉴 선택 1~4 사이
            if (sel == 1) {
                cont.setNext("AdminMember");  // 관리자 -> 회원 관리
            } else if (sel == 2) {
                cont.setNext("AdminItem");  // 관리자 -> 상품 관리
            } else if (sel == 3) {
                cont.setNext("AdminBoard");  // 관리자 -> 게시판 관리
            } else if (sel == 4) {
                System.out.println("[ 관리자 로그아웃 ]");
                cont.setNext(null);  // 로그아웃 시 null 처리
                return true;  // 로그아웃 후 종료
            }

            // Menu 변경 후 처리
            if (cont.getNext() != null) {
                return false;  // 메뉴 변경 후 false 반환
            }
        }
    }


}
