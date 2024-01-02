
/*
 * une interface de jeu classique qui hérite de l'interface MatrixInterface, représentant un niveau donné.
 * cette interface donne le contrat que doit vérifiée la classe l'implémentant, ansi que l'entête de ses méthodes
 *  
 * @invariant \forall(p1,p2 : Pos ;(getMatrix().get(p1)== Cell.ME) && getMatrix().get(p2)==Cell.ME ) => p1==p2 )
 * @invariant \forall( p : Pos, d : Dir ; onEdge(getMatrix(), p , d) <=> getMatrix().get(p)==Cell.WALL )
 * 
 * @author Mohamed Aziz Chahbi
 * @date 12/01/2023
 */
public interface ClassicGameInterface {
    
    /*
     * renvoie true si la case de la matrice à la position spécifiée représente une cible
     * @param p la position 
     * @returns true si la case de la matrice à la position spécifiée représente une cible
     * 
     * @ensures \result => (p.getX()<this.size() && p.getY()<this.size())
     * @ensures \result == 
     * @pure
     */
    public boolean target(Position p);

    /*
     * Teste la condition de victoire, c-a-d s'il y a bien un box sur 
     * toutes les cases de position contenue dans la collection cibles
     * 
     * @returns true si la condition de victoire est vérifiée
     * 
     * @ensures \result == \forall(p : Pos; this.get(p)==Cell.BOX)
     * @pure
     */
    public boolean victoire();

    
}
