import java.io.*;
import java.util.Collection;

/*Classe concrète de RecursiveSokoban héritant de RecursiveGame
*
*implémentant la méthode @move() pour le movement, enterInWorld() pour changer de monde
*'enterInWorld' fait le changement comme si les mondes étaient cycliques dans l'univers des matrices 
*On a aussi une implémentation de swapMatrix qui échange deux cellules dans un monde 
*/
public class RecursiveSokoban extends RecursiveGame {
    

    //constructeur faisant appel au constructeur de la superclasse
    public RecursiveSokoban (File file){
        super(file);
    }

    /*
     * Pour avancer dans une direction 
     * 
     * @param d
     *          la direction dans laquelle je joueur souhaite avancer
     * 
     * @requires d !=  null
     * 
     * la méthode récupère la position du joueur dans l'univers ou il se trouve et essaie de déplacer dans la {@code Direction} donnée
     * 
     * @ensures q UPos of RecursiveCell.ME && !hasNext(q, d) ==> aucun changement
     * 
     * @ensures hasNext(q, d) && next(q, d) == RecursiveCell.EMPTY ==> RecursiveCell.ME && RecursiveCell.EMPTY changent de place et on affiche un nouveau écran
     * 
     * @ensures hasNext(q, d) && next(q, d) == RecursiveCell.WALL ==> aucun changement
     * 
     * sinon on teste au fur et à mesure la prochaine cellule dans cette direction tant qu'elle est une boite
     * et on arrete lorsqu'on trouve une RecursiveCell.EMPTY ou une RecursiveCell.WALL
     * 
     * si la dernière cellule testée est vide, elle échange sa place avec la boite placée apres le joueur dans la direction d, 
     * et le joueur prend l'ancienne place cette-dite boite
     * 
     * sinon si c'est un mur et que au cours du parcours, il n'y a pas eu de boite monde, il n'y a pas de mouvements
     * s'il y a eu une boite monde, la cellule se placant avant la dernière boite monde entre dedans et on fait déplacer les autres cellules avant
     * après on fait un zoom sur cette boite monde
     * 
     * Pour l'instant le mécanisme pour zoomer n'a pas pu etre implémenter
     * 
     * 
     * 
     * @throw new NullPointerExecption if d == null
     */
    public void move(Direction d){

        if(d == null){
            throw new NullPointerException("L'argument direction doit etre non null");
        }

        boolean moved = false; // variable témoin pour savoir si le mouvement n'est pas possible ou
                            //si le mouvemrnt a été fait

        ArrayList<RecursiveCell> mesCell = new ArrayList<RecursiveCell>(); //variable pour stocker les cellules au cours du chainage

        for(int n = 0; n < univers.usize() && !moved; n++){ // parcours dans les mondes

            Matrice<RecursiveCell> uneMatriceMonde = getWorld(n);

            for(int i = 0; i < uneMatriceMonde.size() && !moved; i++){

                for(int j = 0; j < uneMatriceMonde.size() && !moved; j++){

                    Position p = new Position(i, j);
                    
                    if(uneMatriceMonde.getElement(p) == RecursiveCell.ME){ //cellule correspondant au joueur

                        UPos uposMe = new UPos(n, p); // Position du joueur dans l'espace

                        if(!(hasNext(uposMe, d))){ //on ne peut pas bouger
                            moved = false;
                            break; // on sort de la boucle j
                        }
                        
                        
                        UPos uposNext = next(uposMe, d); //Position de la cellule juste après le joueur dans la direction d
                        RecursiveCell nextCell = univers.getElement(uposNext); //cellule suivant le joueur dans la direction d

                        
                        switch(nextCell){

                            case EMPTY:
                                //  le joueur va dans la cellule vide
                                swapMatrix(uposMe, uposNext); 
                                moved = true;
                                break;  
                                
                            case WALL: // on ne peut pas bouger
                                moved = true;
                                break;

                            default: //soit une boite simple soit une boite-monde
                                
                                UPos uposLastWorld = null; //variable pour stocker la dernière position d'une boite monde dans le chainage
                                int k = 0; //variable pour pour stocker la position de uposLastWorld dans mesCell
                                UPos uposTemp1 = uposNext; //variable pour mettre la position de la cellule avant la derniere boite monde, 
                                                        //cette variable sera mis à jour seulement quand il y a une boite monde

                                UPos uposTemp2 = uposNext; // variable pour stocker la position avant upoBoxnext
                                UPos uposBoxNext = next(uposNext, d); // variable pour mettre la position d'une cellule au fur et à  mesure dans le chainage
                                                               

                                mesCell.add(univers.getElement(uposMe));
                                mesCell.add(nextCell);

                                if(nextCell.isWorldBox()){
                                    uposLastWorld = uposNext;
                                    k = 1;
                                }
                                

                                //Tant que c'est pas une case vide et que c'est pas un mur
                                while (!(univers.getElement(uposBoxNext) == RecursiveCell.EMPTY) && !(univers.getElement(uposBoxNext) == RecursiveCell.WALL)) {

                                    if(univers.getElement(uposBoxNext).isWorldBox()){
                                        uposLastWorld = uposBoxNext;
                                        uposTemp1 = uposTemp2;
                                        k = mesCell.size() ; // car la derniere boitemonde n'est pas encore ajoutée
                                    }

                                    mesCell.add(univers.getElement(uposBoxNext));
                                    uposTemp2 = uposBoxNext;
                                    uposBoxNext = next(uposBoxNext, d);
                                    
                                }

                                switch(univers.getElement(uposBoxNext)){

                                    case EMPTY:
                                        // la boite apres le joueur (dans la direction d) va dans la case vide
                                        swapMatrix(uposNext, uposBoxNext);
                                        // Le joueur prend la place libérée par cette dernière boite
                                        swapMatrix(uposMe, uposNext);
                                        moved = true;
                                        break;
                                    case WALL:
                                        
                                        //cas ou il n'y a pas de boite monde dans le chainage
                                        if(uposLastWorld == null){
                                            moved = true;
                                    	    break;
                                        }

                                        // le reste du code c'est le cas ou il y a une boite monde


                                        if(uposLastWorld.equals(uposNext)){ //cas ou la derniere boite monde se trouve juste après le joueur dans la direction d
                                            //le joueur entre dans cette boite-monde
                                            enterInWorld(RecursiveCell.ME, uposME);
                                            
                                            moved = true;
                                    	    break;
                                        }

                                        //on fait entrer la boite qui se trouve dans la position k-1 dans la dernière boite-monde du chainage
                                        enterInWorld(univers.getElement(uposTemp1), uposTemp1);


                                        //et on avance les autres cellules les unes apres les autres
                                        switch(d){
                                            case Direction.UP:
                                                for( ; k > 1; k--){
                                                    swapMatrix(new UPos(n, new Position(i, j-k+1)), new UPos(n, new Position(i, j-k+2)));
                                                }
                                                moved true; 
                                                break;

                                            case Direction.DOWN:
                                                for( ; k > 1; k--){
                                                    swapMatrix(new UPos(n, new Position(i, j+k-1)), new UPos(n, new Position(i, j+k-2)));
                                                }
                                                moved true; 
                                                break;

                                            case Direction.LEFT:
                                                for( ; k > 1; k--){
                                                    swapMatrix(new UPos(n, new Position(i-k+1, j)), new UPos(n, new Position(i-k+2, j)));
                                                }
                                                moved true; 
                                                break;

                                            case Direction.RIGHT:
                                                for( ; k > 1; k--){
                                                    swapMatrix(new UPos(n, new Position(i+k-1, j)), new UPos(n, new Position(i+k-2, j)));
                                                }
                                                moved true; 
                                                break;

                                        }
                                        
                                        
                                    default:
                                        moved = true;
                                        break;
                                }
                                                      
                                moved = true;
                                break;
                                
                        }
                    }
                }
            }
        }
    }
    
    
    /*
     * Changer deux cellules de place
     * 
     * @param 
     *          upo1 position du premier élément dans l'univers
     * 
     * @param
     *          upo2 position du second élément dans l'univers
     * 
     * @requires upo1 != null && upo2 != null
     * 
     * @requires upo1.getIndexWorld() == upo2.getIndexWorld()
     * 
     * @ensures \old univers.getElement(upo1) == univers.getElement(upo2)
     * 
     * @ensures \old univers.getElement(upo2) == univers.getElement(upo1)
     */
    public void swapMatrix(UPos upo1, UPos upo2){
        if((upo1 == null) || (upo2 == null)){
            throw new NullPointerExeption("les positions doivent etre non null");
        }

        if(upo1.getIndexWorld() != upo2.getIndexWorld()){
            throw new IllegalArgumentException("Les éléments doivent etre dans le meme monde")
        }

        RecursiveCell temp = univers.getElement(upo1);

        univers.setElement(upo1, univers.getElement(upo2));
        univers.setElement(upo2, temp);
        show(); //pour afficher la matrice mise à jour
    }

    /*
    *copie de la méthode show de la classe classicgame
    *
    *cette méthode doit prendre en compte l'indice du monde à afficher
    *
    *Rien ne garantit que gameBoard prend en compte la matrice sur laquelle le jeu se déroule, 
    *j'ai fait cette remarque l'implémentation de la superclasse n'est pas encore complète
    */ 
    public void show(){
		boolean cible = false;
		for(int i = 0; i < gameBoard.size(); i++){
    			System.out.print("\n");
            		for(int j = 0; j < gameBoard.size(); j++){
            			cible = false;
            			if(cibles.contains(new Position(i,j)))
            				cible = true;
            					
            			switch(gameBoard.getElement(new Position(i,j))){
            				case ME:
            					if( cible == true)
            						System.out.print("A");
            					else
            						System.out.print("a");
            					break;
            				case BOX :
            					if( cible == true)
            						System.out.print("+");
            					else
            						System.out.print("*");
            					break;
            				case EMPTY:
            					if( cible == true)
            						System.out.print("@");
            					else
            						System.out.print(" ");
            					break;
            				case WALL:
            					System.out.print("#");
            					break;
            			
            			};
            			
            		}
            }
            System.out.print("\n");
            if(victoire() == true){
            	System.out.print("WIN" + "\n");
            }    					
	}	

}

/**Méthode qui permet à une cellule (personnage ou boite) de quitter un monde et d'aller dans le monde suivant. elle n'assure pas l'affichage 
*@param c
*           La cellule qui change de monde
*@param q
*           Sa position dans l'univers
*
*@requires c != WALL && c != EMPTY
*
*@ensures univers.getElement(q) == RecursiveCell.Empty
*
*Cette cellule se place dans le prochain monde suivant un ordre cyclique dans la structure arrayList
*
*
*/
public void enterInWorld(RecursiveCell c, UPos q){
    if((c == RecursiveCell.WALL) || (c == RecursiveCell.EMPTY)){
        throw new IllegalArgumentException("Cette cellule ne peut pas changer de monde");
    }
    
    RecursiveCell temp = c;
    univers.setElement(q, RecursiveCell.EMPTY);
    int oldWorld = q.getIndexWorld();
    int newWorld = oldWorld < univers.size() - 1? univers.size() : 0 ;
    UPos q2 = new UPos(newWorld, q.getPosition());
}