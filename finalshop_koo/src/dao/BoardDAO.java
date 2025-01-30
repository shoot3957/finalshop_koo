package dao;

import dto.Board;
import util.Util;
import java.nio.file.*;
import java.util.*;

public class BoardDAO {
    private static BoardDAO instance;
    private Map<Integer, Board> boardMap;  // 게시글 번호를 키로 하는 게시글 맵
    private static final String FILE_PATH = "C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/board.txt";

    private BoardDAO() {
        boardMap = new HashMap<>();
        loadBoards();  // 초기화 시 게시글 정보 로드
    }

    public static BoardDAO getInstance() {
        if (instance == null) {
            instance = new BoardDAO();
        }
        return instance;
    }

    // 게시글 추가
    public boolean addBoard(String title, String id, String contents) {
        int boardNum = Board.getNextBoardNum();  // 새로운 게시글 번호 생성
        Board newBoard = new Board(boardNum, title, id, contents);
        boardMap.put(boardNum, newBoard);
        saveBoards();  // 게시글 저장
        return true;
    }

    // 게시글 조회
    public Board getBoardByNum(int boardNum) {
        return boardMap.get(boardNum);  // 해당 번호의 게시글 반환
    }

    // 모든 게시글 목록 조회
    public Collection<Board> getAllPosts() {
        return boardMap.values();  // 모든 게시글 반환
    }

    // 게시글 삭제
    public boolean deletePost(int boardNum) {
        if (boardMap.containsKey(boardNum)) {
            boardMap.remove(boardNum);  // 게시글 삭제
            saveBoards();  // 삭제 후 파일 저장
            return true;
        }
        return false;  // 게시글이 존재하지 않으면 삭제 실패
    }

    // 게시글 파일로 저장
    void saveBoards() {
        List<String> lines = new ArrayList<>();
        
        for (Board board : boardMap.values()) {
            // 게시글 정보를 파일에 저장할 형식으로 변환
            lines.add(board.getBoardNum() + "/" + board.getTitle() + "/" + board.getContents() + "/" + board.getId() + "/" + board.getDate() + "/" + board.getHits());
        }

        Util.writeLinesToFile(FILE_PATH, lines);  // Util로 파일 저장
    }

    // 게시글 파일에서 로드
    private void loadBoards() {
        if (!Util.fileExists(FILE_PATH)) {  // 파일이 없으면 생성 후 빈 리스트 반환
            System.out.println("게시글 파일이 존재하지 않음. 새로 생성합니다.");
            Util.createFile(FILE_PATH);
            return;
        }

        List<String> lines = Util.readLinesFromFile(FILE_PATH);  // Util로 파일 읽기
        if (lines.isEmpty()) {
            System.out.println("게시글 파일이 비어 있음.");
            return;
        }

        for (String line : lines) {
            String[] parts = line.split("/");  // '/' 구분자로 분리
            if (parts.length == 6) {
                try {
                    int boardNum = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String contents = parts[2];
                    String id = parts[3];
                    String date = parts[4];
                    int hits = Integer.parseInt(parts[5]);

                    // 게시글 객체 생성 후 맵에 추가
                    Board board = new Board(boardNum, title, id, contents, date, hits);
                    boardMap.put(boardNum, board);
                } catch (NumberFormatException e) {
                    System.out.println("게시글 데이터 변환 오류: " + Arrays.toString(parts));
                }
            }
        }
        System.out.println("게시글 로드 완료: " + boardMap.size() + "개");
    }
}
