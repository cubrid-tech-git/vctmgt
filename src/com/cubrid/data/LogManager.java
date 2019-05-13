package com.cubrid.data;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cubrid.vo.LogVo;
import com.cubrid.vo.RVConfirmVo;

public class LogManager {
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
	
	public static int insertLog(LogVo vo) {
		int result = 0;
		SqlSession session = sqlMapper.openSession();
		result = session.insert("log.insertLog", vo);
		session.commit();
		session.close();
		return result;
	}
	
	public static List<LogVo> selectLog(LogVo vo) {
		SqlSession session = sqlMapper.openSession();
		List<LogVo> list = session.selectList("log.selectLog", vo);
		session.commit();
		session.close();
		return list;
	}
	
	public static RVConfirmVo selectRvctConfirm(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		RVConfirmVo rVctVo = session.selectOne("log.selectRvctConfirm", vo);
		session.commit();
		session.close();
		return rVctVo;
	}
	
}