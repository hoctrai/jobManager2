package com.tma.nht.controller;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.tma.nht.model.JobObject;
import com.tma.nht.model.JobState;
import com.tma.nht.resource.JobResource;
import com.tma.nht.view.JobManangerGUI;
import com.tma.nht.view.LogDialog;

public class JobController {
	public static final JobController jobController = new JobController();
	
	Map<String,String[]> mapTarget = new HashMap<>();
	private JobManangerGUI m_jobGui;

	private List<JobObject> m_jobs;

	private String[] categoryjobs;
	private String[] targets;
	
	public void setJobManager(JobManangerGUI jobManangerGUI) {
		m_jobGui = jobManangerGUI;
	}
	
	public JobManangerGUI getJobGui() {
		return m_jobGui;
	}
	
	/*--select Targer(Viewer)--*/
	public void selectionChange(SelectionEvent e) {
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
	public void selectionChangeType(String str) {
		if(str.equalsIgnoreCase("States")){
			String[] itemStatetes = { "All", "Planned", "Worked Pool", "Execution" };
			m_jobGui.getComboValue().setItems(itemStatetes);
			m_jobGui.getComboValue().setEnabled(true);
		}
		else if(str.equalsIgnoreCase("Catergoryjob")){
			m_jobGui.getTree().removeAll();
			List <String> categorys = new LinkedList<String>();
 			updateTreeCategory(categorys);
			
			m_jobGui.getComboValue().setItems((String[]) categoryjobs);
			m_jobGui.getComboValue().setEnabled(true);
			
		}else{
			try{
				m_jobGui.getTree().removeAll();
				m_jobGui.getComboValue().setItems((String[]) targets);
				
				
				
				updateTree();
			}catch(Exception ex){
				//new LogDialog(ex.getMessage());
			}
		}
	}

	private int checkCategory(String str) {
		for(int j = 0; j < m_jobGui.getTree().getItemCount(); j++){
			if(str.equals(m_jobGui.getTree().getItem(j).getText()))
				return j;
		}
		return -1;
		
	}

	/*--select Value (Filter)--*/
	public void selectionChangeValue(String strValue) {
		int iValue;
		if(JobResource.STATE_COMBO_VALUE.contains(strValue)){
			JobState state =JobState.valueOf(strValue.trim());
			filterState(state);
			
		}
		
		else {
			try {
				getJobs(strValue);
				
				
			} catch (Exception e) {
				// TODO: handle exception
				filterCategory(strValue);
			}
			
		}
	}
	
	private void filterCategory(String strValue) {
		m_jobGui.getTree().removeAll();
		updateTree(strValue);
	}

	private void filterState(JobState state) {
		m_jobGui.getTree().removeAll();
		
		if(state.getState().equals("All")){
			JobState[] States = {state.Planned, state.WorkedPool, state.Execution};
			for(int i = 0; i < 3; i++){
				updateTree(States[i].getState());
			}
		}
		else{
			updateTree(state.getState());
		}
	}

	private void updateTree(String str) {
		String target="-1";
		TreeItem treeState = new TreeItem(m_jobGui.getTree(), SWT.NONE);
		treeState.setText(str);
		
		for(int i = 0; i < m_jobs.size(); i++){
			if(m_jobs.get(i).getState()[0].equalsIgnoreCase(str)||m_jobs.get(i).getJobCategory().equalsIgnoreCase(str)){
				target = "Target: " + m_jobs.get(i).getTargetId();
				TreeItem item = new TreeItem(treeState, SWT.NONE);
				item.setText(target);
			
			}
		}
	}

	/*--open File (Menu) --*/
	public void readFile(){
		FileDialog dialog = new FileDialog(m_jobGui.getParent().getShell());
		String path = dialog.open();
		File file = new File(path);
		 int index = (int) (file.length()/2580000);
         int index2 = 100/index;
		
		FOA foa = new FOA(path);
		m_jobs = foa.getJobs();
		
		/*
		 *  progressBar
		 */
		
		m_jobGui.getTree().removeAll();
		categoryjobs = (String[]) foa.getCategoryjob().toArray(new String[foa.getCategoryjob().size()]); 
		targets = (String[]) foa.getStrTargets().toArray(new String[foa.getStrTargets().size()]);
		
		LogDialog logDialog = new LogDialog();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				logDialog.createAndShowProgress(logDialog, index2);
			}
		});
		updateTree();
		
        /*
         * end progressbar
         */
        
		
	}
	
	/*--update treeView--*/
	public void updateTree(){
		
		String target="-1";
		for(int i = 0; i < m_jobs.size(); i++){
				if(Integer.parseInt(target) != (m_jobs.get(i).getTargetId())){
				
				target = ""+m_jobs.get(i).getTargetId();
				TreeItem item = new TreeItem(m_jobGui.getTree(), SWT.NONE);
				item.setText("Target: "+target);
			}
		}
	}
	
	public void updateTreeCategory(List<String> categorys){
		String target = "-1";
		for(int i = 0; i < m_jobs.size(); i++){
			int j = checkCategory(m_jobs.get(i).getJobCategory());
			
			if (i == 0 || j == -1) {
				String category = m_jobs.get(i).getJobCategory();
				TreeItem item = new TreeItem(m_jobGui.getTree(), SWT.NONE);
				item.setText(category);
				
				target = "" + m_jobs.get(i).getTargetId();
				TreeItem item1 = new TreeItem(item, SWT.NONE);
				item1.setText("Target: " + target);
				
			} else if(Integer.parseInt(target) != (m_jobs.get(i).getTargetId())){
				target = ""+m_jobs.get(i).getTargetId();
				TreeItem item = new TreeItem(m_jobGui.getTree().getItem(j), SWT.NONE);
				item.setText("Target: " + target);
			}
		}
	}

//	public void updateTreeCategory(List<String> categorys){
//		String target = "-1";
//		for(int i = 0; i < m_jobs.size(); i++){
//			int j = checkCategory(m_jobs.get(i).getJobCategory());
//			
//			if (i == 0 || j == -1) {
//				String category = m_jobs.get(i).getJobCategory();
//				TreeItem item = new TreeItem(m_jobGui.getTree(), SWT.NONE);
//				item.setText(category);
//				
//				target = "" + m_jobs.get(i).getTargetId();
//				TreeItem item1 = new TreeItem(item, SWT.NONE);
//				item1.setText("Target: " + target);
//				
//			} else if(Integer.parseInt(target) != (m_jobs.get(i).getTargetId())){
//				target = ""+m_jobs.get(i).getTargetId();
//				TreeItem item = new TreeItem(m_jobGui.getTree().getItem(j), SWT.NONE);
//				item.setText("Target: " + target);
//			}
//		}
//	}
	
	public void exit() {
		if(MessageDialog.openConfirm(m_jobGui.getParent().getShell(), 
				"Confirmation", "Do you want to exit")){
			System.exit(0);
		}
	}

	/*--select TableItem--*/
	public void getId(Event e) {
		Point pt = new Point(e.x, e.y);
		TableItem item = m_jobGui.getTable().getItem(pt);
		if(item == null)
			return;
		
		for(int i = 0; i< m_jobGui.getTable().getColumnCount(); i++){
			Rectangle rect = item.getBounds(i);
			
			if(rect.contains(pt)){
				//int index = m_jobGui.getTable().indexOf(item);
				long idJob = Long.parseLong(item.getText(0));
				search(idJob);
			}
		}
	}

	private void search(long idJob) {
		for(int i = 0; i < m_jobs.size(); i++){
			if(idJob == m_jobs.get(i).getId()){
				m_jobGui.setTxtDetail(m_jobs.get(i).toString());
				break;
			}
		}
	}
}
