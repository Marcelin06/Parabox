/**
 * Classe concrète {@code Position} créee pour représenter une position
 * dans une matrice (tableau à deux dimensions)
 * x est l'abscisse
 * y est l'ordonné
 * @création 10 janvier 2023
 */
public class Position{
    int x;
    int y;


    /**
     * Initiale une nouvelle position
     * 
     * @param x
     *          l'abscisse
     * @param y
     *          l'ordonnée
     * @requires x >= 0 && y >= 0
     * @ensures  this.x == x && this.y == y
     * 
     */
    public Position(int x, int y){
        if((x < 0) || (y < 0)){
            throw new IllegalArgumentException("x et y doivent etre des entiers positifs ou nuls");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Renvoi l'abscisse de ce point
     * @ensures \result >= 0
     * @return l'abscisse de ce point
     * @pure
     */
    public int getX(){
        return this.x;
    }

    /**
     * Renvoi l'ordonnée de ce point
     * @ensures \result >= 0
     * @return l'ordonnée de ce point
     * @pure
     */
    public int getY(){
        return this.y;
    }

	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
    /**
     * Redéfinition de la méthode {@code equals}
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Position)){
            return false;
        }

        Position o = (Position) obj;
        return (getX() == o.getX()) && (getY() == o.getY());
    }

    /**
     * Redéfinition de la méthode {@code toString}
     */
    @Override
    public String toString(){
        String result = "{Position : Abscisse : ";
        result += getX();
        result += " ; Ordonnée : " + getY();
        result += " }";
        return result;
    }

    /**
     * Redéfinition de la méthode {@code hashCode}
     */
    @Override
    public int hashCode(){
        int code = 50;
        code = (31*getX())%50;
        code += getX() + getY();
        return code;

    }
}
