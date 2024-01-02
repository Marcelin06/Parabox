import java.io.*;

//creer une interface CellInterface commune à cette classe et à Cell
//et rendre la classe Matrice générique avec Matrice<CellInterface>

/*une RecursiveCell peut être :
 * soit une cell simple, voir les static final ci-dessous
 * soit un World (vu que c'est récursif) de type Matrice<RecursiveCell>
 * N.B un word peut être soit une boîte-monde, soit une boîte simple,
 * et dans ce cas on le représente par une Matrice<RecursiveCell> de taille 1
 */
public class RecursiveCell implements CellInterface {
    public static final int ME=0;
    public static final int WALL=1;
    public static final int EMPTY=2;
    //public static final int BOX=3;    on omet les boîtes simples, en considérant
                                        //qu'elles sont des World de taille 1

    private Matrice<RecursiveCell> world;   //c'est une boîte, soit simple soit boîte-monde

    /*constructeur chargé uniquement d'instancier les Cell World,
     * étant donné que les autres sont des constantes publiques, on peut
     *  les voir 
     * comme dans la classe enum.
     * @param univers c'est l'univers qui contiendra la liste des World
     * @param indexWorld c'est le monde en question
     */
    public RecursiveCell(Univers u, int indexWorld){
        this.world=u.getWorld(indexWorld);
    }

    public boolean isWorld(){
        return (!(this==RecursiveCell.ME||this==RecursiveCell.WALL||this==RecursiveCell.EMPTY));
    }

    public boolean isSimpleBox(){
        return (isWorld() && this.world.size()==1);
    }

    public boolean isWorldBox(){
        return (isWorld() && !isSimpleBox());
    }

}
