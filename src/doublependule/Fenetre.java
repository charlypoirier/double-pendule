package doublependule;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Fenetre {
    public static void main(String[] args){
        JFrame fenetre = new JFrame();
        fenetre.setBounds(350, 15, 800, 800);
        fenetre.setTitle("Double pendule");
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Le système
        Systeme systeme = new Systeme(fenetre.getWidth(), fenetre.getHeight());
        fenetre.add(systeme);
        
        fenetre.setVisible(true);
    }
}
