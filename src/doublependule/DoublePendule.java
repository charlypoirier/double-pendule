package doublependule;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class DoublePendule {
    // Origine
    private final Point origine;
    
    // Premier pendule
    private final Point masse1;
    private final int poids1;
    private final int rayon1;
    private final int longueur1;
    private double angle1;
    private double angle1vit;
    private double angle1acc;
    
    // Deuxième pendule
    private final Point masse2;
    private final int poids2;
    private final int rayon2;
    private final int longueur2;
    private double angle2;
    private double angle2vit;
    private double angle2acc;
    
    // Trace
    private final ArrayList<Point> enregistrement1;
    private Point dernier1;
    private final ArrayList<Point> enregistrement2;
    private Point dernier2;
    private final int limite;
    
    public DoublePendule(int origineX, int origineY, int longueur1, int longueur2, int poids1, int poids2, int rayon1, int rayon2){
        // Origine
        this.origine = new Point(origineX, origineY);
        
        // Premier pendule
        this.masse1 = new Point();
        this.poids1 = poids1;
        this.rayon1 = rayon1;
        this.longueur1 = longueur1;
        this.angle1 = Math.PI;
        this.angle1vit = 0;
        this.angle1acc = 0;
        
        // Deuxième pendule
        this.masse2 = new Point();
        this.poids2 = poids2;
        this.rayon2 = rayon2;
        this.longueur2 = longueur2;
        this.angle2 = Math.PI/2;
        this.angle1vit = 0;
        this.angle2acc = 0;
        
        // Trace
        this.enregistrement1 = new ArrayList<>();
        this.enregistrement2 = new ArrayList<>();
        this.limite = 500;
    }
    
    public void dessiner(Graphics g){
        Graphics2D g2 = (Graphics2D)g; // Permet d'avoir la méthode "setStroke()"
        g2.setStroke(new BasicStroke(2));
        
        // Trace du premier pendule
        g2.setColor(Color.CYAN);
        Iterator<Point> it1 = enregistrement1.iterator();
        while(it1.hasNext()){
            if(this.dernier1 != null){
                Point p = it1.next();
                if(it1.hasNext()){
                    g2.drawLine((int)dernier1.getX(), (int)dernier1.getY(), (int)p.getX(), (int)p.getY());
                }
                this.dernier1 = p;
            }else{
                this.dernier1 = it1.next();
            }
        }
        
        // Trace du deuxième pendule
        g2.setColor(Color.MAGENTA);
        Iterator<Point> it2 = enregistrement2.iterator();
        while(it2.hasNext()){
            if(this.dernier2 != null){
                Point p = it2.next();
                if(it2.hasNext()){
                    g2.drawLine((int)dernier2.getX(), (int)dernier2.getY(), (int)p.getX(), (int)p.getY());
                }
                this.dernier2 = p;
            }else{
                this.dernier2 = it2.next();
            }
        }
        
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.WHITE);
        
        // Premier pendule
        g2.drawLine((int)this.origine.getX(), (int)this.origine.getY(), (int)this.masse1.getX(), (int)this.masse1.getY());
        g2.fillOval((int)masse1.getX()-rayon1/2, (int)masse1.getY()-rayon1/2, rayon1, rayon1);
        
        // Deuxième pendule
        g2.drawLine((int)masse1.getX(), (int)masse1.getY(), (int)this.masse2.getX(), (int)this.masse2.getY());
        g2.fillOval((int)masse2.getX()-rayon2/2, (int)masse2.getY()-rayon2/2, rayon2, rayon2);
    }
    
    public void actualiser(){
        // Accélération du premier pendule
        angle1acc = ((-(Systeme.GRAVITE)*(2*poids1+poids2)*Math.sin(angle1))+(-(poids2)*Systeme.GRAVITE*Math.sin(angle1-2*angle2))+(-2*Math.sin(angle1-angle2)*poids2)*(angle2vit*angle2vit*longueur2+angle1vit*angle1vit*longueur1*Math.cos(angle1-angle2)))/(longueur1*(2*poids1+poids2-poids2*Math.cos(2*angle1-2*angle2)));
        angle1vit += angle1acc;
        angle1 += angle1vit;
        angle1vit *= 0.999;
        
        // Accélération du deuxième pendule
        angle2acc = ((2*Math.sin(angle1-angle2))*((angle1vit*angle1vit*longueur1*(poids1+poids2))+(Systeme.GRAVITE*(poids1+poids2)*Math.cos(angle1))+(angle2vit*angle2vit*longueur2*poids2*Math.cos(angle1-angle2))))/(longueur2 * (2*poids1+poids2-poids2*Math.cos(2*angle1-2*angle2)));
        angle2vit += angle2acc;
        angle2 += angle2vit;
        angle2vit *= 0.999;
        
        // Positions des masses
        this.masse1.setLocation(this.longueur1*Math.cos(Math.PI/2+angle1)+(int)this.origine.getX(), this.longueur1*Math.sin(Math.PI/2+angle1)+(int)this.origine.getY());
        this.masse2.setLocation(this.longueur2*Math.cos(Math.PI/2+angle2)+masse1.getX(), this.longueur2*Math.sin(Math.PI/2+angle2)+masse1.getY());
        
        // Limitation de la trace du premier pendule
        this.enregistrement1.add(new Point(this.masse1));
        if(enregistrement1.size() > this.limite){
            enregistrement1.remove(0);
        }
        this.dernier1 = null;
        
        // Limitation de la trace du deuxième pendule
        this.enregistrement2.add(new Point(this.masse2));
        if(enregistrement2.size() > this.limite){
            enregistrement2.remove(0);
        }
        this.dernier2 = null;
    }
}
