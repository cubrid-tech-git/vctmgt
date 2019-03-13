package com.cubrid.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cubrid.util.KeyGen;
import com.cubrid.vo.MailVo;
import com.cubrid.vo.RVConfirmVo;
import com.cubrid.vo.VConfirmVo;

public class CubMailSender {

	public static MailVo makeSendType(String type, MailVo vo) {
		// ��ü�ް� ��� ��û (��û�� -> ������, CC)
		if (type.equals("RConfirm")) {
			vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : ��ü�ް� ��� ��û�մϴ�.");
			RVConfirmVo rvctVo = (RVConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			content.append("����Ʈ : " + rvctVo.getSite() + "<br>");
			content.append("Ʈ��ŷ ���� ���� : <a href='http://www.cubrid.com:8888/browse/" + rvctVo.getPkey() + "'>"
					+ rvctVo.getPkey() + "</a><br>");
			content.append("�ʰ��ٹ� ���� �ð� : " + rvctVo.getWork_date() + "<br>");
			content.append("�ʰ��ٹ� ���� �ð� : " + rvctVo.getWork_end_date() + "<br>");
			content.append("��ü�ް� �߻� �ð� : " + rvctVo.getWork_time() + " �ð�<br>");
			content.append("�ʰ��ٹ� ���� : " + rvctVo.getReason() + "<br>");
			vo.setContent(content.toString());

			// ��ü�ް� ��� ���� �� ����
		} else if (type.equals("RConfirmYN")) {
			// ���ν�
			RVConfirmVo rVo = (RVConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			if (vo.isConfirm()) {
				vo.setSubject("[�ް���Ȳ��] ��ü�ް� ��� �����մϴ�.");
				content.append("����Ʈ : " + rVo.getSite() + "<br>");
				content.append("Ʈ��ŷ ���� ���� : <a href='http://www.cubrid.com:8888/browse/" + rVo.getPkey() + "'>"
						+ rVo.getPkey() + "</a><br>");
				content.append("�ʰ��ٹ� ���� �ð� : " + rVo.getWork_date() + "<br>");
				content.append("�ʰ��ٹ� ���� �ð� : " + rVo.getWork_end_date() + "<br>");
				content.append("��ü�ް� �߻� �ð� : " + rVo.getWork_time() + " �ð�<br>");
				content.append("�ʰ��ٹ� ���� : " + rVo.getReason() + "<br><br>");
				content.append("�����մϴ�.<br>");
				vo.setContent(content.toString());
				// ������
			} else {
				vo.setSubject("[�ް���Ȳ��] ��ü�ް� ��� �̽����մϴ�.");
				content.append("����Ʈ : " + rVo.getSite() + "<br>");
				content.append("Ʈ��ŷ ���� ���� : <a href='http://www.cubrid.com:8888/browse/" + rVo.getPkey() + "'>"
						+ rVo.getPkey() + "</a><br>");
				content.append("�ʰ��ٹ� ���� �ð� : " + rVo.getWork_date() + "<br>");
				content.append("�ʰ��ٹ� ���� �ð� : " + rVo.getWork_end_date() + "<br>");
				content.append("��ü�ް� �߻� �ð� : " + rVo.getWork_time() + " �ð�<br>");
				content.append("�ʰ��ٹ� ���� : " + rVo.getReason() + "<br><br>");
				content.append("�̽����մϴ�.<br>");
				vo.setContent(content.toString());
			}

			// �ް�(��ü or ����) ��� ��û
		} else if (type.equals("VctConfirm")) {
			VConfirmVo vConfirmVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();

			if (vConfirmVo.getVct_type().equals("A")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : �����ް� ��� ��û�մϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " ��<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("R")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : ��ü�ް� ��� ��û�մϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " �ð�<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("C")) {

			} else if (vConfirmVo.getVct_type().equals("S")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : ����(Ư��)�ް� ��� ��û�մϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " ��<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else {
				System.out.println("VCT CONFIRM MAILING : NOT A VCT TYPE");
			}

			// �ް� / ��ü�ް� ��� ���� �� ����
		} else if (type.equals("VctConfirmYN")) {
			VConfirmVo vVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			// ���ν�
			if (vo.isConfirm()) {
				if (vVo.getVct_type().equals("A")) {
					vo.setSubject("[�ް���Ȳ��] �����ް� ��� �����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " ��<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�����մϴ�.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("R")) {
					vo.setSubject("[�ް���Ȳ��] ��ü�ް� ��� �����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " �ð�<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�����մϴ�.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("C")) {

				} else if (vVo.getVct_type().equals("S")) {
					vo.setSubject("[�ް���Ȳ��] ����(Ư��)�ް� ��� �����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " ��<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�����մϴ�.<br>");
					vo.setContent(content.toString());
				}
				// ������
			} else {
				if (vVo.getVct_type().equals("A")) {
					vo.setSubject("[�ް���Ȳ��] �����ް� ��� �̽����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " ��<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�̽����մϴ�.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("R")) {
					vo.setSubject("[�ް���Ȳ��] ��ü�ް� ��� �̽����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " �ð�<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�̽����մϴ�.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("C")) {

				} else if (vVo.getVct_type().equals("S")) {
					vo.setSubject("[�ް���Ȳ��] ����(Ư��)�ް� ��� �̽����մϴ�.");
					content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("�ް� �ð� : " + vVo.getVct_time() + " ��<br>");
					content.append("���� : " + vVo.getReason() + "<br><br>");
					content.append("�̽����մϴ�.<br>");
					vo.setContent(content.toString());
				}
			}

			// �ް� ��û ���� ������ ��� mail ������
		} else if (type.equals("VConfirmUpdate")) {
			VConfirmVo vConfirmVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();

			if (vConfirmVo.getVct_type().equals("A")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : �����ް� ��û���� �����Ͽ� �ٽ� �����ϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " ��<br>");
				content.append("�ް� ���� : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("R")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : ��ü�ް� ��û���� �����Ͽ� �ٽ� �����ϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " �ð�<br>");
				content.append("�ް� ���� : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("C")) {

			} else if (vConfirmVo.getVct_type().equals("S")) {
				vo.setSubject("[�ް���Ȳ��] " + vo.getEname() + " : ����(Ư��)�ް� ��û���� �����Ͽ� �ٽ� �����ϴ�.");
				content.append("�ް� �Ⱓ : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("�ް� �ð� : " + vConfirmVo.getVct_time() + " ��<br>");
				content.append("�ް� ���� : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else {
				System.out.println("VCT CONFIRM MAILING : NOT A VCT TYPE");
			}
			// �ް� ��û ���� ������ ��� mail ������
		} else if (type.equals("VConfirmDel")) {
			VConfirmVo vVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			vo.setSubject("[�ް���Ȳ��] �ް� ���γ��� ��� �մϴ�.");
			content.append("�ް� �Ⱓ : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
			if (vVo.getVct_type().equals("R"))
				content.append("�ް� �ð� : " + vVo.getVct_time() + " �ð�<br>");
			else
				content.append("�ް� �ð� : " + vVo.getVct_time() + " ��<br>");
			content.append("���� : " + vVo.getReason() + "<br><br>");
			vo.setContent(content.toString());
		}

		return vo;
	}

	public static boolean send(MailVo vo) {
		String sendId = vo.getSender();
		String sendPw = vo.getSender_password();
		System.out.println(vo.getEname() + "\t" + vo.getReceiver() + "\n" + vo.getSubject() + "\t" + vo.getContent());

		boolean successCheck = false;

		// ���� �߼��� ���� ���� ����
		// ������ �߼��� �� SMTP ������ ���� ���� ������ ����
		// ������ ��� ���� ��ü
		Properties p = new Properties();

		// SMTP ������ ���� ����
		// Naver�� ������ ��� ���̹� ���̵� ����
		// Google�� ������ ��� ������ Gmail �ּ�
		p.put("mail.stmp.user", sendId);

		// SMTP ���� ���� ����
		// ���̹��� ��� smtp.naver.com
		// Google �� ��� smtp.gmail.com
		// p.put("mail.smtp.host", "smtp.gmail.com");
		// p.put("mail.smtp.host", "mail.cubrid.com");
		p.put("mail.smtp.host", "smtp.office365.com");
		p.put("mail.smtp.starttls.enable","true");
		p.put("mail.smtp.ssl.trust", "smtp.office365.com");

		// �Ʒ� ������ ���̹��� ������ ����
		// p.put("mail.smtp.port", "465"); // gmail, naver
		// p.put("mail.smtp.port", "25");
		p.put("mail.smtp.port", "587");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		// p.put("mail.smtp.socketFactory.port", "465"); // gmail, naver
		// p.put("mail.smtp.socketFactory.port", "25");
		p.put("mail.smtp.socketFactory.port", "587");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// p.put("mail.smtp.socketFactory.fallback", "false"); // gmail, naver
		p.put("mail.smtp.socketFactory.fallback", "true");

		try {
			Authenticator auth = new SMTPAuthenticator(sendId, KeyGen.dec(sendPw));
			Session ses = Session.getInstance(p, auth);

			// ������ ������ �� ���� ��Ȳ�� �ֿܼ� ���
			ses.setDebug(true);

			// ������ ������ ��� ���� ��ü
			MimeMessage msg = new MimeMessage(ses);

			// ���� ����
			msg.setSubject(vo.getSubject(), "UTF-8");

			// ������ ����� ���� �ּ�
			Address fromAddr = new InternetAddress(sendId);
			msg.setFrom(fromAddr);

			// �޴� ����� ���� �ּ�
			// Address toAddr = new InternetAddress(vo.getReceiver());
			// msg.addRecipient(Message.RecipientType.TO, toAddr);
			Address[] toAddrArr = { new InternetAddress(vo.getReceiver()), new InternetAddress(vo.getSender()) };
			msg.addRecipients(Message.RecipientType.TO, toAddrArr);

			// ���� ���� �ּ�
			Address ccAddr = null;
			if (vo.getCc() != null) {
				ccAddr = new InternetAddress(vo.getCc());
				msg.addRecipient(Message.RecipientType.CC, ccAddr);
			}

			// �޽��� ������ ����� ����, ĳ���� �� ����
			msg.setContent(vo.getContent(), "text/html;charset=UTF-8");

			// �߼��ϱ�
			Transport.send(msg);
			successCheck = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return successCheck;
	}
}
