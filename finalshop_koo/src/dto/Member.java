package dto;

public class Member {
    private static int num = 1000; // 회원 번호 자동 증가를 위한 static 변수
    private int memberNum;
    private String id;
    private String pw;
    private String memberName;
    private String email;
    private String phone;
    private String address;

    // 생성자
    public Member(String id, String pw, String memberName) {
        this.memberNum = num++;  // 회원 번호 자동 증가
        this.id = id;
        this.pw = pw;
        this.memberName = memberName;
        this.email = "";  // 기본값
        this.phone = "";  // 기본값
        this.address = "";  // 기본값
    }

    // 기존 회원 정보를 불러올 때 사용하는 생성자
    public Member(int memberNum, String id, String pw, String memberName) {
        this.memberNum = memberNum;  // 기존 회원 번호 유지
        this.id = id;
        this.pw = pw;
        this.memberName = memberName;
        this.email = "";
        this.phone = "";
        this.address = "";

        // 기존 회원 정보 중 가장 큰 회원 번호를 static 변수 num에 반영
        if (memberNum >= num) {
            num = memberNum + 1;
        }
    }

    // getter 메서드
    public int getMemberNum() {
        return memberNum;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return pw;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // setter 메서드
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setPassword(String pw) {
        this.pw = pw;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Member 객체를 파일에 저장할 수 있는 형식으로 변환하는 메서드
    public String toFileString() {
        return memberNum + "/" + id + "/" + pw + "/" + memberName + "/" + email + "/" + phone + "/" + address;
    }

    @Override
    public String toString() {
        return "Member{memberNum=" + memberNum + ", id='" + id + "', memberName='" + memberName + "', email='" + email + "', phone='" + phone + "', address='" + address + "'}";
    }
}
