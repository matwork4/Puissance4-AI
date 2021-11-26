package game_package;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JButton;

public class Terrain {
	
	public int[][] terrain;
	int decal_left = 0, decal_top = 0;
	int unit_size = 0, screen_height = 0, screen_width = 0;
	public boolean color = true; 
	boolean isAi;
	int winner = 0;
	int nbPlays = 0;
	
	//Constructeurs du terrain
	public Terrain() {
		this.terrain = new int[6][7];
		for(int i=0;i<terrain.length;i++) {
			for(int j=0;j<terrain[i].length;j++) {
				this.terrain[i][j]=0;
			}	
		}
	}
	
	public Terrain(int dimX, int dimY,int unit_size,int screen_height,int screen_width, boolean isai) {

		if(isai == false)
		{
			isAi = false;
		}
		else
		{
			isAi = true;
		}

		this.unit_size=unit_size;
		this.screen_height=screen_height;
		this.screen_width=screen_width;
		
		//initialise le terrain à 0
		this.terrain = new int[dimX][dimY];
		for(int i=0;i<terrain.length;i++) {
			for(int j=0;j<terrain[i].length;j++) {
				this.terrain[i][j]=0;
			}	
		}
		
		//centre le terrain à gauche
		int decal_left = (screen_width-(terrain.length+1)*unit_size)/2;
		this.decal_left=decal_left;
						
		//centre le terrain en haut
		int decal_top = (screen_height-(terrain[0].length+1)*unit_size)/2;
		this.decal_top=decal_top;
	}
	
	
	//Affiche le terrain dans la console
	public void affiche() {
		System.out.println("\n------------");
		for(int i=0;i<terrain.length;i++) {
			System.out.println();
			for(int j=0;j<terrain[i].length;j++) {
				System.out.print(terrain[i][j] + " ");
			}
		}
	}
	
	
	//dessine le terrain
	public void dessine(Graphics g) {
		
		g.setColor(Color.gray);
		
		for(int i=0;i<terrain.length;i++) {
			g.drawLine(decal_left,(i+1)*unit_size+decal_top,(terrain.length+1)*unit_size+decal_left,(i+1)*unit_size+decal_top);
		}
		for(int i=0;i<terrain[0].length;i++) {
			g.drawLine((i+1)*unit_size+decal_left,decal_top,(i+1)*unit_size+decal_left,(terrain[0].length-1)*unit_size+decal_top);
		}
		g.drawLine(decal_left,decal_top ,decal_left+(terrain.length+1)*unit_size,decal_top );
		g.drawLine(decal_left ,decal_top ,decal_left, decal_top+(terrain[0].length-1)*unit_size  );
		

		for(int i=0;i<terrain.length;i++) {
			for(int j=0;j<terrain[i].length;j++) {
				if(terrain[i][j]==1) {
					g.setColor(Color.orange);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}else if(terrain[i][j]==2) {
					g.setColor(Color.red);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}else if(terrain[i][j]==3) {
					g.setColor(Color.blue);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}else if(terrain[i][j]==-1) {
					g.setColor(Color.yellow);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}else if(terrain[i][j]==-2) {
					g.setColor(Color.magenta);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}else if(terrain[i][j]==-3) {
					g.setColor(Color.cyan);
					this.dessinePiece(this.findX(j), this.findY(i), g);
				}
				
			}
		}
		
		
	}
	
	//X c'est les abscisses, Y l'axe des ordonnées
	public void dessinePiece(int X,int Y,Graphics g) {
		int size = (int)Math.round(unit_size/1.2);
		
		g.fillOval(X,Y,size,size);
		//System.out.println("On dessine (normalement)");
		
	}
	
	//ajoute un jeton dans le tableau
	public void ajoutPiece(int posX, Graphics g) {
		boolean add=false;
		
		for(int i=terrain.length-1;i>=0;i--) {
			if(terrain[i][posX]==0) {
				if(color) {
					this.terrain[i][posX]=1;
				}else if(isAi){
					this.terrain[i][posX]=3;
				}else {
					this.terrain[i][posX]=2;
				}
				
				i=0;
				add=true;
				System.out.println("On ajoute un jeton");	
				
				int victoire=testVictoire();
				System.out.println("\n victoire = " + victoire);
				if(victoire==-1) {
					System.out.println("==== Victoire joueur 1 ! ====");
					this.winner = -victoire;
				}else if(victoire==-2) {
					System.out.println("==== Victoire joueur 2 ! ====");
					this.winner = -victoire;
				}else if(victoire==-3) {
					System.out.println("==== Victoire de l'IA ! ====");
					this.winner = -victoire;
				}
			}
		}
		//Si on n'ajoute pas le jeton alors on ne change pas de joueur;
		if(add==false) {
			this.color=!color;
		}else {
			//on incrémente le nombre de coups joués
			nbPlays++;
			System.out.println("Nb_plays = " + nbPlays);
		}
		
		this.affiche();
	}
	
	
	//Trouve la position X et Y du jeton sur le graphique
	public int findX(int posX) {
		int X = this.decal_left + this.unit_size*posX;
		return X+(int)Math.round(unit_size/10);
	}
	public int findY(int posY) {
		int Y = this.decal_top + this.unit_size*posY;
		return Y+(int)Math.round(unit_size/10);
	}
	
	
	
	public void setButton(JButton button,int posX) {
		int x = decal_left + posX*unit_size+5;
		int y = decal_top - (unit_size/2);
		int l1 = unit_size-10;
		int l2 = unit_size/3;
		
		button.setBounds(x,y,l1,l2);
	}
	
	//return 1 si p1 gagne, return 2 si p2 gagne, return 0 sinon
	public int testVictoire() {
		int newColor=0;
		for(int i=0;i<terrain.length;i++) {
			for(int j=0;j<terrain[i].length;j++) {
				
				if(terrain[i][j] != 0) {
					newColor=-terrain[i][j];
					//test victoire en largeur
					if(j+3 < terrain[i].length ) {
						if(terrain[i][j]==terrain[i][j+1] && terrain[i][j]==terrain[i][j+2] && terrain[i][j]==terrain[i][j+3]) {
							terrain[i][j]=newColor;
							terrain[i][j+1]=newColor;
							terrain[i][j+2]=newColor;
							terrain[i][j+3]=newColor;
							return terrain[i][j];
						}
					}
					//test victoire en hauteur
					if(i+3 < terrain.length ) {
						if(terrain[i][j]==terrain[i+1][j] && terrain[i][j]==terrain[i+2][j] && terrain[i+3][j]==terrain[i][j]) {
							terrain[i][j]=newColor;
							terrain[i+1][j]=newColor;
							terrain[i+2][j]=newColor;
							terrain[i+3][j]=newColor;
							return terrain[i][j];
						}
					}
					//test victoire en diagonale (de haut gauche à bas droite)
					if(i+3 < terrain.length && j+3 < terrain[i].length) {
						if(terrain[i][j]==terrain[i+1][j+1] && terrain[i][j]==terrain[i+2][j+2] && terrain[i+3][j+3]==terrain[i][j]) {
							
							terrain[i][j]=newColor;
							terrain[i+1][j+1]=newColor;
							terrain[i+2][j+2]=newColor;
							terrain[i+3][j+3]=newColor;
							return terrain[i][j];
						}
					}
					//test victoire en diagonale (de haut droite à bas gauche)
					if(i+3 < terrain.length && j-3 >= 0) {
						if(terrain[i][j]==terrain[i+1][j-1] && terrain[i][j]==terrain[i+2][j-2] && terrain[i+3][j-3]==terrain[i][j]) {
							terrain[i][j]=newColor;
							terrain[i+1][j-1]=newColor;
							terrain[i+2][j-2]=newColor;
							terrain[i+3][j-3]=newColor;
							return terrain[i][j];
						}
					}
				}
			}
		}
		
		return 0;
	}
	
	

	
}
