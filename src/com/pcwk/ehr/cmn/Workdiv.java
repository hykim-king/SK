/**
 * Package Name : com.pcwk.ehr.member.dao <br/>
 * Class Name: Workdiv.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ──────────────────────────────────────────<br/>
 * 최초 생성일 : 2025-04-18<br/>
 *
 * ──────────────────────────────────────────<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.cmn;

import java.util.List;

/**
 * DAO 표준
 */
public interface Workdiv<T> {

	/**
	 * 회원정보를 신규로 등록
	 * 
	 * @param dto
	 * @return 0(등록실패)/1(등록성공)
	 */
	int doSave(T dto);

	/**
	 * 회원 단건 조회
	 * 
	 * @param dto
	 * @return MemberVO
	 */
	T doSelectOne(T dto);

	/**
	 * 회원목록 조회
	 * 
	 * @param dto
	 * @return List<MemberVO>
	 */
	List<T> doRetrieve(T dto);

	/**
	 * 회원 정보 수정
	 * 
	 * @param dto
	 * @return 0(실패)/1(성공)
	 */
	int doUpdate(T dto);

	/**
	 * 회원 정보 삭제
	 * 
	 * @param dto
	 * @return 0(실패)/1(성공)
	 */
	int doDelete(T dto);

}