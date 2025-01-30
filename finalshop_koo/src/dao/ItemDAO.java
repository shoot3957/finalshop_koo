package dao;

import dto.Item;
import util.Util;

import java.nio.file.*;
import java.util.*;

public class ItemDAO {
    private static ItemDAO instance;
    private Map<Integer, Item> itemMap;  // 상품 번호를 키로 하는 상품 정보 맵

    private ItemDAO() {
        itemMap = new HashMap<>();
        loadItems(); // 초기화 시 상품 정보 로드
    }

    public static ItemDAO getInstance() {
        if (instance == null) {
            instance = new ItemDAO();
        }
        return instance;
    }

    // 상품 추가
    public boolean addItem(String categoryName, String itemName, int price) {
        int itemNum = Item.getNextItemNum();  // 새로운 상품 번호 생성
        Item newItem = new Item(itemNum, categoryName, itemName, price);
        itemMap.put(itemNum, newItem);
        saveItems();  // 상품 저장
        return true;
    }

    // 상품 조회
    public Item getItemByNum(int itemNum) {
        return itemMap.get(itemNum);  // 해당 번호의 상품 반환
    }

    // 모든 상품 목록 조회
    public Collection<Item> getAllItems() {
        return itemMap.values();  // 모든 상품 반환
    }

    // 상품 삭제
    public boolean deleteItem(int itemNum) {
        if (itemMap.containsKey(itemNum)) {
            itemMap.remove(itemNum);  // 상품 삭제
            saveItems();  // 삭제 후 파일 저장
            return true;
        }
        return false;  // 상품이 존재하지 않으면 삭제 실패
    }

    // 상품 파일로 저장
    void saveItems() {
        Path path = Paths.get("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/item.txt");
        List<String> lines = new ArrayList<>();
        
        for (Item item : itemMap.values()) {
            // 상품 정보를 파일에 저장할 형식으로 변환
            lines.add(item.getItemNum() + "/" + item.getCategoryName() + "/" + item.getItemName() + "/" + item.getPrice());
        }
        
        Util.writeLinesToFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/item.txt", lines);  // Util로 파일 저장
    }

    // 상품 파일에서 로드
    private void loadItems() {
        Path path = Paths.get("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/item.txt");
        if (!Util.fileExists("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/item.txt")) {  // Util로 파일 존재 여부 확인
            return;
        }
        
        List<String> lines = Util.readLinesFromFile("C:/koo/githubWorkspace/level10/finalshop_koo/finalshop_koo/src/file/item.txt");  // Util로 파일 읽기
        if (lines != null) {
            for (String line : lines) {
                String[] parts = line.split("/");  // '/' 구분자로 분리
                if (parts.length == 4) {
                    int itemNum = Integer.parseInt(parts[0]);
                    String categoryName = parts[1];
                    String itemName = parts[2];
                    int price = Integer.parseInt(parts[3]);
                    
                    // 상품 객체 생성 후 맵에 추가
                    Item item = new Item(itemNum, categoryName, itemName, price);
                    itemMap.put(itemNum, item);
                }
            }
            System.out.println("상품 로드 완료");
        }
    }
}
