package com.cubrid.scheduler;

import java.util.List;

import com.cubrid.dao.UserDaoImpl;
import com.cubrid.dao.VctDaoImpl;
import com.cubrid.util.CurrentTime;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.UserVo;
import com.cubrid.vo.VConfirmVo;
import com.cubrid.vo.VctVo;
/**
 * �������� ����� �ʼ� ��������
 * @author Hun
 *
 */
public class RvctScheduler {
	public void initRvct() {
		System.out.println("*** Start of Rvct Scheduling. ***");
		VctDaoImpl vctDao = new VctDaoImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		String startDate = CurrentTime.makeStartQuater();
		String endDate = CurrentTime.makeEndQuater();
		String secondEndDate = startDate.substring(0, 8) + "10";
//		System.out.println(startDate + "\t" + endDate);
//		System.out.println(startDate.substring(0, 8));
		// rvct �б⺰�� �ʱ�ȭ�ϴ°� �߰�
		
		// 1. schedulingRvct1 �غ�
		// 1-1. eno �˴� �̾ƿ���
		List<UserVo> userList = userDao.selectAllEno();
		for(UserVo vo : userList) {
			System.out.println("eno : " + vo.getEno());
			
			// �� �б� ���� ��ü�ް��� �Ҽ��� �ڸ� �������� (�Ҽ��� �ڸ��� ���� �б�� �̿�)
			VctVo vctVo = new VctVo();
			vctVo.setEno(vo.getEno());
			vctVo.setRegdate(CurrentTime.makeStartQuater());
			RVConfirmVo rvconfirmVo = new RVConfirmVo();
			rvconfirmVo.setWork_time(0);
			rvconfirmVo.setEno(vo.getEno());
			rvconfirmVo.setWork_date(startDate);
			rvconfirmVo.setWork_end_date(endDate);
			
			// vct ���̺� ���ο� �б��� ��ü�ް� ���� �Է�
			int count = vctDao.schedulingRvct1(rvconfirmVo);
			
			if(count != 1) {
				System.out.println("scheduling is failed!");
				break;
			}
		}
		System.out.println("*** End of Rvct Scheduling. ***");
	}
}
