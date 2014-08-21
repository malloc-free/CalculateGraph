/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michael
 */
public class CombinedBoard {
    private Board upperLeft;
    private Board upperRight;
    private Board lowerLeft;
    private Board lowerRight;
    private int dimX;
    private int dimY;
    
    public enum Quadrant{
        UPPER_LEFT{},
        UPPER_RIGHT{},
        LOWER_LEFT{},
        LOWER_RIGHT{}
    }
    
    public CombinedBoard(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;
    }
    
    /**
     * Add a board to the selected quadrant.
     * @param board
     * @param quad
     * @return 
     */
    public boolean addBoard(Board board, Quadrant quad){
        boolean added = false;
        
        if(quad == Quadrant.UPPER_LEFT){
            
            if(upperRight != null && upperRight.getXDimension()
                    + board.getXDimension() < dimX){
                added &= true;
            }
            else{
                added = false;
            }
            if(lowerLeft != null && upperLeft.getYDimension()
                    + board.getYDimension() < dimY){
                added &= true;
            }
            else{
                added = false;
            }
            
            if(added){
                upperLeft = board;
            }
        }
        
        return added;
    }
    
    public static List<Square> transformGenerate(Board board, 
            Board boardTwo, Square square, Quadrant quad){
        
        List<Square> squareList = new ArrayList<>();
        
        int newDimX = square.getPosition().xPos;
        int newDimY = square.getPosition().yPos;
        
        if(quad == Quadrant.UPPER_RIGHT || quad == Quadrant.LOWER_RIGHT){
            newDimX += (boardTwo.getXDimension() -
                    board.getXDimension());
        }
        if(quad == Quadrant.LOWER_LEFT || quad == Quadrant.LOWER_RIGHT){
            newDimY += (boardTwo.getYDimension() 
                    - board.getYDimension());
        }
        
        Square transformed = boardTwo.getSquareAt(newDimX, newDimY);
        
        for(Move m : transformed.getMoves()){
            if(!board.contains(m.getSquareTwo())){
                squareList.add(m.getSquareTwo());
            } 
        }
        
        return squareList;
    }
    
    public static Square transformBack(Board board, Board boardTwo, Square square,
            Quadrant quad){
        
        int newDimX = square.getPosition().xPos;
        int newDimY = square.getPosition().yPos;
        
        if(quad == Quadrant.UPPER_RIGHT || quad == Quadrant.LOWER_RIGHT){
            int temp = boardTwo.getXDimension() - board.getXDimension();
            newDimX -= temp;
        }
        if(quad == Quadrant.LOWER_LEFT || quad == Quadrant.LOWER_RIGHT){
            int temp = boardTwo.getYDimension() - board.getYDimension();
            newDimY -= temp;
        }
        
        return board.getSquareAt(newDimX, newDimY);
    }
}
