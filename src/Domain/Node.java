package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Cloneable {
	private boolean flag;   //��־λ���жϵ��Ƿ��ѱ��ϲ���trueΪ�ѱ��ϲ�
	private int Xpos;
	private int Ypos;
	private int grade;   //�����ֵ
	
	public Node(){}  //�޲ι��캯��
	
	public Node(int grade){   //���ع��캯��
		this.grade = grade;
	}
	
	/**
	 * ���������ģʽ��ʼ������б������������ֵ��
	 * @param n     ������
	 * @param low   ȡֵ��Χ��Сֵ
	 * @param high  ȡֵ��Χ���ֵ
	 * @return      �������ɵĵ���б�
	 */
	public static ArrayList<Node> initialize(int n,int low,int high) {
		List<Node> L = new ArrayList<Node>(n);
		int rand = 0;
		int dis = high - low;
		for(int i = 0; i < n; i++){
			rand = (int) (Math.random() * dis + low);
			L.add(new Node(rand));
		}
		return (ArrayList<Node>) L;
		
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getXpos() {
		return Xpos;
	}
	public void setXpos(int xpos) {
		Xpos = xpos;
	}
	public int getYpos() {
		return Ypos;
	}
	public void setYpos(int ypos) {
		Ypos = ypos;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
        return super.clone();  
	}
}
