package com.cubrid.vo;

public class MailVo {
	private Object vo;
	private int eno;
	private String ename;
	private String manager_ename;
	private String sender;
	private String sender_password;
	private String receiver;
	private String receiver_password;
	private int dno;
	private String subject;
	private String content;
	private String cc;
	private boolean isConfirm;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Object getVo() {
		return vo;
	}
	public void setVo(Object vo) {
		this.vo = vo;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public String getManager_ename() {
		return manager_ename;
	}
	public void setManager_ename(String manager_ename) {
		this.manager_ename = manager_ename;
	}
	public String getSender_password() {
		return sender_password;
	}
	public void setSender_password(String sender_password) {
		this.sender_password = sender_password;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public boolean isConfirm() {
		return isConfirm;
	}
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getReceiver_password() {
		return receiver_password;
	}
	public void setReceiver_password(String receiver_password) {
		this.receiver_password = receiver_password;
	}
	
	@Override
	public String toString() {
		return "MailVo [vo=" + vo + ", eno=" + eno + ", ename=" + ename
				+ ", manager_ename=" + manager_ename + ", sender=" + sender
				+ ", sender_password=" + sender_password + ", receiver="
				+ receiver + ", receiver_password=" + receiver_password
				+ ", dno=" + dno + ", subject=" + subject + ", content="
				+ content + ", cc=" + cc + ", isConfirm=" + isConfirm + "]";
	}
}