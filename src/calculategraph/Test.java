/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculategraph;

import nz.ac.aut.knights.model.Board;
import nz.ac.aut.knights.model.Square;

/**
 *
 * @author michael
 */
public class Test {
    static long time;
    
    public static void main(String... args) throws InterruptedException{
       
        Board threeFour = new Board(3, 4);
        Board fiveFour = new Board(5, 4);
        Board fiveFive = new Board(5, 5);
        Board sixSix = new Board(6, 6);
        Board sevenSeven = new Board(7, 7);
        Board eightEight = new Board(8, 8);
        
        Square start = threeFour.getSquareAt(0, 0);
        
        calculate(threeFour, 0, 0);
        calculate(fiveFour, 0, 0);
        calculate(fiveFive, 0, 0);
        calculate(sixSix, 0, 0);
        calculate(sevenSeven, 0, 0);
    }
    
    public static void calculate(final Board board, int startX, int startY) throws InterruptedException{
        Square start = board.getSquareAt(startX, startY);
        int maxLevel = board.getXDimension() * board.getYDimension();
        CalculateGraph.setMaxThreads(4);
        System.out.println("Calculating for: "  + board.getXDimension()
                + " x " + board.getYDimension());
        time = System.currentTimeMillis();
        CalculateGraph.findSolution(board, maxLevel, start, true, new KnightCalc.CallBack() {

            @Override
            public void complete() {
                time = System.currentTimeMillis() - time;
                System.out.println("Time for " + board.getXDimension() 
                        + " * " + board.getYDimension() + " is: " + time);
            }
        });
    }
    
}
