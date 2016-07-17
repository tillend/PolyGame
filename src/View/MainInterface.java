package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MainInterface extends JFrame implements ActionListener {

	private JPanel pane = null;
	private CardLayout card = null;
	private JButton button_1 = null;
	private JPanel p1 = null, p2 = null, p3 = null; // 三个面板，分别显示欢迎界面，多边形界面，自定义数据界面
	private JLabel a1 = null, a2 = null, a3 = null; // 分别为，顶点数，顶点值，边符号3个标签
	private JTextField b1 = null, b2 = null, b3 = null; // 分别为顶点数、顶点值、边符号的输入框

	private JToolBar buttonpanel;     // 定义按钮面板
	JButton button[];                 // 定义按钮组
	private String tiptext[] = { "初始化", "撤销", "复盘", "最优解" };
	
	private String names[] = { "initialize", "undo", "again", "best" }; // 定义工具栏图标的名称
	// private Icon icons[];                //定义图象数组

	private DrawArea drawarea;             // 画布类的定义
	private JLabel startbar;               // 底部状态栏
	
	String strNode,strOpr;           //顶点数、顶点值、边符号
    String strN;
	

	public MainInterface(String str) {
		
		super("多边形游戏");
		// 菜单设置
		JMenu menuMode = new JMenu("游戏模式"); // 初始化菜单
		JMenu menuHelp = new JMenu("游戏帮助");

		JMenuItem itemRandom = new JMenuItem("随机模式");
		JMenuItem itemCustom = new JMenuItem("自定义模式");

		JMenuItem itemHelp = new JMenuItem("关于");

		menuMode.add(itemRandom); // 将菜单项添加到菜单下
		menuMode.add(itemCustom);

		menuHelp.add(itemHelp);

		JMenuBar menuBar = new JMenuBar(); // 初始化菜单栏
		menuBar.add(menuMode); // 增加菜单到菜单栏
		menuBar.add(menuHelp);

		setJMenuBar(menuBar); // 设置菜单

		// 注册菜单响应
		itemHelp.addActionListener(new ActionListener() { // 菜单事件处理
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(MainInterface.this,
						"本程序为多边形游戏，玩家可以使用随机模式和自定义模式"); // 显示软件信息
			}
		});

		// 工具栏的初始化
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);
		// icons = new ImageIcon[names.length];
		button = new JButton[names.length];
		for (int i = 0; i < names.length; i++) 
		{
			// icons[i] = new
			// ImageIcon(getClass().getResource("/icon/"+"pen.jpg"));  //获得图片（以类路径为基准）
			// button[i] = new JButton("", icons[i]);                  //创建工具条中的按钮
			button[i] = new JButton(tiptext[i]);                       // 创建工具条中的按钮
			button[i].setToolTipText(tiptext[i]);                      // 这里是鼠标移到相应的按钮上给出相应的提示
			buttonpanel.add(button[i]);
			button[i].setBackground(Color.green);
			button[i].addActionListener(this);
		}

		// 绘画区的初始化
		drawarea = new DrawArea(this);

		// 状态栏的初始化
		startbar = new JLabel(str);

		Container con = getContentPane();// 得到内容面板

		// 创建新卡片布局
		card = new CardLayout(5, 5);
		pane = new JPanel(card);
		button_1 = new JButton("确定");

		// 创建自定义数据输入的标签和文本框
		a1 = new JLabel("顶点数");
		a2 = new JLabel("顶点值");
		a3 = new JLabel("边符号");
		b1 = new JTextField(100);
		b2 = new JTextField(100);
		b3 = new JTextField(200);
		b1 = new JTextField(strN);
		b2 = new JTextField(strNode);
		b3 = new JTextField(strOpr);
		
		

		a1.setBounds(20, 40, 50, 30); // 设置各标签位置和大小
		b1.setBounds(80, 40, 200, 30);
		a2.setBounds(20, 80, 50, 30);
		b2.setBounds(80, 80, 200, 30);
		a3.setBounds(20, 120, 50, 30);
		b3.setBounds(80, 120, 200, 30);
		button_1.setBounds(50, 160, 80, 30);
		
		//初始化三个面板
		p1 = new JPanel(); // 欢迎界面
		p2 = new JPanel(); // 显示多边形
		p3 = new JPanel(); // 自定义数据

		p3.setLayout(null);

		p1.setBackground(Color.RED);
		p2.setBackground(Color.white);
		p3.setBackground(Color.GREEN);

		p1.add(new JLabel("欢迎界面"));
		p2.add(new JLabel("显示多边形"));
		p3.add(new JLabel("自定义数据"));

		p3.add(a1);       // 将各标签添加到自定义数据面板
		p3.add(b1); 
		p3.add(a2);
		p3.add(b2);
		p3.add(a3);
		p3.add(b3);
		p3.add(button_1);

		pane.add(p1, "p1");  //将面板加到卡片布局中
		pane.add(p2, "p2");
		pane.add(p3, "p3");

		// 翻转卡片布局动作

		button_1.addActionListener(new ActionListener() { // 输入数据，确定后，回到显示多边形的界面
			public void actionPerformed(ActionEvent e) {
				
				strN=b1.getText();         //获取文本框的值
				strNode=b2.getText();
				strOpr=b3.getText();
				
//				System.out.println(strN);
//				System.out.println(strNode);
//				System.out.println(strOpr);
				drawarea.initialize(strN,strNode,strOpr);
				card.show(pane, "p2");
			}
		});
		
		itemRandom.addActionListener(new ActionListener() { // 若使用随机模式，出现显示多边形的界面
					public void actionPerformed(ActionEvent e) {
						try {
							drawarea.initialize();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						card.show(pane, "p2");
					}
				});
		
		itemCustom.addActionListener(new ActionListener() { // 若采用自定义模式，出现自定义输入数据界面
					public void actionPerformed(ActionEvent e) {
						
						card.show(pane, "p3");
					}
				});

		con.add(pane);    //将卡片布局添加到容器中

		p2.setLayout(new BorderLayout());         //将对应的对象添加到面板p2，显示多边形的界面
		p2.add(buttonpanel, BorderLayout.NORTH);
		p2.add(drawarea, BorderLayout.CENTER);
		p2.add(startbar, BorderLayout.SOUTH);
		
		Toolkit tool = getToolkit();             // 得到一个Tolkit类的对象（主要用于得到屏幕的大小）
		Dimension dim = tool.getScreenSize();    // 得到屏幕的大小 （返回Dimension对象）
		setBounds(40, 40, dim.width - 270, dim.height - 100);     // 设置容器大小和坐标

		pane.setBounds(0, 31, dim.width - 270, dim.height - 100); // 设置卡片布局的坐标和大小。
	
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 设置状态栏显示的字符
	public void setStratBar(String s) {
		startbar.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button[0]) {
			// System.out.println("开始");
			try {
				drawarea.initialize();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		if (e.getSource() == button[1]) {
			// System.out.println("撤销");
			try {
				drawarea.undo();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			drawarea.gameEnd = false;
			repaint();
		}
		if (e.getSource() == button[2]) {
			drawarea.again();
			drawarea.gameEnd = false;
			repaint();
		}
		if (e.getSource() == button[3]) {
//			drawarea.best();
			DrawOptimalSolutionThread dosThread = new DrawOptimalSolutionThread();
			dosThread.start();
		}
	}
	
	class DrawOptimalSolutionThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			drawarea.optimalSolution();
		}
		
	}

}

