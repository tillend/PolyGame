package Algorithm;
import java.util.Vector;

/*
 * 多边形游戏
 * 链类，每条链都有两条子链通过它们之间的运算符链接而成
 * @description 所给的多边形的顶点和边的顺时针序列为 op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice 除了最大最小值的坐标，所有下标均从1开始到n结束
 */

public class Chain {
	public int i;            	//链开始下标
	public int j;			 	//链长度
	public int v;			 	//链值
	public int isMax;        	//第三维坐标即是否为最大值，下标0代表最小值，下标1代表最大值
	public Chain next;		 	//
	public Chain last1,last2;	//用于保存组合成该链的两个子链
	public Chain(int i,int j,int isMax){
		//构造方法
		this.i = i;
		this.j = j;
		this.isMax = isMax;
	}
}