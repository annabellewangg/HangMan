/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ddavid
 */
public class HMRunner {
   
    
    public static void main(String[] args) {
        HangMan m = new HangMan();
        
        //System.out.println("should return 4 for updateScore(3, true):" + Logic.updateScore(3,  true));
        System.out.println("should return 'rsltn' for addStrike(rslt, n):" + Logic.addStrike("rslt", "n"));
        System.out.println("should return 'abc' for addStrike(abc, c):" + Logic.addStrike("abc", "c"));
        System.out.println("should return 'true' for guessWord(word, WORD):" + Logic.guessWord("word", "WORD"));
        
        
    }
    
}
