package menu_admin;

import _mall.MenuCommand;
import dao.MemberDAO;
import util.Util;

public class AdminMember implements MenuCommand {

    @Override
    public void init() {
        // 초기화 메서드 (필요하다면 추가)
    }

    @Override
    public boolean update() {
        while (true) {
            System.out.println("=====[ 회원 관리 ]=====");
            System.out.println("[1] 회원 목록 보기\n[2] 회원 삭제\n[0] 뒤로 가기");
            System.out.println("=====================");

            int sel = Util.getValue("메뉴 입력", 0, 2);  // 메뉴 선택 0~2 사이
            if (sel == 1) {
                showMemberList();
            } else if (sel == 2) {
                deleteMember();
            } else if (sel == 0) {
                return false;  // 뒤로가기
            }
        }
    }

    // 회원 목록 보기
    private void showMemberList() {
        MemberDAO dao = MemberDAO.getInstance();
        dao.getAllMembers().forEach(member -> {
            System.out.println("회원 ID: " + member.getId() + ", 이름: " + member.getMemberName());
        });
    }

    // 회원 삭제
    private void deleteMember() {
        String memberId = Util.getValue("삭제할 회원 ID");
        MemberDAO dao = MemberDAO.getInstance();

        // 관리자 본인 회원은 삭제 불가
        if (memberId.equals(dao.getLoginId())) {
            System.out.println("관리자는 삭제할 수 없습니다.");
            return;
        }

        if (dao.getMemberById(memberId) != null && dao.deleteMember(memberId)) {
            System.out.println("회원 삭제 완료");
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }
}
