package menu_admin;

import _mall.MenuCommand;
import dao.BoardDAO;  // 게시글 관리 DAO
import util.Util;

public class AdminBoard implements MenuCommand {

    @Override
    public void init() {
        System.out.println("=====[ 관리자 게시판 ]=====");
        System.out.println("[1] 게시판 조회\n[2] 게시글 삭제\n[0] 뒤로 가기");
        System.out.println("=========================");
    }

    @Override
    public boolean update() {
        // 메뉴 선택 처리
        int sel = Integer.parseInt(Util.getValue("메뉴 입력")); // 메뉴 입력 받기
        if (sel == 1) {
            // 게시판 조회
            showBoard();
        } else if (sel == 2) {
            // 게시글 삭제
            deletePost();
        } else if (sel == 0) {
            // 뒤로 가기
            return true; // true를 반환하여 뒤로 가기를 처리
        }
        return false;
    }

    // 게시판 조회
    private void showBoard() {
        BoardDAO dao = BoardDAO.getInstance();
        dao.getAllPosts().forEach(post -> {
            System.out.println("게시글 ID: " + post.getId() + ", 제목: " + post.getTitle() + ", 내용: " + post.getContents());  // getContent() -> getContents()
        });
    }

    // 게시글 삭제
    private void deletePost() {
        int postId = Integer.parseInt(Util.getValue("삭제할 게시글 ID"));
        BoardDAO dao = BoardDAO.getInstance();
        if (dao.deletePost(postId)) {
            System.out.println("게시글 삭제 완료");
        } else {
            System.out.println("존재하지 않는 게시글입니다.");
        }
    }
}
