package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class DoublePendulum {
    // Origin
    private final Point origin;
    
    // First pendulum
    private final Point mass1;
    private final int weight1;
    private final int radius1;
    private final int length1;
    private double angle1;
    private double angle1vel;
    private double angle1acc;
    
    // Second pendulum
    private final Point mass2;
    private final int weight2;
    private final int radius2;
    private final int length2;
    private double angle2;
    private double angle2vel;
    private double angle2acc;
    
    // Tail
    private final ArrayList<Point> tail1;
    private Point last1;
    private final ArrayList<Point> tail2;
    private Point last2;
    private final int limit;
    
    public DoublePendulum(int originX, int originY, int length1, int length2, int weight1, int weight2, int radius1, int radius2){
        // Origin
        this.origin = new Point(originX, originY);
        
        // First pendulum
        this.mass1 = new Point();
        this.weight1 = weight1;
        this.radius1 = radius1;
        this.length1 = length1;
        this.angle1 = Math.PI;
        this.angle1vel = 0;
        this.angle1acc = 0;
        
        // Second pendulum
        this.mass2 = new Point();
        this.weight2 = weight2;
        this.radius2 = radius2;
        this.length2 = length2;
        this.angle2 = Math.PI/2;
        this.angle1vel = 0;
        this.angle2acc = 0;
        
        // Tails
        this.tail1 = new ArrayList<>();
        this.tail2 = new ArrayList<>();
        this.limit = 500;
    }
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        
        // Tail of the first pendulum
        g2.setColor(Color.CYAN);
        Iterator<Point> it1 = tail1.iterator();
        while(it1.hasNext()){
            if(this.last1 != null){
                Point p = it1.next();
                if(it1.hasNext()){
                    g2.drawLine((int)last1.getX(), (int)last1.getY(), (int)p.getX(), (int)p.getY());
                }
                this.last1 = p;
            }else{
                this.last1 = it1.next();
            }
        }
        
        // Tail of the second pendulum
        g2.setColor(Color.MAGENTA);
        Iterator<Point> it2 = tail2.iterator();
        while(it2.hasNext()){
            if(this.last2 != null){
                Point p = it2.next();
                if(it2.hasNext()){
                    g2.drawLine((int)last2.getX(), (int)last2.getY(), (int)p.getX(), (int)p.getY());
                }
                this.last2 = p;
            }else{
                this.last2 = it2.next();
            }
        }
        
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.WHITE);
        
        // First pendulum
        g2.drawLine((int)this.origin.getX(), (int)this.origin.getY(), (int)this.mass1.getX(), (int)this.mass1.getY());
        g2.fillOval((int)mass1.getX()-radius1/2, (int)mass1.getY()-radius1/2, radius1, radius1);
        
        // Second pendulum
        g2.drawLine((int)mass1.getX(), (int)mass1.getY(), (int)this.mass2.getX(), (int)this.mass2.getY());
        g2.fillOval((int)mass2.getX()-radius2/2, (int)mass2.getY()-radius2/2, radius2, radius2);
    }
    
    public void update(){
        // First pendulum's acceleration
        angle1acc = ((-(System.GRAVITY)*(2*weight1+weight2)*Math.sin(angle1))+(-(weight2)*System.GRAVITY*Math.sin(angle1-2*angle2))+(-2*Math.sin(angle1-angle2)*weight2)*(angle2vel*angle2vel*length2+angle1vel*angle1vel*length1*Math.cos(angle1-angle2)))/(length1*(2*weight1+weight2-weight2*Math.cos(2*angle1-2*angle2)));
        angle1vel += angle1acc;
        angle1 += angle1vel;
        angle1vel *= 0.999;
        
        // Second pendulum's acceleration
        angle2acc = ((2*Math.sin(angle1-angle2))*((angle1vel*angle1vel*length1*(weight1+weight2))+(System.GRAVITY*(weight1+weight2)*Math.cos(angle1))+(angle2vel*angle2vel*length2*weight2*Math.cos(angle1-angle2))))/(length2 * (2*weight1+weight2-weight2*Math.cos(2*angle1-2*angle2)));
        angle2vel += angle2acc;
        angle2 += angle2vel;
        angle2vel *= 0.999;
        
        // Positions of the masses
        this.mass1.setLocation(this.length1*Math.cos(Math.PI/2+angle1)+(int)this.origin.getX(), this.length1*Math.sin(Math.PI/2+angle1)+(int)this.origin.getY());
        this.mass2.setLocation(this.length2*Math.cos(Math.PI/2+angle2)+mass1.getX(), this.length2*Math.sin(Math.PI/2+angle2)+mass1.getY());
        
        // First tail limit
        this.tail1.add(new Point(this.mass1));
        if(tail1.size() > this.limit){
            tail1.remove(0);
        }
        this.last1 = null;
        
        // Second tail limit
        this.tail2.add(new Point(this.mass2));
        if(tail2.size() > this.limit){
            tail2.remove(0);
        }
        this.last2 = null;
    }
}
