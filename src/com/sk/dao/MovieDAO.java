// MovieDAO.java
package com.sk.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements WorkDiv<MovieDTO> {
    private static final String FILE_PATH = "data/MovieList.csv";

    @Override
    public int add(MovieDTO movie) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.newLine();
            bw.write(String.join(",",
                movie.getTitle(),
                String.valueOf(movie.getYear()),
                movie.getCountry(),
                movie.getRuntime(),
                movie.getGenre(),
                movie.getDirector()
            ));
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 
     * 새로운 update 메서드: 
     * @param originalTitle 수정 전(검색용) 제목
     * @param movie         수정된 MovieDTO
     */
    public int update(String originalTitle, MovieDTO movie) {
        List<MovieDTO> list = getAll();
        boolean updated = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equalsIgnoreCase(originalTitle)) {
                list.set(i, movie);
                updated = true;
                break;
            }
        }

        if (updated) {
            writeAll(list);
            return 1;
        } else {
            return 0;
        }
    }

    /** 
     * 기존 인터페이스 구현부: 
     * 제목을 바꾸지 않을 때만 정상 동작합니다.
     */
    @Override
    public int update(MovieDTO movie) {
        return update(movie.getTitle(), movie);
    }

    @Override
    public int delete(MovieDTO movie) {
        List<MovieDTO> list = getAll();
        boolean removed = list.removeIf(m -> m.getTitle().equalsIgnoreCase(movie.getTitle()));
        if (removed) {
            writeAll(list);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public MovieDTO get(MovieDTO movie) {
        for (MovieDTO m : getAll()) {
            if (m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<MovieDTO> getAll() {
        List<MovieDTO> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isFirst = true;
            while ((line = br.readLine()) != null) {
                if (isFirst) { isFirst = false; continue; }
                String[] row = line.split(",");
                if (row.length < 6) continue;
                MovieDTO dto = new MovieDTO(
                    row[0],
                    Integer.parseInt(row[1].trim()),
                    row[2],
                    row[3],
                    row[4],
                    row[5]
                );
                list.add(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void writeAll(List<MovieDTO> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("title,year,country,runtime,genre,director");
            for (MovieDTO m : data) {
                bw.newLine();
                bw.write(String.join(",",
                    m.getTitle(),
                    String.valueOf(m.getYear()),
                    m.getCountry(),
                    m.getRuntime(),
                    m.getGenre(),
                    m.getDirector()
                ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("❗ 파일이 열려 있어 쓸 수 없습니다. 닫아주세요.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
