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
	private JPanel p1 = null, p2 = null, p3 = null; // ������壬�ֱ���ʾ��ӭ���棬����ν��棬�Զ������ݽ���
	private JLabel a1 = null, a2 = null, a3 = null; // �ֱ�Ϊ��������������ֵ���߷���3����ǩ
	private JTextField b1 = null, b2 = null, b3 = null; // �ֱ�Ϊ������������ֵ���߷��ŵ������

	private JToolBar buttonpanel;     // ���尴ť���
	JButton button[];                 // ���尴ť��
	private String tiptext[] = { "��ʼ��", "����", "����", "���Ž�" };
	
	private String names[] = { "initialize", "undo", "again", "best" }; // ���幤����ͼ�������
	// private Icon icons[];                //����ͼ������

	private DrawArea drawarea;             // ������Ķ���
	private JLabel startbar;               // �ײ�״̬��
	
	String strNode,strOpr;           //������������ֵ���߷���
    String strN;
	

	public MainInterface(String str) {
		
		super("�������Ϸ");
		// �˵�����
		JMenu menuMode = new JMenu("��Ϸģʽ"); // ��ʼ���˵�
		JMenu menuHelp = new JMenu("��Ϸ����");

		JMenuItem itemRandom = new JMenuItem("���ģʽ");
		JMenuItem itemCustom = new JMenuItem("�Զ���ģʽ");

		JMenuItem itemHelp = new JMenuItem("����");

		menuMode.add(itemRandom); // ���˵�����ӵ��˵���
		menuMode.add(itemCustom);

		menuHelp.add(itemHelp);

		JMenuBar menuBar = new JMenuBar(); // ��ʼ���˵���
		menuBar.add(menuMode); // ���Ӳ˵����˵���
		menuBar.add(menuHelp);

		setJMenuBar(menuBar); // ���ò˵�

		// ע��˵���Ӧ
		itemHelp.addActionListener(new ActionListener() { // �˵��¼�����
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(MainInterface.this,
						"������Ϊ�������Ϸ����ҿ���ʹ�����ģʽ���Զ���ģʽ"); // ��ʾ�����Ϣ
			}
		});

		// �������ĳ�ʼ��
		buttonpanel = new JToolBar(JToolBar.HORIZONTAL);
		// icons = new ImageIcon[names.length];
		button = new JButton[names.length];
		for (int i = 0; i < names.length; i++) 
		{
			// icons[i] = new
			// ImageIcon(getClass().getResource("/icon/"+"pen.jpg"));  //���ͼƬ������·��Ϊ��׼��
			// button[i] = new JButton("", icons[i]);                  //�����������еİ�ť
			button[i] = new JButton(tiptext[i]);                       // �����������еİ�ť
			button[i].setToolTipText(tiptext[i]);                      // ����������Ƶ���Ӧ�İ�ť�ϸ�����Ӧ����ʾ
			buttonpanel.add(button[i]);
			button[i].setBackground(Color.green);
			button[i].addActionListener(this);
		}

		// �滭���ĳ�ʼ��
		drawarea = new DrawArea(this);

		// ״̬���ĳ�ʼ��
		startbar = new JLabel(str);

		Container con = getContentPane();// �õ��������

		// �����¿�Ƭ����
		card = new CardLayout(5, 5);
		pane = new JPanel(card);
		button_1 = new JButton("ȷ��");

		// �����Զ�����������ı�ǩ���ı���
		a1 = new JLabel("������");
		a2 = new JLabel("����ֵ");
		a3 = new JLabel("�߷���");
		b1 = new JTextField(100);
		b2 = new JTextField(100);
		b3 = new JTextField(200);
		b1 = new JTextField(strN);
		b2 = new JTextField(strNode);
		b3 = new JTextField(strOpr);
		
		

		a1.setBounds(20, 40, 50, 30); // ���ø���ǩλ�úʹ�С
		b1.setBounds(80, 40, 200, 30);
		a2.setBounds(20, 80, 50, 30);
		b2.setBounds(80, 80, 200, 30);
		a3.setBounds(20, 120, 50, 30);
		b3.setBounds(80, 120, 200, 30);
		button_1.setBounds(50, 160, 80, 30);
		
		//��ʼ���������
		p1 = new JPanel(); // ��ӭ����
		p2 = new JPanel(); // ��ʾ�����
		p3 = new JPanel(); // �Զ�������

		p3.setLayout(null);

		p1.setBackground(Color.RED);
		p2.setBackground(Color.white);
		p3.setBackground(Color.GREEN);

		p1.add(new JLabel("��ӭ����"));
		p2.add(new JLabel("��ʾ�����"));
		p3.add(new JLabel("�Զ�������"));

		p3.add(a1);       // ������ǩ��ӵ��Զ����������
		p3.add(b1); 
		p3.add(a2);
		p3.add(b2);
		p3.add(a3);
		p3.add(b3);
		p3.add(button_1);

		pane.add(p1, "p1");  //�����ӵ���Ƭ������
		pane.add(p2, "p2");
		pane.add(p3, "p3");

		// ��ת��Ƭ���ֶ���

		button_1.addActionListener(new ActionListener() { // �������ݣ�ȷ���󣬻ص���ʾ����εĽ���
			public void actionPerformed(ActionEvent e) {
				
				strN=b1.getText();         //��ȡ�ı����ֵ
				strNode=b2.getText();
				strOpr=b3.getText();
				
//				System.out.println(strN);
//				System.out.println(strNode);
//				System.out.println(strOpr);
				drawarea.initialize(strN,strNode,strOpr);
				card.show(pane, "p2");
			}
		});
		
		itemRandom.addActionListener(new ActionListener() { // ��ʹ�����ģʽ��������ʾ����εĽ���
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
		
		itemCustom.addActionListener(new ActionListener() { // �������Զ���ģʽ�������Զ����������ݽ���
					public void actionPerformed(ActionEvent e) {
						
						card.show(pane, "p3");
					}
				});

		con.add(pane);    //����Ƭ������ӵ�������

		p2.setLayout(new BorderLayout());         //����Ӧ�Ķ�����ӵ����p2����ʾ����εĽ���
		p2.add(buttonpanel, BorderLayout.NORTH);
		p2.add(drawarea, BorderLayout.CENTER);
		p2.add(startbar, BorderLayout.SOUTH);
		
		Toolkit tool = getToolkit();             // �õ�һ��Tolkit��Ķ�����Ҫ���ڵõ���Ļ�Ĵ�С��
		Dimension dim = tool.getScreenSize();    // �õ���Ļ�Ĵ�С ������Dimension����
		setBounds(40, 40, dim.width - 270, dim.height - 100);     // ����������С������

		pane.setBounds(0, 31, dim.width - 270, dim.height - 100); // ���ÿ�Ƭ���ֵ�����ʹ�С��
	
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ����״̬����ʾ���ַ�
	public void setStratBar(String s) {
		startbar.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button[0]) {
			// System.out.println("��ʼ");
			try {
				drawarea.initialize();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		if (e.getSource() == button[1]) {
			// System.out.println("����");
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

