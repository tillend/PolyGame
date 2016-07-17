package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Cloneable {
	private boolean flag;   //标志位，判断点是否已被合并，true为已被合并
	private int Xpos;
	private int Ypos;
	private int grade;   //顶点的值
	
	public Node(){}  //无参构造函数
	
	public Node(int grade){   //重载构造函数
		this.grade = grade;
	}
	
	/**
	 * 描述：随机模式初始化点的列表（随机生成整数值）
	 * @param n     顶点数
	 * @param low   取值范围最小值
	 * @param high  取值范围最大值
	 * @return      返回生成的点的列表
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
