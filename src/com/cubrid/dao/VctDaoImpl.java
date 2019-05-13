package com.cubrid.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubrid.data.VctManager;
import com.cubrid.util.CurrentTime;
import com.cubrid.vo.CostStaticsVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public class VctDaoImpl implements VctDao {

	@Override
	public List<VctVo> selectAllVct(int eno) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String start = CurrentTime.startYear();
		String end = null;
		
		if(month % 3 == 1 && day <= 10) { // 분기 시작일 경우
			end = CurrentTime.makeEndLastQuater();
		} else {
			end = CurrentTime.makeEndQuater();
		}
		
		return VctManager.selectAllVct(end, start, eno);
	}

	@Override
	public int insertRvctConfirm(RVConfirmVo vo) {
		return VctManager.insertRvctConfirm(vo);
	}

	@Override
	public List<RVConfirmVo> selectRvctConfirm(Map<String, Object> map) {
		return VctManager.selectRvctConfirm(map);
	}

	@Override
	public int rvctConfirmDel(int id) {
		return VctManager.rvctConfirmDel(id);
	}

	@Override
	public int rvctConfirmY1(RVConfirmVo vo) {
		return VctManager.rvctConfirmY1(vo);
	}

	@Override
	public VctVo rvctConfirmY2(RVConfirmVo vo) {
		return VctManager.rvctConfirmY2(vo);
	}

	@Override
	public int rvctConfirmY3(VctVo vo) {
		return VctManager.rvctConfirmY3(vo);
	}

	@Override
	public int rvctConfirmN(RVConfirmVo vo) {
		return VctManager.rvctConfirmN(vo);
	}

	@Override
	public int updateRvctConfirm(RVConfirmVo vo) {
		return VctManager.updateRvctConfirm(vo);
	}

	@Override
	public int schedulingRvct1(RVConfirmVo vo) {
		return VctManager.schedulingRvct1(vo);
	}
	
	@Override
	public List<RVConfirmVo> schedulingRvct1_1(VctVo vo) {
		return VctManager.schedulingRvct1_1(vo);
	}

	@Override
	public List<VConfirmVo> schedulingRvct2(String work_end_date) {
		return VctManager.schedulingRvct2(work_end_date);
	}

	@Override
	public int schedulingRvct3(VConfirmVo vo) {
		return VctManager.schedulingRvct3(vo);
	}

	@Override
	public int schedulingRvct4(String endDate) {
		return VctManager.scheduleingRvct4(endDate);
	}

	@Override
	public List<VctVo> schedulingAvct1(String endDate) {
		return VctManager.schedulingAvct1(endDate);
	}

	@Override
	public int schedulingAvct2(VctVo vo) {
		return VctManager.schedulingAvct2(vo);
	}

	@Override
	public int schedulingAvct3() {
		return VctManager.schedulingAvct3();
	}

	@Override
	public VctVo selectRemainVct(VctVo vo) {
		return VctManager.selectRemainVct(vo);
	}

	@Override
	public int insertVctConfirm(VConfirmVo vo) {
		return VctManager.insertVctConfirm(vo);
	}

	@Override
	public List<VConfirmVo> selectVctConfirmForAdmin(int adminEno) {
		return VctManager.selectVctConfirmForAdmin(adminEno);
	}

	@Override
	public VctVo selectVctConfirmVctcount(int vctId) {
		return VctManager.selectVctConfirmVctcount(vctId);
	}

	@Override
	public int updateVctConfirmVctcount(VConfirmVo vo) {
		return VctManager.updateVctConfirmVctcount(vo);
	}

	@Override
	public int vctUsedHistory(VConfirmVo vo) {
		return VctManager.vctUsedHistory(vo);
	}

	@Override
	public List<VConfirmVo> selectConfirmedVctInfo(String date) {
		return VctManager.selectConfirmedVctInfo(date);
	}

	@Override
	public List<VctVo> selectRvctCostInfo(String date) {
		return VctManager.selectRvctCostInfo(date);
	}

	@Override
	public CostStaticsVo selectRvctCostDetailStatics(int eno, String quater) {
		return VctManager.selectRvctCostDetailStatics(eno, quater);
	}

	@Override
	public List<RVConfirmVo> selectRvctCostDetailList(int eno, String quater) {
		return VctManager.selectRvctCostDetailList(eno, quater);
	}

	@Override
	public int schedulingRvct2_1(RVConfirmVo vo) {
		return VctManager.schedulingRvct2_1(vo);
	}

	@Override
	public String selectAllV() {
		String curDate = CurrentTime.getCurYear() + "-" + CurrentTime.getCurMonth();
		List<VConfirmVo> list = VctManager.selectConfirmedVctInfo(curDate);
		StringBuffer result = new StringBuffer();
		
		for(VConfirmVo vo : list) {
			result.append("{ ");
			if(vo.getVct_type().equals("R")) {
				result.append("title : '대체)" + vo.getEname() + "', ");
				result.append("start : '" + vo.getFrom_vctdate() + "', ");
				result.append("end : '" + CurrentTime.makeFullCalendarEndDate(vo.getTo_vctdate()) + "', ");
				result.append("color : '#660033', content : '날짜 : " + vo.getFrom_vctdate() + " ~ " + vo.getTo_vctdate() + " (" + vo.getVct_time() + "시간)\\n" + vo.getReason().split(" <br><br>")[0] + "' ");
			} else if(vo.getVct_type().equals("A")) {
				result.append("title : '연차)" + vo.getEname() + "', ");
				result.append("start : '" + vo.getFrom_vctdate() + "', ");
				result.append("end : '" + CurrentTime.makeFullCalendarEndDate(vo.getTo_vctdate()) + "', ");
				result.append("content : '날짜 : " + vo.getFrom_vctdate() + " ~ " + vo.getTo_vctdate() + " (" + vo.getVct_time() + "일)\\n" + vo.getReason() + "' ");
			} else if(vo.getVct_type().equals("S")) {
				result.append("title : '경조)" + vo.getEname() + "', ");
				result.append("start : '" + vo.getFrom_vctdate() + "', ");
				result.append("end : '" + CurrentTime.makeFullCalendarEndDate(vo.getTo_vctdate()) + "', ");
				result.append("color : '#2E8B57', content : '날짜 : " + vo.getFrom_vctdate() + " ~ " + vo.getTo_vctdate() + " (" + vo.getVct_time() + "일)\\n" + vo.getReason() + "' ");
			} else if(vo.getVct_type().equals("C")) {
				result.append("title : '복합)" + vo.getEname() + "', ");
				result.append("start : '" + vo.getFrom_vctdate() + "', ");
				result.append("end : '" + CurrentTime.makeFullCalendarEndDate(vo.getTo_vctdate()) + "', ");
				result.append("color : '#FFE4E1', content : '날짜 : " + vo.getFrom_vctdate() + " ~ " + vo.getTo_vctdate() + " (" + vo.getVct_time() + "일)\\n" + vo.getReason() + "' ");
			}
			result.append(" },");
		}
		if(result.length() > 0)	result.deleteCharAt(result.length() - 1);
		
		return result.toString();
	}

	@Override
	public int updateVctConfirmOnlyVct_confirm(VConfirmVo vo) {
		return VctManager.updateVctConfirmOnlyVct_confirm(vo);
	}

	@Override
	public int insertVctConfirmComplex(VConfirmVo vo) {
		// 1. 연차휴가 confirm 만들기
		VConfirmVo avConfirmVo = vo;
		avConfirmVo.setVct_id((int)vo.getComplexMap().get("avctId"));
		avConfirmVo.setVct_time((float)vo.getComplexMap().get("avctTime"));
		System.out.println("-- avConfirmVo --");
		System.out.println(avConfirmVo.toString());
		
		// 2. 대체휴가 confirm 만들기
		VConfirmVo rvConfirmVo = vo;
		rvConfirmVo.setVct_id((int)vo.getComplexMap().get("rvctId"));
		rvConfirmVo.setVct_time((float)vo.getComplexMap().get("rvctTime"));
		System.out.println("-- rvConfirmVo --");
		System.out.println(rvConfirmVo.toString());
		
		return 0;
	}

	@Override
	public List<VConfirmVo> selectVctConfirmList(int eno) {
		return VctManager.selectVctConfirmList(eno);
	}

	@Override
	public VConfirmVo selectVctConfirmListById(int id) {
		return VctManager.selectVctConfirmListById(id);
	}

	@Override
	public int vctDel(int id) {
		return VctManager.vctDel(id);
	}

	@Override
	public int regVctUpdate(VConfirmVo vo) {
		return VctManager.regVctUpdate(vo);
	}

	@Override
	public List<RVConfirmVo> selectTrackingList(RVConfirmVo vo) {
		return VctManager.selectTrackingList(vo);
	}

	@Override
	public RVConfirmVo selectTrackingSiteReason(int id) {
		return VctManager.selectTrackingSiteReason(id);
	}

	@Override
	public List<VctVo> adminPageSelectAllVctList(Map<String, String> map) {
		return VctManager.adminPageSelectAllVctList(map);
	}

	@Override
	public List<VConfirmVo> adminPageUpdateVctConfirmList(int vct_id) {
		return VctManager.adminPageUpdateVctConfirmList(vct_id);
	}

	@Override
	public int adminPageUpdateVct(Map<String, VctVo> map, int type) {
		return VctManager.adminPageUpdateVct(map, type);
	}
}