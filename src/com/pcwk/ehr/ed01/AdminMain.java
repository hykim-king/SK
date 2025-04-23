package com.pcwk.ehr.ed01;

import java.util.Scanner;
import java.util.List;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.ed01.AdminDao.Movie;

public class AdminMain implements PLog {
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        LOG.info("🔐 관리자 로그인 시도");

        System.out.print("🔐 관리자 ID: ");
        if (!ADMIN_ID.equals(sc.nextLine())) {
            LOG.error("❌ ID 오류");
            return;
        }

        System.out.print("🔐 비밀번호: ");
        if (!ADMIN_PW.equals(sc.nextLine())) {
            LOG.error("❌ 비밀번호 오류");
            return;
        }

        LOG.info("✅ 관리자 로그인 성공");

        AdminDao dao = new AdminDao();
        boolean running = true;

        while (running) {
            LOG.info("\n📋 관리자 메뉴 표시");
            System.out.println("1. 영화 전체 목록 보기");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 종료");
            System.out.print("선택: ");

            switch (sc.nextLine()) {
                case "1":
                    LOG.info("📃 영화 전체 목록 요청");
                    List<AdminDao.Movie> list = dao.doRetrieve();
                    for (AdminDao.Movie m : list) {
                        LOG.info("🎬 " + m);
                    }
                    break;
                case "2":
                    LOG.info("✏️ 영화 수정 요청");
                    System.out.print("수정할 영화 제목: ");
                    String title = sc.nextLine();
                    AdminDao.Movie movie = dao.doSelectOne(title);
                    if (movie == null) {
                        LOG.warn("❌ 영화 없음: " + title);
                        break;
                    }
                    System.out.print("새 제목: "); movie.setTitle(sc.nextLine());
                    System.out.print("새 연도: "); movie.setYear(Integer.parseInt(sc.nextLine()));
                    System.out.print("새 나라: "); movie.setCountry(sc.nextLine());
                    System.out.print("새 유형: "); movie.setRuntime(sc.nextLine());
                    System.out.print("새 장르: "); movie.setGenre(sc.nextLine());
                    System.out.print("새 감독: "); movie.setDirector(sc.nextLine());
                    dao.doUpdate(movie);
                    LOG.info("✅ 영화 수정 완료: " + movie.getTitle());
                    break;
                case "3":
                    LOG.info("🗑️ 영화 삭제 요청");
                    System.out.print("삭제할 영화 제목: ");
                    String deleteTitle = sc.nextLine();
                    if (dao.doDelete(deleteTitle) == 1) {
                        LOG.info("✅ 삭제 완료: " + deleteTitle);
                    } else {
                        LOG.warn("❌ 삭제 실패: " + deleteTitle);
                    }
                    break;
                case "4":
                    running = false;
                    LOG.info("👋 프로그램 종료 요청");
                    break;
                default:
                    LOG.warn("❗ 잘못된 입력");
            }
        }

        sc.close();
    }

	@Override
	public Movie doSelectOne(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doDelete(String title) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Movie> doRetrieve() {
		// TODO Auto-generated method stub
		return null;
	}
}
