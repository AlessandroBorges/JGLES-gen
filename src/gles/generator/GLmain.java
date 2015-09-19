package gles.generator;

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
    public static final String GL = "gl", GLES1 = "gles1", GLES2="gles2",
                               GLES3="gles3",GLES31="gles31",GLES32="gles32",
                               EGL="egl";
    
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
        GL, GLES1, GLES2, GLES3, GLES31, GLES32, EGL;
        
        public String toString2(){
            return super.toString().toLowerCase();
        }
    }
    
    /**
     * Current API 
     */
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
     * After instantiation, call {@link #go()} to start processing in lazy mode.
     * 
     * @see #go()
     * @param path - path do egl.xml or gl.xml
     * @param api - api to process
     * @param obj - dummy parameter
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
     * Simple Ctor
     * @param api - api to parse
     */
    public GLmain(GL_API api){
        this("",api);
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
     * @param path - path to file
     * @param api - GL_API to load
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
        boolean useGLES2 = (api == GL_API.GLES2) 
                || (api == GL_API.GLES3) 
                || (api == GL_API.GLES31) 
                || (api == GL_API.GLES32) ;
        
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
        case GLES3: return GLmain.GLES3;
        case GLES31: return GLmain.GLES31;
        case GLES32: return GLmain.GLES32;
        default:
            return null;        
        }
    }
    
    /**
     * String representation of current GL_API.
     * 
     * @return api string
     * 
     * @see GL_API
     * @see api
     */
    public String getAPIString(){
        return api.toString();
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
     * @return current api as string
     */
    protected String getAPI(){
        return getAPIString(api);
    }
    
    /**
     * TODO improve to replace {@link #getAPI()}
     * @return
     */
    protected GL_API getAPI_new(){
        return api;
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
     * @param asStatic - true to create static function pointers
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtPointers(boolean asStatic){
        StringBuilder sb = new StringBuilder(8*1024);        
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String cFPNProc = glExt.asCfunctionPointers(asStatic);
            sb.append(cFPNProc);
        }     
        return sb.toString();
    }
    
    /**
     * Get the source code for a PFN_FUNC loader, 
     * as eglGetProcAdress, wglGetProcAddress, glxGetProcAddress
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
     * List all extension names available
     * @return list of extensions
     */
    public List<String> getAllExtensionNames(){
        return queryExtensionNames("");
    }
    
    /**
     * Query Extensions by name.<br>
     * Use "*" to get all functions.<br>
     * Partial names are also accepted, as "AMD", "IMG","NV", "EXT", "QCOM", etc.
     * 
     * @param queryExt - CASE sensitive substring or a regex to query extensions by name. 
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
            String test = "abcdefgl1234";
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
        
        checkExtensionGroup(list);
        return list;
    }
    
    /**
     * Get C Extension Function Pointers 
     * 
     * @param extensionName - extension name or regex to query function pointers
     * @param asStatic - true to create static function pointers
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtPointers(String extensionName, boolean asStatic) {
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
                String cFPNProc = glExt.asCfunctionPointers(asStatic);
                sb.append(cFPNProc);
            }
        }
        return sb.toString();
    }
    
    
    /**
     * Get C Extension Function Pointers 
     * 
     * @param extensionNames - List of <b>exactly</b>  extension name function pointers
     * @param asStatic - true to create static functions
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtPointers(List<String> extensionNames, boolean asStatic) {                
        List<String> list = extensionNames;
        StringBuilder sb = new StringBuilder(8 * 1024);
        // list extensions used
        
        sb.append("   // Declaration of FuncPointer vars \n");
       // sb.append("   // Extension key \"").append(extensionName).append("\" \n");
//        for(String ext : list){
//            sb.append("   // ").append(ext).append("\n");
//        }
        
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (containsIgnoreCase(list, name)) {
                glExt.setJavaAPIMode(getAPI());
                String cFPNProc = glExt.asCfunctionPointers(asStatic);
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
    public String asCFunctionExtLoaders(String extensionName, boolean asStatic) {
        if (extensionName == null) {
            extensionName = "";
        }
        
        List<String> list = queryExtensionNames(extensionName);
        List<String> listLoaderNames = new ArrayList<String>(list.size());
        StringBuilder sb = new StringBuilder(8 * 1024);
        // list extensions used
        
        sb
       //.append("  // function loader\n")
        .append(getCFuncProcAddress())
        .append("\n\n");
        sb.append("   // Declaration of LOADERS for function pointers - PFN_PROC \n");
        sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        for(String ext : list){
            sb.append("   // ").append(ext).append("\n");
        }
        
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (containsIgnoreCase(list,name)) {
                glExt.setJavaAPIMode(getAPI());
                String loader = glExt.asCfunctionLoaders(asStatic);
                sb.append(loader);
                
                listLoaderNames.add(glExt.getFunctionLoaderName());
            }
        }
        
        // declare loadALL()
        sb.append("\n\n");
        sb.append("   // Declaration of loadALL(), to call all PFN_PROC pointers \n");
        sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        if(asStatic){
            sb.append(" static");
        }
        sb.append(" int loadALL(){\n");
        
        for (String string : listLoaderNames) {
            sb.append("\t ").append(string).append("();\n");
        }
        sb.append("       return 1;\n");
        sb.append("   } // loadALL()\n");
        
        
        return sb.toString();
    }
    
    
    /**
     * <b>GL_ANDROID_extension_pack_es31a</b> or <b>ANDROID_extension_pack_es31a</b> is 
     *  OpenGL ES extensions available on OpenGL ES 3.1 and forward.<br>
     *  
     *  It is a umbrella extension, because it doesn't define any enumeration or function,
     *  but if available, it means the following extensions are also supported:<br>
     *  <pre>
     *  KHR_debug
     *  KHR_texture_compression_astc_ldr
     *  KHR_blend_equation_advanced
     *  OES_sample_shading
     *  OES_sample_variables
     *  OES_shader_image_atomic
     *  OES_shader_multisample_interpolation
     *  OES_texture_stencil8
     *  OES_texture_storage_multisample_2d_array
     *  EXT_copy_image
     *  EXT_draw_buffers_indexed
     *  EXT_geometry_shader
     *  EXT_gpu_shader5
     *  EXT_primitive_bounding_box
     *  EXT_shader_io_blocks
     *  EXT_tessellation_shader
     *  EXT_texture_border_clamp
     *  EXT_texture_buffer
     *  EXT_texture_cube_map_array
     *  EXT_texture_sRGB_decode
     * </pre>
     * 
     * This method check if the extensionNames collection contains ANDROID_EXTENSION_PACK, or
     * &quot;aep&quot; string.<br>
     * If AEP is present, all 20 required extensions are loaded into
     * extensionNames.
     * 
     * @param extensionNames
     *            - Collection of extensions names to load. If it contains
     *            ANDROID_EXTENSION_PACK or &quot;aep&quot; , then all 20
     *            required AEP extensions are loaded:
     * 
     * @return true if ANDROID_EXTENSION_PACK or &quot;aep&quot; was requested.
     *
     * @see GLExtension#checkRequestForGroupExtensions(Collection)
     * 
     */
    public Collection<String>  checkExtensionGroup(Collection<String> extensionNames){
        return GLExtension.checkRequestForGroupExtensions(extensionNames);
    }
    
    /**
     *  * This method check if the extensionNames collection contains ANDROID_EXTENSION_PACK, or
     * &quot;aep&quot; string.<br>
     * If AEP is present, a list with all 20 required extensions are returned
     * 
     * @param extension - extension name
     * @return a List of extension names.
     * 
     * @see GLmain#checkExtensionGroup(Collection)
     */
    public Collection<String> checkEAP(String extension){
        List<String> extensionNames = new ArrayList<String>();
        extensionNames.add(extension);
        checkExtensionGroup(extensionNames);
        return extensionNames;        
    }
    
    /**
     * Get C Extension Function Pointers 
     * TODO retrofit
     * @param extensionName - List of <b>exactly</b> extension names query function pointers
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtLoaders(List<String> extensionNames, boolean asStatic) {
       
        checkExtensionGroup(extensionNames);
        List<String> list = extensionNames;
        List<String> listLoaderNames = new ArrayList<String>(list.size());
        StringBuilder sb = new StringBuilder(8 * 1024);
        // list extensions used
        sb.append("  // function loader\n")
        .append(getCFuncProcAddress())
        .append("\n\n");
        sb.append("   // Declaration of LOADERS for function pointers - PFN_PROC \n");
       // sb.append("   // Extension key \"").append(extensionName).append("\" \n");
//        for(String ext : list){
//            sb.append("   // ").append(ext).append("\n");
//        }
        
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (containsIgnoreCase(list,name)) {
                glExt.setJavaAPIMode(getAPI());
                String loader = glExt.asCfunctionLoaders(asStatic);
                //skip extensions with no functions
                if(loader.trim().length() > 5){
                    sb.append(loader);
                    listLoaderNames.add(glExt.getFunctionLoaderName());
                }
            }
        }
        
        // declare loadALL()
        sb.append("\n\n");
        sb.append("  // Declaration of loadALL(), to call all PFN_PROC pointers \n");
       // sb.append("   // Extension key \"").append(extensionName).append("\" \n");
        if(asStatic){
            sb.append(" static");
        }
        sb.append(" int loadALL(){\n");
        
        for (String string : listLoaderNames) {
            sb.append("\t ").append(string).append("();\n");
        }
        sb.append("       return 1;\n");
        sb.append("   } \n\n");
        
        
        return sb.toString();
    }

    /**
     * Get C Function Pointers 
     * @return java loading methods for GL/EGL Extensions
     */
    public String asCFunctionExtLoaders(boolean asStatic) {
        StringBuilder sb = new StringBuilder(8 * 1024);
        
        sb.append("  // function loader\n")
        .append(getCFuncProcAddress())
        .append("\n\n");
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(getAPI());
            String loader = glExt.asCfunctionLoaders(asStatic);
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
        
        String extensions = asJavaExtensionAll();
        String jniHeader = asJavaJnigenHeaderExt("");
        
        String className = getAPI().toUpperCase() + "Ext";
        sb.append("/**\n * Place holder for license disclaimer\n **/\n")
          .append("\t package myPackage.glstuff;\n")
          .append("\t import android.opengl.*;\n")
          .append("\n\n")
          .append("   public class ").append(className).append("{\n")
          .append(jniHeader)
          .append(extensions)
          .append("\n   }// end of class ").append(className)
          .append("\n");
        
        return sb.toString();
    }
    
    /**
     * Create a Java class to bind to GL/EGL Core Features
     * @return java class source code for Core features
     */
    public String asJavaClassCore(GLFeatureEnum feature, boolean backwards){
        StringBuilder sb = new StringBuilder(10*1024);
        
        String core = asJavaCore(feature, backwards);
        String jniHeader = asJavaJnigenHeaderCore(feature);
        String backwardsStr = backwards ? " with ALL backwards API functions. ":" without backwards API functions.";
        String className = feature.getApi().toUpperCase() + "Core";
        sb.append("  /**\n   * Place holder for license disclaimer.\n   */\n")
          .append("    package gles.generated;\n\n")
          .append("    import android.opengl.*;\n\n")
          
          .append("  /**\n")
          .append("   * Java class for Core ").append(feature.getApi()).append("\n")
          .append("   * Featuring: ").append(feature.toString()).append(backwardsStr).append("\n")
          .append("   */\n")
          
          .append("    public class ").append(className).append("{\n")
          .append(jniHeader)
          .append(core)
          .append("\n    }// end of class ").append(className)
          .append("\n");
        
        return sb.toString();
    }
    
    /**
     * Return class source code for handling EGL/GLES extension content, including 
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
     * @param classNameSugestion - proposed name for this class
     * @return String - source code for class wrapping an extension
     */
    public String asJavaClassExt(String extensionName, String classNameSugestion){
        
        List<String> list = new ArrayList<String>();
        list.add(extensionName);
        
        return asJavaClassExt(list, classNameSugestion);
        
//        Set<GLExtension> glExts = new HashSet<GLExtension>();
//        //get extensions java codes and the GLExtension
//        String extensions = asJavaExtension(extensionName, glExts);
//        String midClassName = extName2ClassName(extensionName);
//        String className = getAPI().toUpperCase()+ midClassName + "Ext";
//        className = classNameSugestion != null ? classNameSugestion : className;
//        
//        String jniHeader = asJavaJnigenHeader(extensionName); 
//        
//        StringBuilder sb = new StringBuilder(10*1024);
//        sb.append("  /**\n   * Place holder for license disclaimer.\n   **/\n\n")
//          .append("    package gles.generated;\n\n")
//          .append("    import android.opengl.*;\n")
//          .append("\n\n")
//          .append("  /**\n   * <pre>\n");
//        
//          if(glExts.size()>1){
//              sb.append("   * Main extension: ").append(extensionName).append("\n")
//                .append("   * Included extensions: ").append("\n");
//          }
//          
//          for(GLExtension glExt : glExts){
//             createComments(glExt, sb);
//          }
//          sb.append("   * </pre>\n")
//            .append("   **/\n")
//            .append("   public class ").append(className).append("{\n")
//            .append(jniHeader)
//            .append(extensions)
//            .append("\n   }// end of class ").append(className)
//            .append("\n");
//        
//        return sb.toString();
    }
    
    /**
     * Return class source code for handling EGL/GLES extension content, including 
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
    public String asJavaClassExt(List<String> extensionNames, String classNameSugestion){ 
        
        checkExtensionGroup(extensionNames);
        
        Set<GLExtension> glExts = new HashSet<GLExtension>();
        //get extensions java codes and the GLExtension
        String extensions = asJavaExtension(extensionNames, glExts);
        
        String midClassName = "Selected"; // extName2ClassName(extensionName);
        String className = getAPI().toUpperCase()+ midClassName + "Ext";
        
        className = classNameSugestion != null ? classNameSugestion : className;
        
        String jniHeader = asJavaJnigenHeaderExt(extensionNames);
        
        StringBuilder sb = new StringBuilder(10*1024);
        
        sb.append("  /**\n   * Place holder for license disclaimer.\n   **/\n\n")
          .append("    package gles.generated;\n\n")
          .append("    import android.opengl.*;\n")
          .append("\n\n")
          .append("  /** <pre>\n   * \n");
        
          if(glExts.size()>1){
              sb.append("   * Main extension: ").append("selection").append("<br>\n")
                .append("   * Included extensions: ").append("<br>\n");
          }
          
          for(GLExtension glExt : glExts){
             createComments(glExt, sb);
          }
          sb.append("   *</pre> \n")
            .append("   **/\n")
            .append("   public class ").append(className).append("{\n")
            .append(jniHeader)
            .append(extensions)
            .append("\n   }// end of class ").append(className)
            .append("\n");
        
        return sb.toString();
    }
    
    /**
     * Create some basic info about API.<br>
     * Example:
     *  "Extension EGL_EXTENSION_NAME, API: EGL "
     * @param glExt
     * @param sb
     */
    private static void createComments(GLExtension glExt, StringBuilder sb){
       
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
        
        sb.append("   * Extension: ").append(api).append(" <br>\n");
    }
    
    /**
     * Get corrent include for GLES2.0, up to GLES32
     * @param api - one of GLES2 up to GLES32.
     * @return a cpp include line
     */
    private String getGL2IncludeLine(GL_API api){        
        if(!isGLES2Type(api)){
            throw new UnsupportedOperationException("Must be a GL_API.GLES2 or higher!");
        }
        String gles2String = null;
        // i am really restrict here
        switch (api) {
        case GLES2:  gles2String  =  "    #include <GLES2/gl2.h> \n"; break;
        case GLES3:  gles2String  =  "    #include <GLES3/gl3.h> \n"; break;
        case GLES31: gles2String  =  "    #include <GLES3/gl31.h> \n"; break;
        case GLES32: gles2String  =  "    #include <GLES3/gl32.h> \n"; break;                
        default:     gles2String  =  "    #include <GLES3/gl32.h> \n"; break;
        }
        return gles2String;
    }
    
    /**
     * Generate code for libGDX's jnigen header.<br>
     * It's a comment like  / *JNI, but  used to define includes, statics, and functions.
     * 
     * @see #asJavaJnigenHeaderCore()
     * @see #asJavaJnigenHeaderExt(List)
     * 
     * @param extensionName - name can be full, partial or a regex. Use "none" to skip extension loading.
     * @return java source code with jnigen header
     */
    public String asJavaJnigenHeaderExt(String extensionName){
        if (extensionName == null) {
            extensionName = "";
        }  
        
        if(extensionName.equalsIgnoreCase("none")){            
            return asJavaJnigenHeaderCore(GLFeatureEnum.GL_ES_VERSION_2_0);
        }
        
        List<String> list = new ArrayList<String>();
        list.add(extensionName);
        
        return asJavaJnigenHeaderExt(list);
    }
    
    
    /**
     * Generate code for libGDX's jnigen header.<br>
     * It's a comment like  / *JNI, but  used to define includes, statics, and functions.
     * 
     * 
     * @return
     */
    public String asJavaJnigenHeaderCore(GLFeatureEnum feature){
                
        StringBuilder sb = new StringBuilder(2*1024);
        
        //sb.append("    // jnigen header\n");
        sb.append("    //@OFF \n");
        sb.append("    /*JNI \n");
        
        // declare EGL/GLES imports       
      //  sb.append("  // include section\n");
        if(api != GL_API.GL){
            sb.append("    // EGL includes\n");
            sb.append("    #define EGL_EGLEXT_PROTOTYPES 1\n");
            sb.append("    #include <EGL/egl.h>\n");
            sb.append("    #include <EGL/eglext.h>\n\n");
            
            if(api == GL_API.GLES1){
                sb.append("    // GLES 1.x  api\n");
                sb.append("    #define GL_GLEXT_PROTOTYPES 1 \n");
                sb.append("    #include <GLES/egl.h>\n");
                sb.append("    #include <GLES/gl.h>\n");
                sb.append("    #include <GLES/glext.h>\n\n");                
            }else if(isGLES2Type(api)){                               
                sb.append("    // GLES 2.0+ api\n");
                sb.append("    #define  GL_GLEXT_PROTOTYPES 1 \n");
                
                String gles2String = getGL2IncludeLine(glFeatureToAPI(feature)); 
                sb.append(gles2String);
                sb.append("    #include <GLES2/gl2ext.h> \n\n");
            }
        }       
        sb.append("    */ // jnigen header \n\n");
        
        sb.append("  static protected native void init();/* \n");
        sb.append("    //some native init\n\n");
        sb.append("  */\n\n");
        
        sb.append("  /** loading native stuff */\n");
        sb.append("  static{\n");
        sb.append("    init();\n");
        sb.append("  }\n\n");
        
        return sb.toString();
    }
    
    /**
     * Convert GLFeatureEnum to GL_API
     * @param feature 
     * @return
     */
    private GL_API glFeatureToAPI(GLFeatureEnum feature){
        String apiStr = feature.getApi();
        switch (apiStr) {
        case "egl": return GL_API.EGL;
        case "gl": return GL_API.GL;
        case "gles1": return GL_API.GLES1;
        case "gles2": return GL_API.GLES2;
        case "gles3": return GL_API.GLES3;
        case "gles31": return GL_API.GLES31;
        case "gles33": return GL_API.GLES32;
        default:
            return GL_API.GLES2;
        }
    }
    
    /**
     * Generate code for libGDX's jnigen header.<br>
     * It's a comment like  / *JNI, but  used to define includes, statics, and functions.
     * 
     * @see #asCFunctionExtPointers(List, boolean)
     * @see #asCFunctionExtLoaders(List, boolean)
     * 
     * @param extensionNames - List of <b>exactly</b> extension names to load.
     * @return source code for Java classes
     */
    public String asJavaJnigenHeaderExt(List<String> extensionNames){
          
        checkExtensionGroup(extensionNames);       
        StringBuilder sb = new StringBuilder(2*1024);
        
       // sb.append("    // jnigen header\n");
        sb.append("    //@OFF \n");
        sb.append("    /*JNI \n");
        
        // declare EGL/GLES imports       
       // sb.append("  // include section\n");
        if(api != GL_API.GL){
            sb.append("    // EGL includes\n");
            sb.append("    #define EGL_EGLEXT_PROTOTYPES 1\n");
            sb.append("    #include <EGL/egl.h>\n");
            sb.append("    #include <EGL/eglext.h>\n\n");
            
            if(api == GL_API.GLES1){
                sb.append("  // GLES 1.x  api\n");
                sb.append("    #define GL_GLEXT_PROTOTYPES 1 \n");
                sb.append("    #include <GLES/egl.h>\n");
                sb.append("    #include <GLES/gl.h>\n");
                sb.append("    #include <GLES/glext.h>\n\n");                
            }else if(isGLES2Type(api)){                               
                sb.append("  // GLES 2.0+ / GLES 3.x api\n");
                sb.append("    #define  GL_GLEXT_PROTOTYPES 1 \n");
                
                String gles2String = getGL2IncludeLine(api); 
                sb.append(gles2String);
                sb.append("    #include <GLES2/gl2ext.h> \n\n");
            }
        }
        
         // declare FUNC Pointers       
        sb.append("  // function pointers section \n");
        String funcPtr = asCFunctionExtPointers(extensionNames, true);        
        sb.append(funcPtr);
        
        
        sb.append("\n  // extension loaders\n");
        String funcLoaders = asCFunctionExtLoaders(extensionNames, true);        
        sb.append(funcLoaders);
        
        sb.append("");
        sb.append("");
        
        sb.append("  */\n  // end of JNI header\n\n");
        
        sb.append("  static protected native void init(); /* \n");
        sb.append("    loadAll();\n");
        sb.append("  */\n\n");
        
        sb.append("  /** loading native stuff */\n");
        sb.append("  static{\n");
        sb.append("    init();\n");
        sb.append("  }\n\n");
        
        return sb.toString();
    }
    
    /**
     * Return all extension content, including 
     * enumerations and functions.
     * 
     * @return String representation of all API extensions
     */
    public String asJavaExtensionAll(){
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
    public String asJavaCore(GLFeatureEnum featureName, boolean backwards) {
        StringBuilder sb = new StringBuilder(5 * 1024);
        List<GLFeatureEnum> featureNameList = null;

        if (backwards) {
            featureNameList = featureName.getBackwardsList();
        } else {
            featureNameList = new ArrayList<GLFeatureEnum>(1);
            featureNameList.add(featureName);
        }

        for (GLFeatureEnum fetName : featureNameList) {
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
        Collection<String> eapList = new ArrayList<String>();
        eapList.add(extName);
        checkExtensionGroup(eapList);

        StringBuilder sb = new StringBuilder(2 * 1024);
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            String name = glExt.name.trim();
            if (containsIgnoreCase(eapList, name)) {
                glExt.setJavaAPIMode(getAPI());
                String extensionsAsJavaString = glExt.asJavaString();
                sb.append(extensionsAsJavaString);
                if (extList != null && !extList.contains(glExt)) {
                    extList.add(glExt);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Check if a List of strings contains a partial name.
     * 
     * @param list - list of names
     * @param partialName - partial name to look for.
     * @return true if list contains partial name
     */
    private boolean containsIgnoreCase(Collection<String> list, String partialName){
        partialName = partialName.trim().toLowerCase();
        for (String fullName : list) {
            fullName = fullName.trim().toLowerCase();
            if(fullName.contains(partialName) || partialName.contains(fullName))
                return true;
        }        
        return false;
    }
    
    /**
     * Creates a Java Extension class from a list of extension names
     * @param extensionNames - [in] list of extensions names
     * @param extList - [output] a null or empty list of GLExtensions used 
     * 
     * @return String with generated source code
     */
    public String asJavaExtension(List<String> extensionNames, Collection<GLExtension> extList) {
        //String extName = extension.trim().toLowerCase();

        checkExtensionGroup(extensionNames);
        StringBuilder sb = new StringBuilder(2 * 1024);
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            String glExtName = glExt.name.trim();
            if(containsIgnoreCase(extensionNames, glExtName))//if (extensionNames.contains(glExtName)) 
            {
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
     * Return  extension content, including enumerations and functions.<br>  
     *  Extensions are loaded by name, from a Collection.<br>
     *  <pre>
     *  <b>Example 1</b>
     *   // How to get Enumerations and Functions related to GL_OES_mapbuffer
     *   String[] exts = {"EGL_KHR_config_attribs","EGL_KHR_lock_surface"};
     *   Collection<String> extensions = Arrays.asList(exts);
     *   String someExtensions = glMain.asJavaExtension(extName);
     *   
     *   <b>Example 2</b>
     *   // how get all AMD related extensions, in java code ?  
     *   List<String> amdNames  = queryExtensionNames("AMD");  
     *   String amdExtensions = glMain.asJavaExtension(amdNames);
     *
     *    <b>Example 3</b>
     *   // how get all AMD related extensions, in java code ?     
     *   String amdExtensions = glMain.asJavaExtension("AMD");
     *   
     *   @see #queryExtensionNames(String)
     *   @see #asJavaExtension(String)
     *   @see #asJavaExtension(String, Collection)
     *  </pre>
     * @param extension - collection of extension name 
     */
    public String asJavaExtension(Collection<String> extensionNames) {       
        
        Collection<GLExtension> extList = new ArrayList<GLExtension>();

        StringBuilder sb = new StringBuilder(2 * 1024);
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            String glExtName = glExt.name.trim();
            if (extensionNames.contains(glExtName)) {
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
     * check is a GL_API is a GLES2.0+ class api 
     * @param api
     * @return
     */
    public static boolean isGLES2Type(GL_API api){
       return (api == GL_API.GLES2) || 
              (api == GL_API.GLES3) ||
              (api == GL_API.GLES31) ||
              (api == GL_API.GLES32);
    }
    
    /**
     * Generate a C++ class to load extension Functions 
     * @return C++ source code
     */
    public String asCclassExt(){
        String className = getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        if(api==GL_API.EGL){
            sb.append("#define  EGL_EGLEXT_PROTOTYPES 1 \n");
            sb.append("#include <EGL/egl.h> \n");
            sb.append("#include <EGL/eglext.h> \n");
        } 
        else if( isGLES2Type(api) /*api==GL_API.GLES2*/ ){
            sb.append("#define  GL_GLEXT_PROTOTYPES 1 \n");
            sb.append("#include <GLES3/gl32.h> \n");
            sb.append("#include <GLES2/gl2ext.h> \n");
        } else if(api==GL_API.GLES1){
            sb.append("#define  GL_GLEXT_PROTOTYPES 1 \n");
            sb.append("#include <GLES/gl.h> \n" );
            sb.append("#include <GLES/glext.h> \n");
        }
        sb.append("\n");
        sb.append("using namespace std;\n");
        sb.append("  class ").append(className).append("{\n");
        
        // variables
        sb.append("     public:\n");
        String fntProc = asCFunctionExtPointers(false);
        sb.append(fntProc);
        
        //methods
        sb.append("\n");
        sb.append("     public:\n");
        String loaders = asCFunctionExtLoaders(false);
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
     * Generate a C++ class to load extension functions. 
     * @param extension - extension to load - partial names are accepted
     * @param nameSuggestion - class name
     * @return C++ source code
     */
    public String asCclassExt(String extension, String nameSuggestion){
        String className = nameSuggestion != null ? nameSuggestion : getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        if(api != GL_API.GL){
            sb.append("#define  EGL_EGLEXT_PROTOTYPES \n");
            sb.append("#include <EGL/egl.h> \n");
            sb.append("#include <EGL/eglext.h> \n");
        } 

        if(isGLES2Type(api) /* api==GL_API.GLES2*/){
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
        String fntProc = asCFunctionExtPointers(extension,false);
        sb.append(fntProc);
        
        //methods
        sb.append("\n");
        sb.append("     public:\n");
        String loaders = asCFunctionExtLoaders(extension, false);
        sb.append(loaders);

        
        sb.append("\n");
        sb.append(" } // end of class");
        return sb.toString();
    }
    
    /**
     * Append include for EGL/GLES.<br>
     *  TODO - update other methos to use this
     * @param sb
     * @param feature
     */
    private void appendHeaderInclude(StringBuilder sb, GLFeatureEnum feature){
        int type = feature.getType();
        if (type == GLFeatureEnum.GL_Type) {
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GL/gl.h> \n");
            sb.append("#include <GL/glext.h> \n");
            return;
        }
        // always add EGL
        sb.append("#define  EGL_EGLEXT_PROTOTYPES \n");
        sb.append("#include <EGL/egl.h> \n");
        sb.append("#include <EGL/eglext.h> \n");

        if (type == GLFeatureEnum.GLES1_Type) {
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");
            sb.append("#include <GLES/gl.h> \n");
            sb.append("#include <GLES/glext.h> \n");
        }else {// GLES2 / GLES3 case
            sb.append("#define  GL_GLEXT_PROTOTYPES \n");            
            switch (feature) {
                case GL_ES_VERSION_2_0:
                    sb.append("#include <GLES2/gl2.h> \n");
                    break;
               case GL_ES_VERSION_3_0:
                    sb.append("#include <GLES3/gl3.h> \n");
                    break;
               case GL_ES_VERSION_3_1:
                   sb.append("#include <GLES3/gl31.h> \n");
                   break;
               case GL_ES_VERSION_3_2:
                   sb.append("#include <GLES3/gl32.h> \n");
                   break;
                default:
                    break;
            }           
            sb.append("#include <GLES2/gl2ext.h> \n");
        }        
    }
    
    /**
     * TODO - finish this !
     * 
     * 
     * Generate a C++ class to load extension Functions 
     * @param extensions to load     
     * @return C++ source code
     * 
     * @see #asJavaClassCore(GLFeatureEnum, boolean)
     */
    public String asCclassCoreExt(GLFeatureEnum feature, 
                                  boolean backwards,
                                  List<String> extensions, 
                                  String nameSuggestion){
        
        String className = nameSuggestion != null ? nameSuggestion : getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        // append includes
        appendHeaderInclude(sb, feature);
       
             
        sb.append("\n");
        sb.append("using namespace std;\n");
        sb.append("  class ").append(className).append("{\n");
        
        if (extensions.size() > 0) {
            // variables
            sb.append("     public:\n");
            String fntProc = asCFunctionExtPointers(extensions, false);
            sb.append(fntProc);

            // methods
            sb.append("\n");
            sb.append("     public:\n");
            String loaders = asCFunctionExtLoaders(extensions, false);
            sb.append(loaders);
        }
        
        List<GLFeatureEnum> featureNameList = null;

        if (backwards) {
            featureNameList = feature.getBackwardsList();
        } else {
            featureNameList = new ArrayList<GLFeatureEnum>(1);
            featureNameList.add(feature);
        }

        for (GLFeatureEnum fetName : featureNameList) {
            String name = fetName.getName();
            String number = fetName.getNumber();
            
            sb.append("  /**\n")
              .append("   * ").append(" Core Feature: ").append(name).append(" ").append(number).append("\n")
              .append("  */\n");

            GLFeature api_feature = getAPIFeature(fetName);
            api_feature.setJavaAPIMode(getAPI());
            String coreAsCPPString = api_feature.asJavaString();
            sb.append(coreAsCPPString);
        }
        
        
        
        sb.append("\n");
        sb.append(" } // end of class");
        return sb.toString();
    }
    
    /**
     * Generate a C++ class to load extension Functions 
     * @param extensions to load
     * 
     * @return C++ source code
     */
    public String asCclassExt(List<String> extensions, String nameSuggestion){
        String className = nameSuggestion != null ? nameSuggestion : getAPI().toUpperCase() + "Ext";
        StringBuilder sb = new StringBuilder(10*1024);
        if(api != GL_API.GL){
            sb.append("#define  EGL_EGLEXT_PROTOTYPES \n");
            sb.append("#include <EGL/egl.h> \n");
            sb.append("#include <EGL/eglext.h> \n");
        } 
        // GL-ES
        if(isGLES2Type(api) /* api==GL_API.GLES2*/){
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
        String fntProc = asCFunctionExtPointers(extensions,false);
        sb.append(fntProc);
        
        //methods
        sb.append("\n");
        sb.append("     public:\n");
        String loaders = asCFunctionExtLoaders(extensions, false);
        sb.append(loaders);

        
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
         case GLES2:
         case GLES3:
         case GLES31:
         case GLES32:mask = GLES2_MASK;  break;
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
     public List<GLFunction> getCoreFunctions(GLFeatureEnum featureName){
         GLFeature feature = getAPIFeature(featureName);
         if(feature == null){
             throw new IllegalArgumentException("Invalid feature. Feature " + 
                                                 featureName + " is not available for " +
                                                  this.getAPI_new());
         }
         return feature.getAllFunctions(); //.getFunctions();
     }
     
     /**
      * Get GLFeature from this parsed GL/EGL API
      * @param featureName - one of EGL/GLES/GL
      * @return Requested feature
      */
     public GLFeature getAPIFeature(GLFeatureEnum featureName){
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
     public List<GLenum> getCoreExtrictEnum(GLFeatureEnum featureName){
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
    public List<GLenum> getCoreBackwardsEnum(GLFeatureEnum featureName) {
        List<GLFeatureEnum> backwards = featureName.getBackwardsList();
        List<GLenum> list = new ArrayList<GLenum>();

        for (GLFeatureEnum fet : backwards) {
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

        boolean testFunctionPointers = false;
        boolean testQueryExt = false;
        boolean testFunctionLoaders = false;
        boolean testAllLoaders = false;
        boolean testCclass = false;
        boolean testFeaturesNames = false;
        boolean testCore = false;
        boolean testJavaClass = false;
        boolean testSingleClassExt = false;
        boolean testAsJavaCore = true;
        boolean testJNIgenHeader = false;
        boolean testClassExtSelec = false;
        
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
        String ext = "INTEL";
        if (testFunctionPointers) {
            
            String pointers = gl.asCFunctionExtPointers(ext, true);
            System.out.println(pointers);
        }

        if (testFunctionLoaders) {
            String pfnLoaders = gl.asCFunctionExtLoaders(ext,true);
            System.out.println("\n\n// asCFunctionLoaders\n " + pfnLoaders);
        }

        if (testAllLoaders) {
            String allLoaders = gl.asCforAllLoadersExt();
            System.out.println("//all loaders: \n" + allLoaders);
        }

        if (testCclass) {
            String cClass = gl.asCclassExt(ext,null);
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
            for (GLFunction func : gl.getCoreFunctions(GLFeatureEnum.GL_ES_VERSION_3_2)) {
                System.out.println(func);
            }

            System.out.println("Test Core Functions:");
            for (GLenum enume : gl.getCoreBackwardsEnum(GLFeatureEnum.GL_ES_VERSION_3_2)) {
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
            String extName ="aep";// ext;//"KHR_create_context";
            String javaClass = gl.asJavaClassExt(extName,"GLESextensions");
            System.out.println(javaClass);
        }
        
        if(testAsJavaCore){
            System.out.println("//Core Java Class Creation");            
            String javaCore = gl.asJavaClassCore(GLFeatureEnum.EGL_VERSION_1_5, false);
            System.out.println(javaCore);
        }
    
        if(testJNIgenHeader){
            String header = gl.asJavaJnigenHeaderExt("AMD");
            System.err.println("// jnigen Header\n "+ header);
        }
        
        if(testClassExtSelec){
            List<String> listAMD = gl.queryExtensionNames("AMD");
            List<String> listQCOM = gl.queryExtensionNames("QCOM");
            
            List<String> listAll = listAMD;
            listAll.addAll(listQCOM);
            
            String javaClass = gl.asJavaClassExt(listAll, "GLES_EXTtest");
            System.err.println("//Java ext\n\n" + javaClass);
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
