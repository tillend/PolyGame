package Domain;
/**
 * 在每局游戏中，每次选中连线，相当于合并两点，即为将一个点和一条线置为已使用
 * @author LIn
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements Cloneable {
	private List<Node> listnode;
	private List<Line> listline;
	private int[] movement;   //删除边的顺序
	private int grade;
	private int num;          //结点或边数目
	private int count = 0;    //删除边的记录数
	
	//构造函数,初始化点与线的容器
	public Game(int n){
		this.num = n;
		movement = new int[n];
		listnode = new ArrayList<Node>(n);
		listline = new ArrayList<Line>(n);
	}
	
	/**
	 * deleteLine的外部方法
	 * @param tx
	 * @param ty
	 * @return
	 */
	public boolean deleteLine(int tx, int ty){
		int lineNum = -1;
		
		//匹配删除的线
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
	 * 描述：删除连线，得出计算结果，合并两点
	 * 步骤：1.将计算结果保留于左顶点
	 *     2.分别从列表中删除右顶点及被选中的连线,即将标志设置为已使用
	 *     3.将右顶点的下一条连线接至新的顶点
	 *     4.若删除后只剩下一个点，则说明游戏结束，计算成绩
	 * @param lineNum 线在列表中的编号
	 * @return 判断游戏是否结束
	 */
	public boolean deleteLine(int lineNum) {
		int result = 0;
		
		//删除第一条连线时不需要进行点合并
		if(count == 0){
			Line l = listline.get(lineNum); 
			l.setFlag(true);
			movement[count++] = lineNum;  //记录边的序号
			return false;
		}
		
		//将计算结果保留于左顶点
		while(true){
			if(listline.get(lineNum % num).isFlag()){  //找出下一条未被使用的线
				lineNum++;
			}else{
				break;
			}
		}
		Line l = listline.get(lineNum % num); 
		
		if(l.getOperator() == '+'){   //判断运算符
			result = l.getLnode().getGrade() + l.getRnode().getGrade();
		}else if(l.getOperator() == '*'){
			result = l.getLnode().getGrade() * l.getRnode().getGrade();
		}

		l.getLnode().setGrade(result);
		
		//分别从列表中删除右顶点及被选中的连线,即将标志设置为已使用
		l.getRnode().setFlag(true);   //listnode.remove(l.getRnode());
		l.setFlag(true);              //listline.remove(lineNum);
		movement[count++] = lineNum;  //记录边的序号
		
		//将右顶点的下一条连线接至新的顶点
		while(count < num){
			lineNum++;
			if(!listline.get(lineNum % num).isFlag()){  //找出下一条未被使用的线
				Line next = listline.get(lineNum % num);
				if(next.getLnode().equals(l.getRnode())){  //下一条不是空线时需要重连
					next.setLnode(l.getLnode());
				}
				break;
			}
		}    
		
		//只剩下一个点时计算成绩
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
	 * 重新开始
	 * 调用方法：replay(Game game),使用game对象来深复制
	 * @param copy  初始化游戏的深复制对象
	 * @return      开始的游戏状态
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
	 * 描述:悔棋
	 * @param deleteOrder  原游戏线的删除次序
	 * @param count        已删除的线的数量
	 * @param copy         初始化游戏的深复制对象
	 * @return             返回上一步的游戏状态
	 * @throws CloneNotSupportedException
	 */	
	public Game regret(int[] deleteOrder, int count, Game copy) throws CloneNotSupportedException{
		if(count == deleteOrder.length){
			System.out.print("游戏已结束，无法悔棋");
			return null;
		}else if(count == 1){   //相当于重来
			return (Game) copy.clone();
		}
		
		//复原上一步的状态
		Game game = (Game) copy.clone();
		for(int i = 0; i < count - 1; i++){
			game.movement[i] = deleteOrder[i];
			game.deleteLine(movement[i]);
			game.count = count - 1;
		}
		return game;

	}
	
	/**
	 * 描述：执行基于动态规划的最高分方案
	 * @param deleteOrder  最高分方案的删除次序
	 * @param copy         初始化游戏的深复制对象
	 * @return 
	 * @return
	 */
	public Game optimalSolution(int[] deleteOrder, Game copy) throws CloneNotSupportedException{
		Game game = (Game) copy.clone();
		for(int i = 0; i < deleteOrder.length; i++){
			game.movement[i] = deleteOrder[i];   //依次删除线
			/*
			 * 依序删除线，画出删除线后的多边形
			 * 或
			 * 返回最高分方案至main
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
	
	/*深复制*/
	public Object clone() throws CloneNotSupportedException {
	    Game game =  (Game) super.clone();
	    
		//分别对Node和Line的List进行深复制
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
