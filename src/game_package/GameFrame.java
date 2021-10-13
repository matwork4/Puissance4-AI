package game_package;
import javax.swing.JButton;
import javax.swing.JFrame;


public class GameFrame extends JFrame{
	
	GameFrame(){
		
		
		
		this.add(new GamePanel());
		this.setTitle("Puissance 4 by Mathis - M1 IC - 2021");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
		
	}
}
