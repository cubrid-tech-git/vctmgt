package com.cubrid.dao;

import java.util.List;

import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public interface UserDao {
	
	/**
	 * cubrid 멤버를 조회하는 메소드(전체 조회는 eno:=0)
	 * @param int
	 * @return List
	 */
	public List<UserVo> selectCubUser(int eno);
	
	/**
	 * 신규 사원을 등록하는 메소드
	 * @param UserVo
	 * @return
	 */
	public int regCubUser(UserVo vo);
	
	/**
	 * 관리자 로그인 확인 메소드
	 * @param UserVo
	 * @return String
	 */
	public UserVo selectLoginCheck(String ename);
	
	/**
	 * 사번으로 관리자 정보 가져오는 메소드
	 * @param int
	 * @return UserVo
	 */
	public UserVo selectAdmin(int eno);
	
	/**
	 * 관리자 비밀번호 변경 메소드
	 * @param UserVo
	 * @return int
	 */
	public int updateAdminPw(UserVo vo);
	
	/**
	 * 중복되지 않는 모든 eno 값을 조회하는 메소드 
	 * @return List<UserVo>
	 */
	public List<UserVo> selectAllEno();
	
	/**
	 * 사용자를 삭제하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public int deleteUser(UserVo vo);
	
	/**
	 * 메일 발송시 보내는 사람의 eno를 통해 mail 정보들을 조회함
	 * 비밀번호, 받는사람, cc 정보
	 * @param int
	 * @return MailVo
	 */
	public MailVo selectMailInfo(int eno);
	
	/**
	 * 대체휴가 등록 승인시 필요한 정보들을 rvct_confirm 테이블에서 조회하는 메소드
	 * @param int
	 * @return RVConfirmVo
	 */
	public RVConfirmVo selectRVConfirmYNMail(int eno);
	
	/**
	 * 휴가 승인 및 미승인시 vct_confirm 테이블에서 조회하는 메소드
	 * @param int
	 * @return VConfirmVo
	 */
	public VConfirmVo selectVctConfirmYNMail(int id);
	
	/**
	 * email의 비밀번호를 변경하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public int updateEmailPassword(UserVo vo);
	
	/**
	 * 관리자가 사용자 정보 수정시 UPDATE 수행하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public int cubUserUpdate(UserVo vo);
	
	/**
	 * 관리자가 admin의 권한을 변경할 때 사용하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public int cubAdminChange(UserVo vo);
}