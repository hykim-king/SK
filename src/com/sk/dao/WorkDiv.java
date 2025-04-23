package com.sk.dao;

import java.util.List;

public interface WorkDiv<T> {
    int add(T t);            // 등록
    int update(T t);         // 수정
    int delete(T t);         // 삭제
    T get(T t);              // 한 건 조회
    List<T> getAll();        // 전체 조회
}
