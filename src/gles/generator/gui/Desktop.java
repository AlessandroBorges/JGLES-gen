/**
 * 
 */
package gles.generator.gui;

import gles.generator.GLFeatureEnum;
import gles.generator.GLmain;
import gles.generator.GLmain.GL_API;
import gles.generator.gui.Operations.OPERATIONS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.GroupBuilder;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
//import javax.swing.MyEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;



/**
 * User Graphics Interface to GLESgen
 * @author Alessandro Borges
 *
 */
@SuppressWarnings("serial")
public class Desktop extends JFrame
implements Observer
{
   

    public static final String EGL = "EGL";
    public static final String GLES1 = "GLES1";
    public static final String GLES2 = "GLES2";
    public static final String GLES3 = "GLES3";
    
    private boolean glXmlLoaded = false;
    private JTabbedPane tabbedPane;
    
    private GLmain glCurrent;
    
    /**
     * 
     */
    private Map<String, MyEditor> mapEditor = new HashMap<String, MyEditor>();
    
    /**
     * options
     */
    private Operations options;
    
    private static final boolean DEBUG = false; 
  
    private Progresso progresso;
    
    /**
     * current api name
     */
    private String currentAPI = "";
    /**
     * store loaded GLmain 
     */
    private Map<String, GLmain> map = new HashMap<String, GLmain>();
    
    static{     
        if(!Beans.isDesignTime()){
            setLF();
          //  DefaultSyntaxKit.initKit();
        }
        
    }
    
    /**
     * set Nimbus Look and Feel
     */
    static private void setLF(){
        try {
            boolean ok = false;
            // disable Nimbus, for while
            boolean useNimbus = false;
            
            if(useNimbus)
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
        
        final JToggleButton btnEgl = new JToggleButton("EGL");
        btnEgl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress("Processing egl.xml...", "Dont Worry! It's pretty fast!");
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
        
        final JToggleButton btnGles = new JToggleButton("GL-ES 1.x");
        btnGles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress("Processing gl.xml for GL-ES 1.x", "It may take a while ...");
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
        
        final JToggleButton btnGles2 = new JToggleButton("GL-ES 2.0+");
        btnGles2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress("Processing gl.xml for GL-ES 2.0+", "It may take a while ...");
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
        btPanel.add(btnGles2);
        
        /////////////////////////////////
        
        final JToggleButton btnGles3 = new JToggleButton("GL-ES 3.x");
        btnGles3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProgress("Processing gl.xml for GL-ES 3.0+", "It may take a while ...");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        doGLES3();
                    }
                };
                Thread t = new Thread(r,"GLES 3.x+ process...");
                t.start();               
            }
        });
        btPanel.add(btnGles3);
        
        /////////////////////////////////
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(btnEgl);
        bg.add(btnGles);
        bg.add(btnGles2);
        bg.add(btnGles3);
        
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout(0, 0));
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panelOptions = new JPanel();
        panelOptions.setLayout(new BorderLayout());       
        options = new Operations();
        panelOptions.add(options, BorderLayout.CENTER);
        options.addObserver(this);
        
        mainPanel.add(panelOptions, BorderLayout.WEST);        
    }
    
    /**
     * @return the glCurrent
     */
    protected final GLmain getGlCurrent() {
        if (glCurrent == null) {
            if (map.size() > 0) {
                Collection<GLmain> col = map.values();
                glCurrent = (GLmain) col.toArray()[0];
            }
        }
        return glCurrent;
    }


    /**
     * @param glCurrent the glCurrent to set
     */
    protected final void setGlCurrent(GLmain glCurrent) {
        this.glCurrent = glCurrent;
    }


    /**
     * 
     * @param btLaunch 
     * @param message
     * @param note
     */
    private void createProgress(String message, String note){
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
        GLmain gl = map.get(EGL);
        if(gl==null){
            gl = new GLmain("", GL_API.EGL, null);
            map.put(EGL, gl);
            gl.go();            
            addXMLEditor("egl.xml", gl.getXML()); 
        } 
        setGlCurrent(gl);        
        
        //String javaClass = gl.asJavaClassCore(GLFeatureEnum.EGL_VERSION_1_5, true);
        //addJavaEditor("EGLcore" , javaClass);
        progresso.done();
    }
    
   
    
    /**
     * generates GLES 1.x source code
     */
    protected void doGLES1(){         
        GLmain gl = map.get(GLES1);        
        if(gl==null){
            gl = new GLmain("", GL_API.GLES1, null);
            map.put(GLES1, gl);
            gl.go();  
        } 
        setGlCurrent(gl);
        if(!glXmlLoaded){
            addXMLViewer("gl.xml", gl.getXML()); 
            glXmlLoaded = true;
        }  
        
      //String javaClass = gl.asJavaClassCore(GLFeatureEnum.GL_VERSION_ES_CM_1_0, true);
      // addJavaEditor("GLESCMcore" , javaClass);
        progresso.done();  
    }
    
    /**
     * generates GLES 2.0+ source code
     */
    protected void doGLES2(){
        GLmain gl = map.get(GLES2);
       
        if(gl==null){
            gl = new GLmain("", GL_API.GLES2, null);
            map.put(GLES2, gl);
            gl.go();  
        } 
        setGlCurrent(gl);
        
        if(!glXmlLoaded){
            addXMLViewer("gl.xml", gl.getXML()); 
            glXmlLoaded = true;
        }  
        
   //    String javaClass = gl.asJavaClassCore(GLFeatureEnum.GL_ES_VERSION_2_0, true);
   //    addJavaEditor("GLES2core" , javaClass);
        progresso.done();
    }
    
    /**
     * generates GLES 3.0+ source code
     */
    protected void doGLES3(){
        GLmain gl = map.get(GLES3);
        if(gl==null){
            gl = new GLmain("", GL_API.GLES3, null);
            map.put(GLES3, gl);
            gl.go();  
        } 
        setGlCurrent(gl);       
        if(!glXmlLoaded){
            addXMLViewer("gl.xml", gl.getXML()); 
            glXmlLoaded = true;
        }  
        
    //   String javaClass = gl.asJavaClassCore(GLFeatureEnum.GL_ES_VERSION_2_0, true);
    //   addJavaEditor("GLES3core" , javaClass);
        progresso.done();
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addJavaEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MyEditor jc = createJavaEditor(panel);
        //jc.setText(text);
        jc.setContent(MyEditor.TYPE_JAVA, text, name);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
        tabbedPane.setSelectedComponent(panel);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addCPPEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MyEditor jc = createCPPEditor(panel);
      //jc.setText(text);
        jc.setContent(MyEditor.TYPE_CPP, text, name);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
        tabbedPane.setSelectedComponent(panel);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addHTMLEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MyEditor jc = createHTMLEditor(panel, text);        
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addHTMLViewer(String name, String content){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MyEditor jc = createHTMLViewer(panel) ;
        jc.setContent(content);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
        tabbedPane.setSelectedComponent(panel);
    }
    
    /**
     * Add a new Editor tab
     * @param name
     */
    public void addXMLEditor(String name, String text){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        MyEditor jc = createXMLEditor(panel);      
        jc.setContent(MyEditor.TYPE_XML,text,name);
        mapEditor.put(name,jc);          
        tabbedPane.addTab(name, null, panel, null);
        tabbedPane.setSelectedComponent(panel);
    }
    
    
    /**
     * Add a new Editor tab for XML
     * @param name  - tab name
     * @param content - url string to load content 
     */
    public void addXMLViewer(String name, String content){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
      
        final MyEditor codeEditor = new MyEditor(false, MyEditor.TYPE_XML);       
        panel.add(codeEditor, BorderLayout.CENTER);
        panel.doLayout(); 
        codeEditor.setContent(content);
        
        mapEditor.put(name,codeEditor);          
        tabbedPane.addTab(name, null, panel, null);
        tabbedPane.setSelectedComponent(panel);
    }
    
    /**
     * 
     * @param c
     * @return
     */
    private MyEditor createEditor(JComponent c, String type){
        final MyEditor codeEditor = new MyEditor(type);       
        c.add(codeEditor, BorderLayout.CENTER);
        c.doLayout();        
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
    private MyEditor createJavaEditor(JComponent c){
        MyEditor codeEditor =  createEditor(c, MyEditor.TYPE_JAVA);         
        return codeEditor;
    }    
    
    /**
     * 
     * @param c
     * @return
     */
    private MyEditor createXMLEditor(JComponent c){
        return createEditor(c, MyEditor.TYPE_XML);
    }
    
    /**
     * TODO - use WebKit 
     * @param c
     * @return
     */
    private MyEditor createHTMLEditor(JComponent c){        
        String html = "<html><body>Hello, world</body></html>";
        return createHTMLEditor(c, html);
    }
    
    /**
     * TODO use webkit
     * @param c component to attach
     * @param content text content
     * @return MyEditor instance
     */
    private MyEditor createHTMLEditor(JComponent c, String content){ 
        final MyEditor codeEditor = new MyEditor(MyEditor.TYPE_HTML);
        codeEditor.setContent(content);
        c.add(codeEditor, BorderLayout.CENTER);
        c.doLayout();
        return codeEditor; 
    }
    
    /**
     * TODO use webkit
     * @param c component to attach
     * @param content text content
     * @return MyEditor instance
     */
    private MyEditor createHTMLViewer(JComponent c){ 
        final MyEditor codeEditor = new MyEditor(MyEditor.TYPE_HTML);         
        c.add(codeEditor, BorderLayout.CENTER);
        c.doLayout();
        return codeEditor; 
    }
    
    /**
     * 
     * @param c - container for editor
     * @return created editor
     */
    private MyEditor createCPPEditor(JComponent c){
        return createEditor(c, MyEditor.TYPE_CPP);
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
        desktop.setSize (new Dimension(960,600));
        desktop.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        desktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop.setLocationRelativeTo(null);
        desktop.setVisible(true);
        
        String html = "<html><body>Hello World</body></html>" ;
        
        try {
            html = readUsingFiles("README.html");
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        desktop.addHTMLViewer("Overview", html );
        if(DEBUG)
            System.err.println(html);

    }
    
    /**
     * nice way to read txt files.
     * @param fileName - file name
     * @return string with file content
     * @throws IOException
     */
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));        
    }


    /**
     * Observable method.
     * It will be called by Options instance every time a button is clicked
     * @param observable - the Options panel instance
     * @param obj - possible the operation to perform.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable observable, Object obj) {
        Object source = ((MyObservable)observable).getSource();
        
        if(source instanceof Operations){
            action(obj);        
        }
        
        if(source instanceof ExtensionChooser){
            setExtensions((List<String>)obj);
        }       
        
    }
    
    private List<String> listExt;
    /**
     * set List of Extension names.
     * @param list
     */
    public void setExtensions(List<String> list){
        listExt = list;
    }
    
    /**
     * perform a action based on obj
     * @param obj - possibly a instance of {@link OPERATIONS}
     */
    public void action(Object obj){        
        System.out.println(obj);
        
        if(!(obj instanceof Operations.OPERATIONS)){
            throw new IllegalArgumentException("Action failed. Unknow object: "+obj);      
        }
        GLmain gl = getGlCurrent();
        
        if(gl==null){
            JOptionPane.showMessageDialog(this,
                      "<html><h2>EGL/GLES definition not loaded yet.</h2><br>"
                    + "<p>To load a API, click on &lt;EGL&gt; &lt;GL-ES x.x&gt; buttons"
                    + " at top menu.</html>",
                    "EGL/GL-ES definition not loaded yet.", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String api = gl.getAPIString();
        
        Operations.OPERATIONS op = (Operations.OPERATIONS)obj;
        
        switch (op) {
        case  C_FUNCTION_LOADER:
        {
            String classNameSugestion = api.toUpperCase()+"SelectedExtensions";
            classNameSugestion = askUserAboutClassName(api, classNameSugestion,
                    "CPP Class with Selected Extensions"); 
            
            List<String> values = askUserAboutExtension(api, gl);
            String pointers   = gl.asCFunctionExtPointers(values,true);           
            String pfnLoaders = gl.asCFunctionExtLoaders(values,true);
            String func_laoders = pointers +"\n" + pfnLoaders;
            
            addCPPEditor(api +"SelectedExt", func_laoders);
        }
            break;
            
        case CPP_CLASS_CORE:
            GLFeatureEnum feature = askUserAboutFeature(gl.api);
            String cppClassCore = gl.asJavaClassCore (feature, false);            
            addCPPEditor(api+"Core" , cppClassCore);
            break;               
            
        case CPP_CLASS_EXT:
        {
            List<String> values = gl.getAllExtensionNames();
            ExtensionChooser chooser = new ExtensionChooser(this,values, api);
            chooser.setTitle("Select extensions for " + api);
            chooser.setPaletteTitle("Select extensions for C++ " + api);
            chooser.setVisible(true);           
            values = chooser.getValue();            
            String classNameSugestion = api.toUpperCase()+"CppSelectedExtensions";
            classNameSugestion = askUserAboutClassName(api, classNameSugestion,
                    "C++ Class with Selected Extensions");      
            
            String cppExtensions = gl.asCclassExt(values, classNameSugestion);
            addCPPEditor(classNameSugestion, cppExtensions);            
        }            
            break;
            
        case JAVA_CLASS_ALL_EXTENSIONS:
            addJavaEditor(api +"Extensions",  gl.asJavaClassExt());
            break;
            
        case JAVA_CLASS_CORE:            
            GLFeatureEnum _feature = askUserAboutFeature(gl.api);
            String javaClass = gl.asJavaClassCore(_feature, false);            
            addJavaEditor(api +"Core" , javaClass);
            break;
            
        case JAVA_CLASS_SELECTED_EXT:   
        {
            List<String> values = gl.getAllExtensionNames();
            ExtensionChooser chooser = new ExtensionChooser(this,values, api);
            chooser.setTitle("Select extensions for " + api);
            chooser.setPaletteTitle("Select extensions for " + api);
            chooser.setVisible(true);           
            values = chooser.getValue();
//            for (String ext : values) {
//                System.err.println(ext);
//            }
            String classNameSugestion = api.toUpperCase()+"SelectedExtensions";
            classNameSugestion = askUserAboutClassName(api, classNameSugestion, "Java Class with Selected Extensions");          
            
            String javaExtensions = gl.asJavaClassExt(values, classNameSugestion);
            addJavaEditor(classNameSugestion, javaExtensions);
        }
            break;
            
        case JAVA_METHOD_EXT:
        {
            List<String> values = gl.getAllExtensionNames();
            ExtensionChooser chooser = new ExtensionChooser(this,values, api);
            chooser.setTitle("Select extensions for " + api);
            chooser.setPaletteTitle("Select extensions for " + api);
            chooser.setVisible(true);           
            values = chooser.getValue();

            String classNameSugestion = api.toUpperCase()+"ExtMethods";
            classNameSugestion = askUserAboutClassName(api, classNameSugestion, "Java Methods for Selected Extensions");          
            
            String javaExtensions = gl.asJavaExtension(values);
            addJavaEditor(classNameSugestion, javaExtensions);
        }
            break;

        default:
            break;
        }
    }
    
    /**
     * Ask user about picking a GLfeature of a API.
     * Default is the highest one
     * @param api - GL_API to choose
     * @return choosed GLFeature 
     */
    private GLFeatureEnum askUserAboutFeature(GL_API api){
        GLFeatureEnum[] features = null;
        GLFeatureEnum defaultFeature = null;
//        if(api==GL_API.EGL){
//            features = GLFeatureEnum.EGL_FEATURES;
//        }
        
        switch (api) {
        case EGL:
            features = GLFeatureEnum.EGL_FEATURES;                
            break;
        case GL:
            features = GLFeatureEnum.GL_FEATURES;                
            break;
        case GLES1:
            features = GLFeatureEnum.GLES1_FEATURES;                
            break;
        case GLES2:
            features = GLFeatureEnum.GLES2_FEATURES;                
            break;
        case GLES3:
            features = GLFeatureEnum.GLES2_FEATURES;                
            break;

        default:
            break;
        }
        
        defaultFeature =  features[features.length-1];
        GLFeatureEnum feature = (GLFeatureEnum)JOptionPane.showInputDialog(this,
                "Choose feature level:",
                "OpenGL-ES feature level", 
                JOptionPane.QUESTION_MESSAGE,
                null, 
                features, 
                defaultFeature);
        
        if(feature==null){
            defaultFeature =  features[features.length-1];
        }
        
        return feature;
    }
    
    /**
     * Ask user to Select Extensions
     * @param api - api to query
     * @param gl - GLmain instance
     * @return selected extension names
     * 
     */
    private List<String> askUserAboutExtension(String api, GLmain gl){
        List<String> values = gl.getAllExtensionNames();
        ExtensionChooser chooser = new ExtensionChooser(this,values, api);
        chooser.setTitle("Select extensions for " + api);
        chooser.setVisible(true);           
        values = chooser.getValue();
        
        return values;
    }
    
    /**
     * Ask user about a nice file name
     * @param api - api used, like api.toUpperCase()+"SelectedExtensions"
     * @param suggestion - 
     * @param msg - Java Class with Selected Extensions
     * @return filename
     */
    private String askUserAboutClassName(String api, String suggestion, String msg){
        String classNameSugestion = suggestion;//api.toUpperCase()+"SelectedExtensions";
        
        classNameSugestion = (String)JOptionPane.showInputDialog(this,
                "Enter File name:",
                msg, //"Java Class with Selected Extensions", 
                JOptionPane.QUESTION_MESSAGE,
                null, 
                null, 
                classNameSugestion);
        
        if(classNameSugestion==null){
            classNameSugestion = suggestion;
        }
        
        return classNameSugestion;
    }
   
}
