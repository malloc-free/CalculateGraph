/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.knights.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import nz.ac.aut.knights.visual.graph.gui.VisualGraph.CallBack;

/**
 *
 * @author Michael Holmwood
 */
public class Knight implements Cloneable, Comparable<Knight>{
    
    //private Deque<Square> visited;
    private Decider decider;
    private Deque<Square> visited;
    
    public Knight(){
        visited = new LinkedList<>();
        //decider = new DefaultDecider();
    }

    @Override
    public int compareTo(Knight o) {
        return visited.size() - o.getLevel();
    }
    
//    private class DefaultDecider implements Decider{
//        @Override
//        public Square decide(Knight knight, CallBack callBack) {
//            Square s = visited.peek();
//
//            if(s.allVisited()){
//                backwards(knight, callBack);
//            }
//            else{
//                List<Move> moves = s.getMoves();
//                for(int x = s.getVisitNum(); 
//                        x < moves.size(); x++){
//
//                    Square next;
//                    Move m = moves.get(x);
//
//                    if(!(next = m.getSquareTwo()).getTouched()){
//                        visited.add(next);
//                        EnumKeyValue v = new EnumKeyValue();
//                        v.keyEnum = VERTEX_NAME;
//                        v.object = next.toString();
//                        callBack.modified(VERTEX_ADD, v);
//                        
//                    }
//                }
//            }
//
//            return visited.peek();
//        }
//
//        @Override
//        public Square backwards(Knight knight, CallBack callBack) {
//            Square s = visited.poll();
//            s.setTouched(false);
//            s.clearVisitNum();
//
//            return decide(Knight.this, callBack);
//        }
//    }
    
    public Square getFirst(){
        return visited.getFirst();
    }
    
    public Square getLast(){
        return visited.getLast();
    }
    
    public boolean firstLastMatch(Square first, Square last){
        return first.equals(visited.getFirst()) && last.equals(visited.getLast());
    }
    
    /**
     * Add square to this knight.
     * @param s 
     */
    public boolean addSquare(Square s){
        boolean contains = false;
        
        if(!visited.contains(s)){
            visited.add(s);
            
            contains = true;
        }
        
        return contains;
    }
    
    public boolean contains(Square s){
        return visited.contains(s);
    }
    
    public int getLevel(){
        return visited.size();
    }
    
    /**
     * Use the set decider to decide what square to use next.
     * @return 
     */
    public Square decide(CallBack callBack){
        return decider.decide(this, callBack);
    }
    
    /**
     * Get the square the knight is currently residing on.
     * @return 
     */
    public Square getCurrentSquare(){
        return visited.peekLast();
    }
    
    /**
     * Add the first square to this knight.
     * @param sq 
     */
    public void setFirstSquare(Square sq){
        visited.add(sq);
    }
    
    /**
     * Remove all of the squares that are in the current map from the
     * list.
     * 
     * @param squares 
     */
    public void removeVisited(List<Square> squares){
        for(Square s : squares){
            squares.removeAll(visited);
        }
    }
    
    /**
     * The moves for the knight.
     */
    private enum Movement{
        FIRST   ( 1,  2),
        SECOND  ( 1, -2),
        THIRD   (-1,  2),
        FOURTH  (-1, -2),
        FIFTH   ( 2,  1),
        SIXTH   ( 2, -1),
        SEVENTH (-2,  1),
        EIGHTH  (-2, -1);
        
        int xMov;
        int yMov;
        
        private Movement(int x, int y){
            this.xMov = x;
            this.yMov = y;
        }
    }
    
    /**
     * Generate the squares for the knight from the given square.
     * 
     * @param square - The square to generate the moves from.
     * @param b - The board to base it on.
     * @return - The list of squares.
     */
    public static List<Square> generateMoves(Square square, Board b){
        List<Square> squares = new LinkedList<>();
        
        int y, x;
        
        Position p = square.getPosition();
        
        for(Movement m : Movement.values()){
            if((x = p.xPos + m.xMov) >= 0 && x < b.getXDimension()
                    && (y = p.yPos + m.yMov) >= 0 && y < b.getYDimension()){
                squares.add(new Square(x, y));
            }
        }
            
        return squares;
    }
    
    @Override
    public Knight clone(){
        Knight k = new Knight();
        
        for(Square s : visited){
            k.visited.add(s);
        }
        
        return k;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        int x = 0;
        
        for(Square s : visited){
            builder.append("Square no: ").append(x++).append(" ");
            builder.append(s.toString()).append("\n");
        }
        
        return builder.toString();
    }
    /**
     * Small interface, used to decide the next move.
     */
    public interface Decider{
        public Square decide(Knight knight, CallBack back);
        public Square backwards(Knight knight, CallBack back);
    }
    
}
