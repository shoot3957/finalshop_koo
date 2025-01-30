package menu_member;

import _mall.MenuCommand;
import dao.MemberDAO;
import util.Util;

public class MemberQuit implements MenuCommand {

    private String memberId;

    @Override
    public void init() {
        System.out.println("=====[ 회원 탈퇴 ]=====");
        this.memberId = Util.getValue("탈퇴할 회원 ID: ");  // 탈퇴할 회원 ID를 입력받습니다.
    }

    @Override
    public boolean update() {
        MemberDAO dao = MemberDAO.getInstance();

        // 회원 ID로 회원 정보 조회
        if (dao.getMemberById(memberId) != null) {
            // 회원 탈퇴 처리
            if (dao.deleteMember(memberId)) {
                System.out.println("회원 탈퇴가 완료되었습니다.");
            } else {
                System.out.println("회원 탈퇴에 실패했습니다.");
            }
        } else {
            System.out.println("존재하지 않는 회원입니다.");
        }

        // 탈퇴 후 종료
        return true;
    }
}
