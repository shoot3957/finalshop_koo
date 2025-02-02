package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Board;
import util.Util;

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
        saveSingleBoard(newBoard);  // 한 개의 게시글만 추가
        return true;
    }
    
    private void saveSingleBoard(Board board) {
        String line = board.getBoardNum() + "/" + board.getTitle() + "/" + board.getContents() + "/" 
                      + board.getId() + "/" + board.getDate() + "/" + board.getHits();

        try {
            Files.write(Paths.get(FILE_PATH), 
                        Collections.singletonList(line),  // 한 줄씩 추가
                        StandardOpenOption.CREATE, 
                        StandardOpenOption.APPEND);
            System.out.println("게시글 저장 완료: " + board.getBoardNum());
        } catch (IOException e) {
            System.out.println("게시글 저장 실패: " + e.getMessage());
        }
    }
    // 게시글 조회
    public Board getBoardByNum(int boardNum) {
        return boardMap.get(boardNum);  // 해당 번호의 게시글 반환
    }
    
    public int getMaxBoardNum() {
        return boardMap.keySet().stream().max(Integer::compare).orElse(0);
    }
    // 모든 게시글 목록 조회
    public Collection<Board> getAllPosts() {
        return boardMap.values();  // 모든 게시글 반환
    }

    // 게시글 삭제
    public boolean deletePost(int boardNum) {
        if (!boardMap.containsKey(boardNum)) {
            return false;
        }
        
        boardMap.remove(boardNum);
        saveBoards(); // 파일에서도 삭제 반영
        return true;
    }

    // 게시글 파일로 저장
    void saveBoards() {
        List<String> lines = new ArrayList<>();
        
        for (Board board : boardMap.values()) {
            lines.add(board.getBoardNum() + "/" + board.getTitle() + "/" + 
                      board.getContents() + "/" + board.getId() + "/" + 
                      board.getDate() + "/" + board.getHits());
        }

        Util.writeLinesToFile(FILE_PATH, lines);  // 파일 저장
    }



    // 게시글 파일에서 로드
    private void loadBoards() {
        if (!Util.fileExists(FILE_PATH)) {  
            System.out.println("게시글 파일이 존재하지 않음. 새로 생성합니다.");
            Util.createFile(FILE_PATH);
            return;
        }

        List<String> lines = Util.readLinesFromFile(FILE_PATH);
        if (lines.isEmpty()) {
            System.out.println("게시글 파일이 비어 있음.");
            return;
        }

        for (String line : lines) {
            String[] parts = line.split("/");  
            if (parts.length == 6) {  // 올바른 형식인지 확인
                try {
                    int boardNum = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String contents = parts[2]; // ✅ [수정] 제목 다음에 '내용'!
                    String id = parts[3];       // ✅ [수정] 내용 다음에 '작성자 ID'!
                    String date = parts[4];
                    int hits = Integer.parseInt(parts[5]);

                    // ✅ [올바른 순서로 Board 객체 생성]
                    Board board = new Board(boardNum, title, contents, id, date, hits);
                    boardMap.put(boardNum, board);
                } catch (NumberFormatException e) {
                    System.out.println("⚠ 게시글 데이터 변환 오류: " + Arrays.toString(parts));
                }
            }
        }
        System.out.println("✅ 게시글 로드 완료: " + boardMap.size() + "개");
    }



}
