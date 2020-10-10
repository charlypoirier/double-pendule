package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class System extends JPanel implements KeyListener, ActionListener{
    
    public static final double GRAVITY = 0.3;
    private final Timer timer;
    private final DoublePendulum doublePendulum;
    
    public System(int width, int height){
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        doublePendulum = new DoublePendulum(width/2, height/2, 170, 170, 1, 1, 30, 30);
        
        timer = new Timer(5, this);
        timer.start();
    }
    
    @Override
    public void paint(Graphics g){
        // Background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Double pendulum
        doublePendulum.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        doublePendulum.update();
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
