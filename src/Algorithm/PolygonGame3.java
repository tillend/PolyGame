package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import Domain.Line;

/*
 * �������Ϸ
 * @description �����Ķ���εĶ���ͱߵ�˳ʱ������Ϊ op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice �����±����1��ʼ��n�����������±��������
 */
public class PolygonGame3 {
	public static int n; 							//�ܽڵ���
	public static int v[];							//ÿ���ڵ����ֵ
	public static Chain m[][][];					//���涯̬�滮���̵���Ϣ��������ݻ�����ŷ���
	public static char op[];						//������
	public static Stack<Pair> stack = new Stack<Pair>();  //���ڱ�����ݵ�·��
	public static Stack<Integer> index = new Stack<Integer>();
	public static int order[];
	public static void testDataSet(int v[],char op[]){
		//����������ɲ������ݼ�����������ɽڵ���ֵ�ͷ���
		
		for(int i=1;i<v.length;i++){
			v[i] = (int)(Math.random() * 30 ) - 15;
			if( v[i] % 2 == 0)
 				op[i] = '+';
			else
				op[i] = '*';
		}
		
	}
	
	public static void init(){
		//��һЩ��Ҫ�ĳ�ʼ���������������󣬳�ʼ����̬����
		stack = new Stack<Pair>();  //���ڱ�����ݵ�·��
		index = new Stack<Integer>();
		n = 6 ;       //n��ֵ���Ժ����޸ģ��������ݼ��Ĵ�С
		v = new int[n+1];
		op = new char[n+1];
		m = new Chain[n+1][n+1][2];
		order = new int[n+1];
		//���ɲ�������
		testDataSet(v,op);
		
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[m.length-1].length;j++){
				m[i][j][0] = new Chain(i,j,0);
				m[i][j][1] = new Chain(i,j,1);
			}
		}
		
		for(int i=1;i<m.length;i++)
			m[i][1][0].v = m[i][1][1].v = v[i];
		
	}
	
	public static void init(int nn, List<Line> list) {
		n = nn;
		v = new int[n+1];
		op = new char[n+1];
		m = new Chain[n+1][n+1][2];
		order = new int[n+1];
		for (int i=0; i<list.size(); i++) {
			v[i+1] = list.get(i).getLnode().getGrade();
			op[i+1] = list.get((i-1+list.size())%list.size()).getOperator();
		}
		
		//��ӡ���ɵ�ֵ����
		for (int i=1; i<v.length; i++) {
			System.out.print(v[i] + " ");
		}
		System.out.println();
		
		for (int i=1; i<v.length; i++) {
			System.out.print(op[i] + " ");
		}
		System.out.println();
		
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
		//��������
		for(int j = 1; j <= n; j++)
			for(int i = 1; i <= n; i++)
				for(int s = 1; s < j; s++)
					minMax(i, s, j); //������Сֵ�����ֵ�����±���
		
		int tmp = m[1][n][1].v;
		for(int i = 1; i<=n;i++)
			if(tmp < m[i][n][1].v) tmp = m[i][n][1].v;
		
		return tmp; //�������Ž��
	}
	
	public static void minMax(int i, int s, int j){
		int[] e = new int[5];
		//�ֱ𱣴��������������ֵ����Сֵ�����ݲ������Ͳ�ͬ����ϣ�������ϳ����������Сֵ

		int a = m[i][s][0].v;
		int b = m[i][s][1].v;
		int r = (i + s - 1) % n + 1;
		int c = m[r][j-s][0].v;
		int d = m[r][j-s][1].v;
		int minf, maxf;	//�ϳ�����Сֵ�����ֵ
		
		if(op[r] == '+'){
			//������Ϊ+
			minf = a + c;
			if(minf < m[i][j][0].v||m[i][j][0].last1 == null){
				//������Сֵ��Ϣ����������������
				m[i][j][0].last1 = m[i][s][0];
				m[i][j][0].last2 = m[r][j-s][0]; 
			}
			maxf = b + d;
			if(maxf > m[i][j][1].v||m[i][j][1].last1 == null){
				//�������ֵ��Ϣ����������������
				m[i][j][1].last1 = m[i][s][1];
				m[i][j][1].last2 = m[r][j-s][1];
			}
		}else{
			//���������������Сֵ�����ֲ�ͬ���
			e[1] = a * c;
			e[2] = a * d;
			e[3] = b * c;
			e[4] = b * d;
			minf = Integer.MAX_VALUE;
			maxf = Integer.MIN_VALUE;
			for(int k = 1; k < 5; k++){
				//maxf = Math.max(maxf, e[k]);
				//minf = Math.min(minf, e[k]);
				if(maxf < e[k]){
					maxf = e[k];
					//������Сֵ��Ϣ����������������
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
					//�������ֵ��Ϣ����������������
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
		
		if(s == 1){
			m[i][j][0].v = minf;
			m[i][j][1].v = maxf;
		}else{
			if(m[i][j][0].v > minf) 
				m[i][j][0].v = minf;
			
			if(m[i][j][1].v < maxf) 
				m[i][j][1].v = maxf;
		}
	}
	public static void back(int i,int j,int isMax){
		//���ݣ��������ŷ���
		if(j <= 1){
			return;
		}
		int ti1 = m[i][j][isMax].last1.i;
		int tj1 = m[i][j][isMax].last1.j;
		int tisMax1 = m[i][j][isMax].last1.isMax;
		int ti2 = m[i][j][isMax].last2.i;
		int tj2 = m[i][j][isMax].last2.j;
		int tisMax2 = m[i][j][isMax].last2.isMax;
		m[ti1][tj1][tisMax1].next = m[i][j][isMax];
		m[ti2][tj2][tisMax2].next = m[i][j][isMax];
		stack.push(new Pair(m[ti1][tj1][tisMax1],m[ti2][tj2][tisMax2]));
		index.push(ti2);
		System.out.print("("+m[ti1][tj1][tisMax1].v);
		
		if(tj1 > 1){
			back(ti1,tj1,tisMax1);
		}
		System.out.print(op[ti2]+""+m[ti2][tj2][tisMax2].v);
		if(tj2 > 1){
			back(ti2,tj2,tisMax2);
		}
		System.out.print(")");
		
		
	}
	public static void resolve(){
		int ans = polyMax();
		System.out.println(ans);
		for(int i = 1; i<=n;i++){
			if(ans == m[i][n][1].v){
				//System.out.println(m[i][n][1].last1.i+":"+m[i][n][1].last1.j);
				back(i,n,1);
				break;
			}
		}
		Arrays.fill(order, 0);
		
		System.out.println();
		int tmp = 1;
		while(!index.empty()){
			int top = index.pop();
			//System.out.println(top);
			order[top] = tmp++;
		}
		for(int i = 1; i <=n;i++) {
			System.out.print(order[i]+" ");
		}
		System.out.println();
	}
//	public static void main(String[] args){
////		for(int i = 0; i<100;i++)
////			resolve();
//		init();
//		resolve();
//	}
	
}

