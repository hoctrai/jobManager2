package com.tma.nht.view;

import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;

import org.eclipse.jface.dialogs.MessageDialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;

public class LogDialog extends JPanel implements ActionListener,
PropertyChangeListener{
	//public static final LogDialog logDialog = new LogDialog();
	
	JProgressBar pbar;
	LogDialog it;
	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;

	JobManangerGUI m_jobsGui;
	
//	public LogDialog() {
//		pbar = new JProgressBar();
//	    pbar.setMinimum(MY_MINIMUM);
//	    pbar.setMaximum(MY_MAXIMUM);
//	    add(pbar);
//	    JFrame frame = new JFrame("Progress Bar Example");
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.setContentPane(this);
//	    frame.pack();
//	    frame.setVisible(true);
//
//	    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
//	      final int percent = i;
//	      try {
//	        SwingUtilities.invokeLater(new Runnable() {
//	          public void run() {
//	            updateBar(percent);
//	          }
//	        });
//	        Thread.sleep(100);
//	      } catch (InterruptedException e) {}
//	    }
//	}

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
	    private JButton startButton;
	    private JTextArea taskOutput;
	    private Task task;
	 
	    class Task extends SwingWorker<Void, Void> {
	        @Override
	        public Void doInBackground() {
	            Random random = new Random();
	            int progress = 0;
	            setProgress(0);
	            try {
	                Thread.sleep(1000);
	                while (progress < 100 && !isCancelled()) {
	                    //Sleep for up to one second.
	                    Thread.sleep(random.nextInt(1000));
	                    //Make random progress.
	                    progress += random.nextInt(10);
	                    setProgress(Math.min(progress, 100));
	                }
	            } catch (InterruptedException ignore) {}
	            return null;
	        }
	 
	        @Override
	        public void done() {
	            Toolkit.getDefaultToolkit().beep();
//	            startButton.setEnabled(true);
	            progressMonitor.setProgress(0);
	        }
	    }
	 
	    public LogDialog() {
	        super(new BorderLayout());
	 
	        //Create the demo's UI.
//	        startButton = new JButton("Start");
//	        startButton.setActionCommand("start");
//	        startButton.addActionListener(this);
//	 
//	        taskOutput = new JTextArea(5, 20);
//	        taskOutput.setMargin(new Insets(5,5,5,5));
//	        taskOutput.setEditable(false);
//	 
//	        add(startButton, BorderLayout.PAGE_START);
//	        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
//	        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	        progressMonitor = new ProgressMonitor(LogDialog.this,
                    "Running a Long Task",
                    "", 0, 100);
			progressMonitor.setProgress(0);
			task = new Task();
			task.addPropertyChangeListener(this);
			task.execute();
	 
	    }
	 
	    /**
	     * Invoked when the user presses the start button.
	     */
	    public void actionPerformed(ActionEvent evt) {
	        
//	        startButton.setEnabled(false);
	    }
	 
	    /**
	     * Invoked when task's progress property changes.
	     */
	    public void propertyChange(PropertyChangeEvent evt) {
	        if ("progress" == evt.getPropertyName() ) {
	            int progress = (Integer) evt.getNewValue();
	            progressMonitor.setProgress(progress);
	            String message =
	                String.format("Completed %d%%.\n", progress);
	            progressMonitor.setNote(message);
	            //taskOutput.append(message);
//	            if (progressMonitor.isCanceled() || task.isDone()) {
//	                Toolkit.getDefaultToolkit().beep();
//	                if (progressMonitor.isCanceled()) {
//	                    task.cancel(true);
//	                    taskOutput.append("Task canceled.\n");
//	                } else {
//	                    taskOutput.append("Task completed.\n");
//	                }
//	                startButton.setEnabled(true);
//	            }
	        }
	 
	    }
	 
	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    public static void createAndShowGUI(LogDialog dialog) {
	        //Create and set up the window.
	        JFrame frame = new JFrame("ProgressMonitorDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Create and set up the content pane.
	        JComponent newContentPane = dialog;
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);
	 
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
}
