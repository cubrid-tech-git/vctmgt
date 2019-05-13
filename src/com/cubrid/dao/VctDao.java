package com.cubrid.dao;

import java.util.List;
import java.util.Map;

import com.cubrid.vo.CostStaticsVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public interface VctDao {
	/**
	 * 메인 화면에서 전체 휴가 갯수를 보여주는 메소드
	 * @return List<VctVo>
	 * 
	 */
	public List<VctVo> selectAllVct(int eno);
	
	/**
	 * 대체휴가 신청을 등록하는 메소드
	 * @param RVConfirmVo
	 * @return int 
	 */


	public int insertRvctConfirm(RVConfirmVo vo);
	
	/**
	 * 대체휴가 신청 후 잘못되었을 경우 해당 대체휴가를 삭제하는 메소드
	 * @param int
	 * @return int
	 */
	public int rvctConfirmDel(int id);
	
	/**
	 * 대체휴가 신청을 조회하는 메소드
	 * @param Map<String, Object>
	 * @return List<RVConfirmVo>
	 */
	public List<RVConfirmVo> selectRvctConfirm(Map<String, Object> map);
	
	/**
	 * 대체휴가 승인시 rvct_confirm.status = 'Y' 로 바꾸는 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int rvctConfirmY1(RVConfirmVo vo);
	
	/**
	 * 대체휴가 승인시 vct 테이블에서 id, vctcount를 조회하는 메소드
	 * @param RVConfirmVo
	 * @return VctVo
	 */
	public VctVo rvctConfirmY2(RVConfirmVo vo);
	
	/**
	 * 대체휴가 승인시 vct 테이블에 vctcount 값을 갱신하는 메소드
	 * @param VctVo
	 * @return int
	 */
	public int rvctConfirmY3(VctVo vo);
	
	/**
	 * 대체휴가 미승인 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int rvctConfirmN(RVConfirmVo vo);
	
	/**
	 * 대체휴가 신청을 수정하는 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int updateRvctConfirm(RVConfirmVo vo); 
	
	/**
	 * 대체휴가 초기화 스케쥴링 첫번째 메소드 (4번째까지 있음)
	 * vct 테이블에 INSERT 수행
	 * 필요 파라미터 : eno, work_date, work_end_date
	 * @param RVConfirmVo
	 * @return int
	 */
	public int schedulingRvct1(RVConfirmVo vo);
	
	/**
	 * 전 분기 남은 소수점 자리 대체휴가 값을 가져오는 메소드
	 * @param VctVo
	 * @return RVConfirmVo 
	 */
	public List<RVConfirmVo> schedulingRvct1_1(VctVo vo);
	
	/**
	 * 미처리된 vct_confirm 데이터들을 조회하는 메소드
	 * 필요한 파라미터는 분기 끝나는 날짜
	 * ex) work_end_date : 2015-04-10
	 * @param String
	 * @return VConfirmVo
	 */
	public List<VConfirmVo> schedulingRvct2(String work_end_date);
	
	/**
	 * 분기 10일에 남은 work_time을 이월하는 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int schedulingRvct2_1(RVConfirmVo vo);
	
	/**
	 * schedulingRvct2에서 조회된 항목을 UPDATE 하는 메소드
	 * 필요 파라미터 : vct_confirm.id
	 * @param RVConfirmVo
	 * @return int
	 */
	public int schedulingRvct3(VConfirmVo vo);
	
	/**
	 * 분기 지난 rvct_confirm 테이블의 데이터들을 N 으로 바꾸는 메소드
	 * 파라미터는 분기 시작일, 다음 분기 시작일이 필요함
	 * ex) regdate : 2015-01-01, enddate : 2015-04-01
	 * @param HashMap<String, String>
	 * @return int
	 */
	public int schedulingRvct4(String endDate);
	
	/**
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드1
	 * vct 테이블에서 연차휴가 잔량을 조회
	 * @param String
	 * @return List<VctVo>
	 */
	public List<VctVo> schedulingAvct1(String endDate);
	
	/**
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드2
	 * 연 초에 새로운 대체휴가 정보들을 insert 수행
	 * @param VctVo
	 * @return int
	 */
	public int schedulingAvct2(VctVo vo);
	
	/**
	 * 스케쥴링에 의한 연차휴가 자동 갱신 메소드3
	 * vct_confirm 테이블에서 연차휴가 신청 정보들을 취소함 [ status = 'N' 으로 변경 ]
	 * @return int
	 */
	public int schedulingAvct3();
	
	/**
	 * 남은 휴가 시간을 조회하는 메소드
	 * @param VctVo
	 * @return VctVo
	 */
	public VctVo selectRemainVct(VctVo vo);
	
	/**
	 * 휴가 신청시 insert 수행하는 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int insertVctConfirm(VConfirmVo vo);
	
	/**
	 * 연차 + 대체 휴가 신청시 insert 수행하는 메소드
	 * @param RVConfirmVo
	 * @return int
	 */
	public int insertVctConfirmComplex(VConfirmVo vo);
	
	/**
	 * 관리자가 휴가 신청 현황을 조회하는 쿼리
	 * @param int
	 * @return List<VConfirmVo>
	 */
	public List<VConfirmVo> selectVctConfirmForAdmin(int adminEno);
	
	/**
	 * vctcount값을 조회하기 위해 vct 테이블을 조회하는 메소드
	 * @param int
	 * @return VctVo
	 */
	public VctVo selectVctConfirmVctcount(int vctId);
	
	/**
	 * vct.vctcount, vct_confirm.status='Y' UPDATE 메소드
	 * @param VConfirmVo
	 * @return int
	 */
	public int updateVctConfirmVctcount(VConfirmVo vo);
	
	/**
	 * 휴가 사용 승인시 사용한 정보를 history에 남기는 메소드
	 * @param VConfirmVo
	 * @return int
	 */
	public int vctUsedHistory(VConfirmVo vo);
	
	/**
	 * 정보 페이지에서 휴가 사용 현황에 대한 내용을 출력하는 메소드 
	 * @param String
	 * @return VConfirmVo
	 */
	public List<VConfirmVo> selectConfirmedVctInfo(String date);
	
	/**
	 * 정보 페이지에서 대체휴가 정산 정보를 분기별로 보여주는 메소드
	 * @param String
	 * @return List<VctVo>
	 */
	public List<VctVo> selectRvctCostInfo(String date);
	
	/**
	 * 정보 페이지에서 대체휴가 상세보기의 통계정보를 보여주는 메소드
	 * @param int
	 * @param String
	 * @return List<CostStaticsVo>
	 */
	public CostStaticsVo selectRvctCostDetailStatics(int eno, String quater);
	
	/**
	 * 정보 페이지에서 대체휴가 상세보기의 세부 리스트를 보여주는 메소드
	 * @param int
	 * @param String
	 * @return List<RVConfirmVo>
	 */
	public List<RVConfirmVo> selectRvctCostDetailList(int eno, String quater);
	
	/**
	 * summaryPage.jsp에서 fullCalendar에 event 보여주는 데이터 가져오는 메소드
	 * @param String
	 * @return String
	 */
	public String selectAllV();
	
	/**
	 * vct_confirm.status = 'Y' 만 수행하는 메소드 (경조(특별)휴가일 경우 사용하는 메소드)
	 * @param VConfirmVo
	 * @return int
	 */
	public int updateVctConfirmOnlyVct_confirm(VConfirmVo vo);
	
	/**
	 * 휴가 신청, 승인, 거절 등 리스트를 보여줄 메소드
	 * @param int
	 * @return VConfirmVo
	 */
	public List<VConfirmVo> selectVctConfirmList(int eno);
	
	/**
	 * 휴가 신청한 내용을 수정하거나 삭제할 경우, 휴가 정보를 조회하는 메소드
	 * @param int
	 * @return VConfirmVo
	 */
	public VConfirmVo selectVctConfirmListById(int id);
	
	/**
	 * 휴가 신청한 내용을 삭제하는 메소드
	 * @param int
	 * @return int
	 */
	public int vctDel(int id);
	
	/**
	 * 휴가 신청한 내용을 수정하는 메소드
	 * @param VConfirmVo
	 * @return int
	 */
	public int regVctUpdate(VConfirmVo vo);
	
	/**
	 * 대체휴가 신청시 트래킹 서비스 정보를 가져오는 메소드
	 * @param RVConfirmVo
	 * @return RVConfirmVo
	 */
	public List<RVConfirmVo> selectTrackingList(RVConfirmVo vo);
	
	/**
	 * 대체휴가 신청시 선택된 ID에 해당하는 rvct_confirm 테이블의 데이터들을 조회
	 * @param int
	 * @return RVConfirmVo
	 */
	public RVConfirmVo selectTrackingSiteReason(int id);
	
	/**
	 * 관리자 페이지에서 휴가 수정을 위해 휴가 리스트를 조회하는 쿼리
	 * @param Map<String, String>
	 * @return List<VctVo>
	 */
	public List<VctVo> adminPageSelectAllVctList(Map<String, String> map);
	
	/**
	 * 관리자 페이지에서 수정을 눌렀을 경우, 휴가 신청 내용들을 수정하도록 팝업창이 출력될 때 해당 vct_id를 통해 SELECT 를 수행 
	 * @param int
	 * @return List<VConfirmVo>
	 */
	public List<VConfirmVo> adminPageUpdateVctConfirmList(int vct_id);
	
	/**
	 * 관리자 페이지에서 수정된 휴가 수치를 vct와 vct_confirm 테이블에 update 수행하는 메소드
	 * @param Map<String, VctVo>
	 * @return int
	 */
	public int adminPageUpdateVct(Map<String, VctVo> map, int type);
}