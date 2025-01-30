package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Util {

    private Util() {}

    private static Scanner sc = new Scanner(System.in);

    // 파일 존재 여부 확인
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    // 파일 읽기 (파일이 존재하지 않으면 빈 리스트 반환)
    public static List<String> readLinesFromFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (!Files.exists(path)) {
                System.out.println("파일이 존재하지 않습니다: " + filePath);
                return List.of();  // 파일이 없으면 빈 리스트 반환
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("파일 읽기 실패 (" + filePath + "): " + e.getMessage());
            return List.of();  // 예외 발생 시 빈 리스트 반환
        }
    }

    // 파일에 내용 쓰기 (파일이 없으면 생성 후 저장)
    public static void writeLinesToFile(String filePath, List<String> lines) {
        Path path = Paths.get(filePath);
        try {
            // 디렉토리 존재 여부 확인 후 없으면 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines);
            System.out.println(filePath + " 저장 완료");
        } catch (IOException e) {
            System.out.println("파일 저장 실패 (" + filePath + "): " + e.getMessage());
        }
    }

    // 파일 생성 (디렉토리도 함께 생성)
    public static void createFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent()); // 디렉토리가 없으면 생성
                Files.createFile(path);
                System.out.println("파일 생성 완료: " + filePath);
            } else {
                System.out.println("파일 이미 존재: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("파일 생성 중 오류 발생 (" + filePath + "): " + e.getMessage());
        }
    }

    // 사용자 입력 받기
    public static String getValue(String msg) {
        System.out.print(msg);
        return sc.next();
    }
    
    public static int getValue(String msg, int min, int max) {
        int value;
        while (true) {
            System.out.print(msg + " (" + min + " ~ " + max + "): ");
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.println("잘못된 범위입니다. 다시 시도하세요.");
                }
            } else {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                sc.next(); // 잘못된 입력을 넘기기 위해 next() 사용
            }
        }
        return value;
    }
}
