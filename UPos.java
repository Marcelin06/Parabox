import java.io.*;

/*position d'un element dans l'univers
 * elle consiste en : l'indice du monde où il se trouve et sa position dedans
 */
public class UPos {
    
    private int indexWorld;
    private Position posInWorld;

    public UPos(int indexWorld, Position p){
        this.indexWorld=indexWorld;
        this.posInWorld=p;
    }

    public int getIndexWorld(){
        return indexWorld;
    }    

    public Position getPosition(){
        return posInWorld;
    }

}
