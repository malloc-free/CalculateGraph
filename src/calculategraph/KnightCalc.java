/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculategraph;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;
import nz.ac.aut.knights.model.Knight;
import nz.ac.aut.knights.model.Move;
import nz.ac.aut.knights.model.Square;

/**
 *
 * @author Michael Holmwood
 */
public class KnightCalc extends Thread{
        
        CalcGroup group;
        Knight k;
        String label;
        
        public interface CallBack{
            public void complete();
        }
        
        public void setUp(KnightCalc calc){
            
        }
    
        @Override
        public void run() {
            try {
                if(group.keeperSet.compareAndSet(false, true)){
                    group.keep = this;
                    
                    update();
                }
                else
                    calc();
            } catch (InterruptedException ex) {
                if(group.callBack != null){
                        group.callBack.complete();
                }
            }
        }
        
        private void update() throws InterruptedException{
            group.keep = this;
            
            do{
                sleep(1000);
                System.out.println("Num calcs = " + group.atomInt.toString());
                System.out.println("Num complete = " + group.atomFin.toString());
                System.out.println("Num threads running = " + group.atomComplete.toString());
                System.out.println("Num found = " + group.complete.size());
            }
            while(!interrupted());
        }
        
        private void calc() throws InterruptedException{
            //System.out.println(label + " : Starting!\n");
             k = group.queue.take();
            
             do{
                //System.out.println(label + " : " + k + "\n");
                Square sqOne = k.getCurrentSquare();
                Knight clone = k.clone();
                boolean added = false;
                
                for(Move m : sqOne.getMoves()){
                    Square sqTwo = m.getSquareTwo();
                    if(!k.contains(sqTwo)){
                        group.atomInt.incrementAndGet();
                        if(!added){
                            k.addSquare(sqTwo);
                            added = true;
                        }
                        else{
                            Knight temp = clone.clone();
                            temp.addSquare(sqTwo);
                            group.queue.add(temp);
                        }
                    }
                }
                boolean found = false;
                
                if(!added || (found = k.getLevel() >= group.maxLevel)){
                    if(found){
                        group.complete.add(k);
                        if(group.exitFirst){
                            group.stop.set(true);
                        }
                    }
                    //System.out.println(LINE + label + " finished : \n" + k + LINE);
                    group.atomFin.incrementAndGet();
                    Knight temp;
//                    if(group.calcs.length > 1){
//                        temp = group.queue.poll(5l, TimeUnit.SECONDS);
//                    }
//                    else{
                        temp = group.queue.poll();
                    //}
                    
                    if(temp == null){
                        System.out.println("Nothing to do!");
                        break;
                    }
                    else
                        k = temp;
                }
                
            }
             
            while(!group.stop.get());
             
             if(group.atomComplete.decrementAndGet() == 0){
                 group.keep.interrupt();
             }
        }
    }
