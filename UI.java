import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Collection;

/**
 * Définition d'un graphique interface, que est fait a la base d'un matrice carre
 * 
 * @création 11 janvier 2023
 */
 
public class UI extends JFrame{

	//Parametres de fenetre
	private JPanel gameBoard;
	private int size;
	private int frameSize;
	
	
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
	public UI(Matrice<Cell> gameMatrice, Collection<Position> cible){
		super();
		
		if(gameMatrice == null){
			throw new  NullPointerException("Matrix is null");
		}
		
		this.size = gameMatrice.size();
		this.frameSize = size*50;
		JPanel[][] cells = new JPanel[size][size];
		
		this.setSize(frameSize,frameSize);
		this.gameBoard =  new JPanel();
		this.gameBoard.setLayout(new GridLayout(size,size));
		
		boolean isCible;
		for( int i= 0; i < size; i++){
			for(int j=0; j < size; j++){
				isCible = false;
				cells[i][j] = new JPanel();
				cells[i][j].setLayout(new GridLayout());
				cells[i][j].setBackground(Color.black);
				Cell cell = gameMatrice.getElement(new Position(i,j)) ;
				
				if(cible.contains(new Position(i,j))){
					isCible = true;
				}
					
				switch(cell)
				{
					case ME:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.personagePoseIcon));
						}
						else
						{
							cells[i][j].add(new JLabel(this.personageIcon));
						}
						break;
					case WALL:
						cells[i][j].add(new JLabel(this.murIcon));
						break;
					case BOX:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.boitePoseIcon));
            					}else{
							cells[i][j].add(new JLabel(this.boiteIcon));
						}
						break;
					default:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.cibleIcon));
						}
            					
            			}
            			this.gameBoard.add(cells[i][j]);
				}
					
			}
		
		 
		
		
		add(this.gameBoard);
		//setResizable(false);
		setFocusable(true);
		setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public void swap(Matrice<Cell> gameMatrice, Collection<Position> cible ){
		gameBoard.removeAll();
		gameBoard.repaint();
		gameBoard.revalidate();
		
		JPanel[][] cells = new JPanel[size][size];
		boolean isCible;
		for( int i= 0; i < size; i++){
			for(int j=0; j < size; j++){
				isCible = false;
				cells[i][j] = new JPanel();
				cells[i][j].setLayout(new GridLayout());
				cells[i][j].setBackground(Color.black);
				Cell cell = gameMatrice.getElement(new Position(i,j)) ;
				
				if(cible.contains(new Position(i,j))){
					isCible = true;
				}
					
				switch(cell)
				{
					case ME:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.personagePoseIcon));
						}
						else
						{
							cells[i][j].add(new JLabel(this.personageIcon));
						}
						break;
					case WALL:
						cells[i][j].add(new JLabel(this.murIcon));
						break;
					case BOX:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.boitePoseIcon));
            					}else{
							cells[i][j].add(new JLabel(this.boiteIcon));
						}
						break;
					default:
						if(isCible == true){
							cells[i][j].add(new JLabel(this.cibleIcon));
						}
            					
            			}
            			this.gameBoard.add(cells[i][j]);
				}
					
			}
		
		
		
		
}
	
}
