package com.cubrid.data;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cubrid.util.CurrentTime;
import com.cubrid.vo.CostStaticsVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public class VctManager {
	private static SqlSessionFactory sqlMapper;
	
	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/cubrid/data/VctMapConfig.xml");
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<VctVo> selectAllVct(String rEnddate, String aRegdate, int eno) {
		SqlSession session = sqlMapper.openSession();
		HashMap<String, Object> map = new HashMap<>();
		map.put("rEnddate", rEnddate);
		map.put("aRegdate", aRegdate);
		map.put("eno", eno);
		List<VctVo> list = session.selectList("vct.selectAllVct", map);
		session.commit();
		session.close();
		
		return list;
	}
	
	public static int insertRvctConfirm(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.insert("vct.insertRvctConfirm", vo);
		session.commit();
		session.close();
		
		return result;
	}
	
	public static List<RVConfirmVo> selectRvctConfirm(Map<String, Object> map) {
		SqlSession session = sqlMapper.openSession();
		List<RVConfirmVo> list = session.selectList("vct.selectRvctConfirm", map);
		/*
		 * map ��ü ����
		 * key : confirmType - value : manager or employee
		 * key : confirmEno - value : eno
		 */
		session.commit();
		session.close();
		
		return list;
	}
	
	public static int rvctConfirmDel(int id) {
		SqlSession session = sqlMapper.openSession();
		int result = session.delete("vct.rvctConfirmDel", id);
		session.commit();
		session.close();
		return result;
	}
	
	public static int rvctConfirmN(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.rvctConfirmN", vo);
		session.commit();
		session.close();
		return result;
	}
	
	public static int rvctConfirmY1(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.rvctConfirmY1", vo);
		session.commit();
		session.close();
		return result;
	}
	
	public static VctVo rvctConfirmY2(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		VctVo vctVo = session.selectOne("vct.rvctConfirmY2", vo);
		session.commit();
		session.close();
		return vctVo;
	}
	
	public static int rvctConfirmY3(VctVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.rvctConfirmY3", vo);
		session.commit();
		session.close();
		return result;
	}
	
	public static int updateRvctConfirm(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.updateRvctConfirm", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * ��ü�ް� �ʱ�ȭ �����층 ù��° �޼ҵ� (4��°���� ����)
	 * vct ���̺� INSERT ����
	 * �ʿ� �Ķ���� : eno, work_date, work_end_date
	 * @param RVConfirmVo
	 * @return int
	 */
	public static int schedulingRvct1(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.insert("vct.schedulingRvct1", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �� �б� ���� ��ü�ް��� �Ҽ��� �ڸ� ���� �������� �޼ҵ�
	 * �ʿ� �Ķ���� : eno, regdate(�� �б��� ��ü�ް� ������)
	 * @param VctVo
	 * @return RVConfirmVo
	 */
	public static List<RVConfirmVo> schedulingRvct1_1(VctVo vo) {
		SqlSession session = sqlMapper.openSession();
		List<RVConfirmVo> list = session.selectList("vct.schedulingRvct1_1", vo);
		System.out.println("manager " + list);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ��ó���� vct_confirm �����͵��� ��ȸ�ϴ� �޼ҵ�
	 * �ʿ��� �Ķ���ʹ� �б� ������ ��¥
	 * ex) work_end_date : 2015-04-10
	 * @param String
	 * @return VConfirmVo
	 */
	public static List<VConfirmVo> schedulingRvct2(String work_end_date) {
		SqlSession session = sqlMapper.openSession();
		List<VConfirmVo> list = session.selectList("vct.schedulingRvct2", work_end_date);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * �б� 10�Ͽ� ���� work_time�� �̿��ϴ� �޼ҵ�
	 * @param RVConfirmVo
	 * @return int
	 */
	public static int schedulingRvct2_1(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.schedulingRvct2_1", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * schedulingRvct2���� ��ȸ�� �׸��� UPDATE �ϴ� �޼ҵ�
	 * �ʿ� �Ķ���� : vct_confirm.id
	 * @param RVConfirmVo
	 * @return int
	 */
	public static int schedulingRvct3(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.schedulingRvct3", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �б� ���� rvct_confirm ���̺��� �����͵��� N ���� �ٲٴ� �޼ҵ�
	 * �Ķ���ʹ� �б� ������, ���� �б� �������� �ʿ���
	 * ex) regdate : 2015-01-01, enddate : 2015-04-01
	 * @param HashMap<String, String>
	 * @return int
	 */
	public static int scheduleingRvct4(String endDate) {
		SqlSession session = sqlMapper.openSession();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("endDate", endDate);
		int result = session.update("vct.schedulingRvct4", map);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �����층�� ���� �����ް� �ڵ� ���� �޼ҵ�1
	 * vct ���̺��� �����ް� �ܷ��� ��ȸ
	 * @param String
	 * @return List<VctVo>
	 */
	public static List<VctVo> schedulingAvct1(String endDate) {
		SqlSession session = sqlMapper.openSession();
		List<VctVo> list = session.selectList("vct.schedulingAvct1", endDate);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * �����층�� ���� �����ް� �ڵ� ���� �޼ҵ�2
	 * �� �ʿ� ���ο� ��ü�ް� �������� insert ����
	 * @param VctVo
	 * @return int
	 */
	public static int schedulingAvct2(VctVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.insert("vct.schedulingAvct2", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �����층�� ���� �����ް� �ڵ� ���� �޼ҵ�3
	 * vct_confirm ���̺��� �����ް� ��û �������� ����� [ status = 'N' ���� ���� ]
	 * @return int
	 */
	public static int schedulingAvct3() {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.schedulingAvct3");
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * ���� �ް� �ð��� ��ȸ�ϴ� �޼ҵ�
	 * @param VctVo
	 * @return VctVo
	 */
	public static VctVo selectRemainVct(VctVo vo) {
		SqlSession session = sqlMapper.openSession();
		VctVo vctVo = session.selectOne("vct.selectRemainVct", vo);
		session.commit();
		session.close();
		return vctVo;
	}
	
	/**
	 * �ް� ��û�� insert �����ϴ� �޼ҵ�
	 * @param RVConfirmVo
	 * @return int
	 */
	public static int insertVctConfirm(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.insert("vct.insertVctConfirm", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �����ڰ� �ް� ��û ��Ȳ�� ��ȸ�ϴ� ����
	 * @param int
	 * @return List<VConfirmVo>
	 */
	public static List<VConfirmVo> selectVctConfirmForAdmin(int adminEno) {
		SqlSession session = sqlMapper.openSession();
		List<VConfirmVo> list = session.selectList("vct.selectVctConfirmForAdmin", adminEno);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * vctcount���� ��ȸ�ϱ� ���� vct ���̺��� ��ȸ�ϴ� �޼ҵ�
	 * @param int
	 * @return VctVo
	 */
	public static VctVo selectVctConfirmVctcount(int vctId) {
		SqlSession session = sqlMapper.openSession();
		VctVo vo = session.selectOne("vct.selectVctConfirmVctcount", vctId);
		session.commit();
		session.close();
		return vo;
	}
	
	/**
	 * vct.vctcount, vct_confirm.status='Y' UPDATE �޼ҵ�
	 * @param VConfirmVo
	 * @return int
	 */
	public static int updateVctConfirmVctcount(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.updateVctConfirmVctcount", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * vct_confirm.status = 'Y' �� �����ϴ� �޼ҵ� (����(Ư��)�ް��� ��� ����ϴ� �޼ҵ�)
	 * @param VConfirmVo
	 * @return int
	 */
	public static int updateVctConfirmOnlyVct_confirm(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.updateVctConfirmOnlyVct_confirm", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * �ް� ��� ���ν� ����� ������ history�� ����� �޼ҵ�
	 * @param VConfirmVo
	 * @return int
	 */
	public static int vctUsedHistory(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.insert("vct.vctUsedHistory", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * ���� ���������� �ް� ��� ��Ȳ�� ���� ������ ����ϴ� �޼ҵ� 
	 * @param String
	 * @return List<VConfirmVo>
	 */
	public static List<VConfirmVo> selectConfirmedVctInfo(String date) {
		SqlSession session = sqlMapper.openSession();
		List<VConfirmVo> list = session.selectList("vct.selectConfirmedVctInfo", date);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ���� ���������� ��ü�ް� ���� ������ �б⺰�� �����ִ� �޼ҵ�
	 * @param String
	 * @return List<VctVo>
	 */
	public static List<VctVo> selectRvctCostInfo(String date) {
		SqlSession session = sqlMapper.openSession();
		List<VctVo> list = session.selectList("vct.selectRvctCostInfo", date);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ���� ���������� ��ü�ް� �󼼺����� ��������� �����ִ� �޼ҵ�
	 * @param int
	 * @param String
	 * @return List<CostStaticsVo>
	 */
	public static CostStaticsVo selectRvctCostDetailStatics(int eno, String quater) {
		SqlSession session = sqlMapper.openSession();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("eno", eno);
		map.put("startDate", CurrentTime.korToQuaterStart(quater));
		map.put("endDate", CurrentTime.korToQuater(quater));
		CostStaticsVo vo = session.selectOne("vct.selectRvctCostDetailStatics", map);
		session.commit();
		session.close();
		return vo;
	}
	
	/**
	 * ���� ���������� ��ü�ް� �󼼺����� ���� ����Ʈ�� �����ִ� �޼ҵ�
	 * @param int
	 * @param String
	 * @return List<RVConfirmVo>
	 */
	public static List<RVConfirmVo> selectRvctCostDetailList(int eno, String quater) {
		SqlSession session = sqlMapper.openSession();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("eno", eno);
		map.put("startDate", CurrentTime.korToQuaterStart(quater));
		map.put("endDate", CurrentTime.korToQuaterEnd01(quater));
		List<RVConfirmVo> list = session.selectList("vct.selecrRvctCostDetailList", map);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * �ް� ��û, ����, ���� �� ����Ʈ�� ������ �޼ҵ�
	 * @param int
	 * @return VConfirmVo
	 */
	public static List<VConfirmVo> selectVctConfirmList(int eno) {
		SqlSession session = sqlMapper.openSession();
		List<VConfirmVo> list = session.selectList("vct.selectVctConfirmList", eno);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * �ް� ��û�� ������ �����ϰų� ������ ���, �ް� ������ ��ȸ�ϴ� �޼ҵ�
	 * @param int
	 * @return VConfirmVo
	 */
	public static VConfirmVo selectVctConfirmListById(int id) {
		SqlSession session = sqlMapper.openSession();
		VConfirmVo vVo = session.selectOne("vct.selectVctConfirmListById", id);
		session.commit();
		session.close();
		return vVo;
	}
	
	/**
	 * �ް� ��û�� ������ �����ϴ� �޼ҵ�
	 * @param int
	 * @return int
	 */
	public static int vctDel(int id) {
		int result = 0;
		SqlSession session = sqlMapper.openSession();
		VConfirmVo vVo = session.selectOne("vct.vctDel1", id);
		System.out.println(vVo.toString());
		
		if(vVo.getStatus().equals("Y")) {
			// Y �� ��� ������ �� ���̱� ������ vct.vctcount + vct_confirm.vct_time�� vct.vctcount�� �ִ´�
			vVo.setRemain_vct_time(vVo.getRemain_vct_time() + vVo.getVct_time());
			result = session.update("vct.vctDel2", vVo);
			// update ���� �� vct_confirm delete ����
			if(result > 0) {
				result = session.delete("vct.vctDel3", id);
				if(result > 0) session.commit();
				else session.rollback();
			} 
		} else {
			// vct_confirm delete ����
			result = session.delete("vct.vctDel3", id);
			if(result > 0) session.commit();
			else session.rollback();
		}
		
		session.close();
		
		return result;
	}
	
	/**
	 * �ް� ��û�� ������ �����ϴ� �޼ҵ�
	 * @param VConfirmVo
	 * @return int
	 */
	public static int regVctUpdate(VConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		int result = session.update("vct.regVctUpdate", vo);
		session.commit();
		session.close();
		return result;
	}
	
	/**
	 * ��ü�ް� ��û�� Ʈ��ŷ ���� ������ �������� �޼ҵ�
	 * @param RVConfirmVo
	 * @return RVConfirmVo
	 */
	public static List<RVConfirmVo> selectTrackingList(RVConfirmVo vo) {
		SqlSession session = sqlMapper.openSession();
		List<RVConfirmVo> list = session.selectList("vct.selectTrackingList", vo);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ��ü�ް� ��û�� ���õ� ID�� �ش��ϴ� rvct_confirm ���̺��� �����͵��� ��ȸ
	 * @param int
	 * @return RVConfirmVo
	 */
	public static RVConfirmVo selectTrackingSiteReason(int id) {
		SqlSession session = sqlMapper.openSession();
		RVConfirmVo vo = session.selectOne("vct.selectTrackingSiteReason", id);
		session.commit();
		session.close();
		return vo;
	}
	
	/**
	 * ������ ���������� �ް� ������ ���� �ް� ����Ʈ�� ��ȸ�ϴ� ����
	 * @param Map<String, String>
	 * @return List<VctVo>
	 */
	public static List<VctVo> adminPageSelectAllVctList(Map<String, String> map) {
		SqlSession session = sqlMapper.openSession();
		List<VctVo> list = session.selectList("vct.adminPageSelectAllVctList", map);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ������ ���������� ������ ������ ���, �ް� ��û ������� �����ϵ��� �˾�â�� ��µ� �� �ش� vct_id�� ���� SELECT �� ���� 
	 * @param int
	 * @return List<VConfirmVo>
	 */
	public static List<VConfirmVo> adminPageUpdateVctConfirmList(int vct_id) {
		SqlSession session = sqlMapper.openSession();
		List<VConfirmVo> list = session.selectList("vct.adminPageUpdateVctConfirmList", vct_id);
		session.commit();
		session.close();
		return list;
	}
	
	/**
	 * ������ ���������� ������ �ް� ��ġ�� vct�� vct_confirm ���̺� update �����ϴ� �޼ҵ�
	 * @param Map<String, VctVo>
	 * @return int
	 */
	public static int adminPageUpdateVct(Map<String, VctVo> map, int type) {
		VctVo vctVo = map.get("Vct");
		
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		
		if(type == 0) {	// vct�� ����
			result = session.update("adminPageUpdateVct", vctVo);
			if(result > 0) session.commit();
			else session.rollback();
			
		} else if(type == 1) {	// vct_confirm, vct ��� ����
			VctVo vctConfirmVo = map.get("VctConfirm");
			
			result = session.update("vct.adminPageUpdateVctConfirm", vctConfirmVo);
			
			if(result > 0) {
				result = session.update("adminPageUpdateVct", vctVo);
				if(result > 0) session.commit();
				else session.rollback();
			} 
		}
		
		session.close();
		
		return result;
	}
}
