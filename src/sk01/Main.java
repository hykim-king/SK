package sk01;

import java.util.Scanner;
import com.sk.dao.PLog;

public class Main implements PLog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        LOG.info("🎬 영화 프로그램 시작");

        while (running) {
            LOG.info("📋 모드를 선택하세요:");
            LOG.info("1. 관리자 모드");
            LOG.info("2. 사용자 모드");
            LOG.info("3. 종료");
            LOG.info("입력: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    LOG.info("🔐 관리자 모드로 이동 중...");
                    Admin.run(sc);
                    break;
                case "2":
                    LOG.info("👤 사용자 모드로 이동 중...");
                    User.run(sc);
                    break;
                case "3":
                    LOG.info("👋 프로그램 종료");
                    running = false;
                    break;
                default:
                    LOG.warn("❗ 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }

        sc.close();
    }
}
