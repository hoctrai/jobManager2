package com.tma.nht.model;

public class JobObject {
	private int m_targetId;
	private int m_id;
	private String m_jobCategory;
	private String m_jobType;
	private String[] m_state = new String[2];
	
	public JobObject(int targetId, String category, String status, String data){
		m_targetId = targetId;
		m_jobCategory = category;
		m_state[0] = status;
		m_state[1] = data;
		
	}
	
	public int getTargetId() {
		return m_targetId;
	}
	public void setTargetId(int targetId) {
		this.m_targetId = targetId;
	}
	
	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		this.m_id = id;
	}
	
	public String getJobCategory() {
		return m_jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.m_jobCategory = jobCategory;
	}
	
	public String getJobType() {
		return m_jobType;
	}
	public void setJobType(String jobType) {
		this.m_jobType = jobType;
	}
	
	public String[] getState() {
		return m_state;
	}
	public void setState(String[] state) {
		this.m_state = state;
	}
	
	
}
