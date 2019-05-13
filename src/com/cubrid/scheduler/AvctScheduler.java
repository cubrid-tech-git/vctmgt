package com.cubrid.scheduler;

import java.util.List;

import com.cubrid.dao.UserDaoImpl;
import com.cubrid.dao.VctDaoImpl;
import com.cubrid.util.CurrentTime;
import com.cubrid.vo.VctVo;

public class AvctScheduler {

        public void initRvct() {
                System.out.println("*** Start of Avct Scheduling. ***");

                VctDaoImpl vctDao = new VctDaoImpl();
                UserDaoImpl userDao = new UserDaoImpl();
                //String endDate = "2016-01-01";        // 테스트용 변수
                /*
                        테스트용 변수로 설정되어 있어서 2017-01-1 기준이 아닌
                        2016-01-01 기준으로 휴가 카운팅을 했던 오류 수정
                */
                String endDate = CurrentTime.startYear();
                List<VctVo> vctList = vctDao.schedulingAvct1(endDate);
                int memberCount = 0;

                for(VctVo vo : vctList) {
                        System.out.println("id : " + vo.getId() + "\teno : " + vo.getEno() + "\tename : " + vo.getEname() + "\tvct_type : " + vo.getVct_type() + "\tvctcount : " + vo.getVctcount() + "\tregdate : " + vo.getRegdate() + "\tenddate : " + vo.getEnddate());

                        if(userDao.selectCubUser(vo.getEno()) != null) {
                                vo.setRegdate(CurrentTime.startYear());
                                vo.setEnddate(CurrentTime.endYear());
                                memberCount += vctDao.schedulingAvct2(vo);
                        }
                }
                System.out.println(memberCount + "명 연차휴가 갱신 완료.");

                int vctConfirmResult = vctDao.schedulingAvct3();

                System.out.println(vctConfirmResult + " 연차휴가 신청 현황 삭제");

                System.out.println("*** End of Avct Scheduling. ***");
        }
}