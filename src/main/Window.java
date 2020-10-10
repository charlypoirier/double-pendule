package main;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Window {
    
    public static void main (String[] args) {
        // The window
        JFrame window = new JFrame();
        window.setBounds(350, 15, 800, 800);
        window.setTitle("Double pendulum");
        window.setResizable(false);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // The system
        System system = new System(window.getWidth(), window.getHeight());
        window.add(system);
        window.setVisible(true);
    }
    
}
