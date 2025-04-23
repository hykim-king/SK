package com.pcwk.ehr.cmn;

import java.util.List;

public interface Workdiv<T> {
    int doSave(T dto);                // 저장
    int doUpdate(T dto);              // 수정
    int doDelete(String title);       // 삭제 (제목으로)
    T doSelectOne(String title);      // 단일 조회 (제목으로)
    List<T> doRetrieve();             // 전체 조회
}
