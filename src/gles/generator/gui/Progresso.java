package gles.generator.gui;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;



@SuppressWarnings("serial")
public class Progresso extends JDialog {
    private JLabel lbMessage;
    private JLabel lbNote;
    private JProgressBar progressBar;
    
    private Window owner = null;
    
    public Progresso(){
        init();
    }
    
    public Progresso(Window owner) {
        super(owner);
        this.owner = owner;
        init();   
    }
    
    protected void init(){
        setTitle("Progress...");        
        final JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);    
        panel.setLayout(null);
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(10, 83, 255, 30);
        panel.add(progressBar);
        
        lbMessage = new JLabel("Message");
        lbMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
        lbMessage.setBounds(10, 13, 255, 24);
        panel.add(lbMessage);
        
        lbNote = new JLabel("Note");
        lbNote.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbNote.setBounds(10, 48, 255, 24);
        panel.add(lbNote);
        setSize(292, 175);
        setLocationRelativeTo(owner);
        
    }
    
    public void setNote(String note){
        lbNote.setText(note);
    }
    
    public void setMessage(String msg){
        lbMessage.setText(msg);
    }
    
    /**
     * Finalize this Progresso dialog
     */
    public void done(){
        setMessage("Task done !");
        setNote("100 %");
        progressBar.setIndeterminate(false);
        progressBar.setValue(100);
        progressBar.setString("100 %");
        
        SwingUtilities.invokeLater(new Runnable() {            
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finish();                
            }
        });
    }
    
    protected void finish(){
        setVisible(false);
        owner = null;
    }
    
}
