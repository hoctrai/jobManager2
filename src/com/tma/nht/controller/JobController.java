package com.tma.nht.controller;

import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.internal.win32.OPENFILENAME;
import org.eclipse.swt.widgets.FileDialog;

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
	
	/*--select Targer(Viewer)--*/
	public void selectionChange(SelectionChangedEvent e) {
		
	}
	
	/*--- selection Type (Filter)--*/
	public void selectionChangeType(SelectionEvent e) {
		if(m_jobGui.getComboType().getText().equalsIgnoreCase("States")){
			
			String [] itemStatetes = {"All", "Planned", "Worked Pool", "Execution"};
			m_jobGui.getComboValue().setItems(itemStatetes);
			m_jobGui.getComboValue().setEnabled(true);
			
		}
	}

	/*--select Value (Filter)--*/
	public void selectionChangeValue(SelectionEvent e) {
		
		
	}
	/*--open File (Menu) --*/
	public void loadViewer() {
		FileDialog dialog = new FileDialog(m_jobGui.getParent().getShell());
		String path = dialog.open();
		
		
	}
	
}
