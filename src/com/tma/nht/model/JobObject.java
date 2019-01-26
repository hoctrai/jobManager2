package com.tma.nht.model;
public class JobObject {
	private int m_targetId;
	private long m_id;
	private String m_jobCategory;
	private String m_jobType;
	private String[] m_state = new String[2];
	private String m_submit;
	private String m_start;
	private String m_timeout;
	private String m_sever;
	
	public JobObject(int targetId, String status, String data){
		m_targetId = targetId;
		m_state[0] = status;
		m_state[1] = data;
		
	}
	
	public JobObject(long id, String category, String type, String submit, String start, String timeout, String server) {
		m_id = id;
		m_jobCategory = category;
		m_jobType = type;
		m_submit = submit;
		m_start = start;
		m_timeout = timeout;
		m_sever = server;
	}

	public int getTargetId() {
		return m_targetId;
	}
	public void setTargetId(int targetId) {
		this.m_targetId = targetId;
	}
	
	public long getId() {
		return m_id;
	}
	public void setId(long id) {
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

	public String getSubmit() {
		return m_submit;
	}
	public void setSubmit(String m_submit) {
		this.m_submit = m_submit;
	}

	public String getStart() {
		return m_start;
	}
	public void setStart(String m_start) {
		this.m_start = m_start;
	}

	public String getTimeout() {
		return m_timeout;
	}
	public void setTimeout(String m_timeout) {
		this.m_timeout = m_timeout;
	}

	public String getSever() {
		return m_sever;
	}
	public void setSever(String m_sever) {
		this.m_sever = m_sever;
	}
	
	public String toString(){
		return m_targetId+"\n" + m_id + "\n" + m_jobCategory + "\n" + m_jobType+ "\n"+ m_submit + "\n" + m_start + "\n" + m_timeout + "\n" + m_sever;
	}
}
