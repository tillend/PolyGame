package Algorithm;

/*
 * 多边形游戏
 * 链类，每条链都有两条子链通过它们之间的运算符链接而成
 * @description 所给的多边形的顶点和边的顺时针序列为 op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice 除了最大最小值的坐标，所有下标均从1开始到n结束
 */
 
public class Pair {
	public Chain first; //用于保存两个子链中的一个
	public Chain second; //用于保存两个子链中的一个
	public Pair(Chain f,Chain s){
		first = f;
		second = s;
	}
}