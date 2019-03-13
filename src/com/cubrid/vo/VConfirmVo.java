package com.cubrid.vo;

import java.util.Map;

public class VConfirmVo {
	@Override
	public String toString() {
		return "VConfirmVo [id=" + id + ", vct_type=" + vct_type + ", vct_id="
				+ vct_id + ", status=" + status + ", from_vctdate="
				+ from_vctdate + ", to_vctdate=" + to_vctdate
				+ ", remain_vct_time=" + remain_vct_time + ", vct_time="
				+ vct_time + ", eno=" + eno + ", ename=" + ename + ", reason="
				+ reason + ", complexMap=" + complexMap + "]";
	}
	
	private int id;
	private String vct_type;
	private int vct_id;
	private String status;
	private String from_vctdate;
	private String to_vctdate;
	private float remain_vct_time;
	private float vct_time;
	private int eno;
	private int dno;
	private String ename;
	private String reason;
	private Map<String, Object> complexMap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVct_type() {
		return vct_type;
	}
	public void setVct_type(String vct_type) {
		this.vct_type = vct_type;
	}
	public int getVct_id() {
		return vct_id;
	}
	public void setVct_id(int vct_id) {
		this.vct_id = vct_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFrom_vctdate() {
		return from_vctdate;
	}
	public void setFrom_vctdate(String from_vctdate) {
		this.from_vctdate = from_vctdate;
	}
	public String getTo_vctdate() {
		return to_vctdate;
	}
	public void setTo_vctdate(String to_vctdate) {
		this.to_vctdate = to_vctdate;
	}
	public float getRemain_vct_time() {
		return remain_vct_time;
	}
	public void setRemain_vct_time(float remain_vct_time) {
		this.remain_vct_time = remain_vct_time;
	}
	public float getVct_time() {
		return vct_time;
	}
	public void setVct_time(float vct_time) {
		this.vct_time = vct_time;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Map<String, Object> getComplexMap() {
		return complexMap;
	}
	public void setComplexMap(Map<String, Object> complexIdMap) {
		this.complexMap = complexIdMap;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
}
