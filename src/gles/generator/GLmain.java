package gles.generator;

import gles.generator.internal.FeatureNameEnum;
import gles.generator.internal.GLExtension;
import gles.generator.internal.GLFeature;
import gles.generator.internal.GLFeatureMap;
import gles.generator.internal.GLFunction;
import gles.generator.internal.GLcmdSet;
import gles.generator.internal.GLenum;
import gles.generator.internal.GLenumAll;
import gles.generator.internal.XMLParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.PatternSyntaxException;

/**
 * Class to produce Java & C++ bindings to EGL / OpenGL-ES. 
 * 
 * @author Alessandro Borges
 *
 */
public class GLmain {
    /**
     * API Strings enumeration
     */
    public static final String GL = "gl", GLES1 = "gles1", GLES2="gles2", EGL="egl";
    
    /**
     * masks for GLFeatures
     */
    private static String EGL_MASK = "egl";
    private static String GL_MASK = "GL_VERSION";
    private static String GLES1_MASK = "ES_CM";
    private static String GLES2_MASK = "ES_VERSION";
    
    /**
     * XML file names
     */
    public static final String GL_XML = "gl.xml", EGL_XML = "egl.xml";
    /**
     * API enumeration
     * @author Alessandro Borges
     *
     */
    public enum GL_API{
        GL, GLES1, GLES2, EGL
    }
    
    public GL_API api;    
    /**
     * the parser
     */
    private XMLParser parser;
    
    /**
     * where to store the GL/GLES/EGL commands
     */
    protected GLcmdSet cmdSet;
    /**
     * All Extensions
     */
    private Set<GLExtension> glesExt;
    
    /**
     * All enumerations
     */
    private GLenumAll enumAll;
    
    /**
     * All Features 
     */
    private GLFeatureMap featuresMap;
    
    private Set<GLFeature> features;
    
    private Set<GLFunction> allCoreFunctions = new TreeSet<GLFunction>();
    private Set<GLenum> allCoreEnums = new TreeSet<GLenum>();
    
    private String xmlFile = null;
    
    private int progress = 0;
    
    private String _path;
    private GL_API _api;
    private boolean goDone = false;
    
    /**
     * Process an EGL/GL in lazy mode.<br>
     * 
     * After instatiation, call {@link #go()} to start processing in lazy mode.
     * 
     * @see #go()
     * @param path - path do egl.xml or gl.xml
     * @param api - api to process
     * @param obj - dummi parameter
     */
    public GLmain(String path, GL_API api, Object obj){
        this._path = path;
        this._api = api;
    }
    
    /**
     * Start processing of EGL/GL XML immediately.
     *  
     * @param path - path do egl.xml or gl.xml
     * @param api - api to process
     */
    public GLmain(String path, GL_API api){
        go(path,api);
    }
    
    
    
    /**
     * Start processing in lazy mode.
     * Works only once and iff this instance was 
     * created with lazy {@link #GLmain(String, GL_API, Object)} constructor
     * 
     * @see #GLmain(String, GL_API)
     * 
     */
    public void go() {
        if (!goDone) {
            go(_path, _api);
        } else {
            System.err.println("XML already processed!");
        }
    }
    
    /**
     * start to XML processing
     * @param path
     * @param api
     */
    private void go(String path, GL_API api){
        if(goDone) {
            System.err.println("XML already processed!");
            return;
        }
        goDone = true;
        
        setProgress(0);
        this.api = api;
        parser = new XMLParser();
        String xml = (api == GL_API.EGL) ? EGL_XML  : GL_XML ;
        
        setProgress(5);
        parser.process(path + xml);
        
        setProgress(45);
        xmlFile = parser.getReadingXML();
        
        setProgress(50);
        cmdSet = parser.cmdSet;
        enumAll = parser.enumAll;  
        featuresMap = GLFeature.mapFeatures;
        
        boolean useEGL = api == GL_API.EGL;
        boolean useGL = api == GL_API.GL;
        boolean useGLES = api == GL_API.GLES1;
        boolean useGLES2 = api == GL_API.GLES2;
        
        glesExt = parser.filterExtensions(useGL, useGLES, useGLES2, useEGL);   
        setProgress(60);
        for (GLExtension glExt : glesExt) {
            glExt.setCommands(parser.cmdSet);
            glExt.setEnumerations(enumAll);
        } 
        
        features = getFeatures();
        setProgress(75);
        /*
         * set commands and enumerations to core
         */
        for(GLFeature feature : features){
            feature.setCommands(cmdSet);
            feature.setEnumerations(enumAll);
        }
        setProgress(80);
        
        /*
         * populate functions and enumerations
         */
        for(GLFeature feature : features){
            allCoreFunctions.addAll(feature.getAllFunctions());
            allCoreEnums.addAll(feature.getAllEnumerations());
        }
        
        setProgress(100);
        
    } // end Ctor
    
    /**
     * Get progress of XML processing.
     * @return value from 0 to 100
     */
    public synchronized int getProgress(){
        return progress;
    }
    
    /**
     * 
     * @param value
     */
    private synchronized  void  setProgress(int value){        
            progress = value;        
    }
    
    public static String getAPIString(GL_API mode){
        switch (mode) {
        case EGL: return GLmain.EGL;
        case GL: return GLmain.GL;
        case GLES1: return GLmain.GLES1;
        case GLES2: return GLmain.GLES2;
        default:
            return null;        
        }
    }
    
    /**
     * Get the XML used to process
     * @return
     */
    public String getXML(){
        return xmlFile;
    }
    
    /**
     * Get current API as String
     * @return
     */
    protected String getAPI(){
        return getAPIString(api);
    }
    
    /**
     * Get all Extensions as a Java method call 
     * @return java loading methods for GL/EGL Extensions
     */
    public String getExtensionsAsJava(){
        StringBuilder sb = new StringBuilder(8*1024);
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String extensionsAsJavaString = glExt.asJavaString();
            sb.append(extensionsAsJavaString);
        }        
        return sb.toString();
    }
    
    /**
     * Get C Extension Function Pointers 
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtPointers(){
        StringBuilder sb = new StringBuilder(8*1024);        
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String cFPNProc = glExt.asCfunctionPointers();
            sb.append(cFPNProc);
        }     
        return sb.toString();
    }
    
    /**
     * Get the source code for a PFN_FUNC loader, 
     * as eglGetProcAdress
     * @return
     */
    public String getCFuncProcAddress(){
        switch(this.api){
        case GL: return getCFuncAddressGL();
        default:
            return getCFuncProceAddressGLES();
        }
    }
    
    /**
     * Return a function for PFN_PROC  loading using wglGetProcAddress.     *
     * TODO extend it for glXGetProcAddress 
     * @return C code definition for loading function
     */
    protected String getCFuncAddressGL(){
        String loader =
        " // from https://www.opengl.org/wiki/Load_OpenGL_Functions \n"+
        " //void *GetAnyGLFuncAddress(const char *name)\n" +
        "  void *GetAnyGLFuncAddress(const char *name)\n" +
        "  {\n"+
        "    void *p = (void *)wglGetProcAddress(name);\n"+
        "    if(p == 0 ||\n"+
        "      (p == (void*)0x1) || (p == (void*)0x2) || (p == (void*)0x3) ||\n"+
        "      (p == (void*)-1) ) {\n"+        
        "      HMODULE module = LoadLibraryA(\"opengl32.dll\");"+
        "      p = (void *)GetProcAddress(module, name);\n"+
        "     }\n"+
        " \n"+
        "     return p;\n"+
        "   }\n";
        
        return loader;
    }
    
    /**
     * Return a macro for PFN_PROC  loading using eglGetProcAddress.
     * To be used on EGL / GLES api
     * @return definition 
     */
    protected String getCFuncProceAddressGLES(){
        return "  #define "+GLExtension.FUNC_LOADER+"(x) eglGetProcAddress(x)";
    }
    
    /**
     * Query Extensions by name.
     * Use "*" to get all functions
     * 
     * @param queryExt - CASE sentive substring or a regex to query extensions by name. 
     * @return list of extension names matching query
     */
    public List<String> queryExtensionNames(String queryExt){
        List<String> list = new ArrayList<String>();    
        if(queryExt==null)
            queryExt = "";    
        // mask for all
        queryExt = queryExt.equalsIgnoreCase("*") ? "" : queryExt;
        
        String regex = queryExt;
        boolean useRegEx = false;
        try{
            String test = "abcdefgh1234";
            test.matches(regex);
            useRegEx = true;
        }catch(PatternSyntaxException psx){
            System.err.println("invalid expression to query GLExtensions");
            psx.printStackTrace();
        }       
                
        String sub = queryExt.trim();
        for (GLExtension glExt : glesExt) {
            String name = glExt.name;
            name = name.trim();//.toLowerCase();
            boolean match = false;
            if(useRegEx){
                match = name.matches(regex);
            }            
            if (name.contains(sub) || name.equalsIgnoreCase(sub) || match) {
                list.add(glExt.name);
            }      
        }             
        return list;
    }
    
    /**
     * Get C Extension Function Pointers 
     * 
     * @param extensionName - extension name or regex to query function pointers
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtPointers(String extensionName) {
        if (extensionName == null) {
            extensionName = "";
        }
        
        List<String> list = queryExtensionNames(extensionName);
        StringBuilder sb = new StringBuilder(8 * 1024);
        // list extensions used
        
        sb.append("   // Declaration of FuncPointer vars \n");
        sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        for(String ext : list){
            sb.append("   // ").append(ext).append("\n");
        }
        
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (list.contains(name)) {
                glExt.setJavaAPIMode(getAPI());
                String cFPNProc = glExt.asCfunctionPointers();
                sb.append(cFPNProc);
            }
        }
        return sb.toString();
    }
    
    /**
     * Get C Extension Function Pointers 
     * 
     * @param extensionName - extension name or regex to query function pointers
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtLoaders(String extensionName) {
        if (extensionName == null) {
            extensionName = "";
        }
        
        List<String> list = queryExtensionNames(extensionName);
        List<String> listLoaderNames = new ArrayList<String>(list.size());
        StringBuilder sb = new StringBuilder(8 * 1024);
        // list extensions used
        sb.append("  // function loader\n")
        .append(getCFuncProcAddress())
        .append("\n\n");
        sb.append("   // Declaration of LOADERS for function pointers - PFN_PROC \n");
        sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        for(String ext : list){
            sb.append("   // ").append(ext).append("\n");
        }
        
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (list.contains(name)) {
                glExt.setJavaAPIMode(getAPI());
                String loader = glExt.asCfunctionLoaders();
                sb.append(loader);
                
                listLoaderNames.add(glExt.getFunctionLoaderName());
            }
        }
        
        // declare loadALL()
        sb.append("\n\n");
        sb.append("   // Declaration of loadALL(), to call all PFN_PROC pointers \n");
        sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        sb.append("  int loadALL(){\n");
        
        for (String string : listLoaderNames) {
            sb.append("\t ").append(string).append("();\n");
        }
        sb.append("       return 1;\n");
        sb.append("   } // loadALL()\n");
        
        
        return sb.toString();
    }

    /**
     * Get C Function Pointers 
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtLoaders() {
        StringBuilder sb = new StringBuilder(8 * 1024);
        
        sb.append("  // function loader\n")
        .append(getCFuncProcAddress())
        .append("\n\n");
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String loader = glExt.asCfunctionLoaders();
            sb.append(loader);
        }
        return sb.toString();
    }
    
    /**
     * Create a Java class to bind to GL/EGL extensions
     * @return java class source code
     */
    public String asJavaClassExt(){
        StringBuilder sb = new StringBuilder(10*1024);
        
        String extensions = asJavaExtension();
        
        String className = getAPI().toUpperCase() + "Ext";
        sb.append("/**\n * Place holder for license disclaimer\n **/\n")
          .append("\t package myPackage.glstuff;\n")
          .append("\t import android.opengl.*;\n")
          .append("\n\n")
          .append("   public class ").append(className).append("{\n")
          .append(extensions)
          .append("\n   }// end of class ").append(className)
          .append("\n");
        
        return sb.toString();
    }
    
    /**
     * Create a Java class to bind to GL/EGL Core Features
     * @return java class source code for Core features
     */
    public String asJavaClassCore(FeatureNameEnum feature, boolean backwards){
        StringBuilder sb = new StringBuilder(10*1024);
        
        String extensions = asJavaCore(feature, backwards);
        
        String className = getAPI().toUpperCase() + "Ext";
        sb.append("/**\n * Place holder for license disclaimer\n **/\n")
          .append("\t package myPackage.glstuff;\n")
          .append("\t import android.opengl.*;\n")
          .append("\n\n")
          .append("   public class ").append(className).append("{\n")
          .append(extensions)
          .append("\n   }// end of class ").append(className)
          .append("\n");
        
        return sb.toString();
    }
    
    /**
     * Return class source code for hadling EGL/GLES extension content, including 
     * enumerations and functions.
     *  <pre>
     *  Extension is search by name, and can be partial.<br>
     *  Example:
     *   // How to get Enumerations and Functions related to GL_OES_mapbuffer
     *   String extName = "GL_OES_mapbuffer";
     *   String amdExtensions = glMain.asJavaExtension(extName);
     *   
     *   // how get all AMD related extensions, in java code      
     *   String amdExtensions = glMain.asJavaExtension("AMD");
     *  
     *  </pre>
     * @param extension - extension name
     * @return String - source code for class wrapping an extension
     */
    public String asJavaClassExt(String extensionName){
        
        Set<GLExtension> glExts = new HashSet<GLExtension>();
        //get extensions java codes and the GLExtension
        String extensions = asJavaExtension(extensionName, glExts);
        String midClassName = extName2ClassName(extensionName);
        String className = getAPI().toUpperCase()+ midClassName + "Ext";
        
        StringBuilder sb = new StringBuilder(10*1024);
        sb.append("  /**\n   * Place holder for license disclaimer.\n   **/\n\n")
          .append("    package myPackage.glstuff;\n")
          .append("    import android.opengl.*;\n")
          .append("\n\n")
          .append("  /**\n   * <pre>\n");
        
          if(glExts.size()>1){
              sb.append("   * Main extension: ").append(extensionName).append("\n")
                .append("   * Included extensions: ").append("\n");
          }
          
          for(GLExtension glExt : glExts){
             createComments(glExt, sb);
          }
          sb.append("   * </pre>\n")
            .append("   **/\n")
            .append("   public class ").append(className).append("{\n")
            .append(extensions)
            .append("\n   }// end of class ").append(className)
            .append("\n");
        
        return sb.toString();
    }
    
    private static void createComments(GLExtension glExt, StringBuilder sb){
       // int enumCount = glExt.getEnumCount();
       // int funcCount =  glExt.getFunctionCount();
       // String name = glExt.name ;
        String resume = null;
        if(glExt.requires != null && glExt.requires.size() > 0){
            resume = glExt.requires.get(0).getResume();
        }else{            
            resume = glExt.name;
            if(glExt.supportedAPI != null){ 
                resume += ", API: ";
                resume += glExt.isEGL() ? "EGL" : "";
                resume += glExt.isGL() ? " GL" : "";
                resume += glExt.isGLES1() ? " GL-ES 1.x" : "";
                resume += glExt.isGLES2() ? " GL-ES 2.0+ " : "";
            }
        }
        String api = resume;
      //  String glAPI = glExt.supportedAPI;
        
        sb.append("   * Extension: ").append(api).append("\n");
    }
    
    /**
     * Return all extension content, including 
     * enumerations and functions.
     * 
     * @return String representation of all API extensions
     */
    public String asJavaExtension(){
        StringBuilder sb = new StringBuilder(5 * 1024);
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String extensionsAsJavaString = glExt.asJavaString();
            sb.append(extensionsAsJavaString);
        }        
        return sb.toString();
    }
    
    /**
     * Return Core enumeration and functions 
     * 
     * @param feature - the FeatureNameEnum to core 
     * @return String representation of all API extensions
     */
    public String asJavaCore(FeatureNameEnum featureName, boolean backwards) {
        StringBuilder sb = new StringBuilder(5 * 1024);
        List<FeatureNameEnum> featureNameList = null;

        if (backwards) {
            featureNameList = featureName.getBackwardsList();
        } else {
            featureNameList = new ArrayList<FeatureNameEnum>(1);
            featureNameList.add(featureName);
        }

        for (FeatureNameEnum fetName : featureNameList) {
            String name = fetName.getName();
            String number = fetName.getNumber();
            
            sb.append("  /**\n")
              .append("   * ").append(" Core Feature: ").append(name).append(" ").append(number).append("\n")
              .append("  */\n");

            GLFeature feature = getAPIFeature(fetName);
            feature.setJavaAPIMode(getAPI());
            String coreAsJavaString = feature.asJavaString();
            sb.append(coreAsJavaString);
        }
        return sb.toString();
    }
    
    /**
     * Return  extension content, including 
     * enumerations and functions.
     *  <pre>
     *  Extension is search by name, and can be partial.<br>
     *  Example:
     *   // How to get Enumerations and Functions related to GL_OES_mapbuffer
     *   String extName = "GL_OES_mapbuffer";
     *   String amdExtensions = glMain.asJavaExtension(extName);
     *   
     *   // how get all AMD related extensions, in java code      
     *   String amdExtensions = glMain.asJavaExtension("AMD");
     *  
     *  </pre>
     * @param extension - extension name
     * @return String - representation of requested extension
     */
    public String asJavaExtension(String extension) {
        return asJavaExtension(extension, null);
//        String extName = extension.trim().toLowerCase();
//
//        StringBuilder sb = new StringBuilder(2 * 1024);
//        // Extensions as JavaString
//        for (GLExtension glExt : glesExt) {
//            String glExtName = glExt.name.trim().toLowerCase();
//            if (glExtName.equalsIgnoreCase(extName) || glExtName.contains(extName)) {
//                glExt.setJavaAPIMode(getAPI());
//                String extensionsAsJavaString = glExt.asJavaString();
//                sb.append(extensionsAsJavaString);
//            }
//        }
//        return sb.toString();
    }
    
    
    /**
     * Return  extension content, including 
     * enumerations and functions.
     *  <pre>
     *  Extension is search by name, and can be partial.<br>
     *  Example:
     *   // How to get Enumerations and Functions related to GL_OES_mapbuffer
     *   String extName = "GL_OES_mapbuffer";
     *   String amdExtensions = glMain.asJavaExtension(extName);
     *   
     *   // how get all AMD related extensions, in java code      
     *   String amdExtensions = glMain.asJavaExtension("AMD");
     *  
     *  </pre>
     * @param extension - extension name
     * @return String - representation of requested extension
     */
    public String asJavaExtension(String extension, Collection<GLExtension> extList) {
        String extName = extension.trim().toLowerCase();

        StringBuilder sb = new StringBuilder(2 * 1024);
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            String glExtName = glExt.name.trim().toLowerCase();
            if (glExtName.equalsIgnoreCase(extName) || glExtName.contains(extName)) {
                glExt.setJavaAPIMode(getAPI());
                String extensionsAsJavaString = glExt.asJavaString();
                sb.append(extensionsAsJavaString);
                if(extList != null && !extList.contains(glExt)){
                    extList.add(glExt);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Generate a C++ class to load extension Functions 
     * @return C++ source code
     */
    public String asCclassExt(){
        String className = getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        if(api==GL_API.EGL){
            sb.append("#define  EGL_EGLEXT_PROTOTYPES \n");
            sb.append("#include <EGL/egl.h> \n");
            sb.append("#include <EGL/eglext.h> \n");
        } // GL_GLEXT_PROTOTYPES
        else if(api==GL_API.GLES2){
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GLES3/gl32.h> \n");
            sb.append("#include <GLES2/gl2ext.h> \n");
        } else if(api==GL_API.GLES1){
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GLES/gl.h> \n" );
            sb.append("#include <GLES/glext.h> \n");
        }
        sb.append("\n");
        sb.append("using namespace std;\n");
        sb.append("  class ").append(className).append("{\n");
        
        // variables
        sb.append("     public:\n");
        String fntProc = asCFunctionExtPointers();
        sb.append(fntProc);
        
        //methods
        sb.append("\n");
        sb.append("     public:\n");
        String loaders = asCFunctionExtLoaders();
        sb.append(loaders);
        
        sb.append("\n");
        sb.append("     public:\n");
        String allLoader = asCforAllLoadersExt();
        sb.append(allLoader);
        
        sb.append("\n");
        sb.append(" } // end of class");
        return sb.toString();
    }
    
    
    /**
     * Generate a C++ class to load extension Functions 
     * @param extension to load
     * @return C++ source code
     */
    public String asCclassExt(String extension){
        String className = getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        if(api==GL_API.EGL){
            sb.append("#define  EGL_EGLEXT_PROTOTYPES \n");
            sb.append("#include <EGL/egl.h> \n");
            sb.append("#include <EGL/eglext.h> \n");
        } // GL_GLEXT_PROTOTYPES
        else if(api==GL_API.GLES2){
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GLES3/gl32.h> \n");
            sb.append("#include <GLES2/gl2ext.h> \n");
        } else if(api==GL_API.GLES1){
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GLES/gl.h> \n" );
            sb.append("#include <GLES/glext.h> \n");
        }
        sb.append("\n");
        sb.append("using namespace std;\n");
        sb.append("  class ").append(className).append("{\n");
        
        // variables
        sb.append("     public:\n");
        String fntProc = asCFunctionExtPointers(extension);
        sb.append(fntProc);
        
        //methods
        sb.append("\n");
        sb.append("     public:\n");
        String loaders = asCFunctionExtLoaders(extension);
        sb.append(loaders);
        
//        sb.append("\n");
//        sb.append("     public:\n");
//        String allLoader = asCforAllLoadersExt(extension);
//        sb.append(allLoader);
        
        sb.append("\n");
        sb.append(" } // end of class");
        return sb.toString();
    }
    
    
    /**
     * Remove "_" on extensions names
     * @param extName
     * @return
     */
    private static String extName2ClassName(String extName) {
        StringBuffer sb = new StringBuffer(96);
        char under = '_';

        for (int i = 0; i < extName.length();) {
            char ch = extName.charAt(i++);
            if (ch == under) {                
                if(i<extName.length())
                ch = extName.charAt(i++);
                ch = Character.toUpperCase(ch);
                sb.append(ch);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    
    /**
     * Get all Features names
     * @return
     */
    public Set<String> getAllFeatureNames(){
        Set<String> names = featuresMap.keySet();        
        Set<String> result = new TreeSet<String>(names);        
        return result;
    }
    
    
    /**
     * Get all features in this parsed API.
     * @return Set of Features.
     */
     public Set<GLFeature> getFeatures(){
         if(this.features != null){
             return features;
         }
         
         Set<GLFeature> featuresSet = new TreeSet<GLFeature>();         
         String mask = GLES2_MASK;
         switch (api) {
         case EGL:   mask = EGL_MASK;    break;
         case GLES1: mask = GLES1_MASK;  break;
         case GLES2: mask = GLES2_MASK;  break;
         case GL:    mask = GL_MASK;     break;        
        }         
         mask = mask.toLowerCase();
         Set<String> allFeatureNames = getAllFeatureNames(); 
         for (String featureName : allFeatureNames ) {
            String name = featureName.toLowerCase();            
            if (name.contains(mask)) {
                GLFeature feat = featuresMap.get(featureName);
                featuresSet.add(feat);
            }
        }
        return featuresSet;
     }
     
     /**
      * Get Core Functions
      * @return list of GLFunctions of Core API
      */
     public List<GLFunction> getCoreFunctions(FeatureNameEnum featureName){
         GLFeature feature = getAPIFeature(featureName);
         
         return feature.getAllFunctions(); //.getFunctions();
     }
     
     /**
      * Get GLFeature from this parsed GL/EGL API
      * @param featureName - one of EGL/GLES/GL
      * @return Requested feature
      */
     public GLFeature getAPIFeature(FeatureNameEnum featureName){
         String name = featureName.getName().toLowerCase();
         for(GLFeature fe : features){
             String feName = fe.name.trim().toLowerCase();
             if(name.equalsIgnoreCase(feName)){
                 return fe;
             }
         }         
         return null;
     }
     
     /**
      * Get Core Enumerations for this API.<br>
      * 
      * Please note this methods DOESN'T include backwards enumerations
      * 
      * @param featureName - name of feature
      * @return list of Core Enumerations
      */
     public List<GLenum> getCoreExtrictEnum(FeatureNameEnum featureName){
         GLFeature feature = getAPIFeature(featureName);
         
         return feature.getAllEnumerations();//.getEnumerations();
     }
     
     /**
      * Get Core Enumerations for this parsed GL/GLES/EGL,
      * including all backwards enumerations
      * 
      * @param featureName - one of valid Features of this API
      * @return list of Core Enumerations
      */
    public List<GLenum> getCoreBackwardsEnum(FeatureNameEnum featureName) {
        List<FeatureNameEnum> backwards = featureName.getBackwardsList();
        List<GLenum> list = new ArrayList<GLenum>();

        for (FeatureNameEnum fet : backwards) {
            GLFeature feature = GLFeature.getGLFeature(fet);
            list.addAll(feature.getAllEnumerations());
        }
        return list;
    }
     
     /**
      * Get all functions available
      * @return
      */
     public Set<GLFunction> getAllFunctions(){
         return this.allCoreFunctions;
     }
     
     /**
      * Get all Enumerations
      * @return
      */
     public Set<GLenum> getAllEnum(){
         return this.allCoreEnums;
     }
     
     /**
      * Get a GLFeature by name
      *  
      * @param name GLFeature name
      * @return requested GLFeature
      */
      public GLFeature getFeature(String name){
          return featuresMap.get(name);
      }
      
          
    
    /**
     * 
     * @param apiName
     * @return
     */
    public String asCforAllLoadersExt() {
        String apiName = getAPI();       
        StringBuilder sb = new StringBuilder(5 * 1024);        
        String funcAllLoaderName = "loadAllExtensions" + apiName.toUpperCase();
        // get all extensions names
        
        sb.append("\n");
        sb.append("  /**\n").append("   * Call all extension loaders:\n");
        sb.append("   **/\n");
        List<String> loaderNames = new ArrayList<String>(64);
        
        // get all loaderNames
        for (GLExtension ext : glesExt) {
            loaderNames = ext.asCextFuncLoaderNames(loaderNames);
        }
        
        sb.append("    int ").append(funcAllLoaderName).append("(){\n");
       
        for(String loader : loaderNames){
            sb.append("     ").append(loader).append(";\n");
        }
        
        sb.append("\n     return 1;\n");
        sb.append("   } // end of ").append(funcAllLoaderName).append("\n");
        return sb.toString();
    }
    
    
    
//    /**
//     * 
//     * @param apiName
//     * @return
//     */
//    public String asCforAllLoadersExt(String extensions) {
//        String apiName = getAPI();       
//        StringBuilder sb = new StringBuilder(5 * 1024);        
//        String funcAllLoaderName = "loadAllExtensions" + apiName.toUpperCase();
//        // get all extensions names
//        
//        sb.append("\n");
//        sb.append("  /**\n").append("   * Call all extension loaders:\n");
//        sb.append("   **/\n");
//        List<String> loaderNames = new ArrayList<String>(64);
//        
//        // get all loaderNames
//        for (GLExtension ext : glesExt) {
//            loaderNames = ext.asCextFuncLoaderNames(loaderNames);
//        }
//        
//        sb.append("    int ").append(funcAllLoaderName).append("(){\n");
//       
//        for(String loader : loaderNames){
//            sb.append("     ").append(loader).append(";\n");
//        }
//        
//        sb.append("\n     return 1;\n");
//        sb.append("   } // end of ").append(funcAllLoaderName).append("\n");
//        return sb.toString();
//    }
//    
    /**
     * Main tests
     * @param args
     */
    public static void main(String[] args) {
        GLmain gl = new GLmain("", GL_API.EGL);

        boolean testFunctionPointers = true;
        boolean testQueryExt = false;
        boolean testFunctionLoaders = true;
        boolean testAllLoaders = false;
        boolean testCclass = true;
        boolean testFeaturesNames = false;
        boolean testCore = false;
        boolean testJavaClass = false;
        boolean testSingleClassExt = true;
        boolean testAsJavaCore = false;
        
        if(testQueryExt){                       
            String regex = "Q";
            
            print("regex: " + regex);
            print(gl.queryExtensionNames(regex));
            
            regex = "AMD";
            print("regex: " + regex);
            print(gl.queryExtensionNames(regex));
            
            regex = "NV";
            print("regex: " + regex);
            print(gl.queryExtensionNames(regex));
            
            regex = "KHR";
            print("regex: " + regex);
            print(gl.queryExtensionNames(regex));
        }
        String ext = "EGL_EXT_device_enumeration";
        if (testFunctionPointers) {
            
            String pointers = gl.asCFunctionExtPointers(ext);
            System.out.println(pointers);
        }

        if (testFunctionLoaders) {
            String pfnLoaders = gl.asCFunctionExtLoaders(ext);
            System.err.println("\n\n// asCFunctionLoaders\n " + pfnLoaders);
        }

        if (testAllLoaders) {
            String allLoaders = gl.asCforAllLoadersExt();
            System.out.println("//all loaders: \n" + allLoaders);
        }

        if (testCclass) {
            String cClass = gl.asCclassExt(ext);
            System.out.println("//cClass: \n" + cClass);
        }

        if (testFeaturesNames) {
            Set<String> featureNames = gl.getAllFeatureNames();
            System.out.println("GL Features avail:");
            for (String name : featureNames) {
                System.out.println(name);
            }
            Set<GLFeature> features = gl.getFeatures();
            System.out.println("\n\nFeatures of this api: ");
            for (GLFeature glFeature : features) {
                System.out.println("feature: " + glFeature);
            }

        }

        if (testCore) {
            System.out.println("Test Core Functions:");
            for (GLFunction func : gl.getCoreFunctions(FeatureNameEnum.EGL_VERSION_1_2)) {
                System.out.println(func);
            }

            System.out.println("Test Core Functions:");
            for (GLenum enume : gl.getCoreBackwardsEnum(FeatureNameEnum.EGL_VERSION_1_2)) {
                System.out.println(enume);
            }
        }
        
        if(testJavaClass){
            System.out.println("//Test Java Class Creation");
            String javaClass = gl.asJavaClassExt();
            System.out.println(javaClass);
        }
        
        if(testSingleClassExt){
            System.out.println("//Single Extension Java Class Creation");
            String extName = ext;//"KHR_create_context";
            String javaClass = gl.asJavaClassExt(extName);
            System.out.println(javaClass);
        }
        
        if(testAsJavaCore){
            System.out.println("//Core Java Class Creation");            
            String javaCore = gl.asJavaCore(FeatureNameEnum.GL_ES_VERSION_2_0, false);
            System.out.println(javaCore);
        }
    
    

    }
    
    /**
     * Print a collection
     * @param list
     */
    private static void print(Collection<?> list){
        int i = 1;
        for (Object object : list) {
            System.err.println(" #"+ (i++) +" - "+ object.toString());
        }
    }
    
    private static void print(Object obj){
        System.err.println(obj );
    }

}
