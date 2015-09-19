/**
 * 
 */
package gles.generator.gui;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.sciss.syntaxpane.DefaultSyntaxKit;

/**
 * Class to wrap JEditorPane and WebViewPanel
 * @author Alessandro Borges
 *
 */
public class MyEditor extends JPanel {
    /**
     * JavaFX WebKit
     */
    private WebViewPanel webViewPanel;
    /**
     * Plain Java Editor
     */
    private JEditorPane editorPane;
    
    private static final String hellWorld = "<html><body><h1>Hello, World!</h1></body></html>";
    
    private boolean DEBUG = false;
    /**
     * static flag about using JavaFX
     */
    private static boolean _static_useFX;
    
    /**
     * useFX per instance
     */
    private boolean useFX = _static_useFX;
      
    /**
     * Where to store files to load with WebView
     */
    private static File tempFolder;
    
    /**
     * Content Types
     */
    public static final String TYPE_JAVA = "text/java",
                               TYPE_JAVAFX = "text/javafx",
                               TYPE_CPP = "text/cpp",
                               TYPE_CSHARP = "text/csharp",
                               TYPE_XML = "text/xml",
                               TYPE_HTML = "text/html",
                               TYPE_HTML_SRC = "text/plain";
    
    /**
     * Main data
     */
    private String content=hellWorld, 
                   type = TYPE_HTML , 
                   title = "index.html";
    /**
     * DefaultTheme
     */
    private String defaultTheme = THEME_CORE_DEFAULT;
    
    static {
        String defaultValue = "0";
        String version = com.sun.javafx.runtime.VersionInfo.getRuntimeVersion();
        System.out.println("JavaFX: "+ version);
        _static_useFX = version !=null && !version.equals(defaultValue);      
        if (_static_useFX) {
            try {
                Unzip unzip = new Unzip();
                tempFolder = unzip.unzipSyntaxScripts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //init JEDITOR
            DefaultSyntaxKit.initKit();
        }
    }// static
    
    
    /**
     * Plain Ctor.<br>
     * Same as MyEditor(false, TYPE_HTML) 
     * 
     */
    public MyEditor(){
        this(false, TYPE_HTML); 
    }
    
   /**
    * Ctor 
    * @param useFx - set true to use JavaFX, false to use JEditorPane
    * @param contentType - content type
    */
    public MyEditor(boolean useFx, String contentType){
        super();
        this.useFX = useFx;
        this.type = contentType;
        init();
    }
    
    /**
     * initialize GUI
     */
    protected void init(){        
        setLayout(new BorderLayout());
        
        if(useFX){
           System.out.println("JavaFX mode.");
           webViewPanel = new WebViewPanel(); 
           add(webViewPanel,BorderLayout.CENTER);          
        }else{
            DefaultSyntaxKit.initKit();
            System.out.println("JEditor mode.");
            editorPane = new JEditorPane(type,content);
            JScrollPane scroll = new JScrollPane(editorPane);
            add(scroll,BorderLayout.CENTER);
        }
    }
    
    /**
     * Create a Editor with Content type defined.
     *  *  Must be one of : <br>
     *  {@link #TYPE_JAVA} - to display Java source<br>
     *  {@link #TYPE_JAVAFX} - to display JavaFX source<br>
     *  {@link #TYPE_CPP} - to display C++ source<br>
     *  {@link #TYPE_CSHARP} - to display Csharp source<br>
     *  {@link #TYPE_XML} - to display XML <br>
     *  {@link #TYPE_HTML} - to display HTML<br>
     *  {@link #TYPE_HTML_SRC} - to display HTML source<br>  
     *  
     * 
     * @param contentType - content type is one of above types
     */
    public MyEditor(String contentType){
        super();
        this.type = contentType;
        init();
    }
    
    /**
     * Create a editor.<br>
     * type is one of : <br>
     *  {@link #TYPE_JAVA} - to display Java source<br>
     *  {@link #TYPE_JAVAFX} - to display JavaFX source<br>
     *  {@link #TYPE_CPP} - to display C++ source<br>
     *  {@link #TYPE_CSHARP} - to display Csharp source<br>
     *  {@link #TYPE_XML} - to display XML <br>
     *  {@link #TYPE_HTML} - to display HTML<br>
     *  {@link #TYPE_HTML_SRC} - to display HTML source<br>  
     *  
     * @param type - one of above types
     * @param content - text content
     * @param title - Editor title. Can ne null
     */
    public MyEditor(String type, String content, String title){
        super();
        this.content = content;
        this.type = type;
        this.title = fixName( title);
        init();
    }
    
    /**
     * Set URL
     * @param url
     */
    public void setURL(String url){
        if(webViewPanel !=null){
            webViewPanel.setURL(url);
            return;
        }
        
        try {
            String out = new Scanner(new URL(url).openStream(), "UTF-8")
            .useDelimiter("\\A").next();
            this.setContentType(this.type);
            this.editorPane.setText(out);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * Set  Theme.
     * One of :<br>
     * {@link #THEME_CORE_DEFAULT} <br>
     * {@link #THEME_DEFAULT}<br>
     * {@link #THEME_MIDNIGHT}<br>
     * {@link #THEME_RDARK}<br>
     * 
     * @param theme - theme to set
     */
    public void setTheme(String theme){
        defaultTheme = theme;
        reload();
    }
    
   /**
    * Set Content.
    * 
    * 
    * @param type - content type. One of {@link #TYPE_XML}, {@link #TYPE_JAVA}, 
    *               {@link #TYPE_CPP} , {@link #TYPE_HTML}, {@link #TYPE_XML} or other.
    * @param newContent - content to display
    * @param title - title or filename
    * 
    * @see #TYPE_CPP
    * @see #TYPE_JAVA
    * @see #TYPE_HTML
    * @see #TYPE_HTML_SRC
    * @see #TYPE_XML
    * @see #TYPE_CSHARP
    * @see #TYPE_JAVAFX
    */
    public void setContent(String type, String newContent, String title){
        this.content = newContent;
        this.type = type;
        this.title = fixName(title);
        if(useFX){
            setWebViewContent(this.type, this.content, this.title);
        }else{
            editorPane.setContentType(type);
            editorPane.setText(newContent);
        }
    }
    
    /**
     * Date format for file saving
     */
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");//("yyyyMMdd.HHmmssSS");
    
    /**
     * fix invalid names
     * @param name input names
     * @return fixed name
     */
    private String fixName(String name){      
     
        if(name == null || name.trim().length()<2){
            name = "GLESgen";
            String date = format.format(new Date());
            name +=date;
        }
        String nm = name;
        
        return nm;
    }
    
    /**
     * Set content type.
     *  Must be one of : <br>
     *  {@link #TYPE_JAVA} - to display Java source<br>
     *  {@link #TYPE_JAVAFX} - to display JavaFX source<br>
     *  {@link #TYPE_CPP} - to display C++ source<br>
     *  {@link #TYPE_CSHARP} - to display Csharp source<br>
     *  {@link #TYPE_XML} - to display XML <br>
     *  {@link #TYPE_HTML} - to display HTML<br>
     *  {@link #TYPE_HTML_SRC} - to display HTML source<br>  
     *  
     * 
     * @param type
     */
    public void setContentType(String type){
        this.type = type;
        if(content!=null && content.trim().length() > 1 ){
            reload();
        }
    }
    
    /**
     * Content to display.
     * Uses current type and title. <br>
     *  Force a reload of content.
     * @param content - content to show
     */
    public void setContent(String content){
        this.content = content;
        reload();
    }
    
    /**
     * Set new title. Force a reload of content
     * @param title
     */
    public void setTitle(String title){
        this.title = fixName(title);
        reload();
    }
    
    /**
     * Reloads content.<br>
     * Same as <pre>
     *  setContent(this.type, this.content, this.title);
     *  </pre>
     *  
     *  @see #setContent(String, String, String)
     */
    private void reload(){
        if(this.type==TYPE_HTML){
            setWebViewContent(type, content, title);
        }else
            setContent(this.type, this.content, this.title);
    }
    
    /**
     * 
     * @param webContent
     */
    private void setWebViewContent(String type, String webContent, String title){
        if(!useFX){
            this.editorPane.setContentType(type);
            this.editorPane.setText(webContent);            
            return;
        }
        
        if(type==TYPE_HTML){
            webViewPanel.setContent(webContent);
            return;
        }
                
        String txt ="";
        if(type==TYPE_HTML_SRC){
            if(!title.endsWith(".html")){
                title +=".html";
            }
            txt = prepareHTMLxml(webContent, title);
        }else
        
        if(type==TYPE_XML){
            if(!title.endsWith(".xml")){
                title +=".xml";
            }
            txt = prepareHTMLxml(webContent, title);
        } else
        
        if(type==TYPE_JAVA){
            if(!title.endsWith(".java")){
                title +=".java";
            }
            txt = prepareHTMLJava(webContent, title);
        }else
        
        if(type==TYPE_JAVAFX){
            if(!title.endsWith(".jfx")){
                title +=".jfx";
            }
            txt = prepareHTMLjavaFX(webContent, title);
        }else
        
        if(type==TYPE_CPP){
            if(!title.endsWith(".cpp")){
                title +=".cpp";
            }
            txt = prepareHTMLcpp(webContent, title);
        }else
        
        if(type==TYPE_CSHARP){
            if(!title.endsWith(".cs")){
                title +=".cs";
            }
            txt = prepareHTMLcsharp(webContent, title);
        }else{
            // Unknown type is set as txt, but renderer as cpp
            if(!title.endsWith(".txt")){
                title +=".txt";
            }
            txt = prepareHTMLcpp(webContent, title);
        }
        
        try{
        File url = save(title, txt);        
        webViewPanel.setURL(url.toURI().toURL().toString());
        }catch(Exception ex){
            ex.printStackTrace();            
        }
    }
    
    /**
     * Save file in temp folder
     * @param fileName
     * @param content
     * @return file reference
     * @throws IOException 
     */
    private File save(String fileName, String content) throws IOException{
               
        File file = new File(tempFolder, fileName+".html");
       
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

        System.out.println("Done saving " + file.toURI().toURL());
        
        return file;
    }
    
    /**
     * Get Content.
     * @return content text
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * Prepare HTML to display Java code
     * @param code - source code for Java
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLJava(String code,String name){
        String text = HTML;
                       
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_JAVA);
        text = setToken(text, SCRIPT, SCRIPT_JAVA);
        text = setToken(text, THEME, defaultTheme);
        
        String ccode = uniformHTML(code);
        text = setToken(text, CODE, ccode); 
        return text;
    }
    
    /**
     * Prepare HTML to display XML code
     * @param code - source code for Java
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLxml(String code,String name){
        String text = HTML;
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_XML);
        text = setToken(text, SCRIPT, SCRIPT_XML);
        text = setToken(text, THEME, defaultTheme);
        
        String ccode = uniformHTML(code);
        text = setToken(text, CODE, ccode); 
        return text;
    }
    
    /**
     * Prepare HTML to display HTML code
     * @param code - source code for Java
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLhtml(String code,String name){
        String text = HTML;
        text = setToken(text, CODE, code);        
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_HTML);
        text = setToken(text, SCRIPT, SCRIPT_HTML);
        text = setToken(text, THEME, defaultTheme);
        return text;
    }
    
    /**
     * Prepare HTML to display C++ code
     * @param code - source code for C++
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLcpp(String code, String name){
        String text = HTML;
              
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_CPP);
        text = setToken(text, SCRIPT, SCRIPT_CPP);
        text = setToken(text, THEME, defaultTheme);
        
        String ccode = uniformHTML(code);
        text = setToken(text, CODE, ccode); 
        return text;
    }
    
    /**
     * Prepare HTML to display C# code
     * @param code - source code for C#
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLcsharp(String code,String name){
        String text = HTML;
                
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_CSHARP);
        text = setToken(text, SCRIPT, SCRIPT_CSHARP);
        text = setToken(text, THEME, defaultTheme);
        
        String ccode = uniformHTML(code);
        text = setToken(text, CODE, ccode);
        return text;
    }
    
    
    /**
     * Prepare HTML to display JavaFX code
     * @param code - source code for JavaFX
     * @param name - name to display
     * 
     * @return HTML to display Java Source
     */
    private String prepareHTMLjavaFX(String code, String name){
        String text = HTML;
              
        text = setToken(text, NAME, name);
        text = setToken(text, BRUSH, BRUSH_JAVAFX);
        text = setToken(text, SCRIPT, SCRIPT_JAVAFX);
        text = setToken(text, THEME, defaultTheme);
        
        String ccode = uniformHTML(code);
        text = setToken(text, CODE, ccode); 
        return text;
    }
    
    
    
    /**
     * Replaces token by a value.
     * @param text - text to replace token
     * @param token - token to replace
     * @param value - new value to put on token
     * @return text with all tokens replaced
     */
    private String setToken(String text, String token, String value){
        String res = text;   
        int count = 0;
        while(res.contains(token)){
            res = res.replace(token, value);
            count++;
        }    
//        System.out.println("Replaced Token: " + token + 
//                " by " + value + " " + count + " times.\n"+   res);
        return res;
    }
    
    /**
     * uniformize text to html, by replacing : <br>  
     *  "<" by & lt ; <br>
     *  ">" by & gt ; <br>
     *  "&" by & amp ; <br>
     *  "\"" by & quot ;<br>
     * @param text
     * @return
     */
    private String uniformHTML(String text){
        String res = text;
        
        // < -> &lt;
        res = setToken(res,"<", "&lt;");
        
        if(res.length()>2)
            return res;
        // & -> &amp;
        res = setToken(res, "&", "&amp;");
        // " -> &quot;      
        res = setToken(res,"\"", "&quot;");
       
        // > -> &gt;
        res = setToken(res,">", "&gt;");
        return res;
    }
    
    /**
     * Brushes for Syntax highlight
     */
    public static final String BRUSH_JAVA = "java",
                                BRUSH_JAVAFX = "javafx",
                                BRUSH_CPP = "cpp",
                                BRUSH_CSHARP = "csharp",
                                BRUSH_XML = "xml",
                                BRUSH_HTML = "html";
     
    /**
     * Script for Syntax Highlight
     */
    public static final String SCRIPT_JAVA = "shBrushJava.js",
                                SCRIPT_CPP  = "shBrushCpp.js",
                                SCRIPT_CSHARP  = "shBrushCSharp.js",
                                SCRIPT_XML  = "shBrushXml.js",
                                SCRIPT_HTML  = "shBrushHtml.js",
                                SCRIPT_JAVAFX = "shBrushJavaFX.js";
  
    /**
     * Themes available.
     * @see #setTheme(String) 
     */
    public static final String THEME_DEFAULT = "shThemeDefault.css",
                                THEME_CORE_DEFAULT = "shCoreDefault.css",
                                THEME_MIDNIGHT = "shThemeMidnight.css",
                                THEME_RDARK = "shThemeRDark.css",
                                THEME_EMAC = "shThemeEmacs.css";
            
            
    /**
     *  Tags used to configure editor. 
     *  @see #prepareHTMLJava(String, String)
     *  @see #prepareHTMLcpp(String, String)
     *  @see #prepareHTMLxml(String, String)
     *  @see #prepareHTMLhtml(String, String)      
     */
     protected static final String CODE="#CODE#", 
                                NAME="#NAME#",
                                BRUSH = "#BRUSH#",
                                THEME = "#THEME#",
                                SCRIPT = "#SCRIPT#";
    
    private static final String HTML = 
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org//1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
            + "<head>\n"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
            + "<title>GLES Gen :: #NAME#</title>\n"
            + "<script type=\"text/javascript\" src=\"scripts/shCore.js\"></script>\n"
            + "<script type=\"text/javascript\" src=\"scripts/#SCRIPT#\"></script>\n" 
            + "<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/#THEME#\"/>\n"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>\n"
            + "</head>\n"
            + "<body style=\"background: white; font-family: Helvetica\">\n"
            + "<center><h2>#NAME#</h2></center>\n"
            + "<pre id=\"myCode\" class=\"brush: #BRUSH#;\">" 
            + "#CODE#\n\n"
            + "</pre>\n</html>\n";

    
    public static void main(String[] args){
        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        JFrame frame = new JFrame("Test Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        String java = "/** Example **/\n"
                + " public class Hello{\n"
                + "   public static String hi = \"Hi!\";\n\n"
                + "   public static void main(String[] args){\n"
                + "      System.out.println(hi);\n"
                + "   }\n"
                + " }";
        
        MyEditor editor = new MyEditor(false,MyEditor.TYPE_XML);
        frame.getContentPane().add(editor, BorderLayout.CENTER);
        frame.setVisible(true);
        
       // editor.setURL("http://www.google.com.br");
       // editor.setURL("file:///C:/Users/ALESSA~1/AppData/Local/Temp/GLESgen/HelloWorld.java.html");
       // editor.setContent(TYPE_JAVA, java, "HelloWorld");
        editor.setURL("file:///C:/Users/Livia/Documents/GitHub/JGLES-gen/gl.xml");
        
    }
    
   
}
