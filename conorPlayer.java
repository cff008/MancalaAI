import java.util.LinkedList;
import java.util.List;

/****************************************************************
  * studPlayer.java
  * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
  * evaluator (SBE) function is simple: the # of stones in studPlayer's
  * mancala minue the # in opponent's mancala.
  * -----------------------------------------------------------------------------------------------------------------
  * Licensing Information: You are free to use or extend these projects for educational purposes provided that
  * (1) you do not distribute or publish solutions, (2) you retain the notice, and (3) you provide clear attribution to UW-Madison
  *
  * Attribute Information: The Mancala Game was developed at UW-Madison.
  *
  * The initial project was developed by Chuck Dyer(dyer@cs.wisc.edu) and his TAs.
  *
  * Current Version with GUI was developed by Fengan Li(fengan@cs.wisc.edu).
  * Some GUI componets are from Mancala Project in Google code.
  */




 //################################################################
 // studPlayer class
 //################################################################

 public class conorPlayer extends Player {

     /*Use IDS search to find the best move. The step starts from 1 and keeps incrementing by step 1 until
      * interrupted by the time limit. The best move found in each step should be stored in the
      * protected variable move of class Player.
      */
	 
     public void move(GameState state)
     {
         int depth = 1;
         while(true){
           this.move = maxAction(state, depth); 
           depth = depth + 1;
         }
     }
     
     
     // Return best move for max player. Note that this is a wrapper function created for ease to use.
     // In this function, you may do one step of search. Thus you can decide the best move by comparing the 
     // sbe values returned by maxSBE. This function should call minAction with 5 parameters.
     public int maxAction(GameState state, int maxDepth)
     {

    	 int alpha = Integer.MIN_VALUE;
    	 int beta = Integer.MAX_VALUE;
    	 int move = -1000;
    	 int v = Integer.MIN_VALUE;
    	 for(int i = 0; i < 6; i++){
    		 //check illegal move
    		 if(!state.illegalMove(i)){
    			 GameState s = new GameState(state);
    			 //Move depends on if the bin will result in a second move.
    			 if(!s.applyMove(i)){
    				 v = Math.max(v, minAction(s,1,maxDepth,alpha,beta));
    				 
    			 }else{
    				 v = Math.max(v,maxAction(s,1,maxDepth,alpha,beta));
    				 
    			 }
    			 if(v>alpha){
    				 move = i;
    				 alpha = v;
    			 }
    		 } 
    	 }
    	 return move;
     }
 
  
     
     //return sbe value related to the best move for max player
     public int maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
     {
       //check if the max depth is equal to current depth or if the game is over.
       if(currentDepth == maxDepth || state.gameOver()){
         return sbe(state);
       }    
       
       int v = Integer.MIN_VALUE;
       //iterate through each spot.
       for(int i = 0; i<6; i++){
    	 //make a copy of state.
		   GameState copy = new GameState(state);
    	   if(!state.illegalMove(i)){   
    	      //check if the move results in another turn.
    		   if(!copy.applyMove(i)){
    			   v = Math.max(v, minAction(copy,currentDepth+1,maxDepth,alpha,beta));
    		   }
    		   else{
    			   v = Math.max(v, maxAction(copy, currentDepth+1,maxDepth, alpha, beta));
    			  
    		   }
    		   if(v > beta){
    			   return v;
    		   }
    		   alpha = Math.max(alpha, v);
    	   }
       }
       return v;
       
     }
     //return sbe value related to the bset move for min player
     public int minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
     {
       if(currentDepth == maxDepth || state.gameOver()){
         return sbe(state);
       }
      
       int v = Integer.MAX_VALUE;
       for(int i = 7; i < 13;i++){
    	   GameState copy = new GameState(state);
    	   if(!state.illegalMove(i)){	   
    		   if(!copy.applyMove(i)){
    			   v = Math.min(v, maxAction(copy, currentDepth+1,maxDepth, alpha,beta));
    			   }
    		   else{
    			   v = Math.min(v, minAction(copy, currentDepth+1,maxDepth,alpha,beta));	  
    		   }
    		   if(v <= alpha){
    			   return v;
    		   }
    		   beta = Math.min(beta, v);
    		   
    	   }
       }
       return v;
       
     }

     //the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.
     private int sbe(GameState state)
     {
     //simple function by finding the difference in the two mancala's.
   	 return state.stoneCount(6)-state.stoneCount(13);
     }
 }
