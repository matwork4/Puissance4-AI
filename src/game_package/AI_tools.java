package game_package;

public class AI_tools {

	int hauteur,largeur;
	
	/* Constructeur de la classe AI_tools
	 * On initialise les méthodes qui seront utilisées 
	 * par les intelligences artificielles
	 */
	public AI_tools(int DIM_X, int DIM_Y) {
		
		this.hauteur=DIM_X;
		this.largeur=DIM_Y;	
	}
	
	
	/* ======================================
	 * Fonctions utilisées par les IAs
	 * ============== VVVVVV ================
	 */
	
	
	/* Boolean isPair :
	 * Cette fonction renvoie true si pair, false si impair
	 */
	public boolean isPair(int d) {
		boolean a = (d%2 == 0);
		//System.out.println("=============\n" + d + " est-il pair ? : "+a);
		return a;
	}
	
	
	/* Integer copyTerrain :
	 * Cette fonction permet de copier un terrain dans un autre
	 * afin de ne pas ré-écrire par dessus
	 */
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
	
	
	/* Boolean colonneIsNotFull :
	 * retourne true si la colonne n'est pas remplie et false sinon. 
	 * Elle permet de vérifier si il reste au moins une place pour jouer un jeton.
	 * (équivalent de la fonction [bool canPlay(int col) const;] )
	 */
	public boolean colonneIsNotFull(int indice_colonne, int[][] tab) {
		if(tab[0][indice_colonne]==0) {
			return true;
		}
		return false;
	}
	
	
	/* Boolean estVictorieux :
	 * retourne true si le joueur est victorieux losqu'il joue à cette position et false sinon.
	 * Cette fonction parcours le terrain pour vérifier si il y a une victoire 
	 * de l'un des deux joueurs.
	 * (équivalent de la fonction [bool isWinningMove(int col) const;] )
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
		
		//Test si le nouveau terrain obtenu est victorieux 
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
	
	
	/* Integer[][] addJeton :
	 * Cette fonction permet d'ajouter un jeton dans le terrain à un indice donné.
	 * Elle ajoutera un jeton du joueur ou de l'IA en fonction du tour effectif,
	 * puis renvoie un nouveau tableau qui représente le terrain.
	 */
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
	
	
	/* Void afficheTable :
	 * Cette fonction affiche le tableau qui représente le terrain dans la console.
	 */
	public void afficheTable(int[][] tab) {
		System.out.println("\n------- LA TABLE --------");
		for(int i=0;i<tab.length;i++) {
			System.out.println();
			for(int j=0;j<tab[i].length;j++) {
				System.out.print(tab[i][j] + " ");
			}
		}
	}

	/* int[] changeCoup
	* Si il veut jouer dans une colonne comlpète, on joue ailleurs
	 */
	public int[] changeCoup(int[][] terrain, int largeur, int[] scoreChoix){

		if(!colonneIsNotFull(scoreChoix[1],terrain)){
			for(int i=0;i<largeur;i++) {
				if(colonneIsNotFull(i,terrain)){
					scoreChoix[1]=i;
					i=largeur;
				}
			}
		}

		return scoreChoix;
	}



	
	/* ======================================
	 * Fonctions utilisées par l'IA numéro 3
	 * ============== VVVVVV ================
	 */
	//(pas encore de fonctions)
	//AI3 implémentera minMax avec la coupe Alpha / Beta
	
	
}
