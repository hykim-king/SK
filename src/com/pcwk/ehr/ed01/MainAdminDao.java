package com.pcwk.ehr.ed01;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.admin.vo.MovieVO;
import com.pcwk.ehr.book.dao.AdminDao;

public class MainAdminDao {

    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    @SuppressWarnings("resource")
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("🔐 관리자 ID: ");
        String inputId = sc.nextLine();
        System.out.print("🔐 비밀번호: ");
        String inputPw = sc.nextLine();

        if (!ADMIN_ID.equals(inputId) || !ADMIN_PW.equals(inputPw)) {
            System.out.println("❌ 관리자 인증 실패");
            return;
        }

        AdminDao adminDao = new AdminDao();
        boolean running = true;

        while (running) {
            System.out.println("\n📋 관리자 메뉴");
            System.out.println("1. 전체 영화 목록 보기");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 종료");
            System.out.print("선택: ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    List<MovieVO> movieList = adminDao.doRetrieve(null);
                    for (MovieVO m : movieList) {
                        System.out.println(m);
                    }
                    break;
                case "2":
                    System.out.print("수정할 영화 제목: ");
                    String targetTitle = sc.nextLine();
                    MovieVO found = adminDao.doSelectOne(new MovieVO(targetTitle, 0, "", "", "", ""));
                    if (found == null) {
                        System.out.println("❌ 해당 영화 없음");
                        break;
                    }
                    System.out.print("새 제목: ");
                    found.setTitle(sc.nextLine());
                    System.out.print("새 연도: ");
                    found.setYear(Integer.parseInt(sc.nextLine()));
                    System.out.print("새 나라: ");
                    found.setCountry(sc.nextLine());
                    System.out.print("새 유형(장편/단편): ");
                    found.setRuntime(sc.nextLine());
                    System.out.print("새 장르: ");
                    found.setGenre(sc.nextLine());
                    System.out.print("새 감독: ");
                    found.setDirector(sc.nextLine());
                    adminDao.doUpdate(found);
                    System.out.println("✅ 수정 완료");
                    break;
                case "3":
                    System.out.print("삭제할 영화 제목: ");
                    String deleteTitle = sc.nextLine();
                    int result = adminDao.doDelete(new MovieVO(deleteTitle, 0, "", "", "", ""));
                    if (result > 0) {
                        System.out.println("🗑️ 삭제 완료");
                    } else {
                        System.out.println("❌ 삭제 실패: 영화 없음");
                    }
                    break;
                case "4":
                    running = false;
                    System.out.println("👋 종료합니다");
                    break;
                default:
                    System.out.println("❗ 올바른 메뉴를 선택하세요");
            }
        }

        sc.close();
    }
}
