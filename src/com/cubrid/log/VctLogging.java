package com.cubrid.log;

import java.util.List;

import com.cubrid.vo.LogVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public interface VctLogging {

	/**
	 * ��ü�ް� ��� logging
	 */
	public void rvctRegLogging(RVConfirmVo vo);
	
	/**
	 * ��ü�ް� ��ϵ� ���� ���� logging
	 */
	public void rvctRegModifyLogging(RVConfirmVo vo);
	
	/**
	 * ��ü�ް� ���� / ���� logging
	 */
	public void rvctRegYNLogging(RVConfirmVo vo);
	
	/**
	 * ��ü�ް� ��� ��û logging
	 */
	public void rvctUseLogging(VConfirmVo vo);
	
	/**
	 * ��ü�ް� ��� ���� / ���� logging
	 */
	public void rvctUseYNLogging(VConfirmVo vo);
	
	/**
	 * ��ü�ް� ���� logging 
	 */
	public void rvctRenewalLogging(RVConfirmVo vo);
	
	/**
	 * �����ް� ���� logging
	 */
	public void vctRenewalLogging(VctVo vo);
	
	/**
	 * �����ް� ��� ��û logging
	 */
	public void vctUseLogging(VConfirmVo vo);
	
	/**
	 * �����ް� ��� ���� / ���� logging
	 */
	public void vctUseYNLogging(VConfirmVo vo);
	
	/**
	 * ����� ��� logging
	 */
	public void userAddLogging(UserVo vo);
	
	/**
	 * ����� ���� logging 
	 */
	public void userDelLogging(UserVo vo);
	
	/**
	 * �α� ������ ����ϴ� �޼ҵ�
	 * @return LogVo
	 */
	public List<LogVo> printLog(LogVo vo);
	
	/**
	 * ������ �α��� �̷� logging
	 */
	public void adminLoginLogging(UserVo vo);
	
	/**
	 * ������ ��й�ȣ ���� logging
	 */
	public void adminChangePWLogging(UserVo vo);
}
