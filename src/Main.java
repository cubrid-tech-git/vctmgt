import com.cubrid.data.UserManager;
import com.cubrid.mail.CubMailSender;
import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;


public class Main {
	public static void main(String[] args) {
//		// sender�� eno�� ���� email_password�� ���� email, manager, cc id ��������
//		MailVo mailVo = UserManager.selectMailInfo(16);
//		System.out.println(mailVo.getEno() + "\t" + mailVo.getSender() + "\t" + mailVo.getSender_password() + "\t" + mailVo.getReceiver() + "\t" + mailVo.getManager_ename() + "\t" + mailVo.getDno() + "\t" + mailVo.getCc());
//		//RVConfirmVo ��ü �����
//		RVConfirmVo rVo = new RVConfirmVo();
//		rVo.setSite("�׽�Ʈ����Ʈ");
//		rVo.setPkey("CUBRID-key");
//		rVo.setWork_date("2015-07-10 21:00");
//		rVo.setWork_end_date("2015-07-11 00:00");
//		rVo.setWork_time(3);
//		rVo.setReason("�̰Ž� �׽�Ʈ<br>�׽�Ʈ!");
//		mailVo.setVo(rVo);
//		CubMailSender.makeSendType("RConfirm", mailVo);
//		System.out.println("receiver ���� -> seunghun_kim@naver.com");
//		System.out.println("cc ���� -> seunghun_kim@cubrid.com");
//		mailVo.setReceiver("seunghun_kim@naver.com");
//		mailVo.setCc("seunghun_kim@cubrid.com");
////		mailVo.setCc(null);
//		CubMailSender.send(mailVo);
		
		MailVo mailVo = new MailVo();
		mailVo.setSender("seunghun_kim@cubrid.com");
		mailVo.setReceiver("seunghun_kim@naver.com");
		mailVo.setSender_password("c2V1bmdodW5fa2ltMTIz");
		mailVo.setCc("coke0803@hotmail.com");
		mailVo.setSubject("test�׽�Ʈ");
		mailVo.setContent("�̰��� �׽�Ʈ");
		CubMailSender.send(mailVo);
	}
}
