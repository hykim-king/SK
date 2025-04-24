// AdminMain.java
package sk01;

import java.util.List;
import java.util.Scanner;

import com.sk.dao.MovieDAO;
import com.sk.dao.PLog;
import com.sk.dao.MovieDTO;

public class AdminMain implements PLog {
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run(sc);
        sc.close();
    }

    public static void run(Scanner sc) {
        LOG.info("🔐 관리자 로그인 시도");
        LOG.info("🔐 관리자 ID 입력: ");
        if (!ADMIN_ID.equals(sc.nextLine().trim())) {
            LOG.error("❌ 관리자 ID가 일치하지 않습니다.");
            return;
        }

        LOG.info("🔐 비밀번호 입력: ");
        if (!ADMIN_PW.equals(sc.nextLine().trim())) {
            LOG.error("❌ 비밀번호가 일치하지 않습니다.");
            return;
        }

        LOG.info("✅ 관리자 로그인 성공");

        MovieDAO dao = new MovieDAO();
        boolean running = true;

        while (running) {
            LOG.info("\n📋 관리자 메뉴");
            LOG.info("1. 영화 전체 목록 보기");
            LOG.info("2. 영화 수정");
            LOG.info("3. 영화 삭제");
            LOG.info("4. 종료");
            LOG.info("선택: ");

            String input = sc.nextLine().trim();
            switch (input) {
                case "1":
                    List<MovieDTO> list = dao.getAll();
                    LOG.info("🎬 전체 영화 목록:");
                    if (list.isEmpty()) {
                        LOG.warn("⚠️ 등록된 영화가 없습니다.");
                    } else {
                        for (MovieDTO m : list) {
                            LOG.info(" - {}", m);
                        }
                    }
                    break;

                case "2":
                    LOG.info("수정할 영화 제목 입력: ");
                    String originalTitle = sc.nextLine().trim();

                    MovieDTO movie = dao.get(new MovieDTO(originalTitle));
                    if (movie == null) {
                        LOG.error("❌ 해당 영화가 존재하지 않습니다.");
                        break;
                    }

                    LOG.info("새 제목 [{}]: ", movie.getTitle());
                    String upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setTitle(upd);

                    LOG.info("새 연도 [{}]: ", movie.getYear());
                    upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setYear(Integer.parseInt(upd));

                    LOG.info("새 나라 [{}]: ", movie.getCountry());
                    upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setCountry(upd);

                    LOG.info("새 유형 [{}]: ", movie.getRuntime());
                    upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setRuntime(upd);

                    LOG.info("새 장르 [{}]: ", movie.getGenre());
                    upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setGenre(upd);

                    LOG.info("새 감독 [{}]: ", movie.getDirector());
                    upd = sc.nextLine().trim();
                    if (!upd.isEmpty()) movie.setDirector(upd);

                    int updatedCount = dao.update(originalTitle, movie);
                    if (updatedCount == 1) {
                        LOG.info("✅ 영화 수정 완료");
                    } else {
                        LOG.error("❌ 수정 실패 (파일 쓰기 오류)");
                    }
                    break;

                case "3":
                    LOG.info("삭제할 영화 제목 입력: ");
                    String deleteTitle = sc.nextLine().trim();
                    int del = dao.delete(new MovieDTO(deleteTitle));
                    if (del == 1) {
                        LOG.info("🗑️ 삭제 완료");
                    } else {
                        LOG.error("❌ 삭제 실패 (제목을 다시 확인하세요)");
                    }
                    break;

                case "4":
                    running = false;
                    LOG.info("👋 관리자 프로그램 종료");
                    break;

                default:
                    LOG.error("❗ 잘못된 메뉴 선택입니다.");
            }
        }
    }
}
