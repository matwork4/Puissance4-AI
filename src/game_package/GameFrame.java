package game_package;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame {

	MenuPanel menu;
	GameFrame(){

		menu = new MenuPanel(this);
		this.add(menu);
		//this.add(new GamePanel());
		this.setTitle("Puissance 4 by Mathis, Nicolas, Nozie - M1 IC - 2021");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

	public void startGame(int typePartie, int depth, boolean isAlphabeta)
	{
		/* Deux options */

		/*this.getContentPane().removeAll();
		this.getContentPane().invalidate();*/

		this.menu.setVisible(false);
		this.getContentPane().add(new GamePanel(typePartie, depth, isAlphabeta));
		this.getContentPane().revalidate();
	}

}
