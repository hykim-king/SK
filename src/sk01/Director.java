package sk01;

import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Director {
    private static final String FILE_PATH = "File/director.csv";
    private static final Logger LOG = LogManager.getLogger(Director.class);

    public void showDirectorMenu() {
        List<String[]> movies = readCSV(FILE_PATH);
        Set<String> directorSet = new LinkedHashSet<>();

        for (String[] row : movies) {
            if (row.length >= 6) {
                directorSet.add(row[5]);
            }
        }

        List<String> directorList = new ArrayList<>(directorSet);
        Scanner scanner = new Scanner(System.in);

        LOG.info("감독 리스트 출력: 총 {}명", directorList.size());
        System.out.println("\n🎬 감독을 선택하세요.");
        for (int i = 0; i < directorList.size(); i++) {
            System.out.printf("%2d. %s\n", i + 1, directorList.get(i));
        }

        System.out.print("감독 번호 입력 > ");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > directorList.size()) {
            LOG.warn("잘못된 감독 번호 입력: {}", choice);
            System.out.println("잘못된 번호입니다.");
            return;
        }

        String selectedDirector = directorList.get(choice - 1);
        LOG.info("선택된 감독: {}", selectedDirector);
        System.out.println("\n📽️ [" + selectedDirector + "] 감독의 영화 리스트:");

        for (String[] row : movies) {
            if (row.length >= 6 && row[5].equals(selectedDirector)) {
                System.out.printf(" - %s | %s | %s | %s | %s\n",
                        row[0], row[4], row[2], row[1], row[3]);
            }
        }
    }

    private List<String[]> readCSV(String path) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirst = true;

            while ((line = br.readLine()) != null) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                data.add(line.split(","));
            }
        } catch (IOException e) {
            LOG.error("CSV 파일 읽기 오류", e);
        }
        return data;
    }
}
