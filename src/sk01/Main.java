// Main.java
package sk01;

import java.util.Scanner;

import com.sk.dao.PLog;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;

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
            LOG.info("1. MovieRecommender");
            LOG.info("2. 감독별 영화 보기");
            LOG.info("3. 랜덤 영화 추천");
            LOG.info("4. 영화 검색");
            LOG.info("5. 사용자 모드 종료");
            LOG.info("선택: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    new MovieRecommender().start();
                    break;
                case "2":
                    new Director().showDirectorMenu();
                    break;
                case "3":
                    new Random_C().recommendRandomMovie();
                    break;
                case "4":
                    LOG.info("🔍 검색할 영화 제목 입력: ");
                    String searchTitle = sc.nextLine().trim();
                    MovieDTO found = new MovieDAO().get(new MovieDTO(searchTitle));
                    if (found == null) {
                        LOG.error("❌ '{}' 영화가 존재하지 않습니다.", searchTitle);
                    } else {
                        LOG.info("[{}]", found);
                    }
                    break;
                case "5":
                    running = false;
                    LOG.info("👋 사용자 모드 종료");
                    break;
                default:
                    LOG.error("❗ 잘못된 입력입니다.");
            }
        }
    }
}
