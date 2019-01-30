package com.tma.nht.controller;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

public class JobSelectionAdapter implements SelectionListener{
	
	@Override
	public void widgetSelected(SelectionEvent e){
 		String str = ((Button)e.widget).getText();
		JobController.jobController.selectionChangeType(str);
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
