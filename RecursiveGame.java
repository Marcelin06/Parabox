import java.io.*;

//héritage à vérifier (extends ClassicGame ?)
//Pour l'instant, je considère qu'elle n'en est pas une sous-classe
//voir les remarques dans la classe Jeu
public class RecursiveGame  {
    
    //attributs
    protected Univers univers;
    protected Matrice<RecursiveCell> gameboard;
    protected Collection<UPos> cibles;

    //constructeur 
    public RecursiveGame(File file){
        this.cibles= new ArrayList<UPos>();
        this.univers= new Univers();
        int x = 0; int y = 0;   //coordonnées pour la matrice-monde
		int symbole; int size_m;
        Matrice<RecursiveCell> w;
		try(FileInputStream fin = new FileInputStream(file)){

            
            while((symbole = fin.read()) != -1){

                char name = ((char)symbole);
                //Will read ' ' after name of matrice
                fin.read();
                // We read size of the matrice
                size_m=0;
                while((symbole = fin.read()) != 10)
                    size_m = size_m*10 + symbole - 48; // We need to make 
                    //this manipulation because we read ASCII char	

                this.univers.addWorld(new Matrice<RecursiveCell>(size_m, RecursiveCell.EMPTY, name)); 
            
                int indexWorld=1;
                int compteur_ret_ligne=0;  
                //ceci est un moyen de lire une et une seule matrice, étant donné que son
                // nbe de lignes est égal au nbe de retour à la ligne  
                while(compteur_ret_ligne<size_m){   
                    switch(symbole){
                    case 35 :// #
                        w.setElement(new Position(x,y), RecursiveCell.WALL);
                        y++;
                        break;
                    case 65: // A
                        w.setElement(new Position(x,y), RecursiveCell.ME);
                        y++;
                        break;
                    case 97: //a
                        this.cibles.add(new UPos(0,new Position(x, y)));
                        w.setElement(new Position(x,y), RecursiveCell.ME);
                        y++;
                        break;
                    case 64: // '@'
                        this.cibles.add(new UPos(0,new Position(x, y)));
                        y++;
                        break;
                    case 10 : // '\n'
                        x++; y = 0;
                        ++compteur_ret_ligne;
                        break;
                    case 32 : // ' '
                        w.setElement(new Position(x,y), RecursiveCell.EMPTY);
                        y++;
                        break;
                    default: // une Cell World
                        if (symbole >=98 && symbole<=122)  //symbole dans [b..z], c-a-d il y a un indice dessous
                            this.cibles.add(new Position(x, y));
                        //RecursiveCell world= new RecursiveCell(univers,indexWorld++);
                        //la ligne ci-dessus est fausse, car il n'y a pas encore un monde d'indice indexWorld dans tableau pour 
                        //instancier la bîte-monde. En effet, sur le fichier, on croise la boîte monde avant de croiser la matrice
                        //qu'elle contient (matrice du coup pas encore instanciée).
                        //Pour pallier ce problème, on stocke dans une liste chaînée les triplets contenant les coordonnées de chaque
                        //boîte rencontrée avec l'indexWorld, et on les instancie quand on termine la lecture de toutes les matrices,
                        // c-a-d tout le fichier,
                        //on garantie ainsi que les matrices existent avant, et puis on crée les boîte-mondes A PARTIR de ces matrices

                        w.setElement(new Position(x,y), world);
                        y++;
                    }   //fin switch
                }   //fin du while lecture monde
			}   //fin while lecture fichier
		}   //fin try
		catch(IOException e){
			System.out.println("Plobleme with your file");
		}    	
    }



    /*renvoie true si la boite de UPos @param q est une cible, false sinon */
    public boolean target(UPos q){
        return this.cibles.contains(q);
    }

    /*Renvoie true ssi :
     * on n'est pas onEdge de cette matrice-monde en suivant la direction @param d
     * ou bien
     * aucune case de l'univers n'est une boîte-monde contenant le 
     * monde q.getIndexWorld(), autrement dit ce monde n'existe sous forme
     * d'aucune boîte-monde
     * @pure
     */
    public boolean hasNext(UPos q, Direction d){
        int n=q.getIndexWorld();
        Matrice<RecursiveCell> w= univers.getWorld(n);
        //RecursiveCell c=univers.getElement(q);
        if ( ! w.onEdge(q.getPosition(),d)) 
            return true; 
        for(int k=0; k<this.univers.usize(); ++k){
            Matrice<RecurciveCell> ww= univers.getWorld();
            for(int i=0; i<ww.size();++i)
                for(int j=0; j<ww.size();++j){
                    RecursiveCell c= ww.getElement(new Position(i,j));
                    if(c.isWorldBox() && n==1 )
                        return true;
                }            
        }
        return false; 
    }

    /*Renvoie la prochaine UPos (position dans l'univers) à partir de
     * l'UPos @param q, suivant la Direction @param d
     * @pure 
     */
    public UPos next(UPos q, Direction d){
        if(! hasNext(q,d))
            throw new IllegalArgumentException("il n'y a pas de next");

        int n=q.getIndexWorld();
        Position p=q.getPosition();
        Matrice<RecursiveCell> w= univers.getWorld(n);
        
        if (! w.onEdge(p,d))
            return new UPos(n,w.nextPosition(p,d));

        //maintenant on est sûr que notre monde est bien une boîte-monde
        for(int k=0; k<this.univers.usize(); k++){
            Matrice<RecurciveCell> ww= univers.getWorld();
            for(int i=0; i<ww.size();++i)
                for(int j=0; j<ww.size();++j){
                    Position pp=new Position(i,j);
                    RecursiveCell c= ww.getElement(pp);
                    if(w.equals(c))     //N.B ce if a un true garanti
                        return next(u,new UPos(k,pp),d);
                }            
        }

    }

    public boolean victoire(){
        for (UPos q :cibles){
            if (univers.getElement(q) != Cell.BOX || univers.getElement(q)!=Cell.ME)            	
                return false;
        }
        return true;
    }
    

}
