package com.cubrid.data;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cubrid.util.CurrentTime;
import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;

public class UserManager {
	private static SqlSessionFactory sqlMapper;

	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/cubrid/data/CubMapConfig.xml");
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<UserVo> getCubUser(int eno) {
		SqlSession session = sqlMapper.openSession();
		List<UserVo> list = session.selectList("user.getCubUser", eno);
		session.commit();
		session.close();
		return list;
	}

	public static int regCubUser(UserVo vo) {
		int result = 0;
		SqlSession session = sqlMapper.openSession();
		result = session.insert("user.regCubUser", vo);
		if(result >= 1) {
			UserVo keyVo = session.selectOne("user.getInsertedKey");
			vo.setEno(keyVo.getEno());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("eno", keyVo.getEno());
			map.put("yearStartDate", CurrentTime.startYear());
			map.put("yearEndDate", CurrentTime.endYear());
			map.put("quaterStartDate", CurrentTime.makeStartQuater());
			map.put("quaterEndDate", CurrentTime.makeEndQuater());
			result = session.insert("user.insertInitVct", map);
			if(result < 1) session.rollback();
			else session.commit();
		}
		session.close();
		return result;
	}

	/**
	 * 사용자를 삭제하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public static int deleteUser(UserVo vo) {
		int result = 0;
		SqlSession session = sqlMapper.openSession();
		session.delete("user.deleteUser1", vo);
		session.delete("user.deleteUser2", vo);
		result = session.delete("user.deleteUser3", vo);
		if(result > 0) {
			result = session.delete("user.deleteUser4", vo);
			if(result > 0) session.commit();
			else session.rollback();
		} else session.rollback();
		session.close();
		return result;
	}

	public static List<UserVo> selectCubUser(int eno) {
		SqlSession session = sqlMapper.openSession();
		UserVo vo = new UserVo();
		vo.setEno(eno);
		List<UserVo> list = session.selectList("user.selectCubUser", vo);
		session.commit();
		session.close();
		return list;
	}

	public static UserVo selectAdmin(int eno) {
		SqlSession session = sqlMapper.openSession();
		UserVo vo = new UserVo();
		vo.setEno(eno);
		vo = session.selectOne("user.selectCubUser", vo);
		session.commit();
		session.close();
		return vo;
	}

	public static UserVo selectAdminCheck(String ename) {
		SqlSession session = sqlMapper.openSession();
		UserVo vo = session.selectOne("user.selectAdminCheck", ename);
		session.commit();
		session.close();
		return vo;
	}

	public static int updateAdminPw(UserVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("user.updateAdminPw", vo);
		session.commit();
		session.close();
		return result;
	}

	public static List<UserVo> selectAllEno() {
		SqlSession session = sqlMapper.openSession();
		List<UserVo> list = session.selectList("user.selectAllEno");
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * 메일 발송시 보내는 사람의 eno를 통해 mail 정보들을 조회함
	 * 비밀번호, 받는사람, cc 정보
	 * @param int
	 * @return MailVo
	 */
	public static MailVo selectMailInfo(int eno) {
		SqlSession session = sqlMapper.openSession();
		MailVo mailVo = session.selectOne("user.selectMailInfo", eno);
		session.commit();
		session.close();
		return mailVo;
	}
	
	/**
	 * 대체휴가 등록 승인시 필요한 정보들을 rvct_confirm 테이블에서 조회하는 메소드
	 * @param int
	 * @return RVConfirmVo
	 */
	public static RVConfirmVo selectRVConfirmYNMail(int eno) {
		SqlSession session = sqlMapper.openSession();
		RVConfirmVo rvConfirmVo = session.selectOne("user.selectRVConfirmYNMail", eno);
		session.commit();
		session.close();
		return rvConfirmVo;
	}
	
	/**
	 * 휴가 승인 및 미승인시 vct_confirm 테이블에서 조회하는 메소드
	 * @param int
	 * @return VConfirmVo
	 */
	public static VConfirmVo selectVctConfirmYNMail(int id) {
		SqlSession session = sqlMapper.openSession();
		VConfirmVo vConfirmVo = session.selectOne("user.selectVctConfirmYNMail", id);
		session.commit();
		session.close();
		return vConfirmVo;
	}
	
	/**
	 * email의 비밀번호를 변경하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public static int updateEmailPassword(UserVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("user.updateEmailPassword", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * 관리자가 사용자 정보 수정시 UPDATE 수행하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public static int cubUserUpdate(UserVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("user.cubUserUpdate", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * 관리자가 admin의 권한을 변경할 때 사용하는 메소드
	 * @param UserVo
	 * @return int
	 */
	public static int cubAdminChange(UserVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("user.cubAdminChange", vo);
		session.commit();
		session.close();
		return result;
	}
}