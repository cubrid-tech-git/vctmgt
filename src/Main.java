import com.cubrid.data.UserManager;
import com.cubrid.mail.CubMailSender;
import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;


public class Main {
	public static void main(String[] args) {
//		// sender의 eno를 통해 email_password와 본인 email, manager, cc id 가져오기
//		MailVo mailVo = UserManager.selectMailInfo(16);
//		System.out.println(mailVo.getEno() + "\t" + mailVo.getSender() + "\t" + mailVo.getSender_password() + "\t" + mailVo.getReceiver() + "\t" + mailVo.getManager_ename() + "\t" + mailVo.getDno() + "\t" + mailVo.getCc());
//		//RVConfirmVo 객체 만들기
//		RVConfirmVo rVo = new RVConfirmVo();
//		rVo.setSite("테스트사이트");
//		rVo.setPkey("CUBRID-key");
//		rVo.setWork_date("2015-07-10 21:00");
//		rVo.setWork_end_date("2015-07-11 00:00");
//		rVo.setWork_time(3);
//		rVo.setReason("이거슨 테스트<br>테스트!");
//		mailVo.setVo(rVo);
//		CubMailSender.makeSendType("RConfirm", mailVo);
//		System.out.println("receiver 변경 -> seunghun_kim@naver.com");
//		System.out.println("cc 변경 -> seunghun_kim@cubrid.com");
//		mailVo.setReceiver("seunghun_kim@naver.com");
//		mailVo.setCc("seunghun_kim@cubrid.com");
////		mailVo.setCc(null);
//		CubMailSender.send(mailVo);
		
		MailVo mailVo = new MailVo();
		mailVo.setSender("seunghun_kim@cubrid.com");
		mailVo.setReceiver("seunghun_kim@naver.com");
		mailVo.setSender_password("c2V1bmdodW5fa2ltMTIz");
		mailVo.setCc("coke0803@hotmail.com");
		mailVo.setSubject("test테스트");
		mailVo.setContent("이것은 테스트");
		CubMailSender.send(mailVo);
	}
}
