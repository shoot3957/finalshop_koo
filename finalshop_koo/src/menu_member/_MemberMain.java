package menu_member;

import _mall.MenuCommand;
import util.Util;

public class _MemberMain implements MenuCommand {

    @Override
    public void init() {
        System.out.println("=====[ 멤버 메뉴 ]=====");
        System.out.println("[1] 쇼핑하기\n[2] 게시판\n[3] 장바구니\n[4] 내 정보\n[5] 탈퇴\n[0] 종료");
        System.out.println("=======================");
    }

    @Override
    public boolean update() {
        int sel = Integer.parseInt(Util.getValue("메뉴 선택"));
        switch (sel) {
            case 1:
                MenuCommand shoppingCommand = new MemberShopping();
                shoppingCommand.init();
                shoppingCommand.update();  // 쇼핑하기
                break;
            case 2:
                // 게시판 메뉴 처리
                handleBoardMenu();
                break;
            case 3:
                new MemberCart().viewCart();  // 장바구니 보기
                break;
            case 4:
                new MemberInfo().showMemberInfo();  // 내 정보 보기
                break;
            case 5:
                MenuCommand quitCommand = new MemberQuit();
                quitCommand.init();   // 초기화
                quitCommand.update(); // 회원 탈퇴 처리
                return true;  // 탈퇴 후 종료
            case 0:
                return true;  // 종료
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return false;
    }

    // 게시판 메뉴 처리
    private void handleBoardMenu() {
        MemberBoard memberBoard = new MemberBoard();
        while (true) {
            System.out.println("=====[ 게시글 메뉴 ]=====");
            System.out.println("[1] 게시글 보기\n[2] 게시글 추가\n[3] 내 게시글(삭제)\n[0] 뒤로가기");
            int boardSel = Integer.parseInt(Util.getValue("메뉴 선택"));
            switch (boardSel) {
                case 1:
                    memberBoard.showBoard();  // 게시글 보기
                    break;
                case 2:
                    memberBoard.addPost();  // 게시글 추가
                    break;
                case 3:
                    memberBoard.deletePost();  // 내 게시글 삭제
                    break;
                case 0:
                    return;  // 뒤로가기
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
