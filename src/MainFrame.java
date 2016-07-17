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
	private JPanel p1 = null, p2 = null, p3 = null; // ������壬�ֱ���ʾ��ӭ���棬����ν��棬�Զ������ݽ���
	private JLabel a1 = null, a2 = null, a3 = null; // �ֱ�Ϊ��������������ֵ���߷���3����ǩ
	private JTextField b1 = null, b2 = null, b3 = null; // �ֱ�Ϊ������������ֵ���߷��ŵ������
	private Container container; // ��������

	private JToolBar jmain; // ������
	private JButton start; // ��ʼ
	private JButton again; // ����
	private JButton back; // ����
	private JButton best; // ���Ž�

	public MainFrame() {
		super("�������Ϸ"); // ���ø��๹�캯��

		Container container = getContentPane(); // �õ�����
		container.setLayout(null);
	    
		//���ñ���ͼƬ
		

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
				JOptionPane.showMessageDialog(MainFrame.this,
						"������Ϊ�������Ϸ����ҿ���ʹ�����ģʽ���Զ���ģʽ"); // ��ʾ�����Ϣ
			}
		});

		// ����������
		jmain = new JToolBar();
		start = new JButton("��ʼ ");
		start.setToolTipText("����˰�ť����ʼ��Ϸ");
		back = new JButton("����");
		back.setToolTipText("���ص���һ��");
		again = new JButton("���� ");
		again.setToolTipText("�ص���ʼλ�ã����¿�ʼ");
		best = new JButton("���Ž�");
		best.setToolTipText("��ʾ�˶���ε����Ž�");

		// �������ӵ�������
		jmain.setLayout(new GridLayout(0, 4, 15, 15));
		jmain.add(start);
		jmain.add(back);
		jmain.add(again);
		jmain.add(best);
		jmain.setBounds(1, 1, 280, 30);
		container.add(jmain);

		// ע�ᰴŤ����
		start.addActionListener((ActionListener) this);
		back.addActionListener((ActionListener) this);
		again.addActionListener((ActionListener) this);
		best.addActionListener((ActionListener) this);

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
		b1 = new JTextField("�����붥����");
		b2 = new JTextField("�������Ӧ�Ķ���ֵ,�ո����");
		b3 = new JTextField("�������Ӧ�ߵķ��ţ��ո����");

		a1.setBounds(20, 40, 50, 30);  //���ø���ǩλ�úʹ�С
		b1.setBounds(80, 40, 200, 30);
		a2.setBounds(20, 80, 50, 30);  
		b2.setBounds(80, 80, 200, 30);
		a3.setBounds(20, 120, 50, 30);  
		b3.setBounds(80, 120, 200, 30);
		button_1.setBounds(50,160,80,30);
		
		p1 = new JPanel(); // ��ӭ����              //��ʼ���������
		p2 = new JPanel(); // ��ʾ�����
		p3 = new JPanel(); // �Զ�������
		
		
		p3.setLayout(null);

		p1.setBackground(Color.RED);
		p2.setBackground(Color.white);
		p3.setBackground(Color.GREEN);

		p1.add(new JLabel("��ӭ����"));
		p2.add(new JLabel("��ʾ�����"));
		p3.add(new JLabel("�Զ�������"));
		
		p3.add(a1); p3.add(b1);  //������ǩ��ӵ��Զ����������
		p3.add(a2); p3.add(b2);
		p3.add(a3); p3.add(b3);
		p3.add(button_1);
		
		pane.add(p1, "p1");
		pane.add(p2, "p2");
		pane.add(p3, "p3");

		// ��ת��Ƭ���ֶ���

		button_1.addActionListener(new ActionListener() { // �������ݣ�ȷ���󣬻ص���ʾ����εĽ���
			public void actionPerformed(ActionEvent e) {
				card.show(pane, "p2");
			}
		});
		itemRandom.addActionListener(new ActionListener() { // ��ʹ�����ģʽ��������ʾ����εĽ���
					public void actionPerformed(ActionEvent e) {
						card.show(pane, "p2");
					}
				});
		itemCustom.addActionListener(new ActionListener() { // �������Զ���ģʽ�������Զ����������ݽ���
					public void actionPerformed(ActionEvent e) {
						card.show(pane, "p3");
					}
				});
		pane.setBounds(0, 31, 800, 470); // ���ÿ�Ƭ���ֵ�����ʹ�С��
//		pane.setBackground(null);
//		pane.setOpaque(false);
		container.add(pane);

		setBounds(200, 100, 800, 500); // ����������λ�úʹ�С
		
		//���ñ���ͼƬ
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
		
		setResizable(false);// ���ò��ܸı����С
		setVisible(true); // ���ô��ڿɼ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �رմ���ʱ�˳�����

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