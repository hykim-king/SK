package sk01;

import java.util.List;
import java.util.Scanner;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;
import com.sk.dao.PLog;

public class Admin implements PLog {
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    public static void run(Scanner sc) {
        LOG.info("🔐 관리자 로그인 시도");

        LOG.info("ID 입력: ");
        String inputId = sc.nextLine().trim();
        LOG.info("비밀번호 입력: ");
        String inputPw = sc.nextLine().trim();

        if (!ADMIN_ID.equals(inputId) || !ADMIN_PW.equals(inputPw)) {
            LOG.error("❌ 로그인 실패: ID 또는 비밀번호 오류");
            return;
        }

        LOG.info("✅ 로그인 성공: 관리자 모드 진입");
        MovieDAO dao = new MovieDAO();
        boolean running = true;

        while (running) {
            LOG.info("\n📋 관리자 메뉴: 1. 전체 보기  2. 수정  3. 삭제  4. 종료");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    List<MovieDTO> list = dao.getAll();
                    if (list.isEmpty()) {
                        LOG.info("⚠️ 등록된 영화가 없습니다.");
                    } else {
                        for (MovieDTO m : list) {
                            LOG.info("🎬 " + m);
                        }
                    }
                    break;
                case "2":
                    LOG.info("수정할 영화 제목 입력:");
                    String title = sc.nextLine();
                    MovieDTO movie = dao.get(new MovieDTO(title));
                    if (movie == null) {
                        LOG.warn("❌ 영화가 존재하지 않습니다.");
                        break;
                    }
                    LOG.info("새 제목: "); movie.setTitle(sc.nextLine());
                    LOG.info("새 연도: "); movie.setYear(Integer.parseInt(sc.nextLine()));
                    LOG.info("새 나라: "); movie.setCountry(sc.nextLine());
                    LOG.info("새 유형: "); movie.setRuntime(sc.nextLine());
                    LOG.info("새 장르: "); movie.setGenre(sc.nextLine());
                    LOG.info("새 감독: "); movie.setDirector(sc.nextLine());
                    dao.update(movie);
                    LOG.info("✅ 수정 완료");
                    break;
                case "3":
                    LOG.info("삭제할 영화 제목 입력:");
                    String deleteTitle = sc.nextLine();
                    if (dao.delete(new MovieDTO(deleteTitle)) == 1) {
                        LOG.info("🗑️ 삭제 완료");
                    } else {
                        LOG.warn("❌ 삭제 실패: 해당 영화 없음");
                    }
                    break;
                case "4":
                    running = false;
                    LOG.info("👋 관리자 모드 종료");
                    break;
                default:
                    LOG.warn("❗ 잘못된 메뉴 선택입니다.");
            }
        }
    }
}