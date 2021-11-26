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
    int typePartie = 1;
    int depthPartie = 0;
    int nbDepthButtons = 15;
    int decalDepth = 5;
    JRadioButton[] depths;
    JButton jouer;
    JComboBox boxAIs, boxDepth;
    boolean isAlphabeta = false;
    int maxDepth = 20;
    

    MenuPanel(GameFrame gaameframe){
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
            gameframe.startGame(typePartie,depthPartie,isAlphabeta);
            
        }else if (e.getSource() == buttonJvIA){
        	typePartie = 2;
        	
        	createBoxAIs();
        	createBoxDepths(maxDepth);
        	
        }else if(e.getSource() == buttonIAvIA){
        	typePartie = 3;
        	createBoxAIs();
        	createBoxDepths(maxDepth);
        	
        }else if(e.getSource() == boxAIs) {
        	
        	if(boxAIs.getSelectedIndex()==1) {
        		System.out.println("IA choisit : coupe Alphabeta");
        		isAlphabeta = true;
        	}else{
        		System.out.println("IA choisit : Minimax");
        		isAlphabeta = false;
        	}
        	
        }else if(e.getSource() == boxDepth) {
        	
        	for(int i = 0; i<=maxDepth;i++) {
        		if(boxDepth.getSelectedIndex()==i) {
            		System.out.println("depth selected = "+i);
            		depthPartie = i;
            		
            		/*On change le premier boutton en boutton "play"*/
        			buttonJvJ.setText("Play");
        			buttonJvJ.setEnabled(true);
        			this.add(buttonJvJ);
            	}
        	}
        	
        /* Ceci permettait de générer des boutons correspondant a la profondeur :
    	}else {
        	for(int i=0;i<nbDepthButtons;i++) {
        		if(e.getSource()==depths[i]) {
        			depthPartie = i+decalDepth;
        			
        			buttonJvJ.setText("Play");
        			buttonJvJ.setEnabled(true);
        			this.add(buttonJvJ);
        			
        		}
        	}*/
        }
    }
    
    
    /* createBoxAIs :
     * permet de créer le boutton qui sélectionne l'ia Minimax ou Minimax + coupe alpha beta
     */
    private void createBoxAIs() {
    	
    	/* On grise les boutons */
    	buttonJvJ.setEnabled(false);
    	buttonJvIA.setEnabled(false);
    	buttonIAvIA.setEnabled(false);
    	
    	String[] AIs = {"MiniMax","MiniMax + AlphaBeta"};
    	
    	boxAIs = new JComboBox(AIs);

    	this.add(boxAIs);
    	boxAIs.setFocusable(false);
    	boxAIs.setFont(new Font("Comic Sans",Font.BOLD,21));
    	boxAIs.setForeground(Color.white);
    	boxAIs.setBackground(Color.gray);
    	boxAIs.setBorder(BorderFactory.createEtchedBorder());
    	boxAIs.addActionListener(this);
    	
    	boxAIs.setSelectedIndex(1);
    	
    	boxAIs.setBounds(SCREEN_WIDTH/3,4*SCREEN_HEIGHT/7,SCREEN_WIDTH/3,40);
    }
    
    /* createBoxDepths :
     * permet de créer le boutton qui sélectionne la profondeur de Minimax 
     */
    private void createBoxDepths(int maxDepth) {
    	
    	String[] depths = {"Depth 0"};
    	
    	boxDepth = new JComboBox(depths);
    	
    	for(int i = 1; i<=maxDepth;i++) {
    		boxDepth.insertItemAt("Depth "+i, i);
    	}

    	this.add(boxDepth);
    	boxDepth.setFocusable(false);
    	boxDepth.setFont(new Font("Comic Sans",Font.BOLD,23));
    	boxDepth.setForeground(Color.white);
    	boxDepth.setBackground(Color.gray);
    	boxDepth.setBorder(BorderFactory.createEtchedBorder());
    	boxDepth.addActionListener(this);
    	
    	boxDepth.setSelectedIndex(12);
    	
    	boxDepth.setBounds(SCREEN_WIDTH/3,4*SCREEN_HEIGHT/7+100,SCREEN_WIDTH/3,40);
    }
    
    
    /* createDepth :
     * cette fonction permet de generer des boutons correpondants a la profondeur
     * des algorithmes des IAs. (On ne l'utilise plus)
    */
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
