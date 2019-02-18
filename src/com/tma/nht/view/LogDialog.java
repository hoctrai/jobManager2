package com.tma.nht.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import org.eclipse.jface.dialogs.MessageDialog;

public class LogDialog extends JPanel 
	implements ActionListener,PropertyChangeListener{
	//public static final LogDialog logDialog = new LogDialog();
	int m_index;
	JProgressBar pbar;
	LogDialog it;
	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;

	JobManangerGUI m_jobsGui;
	
	public LogDialog(String str) {
		if(MessageDialog.openConfirm(m_jobsGui.getParent().getShell(), 
				"Log", str)){
			System.exit(0);
		}
	}
	
	public void updateBar(int newValue) {
		pbar.setValue(newValue);
	}
	
	public void  setIt(LogDialog it) {
		this.it = it;
	}
	
	private ProgressMonitor progressMonitor;
		private Task task;
		
		class Task extends SwingWorker<Void, Void> {
			@Override
			public Void doInBackground() {
				int progress = 0;
				setProgress(0);
	            try {
					Thread.sleep(1000);
					while (progress < 100 && !isCancelled()) {
						//Sleep for up to one second.
						
						//Make random progress.
						progress += m_index;
						setProgress(Math.min(progress, 100));
						Thread.sleep(1000);
	                }
	            } catch (InterruptedException ignore) {}
	            
	            return null;
	        }
	 
			@Override
	        public void done() {
				Toolkit.getDefaultToolkit().beep();
				progressMonitor.close();
	        }
	    }
	 
	    public LogDialog() {
	        super(new BorderLayout());
	        progressMonitor = new ProgressMonitor(LogDialog.this,
                    "push data to GUI",
                    "", 0, 100);
			progressMonitor.setProgress(0);
			
			task = new Task();
			task.addPropertyChangeListener(this);
			task.execute();
			
			progressMonitor.close();
	    }
	 
	    /**
	     * Invoked when the user presses the start button.
	     */
	    @Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	    
	    public void propertyChange(PropertyChangeEvent evt) {
	    	if ("progress" == evt.getPropertyName() ) {
	        	int progress = (Integer) evt.getNewValue();
	            progressMonitor.setProgress(progress);
	        }
	 
	    }
	    
	    public void createAndShowProgress(LogDialog dialog, int i) {
	    	m_index = i;
	    	//Create and set up the content pane.
	        JComponent newContentPane = dialog;
	        newContentPane.setOpaque(true); //content panes must be opaque
	    }
}

		
