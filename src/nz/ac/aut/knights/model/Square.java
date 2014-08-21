/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author michael
 */
public class Square implements Cloneable{
    private Position position;
    private boolean touched;
    private List<Move> moves;
    private int visitNum;
    
    private Square(){
        touched = false;
        moves = new LinkedList<>();
        visitNum = 0;
    };
    
    public Square(final int xP, final int yP){
        this();
        position = new Position(){{
            this.xPos = xP;
            this.yPos = yP;
        }};
    }
    
    /**
     * Check to see if all squares have been visited.
     * @return 
     */
    public boolean allVisited(){
        return (visitNum >= moves.size());
    }
    /**
     * Increment the visitNum for this square.
     */
    public void incrementVisitNum(){
        visitNum++;
    }
    
    /**
     * Get the visitNum for this square.
     * @return 
     */
    public int getVisitNum(){
        return visitNum;
    }
    
    /**
     * Reset visitNum to 0.
     */
    public void clearVisitNum(){
        visitNum = 0;
    }
    
    /**
     * Add a move to this square.
     * @param sq 
     */
    public void addMove(Square sq){
        moves.add(new Move(this,sq));
    }
    
    /**
     * Get the moves for this square.
     * @return 
     */
    public List<Move> getMoves(){
        return moves;
    }
    
    /**
     * Check to see if this square has been visited.
     * @return 
     */
    public boolean getTouched(){
        return touched;
    }
    
    public void setTouched(boolean touched){
        this.touched = touched;
    }
    
    public Position getPosition(){
        return position;
    }
    
    @Override
    public Square clone(){
        return new Square(){{
            position = new Position(){{
                xPos = Square.this.position.xPos;
                yPos = Square.this.position.yPos;
            }};
            
            visitNum = Square.this.visitNum;
            touched = false;
        }};
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        return builder.append(position.toString()).toString();
    }

    @Override
    public boolean equals(Object o){
        boolean equal = false;
        
        if(o instanceof Square){
            Square s = (Square)o;
            
            if(position.equals(s.position)){
                equal = true;
            }
        }
        
        return equal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.position.hashCode();
        
        return hash;
    }
   
  
}
