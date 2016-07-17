package Algorithm;

import java.util.Stack;
import java.util.Vector;

/*
 * 多边形游戏
 * @description 所给的多边形的顶点和边的顺时针序列为 op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice 所有下标均从1开始到n结束，其余下标均无意义
 */
 
public class PolygonGame2 {
	public static int n; 							//总节点数
	public static int v[];						//每个节点的数值
	public static Chain m[][][];					//保存动态规划过程的信息，方便回溯获得最优方案
	public static char op[];						//操作符
	public static Stack<Pair> stack = new Stack<Pair>();  //用于保存回溯的路径
	
	public static void testDataSet(int v[],char op[]) {
		//用于随机生成测试数据集，即随机生成节点数值和符号
		for(int i=1;i<v.length;i++) {
			v[i] = i;
			if( i % 2 == 0)
 				op[i] = '+';
			else
				op[i] = '*';
		}
		
		for (int i=1; i<v.length; i++) {
			System.out.print(v[i] + " ");
		}
		System.out.println();
		
		for (int i=1; i<v.length; i++) {
			System.out.print(op[i] + " ");
		}
		System.out.println();
		
	}
	
	public static void init(){
		//做一些必要的初始化操作，创建对象，初始化动态数组
		n = 6;       
		//n的值可以后期修改，根据数据集的大小
		v = new int[n+1];
		op = new char[n+1];
		m = new Chain[n+1][n+1][2];
		//生成测试数据
		testDataSet(v,op);
		
		for(int i=1;i<m.length;i++){
			for(int j=1;j<m[m.length-1].length;j++){
				m[i][j][0] = new Chain(i,j,0);
				m[i][j][1] = new Chain(i,j,1);
			}
		}
		
		for(int i=1;i<m.length;i++)
			m[i][1][0].v = m[i][1][1].v = v[i];
	}
	
	public static int polyMax(){
		//迭代计算
		init();
		for(int j = 1; j <= n; j++)
			for(int i = 1; i <= n; i++)
				for(int s = 1; s < j; s++)
					minMax(i, s, j); //计算最小值与最大值并更新保存
		
		int tmp = m[1][n][1].v;
		for(int i = 1; i<=n;i++)
			if(tmp < m[i][n][1].v) tmp = m[i][n][1].v;
		
		return tmp; //返回最优结果 
	}
	
	public static void minMax(int i, int s, int j){
		int[] e = new int[5];
		//分别保存两条子链的最大值和最小值，根据操作符和不同的组合，计算出合成链的最大最小值
		//两个负值相乘有可能出现更大的正值
		int a = m[i][s][0].v;
		int b = m[i][s][1].v;
		int r = (i + s - 1) % n + 1;
		int c = m[r][j-s][0].v;
		int d = m[r][j-s][1].v;
		int minf, maxf;	//合成链最小值，最大值
		if(op[r] == '+'){
			//操作符为+
			minf = a + c;
			if(minf < m[i][j][0].v){
				//更新最小值信息，保存其两条子链
				m[i][j][0].last1 = m[i][s][0];
				m[i][j][0].last2 = m[r][j-s][0]; 
			}
			maxf = b + d;
			if(maxf > m[i][j][1].v){
				//更新最大值信息，保存其两条子链
				m[i][j][1].last1 = m[i][s][1];
				m[i][j][1].last2 = m[r][j-s][1];
			}
		}else{
			//两条子链的最大最小值的四种不同组合
			e[1] = a * c;
			e[2] = a * d;
			e[3] = b * c;
			e[4] = b * d;
			minf = e[1];
			maxf = e[1];
			for(int k = 2; k < 5; k++){
				//maxf = Math.max(maxf, e[k]);
				//minf = Math.min(minf, e[k]);
				if(maxf < e[k]){
					maxf = e[k];
					//更新最小值信息，保存其两条子链
					if(k == 1){
						m[i][j][1].last1 = m[i][s][0];
						m[i][j][1].last2 = m[r][j-s][0];
					}else if(k == 2){
						m[i][j][1].last1 = m[i][s][0];
						m[i][j][1].last2 = m[r][j-s][1];
					}else if(k == 3){
						m[i][j][1].last1 = m[i][s][1];
						m[i][j][1].last2 = m[r][j-s][0];
					}else if(k == 4){
						m[i][j][1].last1 = m[i][s][1];
						m[i][j][1].last2 = m[r][j-s][1];
					}
				}
				if(minf > e[k]){
					minf = e[k];
					//更新最大值信息，保存其两条子链
					if(k == 1){
						m[i][j][0].last1 = m[i][s][0];
						m[i][j][0].last2 = m[r][j-s][0];
					}else if(k == 2){
						m[i][j][0].last1 = m[i][s][0];
						m[i][j][0].last2 = m[r][j-s][1];
					}else if(k == 3){
						m[i][j][0].last1 = m[i][s][1];
						m[i][j][0].last2 = m[r][j-s][0];
					}else if(k == 4){
						m[i][j][0].last1 = m[i][s][1];
						m[i][j][0].last2 = m[r][j-s][1];
					}
				}
			}
		}
		if(m[i][j][0].v > minf) 
			m[i][j][0].v = minf;
		
		if(m[i][j][1].v < maxf) 
			m[i][j][1].v = maxf;
	}
	public static void back(int i,int j,int isMax){
		//回溯，保存最优方案
		int ti1 = m[i][j][isMax].last1.i;
		int tj1 = m[i][j][isMax].last1.j;
		int tisMax1 = m[i][j][isMax].last1.isMax;
		int ti2 = m[i][j][isMax].last2.i;
		int tj2 = m[i][j][isMax].last2.j;
		int tisMax2 = m[i][j][isMax].last2.isMax;
		m[ti1][tj1][tisMax1].next = m[i][j][isMax];
		m[ti2][tj2][tisMax2].next = m[i][j][isMax];
		stack.push(new Pair(m[ti1][tj1][tisMax1],m[ti2][tj2][tisMax2]));
//		System.out.print("("+m[ti1][tj1][tisMax1].v);
		
		if(tj1 > 1){
			back(ti1,tj1,tisMax1);
		}
//		System.out.print("#"+m[ti2][tj2][tisMax2].v);
		
		if(tj2 > 1){
			back(ti2,tj2,tisMax2);
		}
//		System.out.print(")");
		
		System.out.print(ti2 + " ");
	}
	public static void main(String[] args){
		int ans = polyMax();
		System.out.println("最优成绩:"+ans);
		for(int i = 1; i<=n;i++){
			if(ans == m[i][n][1].v){
//				System.out.println("初始删边"+n);
				//System.out.println(m[i][n][1].last1.i+":"+m[i][n][1].last1.j);
				back(i,n,1);
			}
		}
		
		
	}
}
