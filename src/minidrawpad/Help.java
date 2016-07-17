package minidrawpad;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//帮助菜单功能的事项类
public class Help extends JFrame {
	private DrawPad  drawpad = null;
	Help(DrawPad dp)
	{
		drawpad = dp;
	}
	
	public void MainHeip()
	  {
	  	JOptionPane.showMessageDialog(this,"小小绘图板帮助文档！","小小绘图板",JOptionPane.WARNING_MESSAGE);
	  } 
	 public void AboutBook()
	  {
	  	JOptionPane.showMessageDialog(drawpad,"小小绘图板"+"\n"+"    版本: 1.1.2"+"\n"
	  	   +"    作者:  刘  军  光"+"\n"
	  	   +"    时间:  2009/12/13","小小绘图板",JOptionPane.WARNING_MESSAGE);
	  }
}
