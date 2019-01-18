package com.tma.nht.controller;

import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.tma.nht.view.JobManangerGUI;

public class JobController {
	public static final JobController jobController = new JobController();
	private JobManangerGUI m_jobGui;

	public void setJobManager(JobManangerGUI jobManangerGUI) {
		m_jobGui = jobManangerGUI;
	}
	
	public JobManangerGUI getJobGui() {
		return m_jobGui;
	}

	public void selectionChange(SelectionChangedEvent event) {
		
	}
}
