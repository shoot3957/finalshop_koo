package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Member;
import util.Util;

public class FileDAO {

    private static final String BASE_PATH = "C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/";

    enum FileName {
        BOARD("board.txt"), MEMBER("member.txt"), ITEM("item.txt"), CART("cart.txt");

        private String name;

        FileName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private FileDAO() {}

    private static FileDAO instance = new FileDAO();

    public static FileDAO getInstance() {
        return instance;
    }

    // 파일 초기화 (파일이 없으면 생성)
    public void init() {
        for (FileName fileName : FileName.values()) {
            String filePath = BASE_PATH + fileName.getName();
            if (!Util.fileExists(filePath)) {
                Util.createFile(filePath);
            } else {
                System.out.println(filePath + " 파일 이미 존재.");
            }
        }
    }

    // 모든 파일 로드
    public void loadAllFiles() {
        for (FileName fileName : FileName.values()) {
            String filePath = BASE_PATH + fileName.getName();
            List<String> lines = Util.readLinesFromFile(filePath);
            if (lines != null) {
                System.out.println(fileName.getName() + " 로드 완료: " + lines.size() + " 개의 라인");
            } else {
                System.out.println(fileName.getName() + " 파일 로드 실패");
            }
        }
    }

    // 모든 파일 저장
    public void saveAllFiles() {
        for (FileName fileName : FileName.values()) {
            String filePath = BASE_PATH + fileName.getName();
            List<String> lines = getLinesForFile(fileName);
            Util.writeLinesToFile(filePath, lines);
        }
    }

    // 파일별 데이터 생성
    private List<String> getLinesForFile(FileName fileName) {
        List<String> lines = new ArrayList<>();
        switch (fileName) {
            case MEMBER:
                MemberDAO memberDAO = MemberDAO.getInstance();
                for (Member member : memberDAO.getAllMembers()) {
                    lines.add(member.toFileString());
                }
                break;
            case BOARD:
                BoardDAO boardDAO = BoardDAO.getInstance();
                boardDAO.saveBoards();
                break;
            case ITEM:
                ItemDAO itemDAO = ItemDAO.getInstance();
                itemDAO.saveItems();
                break;
            case CART:
                CartDAO cartDAO = CartDAO.getInstance();
                cartDAO.saveCarts();
                break;
        }
        return lines;
    }

    // 회원 정보 로드
    public List<Member> loadMembersFromFile() {
        List<Member> memberList = new ArrayList<>();
        String filePath = BASE_PATH + "member.txt";
        List<String> lines = Util.readLinesFromFile(filePath);
        if (lines != null) {
            for (String line : lines) {
                String[] parts = line.split("/");
                if (parts.length == 3) {
                    String id = parts[1];
                    String password = parts[2];
                    String name = parts[3];
                    memberList.add(new Member(id, password, name));
                }
            }
        }
        return memberList;
    }
}
