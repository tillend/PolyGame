package Domain;
/**
 * ��ÿ����Ϸ�У�ÿ��ѡ�����ߣ��൱�ںϲ����㣬��Ϊ��һ�����һ������Ϊ��ʹ��
 * @author LIn
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements Cloneable {
	private List<Node> listnode;
	private List<Line> listline;
	private int[] movement;   //ɾ���ߵ�˳��
	private int grade;
	private int num;          //�������Ŀ
	private int count = 0;    //ɾ���ߵļ�¼��
	
	//���캯��,��ʼ�������ߵ�����
	public Game(int n){
		this.num = n;
		movement = new int[n];
		listnode = new ArrayList<Node>(n);
		listline = new ArrayList<Line>(n);
	}
	
	/**
	 * deleteLine���ⲿ����
	 * @param tx
	 * @param ty
	 * @return
	 */
	public boolean deleteLine(int tx, int ty){
		int lineNum = -1;
		
		//ƥ��ɾ������
		for (int i=0; i<listline.size(); i++) {
			int x1 = listline.get(i).getTextAreaX();
			int x2 = x1 + listline.get(i).getTextAreaWeight();
			int y1 = listline.get(i).getTextAreaY();
			int y2 = y1 + listline.get(i).getTextAreaHeigth();
			if (tx>=x1 && tx<=x2 && ty>=y1 && ty<=y2) {
				lineNum = i;
				break;
			}
		}
		return deleteLine(lineNum);
	}
	
	/**
	 * ������ɾ�����ߣ��ó����������ϲ�����
	 * ���裺1.���������������󶥵�
	 *     2.�ֱ���б���ɾ���Ҷ��㼰��ѡ�е�����,������־����Ϊ��ʹ��
	 *     3.���Ҷ������һ�����߽����µĶ���
	 *     4.��ɾ����ֻʣ��һ���㣬��˵����Ϸ����������ɼ�
	 * @param lineNum �����б��еı��
	 * @return �ж���Ϸ�Ƿ����
	 */
	public boolean deleteLine(int lineNum) {
		int result = 0;
		
		//ɾ����һ������ʱ����Ҫ���е�ϲ�
		if(count == 0){
			Line l = listline.get(lineNum); 
			l.setFlag(true);
			movement[count++] = lineNum;  //��¼�ߵ����
			return false;
		}
		
		//���������������󶥵�
		while(true){
			if(listline.get(lineNum % num).isFlag()){  //�ҳ���һ��δ��ʹ�õ���
				lineNum++;
			}else{
				break;
			}
		}
		Line l = listline.get(lineNum % num); 
		
		if(l.getOperator() == '+'){   //�ж������
			result = l.getLnode().getGrade() + l.getRnode().getGrade();
		}else if(l.getOperator() == '*'){
			result = l.getLnode().getGrade() * l.getRnode().getGrade();
		}

		l.getLnode().setGrade(result);
		
		//�ֱ���б���ɾ���Ҷ��㼰��ѡ�е�����,������־����Ϊ��ʹ��
		l.getRnode().setFlag(true);   //listnode.remove(l.getRnode());
		l.setFlag(true);              //listline.remove(lineNum);
		movement[count++] = lineNum;  //��¼�ߵ����
		
		//���Ҷ������һ�����߽����µĶ���
		while(count < num){
			lineNum++;
			if(!listline.get(lineNum % num).isFlag()){  //�ҳ���һ��δ��ʹ�õ���
				Line next = listline.get(lineNum % num);
				if(next.getLnode().equals(l.getRnode())){  //��һ�����ǿ���ʱ��Ҫ����
					next.setLnode(l.getLnode());
				}
				break;
			}
		}    
		
		//ֻʣ��һ����ʱ����ɼ�
		if(num == count){
			for(int i = 0 ; i < num; i++){
				if(!this.listnode.get(i).isFlag()){
					this.grade = listnode.get(i).getGrade();
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * ���¿�ʼ
	 * ���÷�����replay(Game game),ʹ��game���������
	 * @param copy  ��ʼ����Ϸ����ƶ���
	 * @return      ��ʼ����Ϸ״̬
	 * 
	 */
	public Game replay(Game copy){
		Game g = null;
		try {
		    g =  (Game) copy.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return g;
	}

	/**
	 * ����:����
	 * @param deleteOrder  ԭ��Ϸ�ߵ�ɾ������
	 * @param count        ��ɾ�����ߵ�����
	 * @param copy         ��ʼ����Ϸ����ƶ���
	 * @return             ������һ������Ϸ״̬
	 * @throws CloneNotSupportedException
	 */	
	public Game regret(int[] deleteOrder, int count, Game copy) throws CloneNotSupportedException{
		if(count == deleteOrder.length){
			System.out.print("��Ϸ�ѽ������޷�����");
			return null;
		}else if(count == 1){   //�൱������
			return (Game) copy.clone();
		}
		
		//��ԭ��һ����״̬
		Game game = (Game) copy.clone();
		for(int i = 0; i < count - 1; i++){
			game.movement[i] = deleteOrder[i];
			game.deleteLine(movement[i]);
			game.count = count - 1;
		}
		return game;

	}
	
	/**
	 * ������ִ�л��ڶ�̬�滮����߷ַ���
	 * @param deleteOrder  ��߷ַ�����ɾ������
	 * @param copy         ��ʼ����Ϸ����ƶ���
	 * @return 
	 * @return
	 */
	public Game optimalSolution(int[] deleteOrder, Game copy) throws CloneNotSupportedException{
		Game game = (Game) copy.clone();
		for(int i = 0; i < deleteOrder.length; i++){
			game.movement[i] = deleteOrder[i];   //����ɾ����
			/*
			 * ����ɾ���ߣ�����ɾ���ߺ�Ķ����
			 * ��
			 * ������߷ַ�����main
			 * 
			 */
			
		}
		return game;

	}
	
	public int[] getMovement() {
		return movement;
	}

	public void setMovement(int[] movement) {
		this.movement = movement;
	}
	
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public List<Node> getListnode() {
		return listnode;
	}

	public void setListnode(ArrayList<Node> listnode) {
		this.listnode = listnode;
	}

	public List<Line> getListline() {
		return listline;
	}

	public void setListline(ArrayList<Line> listline) {
		this.listline = listline;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	/*���*/
	public Object clone() throws CloneNotSupportedException {
	    Game game =  (Game) super.clone();
	    
		//�ֱ��Node��Line��List�������
	    game.listnode = new ArrayList<Node>(this.num); 
	    Iterator<Node> iterator = this.listnode.iterator(); 
        while(iterator.hasNext()){ 
        	game.listnode.add((Node)iterator.next().clone()); 
        }
        
        game.listline = new ArrayList<Line>(this.num);    
		for(int i = 0; i < this.listline.size(); i++){
			char operator = this.listline.get(i).getOperator();
			game.listline.add(new Line(game.listnode.get(i),game.listnode.get((i+1) % this.num),operator));
		}

	    return game;    
	}

}
