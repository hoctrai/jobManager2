package com.tma.nht.controller;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class JobSelectionAdapter implements SelectionListener{
	
	@Override
	public void widgetSelected(SelectionEvent e){
		String stre = e.getSource().toString();
		if(e.getSource().toString().contains("Button")){
	 		String str = ((Button)e.widget).getText();
			JobController.jobController.selectionChangeType(str);
		
		}else{
			String str = ((Combo)e.widget).getText().replaceAll(" ", "");
			JobController.jobController.selectionChangeValue(str);
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
