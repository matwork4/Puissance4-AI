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
    int typePartie = 0;
    int depthPartie = 0;
    int nbDepthButtons = 15;
    int decalDepth = 5;
    JRadioButton[] depths;
    JButton jouer;

    MenuPanel(GameFrame gaameframe)
    {
        gameframe = gaameframe;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        buttonJvJ = createButton("Player vs Player",1);
        buttonJvIA = createButton("Player vs AI",2);
        buttonIAvIA = createButton("AI vs AI",3);
        this.add(buttonJvJ);
        this.add(buttonJvIA);
        this.add(buttonIAvIA);
        

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonJvJ){
            gameframe.startGame(typePartie,depthPartie);
            
        }else if (e.getSource() == buttonJvIA){
        	typePartie = 2;
        	createDepth(nbDepthButtons);
        	
        }else if(e.getSource() == buttonIAvIA){
        	typePartie = 3;
        	createDepth(nbDepthButtons);
        	
        }else {
        	for(int i=0;i<nbDepthButtons;i++) {
        		if(e.getSource()==depths[i]) {
        			depthPartie = i+decalDepth;
        			
        			/*On change le premier bouton en bouton "play"*/
        			buttonJvJ.setText("Play");
        			buttonJvJ.setEnabled(true);
        			this.add(buttonJvJ);
        			
        		}
        	}
        }
        
    }
    
    
    private void createDepth(int number) {
    	
    	/* Grise les boutons */
    	buttonJvJ.setEnabled(false);
    	buttonJvIA.setEnabled(false);
    	buttonIAvIA.setEnabled(false);
    	
    	/*créer les boutons de profondeur*/
    	depths = new JRadioButton[number];
    	
    	ButtonGroup group = new ButtonGroup();
    	
    	for(int i=0;i<number;i++) {
    		
    		
    		depths[i]=new JRadioButton();
    		group.add(depths[i]);
    		
    		
    		if(i+decalDepth==9 && typePartie==3){
    			depths[i].setText("9 (advised)");
    		}else if(i+decalDepth==12 && typePartie==2){
    			depths[i].setText("12 (advised)");
    		}else {
    			depths[i].setText("Depth : "+(i+decalDepth));
    		}
    		
    		
    		
    		this.add(depths[i]);
    		
    		depths[i].setFocusable(false);
    		depths[i].setFont(new Font("Comic Sans",Font.BOLD,20));
    		depths[i].setForeground(Color.white);
    		depths[i].setBackground(Color.gray);
    		depths[i].setBorder(BorderFactory.createEtchedBorder());
    		depths[i].addActionListener(this);
    		
    		if(i<5) {
    			depths[i].setBounds(SCREEN_WIDTH/8,4*SCREEN_HEIGHT/8+i*60,SCREEN_WIDTH/5,30);
    		}else if(i<10){
    			depths[i].setBounds(SCREEN_WIDTH/2-(SCREEN_WIDTH/10),4*SCREEN_HEIGHT/8+(i-5)*60,SCREEN_WIDTH/5,30);
    		}else {
    			depths[i].setBounds(SCREEN_WIDTH-(SCREEN_WIDTH/3),4*SCREEN_HEIGHT/8+(i-10)*60,SCREEN_WIDTH/5,30);
    		}
    		
    		
    	}
    	
    }
    

    private JButton createButton(String s, int position) {
        JButton but = new JButton(s);
        
		/*
        but.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        but.addActionListener(this);
        but.setAlignmentX(Component.CENTER_ALIGNMENT);
        but.setForeground(new Color(255, 222, 06));
        but.setBackground(Color.BLACK);
        but.setBorder(BorderFactory.createBevelBorder(0, new Color(255, 222, 06), new Color(255, 222, 06)));
        //but.setBounds(137, 35+(i*70), 275, 110);
        but.setFocusable(false);*/
        
        but.setFocusable(false);
        but.setFont(new Font("Comic Sans",Font.BOLD,25));
        but.setForeground(Color.white);
        but.setBackground(Color.gray);
        but.setBorder(BorderFactory.createEtchedBorder());
        but.addActionListener(this);
        
        but.setBounds(SCREEN_WIDTH/4,position*SCREEN_HEIGHT/8,SCREEN_WIDTH/2,50);
        
        return but;
    }

}
