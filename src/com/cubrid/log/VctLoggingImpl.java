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
		if(vo.getStart_type().toLowerCase().equals("ni")) startType = "�ɾ�";
		else if(vo.getStart_type().toLowerCase().equals("va")) startType = "����/���";
		else if(vo.getStart_type().toLowerCase().equals("ev")) startType = "���ϱ��";
		else startType = "��ü";
		if(vo.getEnd_type().toLowerCase().equals("ni")) endType = "�ɾ�";
		else if(vo.getEnd_type().toLowerCase().equals("va")) endType = "����/���";
		else if(vo.getEnd_type().toLowerCase().equals("ev")) endType = "���ϱ��";
		else endType = "��ü";
		logVo.setContent("��ü�ް� ��� ��û<br>��û�� : " + logVo.getEname() + "&nbsp;&nbsp;"
				+ "����Ʈ : " + vo.getSite() + "&nbsp;&nbsp;" + "Ʈ��ŷ : " + "<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>"
				+ "���� �Ⱓ : " + vo.getWork_date() + "(" + startType + ") ~ " + vo.getWork_end_date() + "(" + endType + ")<br>"
						+ "���� : " + vo.getReason());
		
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
		if(vo.getStart_type().toLowerCase().equals("ni")) startType = "�ɾ�";
		else if(vo.getStart_type().toLowerCase().equals("va")) startType = "����";
		else startType = "��ü";
		if(vo.getEnd_type().toLowerCase().equals("ni")) endType = "�ɾ�";
		else if(vo.getEnd_type().toLowerCase().equals("va")) endType = "����";
		else endType = "��ü";
		logVo.setContent("��ü�ް� ��� ����<br>��û�� : " + logVo.getEname() + "&nbsp;&nbsp;"
				+ "����Ʈ : " + vo.getSite() + "&nbsp;&nbsp;" + "Ʈ��ŷ : " + "<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>"
				+ "���� �Ⱓ : " + vo.getWork_date() + "(" + startType + ") ~ " + vo.getWork_end_date() + "(" + endType + ")<br>"
						+ "���� : " + vo.getReason());
		
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
		logVo.setContent("��ü�ް� ����<br>��û�� : " + logVo.getEname() + "(" + logVo.getEno() + ")&nbsp;&nbsp;����Ʈ�� : "
				+ vo.getSite() + "(<a href='http://www.cubrid.com:8888/browse/" + vo.getPkey() + "'>" + vo.getPkey() + "</a>&nbsp;&nbsp��ü�ް� �߻� �ð� : " + vo.getWork_time() + "H");
		
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
		logVo.setContent(vo.getEname() + "(" + vo.getEno() + ") �ű� ���");
		
		LogManager.insertLog(logVo);
	}
	
	@Override
	public void userDelLogging(UserVo vo) {
		String logType = "UDL";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent(vo.getEname() + "(" + vo.getEno() + ") ����"); 

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
		logVo.setContent("������ : " + vo.getEname() + " �� �α���");
		
		LogManager.insertLog(logVo);
	}

	@Override
	public void adminChangePWLogging(UserVo vo) {
		String logType = "CPW";
		LogVo logVo = new LogVo();
		logVo.setEno(vo.getEno());
		logVo.setEname(vo.getEname());
		logVo.setType(logType);
		logVo.setContent("������ : " + vo.getEname() + " �� ��й�ȣ ����");
		
		LogManager.insertLog(logVo);
	}
}
