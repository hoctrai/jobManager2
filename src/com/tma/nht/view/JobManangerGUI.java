package com.tma.nht.view;


import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.tma.nht.controller.JobController;

public class JobManangerGUI {
	
	private Composite m_parent;
	
	private Menu m_menu;
	private Combo m_comboType;
	private Combo m_comboValue;
	private TreeViewer m_viewer;
	private Table m_table;

	private Text m_txtDetail;

	public static void main(String[] args) {
		JobManangerGUI gui = new JobManangerGUI();
	}
	
	public JobManangerGUI() {
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(1000, 540);
		shell.setLayout(new GridLayout(1, false));
		
		initMenu(shell);
		
		m_parent = new Composite(shell, SWT.NONE);
		m_parent.setLayout(new GridLayout(2, false));
		m_parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		initial(shell);
		
		JobController.jobController.setJobManager(this);
		
		shell.open();
		while (!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}

	private void initial(Shell shell) {
		initTop();
		initBottom();
		addListeners();
	}
	
	/*---call event ---*/
	private void addListeners() {
		/*--Viewer--*/
		m_viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				JobController.jobController.selectionChange(e);
			}
		});
		/*--end--*/
		
		/*--filter--*/
		m_comboType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				JobController.jobController.selectionChangeType(e);
			}
		});
		
		m_comboValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				JobController.jobController.selectionChangeValue(e);
			}
		});
		/*--end--*/
	}


	private void initBottom() {
		Composite bottom = new Composite(m_parent, SWT.NONE);
		bottom.setLayout(new GridLayout(2,false));

		GridData gd_bottom = new GridData(GridData.FILL_BOTH);
		gd_bottom.horizontalSpan = 2;
		bottom.setLayoutData(gd_bottom);
		
		Group grpLeft = new Group(bottom, SWT.NONE);
		grpLeft.setText("List Target");
		grpLeft.setLayout(new GridLayout(1, false));
		grpLeft.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		m_viewer = new TreeViewer(grpLeft, SWT.BORDER);
		Tree tree = m_viewer.getTree();
		GridData gd_treeView = new GridData(GridData.FILL_VERTICAL);
		gd_treeView.widthHint=150;
		tree.setLayoutData(gd_treeView);
		
		Group grpRight = new Group(bottom, SWT.NONE);
		grpRight.setText("Description");
		grpRight.setLayout(new GridLayout(1, false));
		grpRight.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		m_table = new Table(grpRight, SWT.NONE);
		m_table.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		m_txtDetail = new Text(grpRight, SWT.BORDER | SWT.MULTI);
		m_txtDetail.setLayoutData(new GridData(GridData.FILL_BOTH));
		
	}
	/*---get Filter---*/
	private void initTop() {
		GridData gd_top = new GridData(GridData.FILL_HORIZONTAL);
		gd_top.horizontalSpan = 2;
		gd_top.widthHint = 827;
		gd_top.heightHint = 39;
		
		Group top = new Group(m_parent, SWT.NONE);
		GridLayout gl_top = new GridLayout(5, false);
		gl_top.horizontalSpacing = 10;
		top.setLayout(gl_top);
		top.setLayoutData(gd_top);
		top.setText("Filter");
		
		Label lblStyle = new Label(top,SWT.NONE);
		lblStyle.setText("Style: ");
		
		GridData gd_comboType = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboType.widthHint = 103;
		m_comboType = new Combo(top, SWT.NONE);
		m_comboType.setLayoutData(gd_comboType);
		String[] itemsType = {"Target: ", "Catergoryjob: ", "States: "};
		m_comboType.setItems(itemsType);
		
		
//		Button rdbTarget = new Button(top, SWT.RADIO);
//		rdbTarget.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		rdbTarget.setText("Target");
//		
//		Button rdbCatergoryjob = new Button(top, SWT.RADIO);
//		rdbCatergoryjob.setText("Catergoryjob");
//		
//		Button rdbStates = new Button(top, SWT.RADIO);
//		rdbStates.setText("States");
		
		Label lblValue = new Label(top, SWT.NONE);
		lblValue.setText("Value: ");
		
		GridData gd_comboValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboValue.widthHint = 103;
		m_comboValue = new Combo(top, SWT.NONE);
		m_comboValue.setLayoutData(gd_comboValue);
		m_comboValue.setEnabled(false);
		new Label(top, SWT.NONE);
		new Label(top, SWT.NONE);
		new Label(top, SWT.NONE);
		new Label(top, SWT.NONE);
		new Label(top, SWT.NONE);
	}
	/*---end--*/
	
	/*--create Menu---*/
	private void initMenu(Shell shell){
		m_menu = new Menu(shell,SWT.BAR);
		
		final MenuItem file = new MenuItem(m_menu, SWT.CASCADE);
		file.setText("&File");
		
		final Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		file.setMenu(fileMenu);
		
		final MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("Open");
		
		final MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit");
		
		final MenuItem window = new MenuItem(m_menu, SWT.CASCADE);
		window.setText("Window");
		
		final Menu windowMenu = new Menu(shell,SWT.DROP_DOWN);
		window.setMenu(windowMenu);
		
		final MenuItem maxItem = new MenuItem(windowMenu, SWT.PUSH);
		maxItem.setText("Maximize");
		
		final MenuItem minItem = new MenuItem(windowMenu, SWT.PUSH);
		minItem.setText("Minimize");
		
		exitItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		openItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				JobController.jobController.loadViewer();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		shell.setMenuBar(m_menu);
	}
	/*---end--*/

	public Composite getParent(){
		return m_parent;
		
	}
	
	public Combo getComboType() {
		return m_comboType;
	}
	public void setComboType(Combo m_comboType) {
		this.m_comboType = m_comboType;
	}

	public Combo getComboValue() {
		return m_comboValue;
	}
	public void setComboValue(Combo m_comboValue) {
		this.m_comboValue = m_comboValue;
	}

	public TreeViewer getViewer() {
		return m_viewer;
	}
	public void setViewer(TreeViewer m_viewer) {
		this.m_viewer = m_viewer;
	}

	public Table getTable() {
		return m_table;
	}
	public void setTable(Table m_table) {
		this.m_table = m_table;
	}

	public Menu getMenu() {
		return m_menu;
	}
	public void setMenu(Menu menu) {
		this.m_menu = menu;
	}
	
	
	
}
