package com.cubrid.dao;

import java.util.List;

import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public interface UserDao {
	
	/**
	 * cubrid ����� ��ȸ�ϴ� �޼ҵ�(��ü ��ȸ�� eno:=0)
	 * @param int
	 * @return List
	 */
	public List<UserVo> selectCubUser(int eno);
	
	/**
	 * �ű� ����� ����ϴ� �޼ҵ�
	 * @param UserVo
	 * @return
	 */
	public int regCubUser(UserVo vo);
	
	/**
	 * ������ �α��� Ȯ�� �޼ҵ�
	 * @param UserVo
	 * @return String
	 */
	public UserVo selectLoginCheck(String ename);
	
	/**
	 * ������� ������ ���� �������� �޼ҵ�
	 * @param int
	 * @return UserVo
	 */
	public UserVo selectAdmin(int eno);
	
	/**
	 * ������ ��й�ȣ ���� �޼ҵ�
	 * @param UserVo
	 * @return int
	 */
	public int updateAdminPw(UserVo vo);
	
	/**
	 * �ߺ����� �ʴ� ��� eno ���� ��ȸ�ϴ� �޼ҵ� 
	 * @return List<UserVo>
	 */
	public List<UserVo> selectAllEno();
	
	/**
	 * ����ڸ� �����ϴ� �޼ҵ�
	 * @param UserVo
	 * @return int
	 */
	public int deleteUser(UserVo vo);
	
	/**
	 * ���� �߼۽� ������ ����� eno�� ���� mail �������� ��ȸ��
	 * ��й�ȣ, �޴»��, cc ����
	 * @param int
	 * @return MailVo
	 */
	public MailVo selectMailInfo(int eno);
	
	/**
	 * ��ü�ް� ��� ���ν� �ʿ��� �������� rvct_confirm ���̺��� ��ȸ�ϴ� �޼ҵ�
	 * @param int
	 * @return RVConfirmVo
	 */
	public RVConfirmVo selectRVConfirmYNMail(int eno);
	
	/**
	 * �ް� ���� �� �̽��ν� vct_confirm ���̺��� ��ȸ�ϴ� �޼ҵ�
	 * @param int
	 * @return VConfirmVo
	 */
	public VConfirmVo selectVctConfirmYNMail(int id);
	
	/**
	 * email�� ��й�ȣ�� �����ϴ� �޼ҵ�
	 * @param UserVo
	 * @return int
	 */
	public int updateEmailPassword(UserVo vo);
	
	/**
	 * �����ڰ� ����� ���� ������ UPDATE �����ϴ� �޼ҵ�
	 * @param UserVo
	 * @return int
	 */
	public int cubUserUpdate(UserVo vo);
	
	/**
	 * �����ڰ� admin�� ������ ������ �� ����ϴ� �޼ҵ�
	 * @param UserVo
	 * @return int
	 */
	public int cubAdminChange(UserVo vo);
}
