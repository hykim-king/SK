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

		LOG.info("🔐 관리자 로그인 시도");

		System.out.print("🔐 관리자 ID: ");
		if (!ADMIN_ID.equals(sc.nextLine())) {
			LOG.error("❌ ID 오류");
			System.out.println("❌ 관리자 ID가 일치하지 않습니다.");

		}

		System.out.print("🔐 비밀번호: ");
		if (!ADMIN_PW.equals(sc.nextLine())) {
			LOG.error("❌ 비밀번호 오류");
			System.out.println("❌ 비밀번호가 일치하지 않습니다.");

		}

		LOG.info("✅ 관리자 로그인 성공");
		System.out.println("✅ 로그인 성공");

		MovieDAO dao = new MovieDAO(); // ✅ MovieDAO 사용
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
				List<MovieDTO> list = dao.getAll();
				System.out.println("🎬 전체 영화 목록:");
				if (list.isEmpty()) {
					System.out.println("⚠️ 등록된 영화가 없습니다.");
				}
				for (MovieDTO m : list) {
					System.out.println(" - " + m);
				}
				break;

			case "2":
				System.out.print("수정할 영화 제목: ");
				String title = sc.nextLine();
				MovieDTO movie = dao.get(new MovieDTO(title));
				if (movie == null) {
					System.out.println("❌ 해당 영화가 존재하지 않습니다.");
					break;
				}
				System.out.print("새 제목: ");
				movie.setTitle(sc.nextLine());
				System.out.print("새 연도: ");
				movie.setYear(Integer.parseInt(sc.nextLine()));
				System.out.print("새 나라: ");
				movie.setCountry(sc.nextLine());
				System.out.print("새 유형: ");
				movie.setRuntime(sc.nextLine());
				System.out.print("새 장르: ");
				movie.setGenre(sc.nextLine());
				System.out.print("새 감독: ");
				movie.setDirector(sc.nextLine());
				dao.update(movie);
				System.out.println("✅ 영화 수정 완료");
				break;

			case "3":
				System.out.print("삭제할 영화 제목: ");
				String deleteTitle = sc.nextLine();
				if (dao.delete(new MovieDTO(deleteTitle)) == 1) {
					System.out.println("🗑️ 삭제 완료");
				} else {
					System.out.println("❌ 삭제 실패");
				}
				break;

			case "4":
				running = false;
				System.out.println("👋 관리자 프로그램 종료");
				break;

			default:
				System.out.println("❗ 잘못된 메뉴 선택입니다.");
			}
		}

		sc.close();
	}
}
