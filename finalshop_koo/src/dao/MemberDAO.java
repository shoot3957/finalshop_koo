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
        System.out.println("🛠 MemberDAO 인스턴스 생성됨!"); // ✅ 싱글톤 체크
    }

    public static MemberDAO getInstance() {
        if (instance == null) {
            instance = new MemberDAO();
        }
        return instance;
    }

    // 로그인한 사용자 설정
    public void setLoginId(String loginId) {
        this.loginId = loginId;
        System.out.println("✅ 로그인 ID 설정됨: " + this.loginId); // ✅ 로그 추가
    }

    // 로그인한 사용자 ID 반환
    public String getLoginId() {
        System.out.println("📌 현재 로그인한 ID 반환: " + loginId); // ✅ 로그 추가
        return loginId;
    }

    // 회원 삭제
    public boolean deleteMember(String memberId) {
        if (members.containsKey(memberId)) {
            members.remove(memberId); // ✅ 해당 회원 삭제
            saveMembers(); // ✅ 삭제된 상태로 다시 저장
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

    public boolean insertMember(String id, String password, String name) {
        if (members.containsKey(id)) return false;

        Member newMember = new Member(id, password, name);
        members.put(id, newMember);
        saveMember(newMember);  // 기존 saveMembers() 대신 saveMember() 사용

        return true;
    }

    public Member getMemberById(String id) {
        System.out.println("🔍 getMemberById() 호출: " + id); // ✅ 디버깅 로그 추가
        System.out.println("📌 현재 저장된 회원 목록: " + members.keySet()); // ✅ 저장된 회원 확인
        return members.get(id);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public void saveMember(Member member) {
        String line = member.getMemberNum() + "/" + member.getId() + "/" + member.getPassword() + "/" + member.getMemberName();
        Util.appendLineToFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/member.txt", line);
    }

    public void saveMembers() {
        List<String> lines = new ArrayList<>();
        for (Member member : members.values()) {
            // ✅ 모든 회원을 다시 저장 (삭제된 회원 제외)
            lines.add(member.getMemberNum() + "/" + member.getId() + "/" + member.getPassword() + "/" + member.getMemberName());
        }
        Util.writeLinesToFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/member.txt", lines);
    }

    private void loadMembers() {
        List<String> lines = Util.readLinesFromFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/member.txt");
        if (lines != null) {
            for (String line : lines) {
                String[] parts = line.split("/");
                int memberNum = Integer.parseInt(parts[0]);  // 회원 번호
                String id = parts[1];
                String password = parts[2];
                String name = parts[3];

                Member member = new Member(memberNum, id, password, name);
                members.put(id, member);
            }
            System.out.println("📌 회원 정보 로드 완료: " + members.keySet()); // ✅ 저장된 회원 확인
        }
    }
}
