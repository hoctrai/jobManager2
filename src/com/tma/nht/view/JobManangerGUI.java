package com.tma.nht.view;


import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

import com.tma.nht.controller.JobController;

public class JobManangerGUI {
	
	private Composite m_parent;
	
	private Combo m_comboType;
	private Combo m_comboValue;
	private TreeViewer m_viewer;
	private Table m_table;
	
	public static void main(String[] args) {
		JobManangerGUI gui = new JobManangerGUI();
	}
	
	public JobManangerGUI() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(861, 540);
		shell.setLayout(new GridLayout(1, false));
		
		initMenu(shell);
		
		m_parent = new Composite(shell, SWT.NONE);
		m_parent.setLayout(new GridLayout(2, false));
		m_parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		initial(shell);
		
		shell.open();
		while (!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		JobController.jobController.setJobManager(this);
	}

	private void initial(Shell shell) {
		initTop();
		initBottom();
		addListeners();
	}

	private void addListeners() {
		m_viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				JobController.jobController.selectionChange(event);
			}
		});
	}

	private void initBottom() {
		Composite bottom = new Composite(m_parent, SWT.NONE);
		bottom.setLayout(new GridLayout(2,false));

		GridData gd_bottom = new GridData(GridData.FILL_BOTH);
		gd_bottom.horizontalSpan = 2;
		bottom.setLayoutData(gd_bottom);
				
		m_viewer = new TreeViewer(bottom, SWT.NONE);
		Tree tree = m_viewer.getTree();
		GridData gd_treeView = new GridData(GridData.FILL_VERTICAL);
		gd_treeView.widthHint=150;
		tree.setLayoutData(gd_treeView);
		
		m_table = new Table(bottom, SWT.NONE);
		m_table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	}

	private void initTop() {
		GridData gd_top = new GridData(GridData.FILL_HORIZONTAL);
		gd_top.horizontalSpan = 2;
		gd_top.widthHint = 827;
		gd_top.heightHint = 39;
		
		Composite top = new Composite(m_parent, SWT.NONE);
		GridLayout gl_top = new GridLayout(5, false);
		gl_top.horizontalSpacing = 10;
		top.setLayout(gl_top);
		top.setLayoutData(gd_top);
		
		Label lblStyle = new Label(top,SWT.NONE);
		lblStyle.setText("Style: ");
		
		
		GridData gd_comboType = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboType.widthHint = 103;
		m_comboType = new Combo(top, SWT.NONE);
		m_comboType.setLayoutData(gd_comboType);
		String[] itemsType = {"Target: ", "Catergoryjob: ", "States: "};
		m_comboType.setItems(itemsType);
		
		Label lblValue = new Label(top, SWT.NONE);
		lblValue.setText("Value: ");
		
		GridData gd_comboValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboValue.widthHint = 103;
		m_comboValue = new Combo(top, SWT.NONE);
		m_comboValue.setLayoutData(gd_comboValue);
		m_comboValue.setEnabled(false);
		new Label(top, SWT.NONE);
		
	}
	private void initMenu(Shell shell){
		final Menu menu = new Menu(shell,SWT.BAR);
		
		final MenuItem file = new MenuItem(menu, SWT.CASCADE);
		file.setText("&File");
		
		final Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		file.setMenu(fileMenu);
		
		final MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("Open");
		
		final MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit");
		
		final MenuItem window = new MenuItem(menu, SWT.CASCADE);
		window.setText("Window");
		
		final Menu windowMenu = new Menu(shell,SWT.DROP_DOWN);
		window.setMenu(windowMenu);
		
		final MenuItem maxItem = new MenuItem(windowMenu, SWT.PUSH);
		maxItem.setText("Maximize");
		
		final MenuItem minItem = new MenuItem(windowMenu, SWT.PUSH);
		minItem.setText("Minimize");
		
		shell.setMenuBar(menu);
	}
}
