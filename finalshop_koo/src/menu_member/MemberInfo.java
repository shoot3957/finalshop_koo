package menu_member;

import java.util.stream.Collectors;

import _mall.MenuCommand;
import dao.MemberDAO;
import dto.Member;
import util.Util;

public class MemberInfo implements MenuCommand {

    private MemberDAO memberDAO;

    public MemberInfo() {
        memberDAO = MemberDAO.getInstance();
    }

    @Override
    public void init() {
        System.out.println("=====[ 회원 정보 관리 ]=====");
        System.out.println("[1] 회원 정보 보기\n[2] 회원 정보 수정\n[0] 뒤로 가기");
        System.out.println("=========================");
    }

    @Override
    public boolean update() {
        int sel = Integer.parseInt(Util.getValue("메뉴 입력"));
        switch (sel) {
            case 1:
                showMemberInfo();  // 회원 정보 보기
                break;
            case 2:
                modifyMemberInfo();  // 회원 정보 수정
                break;
            case 0:
                return true;  // 뒤로 가기
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return false;
    }

    // 회원 정보 출력
    void showMemberInfo() {
        String loginId = memberDAO.getLoginId();  // 로그인된 회원 ID 가져오기
        System.out.println("현재 로그인한 ID: " + loginId);  // 디버깅 출력
        System.out.println("저장된 회원 목록: " + 
        	    memberDAO.getAllMembers().stream().map(Member::getId).collect(Collectors.toList()));

        Member member = memberDAO.getMemberById(loginId);  // 해당 회원 정보 가져오기

        if (member != null) {
            System.out.println("=====[ 회원 정보 ]=====");
            System.out.println("회원 ID: " + member.getId());
            System.out.println("이름: " + member.getMemberName());
            System.out.println("이메일: " + member.getEmail());
            System.out.println("전화번호: " + member.getPhone());
            System.out.println("주소: " + member.getAddress());
            System.out.println("=====================");
        } else {
            System.out.println("회원 정보를 찾을 수 없습니다.");
        }
    }

    // 회원 정보 수정
    private void modifyMemberInfo() {
        String loginId = memberDAO.getLoginId();  // 로그인된 회원 ID 가져오기
        Member member = memberDAO.getMemberById(loginId);  // 해당 회원 정보 가져오기

        if (member != null) {
            System.out.println("=====[ 회원 정보 수정 ]=====");
            String name = Util.getValue("이름: ");
            String email = Util.getValue("이메일: ");
            String phone = Util.getValue("전화번호: ");
            String address = Util.getValue("주소: ");

            // 회원 정보 수정
            member.setMemberName(name);
            member.setEmail(email);
            member.setPhone(phone);
            member.setAddress(address);

            memberDAO.saveMember(member);  // 수정된 정보 저장

            System.out.println("회원 정보가 수정되었습니다.");
        } else {
            System.out.println("회원 정보를 찾을 수 없습니다.");
        }
    }
}
