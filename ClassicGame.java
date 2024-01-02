import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

/*
 * Classe définissant permettant d'instancier un jeu classique et appeler
 * quelques méthodes utiles au jeu
 * 
 * @author Mohamed Aziz Chahbi
 * @date 12/01/2023
 */
public class ClassicGame 
                        implements ClassicGameInterface {

     Collection<Position> cibles;
     Matrice<Cell> gameBoard;
	

    /*
     * Crée un niveau de Sokoban
     * @param w la matrice qui représentera le plateau du jeu
     * @param cibles une collection de positions des cellules cibles
     * @requires w!=null 
     * @requires cibles!=null && !cibles.contains(null)
     * @ensures this.size()==w.size()
     * @ensures 
     * @ensures
     * @throws NullPointerException si w ou cibles est null, ou si cibles contient null
     */
    public ClassicGame(File file){
     
        this.cibles= new ArrayList<Position>();
        
        
        int x = 0; int y = 0;
		int symbole; int size_m = 0;
		try(FileInputStream fin = new FileInputStream(file)){
			// We read name of the matrice;
			char name = ((char)fin.read());
			//Will read '/n' after name of matrice
			fin.read();
			
			// We read size of the matrice
			while((symbole = fin.read()) != 10)
			{
				size_m = size_m*10 + symbole - 48; // We need to make this manipulation because we read ASCII char
				
			}
			
			this.gameBoard = new Matrice<Cell>(size_m, Cell.EMPTY, name);
			while((symbole = fin.read()) != -1)
			{
				
				switch(symbole)
				{
					case 35 :// #
						gameBoard.setElement(new Position(x,y), Cell.WALL);
						y++;
						break;
					case 65: // A
						gameBoard.setElement(new Position(x,y), Cell.ME);
						y++;
						break;
					case 66 : // B
						gameBoard.setElement(new Position(x,y), Cell.BOX);
						y++;
						break;

					case 97: //a
						this.cibles.add(new Position(x, y));
						gameBoard.setElement(new Position(x,y), Cell.ME);
						y++;
						break;
					case 98: //b
						this.cibles.add(new Position(x, y));
						gameBoard.setElement(new Position(x,y), Cell.BOX);
						y++;
						break;
					case 64: // '@'
						this.cibles.add(new Position(x, y));
						y++;
						break;
					case 10 : // '\n'
						x++; y = 0;
						break;
					default:
						y++;
						
				
				}
			}
		}
		catch(IOException e){
			System.out.println("Plobleme with your file");
		}
	
    		
    }

    public boolean target(Position p){
        return this.cibles.contains(p);
    }

    public boolean victoire(){
        for (Position p :cibles){
            if (gameBoard.getElement(p) != Cell.BOX || gameBoard.getElement(p)!=Cell.ME)
            	
                return false;
        }
        return true;
    }
    
}
