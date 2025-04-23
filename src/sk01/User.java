// User.java - 사용자 추천 기능
package sk01;

import java.util.*;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;
import com.sk.dao.PLog;

public class User implements PLog {
    public static void run(Scanner sc) {
        MovieDAO dao = new MovieDAO();
        boolean running = true;

        LOG.info("👤 사용자 모드 시작");

        while (running) {
            LOG.info("\n🎯 사용자 추천 메뉴:");
            LOG.info("1. 선택한 영화 조회 : ");
            LOG.info("2. 영화 목록 정리 : ");
            LOG.info("3. 종료");
            LOG.info("입력: ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    LOG.info("🎲 랜덤 추천 영화 중...");
                    List<MovieDTO> all = dao.getAll();
                    if (all.isEmpty()) {
                        LOG.warn("⚠️ 추천 가능한 영화가 없습니다.");
                    } else {
                        MovieDTO pick = all.get(new Random().nextInt(all.size()));
                        LOG.info("추천 영화: " + pick);
                    }
                    break;
                case "2":
                    LOG.info("🎬 감독 이름을 입력하세요:");
                    String director = sc.nextLine().trim();
                    List<MovieDTO> directed = dao.getAll();
                    boolean found = false;
                    for (MovieDTO m : directed) {
                        if (m.getDirector().equalsIgnoreCase(director)) {
                            LOG.info("추천 영화: " + m);
                            found = true;
                        }
                    }
                    if (!found) {
                        LOG.warn("⚠️ 해당 감독의 영화가 없습니다.");
                    }
                    break;
                case "3":
                    LOG.info("👋 사용자 모드 종료");
                    running = false;
                    break;
                default:
                    LOG.warn("❗ 잘못된 입력입니다.");
            }
        }
    }
}