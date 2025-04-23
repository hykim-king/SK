package sk01;

import java.util.Scanner;
import java.util.List;

import com.sk.dao.PLog;
import sk01.AdminDao.Movie;

public class AdminMain implements PLog {

    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LOG.info("🔐 관리자 로그인 시도");

        System.out.print("🔐 관리자 ID: ");
        if (!ADMIN_ID.equals(sc.nextLine())) {
            LOG.error("❌ ID 오류");
            System.out.println("❌ 관리자 ID가 일치하지 않습니다.");
            return;
        }

        System.out.print("🔐 비밀번호: ");
        if (!ADMIN_PW.equals(sc.nextLine())) {
            LOG.error("❌ 비밀번호 오류");
            System.out.println("❌ 비밀번호가 일치하지 않습니다.");
            return;
        }

        LOG.info("✅ 관리자 로그인 성공");
        System.out.println("✅ 로그인 성공");

        AdminDao dao = new AdminDao();
        boolean running = true;

        while (running) {
            System.out.println("\n📋 관리자 메뉴");
            System.out.println("1. 영화 전체 목록 보기");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 종료");
            System.out.print("선택: ");

            String input = sc.nextLine();

            switch (input) {
                case "1":
                    LOG.info("📃 영화 전체 목록 요청");
                    System.out.println("🎬 전체 영화 목록:");
                    List<AdminDao.Movie> list = dao.doRetrieve();
                    if (list.isEmpty()) {
                        System.out.println("⚠️ 등록된 영화가 없습니다.");
                    }
                    for (AdminDao.Movie m : list) {
                        System.out.println(" - " + m);
                    }
                    break;

                case "2":
                    System.out.print("수정할 영화 제목: ");
                    String title = sc.nextLine();
                    AdminDao.Movie movie = dao.doSelectOne(title);
                    if (movie == null) {
                        LOG.warn("❌ 영화 없음: " + title);
                        System.out.println("❌ 해당 영화가 존재하지 않습니다.");
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
                    System.out.println("✅ 수정 완료");
                    break;

                case "3":
                    System.out.print("삭제할 영화 제목: ");
                    String deleteTitle = sc.nextLine();
                    if (dao.doDelete(deleteTitle) == 1) {
                        LOG.info("✅ 삭제 완료: " + deleteTitle);
                        System.out.println("🗑️ 삭제 완료");
                    } else {
                        LOG.warn("❌ 삭제 실패: " + deleteTitle);
                        System.out.println("❌ 해당 영화가 존재하지 않아 삭제 실패");
                    }
                    break;

                case "4":
                    running = false;
                    LOG.info("👋 프로그램 종료");
                    System.out.println("👋 관리자 프로그램을 종료합니다.");
                    break;

                default:
                    LOG.warn("❗ 잘못된 입력");
                    System.out.println("❗ 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }

        sc.close();
    }

	@Override
	public int doSave(Movie dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(Movie dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(String title) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Movie doSelectOne(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> doRetrieve() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie doSelectOne(Movie dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> doRetrieve(Movie dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doDelete(Movie dto) {
		// TODO Auto-generated method stub
		return 0;
	}
}
