package Domain;
/**
 * ���ǣ�
 * 1.ֻ��1��2������ʱ����Ϸ��Ч
 * @author LIn
 *
 */
public class Test {

	public static void main(String[] args){
		int n = 3;   //�����Ŀ
		if(n < 3){
			System.out.println("��Ϸ��Ч");
			return;
		}
		
		Game g = new Game(n);
		//��ȡֵ��Χ��ʼ����
		g.setListnode(Node.initialize(n,1,10));
		for(int i = 0; i < n; i++){
			System.out.println(g.getListnode().get(i).getGrade());
		}
		//�����ʼ������
		g.setListline(Line.initialize(n,g.getListnode()));
		for(int i = 0; i < n; i++){
			System.out.println(g.getListline().get(i).getOperator());
		}
//		g.deleteLine(3);
//		g.deleteLine(2);
//		g.deleteLine(0);
//		g.deleteLine(1);
		System.out.print(g.getGrade());
	}

}
