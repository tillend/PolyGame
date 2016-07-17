package Domain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Line implements Cloneable {
	private boolean flag;   //标志位，判断线是否已被合并，true为已被合并
	private Node Lnode;
	private Node Rnode;
	private char operator;   //运算符

	//画图需要
	int  R=23,G=90,B=123;	//定义色彩属性
	float stroke = 5.0f;	//定义线条粗细的属性
	int textAreaWeight=30, textAreaHeigth=19; //文字框长宽 
	int textAreaX, textAreaY;
	
	public Line(){}  //无参构造函数
	
	/**
	 * 描述：随机模式初始化线的列表（随机生成运算符）
	 * @param n          顶点数
	 * @param listnode   顶点列表
	 * @return           返回生成的边的列表
	 */
	public static ArrayList<Line> initialize(int n,List<Node> listnode) {
		ArrayList<Line> L = new ArrayList<Line>(n);
		char operator;
		
		//顶点数大于2的情况下，线与点一一对应
		for(int i = 0; i < n; i++){
			if(Math.random() > 0.5){
				operator = '*';
			}else{
				operator = '+';
			}
			L.add(new Line(listnode.get(i),listnode.get((i+1) % n),operator));
		}
		return (ArrayList<Line>) L;
	}

	public void draw(Graphics2D g2d) {
		//画直线
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(Lnode.getXpos(), Lnode.getYpos(), Rnode.getXpos(), Rnode.getYpos());
		
		//画矩形
		g2d.setPaint(new Color(R,G,B));
		g2d.setStroke(new BasicStroke(1.0f));
		//g2d.drawRect(this.textAreaX, this.textAreaY, textAreaWeight, textAreaHeigth);
		
		//画文字
		g2d.setPaint(new Color(R,G,B));
		g2d.setFont(new Font("Times New Roman", 14, (int)stroke*8));//设置字体、
		g2d.drawString(""+this.getOperator(), this.textAreaX+5, this.textAreaY+25);
		
		g2d.setFont(new Font("Times New Roman", 14, (int)stroke*5));//设置字体、
		g2d.drawString(""+this.getLnode().getGrade(), this.getLnode().getXpos()+10, this.getLnode().getYpos());
		g2d.drawString(""+this.getRnode().getGrade(), this.getRnode().getXpos()+10, this.getRnode().getYpos());
		
	}
	
	public Line(Node Lnode,Node Rnode,char operator){
		this.Lnode = Lnode;
		this.Rnode = Rnode;
		this.operator = operator;
		this.textAreaX = (this.Lnode.getXpos()+this.Rnode.getXpos())/2;
		this.textAreaY = (this.Lnode.getYpos()+this.Rnode.getYpos())/2;
	}
	
	
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Node getLnode() {
		return Lnode;
	}
	public void setLnode(Node lnode) {
		Lnode = lnode;
	}
	public Node getRnode() {
		return Rnode;
	}
	public void setRnode(Node rnode) {
		Rnode = rnode;
	}
	public char getOperator() {
		return operator;
	}
	public void setOperator(char operator) {
		this.operator = operator;
	}
	public int getTextAreaX() {
		return textAreaX;
	}
	public void setTextAreaX(int textAreaX) {
		this.textAreaX = textAreaX;
	}
	public int getTextAreaY() {
		return textAreaY;
	}
	public void setTextAreaY(int textAreaY) {
		this.textAreaY = textAreaY;
	}

	public int getTextAreaWeight() {
		return textAreaWeight;
	}

	public void setTextAreaWeight(int textAreaWeight) {
		this.textAreaWeight = textAreaWeight;
	}

	public int getTextAreaHeigth() {
		return textAreaHeigth;
	}

	public void setTextAreaHeigth(int textAreaHeigth) {
		this.textAreaHeigth = textAreaHeigth;
	}

	public float getStroke() {
		return stroke;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException { 
		Line line =  (Line) super.clone();
		//对引用的对象也进行复制 
		line.Lnode = (Node) Lnode.clone();
		line.Rnode = (Node) Rnode.clone();
        return line;
	}
}
