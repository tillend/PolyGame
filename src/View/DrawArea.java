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

	public int index = 0;// �ߵĸ���

	public DrawArea(MainInterface mi) {
		this.mi = mi;
		// ��������ó�����
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		setBackground(Color.white);// ���û������ı����ǰ�ɫ
		addMouseListener(new MouseA());// �������¼�
		addMouseMotionListener(new MouseB());// �������¼�

		// initialize();
	}

	/**
	 * ����
	 */
	public void undo() throws CloneNotSupportedException {
		if (game != null) {
			game = game.regret(game.getMovement(), game.getCount(), copy);
		}
	}

	/**
	 * ����
	 */
	public void again() {
		if (game != null) {
			game = game.replay(copy);
		}
	}
	/**
	 * ���Ž�
	 */
//	public void best() {
//		if (game != null) {
//			
//			game = game.highest_grade(deleteOrder, game.copy);
//		}
//		
//	}
	/* ��Ӧ���Žⰴť������listline��ʼ���㷨��Ҫ�����ݣ��������㷨ȡ�����Ž�
	 * �������Ž�ģ���������ŵ���¼�
	*/
	public void optimalSolution() {
		this.gameEnd = false;
//		game.optimalSolution();
		this.game = (Game) game.replay(copy);
		PolygonGame3.init(game.getListline().size(), game.getListline());
		int ans = PolygonGame3.polyMax();
		System.out.println("���ųɼ�:"+ans);
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

	public void initialize() throws CloneNotSupportedException {   //���ģʽ��ʼ��
		this.gameEnd = false;
		N = 4; // �������
		game = new Game(N);
		// ��ȡֵ��Χ��ʼ����
		// ��ʼ��nodeֵ
		game.setListnode(Node.initialize(N, -10, 10));
		// ��ʼ��λ��
		if (this.Ox == 0) {
			this.Ox = this.getWidth() / 2;
			this.Oy = this.getHeight() / 2;
		}
		System.out.println("ԭ�����꣺" + Ox + " " + Oy);

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
		// ��ʼ��line
		game.setListline(Line.initialize(N, game.getListnode()));
		copy = (Game) game.clone();

	}

	public void initialize(String strN, String strNode, String strOpr) { // �Զ���ģʽ��ʼ��

		ArrayList<Node> listnode = new ArrayList<Node>(N);
		ArrayList<Line> listline = new ArrayList<Line>(N);
		int N = Integer.parseInt(strN);
		// System.out.println(strNode);
		String strNodeGrage[] = strNode.split(" ");  //��ȡ����ֵ
		Node[] nn = new Node[N];
		for (int i = 0; i < N; i++) {
			int nodeGrage1 = Integer.parseInt(strNodeGrage[i]);
			nn[i] = new Node(nodeGrage1);
			listnode.add(nn[i]);
		}

		game.setListnode(listnode);     //���ö���ֵ
		// ��ʼ��λ��
		if (this.Ox == 0) {
			this.Ox = this.getWidth() / 2;
			this.Oy = this.getHeight() / 2;
		}
		System.out.println("ԭ�����꣺" + Ox + " " + Oy);

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
		
		String strLineOpr[] = strOpr.split(" ");   //��ȡ�߷���
		Line[] ll = new Line[N];
		for (int i = 0; i < N; i++) {
			ll[i] = new Line(nn[i], nn[(i + 1) % N], strLineOpr[i].charAt(0));
			listline.add(ll[i]);
		}
		//��ʼ��line
		game.setListline(listline);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;// ������ʻ�
		if (game == null) {
			return;
		} else if (gameEnd) {
			// ��Ϸ��������ӡ������
			System.out.println("��Ϸ�÷�" + game.getGrade());
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
		i.draw(g2d);// �����ʴ���������������У�������ɸ��ԵĻ�ͼ
	}

	class MouseA extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO ����ɿ�
			mi.setStratBar("����ɿ��ڣ�[" + e.getX() + " ," + e.getY() + "]");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO ��갴��
			mi.setStratBar("��갴���ڣ�[" + e.getX() + " ," + e.getY() + "]");// ����״̬����ʾ
			if (game.deleteLine(e.getX(), e.getY())) {
				gameEnd = true;
				repaint();
			} else {
				repaint();
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO ������
			mi.setStratBar("�������ڣ�[" + e.getX() + " ," + e.getY() + "]");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO ����˳�
			mi.setStratBar("����˳��ڣ�[" + e.getX() + " ," + e.getY() + "]");
		}

	}

	class MouseB extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent e) {
			// TODO ����ƶ�
			mi.setStratBar("����ƶ��ڣ�[" + e.getX() + " ," + e.getY() + "]");
		}

	}

	class PrintResult {
		// ��ͼ��Ҫ
		int R = 23, G = 90, B = 123; // ����ɫ������
		float stroke = 5.0f; // ����������ϸ������
		int screenX, screenY;

		PrintResult(int width, int height) {
			screenX = width;
			screenY = height;
		}

		public void draw(Graphics2D g2d) {
			// ��ֱ��
			g2d.setPaint(new Color(R, G, B));
			g2d.setFont(new Font("Times New Roman", 14, (int) stroke * 15));// �������塢
			g2d.drawString("your score:" + game.getGrade(), screenX / 4,
					screenY / 2);
		}
	}


}
