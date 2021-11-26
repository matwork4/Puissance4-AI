package AI_package;

import java.awt.Graphics;
import java.util.*;

import game_package.Terrain;

public class AI0 {
	
	Terrain T;
	Graphics g;
	int[][] tab;
	int choix=0;
	
	public AI0(Terrain T) {
		this.T=T;
		//this.tab=T.terrain;
		
	}
	
	public void play() {
		//this.tab=T.terrain.clone();
		
		tab=copyTerrain(T.terrain);
		
		int osef = minMax(4,tab[0].length,tab);
		
		T.ajoutPiece(choix,g);
		T.color=!T.color;
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
	
	
	
	public int minMax(int depth, int length, int[][] tab) {
		int[] ligne;
		ligne = new int[length];
		
		//isMax peut servir de 'couleur', c'est à dire pour savoir qui joue (couleur) dans la fonction ajoutTest
		boolean isMax = isPair(depth);
		System.out.println("=====\nisMax is : "+isMax);

		int[][] newTab;
		newTab = copyTerrain(tab);
		
		afficheTable(newTab);

		if(depth>0) {
			for(int i=0;i<length;i++) {
				
				if(!colonneIsFull(i,newTab)) {
					//ici on va appeler la fonction ajoutTest pour update le tableau
					System.out.println("\nUpdate du plateau ");
					
					newTab=ajoutTest(i,isMax,newTab);
					
					ligne[i]=minMax(depth-1,length,newTab);
				}else {
					//la ligne est pleine on ne peut donc pas ajouter de jeton ici
					ligne[i]=0;
				}
			}
		}else {
			for(int i=0;i<length;i++) {
				if(!colonneIsFull(i,newTab)) {
					newTab=ajoutTest(i,isMax,newTab);
					
					//Ici fonction qui permet de trouver un nombre représentant l'efficacité du coup 
					ligne[i]=efficacite(newTab);
					
				}else {
					ligne[i]=0;
				}
			}
		}
		afficheLigne(ligne);
		
		if(isMax) {
			System.out.println("--------------\nReturn Max");
			return findMax(ligne);
		}
		System.out.println("--------------\nReturn Min");
		return findMin(ligne);
	}
	
	public int efficacite(int[][] tab) {
		
		for(int i=0;i<tab.length;i++) {
			for(int j=0;j<tab[i].length;j++) {
				//ICI on va tester si il y a une puissance 4 effectuée ou moins 
				if(tab[i][j] == 3) {
					if(j+3 < tab[i].length ) {
						if(tab[i][j]==tab[i][j+1] && tab[i][j]==tab[i][j+2] && tab[i][j]==tab[i][j+3]) {
							return 4;
						}
					}
					//test victoire en hauteur
					if(i+3 < tab.length ) {
						if(tab[i][j]==tab[i+1][j] && tab[i][j]==tab[i+2][j] && tab[i+3][j]==tab[i][j]) {
							return 4;
						}
					}
					//test victoire en diagonale (de haut gauche à bas droite)
					if(i+3 < tab.length && j+3 < tab[i].length) {
						if(tab[i][j]==tab[i+1][j+1] && tab[i][j]==tab[i+2][j+2] && tab[i+3][j+3]==tab[i][j]) {

							return 4;
						}
					}
					//test victoire en diagonale (de haut droite à bas gauche)
					if(i+3 < tab.length && j-3 >= 0) {
						if(tab[i][j]==tab[i+1][j-1] && tab[i][j]==tab[i+2][j-2] && tab[i+3][j-3]==tab[i][j]) {
							return 4;
						}
					}
				}
			}
		}
		
		return 0;
	}
	
	//affiche la table
	public void afficheTable(int[][] tab) {
		System.out.println("\n------- LA TABLE --------");
		for(int i=0;i<tab.length;i++) {
			System.out.println();
			for(int j=0;j<tab[i].length;j++) {
				System.out.print(tab[i][j] + " ");
			}
		}
	}
	
	//affiche la ligne
	public void afficheLigne(int[] ligne) {
		System.out.println("------\nLA LIGNE : ");
		for(int i=0;i<ligne.length;i++) {
			System.out.print(ligne[i] + " ");
		}
	}
	
	
	//cette fonction permet d'ajouter un jeton de couleur virtuellement 
	public int[][] ajoutTest(int choix, boolean couleur, int[][] newTab){
		
		for(int i=newTab.length-1;i>=0;i--) {
			
			//System.out.println("choix = "+choix+" i = "+i);
			if(newTab[i][choix]==0) {
				if(couleur) {
					//On ajoute le jeton de l'IA
					newTab[i][choix]=3;
					i=-1;
				}else {
					//On ajoute le jeton du joueur
					newTab[i][choix]=1;
					i=-1;
				}
			}
			
		}
		
		return newTab;
	}
	
	
	
	
	public boolean isPair(int d) {
		boolean a = (d%2 == 0);
		//System.out.println("=============\n" + d + " est-il pair ? : "+a);
		return a;
	}
	
	//Renvoie le maximum d'une ligne
	public int findMax(int[] ligne) {
		int max=ligne[0];
		
		for(int i=0;i<ligne.length;i++) {
			if (ligne[i]>max){
				max=ligne[i];
				this.choix=i;
			}
		}
		return max;
	}
	
	//Renvoie le minimum d'une ligne 
	public int findMin(int[] ligne) {
		int min=ligne[0];
		
		for(int i=0;i<ligne.length;i++) {
			if (ligne[i]<min){
				min=ligne[i];
			}
		}
		return min;
	}
	
	public boolean colonneIsFull(int i, int[][] tab) {
		if(tab[0][i]==0) {
			return false;
		}
		return true;
	}
	
	
}
