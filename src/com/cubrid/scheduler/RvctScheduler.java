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
		// rvct 歳奄紺稽 段奄鉢馬澗暗 蓄亜
		
		// 1. schedulingRvct1 層搾
		// 1-1. eno 阻陥 嗣焼神奄
		List<UserVo> userList = userDao.selectAllEno();
		for(UserVo vo : userList) {
			System.out.println("eno : " + vo.getEno());
			
			// 穿 歳奄 害精 企端妃亜析 社呪繊 切軒 亜閃神奄 (社呪繊 切軒澗 陥製 歳奄稽 戚杉)
			VctVo vctVo = new VctVo();
			vctVo.setEno(vo.getEno());
			vctVo.setRegdate(CurrentTime.makeStartQuater());
			RVConfirmVo rvconfirmVo = new RVConfirmVo();
			rvconfirmVo.setWork_time(0);
			rvconfirmVo.setEno(vo.getEno());
			rvconfirmVo.setWork_date(startDate);
			rvconfirmVo.setWork_end_date(endDate);
			
			// vct 砺戚鷺拭 歯稽錘 歳奄税 企端妃亜 舛左 脊径
			int count = vctDao.schedulingRvct1(rvconfirmVo);
			
			if(count != 1) {
				System.out.println("scheduling is failed!");
				break;
			}
		}
		System.out.println("*** End of Rvct Scheduling. ***");
	}
}
