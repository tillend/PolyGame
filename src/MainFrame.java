import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class MainFrame extends JFrame implements ActionListener, MouseListener {

	private JPanel pane = null;
	private CardLayout card = null;
	private JButton button_1 = null;
	private JPanel p1 = null, p2 = null, p3 = null; // 三个面板，分别显示欢迎界面，多边形界面，自定义数据界面
	private JLabel a1 = null, a2 = null, a3 = null; // 分别为，顶点数，顶点值，边符号3个标签
	private JTextField b1 = null, b2 = null, b3 = null; // 分别为顶点数、顶点值、边符号的输入框
	private Container container; // 定义容器

	private JToolBar jmain; // 工具栏
	private JButton start; // 开始
	private JButton again; // 重做
	private JButton back; // 撤销
	private JButton best; // 最优解

	public MainFrame() {
		super("多边形游戏"); // 调用父类构造函数

		Container container = getContentPane(); // 得到容器
		container.setLayout(null);
	    
		//设置背景图片
		

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
				JOptionPane.showMessageDialog(MainFrame.this,
						"本程序为多边形游戏，玩家可以使用随机模式和自定义模式"); // 显示软件信息
			}
		});

		// 创建工具栏
		jmain = new JToolBar();
		start = new JButton("开始 ");
		start.setToolTipText("点击此按钮，开始游戏");
		back = new JButton("撤销");
		back.setToolTipText("返回到上一步");
		again = new JButton("重做 ");
		again.setToolTipText("回到起始位置，重新开始");
		best = new JButton("最优解");
		best.setToolTipText("演示此多边形的最优解");

		// 把组件添加到工具栏
		jmain.setLayout(new GridLayout(0, 4, 15, 15));
		jmain.add(start);
		jmain.add(back);
		jmain.add(again);
		jmain.add(best);
		jmain.setBounds(1, 1, 280, 30);
		container.add(jmain);

		// 注册按扭监听
		start.addActionListener((ActionListener) this);
		back.addActionListener((ActionListener) this);
		again.addActionListener((ActionListener) this);
		best.addActionListener((ActionListener) this);

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
		b1 = new JTextField("请输入顶点数");
		b2 = new JTextField("请输入对应的顶点值,空格隔开");
		b3 = new JTextField("请输入对应边的符号，空格隔开");

		a1.setBounds(20, 40, 50, 30);  //设置各标签位置和大小
		b1.setBounds(80, 40, 200, 30);
		a2.setBounds(20, 80, 50, 30);  
		b2.setBounds(80, 80, 200, 30);
		a3.setBounds(20, 120, 50, 30);  
		b3.setBounds(80, 120, 200, 30);
		button_1.setBounds(50,160,80,30);
		
		p1 = new JPanel(); // 欢迎界面              //初始化三个面板
		p2 = new JPanel(); // 显示多边形
		p3 = new JPanel(); // 自定义数据
		
		
		p3.setLayout(null);

		p1.setBackground(Color.RED);
		p2.setBackground(Color.white);
		p3.setBackground(Color.GREEN);

		p1.add(new JLabel("欢迎界面"));
		p2.add(new JLabel("显示多边形"));
		p3.add(new JLabel("自定义数据"));
		
		p3.add(a1); p3.add(b1);  //将各标签添加到自定义数据面板
		p3.add(a2); p3.add(b2);
		p3.add(a3); p3.add(b3);
		p3.add(button_1);
		
		pane.add(p1, "p1");
		pane.add(p2, "p2");
		pane.add(p3, "p3");

		// 翻转卡片布局动作

		button_1.addActionListener(new ActionListener() { // 输入数据，确定后，回到显示多边形的界面
			public void actionPerformed(ActionEvent e) {
				card.show(pane, "p2");
			}
		});
		itemRandom.addActionListener(new ActionListener() { // 若使用随机模式，出现显示多边形的界面
					public void actionPerformed(ActionEvent e) {
						card.show(pane, "p2");
					}
				});
		itemCustom.addActionListener(new ActionListener() { // 若采用自定义模式，出现自定义输入数据界面
					public void actionPerformed(ActionEvent e) {
						card.show(pane, "p3");
					}
				});
		pane.setBounds(0, 31, 800, 470); // 设置卡片布局的坐标和大小。
//		pane.setBackground(null);
//		pane.setOpaque(false);
		container.add(pane);

		setBounds(200, 100, 800, 500); // 设置容器的位置和大小
		
		//设置背景图片
//		JPanel panel = new JPanel() {
//			@Override
//			public void paint(Graphics g) {
//				Graphics2D g2 = (Graphics2D) g;
//				Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("ban2.jpg"));
//				g2.drawImage(img, 0, 0, this);
//			}
//		};
//		panel.setBounds(0,0,800,500);
//		container.add(panel);
		
		setResizable(false);// 设置不能改表窗囗大小
		setVisible(true); // 设置窗口可见
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

	}

    
	public static void main(String[] args) {
		new MainFrame();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}