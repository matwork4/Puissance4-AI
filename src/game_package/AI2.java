package game_package;

import java.awt.Graphics;
import java.util.*;

public class AI2 {
	
	Terrain T;
	Graphics g;
	int[][] terrain;

	int hauteur,largeur;
	//boolean AIturn=true;
	//int nbPlays=0;
	int choix = 0;
	
	
	public AI2(Terrain T,int DIM_X, int DIM_Y) {
		this.T=T;
		this.terrain=copyTerrain(T.terrain);
		
		this.hauteur=DIM_X;
		this.largeur=DIM_Y;

		
	}
	
	
	public void play(Terrain terrain1) {
		terrain=copyTerrain(terrain1.terrain);
		int nbPlays=0;
		int profondeur = 6;
		
		int osef = minMax(terrain, nbPlays, profondeur);
		
		//permet de jouer un jeton à l'indice 0
		terrain1.ajoutPiece(choix,g);
		terrain1.color=!terrain1.color;
		
	}
	
	
	int minMax(int[][] tab, int nbPlays, int profondeur) {
		//Test si le terrain est remplis 
		if(nbPlays==hauteur*largeur) {
			return 0;
		}
		
		//true si c'est le tour de l'IA, false sinon
		boolean AIturn = isPair(nbPlays);
		
		for(int i=0;i<largeur;i++) {
			if(colonneIsNotFull(i,tab)){
				if(estVictorieux(i,tab,AIturn)) {
					int value = (hauteur*largeur+1 - nbPlays)/2;
					return value;
				}
			}
		}
		
		int bestScore = hauteur * largeur;
		//int bestScore = 0;
		
		if(profondeur>0) {
			for(int i=0; i<largeur;i++) {
				if(colonneIsNotFull(i,tab)) {
				
					int[][] newTab;
					newTab=addJeton(i,tab,AIturn);
					int score = -minMax(newTab, nbPlays-1, profondeur-1);
					//Il manque ça : 
					if(i==0) {
						bestScore = score;
						//choix = 0;
					}
					if(score > bestScore) {
						bestScore = score;
						choix = i;
						//afficheTable(tab);
						System.out.println("Score = "+score + " i = "+i);
					}
				}
			}
		}
		
		return bestScore;
	}
	
	
	//Ajoute un jeton au terrain
	public int[][] addJeton(int indice_colonne, int[][] OldTab, boolean AIturn){
		int[][] tab;
		tab=copyTerrain(OldTab);
		
		int indice_ligne = -1;

		for(int i=hauteur-1;i>=0;i--) {
			if (tab[i][indice_colonne]==0){
				
				if(AIturn) {
					tab[i][indice_colonne]=3;
				}else {
					tab[i][indice_colonne]=1;
				}
				indice_ligne = i;
				i=-1;
			}
		}
		
		if(indice_ligne==-1) {
			System.out.println("===== Erreur : addJeton indice_ligne = -1 =====");
		}
		
		return tab;
	}
	
	
	public boolean isPair(int d) {
		boolean a = (d%2 == 0);
		//System.out.println("=============\n" + d + " est-il pair ? : "+a);
		return a;
	}
	
	
	
	
	
	public int[][] copyTerrain(int[][] terrain){
		int[][] newTerrain;
		newTerrain = new int[terrain.length][terrain[0].length];
		
		for(int i=0;i<terrain.length;i++) {
			for(int j=0;j<terrain[i].length;j++) {
				newTerrain[i][j]=terrain[i][j];
			}
		}
		
		return newTerrain;
	}
	
	
	//bool canPlay(int col) const;
	/* retourne true si la colonne n'est pas pleine 
	 * false sinon
	 */
	public boolean colonneIsNotFull(int indice_colonne, int[][] tab) {
		if(tab[0][indice_colonne]==0) {
			return true;
		}
		return false;
	}
	
	
	//bool isWinningMove(int col) const;
	/* retourne true si le joueur est victorieur losqu'il joue ici
	 * retourne false sinon
	 */
	public boolean estVictorieux(int indice_colonne, int[][] OldTab, boolean AIturn) {
		int[][] tab;
		tab=copyTerrain(OldTab);
		
		int indice_ligne = -1;
		
		for(int i=hauteur-1;i>=0;i--) {
			if (tab[i][indice_colonne]==0){
				
				if(AIturn) {
					tab[i][indice_colonne]=3;
				}else {
					tab[i][indice_colonne]=1;
				}
				indice_ligne = i;
				i=-1;
			}
		}
		
		if(indice_ligne==-1) {
			afficheTable(tab);
			System.out.println("indice_colonne = "+indice_colonne);
			System.out.println("===== Erreur : estVictorieux indice_ligne = -1 =====");
			return false;
		}
		
		
		/* Test si le nouveau terrain obtenu est victorieux 
		 */
		
		for(int i=0;i<tab.length;i++) {
			for(int j=0;j<tab[i].length;j++) {
				if(tab[i][j] != 0) {
				
					//test victoire en largeur 
					if(j+3 < tab[i].length ) {
						if(tab[i][j]==tab[i][j+1] && tab[i][j]==tab[i][j+2] && tab[i][j]==tab[i][j+3]) {
							
							return true;
						}
					}
					//test victoire en hauteur
					if(i+3 < tab.length ) {
						if(tab[i][j]==tab[i+1][j] && tab[i][j]==tab[i+2][j] && tab[i+3][j]==tab[i][j]) {
							
							return true;
						}
					}
					//test victoire en diagonale (de haut gauche à bas droite)
					if(i+3 < tab.length && j+3 < tab[i].length) {
						if(tab[i][j]==tab[i+1][j+1] && tab[i][j]==tab[i+2][j+2] && tab[i+3][j+3]==tab[i][j]) {
							
							return true;
						}
					}
					//test victoire en diagonale (de haut droite à bas gauche)
					if(i+3 < tab.length && j-3 >= 0) {
						if(tab[i][j]==tab[i+1][j-1] && tab[i][j]==tab[i+2][j-2] && tab[i+3][j-3]==tab[i][j]) {
							return true;
						}
					}
				}
			}
		}
			
		return false;
	}
	
	public void afficheTable(int[][] tab) {
		System.out.println("\n------- LA TABLE --------");
		for(int i=0;i<tab.length;i++) {
			System.out.println();
			for(int j=0;j<tab[i].length;j++) {
				System.out.print(tab[i][j] + " ");
			}
		}
	}
	
	
}
