package doublependule;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Systeme extends JPanel implements KeyListener, ActionListener{
    
    public static final double GRAVITE = 0.3;
    private final Timer timer;
    private final DoublePendule doublePendule;
    
    public Systeme(int largeur, int hauteur){
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        doublePendule = new DoublePendule(largeur/2, hauteur/2, 170, 170, 1, 1, 30, 30);
        
        timer = new Timer(5, this);
        timer.start();
    }
    
    @Override
    public void paint(Graphics g){
        // Fond
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Double pendule
        doublePendule.dessiner(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        doublePendule.actualiser();
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
