/**
 * 
 */
package gles.generator.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

/**
 * @author Alessandro Borges
 *
 */
@SuppressWarnings("serial")
public class ExtensionChooser extends JDialog {
    
    private JFrame owner;
    /**
     * Extension Pallete
     */
    private Palette palete;
    /**
     * use observable to send message to owner
     */
    private MyObservable observable = new MyObservable(this);
    
    private List<String> value;
    
    public ExtensionChooser() {
        init();
    }
    
    /**
     * create Extension Chooser
     * @param frame - owner
     * @param values - list of values
     * @param availableTitle - title for chooser
     */
    public ExtensionChooser(JFrame frame, List<String> values, String availableTitle) {
        super(frame);
        this.owner = frame;
        this.palete = new Palette();
        palete.setAvailableList(values);
        palete.setAvailableListTitle(availableTitle);
        if(frame instanceof Observer){
            observable.addObserver((Observer)frame);
        }else{
            System.err.println("frame is not observable.");
        }
        init();
    }
    
    
    protected void init(){
        setModal(true);
        setAlwaysOnTop(true);
        setTitle("Extension Chooser");
        setSize(800,600);
        setLocationRelativeTo(owner);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder(null, "Extension Chooser", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));
        
        if(palete != null){
            mainPanel.add(palete, BorderLayout.CENTER);
        }
        
        JPanel panel = new JPanel();
        mainPanel.add(panel, BorderLayout.SOUTH);
        
        JButton btcancel = new JButton("Cancel");
        panel.add(btcancel);
        
        JButton btNext = new JButton("Next");
        btNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNext();
            }
        });
        panel.add(btNext);       
    }

    
    /**
     * @param availableTitle
     * @see gles.generator.gui.Palette#setAvailableListTitle(java.lang.String)
     */
    public void setAvailableListTitle(String availableTitle) {
        palete.setAvailableListTitle(availableTitle);
    }

    /**
     * @param availableTitle
     * @see gles.generator.gui.Palette#setSelectListTitle(java.lang.String)
     */
    public void setSelectListTitle(String availableTitle) {
        palete.setSelectListTitle(availableTitle);
    }

    /**
     * @param title
     * @see gles.generator.gui.Palette#setPaletteTitle(java.lang.String)
     */
    public void setPaletteTitle(String title) {
        palete.setPaletteTitle(title);
    }

    protected void doNext(){
        setVisible(false);
        dispose();  
        value = palete.getSelected();
        observable.notifyObservers(value);
    }
    
    protected void doCancel(){
        setVisible(false);
        dispose();  
        value = palete.getSelected();
        value.clear();
        observable.notifyObservers(value);
    }
    
    /**
     * Get Selected values
     * @return
     */
    public List<String> getValue(){
        return palete.getSelected();
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DialogDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
 
       frame.setLocationByPlatform(true);
       
       // newContentPane.setOpaque(true); //content panes must be opaque
        //frame.setContentPane(newContentPane);
 
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
        String[] options = {"abc", "cbe", "cba"};
        ExtensionChooser dialog = new ExtensionChooser(frame, Arrays.asList(options),"Chooser title" );
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
 

}
