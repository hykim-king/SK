package sk01;

import java.util.*;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;
import com.sk.dao.PLog;

public class User implements PLog {
    public static void run(Scanner sc) {
        MovieDAO dao = new MovieDAO();
        List<MovieDTO> movies = dao.getAll();
        List<MovieDTO> result = new ArrayList<>(movies);

        LOG.info("🌍 원하는 국가를 입력하세요:");
        String country = sc.nextLine().trim();
        result.removeIf(m -> !m.getCountry().equalsIgnoreCase(country));

        LOG.info("🎭 원하는 장르를 입력하세요:");
        String genre = sc.nextLine().trim();
        result.removeIf(m -> !m.getGenre().equalsIgnoreCase(genre));

        LOG.info("📅 원하는 연도를 입력하세요:");
        String year = sc.nextLine().trim();
        result.removeIf(m -> !(String.valueOf(m.getYear()).equals(year)));

        if (result.isEmpty()) {
            LOG.info("⚠️ 조건에 맞는 영화가 없습니다.");
            return;
        }

        LOG.info("😄 지금 기분을 선택하세요:");
        LOG.info("1. 아무거나 추천");
        LOG.info("2. 피곤해.. 짧은 영화 추천");
        String mood = sc.nextLine().trim();

        if ("2".equals(mood)) {
            for (MovieDTO m : result) {
                try {
                    int rt = Integer.parseInt(m.getRuntime().replaceAll("[^0-9]", ""));
                    if (rt < 100) {
                        LOG.info("💤 짧은 영화 추천: " + m);
                    }
                } catch (NumberFormatException e) {
                    LOG.warn("⚠️ 러닝타임 형식 오류: " + m.getRuntime());
                }
            }
        } else {
            MovieDTO pick = result.get(new Random().nextInt(result.size()));
            LOG.info("🎯 추천 영화: " + pick);
        }

        LOG.info("👋 사용자 추천 종료");
    }
}