/**
 * 
 */
package gles.generator.gui;

import gles.generator.GLmain;
import gles.generator.GLmain.GL_API;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import de.sciss.syntaxpane.DefaultSyntaxKit;



/**
 * @author Alessandro Borges
 *
 */
@SuppressWarnings("serial")
public class Desktop extends JFrame {
    
    
    private static final String C_LOADER_FOR_ALL_EXTENSION_FUNCTIONS = "C++ Loader for all Extension Functions";
    private static final String C_EXTENSION_FUNCTION_LOADERS = "C++ Extension Function Loaders";
    private static final String C_EXT_FUNCTION_POINTERS = "C++ Ext. Function Pointers";
    private static final String JAVA_EXTENSIONS_CLASS = "Java Extensions class";

    private JTabbedPane tabbedPane;
    
    private GLmain gl;
    
    private Map<String, JEditorPane> mapEditor = new HashMap<String, JEditorPane>();
    
  
    private Progresso progresso;
    
    static{     
        if(!Beans.isDesignTime()){
          //  setLF();
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
    
    /**
     * 
     * @param btLaunch 
     * @param message
     * @param note
     */
    private void createProgress(JButton btLaunch, String message, String note){
         progresso = new Progresso(this);
         progresso.setMessage(message);
         progresso.setNote(note);
         progresso.setVisible(true);        
    }
    
    
    /**
     * generate EGL source code. <br>
     * First step.
     */
    protected void doEGL(){        
        gl = new GLmain("", GL_API.EGL, null);  
        gl.go(); 
        
    }
    /**
     *generate EGL source code. <br>
     * Second step. 
     */
    protected void doEGL2(){
        addXMLEditor("egl.xml", gl.getXML());  
        addJavaEditor("EGL::" + JAVA_EXTENSIONS_CLASS,  gl.asJavaClassExt());
        String pointers = gl.asCFunctionExtPointers("*",true);
        addCPPEditor("EGL::" + C_EXT_FUNCTION_POINTERS, pointers);
        String pfnLoaders = gl.asCFunctionExtLoaders("",true);
        addCPPEditor("EGL::" + C_EXTENSION_FUNCTION_LOADERS, pfnLoaders); 
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("EGL::" + C_LOADER_FOR_ALL_EXTENSION_FUNCTIONS, allLoaders);
        progresso.done();
    }
    
    /**
     * generates GLES 1.x source code
     */
    protected void doGLES1(){       
        gl = new GLmain("", GL_API.GLES1, null);
        gl.go();        
        
        addXMLEditor("gl.xml", gl.getXML());
        addJavaEditor("GLES 1.x::" + JAVA_EXTENSIONS_CLASS,  gl.asJavaClassExt());  
        
        String pointers = gl.asCFunctionExtPointers("*",true);
        addCPPEditor("GLES 1.x::" + C_EXT_FUNCTION_POINTERS, pointers);
        
        String pfnLoaders = gl.asCFunctionExtLoaders("",true);
        addCPPEditor("GLES 1.x::" + C_EXTENSION_FUNCTION_LOADERS, pfnLoaders);
        
        
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("GLES 1.x::" + C_LOADER_FOR_ALL_EXTENSION_FUNCTIONS, allLoaders);
                
        progresso.done();
    }
    
    /**
     * generates GLES 2.0+ source code
     */
    protected void doGLES2(){
        gl = new GLmain("", GL_API.GLES2);
        addXMLEditor("gl.xml", gl.getXML());
        addJavaEditor("GLES 2.0::" + JAVA_EXTENSIONS_CLASS,  gl.asJavaClassExt());
        
        String pointers = gl.asCFunctionExtPointers("*",true);
        addCPPEditor("GLES 2.0::" + C_EXT_FUNCTION_POINTERS, pointers);        
               
        String pfnLoaders = gl.asCFunctionExtLoaders("",true);
        addCPPEditor("GLES 2.0::" + C_EXTENSION_FUNCTION_LOADERS, pfnLoaders);
        
        String allLoaders = gl.asCforAllLoadersExt();        
        addCPPEditor("GLES 2.0::" + C_LOADER_FOR_ALL_EXTENSION_FUNCTIONS, allLoaders);
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
        JEditorPane jc = createHTMLEditor(panel, text);        
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
        return createHTMLEditor(c, html);
    }
    
    /**
     * Todo use webkit
     * @param c component to attach
     * @param content text content
     * @return JEditorPane instance
     */
    private JEditorPane createHTMLEditor(JComponent c, String content){ 
        final JEditorPane codeEditor = new JEditorPane("text/html",content);
        JScrollPane scrPane = new JScrollPane(codeEditor);  
        scrPane.getVerticalScrollBar().setValue(0);
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
        
        String html = "<html><body>Hello WOrld</body></html>" ;
        
        try {
          //  html = readUsingFiles("README.html");
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        desktop.addHTMLEditor("Overview", html );
        System.err.println(html);

    }
    
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));        
    }
   
}
