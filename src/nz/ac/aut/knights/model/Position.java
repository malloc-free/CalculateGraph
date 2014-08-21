/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

/**
 *
 * @author michael
 */
public class Position implements Comparable<Position>{
    protected int xPos;
    protected int yPos;
    
    public Position(){};
    
    public Position(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    @Override
    public String toString(){
        return "xPos = " + xPos + " yPos = " + yPos;
    }
    
    @Override
    public int compareTo(Position o) {
        
        int retVal = -1;
        
        if(yPos > o.yPos){
            retVal = 1;
        }
        else if(yPos == o.yPos){
            
            if(xPos > o.xPos){
                retVal = 1;
            }
            else if(xPos == o.xPos){
                retVal = 0;
            }
        }
     
        return retVal;
    }
    
    /**
     * Get the xPos for this position.
     * @return 
     */
    public int getX(){
        return xPos;
    }
    
    /**
     * Get the yPos for this position.
     * @return 
     */
    public int getY(){
        return yPos;
    }
    
    @Override
    public boolean equals(Object o){
        boolean equals = false;
        
        if(o instanceof Position){
            if(((Position)o).xPos == xPos &&
                    ((Position)o).yPos == yPos){
                equals = true;
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.xPos;
        hash = 41 * hash + this.yPos;
        
        return hash;
    }
}
