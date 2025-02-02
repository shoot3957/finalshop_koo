package dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import dao.BoardDAO;

public class Board {
    private static int num;  // 게시글 번호를 자동으로 증가시키기 위한 변수
    private int boardNum;    // 게시글 번호
    private String title;    // 제목
    private String id;       // 작성자 ID
    private String date;     // 작성일
    private String contents; // 게시글 내용
    private int hits;        // 조회수

    public Board(int boardNum, String title, String id, String contents) {
        this.boardNum = boardNum;
        this.title = title;
        this.id = id;
        this.contents = contents;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.hits = 0;  // 기본 조회수는 0
    }

    public Board(int boardNum, String title, String id, String contents, String date, int hits) {
        this.boardNum = boardNum;
        this.title = title;
        this.id = id;
        this.contents = contents;
        this.date = date;
        this.hits = hits;
    }

    public static int getNextBoardNum() {
        if (num == 0) {  // 초기 값 설정
            num = BoardDAO.getInstance().getMaxBoardNum();
        }
        return ++num;
    }


    public int getBoardNum() {
        return boardNum;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }

    public int getHits() {
        return hits;
    }

    public void incrementHits() {
        this.hits++;  // 조회수 증가
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardNum=" + boardNum +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                ", hits=" + hits +
                '}';
    }
}
