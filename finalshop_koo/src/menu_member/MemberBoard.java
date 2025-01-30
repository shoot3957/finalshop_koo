package menu_member;

import _mall.MenuCommand;
import dao.BoardDAO;
import dto.Board;
import util.Util;

import java.util.*;

public class MemberBoard implements MenuCommand {

    @Override
    public void init() {
        System.out.println("=====[ 게시판 메뉴 ]=====");
        System.out.println("[1] 게시글 보기\n[2] 게시글 추가\n[3] 내 게시글 삭제\n[0] 뒤로 가기");
        System.out.println("=========================");
    }

    @Override
    public boolean update() {
        int sel = Integer.parseInt(Util.getValue("메뉴 입력"));
        switch (sel) {
            case 1:
                showBoard();
                break;
            case 2:
                addPost();
                break;
            case 3:
                deletePost();
                break;
            case 0:
                return true;  // 뒤로 가기
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return false;
    }

    // 게시판 조회
    void showBoard() {
        BoardDAO boardDAO = BoardDAO.getInstance();
        List<Board> boardList = new ArrayList<>(boardDAO.getAllPosts());
        int page = 1;
        final int POSTS_PER_PAGE = 5;

        while (true) {
            // 페이징 처리
            int start = (page - 1) * POSTS_PER_PAGE;
            int end = Math.min(page * POSTS_PER_PAGE, boardList.size());

            System.out.println("=====[ 게시판 ]=====");
            for (int i = start; i < end; i++) {
                Board post = boardList.get(i);
                System.out.println("게시글 ID: " + post.getBoardNum() + ", 제목: " + post.getTitle() + ", 작성자: " + post.getId() +
                        ", 작성일: " + post.getDate() + ", 조회수: " + post.getHits());
            }
            System.out.println("====================");

            // 페이지 이동
            System.out.println("[1] 이전 페이지 [2] 다음 페이지 [0] 뒤로 가기");
            int sel = Integer.parseInt(Util.getValue("메뉴 입력"));
            if (sel == 1 && page > 1) {
                page--;
            } else if (sel == 2 && end < boardList.size()) {
                page++;
            } else if (sel == 0) {
                break;
            }
        }
    }

    // 게시글 추가
    void addPost() {
        String title = Util.getValue("게시글 제목");
        String content = Util.getValue("게시글 내용");
        BoardDAO boardDAO = BoardDAO.getInstance();
        boardDAO.addBoard(title, content, "현재 유저 ID");  // 현재 유저 ID는 로그인한 ID로 대체

        System.out.println("게시글이 추가되었습니다.");
    }

    // 게시글 삭제
    void deletePost() {
        String postId = Util.getValue("삭제할 게시글 번호");
        BoardDAO boardDAO = BoardDAO.getInstance();

        Board post = boardDAO.getBoardByNum(Integer.parseInt(postId));
        if (post != null && post.getId().equals("현재 유저 ID")) {  // 현재 유저 ID는 로그인한 ID로 대체
            boardDAO.deletePost(Integer.parseInt(postId));
            System.out.println("게시글이 삭제되었습니다.");
        } else {
            System.out.println("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
    }
}
