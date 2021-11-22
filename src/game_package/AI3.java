package game_package;

import java.awt.Graphics;
import java.util.*;

public class AI3 {
	
	Terrain T;
	Graphics g;
	int[][] terrain;
	//la case 0 correspond au score (lu par minmax), et la case 1 correspond au choix (entre 0 et 7)
	int[] scoreChoix;

	int hauteur,largeur, profondeur;
	int choix = 0;
	AI_tools tools;
	
	
	/* Constructeur de la classe AI2
	 * Permet d'initialiser l'IA num�ro 2
	 */
	public AI3(Terrain T,int DIM_X, int DIM_Y,int depth) {
		this.profondeur = depth;
		
		//On initialise les outils de l'IA
		this.tools = new AI_tools(DIM_X,DIM_Y);
		
		this.terrain=tools.copyTerrain(T.terrain);
		this.T=T;
		this.hauteur=DIM_X;
		this.largeur=DIM_Y;
		
	}
	
	
	/* Fonction Play :
	 * Permet � l'IA de jouer son tour
	 */
	public void play(Terrain terrain1) {
		terrain=tools.copyTerrain(terrain1.terrain);
		int nbPlays=0;
		int alpha = -2^10;
		int beta = 2^10;
		
		scoreChoix = minMax(terrain, nbPlays, profondeur, alpha, beta);
		
		// triche : si il veut jouer dans une colonne comlpète, on joue ailleurs
		if(!tools.colonneIsNotFull(scoreChoix[1],terrain)){
			for(int i=0;i<largeur;i++) {
				if(tools.colonneIsNotFull(i,terrain)){
					scoreChoix[1]=i;
					i=largeur;
				}
			}
		}
		
		
		
		//permet de jouer un jeton � l'indice 0
		terrain1.ajoutPiece(scoreChoix[1],g);
		terrain1.color=!terrain1.color;
		
	}
	
	
	/* Algorithme minMax :
	 * 
	 */
	int[] minMax(int[][] tab, int nbPlays, int profondeur, int alpha, int beta) {
		
		int[] scoreChoix = new int[2];
		
		//Test si le terrain est remplit 
		if(nbPlays==hauteur*largeur) {
			scoreChoix[0]=0;
			scoreChoix[1]=0;
			return scoreChoix;
		}
		
		//true si c'est le tour de l'IA, false sinon
		boolean AIturn = tools.isPair(nbPlays);
		
		//on regarde si le prochain coup est victorieux
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
		
		int max = (hauteur*largeur-1 - nbPlays)/2;
		if(beta>max) {
			beta = max;
			if(alpha >= beta) {
				scoreChoix[0]=beta;
				
				return scoreChoix;
			}
		}

		if(profondeur>0) {
			for(int i=0; i<largeur;i++) {
				if(tools.colonneIsNotFull(i,tab)) {
				
					int[][] newTab;
					newTab=tools.addJeton(i,tab,AIturn);
					scoreChoix = minMax(newTab, nbPlays-1, profondeur-1,-beta, -alpha);
					scoreChoix[0]=-scoreChoix[0];
					
					if(scoreChoix[0]>=beta) {
						//choix = i;
						System.out.println("Score = "+scoreChoix[0] + " i = "+scoreChoix[1]);
						return scoreChoix;
					}
					if(scoreChoix[0]>alpha) {
						choix = i;
						alpha=scoreChoix[0];
						System.out.println("Score = "+scoreChoix[0] + " i = "+scoreChoix[1]);
					}
					
					
					
				}
			}
		}
		
		scoreChoix[0]=alpha;
		return scoreChoix;
	}
	
	
	
	
}
