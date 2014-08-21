/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculategraph;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import nz.ac.aut.knights.model.Knight;

public class CalcGroup{
            AtomicBoolean keeperSet;
            AtomicBoolean stop;
            AtomicInteger atomInt;
            AtomicInteger atomFin;
            AtomicInteger atomComplete;
            List<Knight> complete;
            int maxLevel;
            KnightCalc[] calcs;
            boolean exitFirst;
            PriorityBlockingQueue<Knight> queue;
            KnightCalc keep;
            KnightCalc.CallBack callBack;
            
            public CalcGroup(){
                keeperSet = new AtomicBoolean(false);
                atomInt = new AtomicInteger(0);
                atomFin = new AtomicInteger(0);
                atomComplete = new AtomicInteger();
                complete = new LinkedList<>();
                maxLevel = 0;
                exitFirst = false;
                stop = new AtomicBoolean(false);
                queue = new PriorityBlockingQueue<>();
            }
            
               
            public void setCallBack(KnightCalc.CallBack callBack){
                    this.callBack = callBack;
            }
            
            public void addToQueue(Knight knight){
                queue.add(knight);
            }
            
            
            public void setMaxThreads(int threads){
                calcs = new KnightCalc[threads];
                
                for(int x = 0; x < calcs.length; x++){
                    KnightCalc calc = new KnightCalc();
                    calc.label = Integer.toString(x);
                    calcs[x] = calc;
                    calc.group = this;
                }
                
                atomComplete.set(threads - 1);
            }
            
            public void start(){
                for(KnightCalc c : calcs){
                    c.start();
                }
            }
            
            public void joinTo() throws InterruptedException{
                while(keep == null);
                keep.join();
            }
            
            public void setMaxLevel(int maxLevel){
                this.maxLevel = maxLevel;
                
            }
            
            public void setExitFirst(boolean exitFirst){
                this.exitFirst = exitFirst;
            }
            
            public int getNumCalculations(){
                return atomInt.get();
            }
            
            public int getQueueSize(){
                return queue.size();
            }
            
            public int getNumFinishedPaths(){
                return atomFin.get();
            }
            
            public int getNumFoundPaths(){
                return complete.size();
            }
            
            public List<Knight> getCompleteList(){
                return complete;
            }
        }
