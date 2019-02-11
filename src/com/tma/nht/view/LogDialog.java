package com.tma.nht.view;

import java.io.FileInputStream;

import javax.swing.ProgressMonitorInputStream;

import org.eclipse.jface.dialogs.MessageDialog;

public class LogDialog {
	public static final LogDialog logDialog = new LogDialog();

	JobManangerGUI m_jobsGui;
	
	public LogDialog() {
		
	}

	public LogDialog(String str) {
		if(MessageDialog.openConfirm(m_jobsGui.getParent().getShell(), 
				"Log", str)){
			System.exit(0);
		}
	}
	
	public void progressBar(FileInputStream in) {
		ProgressMonitorInputStream pm = 
                new ProgressMonitorInputStream(null, "loading file", in);
	}
	
	public void setJobManager(JobManangerGUI Gui) {
		m_jobsGui = Gui;
	}
	
}
