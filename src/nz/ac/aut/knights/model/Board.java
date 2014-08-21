/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author michael
 */
public class Board {
    private int xDimension;
    private int yDimension;
    private Map<Position, Square> squares;
    
    private Board(){};
    
    /**
     * Constructor for Board, takes x and y dimensions.
     * 
     * @param xDimension
     * @param yDimension 
     */
    public Board(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        generateSquares();
    }
    
    /**
     * Generate the squares for this board.
     */
    private void generateSquares(){
        squares = new TreeMap<>();
        for(int x = 0; x < xDimension; x++){
            for(int y = 0; y < yDimension; y++){
                Square temp = new Square(x, y);
                squares.put(temp.getPosition(), temp);
            }
        }
    }
    
    /**
     * Get the square at the given position.
     * @param position
     * @return 
     */
    public Square getSquare(Position position){
        return squares.get(position);
    }
    
    /**
     * Generate the moves for the given knight.
     * @param knight 
     */
    public void generateMoves(Knight knight){
        if(squares.isEmpty()){
            throw new IllegalStateException("Board has not been initialized");
        }
        
        for(Square s : squares.values()){
            List<Square> sqList = Knight.generateMoves(s, this);
            
            for(Square genSq : sqList){
                if(squares.containsKey(genSq.getPosition()))
                   genSq = squares.get(genSq.getPosition());
                
                s.addMove(genSq);
            }
        }
    }
    
    public Square getSquareAt(int x, int y){
        
        if(!(x >= 0 && x < xDimension) || !(y >= 0 && y < yDimension)){
            throw new IndexOutOfBoundsException("Square is beyond board");
        }
        if(squares.isEmpty()){
            throw new IllegalStateException("Board is not setup");
        }
        
        return squares.get(new Position(x, y));
    }
    
    /**
     * Checks to see if this board contains the specified square.
     * @param s
     * @return 
     */
    public boolean contains(Square s){
        return squares.containsKey(s.getPosition());
    }
    
    /**
     * Generate the average number of moves for any given square 
     * for this board.
     * 
     * @return - The average for the board.
     */
    public double generateAvSize(){
        double total = 0;
        
        for(Square s : squares.values()){
            total += s.getMoves().size();
        }
        
        total /= squares.values().size();
        
        return total;
    }
    
    /**
     * Return a collection containing all squares for this board.
     * @return 
     */
    public Collection<Square> getSquares(){
        return squares.values();
    }
    
    /**
     * Get the xDimension for this board.
     * @return 
     */
    public int getXDimension(){
        return xDimension;
    }
    
    /**
     * Get the yDimension for this board.
     * @return 
     */
    public int getYDimension(){
        return yDimension;
    }
    
    /**
     * @see java.lang.Cloneable
     * @return 
     */
    @Override
    public Board clone(){
        return new Board(){{
            xDimension = Board.this.xDimension;
            yDimension = Board.this.yDimension;
        }};
    }
}
