package com.pcwk.ehr.book.dao;

import java.io.*;
import java.util.*;

import com.pcwk.ehr.admin.vo.MovieVO;
import com.pcwk.ehr.cmn.Workdiv;

public class AdminDao implements Workdiv<MovieVO> {

    private static final String MOVIE_CSV = ".\\data\\MovieList.csv";
    private List<MovieVO> movieList = new ArrayList<>();

    public AdminDao() {
        loadMoviesFromCSV();
    }

    private void loadMoviesFromCSV() {
        movieList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(MOVIE_CSV))) {
            String line;
            br.readLine(); // 헤더 건너뛰기
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    MovieVO movie = new MovieVO(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5]
                    );
                    movieList.add(movie);
                }
            }
        } catch (IOException e) {
            System.out.println("❗ CSV 로딩 오류: " + e.getMessage());
        }
    }

    private void saveMoviesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIE_CSV))) {
            bw.write("title,year,country,runtime,genre,director\n");
            for (MovieVO m : movieList) {
                bw.write(String.join(",",
                        m.getTitle(),
                        String.valueOf(m.getYear()),
                        m.getCountry(),
                        m.getRuntime(),
                        m.getGenre(),
                        m.getDirector()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❗ CSV 저장 오류: " + e.getMessage());
        }
    }

    @Override
    public int doSave(MovieVO dto) {
        movieList.add(dto);
        saveMoviesToCSV();
        return 1;
    }

    @Override
    public MovieVO doSelectOne(MovieVO dto) {
        for (MovieVO movie : movieList) {
            if (movie.getTitle().equalsIgnoreCase(dto.getTitle())) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public List<MovieVO> doRetrieve(MovieVO dto) {
        return new ArrayList<>(movieList);
    }

    @Override
    public int doUpdate(MovieVO dto) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().equalsIgnoreCase(dto.getTitle())) {
                movieList.set(i, dto);
                saveMoviesToCSV();
                return 1;
            }
        }  
        return 0;
    }

    @Override
    public int doDelete(MovieVO dto) {
        Iterator<MovieVO> it = movieList.iterator();
        while (it.hasNext()) {
            if (it.next().getTitle().equalsIgnoreCase(dto.getTitle())) {
                it.remove();
                saveMoviesToCSV();
                return 1;
            }
        }
        return 0;
    }
} 
