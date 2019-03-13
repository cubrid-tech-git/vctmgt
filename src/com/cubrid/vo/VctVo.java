package com.cubrid.vo;

public class VctVo extends UserVo {
	private int id;
	private String vct_type;
	private float vctcount;
	private float vctcount_tmp;
	private String enddate;
	private String cost;
	
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
	public float getVctcount() {
		return vctcount;
	}
	public void setVctcount(float vctcount) {
		this.vctcount = vctcount;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public float getVctcount_tmp() {
		return vctcount_tmp;
	}
	public void setVctcount_tmp(float vctcount_tmp) {
		this.vctcount_tmp = vctcount_tmp;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
}
