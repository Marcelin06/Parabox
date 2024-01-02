import java.io.*;

/**
 * Définition d'une matrice carée de taille strictement positive
 * La colonne la plus à gauche a pour indice 0
 * La première ligne en partant vers le bas a pour indice 0
 * 
 * @création 10 janvier 2023
 * @modification 23 mars 2023
 */
public class Matrice<E extends CellInterface> implements Cloneable {

    private E[][] matrix;
	private char name;
    
     public Matrice(int m, E c, char name){
  		
     	E[][] newa = (E[][]) new CellInterface[m][m];
     	for(int i = 0; i < m; i ++){
     		for(int j = 0; j <m; j++){
     			newa[i][j] = c;
     		}
     	}
     	this.matrix = newa;
     	this.name = name;
     	
     }
     
     
     
     /**
     * Renvoi la taille d'une matrice carrée
     * @param m
     *          La matrice d'éléments E
     * @requires m != null
     * @ensures \result > 0
     * @return la dimension de la matrice carrée
     * 
     * @throws NullPointerException <==> m == null
     * @pure
     */
    public int size(){
      
        
            return matrix[0].length;
    }

    /**
     * Une manière de renommer l'opération _[_] de la spécification
     * Renvoi un élément qui occupe une position donnée dans la matrice
     * à condition que cette position existe
     * @param m
     *          La matrice d'éléments E
     * @param p
     *          La position
     * @requires m != null && p != null
     * @requires p.getX() < size(m) && p.getY() < size(m)
     * @ensures \result != null
     * @return un élément de la matrice
     * 
     * @throws NullPointerException <==> m == null || p == null
     * @throws IllegalArgumentException <==> p.getX() >= size(m) || p.getY() >= size(m)
     * @pure
     */
    public E getElement (Position p){
        
        if(  p == null)
            throw new NullPointerException("La matrice ou la position spécifiée est nulle");
        if( p.getX() >= size() ||  p.getY() >= size())
            throw new IllegalArgumentException("La position ne doit pas être plus grand que la matrice");
        E c = matrix[p.getX()][p.getY()];
        
        return c;
    }

    /**
     * Cette méthode teste si une position donnée est sur le bord d'une matrice en fonction de la direction
     * Renvoi un booléen
     * @param m 
     *          La matrice
     * @param p
     *          La position
     * @param d
     *          La direction
     * @requires (m != null) && (p != null) && (d != null)
     * @ensures onEdge(m, p, Up) ⇔ p.getY() = size(m) − 1
     * @ensures onEdge(m, p, Down) ⇔ p.getY() = 0
     * @ensures onEdge(m, p, Left) ⇔ p.getX() = 0
     * @ensures onEdge(m, p, Right) ⇔ p.getX() = size(m) − 1
     * @return true or false
     * 
     * @throws NullPointerException <==> (m == null) || (p == null) || (d == null)
     * @pure
     */
    public boolean onEdge(Position p, Direction d){

            if((p == null) || (d == null))
                throw new NullPointerException("Le parametre ne doit pas être null");

            if(d== Direction.LEFT){
                return p.getX() == 0;
            }
            if(d== Direction.UP){
                return p.getY() == size() - 1;
            }
            if(d== Direction.DOWN){
                return p.getY() == 0;
            }
            if(d== Direction.RIGHT){
                return p.getX() == size() - 1;
            }
            return false;
    }

    /**
     * Autre manière renommer l'opération _$_ de la spécification Matrix[Elem]
     * Renvoi une position de cette {@code Matrice} en fonction 
     * d'une position et une direction données
     * @param p
     *          la position donnée
     * @param d
     *          la direction donnée
     * 
     * @requires (p != null) && (d != null)
     * @requires onEdge(this, p, d)
     * 
     * @ensures \result != null
     * @ensures nextPosition(p, Up) = (p.getX(), p.getY() + 1) // or \result.getX() = p.getX() && \result.getY() = p.getY() + 1
     * @ensures nextPosition(p, Down) = (p.getX(), p.getY() - 1) // or \result.getX() = p.getX() && \result.getY() = p.getY() -1
     * @ensures nextPosition(p, Left) = (p.getX() + 1, p.getY()) // or \result.getX() = p.getX() + 1 && \result.getY() = p.getY()
     * @ensures nextPosition(p, Right) = (p.getX() - 1, p.getY()) // or \result.getX() = p.getX() - 1 && \result.getY() = p.getY()
     * @return une position de cette matrice
     * @throws IllegalArgumentExcepiton <==> onEdge(this, p, d)
     * @throws NullPointerExeption <==> (p == null) || (d == null)
     * 
     * @pure
     */
    public Position nextPosition(Position p, Direction d){
        if(onEdge(p, d))
            throw new IllegalArgumentException("La position est au bord de la matrice");
        if((p == null) || (d == null)){
            throw new NullPointerException("Les parametres ne doivent pas être null");
        }
        Position res = new Position(0, 0);
        
        
        if(d== Direction.RIGHT){
        	
            res.setX(p.getX());
            res.setY(p.getY() + 1); 
        }
        if(d== Direction.LEFT){
        	
           res.setX(p.getX());
           res.setY((p.getY() - 1)); 
           
        }
        if(d== Direction.DOWN){
        	
            res.setX(p.getX() + 1);
            res.setY(p.getY()); 
            
        }
        if(d== Direction.UP){
            res.setX(p.getX() - 1);
            res.setY(p.getY());
             
        }
        
        return res;
    }

    /**
     * Renvoi une {@code Position} sur le bord
     * @param m
     *          la matrice
     * @param d
     *          la direction
     * @requires m != null && d != null
     * 
     * @ensures d == Up <==> \result.getX() == size(m)/2 && \result.getY() == 0
     * @ensures d == Down <==> \result.getX() == size(m)/2 && \result.getY() == size(m) - 1
     * @ensures d == Left <==> \result.getX() == size(m) - 1 && \result.getY() == size(m)/2
     * @ensures d == Right <==> \result.getX() == 0 && \result.getY() == size(m)/2
     * @return
     * 
     * @throws NullPointerException <==> m == null || d == null
     * @pure
     */
    public Position fromEdge(Direction d){
        if(d== null)
            throw new NullPointerException("Les parametres ne doivent pas être null");
        
        Position res = new Position(0, 0);
        if(d== Direction.UP){
            res.setX(size()/2);
            res.setY(0); 
        }
        if(d== Direction.DOWN){
            res.setX(size()/2);
            res.setY(size() - 1 ); 
        }
        if(d== Direction.RIGHT){
            res.setX(size() - 1);
            res.setY(size()/2); 
        }   
        if(d== Direction.LEFT){
            res.setX(0);
            res.setY(size()/2); 
        }    
        return res;
    }

    /**
     * Une autre facon de nommer l'opération _[_]<-_ de la spécification Matrix[Elem]
     * Insertion d'un {@code Elément} à une {@code Position} donnée dans une {@code Matrice} donnée
     * et renvoi une nouvelle matrice avec l'élément ajouté
     * 
     * @param m
     *          la matrice
     * @param p
     *          la position
     * @param e
     *          l'élément
     * @requires m != null && p != null && e != null
     * 
     * @ensures getElement(insererUnElement(m, p, e), p) == e
     * @ensures !(p.equals(p')) <==> getElement(insererUnElement(m, p, e), p') == getElement(m, p')
     * @ensures size(insererUnElement(m, p, e)) == size(m)
     * 
     * @throws NullPointerException <==> m == null || p == null || e == null
     * 
     */
    public void setElement( Position p, E c){
        if(p == null || c == null)
            throw new NullPointerException("Les parametres ne doivent pas être null");
        
        matrix[p.getX()][p.getY()] = c;
        
    }

    public E[][] getMatrice(){
        return this.matrix;
    }
	

	@Override
	public Matrice clone() {
	    	try {
		// Call the clone method of the Object class to create a shallow copy
		Matrice<E> cloneMatrice = (Matrice<E>) super.clone();
		
		// Create a new matrix and deep copy each element
		E[][] clonedMatrix = (E[][]) new CellInterface[size()][size()];
		for (int i = 0; i < size(); i++) {
		    for (int j = 0; j < size(); j++) {
		        clonedMatrix[i][j] = matrix[i][j];
		    }
		}
		
		// Set the cloned matrix in the clone of the Matrice object
		cloneMatrice.matrix = clonedMatrix;
		
		return cloneMatrice;
	    } catch (CloneNotSupportedException e) {
		// Should not happen, since we implement Cloneable
		throw new InternalError(e);
	    }
	} 
    

}
