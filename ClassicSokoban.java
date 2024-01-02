/*
 * classe implémentant l'inteface ClassicSokobanInterface
 */
import java.io.*;

public class ClassicSokoban extends ClassicGame
                    implements ClassicSokobanInterface{

    //attributs
    private UI ui; 
    
    //La matrice précédente
    private Matrice<Cell> matricePrecedente;
    
    //constructeur
    public ClassicSokoban(File file){
        super(file);
        
        this.ui = new UI(this.gameBoard,this.cibles);
        ui.addKeyListener(new MovePlayer(this));
        show();    
      }
        
    

   /* public boolean movable(Position p, Direction d){
        return this.get(p)==Cell.EMPTY;
    }*/

    
    public void move(Direction d){
    
    	
    	
        for(int i = 0; i < gameBoard.size(); i++){
        
            for(int j = 0; j < gameBoard.size(); j++){
            
                if ( gameBoard.getElement(new Position(i,j))==Cell.ME){
                	
                    Position posMe= new Position(i,j);
                    Position posNext= gameBoard.nextPosition(posMe,d);
                    
                    switch (gameBoard.getElement(posNext)) {
                        case EMPTY:
                            sauvegarderPrecedente();			
                            swapMatrix(posMe,posNext);
                            break;
                            
                        case WALL: 
                            break;
                            
                        case BOX:
                            Position posAfterNext= gameBoard.nextPosition(posNext,d);
                            if(gameBoard.getElement(posAfterNext)==Cell.EMPTY){
                                sauvegarderPrecedente();
                                swapMatrix(posMe,posNext);
                                //maintenant ME a avancé vers la prochaine case
                                swapMatrix(posMe,posAfterNext);
                            }
                            break; 
                                              
                        default:
                            break;
                    }
                }
            }
        }
    }
	public void sauvegarderPrecedente(){
		this.matricePrecedente = this.gameBoard.clone();
	}	
    public void swapMatrix(Position p1, Position p2){
    	
        Cell temp= gameBoard.getElement(p1);
        gameBoard.setElement(p1,gameBoard.getElement(p2) );
        gameBoard.setElement(p2, temp);
   	show();
        ui.swap(this.gameBoard,this.cibles);
      	
    }
    
    public void ctrlZ(){
    	this.gameBoard = this.matricePrecedente.clone();
    	show();
    	ui.swap(this.gameBoard,this.cibles);
    }
	
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
