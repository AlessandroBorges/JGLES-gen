package gles.generator.gui;

import java.util.Observable;

/**
 * light wrapper on top of java.util.Observable
 * @author Alessandro Borges
 *
 */
class MyObservable extends Observable{
    
    private Object src;
    
    /**
     * Ctor
     * @param src - source of events 
     */
    public MyObservable(Object src){
        this.src = src;
    }
    
    /**
     * Get source of events
     * @return
     */
    public Object getSource(){
        return src;
    }
    
    @Override
    public void setChanged(){
        super.setChanged();
    }
    
    
    @Override
    public void clearChanged(){
        super.clearChanged();
    }
    
    @Override
    public void notifyObservers(){
        setChanged();
        super.notifyObservers();        
    }
    
    @Override
    public void notifyObservers(Object arg){
        setChanged();
        super.notifyObservers(arg);
    }
}