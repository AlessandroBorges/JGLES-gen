/**
 * 
 */
package gles.generator.gui;

import gles.generator.GLmain;
import gles.generator.GLmain.GL_API;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.nio.channels.CancelledKeyException;
import java.util.*;

import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import de.sciss.syntaxpane.DefaultSyntaxKit;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



/**
 * @author Alessandro Borges
 *
 */
public class Desktop extends JFrame {
    
    
    private JTabbedPane tabbedPane;
    
    private GLmain gl;
    
    private Map<String, JEditorPane> mapEditor = new HashMap<String, JEditorPane>();
    
  
    private Progresso progresso;
    
    static{     
        if(!Beans.isDesignTime()){
            setLF();
            DefaultSyntaxKit.initKit();
        }
        
    }
    
    static private void setLF(){
        try {
            boolean ok = false;
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    ok = true;
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("L&F: " + info.getName());
                    break;
                }
            }
            if(!ok){
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("LF: " + UIManager.getSystemLookAndFeelClassName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Constructor
     */
    public Desktop() {
        setTitle("GLES :: OpenGL-ES Binding Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));
        
        JPanel topPanel = new JPanel();
        topPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        topPanel.setMinimumSize(new Dimension(300, 120));
        topPanel.setPreferredSize(new Dimension(300, 40));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BorderLayout(0, 0));
        
        JPanel btPanel = new JPanel();
        topPanel.add(btPanel, BorderLayout.CENTER);
        
        final JButton btnEgl = new JButton("EGL");
        btnEgl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress(btnEgl, "Processing egl.xml...", "Dont Worry! It's pretty fast!");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doEGL();
                    }
                };
                Thread t = new Thread(r,"EGL process...");
                t.start();
                
            }
        });
        btPanel.add(btnEgl);
        
        final JButton btnGles = new JButton("GL-ES 1.x");
        btnGles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress(btnGles, "Processing gl.xml for GL-ES 1.x", "It may take a while ...");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doGLES1();
                    }
                };
                Thread t = new Thread(r,"GLES 1.x process...");
                t.start();
            }
        });
        btPanel.add(btnGles);
        
        final JButton btnGles_1 = new JButton("GL-ES 2.0+");
        btnGles_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress(btnGles, "Processing gl.xml for GL-ES 2.0+", "It may take a while ...");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doGLES2();
                    }
                };
                Thread t = new Thread(r,"GLES 2.0+ process...");
                t.start();               
            }
        });
        btPanel.add(btnGles_1);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout(0, 0));
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
    }
    
    
    private void createProgress(JButton btLaunch, String message, String note){
         progresso = new Progresso(this);
         progresso.setMessage(message);
         progresso.setNote(note);
         progresso.setVisible(true);        
    }
    
  
    
    protected void doEGL(){        
        gl = new GLmain("", GL_API.EGL, null);  
        gl.go(); 
        addXMLEditor("egl.xml", gl.getXML());  
        addJavaEditor("Java EGL Extensions",  gl.asJavaClassExt());
        String pointers = gl.asCFunctionExtPointers("*");
        addCPPEditor("C++ EGL Function Pointers", pointers);
        String pfnLoaders = gl.asCFunctionExtLoaders("");
        addCPPEditor("C++ EGL ext. func. Loaders", pfnLoaders); 
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("C++ EGL ext. all func. loaders", allLoaders);
        progresso.done();
    }
    
    protected void doGLES1(){       
        gl = new GLmain("", GL_API.GLES1, null);
        gl.go();        
        
        addXMLEditor("gl.xml", gl.getXML());
        addJavaEditor("Java GLES 1.x Ext.",  gl.asJavaClassExt());  
        
        String pointers = gl.asCFunctionExtPointers("*");
        addCPPEditor("C++ GLES 1.x Extension Function Pointers", pointers);
        
        String pfnLoaders = gl.asCFunctionExtLoaders("");
        addCPPEditor("C++ GLES 1.x Extension Function Loaders", pfnLoaders);
        
        
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("C++ GLES 1.x Loader for all Extension Function", allLoaders);
                
        progresso.done();
    }
    
    protected void doGLES2(){
        gl = new GLmain("", GL_API.GLES2);
        addXMLEditor("gl.xml", gl.getXML());
        addJavaEditor("Java GLES 2.0+ Ext.",  gl.asJavaClassExt());
        
        String pointers = gl.asCFunctionExtPointers("*");
        addCPPEditor("C++ GLES 2.0+ Extension Function Pointers", pointers);        
               
        String pfnLoaders = gl.asCFunctionExtLoaders("");
        addCPPEditor("C++ GLES 2.0+ Extension Function Loaders", pfnLoaders);
        
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("C++ GLES 2.0+ Loader for all Extension Function", allLoaders);
        progresso.done();
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addJavaEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JEditorPane jc = createJavaEditor(panel);
        jc.setText(text);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addCPPEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JEditorPane jc = createCPPEditor(panel);
        jc.setText(text);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addHTMLEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JEditorPane jc = createHTMLEditor(panel);
        jc.setText(text);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addXMLEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JEditorPane jc = createXMLEditor(panel);
        jc.setText(text);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
    }
    
    
    /**
     * 
     * @param c
     * @return
     */
    private JEditorPane createEditor(JComponent c, String type){
        final JEditorPane codeEditor = new JEditorPane();
        JScrollPane scrPane = new JScrollPane(codeEditor);
        c.add(scrPane, BorderLayout.CENTER);
        c.doLayout();
        codeEditor.setContentType(type);
       // codeEditor.setFont(getEditorFont());
       // codeEditor.setText("public static void main(String[] args) {\n}");
        return codeEditor;
    }
    
    protected Font getEditorFont(){
        Font font = new Font("Consolas",Font.BOLD, 12);
        return font;
    }
    
    /**
     * 
     * @param c
     * @return
     */
    private JEditorPane createJavaEditor(JComponent c){
        JEditorPane codeEditor =  createEditor(c, "text/java");         
        return codeEditor;
    }    
    
    /**
     * 
     * @param c
     * @return
     */
    private JEditorPane createXMLEditor(JComponent c){
        return createEditor(c, "text/xml");
    }
    
    /**
     * TODO - use WebKit 
     * @param c
     * @return
     */
    private JEditorPane createHTMLEditor(JComponent c){        
        String html = "<html><body>Hello, world</body></html>";
        final JEditorPane codeEditor = new JEditorPane("text/html",html);
        JScrollPane scrPane = new JScrollPane(codeEditor);
        c.add(scrPane, BorderLayout.CENTER);
        c.doLayout();
        return codeEditor;  
    }
    
    /**
     * 
     * @param c - container for editor
     * @return created editor
     */
    private JEditorPane createCPPEditor(JComponent c){
        return createEditor(c, "text/cpp");
    }
    
    protected JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // use invoke later
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Desktop.runMe();
            }
        });

        // System.out.println("Content types:");
        // String[] types = DefaultSyntaxKit.getContentTypes();
        // for (String type : types) {
        // System.out.println(type);
        // }

    }
    
    private static void runMe(){
        Desktop desktop = new Desktop();
        desktop.setSize (new Dimension(800,600));
        desktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop.setLocationRelativeTo(null);
        desktop.setVisible(true);
        desktop.addHTMLEditor("Overview", "<html><body>Hello, world</body></html>" );
//        desktop.addCPPEditor("Cpp.cpp","// cpp \n int main(){\n}");
//        desktop.addJavaEditor("Java.java","// javaCode\n public static void main(String[] args){\n  }\n");
//        desktop.addXMLEditor("test.xml","<beep>\n\t<text> BLUP ! Blup! </text>\n</beep>\n");
        
    }
    
   
   
}
