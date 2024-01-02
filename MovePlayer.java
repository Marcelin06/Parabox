import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class MovePlayer extends KeyAdapter{
    
    private ClassicSokoban game;

    public MovePlayer(ClassicSokoban game) {
        
        this.game = game;
    }


    
    public void keyPressed(KeyEvent e) {
        Direction direction = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = Direction.UP;
                game.move(direction);
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.DOWN;
                game.move(direction);
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.LEFT;
                game.move(direction);
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.RIGHT;
                game.move(direction);
            case KeyEvent.VK_Z:
            	if((e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
            		game.ctrlZ();
            		System.out.println("Ctrl-Z utilisé");}
            	break;
        }
         
    }

    
        

}
