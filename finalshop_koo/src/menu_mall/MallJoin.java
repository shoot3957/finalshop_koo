package menu_mall;

import _mall.MenuCommand;
import controller.MallController;
import dao.MemberDAO;
import util.Util;

import java.util.regex.Pattern;

public class MallJoin implements MenuCommand {
    private MallController cont;

    @Override
    public void init() {
        cont = MallController.getInstance();
    }

    @Override
    public boolean update() {
        System.out.println("=====[ 회원가입 ]=====");
        MemberDAO dao = MemberDAO.getInstance();
        
        String id = Util.getValue("아이디 ");
        if (id.isEmpty()) {
            System.out.println("아이디는 비어 있을 수 없습니다.");
            return false;
        }

        if (dao.getMemberById(id) != null) {
            System.out.println("이미 사용하는 아이디");
            return false;
        }
        
        String pw = Util.getValue("비밀번호 ");
        if (pw.isEmpty()) {
            System.out.println("비밀번호는 비어 있을 수 없습니다.");
            return false;
        }

        // 비밀번호 정규식 검사 (영어 대소문자 + 숫자만 허용)
        if (!Pattern.matches("^[a-zA-Z0-9]+$", pw)) {
            System.out.println("비밀번호는 영어 대소문자와 숫자만 포함해야 합니다.");
            return false;
        }

        String name = Util.getValue("이름 ");
        if (name.isEmpty()) {
            System.out.println("이름은 비어 있을 수 없습니다.");
            return false;
        }

        
        if (dao.insertMember(id, pw, name)) {
            System.out.println("[ 회원 추가 완료 ]");
        } else {
            System.out.println("[ 회원 추가 실패 ]");
        }
        
        // 메뉴 이동 처리 (다음 화면으로 이동)
        cont.setNext("MallMain");
        return false;
    }
}
