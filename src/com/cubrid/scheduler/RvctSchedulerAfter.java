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
 * ㅎㅎㅎㅎ 디버깅 필수 ㅎㅎㅎㅎ
 * @author Hun
 *
 */
public class RvctSchedulerAfter {
	public void initRvct() {
		System.out.println("*** Start of Rvct Scheduling 2. ***");
		VctDaoImpl vctDao = new VctDaoImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		String startDate = CurrentTime.makeStartQuater();
		String endDate = CurrentTime.makeEndQuater();
		String secondEndDate = startDate.substring(0, 8) + "10";
//		System.out.println(startDate + "\t" + endDate);
//		System.out.println(startDate.substring(0, 8));
		// rvct 분기별로 초기화하는거 추가
		
		// 1. schedulingRvct1 준비
		// 1-1. eno 죄다 뽑아오기
		List<UserVo> userList = userDao.selectAllEno();
		boolean isSuccess = true;
		for(UserVo vo : userList) {
			System.out.println("eno : " + vo.getEno());
			
			// 전 분기 남은 대체휴가일 소수점 자리 가져오기 (소수점 자리는 다음 분기로 이월)
			VctVo vctVo = new VctVo();
			vctVo.setEno(vo.getEno());
			vctVo.setRegdate(CurrentTime.makeStartQuater());
			RVConfirmVo rvconfirmVo = vctDao.schedulingRvct1_1(vctVo).get(0);
			rvconfirmVo.setEno(vo.getEno());
			rvconfirmVo.setWork_date(startDate);
			rvconfirmVo.setWork_end_date(endDate);
			
			// vct 테이블 update
			int count = vctDao.schedulingRvct2_1(rvconfirmVo); // <-- vct 테이블 update 하는 메소드 호출
			if(count != 1) {
				isSuccess = false;
				System.out.println("scheduling2 is failed!");
				break;
			}
			
		}
		// 2. schedulingRvct2 시작
		if(isSuccess) {
			List<VConfirmVo> vconfirmList = vctDao.schedulingRvct2(secondEndDate);
			boolean secondIsSuccess = true;
			for(VConfirmVo vo : vconfirmList) {
				int count = vctDao.schedulingRvct3(vo);
				if(count != 1) {
					System.out.println("second scheduling2 is failed!");
					secondIsSuccess = false;
					break;
				}
			}
			// 3. schedulingRvct4 시작
			if(secondIsSuccess) {
				vctDao.schedulingRvct4(startDate);
				System.out.println("*** End of Rvct Scheduling. ***");
			}
		}
	}
}