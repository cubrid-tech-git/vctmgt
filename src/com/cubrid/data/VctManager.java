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
		 * map 객체 정보
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
	 * 대체휴가 초기화 스케쥴링 첫번째 메소드 (4번째까지 있음)
	 * vct 테이블에 INSERT 수행
	 * 필요 파라미터 : eno, work_date, work_end_date
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
	 * 전 분기 남은 대체휴가의 소수점 자리 값을 가져오는 메소드
	 * 필요 파라미터 : eno, regdate(전 분기의 대체휴가 시작일)
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
	 * 미처리된 vct_confirm 데이터들을 조회하는 메소드
	 * 필요한 파라미터는 분기 끝나는 날짜
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
	 * 분기 10일에 남은 work_time을 이월하는 메소드
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
	 * schedulingRvct2에서 조회된 항목을 UPDATE 하는 메소드
	 * 필요 파라미터 : vct_confirm.id
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
	 * 분기 지난 rvct_confirm 테이블의 데이터들을 N 으로 바꾸는 메소드
	 * 파라미터는 분기 시작일, 다음 분기 시작일이 필요함
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
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드1
	 * vct 테이블에서 연차휴가 잔량을 조회
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
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드2
	 * 연 초에 새로운 대체휴가 정보들을 insert 수행
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
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드3
	 * vct_confirm 테이블에서 연차휴가 신청 정보들을 취소함 [ status = 'N' 으로 변경 ]
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
	 * 남은 휴가 시간을 조회하는 메소드
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
	 * 휴가 신청시 insert 수행하는 메소드
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
	 * 관리자가 휴가 신청 현황을 조회하는 쿼리
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
	 * vctcount값을 조회하기 위해 vct 테이블을 조회하는 메소드
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
	 * vct.vctcount, vct_confirm.status='Y' UPDATE 메소드
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
	 * vct_confirm.status = 'Y' 만 수행하는 메소드 (경조(특별)휴가일 경우 사용하는 메소드)
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
	 * 휴가 사용 승인시 사용한 정보를 history에 남기는 메소드
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
	 * 정보 페이지에서 휴가 사용 현황에 대한 내용을 출력하는 메소드 
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
	 * 정보 페이지에서 대체휴가 정산 정보를 분기별로 보여주는 메소드
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
	 * 정보 페이지에서 대체휴가 상세보기의 통계정보를 보여주는 메소드
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
	 * 정보 페이지에서 대체휴가 상세보기의 세부 리스트를 보여주는 메소드
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
	 * 휴가 신청, 승인, 거절 등 리스트를 보여줄 메소드
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
	 * 휴가 신청한 내용을 수정하거나 삭제할 경우, 휴가 정보를 조회하는 메소드
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
	 * 휴가 신청한 내용을 삭제하는 메소드
	 * @param int
	 * @return int
	 */
	public static int vctDel(int id) {
		int result = 0;
		SqlSession session = sqlMapper.openSession();
		VConfirmVo vVo = session.selectOne("vct.vctDel1", id);
		System.out.println(vVo.toString());
		
		if(vVo.getStatus().equals("Y")) {
			// Y 일 경우 승인이 된 놈이기 때문에 vct.vctcount + vct_confirm.vct_time을 vct.vctcount에 넣는다
			vVo.setRemain_vct_time(vVo.getRemain_vct_time() + vVo.getVct_time());
			result = session.update("vct.vctDel2", vVo);
			// update 성공 시 vct_confirm delete 수행
			if(result > 0) {
				result = session.delete("vct.vctDel3", id);
				if(result > 0) session.commit();
				else session.rollback();
			} 
		} else {
			// vct_confirm delete 수행
			result = session.delete("vct.vctDel3", id);
			if(result > 0) session.commit();
			else session.rollback();
		}
		
		session.close();
		
		return result;
	}
	
	/**
	 * 휴가 신청한 내용을 수정하는 메소드
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
	 * 대체휴가 신청시 트래킹 서비스 정보를 가져오는 메소드
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
	 * 대체휴가 신청시 선택된 ID에 해당하는 rvct_confirm 테이블의 데이터들을 조회
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
	 * 관리자 페이지에서 휴가 수정을 위해 휴가 리스트를 조회하는 쿼리
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
	 * 관리자 페이지에서 수정을 눌렀을 경우, 휴가 신청 내용들을 수정하도록 팝업창이 출력될 때 해당 vct_id를 통해 SELECT 를 수행 
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
	 * 관리자 페이지에서 수정된 휴가 수치를 vct와 vct_confirm 테이블에 update 수행하는 메소드
	 * @param Map<String, VctVo>
	 * @return int
	 */
	public static int adminPageUpdateVct(Map<String, VctVo> map, int type) {
		VctVo vctVo = map.get("Vct");
		
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		
		if(type == 0) {	// vct만 수정
			result = session.update("adminPageUpdateVct", vctVo);
			if(result > 0) session.commit();
			else session.rollback();
			
		} else if(type == 1) {	// vct_confirm, vct 모두 수정
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