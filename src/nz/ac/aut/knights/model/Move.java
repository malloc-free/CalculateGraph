/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

/**
 *
 * @author michael
 */
public class Move {
    private Square sqOne;
    private Square sqTwo;
    
    public Move(Square sqOne, Square sqTwo){
        this.sqOne = sqOne;
        this.sqTwo = sqTwo;
    }
    
    public Square getSquareOne(){
        return sqOne;
    }
    
    public Square getSquareTwo(){
        return sqTwo;
    }
}
