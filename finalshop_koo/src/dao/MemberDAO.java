package dao;

import dto.Member;
import util.Util;
import java.util.*;

public class MemberDAO {
    private static MemberDAO instance;
    private Map<String, Member> members;
    private String loginId;  // 로그인한 사용자의 ID 저장

    private MemberDAO() {
        members = new HashMap<>();
        loadMembers(); // 초기화 시 파일에서 회원 정보 불러오기
    }

    public static MemberDAO getInstance() {
        if (instance == null) instance = new MemberDAO();
        return instance;
    }

    // 로그인한 사용자 설정
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    // 로그인한 사용자 ID 반환
    public String getLoginId() {
        return loginId;
    }

    // 회원 삭제
    public boolean deleteMember(String memberId) {
        if (members.containsKey(memberId)) {
            members.remove(memberId);
            saveMembers();  // 회원 저장
            return true;
        }
        return false;
    }

    // 아이디와 비밀번호가 유효한 회원인지 확인하는 메서드
    public Member isValidMember(String id, String pw) {
        Member member = members.get(id);  // 아이디로 회원 찾기
        if (member != null && member.getPassword().equals(pw)) {  // 비밀번호 일치 여부 확인
            return member;  // 유효한 회원이면 Member 객체 반환
        }
        return null;  // 유효하지 않은 회원이면 null 반환
    }

    // 기타 메서드 (insertMember, getAllMembers 등)
    public boolean insertMember(String id, String password, String name) {
        if (members.containsKey(id)) return false;
        members.put(id, new Member(id, password, name));
        saveMembers();  // 회원 저장
        return true;
    }

    public Member getMemberById(String id) {
        return members.get(id);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public void saveMembers() {
        List<String> lines = new ArrayList<>();
        for (Member member : members.values()) {
            lines.add(member.getId() + "/" + member.getPassword() + "/" + member.getMemberName());
        }
        Util.writeLinesToFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/member.txt", lines);  // 파일로 저장
    }

    private void loadMembers() {
        List<String> lines = Util.readLinesFromFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/member.txt");  // 파일 읽기
        if (lines != null) {
            for (String line : lines) {
                String[] parts = line.split("/");
                String id = parts[1];
                String password = parts[2];
                String name = parts[3];
                Member member = new Member(id, password, name);
                members.put(id, member);
            }
            System.out.println("회원 정보 로드 완료");
        }
    }
}
