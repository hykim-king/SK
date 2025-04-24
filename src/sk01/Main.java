package sk01;

import java.util.Scanner;
import com.sk.dao.PLog;

public class Main implements PLog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("🎬 영화 프로그램 시작");

        while (running) {
            System.out.println("\n📋 모드를 선택하세요:");
            System.out.println("1. 관리자 모드");
            System.out.println("2. 사용자 모드");
            System.out.println("3. 종료");
            System.out.print("입력: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // 관리자 기능은 AdminMain으로 위임
                    AdminMain.run(sc);
                    break;

                case "2":
                    // 사용자 모드
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

    // 👤 사용자 모드
    public static void runUser(Scanner sc) {
        boolean running = true;

        while (running) {
            System.out.println("\n👤 사용자 메뉴");
            System.out.println("1. MovieRecommender");
            System.out.println("2. 감독별 영화 보기");
            System.out.println("3. 랜덤 영화 추천");
            System.out.println("4. 사용자 모드 종료");
            System.out.print("선택: ");
            String choice = sc.nextLine();

            switch (choice) {
            case "1":
				MovieRecommender movierecomender = new MovieRecommender();
				movierecomender.start();
				break;
                case "2":
                    Director director = new Director();
                    director.showDirectorMenu();
                    break;
                case "3":
                    Random_C rand = new Random_C();
                    rand.recommendRandomMovie();
                    break;
                case "4":
                    running = false;
                    System.out.println("👋 사용자 모드 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다.");
            }
        }
    }
}