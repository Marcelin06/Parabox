/*
 * interface de ChainingSokoban, la classe ChainingSokoban doit vérifier le contrat de cette interface
 * cette interface ajoute la possibilité de bouger plusieurs boites à la fois en utilisant "backtracking" 
 */


public interface ChainingSokobanInterface extends ClassicGameInterface{
     /**
     * @param c 
     *          la cellule qu'on essaye de bouger
     * @param w 
     *          la matrice du jeu
     * @param p
     *          la poisition à laquelle on bouge la cellule
     * @param d
     *          la direction dans laquelle on essaye de déplacer la cellule
     * @requires c!=null && w!=null && p!=null && d!=null
     * 
     * @ensures if(w.getElement(p) == Empty) => (moveAt(c, w, p, d) = w.insererUnElement(p,c))
     * 
     * @ensures if(w.getElement(p) == Wall) => (moveAt(c, w, p, d) = null)
     * 
     * @ensures if(w.getElement(p) != Empty && w.getElement(p) != Wall && w′ = moveAt(w.getElement(p), w.insererUnElement(p,Empty), p.nextPosition(d), d) && w' !=null)
     * =>  moveAt(c, w, p, d) = w'.insererUnElement(p,c)
     * 
     * @return la nouvelle matrice après le bougement de la cellule ou retourner une matrice nulle
     * 
     * @throws NullPointerException <==> c==null || w==null || p==null || d==null
     *
     */
    //public Matrice<Cell> moveAt(Cell c, Matrice w, Position p, Direction d);
    
    /**
     * @param w 
     *          la matrice du jeu
     * @param d
     *          la direction du mouvement
     * 
     * @requires w!=null && d != null 
     * 
     * @ensures if(w.getElement(p) == ME && w′ = moveAt(w[p], w.insererUnElement(p,Empty), p.nextPosition(d),d) && w' != null)
     * => move(w,d) = w'
     * 
     * @ensures if(w.getElement(p) == ME && moveAt(Me,w.insererUnElement(p,Empty),p.nextDirection(p),d) == null)
     * => move(w,d) = w
     *  
     * @throws NullPointerException if ( w==null || d == null)
     * 
     * @return la matrice après le mouvement de "ME" ou retourner une matrice nulle
     */
    public void move(Direction d);
   
}
