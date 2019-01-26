package com.tma.nht.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tma.nht.model.JobObject;
import com.tma.nht.model.JobState;
import com.tma.nht.view.JobManangerGUI;

public class JobController {
	public static final JobController jobController = new JobController();
	
	Map<String,String[]> mapTarget = new HashMap<>();
	private JobManangerGUI m_jobGui;

	private List<JobObject> m_jobs;
	
	public void setJobManager(JobManangerGUI jobManangerGUI) {
		m_jobGui = jobManangerGUI;
	}
	
	public JobManangerGUI getJobGui() {
		return m_jobGui;
	}
	
	/*--select Targer(Viewer)--*/
	public void selectionChange(Event e) {
		String strTarget = ((TreeItem) e.item).getText();
		updateTable();
		getJobs(strTarget);
	}
	
	private void getJobs(String strTarget) {
		int j = 0;
		for(int i = 0; i < m_jobs.size(); i++){
			JobObject job = m_jobs.get(i);
			
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(strTarget);
			int targetId = 0;
			if(m.find())
				targetId = Integer.parseInt(m.group());
			
			if(targetId == job.getTargetId()){
				try{
					m_jobGui.getItems().get(j).setText(new String[] {Long.toString(job.getId()), 
							job.getJobCategory(),job.getJobType(), job.getSubmit(),
							job.getStart(), job.getTimeout(), job.getSever()});
					
				}catch (Exception e) {
					m_jobGui.getItems().add(m_jobGui.createItemTable());
					m_jobGui.getItems().get(j).setText(new String[] {Long.toString(job.getId()), 
							job.getJobCategory(),job.getJobType(), job.getSubmit(),
							job.getStart(), job.getTimeout(), job.getSever()});
					
				}
				j++;
			}
		}
	}
	
	private void updateTable() {
		List<TableItem> item = m_jobGui.getItems();
		for(int i = 0; i < item.size(); i++){
			item.get(i).setText(new String[]{"","","","","","",""});
		}
	}

	/*--- selection Type (Filter)--*/
	public void selectionChangeType(SelectionEvent e) {
		if(m_jobGui.getComboType().getText().equalsIgnoreCase("States:")){
			String [] itemStatetes = {"All", "Planned", "Worked Pool", "Execution"};
			m_jobGui.getComboValue().setItems(itemStatetes);
			m_jobGui.getComboValue().setEnabled(true);
		
		}else if(m_jobGui.getComboType().getText().equalsIgnoreCase("Catergoryjob:")){
			
		}else{
			
		}
	}

	/*--select Value (Filter)--*/
	public void selectionChangeValue(SelectionEvent e) {
		String strValue = m_jobGui.getComboValue().getText().replace(" ","");
		System.out.println(strValue);
		JobState state =JobState.valueOf(strValue.trim());//Worker Pool faile becau 
		System.out.println(state);
		
		filterState(state);
		
		
	}
	
	private void filterState(JobState state) {
		//for(int i = m_jobGui.getTree().getItems().length - 1; i > 0 ; i--){
			m_jobGui.getTree().removeAll();
			
		//}
		String target="-1";
		for(int i = 0; i < m_jobs.size(); i++){
				if(m_jobs.get(i).getState()[0].equalsIgnoreCase(state.getState())){
				
				target = "Target: "+m_jobs.get(i).getTargetId();
				TreeItem item = new TreeItem(m_jobGui.getTree(), SWT.NONE);
				item.setText(target);
			}
		}
	}

	/*--open File (Menu) --*/
	public void readFile(){
		FileDialog dialog = new FileDialog(m_jobGui.getParent().getShell());
		String path = dialog.open();
		FOA foa = new FOA(path);
		m_jobs = foa.getJobs();
		loadTree();
	}
	public void loadTree(){
		
		String target="-1";
		
		for(int i = 0; i < m_jobs.size(); i++){

				if(Integer.parseInt(target) != (m_jobs.get(i).getTargetId())){
				
				target = ""+m_jobs.get(i).getTargetId();
				TreeItem item = new TreeItem(m_jobGui.getTree(), SWT.NONE);
				item.setText("Target: "+target);
			}
		}
	}
}
