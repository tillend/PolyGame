package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

import Algorithm.PolygonGame3;
import Domain.Game;
import Domain.Line;
import Domain.Node;
import View.MainInterface;

public class DrawArea extends JPanel {

	private MainInterface mi = null;

	public int Ox;
	public int Oy;

	public int N = 0;
	public Game game = null;
	public Game copy = null;
	public MainInterface main;
	boolean gameEnd = false;

	public int index = 0;// 线的个数

	public DrawArea(MainInterface mi) {
		this.mi = mi;
		// 把鼠标设置成手形
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		setBackground(Color.white);// 设置绘制区的背景是白色
		addMouseListener(new MouseA());// 添加鼠标事件
		addMouseMotionListener(new MouseB());// 添加鼠标事件

		// initialize();
	}

	/**
	 * 撤销
	 */
	public void undo() throws CloneNotSupportedException {
		if (game != null) {
			game = game.regret(game.getMovement(), game.getCount(), copy);
		}
	}

	/**
	 * 重来
	 */
	public void again() {
		if (game != null) {
			game = game.replay(copy);
		}
	}
	/**
	 * 最优解
	 */
//	public void best() {
//		if (game != null) {
//			
//			game = game.highest_grade(deleteOrder, game.copy);
//		}
//		
//	}
	/* 响应最优解按钮，根据listline初始化算法需要的数据，并调用算法取得最优解
	 * 根据最优解模拟点击，播放点击事件
	*/
	public void optimalSolution() {
		this.gameEnd = false;
//		game.optimalSolution();
		this.game = (Game) game.replay(copy);
		PolygonGame3.init(game.getListline().size(), game.getListline());
		int ans = PolygonGame3.polyMax();
		System.out.println("最优成绩:"+ans);
		PolygonGame3.resolve();

		int deleteNum = 0;
		int[] deleteline = new int[N];
		repaint();
		
		for (int i = 0; i < PolygonGame3.order.length; i++) {
			for (int j = 0; j < PolygonGame3.order.length; j++) {
				if(i == PolygonGame3.order[j]){
					deleteline[i] = j;
				}
			}
		}
		
		for (int i = 1; i<PolygonGame3.order.length; i++) {
			deleteNum = deleteline[i-1];
//			deleteNum = (PolygonGame3.order[i] ) % N;
//			for (int j = 0; j < PolygonGame3.order.length; j++) {
//				if(i-1 == PolygonGame3.order[j]){
//					deleteNum = j - 1;
//				}
//			}
			System.out.println(deleteNum);
			try {
				Thread.sleep(1700);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (game.deleteLine(deleteNum)) {
				gameEnd = true;
				repaint();
			} else {
				repaint();
			}
		}
	}

	public void initialize() throws CloneNotSupportedException {   //随机模式初始化
		this.gameEnd = false;
		N = 4; // 点的数量
		game = new Game(N);
		// 按取值范围初始化点
		// 初始化node值
		game.setListnode(Node.initialize(N, -10, 10));
		// 初始化位置
		if (this.Ox == 0) {
			this.Ox = this.getWidth() / 2;
			this.Oy = this.getHeight() / 2;
		}
		System.out.println("原点坐标：" + Ox + " " + Oy);

		int r = Math.min(Ox, Oy) - 90;
		double angle = 0;
		double deltaAngle = 2 * Math.PI / N;
		int deltaX, deltaY;
		for (int i = 0; i < N; i++) {
			deltaX = (int) Math.round(Math.sin(angle) * r);
			deltaY = (int) Math.round(Math.cos(angle) * r);
			game.getListnode().get(i).setXpos(Ox + deltaX);
			game.getListnode().get(i).setYpos(Oy + deltaY);
			angle = angle + deltaAngle;
		}
		// 初始化line
		game.setListline(Line.initialize(N, game.getListnode()));
		copy = (Game) game.clone();

	}

	public void initialize(String strN, String strNode, String strOpr) { // 自定义模式初始化

		ArrayList<Node> listnode = new ArrayList<Node>(N);
		ArrayList<Line> listline = new ArrayList<Line>(N);
		int N = Integer.parseInt(strN);
		// System.out.println(strNode);
		String strNodeGrage[] = strNode.split(" ");  //获取顶点值
		Node[] nn = new Node[N];
		for (int i = 0; i < N; i++) {
			int nodeGrage1 = Integer.parseInt(strNodeGrage[i]);
			nn[i] = new Node(nodeGrage1);
			listnode.add(nn[i]);
		}

		game.setListnode(listnode);     //设置顶点值
		// 初始化位置
		if (this.Ox == 0) {
			this.Ox = this.getWidth() / 2;
			this.Oy = this.getHeight() / 2;
		}
		System.out.println("原点坐标：" + Ox + " " + Oy);

		int r = Math.min(Ox, Oy) - 90;
		double angle = 0;
		double deltaAngle = 2 * Math.PI / N;
		int deltaX, deltaY;
		for (int i = 0; i < N; i++) {
			deltaX = (int) Math.round(Math.sin(angle) * r);
			deltaY = (int) Math.round(Math.cos(angle) * r);
			game.getListnode().get(i).setXpos(Ox + deltaX);
			game.getListnode().get(i).setYpos(Oy + deltaY);
			angle = angle + deltaAngle;
		}
		
		String strLineOpr[] = strOpr.split(" ");   //获取边符号
		Line[] ll = new Line[N];
		for (int i = 0; i < N; i++) {
			ll[i] = new Line(nn[i], nn[(i + 1) % N], strLineOpr[i].charAt(0));
			listline.add(ll[i]);
		}
		//初始化line
		game.setListline(listline);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;// 定义随笔画
		if (game == null) {
			return;
		} else if (gameEnd) {
			// 游戏结束，打印出分数
			System.out.println("游戏得分" + game.getGrade());
			drawResult(g2d, new PrintResult(this.getWidth(), this.getHeight()));
		} else {
			for(int i = 0; i < game.getListline().size(); i++){
				if(!game.getListline().get(i).isFlag()){
					drawLine(g2d, game.getListline().get(i));
				}
			}
		}
	}

	public void drawResult(Graphics2D g2d, PrintResult i) {
		i.draw(g2d);
	}

	public void drawLine(Graphics2D g2d, Line i) {
		i.draw(g2d);// 将画笔传到个各类的子类中，用来完成各自的绘图
	}

	class MouseA extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 鼠标松开
			mi.setStratBar("鼠标松开在：[" + e.getX() + " ," + e.getY() + "]");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 鼠标按下
			mi.setStratBar("鼠标按下在：[" + e.getX() + " ," + e.getY() + "]");// 设置状态栏提示
			if (game.deleteLine(e.getX(), e.getY())) {
				gameEnd = true;
				repaint();
			} else {
				repaint();
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 鼠标进入
			mi.setStratBar("鼠标进入在：[" + e.getX() + " ," + e.getY() + "]");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 鼠标退出
			mi.setStratBar("鼠标退出在：[" + e.getX() + " ," + e.getY() + "]");
		}

	}

	class MouseB extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent e) {
			// TODO 鼠标移动
			mi.setStratBar("鼠标移动在：[" + e.getX() + " ," + e.getY() + "]");
		}

	}

	class PrintResult {
		// 画图需要
		int R = 23, G = 90, B = 123; // 定义色彩属性
		float stroke = 5.0f; // 定义线条粗细的属性
		int screenX, screenY;

		PrintResult(int width, int height) {
			screenX = width;
			screenY = height;
		}

		public void draw(Graphics2D g2d) {
			// 画直线
			g2d.setPaint(new Color(R, G, B));
			g2d.setFont(new Font("Times New Roman", 14, (int) stroke * 15));// 设置字体、
			g2d.drawString("your score:" + game.getGrade(), screenX / 4,
					screenY / 2);
		}
	}


}
