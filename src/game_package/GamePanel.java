package game_package;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.Scanner;
//import GamePanel.MyKeyAdapter;

import AI_package.AI1;
import AI_package.AI2;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 800;
	static final int UNIT_SIZE = 80;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	static final int DIM_Y = 7, DIM_X = DIM_Y-1;
	static long chrono = 0 ; // Fonctions pour le chronometre

	boolean running = false;
	int typepartie;
	Random random;
	Terrain terrain1;
	//AI1 Tron1; On ne l'utilise pas ici
	AI1 Tron2;
	AI2 Tron3;
	Graphics g;
	JButton[] buttons;
	JButton buttonJvJ;
	int depth = 0;
	boolean isAlphabeta;
	
	
	GamePanel(int typePartie, int depth, boolean isAlphabeta){
		typepartie = typePartie;
		this.depth = depth;
		this.isAlphabeta = isAlphabeta;
		random = new Random();

		if(typePartie == 1)
		{
			terrain1 = new Terrain(DIM_X, DIM_Y,UNIT_SIZE,SCREEN_HEIGHT,SCREEN_WIDTH, false);
		}
		else
		{
			terrain1 = new Terrain(DIM_X, DIM_Y,UNIT_SIZE,SCREEN_HEIGHT,SCREEN_WIDTH, true);
		}
		
		//permet de positionner ou on veut
		this.setLayout(null);
		
		//creer les boutons
		this.buttons = new JButton[DIM_Y];
		
		//ajoute les boutons
		setButtons();
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
		startGame();
	}


	public void setButtons() {
		
		for(int i=0;i<DIM_Y;i++) {
			buttons[i]=new JButton();
			
			buttons[i].setText("Clic !");
			buttons[i].setFocusable(false);
			buttons[i].setFont(new Font("Comic Sans",Font.BOLD,12));
			buttons[i].setForeground(Color.white);
			buttons[i].setBackground(Color.gray);
			buttons[i].setBorder(BorderFactory.createEtchedBorder());
			
			//Pour desactiver un bouton :
			//button4.setEnabled(false);
			
			terrain1.setButton(buttons[i], i);
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
		}		
	}
	
	public void greyButtons() {
		for(int i=0;i<DIM_Y;i++) {
			buttons[i].setEnabled(false);
			terrain1.setButton(buttons[i], i);
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
		}
	}
	
	
	
	public void startGame() {
		
		if(terrain1.isAi) {
			//this.Tron1 = new AI1(terrain1);
			this.Tron2 = new AI1(terrain1,DIM_X,DIM_Y,depth);
			this.Tron3 = new AI2(terrain1,DIM_X,DIM_Y,depth);
		}
		
		running = true;
		
	}
	
	public void paintComponent(Graphics g) {
		System.out.println("execute 1 fois");

		super.paintComponent(g);

		draw(g);
	}
	

	public void draw(Graphics g) {

		this.g=g;

		if(running) {


			//l'IA joue
			if(terrain1.isAi && !terrain1.color && terrain1.winner==0) {

				/* Player VS AI : type de Partie 2 */
				if(typepartie == 2) {
					System.out.println("\n C'est au tour de l'IA !");
					if(isAlphabeta) {
						Go_Chrono();
						Tron3.play(terrain1);
						Stop_Chrono();
					}else {
						Go_Chrono();
						Tron2.play(terrain1);
						Stop_Chrono();	
					}
				}
				else if(typepartie == 3) // AI VS AI : type de Partie 3 */
				{
					if(isAlphabeta) {
						while(terrain1.winner==0) {
							Tron3.play(terrain1);
						}
					}else {
						while(terrain1.winner==0) {
							Tron2.play(terrain1);
						}
					}
				}
			}
			
			//affiche la grille
			terrain1.dessine(this.g);
			ecrit();
			
			
		}
		
	}
	

	public void ecrit() {
		
		//Ecrit le vaincoeur 
		if(terrain1.winner==1) {
			g.setColor(Color.orange);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Player 1 win !",(terrain1.screen_width - metrics1.stringWidth("______________"))/2,terrain1.screen_height-terrain1.screen_height/6);
			
			greyButtons();
			
		}else if(terrain1.winner==2) {
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Player 2 win !",(terrain1.screen_width - metrics1.stringWidth("______________"))/2,terrain1.screen_height-terrain1.screen_height/6);
			
			greyButtons();
			
		}else if(terrain1.winner==3) {
			g.setColor(Color.blue);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("AI won the game !",(terrain1.screen_width - metrics1.stringWidth("AI won the game !"))/2,terrain1.screen_height-terrain1.screen_height/6);
			
			greyButtons();
			
		//Dessine le nom du joueur 
		}else if(terrain1.color) {
			g.setColor(Color.orange);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Player 1",(terrain1.screen_width - metrics1.stringWidth("________"))/2,terrain1.screen_height-terrain1.screen_height/6);
		}else if(terrain1.isAi){
			g.setColor(Color.blue);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("AI turn",(terrain1.screen_width - metrics1.stringWidth("________"))/2,terrain1.screen_height-terrain1.screen_height/6);
		}else {
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,UNIT_SIZE/2));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Player 2",(terrain1.screen_width - metrics1.stringWidth("________"))/2,terrain1.screen_height-terrain1.screen_height/6);
		}
		
		
	}
	
	


	
	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i=0;i<DIM_Y;i++) {
			if(e.getSource()==buttons[i]) {
				//on ajoute le jeton
				terrain1.ajoutPiece(i,g);
				//on passe du joueur 1 au joueur 2
				terrain1.color=!terrain1.color;
			}
		}
		
		//redessine par dessus :
		repaint();
		
	}


	// Lancement du chrono
	static void Go_Chrono() {
		chrono = java.lang.System.currentTimeMillis() ;
	}

	// Arret du chrono
	static void Stop_Chrono() {
		long chrono2 = java.lang.System.currentTimeMillis() ;
		long temps = chrono2 - chrono ;
		System.out.println("Temps ecoule = " + temps + " ms") ;
	}


	

}
