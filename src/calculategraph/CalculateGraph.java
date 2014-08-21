/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculategraph;

import calculategraph.KnightCalc.CallBack;
import java.util.List;
import nz.ac.aut.knights.model.Board;
import nz.ac.aut.knights.model.CombinedBoard;
import nz.ac.aut.knights.model.Knight;
import nz.ac.aut.knights.model.Position;
import nz.ac.aut.knights.model.Square;

/**
 *
 * @author michael
 */
public class CalculateGraph {

    public static final String LINE = "\n-------------------------------\n";
    
    public static int maxThreads;
    
    static{
        maxThreads = 2;
    }
    
    /**
     * Set the maximum number of threads to use when calculating.
     * 
     * @param numThreads 
     */
    public static void setMaxThreads(int numThreads){
        maxThreads = numThreads;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Board fiveFourB = new Board(4, 5);
        Board threeFourB = new Board(4, 3);
        Board eightEightB = new Board(8, 8);
        eightEightB.generateMoves(new Knight());
        Square start = fiveFourB.getSquareAt(0, 0);
        List<Knight> list = findSolution(fiveFourB, (5 * 4), start, true, null);
        Square fiveFour = list.get(0).getLast();
        Position p = fiveFour.getPosition();
        System.out.println("Last position = " + p.toString());
//        List<Knight> listTwo = findSolution(threeFourB, (3 * 4), p.getX(), p.getY());
//        Square threeFour = listTwo.get(0).getLast();
//        p = threeFour.getPosition();
//        System.out.println("Last position = " + p.toString());
        
        
        List<Square> sqs = 
                CombinedBoard.transformGenerate(fiveFourB, 
                eightEightB, fiveFour, CombinedBoard.Quadrant.LOWER_LEFT);
        
        Square next = sqs.get(0);
        
        Square tx = CombinedBoard.transformBack(fiveFourB
                , eightEightB, next, CombinedBoard.Quadrant.LOWER_RIGHT);
        
        list = findSolution(fiveFourB, (5 * 4), tx, false, null);
        
    }
    
    
    public static List<Knight> findSolution(Board board, int maxLevel, Square start,
             boolean finishFirst, CallBack call) throws InterruptedException{
        CalcGroup group = new CalcGroup();
        group.setMaxLevel(maxLevel);
        group.setCallBack(call);
        group.setExitFirst(finishFirst);
        board.generateMoves(new Knight());
        System.out.println(start);
        
        Knight k = new Knight();
        k.addSquare(start);
        group.addToQueue(k);
        group.setMaxThreads(maxThreads);
      
        group.start();
        group.joinTo();
        
        StringBuilder builder = new StringBuilder();
        System.out.println(builder.toString());
        System.out.println("Total number : " + group.getNumCalculations());
        System.out.println("Current queue size : " + group.getQueueSize());
        System.out.println("Finished paths : " + group.getNumFinishedPaths());
        System.out.println("Found paths : " + group.getNumFoundPaths());
        
        return group.getCompleteList();
    }
    
    /**
     * Calculate the tree size for the given board.
     *  
     * @param board - The board to calculate for.
     * @return - The calculated tree size for the board.
     */
    public static double calculateTreeSize(Board board, boolean lessThanOne){
        int n = board.getXDimension() * board.getYDimension();
        double average = board.generateAvSize();
        
        return average * calculate(n - 1, average - 1, lessThanOne);
    }
    
    /**
     * Recursively calculate average moves per turn.
     * @param n
     * @param average
     * @param total
     * @return 
     */
    private static double calculate(int n, double average, boolean lessThanOne){
        if(n == 1){
            return 1;
        }
        
        average = ((average * n) - average) / n;
        System.out.println("n = " + n + " average = " + average);
        
        if(!lessThanOne && average < 1)
            return 1;
        else
            average *= calculate(--n, average, lessThanOne);
        
        return average;
    }
    
    
}
