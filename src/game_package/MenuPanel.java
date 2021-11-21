package game_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    JButton buttonJvJ;
    JButton buttonJvIA;
    JButton buttonIAvIA;
    GameFrame gameframe;

    MenuPanel(GameFrame gaameframe)
    {
        gameframe = gaameframe;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

        buttonJvJ = createButton("Joueur vs Joueur","jvj");
        buttonJvIA = createButton("Joueur vs IA","jvIa");
        buttonIAvIA = createButton("IA vs IA","IavIa");;
        this.add(buttonJvJ);
        this.add(buttonJvIA);
        this.add(buttonIAvIA);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonJvJ)
        {
            gameframe.startGame(1);

        }
        else if (e.getSource() == buttonJvIA)
        {
            gameframe.startGame(2);
        }
        else if(e.getSource() == buttonIAvIA)
        {
            gameframe.startGame(3);
        }
    }

    private JButton createButton(String s, String c) {
        JButton but = new JButton(s);
        
		/*but.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				but.setBackground(new Color(255, 222, 06));
				but.setBorder(BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK));
				but.setForeground(Color.BLACK);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				but.setBackground(Color.BLACK);
				but.setBorder(BorderFactory.createBevelBorder(0, new Color(255, 222, 06), new Color(255, 222, 06)));
				but.setForeground(new Color(255, 222, 06));
			}
		});*/
        but.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        but.addActionListener(this);
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setForeground(new Color(255, 222, 06));
        but.setBackground(Color.BLACK);
        but.setBorder(BorderFactory.createBevelBorder(0, new Color(255, 222, 06), new Color(255, 222, 06)));
        //but.setBounds(137, 35+(i*70), 275, 110);
        but.setFocusable(false);
        return but;
    }

}
