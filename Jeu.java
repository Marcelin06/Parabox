//IMPORTANT !!
//Si on va conserver le non-héritage entre les ClassicGame (et ses ss-classe)
//et les RecursiveGame (et ses ss-classes), alors
//dans le main on doit indiquer si notre jeu sera classique ou récursif,
//pour instancier notre game avec le constructeur de la classe correspondante.
//Une possibilité est de l'indiquer comme chaine de caractère passée en 
//argument de la ligne de commande, et faire un switch
import java.io.*;
public class Jeu{

	public static void main(String[] args){
		if (args.length ==0)
			throw new IllegalArgumentException("wrong format."+"you must enter: java jeu classic or java jeu recursive");
		switch (args[0]){
		case "classic":
			ClassicSokoban classicgame = new ChainingSokoban(new File("niveau/classique_3"));
			break;
		case "recursive":
			RecursiveSokoban recursivegame = new RecursiveSokoban(new File("niveau/recursif_3"));
			break;
		default :
		throw new IllegalArgumentException("wrong format."+"you must enter: java jeu classic or java jeu recursive");
		}
		
		
	}
}
