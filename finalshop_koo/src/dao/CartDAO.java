package dao;

import dto.Cart;
import util.Util;

import java.nio.file.*;
import java.util.*;

public class CartDAO {
    private static CartDAO instance;
    private Map<String, List<Cart>> cartMap; // 회원별로 장바구니 목록을 저장

    private CartDAO() {
        cartMap = new HashMap<>();
        loadCarts(); // 초기화 시 장바구니 정보 로드
    }

    public static CartDAO getInstance() {
        if (instance == null) {
            instance = new CartDAO();
        }
        return instance;
    }

    // 장바구니에 상품 추가
    public boolean addItemToCart(String memberId, int itemNum, int itemCnt) {
        List<Cart> carts = cartMap.getOrDefault(memberId, new ArrayList<>());
        
        // 이미 해당 상품이 장바구니에 있는지 확인
        for (Cart cart : carts) {
            if (cart.getItemNum() == itemNum) {
                cart.setItemCnt(cart.getItemCnt() + itemCnt); // 이미 있으면 수량만 추가
                saveCarts();
                return true;
            }
        }
        
        // 장바구니에 상품을 새로 추가
        Cart newCart = new Cart(memberId, itemNum, itemCnt);
        carts.add(newCart);
        cartMap.put(memberId, carts);
        saveCarts();
        return true;
    }

    // 장바구니에서 상품 제거
    public boolean removeItemFromCart(String memberId, int itemNum) {
        List<Cart> carts = cartMap.get(memberId);
        if (carts == null) return false;
        
        Iterator<Cart> iterator = carts.iterator();
        while (iterator.hasNext()) {
            Cart cart = iterator.next();
            if (cart.getItemNum() == itemNum) {
                iterator.remove();
                saveCarts();
                return true;
            }
        }
        return false;
    }

    // 회원의 장바구니 보기
    public List<Cart> getCartByMember(String memberId) {
        return cartMap.getOrDefault(memberId, new ArrayList<>());
    }

    // 장바구니 데이터 파일로 저장
    void saveCarts() {
        Path path = Paths.get("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/cart.txt");
        List<String> lines = new ArrayList<>();
        
        for (Map.Entry<String, List<Cart>> entry : cartMap.entrySet()) {
            String memberId = entry.getKey();
            for (Cart cart : entry.getValue()) {
                lines.add(memberId + "/" + cart.getItemNum() + "/" + cart.getItemCnt());
            }
        }
        
        Util.writeLinesToFile("src/files/cart.txt", lines);  // Util로 파일 저장
    }

    // 장바구니 파일에서 로드
    private void loadCarts() {
        Path path = Paths.get("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/cart.txt");
        if (!Util.fileExists("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/cart.txt")) {  // Util로 파일 존재 여부 확인
            return;
        }
        
        List<String> lines = Util.readLinesFromFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/cart.txt");  // Util로 파일 읽기
        if (lines != null) {
            for (String line : lines) {
                String[] parts = line.split("/");
                if (parts.length == 3) {
                    String memberId = parts[0];
                    int itemNum = Integer.parseInt(parts[1]);
                    int itemCnt = Integer.parseInt(parts[2]);
                    
                    Cart cart = new Cart(memberId, itemNum, itemCnt);
                    cartMap.computeIfAbsent(memberId, k -> new ArrayList<>()).add(cart);
                }
            }
            System.out.println("장바구니 로드 완료");
        }
    }
}
