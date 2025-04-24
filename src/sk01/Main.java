package sk01;


import java.util.List;
import java.util.Scanner;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;
import com.sk.dao.PLog;

public class Main implements PLog {

    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("🎬 영화 프로그램 시작");

        while (running) {
        	System.out.println("-------------------------------------");
            System.out.println("\n📋 모드를 선택하세요:");
            System.out.println("1. 관리자 모드");
            System.out.println("2. 사용자 모드");
            System.out.println("3. 종료");
            System.out.print("입력: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    runAdmin(sc);
                    break;
                case "2":
                    runUser(sc);
                    break;
                case "3":
                    running = false;
                    System.out.println("👋 프로그램 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        sc.close();
        System.out.println("✅ 프로그램 정상 종료");
    }

    // 👮 관리자 모드
    public static void runAdmin(Scanner sc) {
        System.out.print("🔐 관리자 ID: ");
        if (!ADMIN_ID.equals(sc.nextLine())) {
            System.out.println("❌ 관리자 ID가 일치하지 않습니다.");
            return;
        }

        System.out.print("🔐 비밀번호: ");
        if (!ADMIN_PW.equals(sc.nextLine())) {
            System.out.println("❌ 비밀번호가 일치하지 않습니다.");
            return;
        }



        MovieDAO dao = new MovieDAO();
        boolean running = true;

        while (running) {
            System.out.println("\n📋 관리자 메뉴");
            System.out.println("1. 영화 전체 목록 보기");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 영화 검색");
            System.out.println("5. 종료");
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
                        System.out.printf("🗑️ '%s' 영화가 삭제되었습니다.\n", deleteTitle);
                    } else {
                        System.out.printf("❌ '%s' 영화 삭제에 실패했습니다. 제목을 다시 확인해주세요.\n", deleteTitle);
                    }
                    break;

                case "4":
                    System.out.print("검색할 영화 제목: ");
                    String searchTitle = sc.nextLine();
                    MovieDTO found = dao.get(new MovieDTO(searchTitle));
                    if (found == null) {
                        System.out.println("❌ 해당 영화가 존재하지 않습니다.");
                    } else {
                        System.out.println("🔍 조회 결과:");
                        System.out.println(found);
                    }
                    break;
                    
                case "5":
                    Director director = new Director();
                    director.showDirectorMenu();
                    break;

                default:
                    System.out.println("❗ 잘못된 메뉴 선택입니다.");
            }
        }
    }

    // 👤 사용자 모드
    public static void runUser(Scanner sc) {
        boolean running = true;

        while (running) {
            System.out.println("\n👤 사용자 메뉴");
            System.out.println("1. 감독별 영화 보기");
            System.out.println("2. 랜덤 영화 추천");
            System.out.println("3. 사용자 모드 종료");
            System.out.print("선택: ");

            String choice = sc.nextLine();

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
                    System.out.println("👋 사용자 모드 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다.");
            }
        }
    }
}


