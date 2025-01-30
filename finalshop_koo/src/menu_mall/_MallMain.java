package menu_mall;

import _mall.MenuCommand;
import controller.MallController;
import util.Util;

public class _MallMain implements MenuCommand {
    private MallController cont;

    @Override
    public void init() {
        cont = MallController.getInstance();
        System.out.println("=====[ 쇼핑몰 ]=====");
        System.out.println("[1] 회원가입\n[2] 로그인\n[0] 종료");
        System.out.println("=====================");
    }

    @Override
    public boolean update() {
        // Util.getValue를 사용하여 정수값을 받아옴
        int sel = Util.getValue("메뉴 입력", 0, 2); // 0, 2 범위로 메뉴 선택
        if (sel == 0) {
            System.out.println("[ 프로그램 종료 ]");
            cont.setNext(null);
        } else if (sel == 1) {
            cont.setNext("MallJoin"); // 회원가입으로 이동
        } else if (sel == 2) {
            cont.setNext("MallLogin"); // 로그인으로 이동
        }

        return false;
    }
}
