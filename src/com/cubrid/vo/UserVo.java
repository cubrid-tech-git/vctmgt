package com.cubrid.vo;

public class UserVo {
	private int eno;
	private int dno;
	private String dname;
	private String ename;
	private String password;
	private String email;
	private String regdate;
	private int admincode;
	private String adminEname;
	private String email_password;
	
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getAdmincode() {
		return admincode;
	}
	public void setAdmincode(int admincode) {
		this.admincode = admincode;
	}
	public String getAdminEname() {
		return adminEname;
	}
	public void setAdminEname(String adminEname) {
		this.adminEname = adminEname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail_password() {
		return email_password;
	}
	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	@Override
	public String toString() {
		return "UserVo [eno=" + eno + ", dno=" + dno + ", dname=" + dname
				+ ", ename=" + ename + ", password=" + password + ", email="
				+ email + ", regdate=" + regdate + ", admincode=" + admincode
				+ ", adminEname=" + adminEname + ", email_password="
				+ email_password + "]";
	}
}