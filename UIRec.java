import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Collection;

/**
 * Définition d'un graphique interface, que est fait a la base d'un matrice carre
 * 
 * @création 11 janvier 2023
 */
 
public class UIRec extends JFrame{

	//Parametres de fenetre
	private JPanel gameBoard;
	private int size;
	private int frameSize;
	private int imageSize;
	
	
	//Icons pour les objet
	ImageIcon personageIcon = new ImageIcon("icon/perso.jpg");
	ImageIcon boiteIcon = new ImageIcon("icon/box.jpg");
	ImageIcon boitePoseIcon = new ImageIcon("icon/boitePose.jpg");
	ImageIcon murIcon = new ImageIcon("icon/mur.jpg");
	ImageIcon cibleIcon = new ImageIcon("icon/croix.jpg");
	ImageIcon personagePoseIcon = new ImageIcon("icon/persoPose.jpg");
	
	/*
     * Faire une fenetre qui montre gameBoard
     * @param gameMatrice une matrice de joue
     * 
     * @requires gameMatrice!=null 
     *
     * @throws NullPointerException si gamwMatrice est null
     */
	public UIRec(Univers univers, Collection<UPos> cible){

		super();

		if((univers == null) && (univers.usize() > 0)){
			throw new  NullPointerException("Matrix is null");
		}
		this.gameBoard =  new JPanel();
		creerWorld(univers, cible, gameBoard, 0, -1);
		this.frameSize = this.size*100;
		this.setSize(frameSize,frameSize);
		add(this.gameBoard);
		setFocusable(true);
		setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public void creerWorld(Univers univ, Collection<UPos> cible, JPanel rec, int profondeur, int numWorld){
		if(profondeur == 4)
			return;

		Matrice<RecursiveCell> gameMatrice;
		if (profondeur == 0){
			for(int n = 0; n < univ.usize(); n++){
				gameMatrice =  univ.getWorld(n);
				for(int i = 0; i < gameMatrice.size(); i++){
					for(int j = 0; j < gameMatrice.size(); j++){
						Position p = new Position(i, j);
						if(gameMatrice.getElement(p) == RecursiveCell.ME){
							numWorld = n;
							break;

						}
					}
					if(numWorld > 0)
						break;
				}
				if(numWorld > 0)
					break;
			}
			imageSize = 100;
			this.size =gameMatrice.size();
			this.frameSize = size*100;
					


		}
		else{
			gameMatrice =  univ.getWorld(numWorld);
			imageSize = imageSize / gameMatrice.size();
		}

		int size =gameMatrice.size();
		JPanel[][] cells = new JPanel[size][size];
		rec.setLayout(new GridLayout(size,size));
			
		boolean isCible;
		for( int i= 0; i < size; i++){
			for(int j=0; j < size; j++){
	
				isCible = false;
				cells[i][j] = new JPanel();
				cells[i][j].setBackground(Color.black);
				RecursiveCell cell = gameMatrice.getElement(new Position(i,j)) ;
					
				if(cible.contains(new UPos(numWorld, new Position(i,j))){
					isCible = true;
				}
						
				switch(cell)
				{
					case ME:
						if(isCible == true){
							cells[i][j].add(new JLabel(new ImageIcon(personagePoseIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH))));
						}
						else
						{
							cells[i][j].add(new JLabel(new ImageIcon(personageIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH))));
						}
						break;
					case WALL:
						cells[i][j].add(new JLabel(new ImageIcon(murIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH))));
						break;					
					case world:
							if(cell.isSimpleBox()){
								if(isCible == true){
									cells[i][j].add(new JLabel((new ImageIcon(boitePoseIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH)))));
										}else{
									cells[i][j].add(new JLabel((new ImageIcon(boiteIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH)))));
							}}
							else{
								creerWorld(univ,cible, cells[i][j],profondeur+1, gameMatrice.numWorld);
							}
							break;
						default:
							if(isCible == true){
								cells[i][j].add(new JLabel((new ImageIcon(cibleIcon.getImage().getScaledInstance(imageSize, imageSize,Image.SCALE_SMOOTH)))));
							}
									
							}
							rec.add(cells[i][j]);
					}
						
				}

	}

	public void swap(Univers univers, Collection<UPos> cible ){
		gameBoard.removeAll();
		gameBoard.repaint();
		gameBoard.revalidate();
		
		creerWorld(univers, cible, gameBoard, 0, -1);
		
		
}
	
}
