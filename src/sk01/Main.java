// Main.java
package sk01;

import java.util.Scanner;

import com.sk.dao.PLog;

public class Main implements PLog {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        LOG.info("🎬 영화 프로그램 시작");

        while (running) {
            LOG.info("\n📋 모드를 선택하세요:");
            LOG.info("1. 관리자 모드");
            LOG.info("2. 사용자 모드");
            LOG.info("3. 종료");
            LOG.info("입력: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    AdminMain.run(sc);
                    break;

                case "2":
                    runUser(sc);
                    break;

                case "3":
                    running = false;
                    LOG.info("👋 프로그램 종료");
                    break;

                default:
                    LOG.error("❗ 잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        sc.close();
        LOG.info("✅ 프로그램 정상 종료");
    }

    // 👤 사용자 모드
    public static void runUser(Scanner sc) {
        boolean running = true;

        while (running) {
            LOG.info("\n👤 사용자 메뉴");
            LOG.info("1. 감독별 영화 보기");
            LOG.info("2. 랜덤 영화 추천");
            LOG.info("3. 사용자 모드 종료");
            LOG.info("선택: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    Director director = new Director();
                    director.showDirectorMenu();
                    break;
                case "2":
                    Random_C rand = new Random_C();
                    rand.recommendRandomMovie();
                    break;
                case "3":
                    running = false;
                    LOG.info("👋 사용자 모드 종료");
                    break;
                default:
                    LOG.error("❗ 잘못된 입력입니다.");
            }
        }
    }
}
