package com.cubrid.dao;

import java.util.List;

import com.cubrid.data.UserManager;
import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;

public class UserDaoImpl implements UserDao {

	@Override
	public List<UserVo> selectCubUser(int eno) {
		return UserManager.selectCubUser(eno);
	}

	@Override
	public int regCubUser(UserVo vo) {
		return UserManager.regCubUser(vo);
	}

	@Override
	public UserVo selectLoginCheck(String ename) {
		return UserManager.selectAdminCheck(ename);
	}

	@Override
	public UserVo selectAdmin(int eno) {
		return UserManager.selectAdmin(eno);
	}

	@Override
	public int updateAdminPw(UserVo vo) {
		return UserManager.updateAdminPw(vo);
	}

	@Override
	public List<UserVo> selectAllEno() {
		return UserManager.selectAllEno();
	}

	@Override
	public int deleteUser(UserVo vo) {
		return UserManager.deleteUser(vo);
	}

	@Override
	public MailVo selectMailInfo(int eno) {
		return UserManager.selectMailInfo(eno);
	}

	@Override
	public RVConfirmVo selectRVConfirmYNMail(int eno) {
		return UserManager.selectRVConfirmYNMail(eno);
	}

	@Override
	public VConfirmVo selectVctConfirmYNMail(int id) {
		return UserManager.selectVctConfirmYNMail(id);
	}

	@Override
	public int updateEmailPassword(UserVo vo) {
		return UserManager.updateEmailPassword(vo);
	}

	@Override
	public int cubAdminChange(UserVo vo) {
		return UserManager.cubAdminChange(vo);
	}

	@Override
	public int cubUserUpdate(UserVo vo) {
		return UserManager.cubUserUpdate(vo);
	}
}