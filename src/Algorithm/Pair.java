package Algorithm;

/*
 * �������Ϸ
 * ���࣬ÿ����������������ͨ������֮�����������Ӷ���
 * @description �����Ķ���εĶ���ͱߵ�˳ʱ������Ϊ op[1], v[1], op[2], v[2], ..., op[n],v[n]
 * @notice ���������Сֵ�����꣬�����±����1��ʼ��n����
 */
 
public class Pair {
	public Chain first; //���ڱ������������е�һ��
	public Chain second; //���ڱ������������е�һ��
	public Pair(Chain f,Chain s){
		first = f;
		second = s;
	}
}