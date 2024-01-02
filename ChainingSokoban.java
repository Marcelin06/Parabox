/*
 * Cette classe implement ChainingSokobanInterface
 */
import java.io.*;
 import java.util.Collection;
public class ChainingSokoban extends ClassicSokoban implements ChainingSokobanInterface{
    
    /**
     * Crée un niveau de ChainingSokoban, en appelant le constructeur de ClassicSokoban
     * @param w
     *          la matrice du jeu
     * @param cibles
     *          une collection de positions des cellules cibles
     * @requires w!=null && cibles!=null && !cibles.contains(null)
     * 
     * @throws NullPointerException si w ou cibles est null, ou si cibles contient null
     */
    
     
    //constructeur
    public ChainingSokoban(File file){
        super(file);
        
           
      }
   
    public void move(Direction d){
    	if( d == null)
     	       throw new NullPointerException("Un argument est null");
        boolean moved = false;   
            for(int i=0; i<gameBoard.size() && !moved; i++){
            
                for(int j=0; j<gameBoard.size(); j++){
                
                    if ( gameBoard.getElement(new Position(i,j))==Cell.ME){
                        Position posMe= new Position(i,j);
                        Position posNext= gameBoard.nextPosition(posMe,d);
                        switch (gameBoard.getElement(posNext)) {
                            case EMPTY:
                                sauvegarderPrecedente();
                                swapMatrix(posMe,posNext);
                                moved = true;
                                break;
                            case WALL: 
                                break;
                            case BOX:
                                Position posBoxNext = gameBoard.nextPosition(posNext,d);
                                while (gameBoard.getElement(posBoxNext) == Cell.BOX) {
                                    posBoxNext = gameBoard.nextPosition(posBoxNext, d);
                                }
                                switch(gameBoard.getElement(posBoxNext)){
                                    case EMPTY:
                                    	sauvegarderPrecedente();
                                        //On echange la cellule EMPTY avec la BOX qui est juste aprés le joueur.
                                        swapMatrix(posNext, posBoxNext);
                                        // Maintenant, on bouge le joueur.
                                        swapMatrix(posMe, posNext);
                                        moved = true;
                                    case WALL:
                                    	break;
                                }
                                break;
                            default:
                                break;
                    }
                    break;
                }
                
            }
        }
    }
}
