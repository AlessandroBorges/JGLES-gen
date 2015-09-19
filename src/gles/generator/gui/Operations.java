/**
 * 
 */
package gles.generator.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Menu with operations
 * @author Alessandro Borges
 *
 */
@SuppressWarnings("serial")
public class Operations 
extends JPanel 
implements ActionListener
{
    
    private Map<JButton, OPERATIONS> map = new HashMap<JButton, Operations.OPERATIONS>(8);
    
    
    private MyObservable observable = new MyObservable(this);
    
    public enum OPERATIONS{
        JAVA_CLASS_CORE,
        JAVA_CLASS_ALL_EXTENSIONS,
        JAVA_CLASS_SELECTED_EXT,
        JAVA_METHOD_EXT,
        CPP_CLASS_CORE,
        CPP_CLASS_EXT,
        C_FUNCTION_LOADER
    }
    
    public Operations() {
        setSize(new Dimension(200, 600));
        setPreferredSize(new Dimension(200, 600));
        final Dimension dim = new Dimension(184, 24);
        setBorder(new TitledBorder(null, "Generate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnJavaCore = new JButton("Java Class Core ");     
        btnJavaCore.setPreferredSize(dim);
        add(btnJavaCore);
        
        JButton btnJavaClassAll = new JButton("Java Class All Extensions");
        btnJavaClassAll.setPreferredSize(new Dimension(184, 24));
        add(btnJavaClassAll);
        
        JButton btnJavaClassSelected = new JButton("Java Class Selected Extensions");
        btnJavaClassSelected.setPreferredSize(dim);
        add(btnJavaClassSelected);
        
        JButton btnJavaMethExt = new JButton("Java Method Extension");
        btnJavaMethExt.setPreferredSize(dim);
        add(btnJavaMethExt);
        
        JButton btnCCore = new JButton("C++ Class Core");
        btnCCore.setPreferredSize(dim);
        btnCCore.setEnabled(false);
        add(btnCCore);
        
        JButton btnCExtension = new JButton("C++ Class Extension");
        btnCExtension.setPreferredSize(dim);
        add(btnCExtension);
        
        JButton btnCC = new JButton("C / C++ Ext. PFN & Loader");
        btnCC.setPreferredSize(dim);
        add(btnCC);
        
        add(btnJavaCore, OPERATIONS.JAVA_CLASS_CORE);
        add(btnJavaClassAll, OPERATIONS.JAVA_CLASS_ALL_EXTENSIONS);
        add(btnJavaClassSelected, OPERATIONS.JAVA_CLASS_SELECTED_EXT);
        add(btnJavaMethExt, OPERATIONS.JAVA_METHOD_EXT);
        add(btnCCore, OPERATIONS.CPP_CLASS_CORE);
        add(btnCExtension, OPERATIONS.CPP_CLASS_EXT);
        add(btnCC, OPERATIONS.C_FUNCTION_LOADER);
    }
    
    /**
     * register button
     * @param bt button to register
     * @param op operation to call
     */
    private void add(JButton bt, OPERATIONS op){
        map.put(bt, op);
        bt.addActionListener(this);
    }

    /**
     * Add a observer to this object
     * @param o
     */
    public void addObserver(Observer o){
        observable.addObserver(o);
    }
    
    /**
     * Get this observable instance
     * @return
     */
    public Observable getObservable(){
        return this.observable;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       Object src = e.getSource();
       
       Operations.OPERATIONS op = map.get(src);
       observable.setChanged();
       observable.notifyObservers(op);  
       //System.err.println("Action: " + op);
       observable.clearChanged();
    }

}


