package Domain;
/**
 * 考虑：
 * 1.只有1或2个顶点时，游戏无效
 * @author LIn
 *
 */
public class Test {

	public static void main(String[] args){
		int n = 3;   //点的数目
		if(n < 3){
			System.out.println("游戏无效");
			return;
		}
		
		Game g = new Game(n);
		//按取值范围初始化点
		g.setListnode(Node.initialize(n,1,10));
		for(int i = 0; i < n; i++){
			System.out.println(g.getListnode().get(i).getGrade());
		}
		//随机初始化符号
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
