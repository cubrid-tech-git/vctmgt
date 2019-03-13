package com.cubrid.log;

import java.util.List;

import com.cubrid.data.LogManager;
import com.cubrid.vo.LogVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;

public class VctLoggingImpl implements VctLogging {

	@Override
	public void rvctRegLogging(RVConfirmVo vo) {
		String logType = "RVR";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		String startType = null;
		String endType = null;
		if(vo.getStart_type().toLowerCase().equals("ni")) startType = "심야";
		else if(vo.getStart_type().toLowerCase().equals("va")) startType = "휴일/긴급";
		else if(vo.getStart_type().toLowerCase().equals("ev")) startType = "평일긴급";
		else startType = "대체";
		if(vo.getEnd_type().toLowerCase().equals("ni")) endType = "심야";
		else if(vo.getEnd_type().toLowerCase().equals("va")) endType = "휴일/긴급";
		else if(vo.getEnd_type().toLowerCase().equals("ev")) endType = "평일긴급";
		else endType = "대체";
		logVo.setContent("대체휴가 등록 신청<br>신청자 : " + logVo.getEname() + "&nbsp;&nbsp;"
				+ "사이트 : " + vo.getSite() + "&nbsp;&nbsp;" + "트래킹 : " + "<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>"
				+ "지원 기간 : " + vo.getWork_date() + "(" + startType + ") ~ " + vo.getWork_end_date() + "(" + endType + ")<br>"
						+ "내용 : " + vo.getReason());
		
		LogManager.insertLog(logVo);
	}

	@Override
	public void rvctRegModifyLogging(RVConfirmVo vo) {
		String logType = "RVM";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		String startType = null;
		String endType = null;
		if(vo.getStart_type().toLowerCase().equals("ni")) startType = "심야";
		else if(vo.getStart_type().toLowerCase().equals("va")) startType = "휴일";
		else startType = "대체";
		if(vo.getEnd_type().toLowerCase().equals("ni")) endType = "심야";
		else if(vo.getEnd_type().toLowerCase().equals("va")) endType = "휴일";
		else endType = "대체";
		logVo.setContent("대체휴가 등록 수정<br>신청자 : " + logVo.getEname() + "&nbsp;&nbsp;"
				+ "사이트 : " + vo.getSite() + "&nbsp;&nbsp;" + "트래킹 : " + "<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>"
				+ "지원 기간 : " + vo.getWork_date() + "(" + startType + ") ~ " + vo.getWork_end_date() + "(" + endType + ")<br>"
						+ "내용 : " + vo.getReason());
		
		LogManager.insertLog(logVo);
	}

	@Override
	public void rvctRegYNLogging(RVConfirmVo vo) {
		String logType = "RRC";
		vo = LogManager.selectRvctConfirm(vo);
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent("대체휴가 승인<br>신청자 : " + logVo.getEname() + "(" + logVo.getEno() + ")&nbsp;&nbsp;사이트명 : "
				+ vo.getSite() + "(<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>&nbsp;&nbsp대체휴가 발생 시간 : " + vo.getWork_time() + "H");
		
		LogManager.insertLog(logVo);
	}

	@Override
	public void rvctUseLogging(VConfirmVo vo) {
		String logType = "RUS";

	}

	@Override
	public void rvctUseYNLogging(VConfirmVo vo) {
		String logType = "RUC";

	}

	@Override
	public void rvctRenewalLogging(RVConfirmVo vo) {
		String logType = "RRE";

	}

	@Override
	public void vctRenewalLogging(VctVo vo) {
		String logType = "ARE";

	}

	@Override
	public void vctUseLogging(VConfirmVo vo) {
		String logType = "AUS";

	}

	@Override
	public void vctUseYNLogging(VConfirmVo vo) {
		String logType = "AUC";

	}

	@Override
	public void userAddLogging(UserVo vo) {
		String logType = "UAD";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent(vo.getEname() + "(" + vo.getEno() + ") 신규 등록");
		
		LogManager.insertLog(logVo);
	}
	
	@Override
	public void userDelLogging(UserVo vo) {
		String logType = "UDL";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent(vo.getEname() + "(" + vo.getEno() + ") 삭제"); 

		LogManager.insertLog(logVo);
	}

	@Override
	public List<LogVo> printLog(LogVo vo) {
		List<LogVo> list = LogManager.selectLog(vo);
		return list;
	}

	@Override
	public void adminLoginLogging(UserVo vo) {
		String logType = "LGI";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent("관리자 : " + vo.getEname() + " 님 로그인");
		
		LogManager.insertLog(logVo);
	}

	@Override
	public void adminChangePWLogging(UserVo vo) {
		String logType = "CPW";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent("관리자 : " + vo.getEname() + " 님 비밀번호 변경");
		
		LogManager.insertLog(logVo);
	}
}
