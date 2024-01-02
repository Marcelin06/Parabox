/*
 * interface 
 */
public interface ClassicSokobanInterface extends ClassicGameInterface{

    /*
     * @requires 
     * 
     * 
     * @ensures \result == (w.get(p)==Cell.EMPTY)
     * @pure
     */
    //public boolean movable(Pos p, Dir d);

    /*
     * ça consiste à échanger, si le mouvement est possible, les Cells
     *  en positions : position_courante et position_courante$d
     * @requires
     */
    public void move( Direction d);
}
