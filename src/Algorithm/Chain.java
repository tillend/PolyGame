package Algorithm;
import java.util.Vector;

/*
 * �������Ϸ
 * ���࣬ÿ����������������ͨ������֮�����������Ӷ���
 * @description �����Ķ���εĶ���ͱߵ�˳ʱ������Ϊ op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice ���������Сֵ�����꣬�����±����1��ʼ��n����
 */

public class Chain {
	public int i;            	//����ʼ�±�
	public int j;			 	//������
	public int v;			 	//��ֵ
	public int isMax;        	//����ά���꼴�Ƿ�Ϊ���ֵ���±�0������Сֵ���±�1�������ֵ
	public Chain next;		 	//
	public Chain last1,last2;	//���ڱ�����ϳɸ�������������
	public Chain(int i,int j,int isMax){
		//���췽��
		this.i = i;
		this.j = j;
		this.isMax = isMax;
	}
}