/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hebproject;

/**
 *
 * @author david
 */
public class Word {
    
    String name;
    int count;
    
    public Word(String name){
    
    this.name = name;
    count = 1;
    }
    
    public String getName(){
    
    return name;
    }
    
    public int getCount(){
    
    return count;
    }
    
    public void addCount(){
        count++;
    }
}
