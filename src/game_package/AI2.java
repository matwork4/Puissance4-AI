package game_package;

import java.awt.Graphics;
import java.util.*;

public class AI2 {
	
	Terrain T;
	Graphics g;
	int[][] terrain;

	int hauteur,largeur;
	int choix = 0;
	AI_tools tools;
	int depth = 0;

	int[] scoreChoix;
	
	
	/* Constructeur de la classe AI2
	 * Permet d'initialiser l'IA numéro 2
	 */
	public AI2(Terrain T,int DIM_X, int DIM_Y,int depth) {
		
		this.depth = depth;
		
		//On initialise les outils de l'IA
		this.tools = new AI_tools(DIM_X,DIM_Y);
		
		this.terrain=tools.copyTerrain(T.terrain);
		this.T=T;
		this.hauteur=DIM_X;
		this.largeur=DIM_Y;
		
	}
	
	
	/* Fonction Play :
	 * Permet à l'IA de jouer son tour
	 */
	public void play(Terrain terrain1) {
		terrain=tools.copyTerrain(terrain1.terrain);
		int nbPlays=0;
		int profondeur = depth;
		
		//int osef = minMax(terrain, nbPlays, profondeur);

		scoreChoix = minMax(terrain, nbPlays, profondeur);

		scoreChoix = tools.changeCoup(terrain,largeur,scoreChoix);
		
		//permet de jouer un jeton à l'indice 0
		terrain1.ajoutPiece(scoreChoix[1],g);
		terrain1.color=!terrain1.color;
		
	}
	
	
	/* Algorithme minMax :
	 * 
	 */
	int[] minMax(int[][] tab, int nbPlays, int profondeur) {

		int[] scoreChoix = new int[2];

		//Test si le terrain est remplit 
		if(nbPlays==hauteur*largeur) {
			scoreChoix[0]=0;
			scoreChoix[1]=0;
			return scoreChoix;
		}
		
		//true si c'est le tour de l'IA, false sinon
		boolean AIturn = tools.isPair(nbPlays);
		
		for(int i=0;i<largeur;i++) {
			if(tools.colonneIsNotFull(i,tab)){
				if(tools.estVictorieux(i,tab,AIturn)) {
					int value = (hauteur*largeur+1 - nbPlays)/2;
					scoreChoix[0]=value;
					scoreChoix[1]=i;
					return scoreChoix;
				}
			}
		}
		
		int bestScore = hauteur * largeur;
		//int bestScore = 0;
		
		if(profondeur>0) {
			for(int i=0; i<largeur;i++) {
				if(tools.colonneIsNotFull(i,tab)) {
				
					int[][] newTab;
					newTab=tools.addJeton(i,tab,AIturn);
					scoreChoix = minMax(newTab, nbPlays-1, profondeur-1);
					scoreChoix[0]=-scoreChoix[0];

					//Il manque ça :
					if(i==0) {
						bestScore = scoreChoix[0];
						//choix = 0;
					}
					if(scoreChoix[0] > bestScore) {
						bestScore = scoreChoix[0];
						choix = i;
						//afficheTable(tab);
						//System.out.println("Score = "+scoreChoix[0] + " i = "+i);
					}
				}
			}
		}
		scoreChoix[0] = bestScore;
		return scoreChoix;
	}
	
	
	
	
}
