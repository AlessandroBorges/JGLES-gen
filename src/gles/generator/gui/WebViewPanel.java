/**
 * 
 */
package gles.generator.gui;

import java.awt.BorderLayout;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class WebViewPanel extends JPanel  {
    
    static boolean DEBUG = false;

    private Browser browser;
    private Scene scene;
    private int ww, hh;
    
    /**
     * current content
     */
    protected String content;
    /**
     * current url
     */
    protected String url;
    
    private static final int SET_CONTENT = 01, SET_URL = 02;

    public WebViewPanel() {
        this(400,500);
    }
    
    public WebViewPanel(int width, int height) {
        setLayout(new BorderLayout());
        final JFXPanel fxPanel = new JFXPanel(); 
        add(fxPanel,BorderLayout.CENTER);
        add(fxPanel);
        this.ww = width;
        this.hh = height;
        setSize(width, height);
        //addComponentListener(new MySizeListener());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        this.scene = createScene();
        
        fxPanel.setScene(scene);
    }

    public Scene createScene() {
        this.browser = new Browser();
        final Scene scene = new Scene(browser, ww  , hh, Color.web("#666970"));
        scene.getStylesheets().add("gles/generator/gui/BrowserToolbar.css");
        return scene;
    }
    
   
    /**
     * Set HTML content
     * 
     * @param content - html content
     */
    public void setContent(String content) {
        this.content = content;
        invokeLater(content, SET_CONTENT);        
    }
    
    /**
     * 
     * @return
     */
    public String getContent(){
        return content;
    }
    
    /**
     * set URL to display, as "http://www.google.com"
     * @param url url to load on webview
     */
    public void setURL(String url){
        this.url = url;
        invokeLater(url, SET_URL);
    }
    
    /**
     * 
     * @return last url set
     */
    public String getURL(){
        return url;
    }
    
    /**
     * Perform a lazy call to certain operations
     * @param data
     * @param operation
     */
    private void invokeLater(String data, int operation){ 
        Runnable r = new MyRun(data, operation);
        Platform.runLater(r);
    }
    
    
//    class MySizeListener extends ComponentAdapter{        
//        public void componentResized(ComponentEvent e){
//            int w = e.getComponent().getWidth();
//            int h = e.getComponent().getHeight();
//            if(browser != null){
//                browser.setSize(w,h);               
//            }
//        }
//    }
    
    /**
     * runnable for operation
     * @author Alessandro Borges
     *
     */
    class MyRun implements Runnable{
        final String _data;
        final int _operation;
        
        public MyRun(String data, int operation) {           
            _data = data;
            _operation = operation;
        }
        
        @Override
        public void run() {                 
            while(browser == null || ( browser != null && !browser.isVisible())){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(DEBUG)
                System.err.println("MyRun debug: " + _data );
            switch(_operation){
            case SET_CONTENT:  
                if(DEBUG)
                    System.err.println("MyRun debug: setContent." );
                browser.setContent(_data); 
                break;
            case SET_URL :
                if(DEBUG)
                    System.err.println("MyRun debug: loadUrl." );
                browser.loadURL(_data);
                break;
            }
        }
    }
    
    /**
     * The Browser !!!
     * @author Alessandro Borges
     *
     */
    class Browser extends Region {

        final WebView _browser = new WebView();
        final WebEngine webEngine = _browser.getEngine();
        private double _w, _h;

        /**
         * Ctor
         */
        public Browser() {
            // apply the styles
            getStyleClass().add("browser");
            getChildren().add(_browser);
            webEngine.setCreatePopupHandler(new javafx.util.Callback<PopupFeatures, WebEngine>() {
                @Override
                public WebEngine call(PopupFeatures p) {
                    Stage stage = new Stage(StageStyle.UTILITY);
                    WebView wv2 = new WebView();
                    stage.setScene(new Scene(wv2));
                    stage.show();
                    return wv2.getEngine();
                }
            });
        }

        /**
         * Set content
         * 
         * @param content
         *            - html content
         */
        public void setContent(String content) {
            webEngine.loadContent(content);
        }
        
        /**
         * Set new size
         * @param width
         * @param height
         */
        public void setSize(int width, int height){
            this._w = width;
            this._h = height;
            Platform.runLater(new Runnable(){
                public void run(){
                    setPrefSize(_w, _h);
                    setHeight(_h);
                    setWidth(_w);
                    _browser.setPrefSize(_w, _h);                   
                    layoutChildren();
                }
            });
            if(DEBUG)
                System.out.println(" Browser.setSize(" + _w +", "+_h+")");
        }

        /**
         * Load a web url
         * 
         * @param url
         */
        public void loadURL(String url) {
            webEngine.load(url);
        }

        private Node createSpacer() {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            return spacer;
        }

        @Override
        protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            if(DEBUG){
                System.out.println("layout Children: " + w + ", "+h);
              //  Thread.dumpStack();
            }
            layoutInArea(_browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
        }

        @Override
        protected double computePrefWidth(double height) {
            return _w;
        }

        @Override
        protected double computePrefHeight(double width) {
            return _h;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runMe();
            }
        });
    }

    public static void runMe() {
        JFrame frame = new JFrame("WebBrowser");
        frame.setSize(750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        WebViewPanel webPanel = new WebViewPanel();
        mainPanel.add(webPanel, BorderLayout.CENTER);

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        webPanel.setURL("file:///C:/Users/alessandroob/AppData/Local/Temp/GLESgen/Hello.java.html");
        //webPanel.setContent("<HTML><h2>Hello, World !</h2></html>");
    }
}