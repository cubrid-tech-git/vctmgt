package com.cubrid.vo;

public class RVConfirmVo {
	private int id;
	private int eno;
	private String admin;
	private String site;
	private String ename;
	private String pkey;
	private String status;
	private String work_date;
	private String start_type;
	private String work_end_date;
	private String end_type;
	private float work_time;
	private String reason;
	private String regdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public float getWork_time() {
		return work_time;
	}
	public void setWork_time(float work_time) {
		this.work_time = work_time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getWork_end_date() {
		return work_end_date;
	}
	public void setWork_end_date(String work_end_date) {
		this.work_end_date = work_end_date;
	}
	public String getStart_type() {
		return start_type;
	}
	public void setStart_type(String start_type) {
		this.start_type = start_type;
	}
	public String getEnd_type() {
		return end_type;
	}
	public void setEnd_type(String end_type) {
		this.end_type = end_type;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "RVConfirmVo [id=" + id + ", eno=" + eno + ", admin=" + admin
				+ ", site=" + site + ", ename=" + ename + ", pkey=" + pkey
				+ ", status=" + status + ", work_date=" + work_date
				+ ", start_type=" + start_type + ", work_end_date="
				+ work_end_date + ", end_type=" + end_type + ", work_time="
				+ work_time + ", reason=" + reason + ", regdate=" + regdate
				+ "]";
	}
}
