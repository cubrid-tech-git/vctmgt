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
		// 대체휴가 등록 신청 (신청자 -> 과리자, CC)
		if (type.equals("RConfirm")) {
			vo.setSubject("[휴가현황판] " + vo.getEname() + " : 대체휴가 등록 신청합니다.");
			RVConfirmVo rvctVo = (RVConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			content.append("사이트 : " + rvctVo.getSite() + "<br>");
			content.append("트래킹 서비스 정보 : <a href='http://www.cubrid.com:8888/browse/" + rvctVo.getPkey() + "'>"
					+ rvctVo.getPkey() + "</a><br>");
			content.append("초과근무 시작 시간 : " + rvctVo.getWork_date() + "<br>");
			content.append("초과근무 종료 시간 : " + rvctVo.getWork_end_date() + "<br>");
			content.append("대체휴가 발생 시간 : " + rvctVo.getWork_time() + " 시간<br>");
			content.append("초과근무 내용 : " + rvctVo.getReason() + "<br>");
			vo.setContent(content.toString());

			// 대체휴가 등록 승인 및 거절
		} else if (type.equals("RConfirmYN")) {
			// 승인시
			RVConfirmVo rVo = (RVConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			if (vo.isConfirm()) {
				vo.setSubject("[휴가현황판] 대체휴가 등록 승인합니다.");
				content.append("사이트 : " + rVo.getSite() + "<br>");
				content.append("트래킹 서비스 정보 : <a href='http://www.cubrid.com:8888/browse/" + rVo.getPkey() + "'>"
						+ rVo.getPkey() + "</a><br>");
				content.append("초과근무 시작 시간 : " + rVo.getWork_date() + "<br>");
				content.append("초과근무 종료 시간 : " + rVo.getWork_end_date() + "<br>");
				content.append("대체휴가 발생 시간 : " + rVo.getWork_time() + " 시간<br>");
				content.append("초과근무 내용 : " + rVo.getReason() + "<br><br>");
				content.append("승인합니다.<br>");
				vo.setContent(content.toString());
				// 거절시
			} else {
				vo.setSubject("[휴가현황판] 대체휴가 등록 미승인합니다.");
				content.append("사이트 : " + rVo.getSite() + "<br>");
				content.append("트래킹 서비스 정보 : <a href='http://www.cubrid.com:8888/browse/" + rVo.getPkey() + "'>"
						+ rVo.getPkey() + "</a><br>");
				content.append("초과근무 시작 시간 : " + rVo.getWork_date() + "<br>");
				content.append("초과근무 종료 시간 : " + rVo.getWork_end_date() + "<br>");
				content.append("대체휴가 발생 시간 : " + rVo.getWork_time() + " 시간<br>");
				content.append("초과근무 내용 : " + rVo.getReason() + "<br><br>");
				content.append("미승인합니다.<br>");
				vo.setContent(content.toString());
			}

			// 휴가(대체 or 연차) 사용 신청
		} else if (type.equals("VctConfirm")) {
			VConfirmVo vConfirmVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();

			if (vConfirmVo.getVct_type().equals("A")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 연차휴가 사용 신청합니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 일<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("R")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 대체휴가 사용 신청합니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 시간<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("C")) {

			} else if (vConfirmVo.getVct_type().equals("S")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 경조(특별)휴가 사용 신청합니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 일<br>");
				content.append(vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else {
				System.out.println("VCT CONFIRM MAILING : NOT A VCT TYPE");
			}

			// 휴가 / 대체휴가 사용 승인 및 거절
		} else if (type.equals("VctConfirmYN")) {
			VConfirmVo vVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			// 승인시
			if (vo.isConfirm()) {
				if (vVo.getVct_type().equals("A")) {
					vo.setSubject("[휴가현황판] 연차휴가 사용 승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 일<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("승인합니다.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("R")) {
					vo.setSubject("[휴가현황판] 대체휴가 사용 승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 시간<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("승인합니다.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("C")) {

				} else if (vVo.getVct_type().equals("S")) {
					vo.setSubject("[휴가현황판] 경조(특별)휴가 사용 승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 일<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("승인합니다.<br>");
					vo.setContent(content.toString());
				}
				// 거절시
			} else {
				if (vVo.getVct_type().equals("A")) {
					vo.setSubject("[휴가현황판] 연차휴가 사용 미승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 일<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("미승인합니다.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("R")) {
					vo.setSubject("[휴가현황판] 대체휴가 사용 미승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 시간<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("미승인합니다.<br>");
					vo.setContent(content.toString());
				} else if (vVo.getVct_type().equals("C")) {

				} else if (vVo.getVct_type().equals("S")) {
					vo.setSubject("[휴가현황판] 경조(특별)휴가 사용 미승인합니다.");
					content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
					content.append("휴가 시간 : " + vVo.getVct_time() + " 일<br>");
					content.append("사유 : " + vVo.getReason() + "<br><br>");
					content.append("미승인합니다.<br>");
					vo.setContent(content.toString());
				}
			}

			// 휴가 신청 정보 수정할 경우 mail 보내기
		} else if (type.equals("VConfirmUpdate")) {
			VConfirmVo vConfirmVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();

			if (vConfirmVo.getVct_type().equals("A")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 연차휴가 신청내용 수정하여 다시 보냅니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 일<br>");
				content.append("휴가 사유 : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("R")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 대체휴가 신청내용 수정하여 다시 보냅니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 시간<br>");
				content.append("휴가 사유 : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else if (vConfirmVo.getVct_type().equals("C")) {

			} else if (vConfirmVo.getVct_type().equals("S")) {
				vo.setSubject("[휴가현황판] " + vo.getEname() + " : 경조(특별)휴가 신청내용 수정하여 다시 보냅니다.");
				content.append("휴가 기간 : " + vConfirmVo.getFrom_vctdate() + " ~ " + vConfirmVo.getTo_vctdate() + "<br>");
				content.append("휴가 시간 : " + vConfirmVo.getVct_time() + " 일<br>");
				content.append("휴가 사유 : " + vConfirmVo.getReason() + "<br>");
				vo.setContent(content.toString());
			} else {
				System.out.println("VCT CONFIRM MAILING : NOT A VCT TYPE");
			}
			// 휴가 신청 정보 삭제할 경우 mail 보내기
		} else if (type.equals("VConfirmDel")) {
			VConfirmVo vVo = (VConfirmVo) vo.getVo();
			StringBuffer content = new StringBuffer();
			vo.setSubject("[휴가현황판] 휴가 승인내역 취소 합니다.");
			content.append("휴가 기간 : " + vVo.getFrom_vctdate() + " ~ " + vVo.getTo_vctdate() + "<br>");
			if (vVo.getVct_type().equals("R"))
				content.append("휴가 시간 : " + vVo.getVct_time() + " 시간<br>");
			else
				content.append("휴가 시간 : " + vVo.getVct_time() + " 일<br>");
			content.append("사유 : " + vVo.getReason() + "<br><br>");
			vo.setContent(content.toString());
		}

		return vo;
	}

	public static boolean send(MailVo vo) {
		String sendId = vo.getSender();
		String sendPw = vo.getSender_password();
		System.out.println(vo.getEname() + "\t" + vo.getReceiver() + "\n" + vo.getSubject() + "\t" + vo.getContent());

		boolean successCheck = false;

		// 메일 발송을 위한 정보 설정
		// 메일을 발송해 줄 SMTP 서버에 대한 접속 정보를 설정
		// 정보를 담기 위한 객체
		Properties p = new Properties();

		// SMTP 서버의 계정 설정
		// Naver와 연결할 경우 네이버 아이디 지정
		// Google과 연결할 경우 본인의 Gmail 주소
		p.put("mail.stmp.user", sendId);

		// SMTP 서버 정보 설정
		// 네이버일 경우 smtp.naver.com
		// Google 일 경우 smtp.gmail.com
		// p.put("mail.smtp.host", "smtp.gmail.com");
		// p.put("mail.smtp.host", "mail.cubrid.com");
		p.put("mail.smtp.host", "smtp.office365.com");
		p.put("mail.smtp.starttls.enable","true");
		p.put("mail.smtp.ssl.trust", "smtp.office365.com");

		// 아래 정보는 네이버와 구글이 동일
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

			// 메일을 전송할 때 상세한 상황을 콘솔에 출력
			ses.setDebug(true);

			// 메일의 내용을 담기 위한 객체
			MimeMessage msg = new MimeMessage(ses);

			// 제목 설정
			msg.setSubject(vo.getSubject(), "UTF-8");

			// 보내는 사람의 메일 주소
			Address fromAddr = new InternetAddress(sendId);
			msg.setFrom(fromAddr);

			// 받는 사람의 메일 주소
			// Address toAddr = new InternetAddress(vo.getReceiver());
			// msg.addRecipient(Message.RecipientType.TO, toAddr);
			Address[] toAddrArr = { new InternetAddress(vo.getReceiver()), new InternetAddress(vo.getSender()) };
			msg.addRecipients(Message.RecipientType.TO, toAddrArr);

			// 참조 메일 주소
			Address ccAddr = null;
			if (vo.getCc() != null) {
				ccAddr = new InternetAddress(vo.getCc());
				msg.addRecipient(Message.RecipientType.CC, ccAddr);
			}

			// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
			msg.setContent(vo.getContent(), "text/html;charset=UTF-8");

			// 발송하기
			Transport.send(msg);
			successCheck = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return successCheck;
	}
}
