// Main.java - 공통 진입점 (번호 선택)
package sk01;

import java.util.Scanner;
import com.sk.dao.PLog;

public class Main implements PLog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        LOG.info("🎬 영화 프로그램 시작");

        // 메뉴 출력
        LOG.info("\n📋 모드를 선택하세요:");
        LOG.info("1. 관리자 모드");
        LOG.info("2 . 사용자 모드");
        LOG.info("3. 종료");
        LOG.info("입력: ");

        String choice = sc.nextLine().trim();

        // 선택 분기
        switch (choice) {
            case "1":
                LOG.info("🔐 관리자 모드로 이동 중...");
                AdminMain.run(sc); // 관리자 기능 실행
                break;
            case "2":
                LOG.info("👤 사용자 모드로 이동 중...");
                User.run(sc); // 사용자 추천 기능 실행
                break;
            case "3":
                LOG.info("👋 프로그램 종료");
                break;
            default:
                LOG.warn("❗ 잘못된 입력입니다. 프로그램을 종료합니다.");
        }

        sc.close();
        LOG.info("✅ 프로그램 정상 종료");
    }
}
