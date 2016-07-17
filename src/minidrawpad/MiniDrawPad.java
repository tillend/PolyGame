package minidrawpad;

import javax.swing.UIManager;


//主函数类
public class MiniDrawPad {

	/**
	 * @param FileName DrawPad
	 * @author Liu Jun Guang 
	 * @param V 1.0.0 
	 */
	public static void main(String[] args) {
		// TODO 主函数
		/*try {//将界面设置为当前windows界面风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}*/
		DrawPad drawpad = new DrawPad("小小绘图板");
      
	}

}
