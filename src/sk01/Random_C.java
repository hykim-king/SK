package sk01;

import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Random_C {
    private static final String FILE_PATH = "File/MovieList.csv";
    private static final Logger LOG = LogManager.getLogger(Random_C.class);

    public void recommendRandomMovie() {
        List<String[]> movieList = readCSV(FILE_PATH);

        if (movieList.isEmpty()) {
            LOG.warn("영화 목록이 비어 있습니다.");
            System.out.println("😢 추천할 영화가 없습니다.");
            return;
        }

        Random rand = new Random();
        String[] pick = movieList.get(rand.nextInt(movieList.size()));

        LOG.info("랜덤 추천된 영화: {}", Arrays.toString(pick));
        System.out.printf("\n🎲 랜덤 추천 영화: %s | %s | %s | %s | %s | 감독: %s\n",
                pick[0], pick[4], pick[2], pick[1], pick[3], pick[5]);
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
