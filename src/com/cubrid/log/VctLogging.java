package com.cubrid.log;

import java.util.List;

import com.cubrid.vo.LogVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public interface VctLogging {

	/**
	 * 대체휴가 등록 logging
	 */
	public void rvctRegLogging(RVConfirmVo vo);
	
	/**
	 * 대체휴가 등록된 내용 수정 logging
	 */
	public void rvctRegModifyLogging(RVConfirmVo vo);
	
	/**
	 * 대체휴가 승인 / 거절 logging
	 */
	public void rvctRegYNLogging(RVConfirmVo vo);
	
	/**
	 * 대체휴가 사용 신청 logging
	 */
	public void rvctUseLogging(VConfirmVo vo);
	
	/**
	 * 대체휴가 사용 승인 / 거절 logging
	 */
	public void rvctUseYNLogging(VConfirmVo vo);
	
	/**
	 * 대체휴가 갱신 logging 
	 */
	public void rvctRenewalLogging(RVConfirmVo vo);
	
	/**
	 * 연차휴가 갱신 logging
	 */
	public void vctRenewalLogging(VctVo vo);
	
	/**
	 * 연차휴가 사용 신청 logging
	 */
	public void vctUseLogging(VConfirmVo vo);
	
	/**
	 * 연차휴가 사용 승인 / 거절 logging
	 */
	public void vctUseYNLogging(VConfirmVo vo);
	
	/**
	 * 사용자 등록 logging
	 */
	public void userAddLogging(UserVo vo);
	
	/**
	 * 사용자 삭제 logging 
	 */
	public void userDelLogging(UserVo vo);
	
	/**
	 * 로그 내용을 출력하는 메소드
	 * @return LogVo
	 */
	public List<LogVo> printLog(LogVo vo);
	
	/**
	 * 관리자 로그인 이력 logging
	 */
	public void adminLoginLogging(UserVo vo);
	
	/**
	 * 관리자 비밀번호 변경 logging
	 */
	public void adminChangePWLogging(UserVo vo);
}