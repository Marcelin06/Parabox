import java.io.*;
import java.util.*;

/*un univers contient des matrices-mondes (anciennes matrices).
 * c'est un tableau de matrices. 
 * c'est en fait les differents mondes (boites récursives World(n)
 *  représentant chacune une matrice) qui sont dans notre gameboard.
 */
public class Univers {
    
    //ATTENTION !!!!!
    //IL VA FALLOIR modifier la classe Matrice pour la rendre générique:
    //elle peut prendre soit des Cell ou des RecursiveCell
    //creer une interface CellInterface commune à ces deux classes? 
    private List<Matrice<RecursiveCell>> univers;  

    /*Crée un univers vide */
    public Univers(){
        univers= new ArrayList<Matrice<RecursiveCell>>();
    }

    /*Crée un univers à partir d'une @param listeMondes
     */
    public Univers(List<Matrice<RecursiveCell>> listeMondes){
        univers= new ArrayList<Matrice<RecursiveCell>>(listeMondes);
    }

    /*renvoie la taille de l'univers, c-a-d le nombre de monde qu'il contient */
    public int usize(){
        return univers.size();
    }

    public void addWorld(Matrice<RecursiveCell> world){
        univers.add(world);
    }

    /*c'est l'operation (_)_ dans la spec
     * renvoie le monde numéro @param indexWorld qui n'est rien d'autre 
     * qu'une Matrice<RecursiveCell>
     */
    public Matrice<RecursiveCell> getWorld(int indexWorld){
        if (indexWorld<0 || indexWorld>=usize() )
            throw new IllegalArgumentException("index non coforme a un monde");  
        return univers.get(indexWorld);
    }

    /*l'opérations _[_]
     * ça renvoie l'élément situé à @param UPos q
     * c-a-d l'élément dans le monde m et de position p dans ce monde
     */
    public RecursiveCell getElement(UPos q){
        int m=q.getIndexWorld();
        Position p=q.getPosition();
        return getWorld(m).getElement(p);
    }
    
    /*l'opération _[_]<-_
     * mettre @param e dans cet univers à la position @param q 
     */
    public void setElement( UPos q, RecursiveCell e){
        if(q == null || e == null)
            throw new NullPointerException("Les parametres ne doivent pas être null");
        int indexWorld=q.getIndexWorld();
        getWorld(indexWorld).setElement(q.getPosition(),e);
        //N.B la meth setElement sur la ligne de dessus est celle 
        //de la classa Matrice        
    }
}
