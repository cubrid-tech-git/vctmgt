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
 * ぞぞぞぞ 巨獄焔 琶呪 ぞぞぞぞ
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
		// rvct 歳奄紺稽 段奄鉢馬澗暗 蓄亜
		
		// 1. schedulingRvct1 層搾
		// 1-1. eno 阻陥 嗣焼神奄
		List<UserVo> userList = userDao.selectAllEno();
		boolean isSuccess = true;
		for(UserVo vo : userList) {
			System.out.println("eno : " + vo.getEno());
			
			// 穿 歳奄 害精 企端妃亜析 社呪繊 切軒 亜閃神奄 (社呪繊 切軒澗 陥製 歳奄稽 戚杉)
			VctVo vctVo = new VctVo();
			vctVo.setEno(vo.getEno());
			vctVo.setRegdate(CurrentTime.makeStartQuater());
			RVConfirmVo rvconfirmVo = vctDao.schedulingRvct1_1(vctVo).get(0);
			rvconfirmVo.setEno(vo.getEno());
			rvconfirmVo.setWork_date(startDate);
			rvconfirmVo.setWork_end_date(endDate);
			
			// vct 砺戚鷺 update
			int count = vctDao.schedulingRvct2_1(rvconfirmVo); // <-- vct 砺戚鷺 update 馬澗 五社球 硲窒
			if(count != 1) {
				isSuccess = false;
				System.out.println("scheduling2 is failed!");
				break;
			}
			
		}
		// 2. schedulingRvct2 獣拙
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
			// 3. schedulingRvct4 獣拙
			if(secondIsSuccess) {
				vctDao.schedulingRvct4(startDate);
				System.out.println("*** End of Rvct Scheduling. ***");
			}
		}
	}
}
