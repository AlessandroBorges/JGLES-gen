package gles.generator.internal;



import java.util.*;

public class GLExtension
 implements JavaPrinter, Comparable<GLExtension>

{    
    public String name;   
   
    public String supportedAPI, protect;
    
    public static final String FUNC_LOADER = "myFuncLoader";
    
    public static final String GL = "gl", GLES1 = "gles1", GLES2="gles2", EGL="egl";
   
    public List<GLExtensionRequire> requires = new ArrayList<GLExtensionRequire>(2);
    
    private Boolean isGL = null;
   
   
    /**
     * Add require to this Extension
     * @param require
     */
    public void add(GLExtensionRequire require){
        requires.add(require);
        require.owner = this;
        require.extName  = this.name;
    }
        
       
    public boolean isEGL(){
        return supportedAPI != null && supportedAPI.contains(EGL);
    }
    
    public boolean isGL(){
      if(supportedAPI == null) return false;
      if(this.isGL == null){
          String s = supportedAPI.replace(EGL, "   ").replace(GLES1, "     ").replace(GLES2,"     ");
          isGL = s.contains(GL) ;
      }      
      return  isGL.booleanValue();
    }
    
    public boolean isGLES1(){
       return  supportedAPI != null && supportedAPI.contains(GLES1);
    }
    
    public boolean isGLES2(){
        return  supportedAPI != null && supportedAPI.contains(GLES2);
     }
    
    boolean verbose = false;
    
    private String apiMode;
    
    /**
     * set API mode for printing
     * @param api
     */
    public void setJavaAPIMode(String api){
        this.apiMode = api;
    }
    
    /**
     * 
     */
//    @Deprecated
//    public String asJavaString2(){
//        if(apiMode==null) 
//            throw new UnsupportedOperationException("apiMode not set. Please call setJavaAPIMode(api)");
//        //TODO check apiModeValues
//        String enums = getJavaEnum(apiMode);        
//        List<String> functionCproto = getCprototypeFunctions(apiMode);
//        List<String> functionCcall = getCcallFunctions(apiMode);
//        
//        //TODO - build mapping from Cproto to functionJava ( 1:n) 
//        //List<String> functionJava = getJavaStringFunctions(apiMode);
//        Map<String, GLFunction> mapDatabase = getJavaStringFunctionsMap(apiMode);
//        
//        StringBuilder sb = new StringBuilder(64*1024);
//        sb.append("/************************************************************************/\n");
//        sb.append("/** Extensions ").append(this.name).append("  ***/\n");
//        sb.append("/***********************************************************************/\n");
//        sb.append(enums);
//        sb.append('\n');
//        
//        List<String> functionsJava = new ArrayList<String>(8);
//        
//        int size = functionCproto.size();
//        for(int i=0; i<size; i++){            
//            String cProto = functionCproto.get(i);
//            String cCall= functionCcall.get(i);
//            
//            functionsJava = getJavaStringFunction(null, mapDatabase, functionsJava);
//            
//            String javaFunc = functionsJava.get(i);
//            
//            sb.append("\n /**\n")
//            .append("  * C Prototype:")
//            .append(addIdentLines(cProto,"* "))   // create function to add * after new line
//            .append("\n  **/ \n");
//            
//            sb.append("  public static ");
//            sb.append(javaFunc)
//            .append("/*\n")
//            .append("\t // jnigen - native goes here")
//            .append(addIdentLines(cCall,"\t"))
//            .append("\n\t */\n");               
//        }
//        return sb.toString();
//    }
    
    /**
     * Create declaration for function procedure pointers.
     * @return
     */
    public String asCfunctionPointers(){
        String NTAB = "\n\t";
        StringBuilder sb = new StringBuilder(5 * 1024);

        for (GLExtensionRequire require : requires) {
            
          //skip non matching API
            if(!require.api.contains(apiMode) ){
                continue;
            }
            
            List<GLFunction> funcs = require.getAllFunctions();
            // skip functionLESS extensions
            if(funcs==null || funcs.isEmpty())
                continue;
            
            sb.append("\n\n");
            sb.append(" ///////////////////////////////////\n");
            sb.append("  // PFN_PROC functions declaration ");
            {
                if (require.extName != null) {
                    sb.append("\n  // Extension: ").append(require.extName);
                }
                if (require.api != null) {
                    sb.append("\n  // API: ").append(require.api);
                }
                if (require.profile != null) {
                    sb.append("\n  // Profile: ").append(require.profile);
                }
            }
            sb.append("\n //////////////////////////////////");
            sb.append("");

            
            for (GLFunction func : funcs) {
                String funcName = func.name;
                sb.append(NTAB)
                .append(pfnproc(funcName))
                .append("\t")
                .append(funcName)
                .append(";");
            }// for Loops
        }// requires

        return sb.toString();
    }
    
    /**
     * get plain function loader name
     * @return
     */
    public String getFunctionLoaderName(){
        return "loadExtFunc" + name;
    }
    
    /**
     * Create method to load extensions
     * @return
     */
    public String asCfunctionLoaders(){
        
        String NTAB = "\n\t  ";
        StringBuilder sb = new StringBuilder(5 * 1024);

        for (GLExtensionRequire require : requires) {
            //skip non matching API
            if(!require.api.contains(apiMode) ){
                continue;
            }
            
            List<GLFunction> funcs = require.getAllFunctions();
            // skip extensions without functions
            if(funcs.isEmpty())
                continue;
            
            sb.append("\n");
            sb.append(" /// ====================================\n")
              .append("  // PFN_PROC Extensions functions Loader. ");
            {
                if (require.extName != null) {
                    sb.append("\n  // Extension: ").append(require.extName);
                }
                if (require.api != null) {
                    sb.append("\n  // API: ").append(require.api);
                }
                if (require.profile != null) {
                    sb.append("\n  // Profile: ").append(require.profile);
                }
            }
            sb.append("\n  /// =====================================/\n");
            sb.append("");

            sb.append("    int loadExtFunc").append(require.extName).append("(){");
            for (GLFunction func : funcs) {
                String funcName = func.name.trim();
                String pfnName = pfnproc(funcName);
                sb.append(NTAB)
                .append(funcName)
                .append(" = (")
                .append("")
                .append(pfnName)
                .append(") ").append(FUNC_LOADER).append("(")
                .append("\"").append(funcName).append("\");");
            }// for func
            sb.append("\n")
            .append(NTAB)
            .append("return 1;\n")
            .append("")
            .append("     } // end of loadExtFunc").append(require.extName).append("()\n");
        }// requires

       
        return sb.toString();
    }
    
    
    /**
     * Create list with functionLoader names
     * @return
     */
    public List<String> asCextFuncLoaderNames(List<String> loaderNames){
       // List<String> loaderNames = new ArrayList<String>();
        if(loaderNames==null){
            loaderNames = new ArrayList<String>();
        }
        for (GLExtensionRequire require : requires) {
            List<GLFunction> funcs = require.getAllFunctions();
            // skip extensions without functions
            if(funcs.isEmpty())
                continue;
            String name = "loadExtFunc"+require.extName +"()";
            loaderNames.add(name);
        }// requires
        return loaderNames;
    }
    
        
   
//    public List<String> asCforAllLoaders(String apiName){
//        List<String> loaderNames = new ArrayList<String>();
//       // String funcName = "loadAllExtensions" + apiName.toUpperCase();
//        
//        // get all extensions names
//        for (GLExtensionRequire require : requires) {
//            List<GLFunction> funcs = require.getAllFunctions();
//            // skip extensions without functions
//            if(funcs.isEmpty()) continue;
//            
//            loaderNames.add(" require.extName");
//            
//        }//         
//       
//        return loaderNames;
//    }
    
    private static String PFN = "PFN";
    private static String PROC = "PROC"; 
    /**
     * PFNie and PROCfie function.
     *  
     * @param funcName - a function name as glUseProgram
     * @return a function pointer, as PFNGLUSEPROGRAMPROC
     */
    private static String pfnproc(String funcName){        
        return PFN + funcName.trim().toUpperCase() + PROC;
    }
    
    
    /**
     * Print this Extension set as String
     */
    public String asJavaString(){
        if(apiMode==null) 
            throw new UnsupportedOperationException("apiMode not set. Please call setJavaAPIMode(api)");
        
        //TODO check apiModeValues
        Map<String, GLFunction> mapDatabase = getJavaStringFunctionsMap(apiMode);
        //enumeration section
        StringBuilder sb = new StringBuilder(2*1024);
        // skip enumLESS extensions
        if (getEnumCount() > 0) {
            sb.append("    /**\n");
            sb.append("     * Enumeration for extension: ").append(this.name).append("\n");
            sb.append("     */\n");
            String enums = getJavaEnum(apiMode);
            sb.append(enums);
            sb.append('\n');
        }
        List<GLExtensionRequire> requires = getRequires(apiMode);
        List<String> jFuncStringList = new ArrayList<String>();
        // function section
        for (GLExtensionRequire require : requires) {
            List<GLFunction> funcs = require.getAllFunctions();
            //skip functionLESS extension
            if(funcs==null || funcs.size()==0){
                continue;
            }
            sb.append(" // Function(s) for extension ");
            {
                if(require.extName != null){
                    sb.append(require.extName);
                }
                if(require.api != null){
                    sb.append(", API: ").append(require.api);
                }
                if(require.profile != null){
                    sb.append(", profile: ").append(require.profile);
                } 
            }
            sb.append("   \n");
                       
            
            for(GLFunction func : funcs){
                jFuncStringList = getJavaStringFunction(func, mapDatabase, jFuncStringList);
                String cProto = func.asCProto();
                String cCall = func.asCcallFunction();
                
                // loop on possible java versions of this func
                for(String asJavaString : jFuncStringList){
                    sb.append("\n")
                    .append(" /**\n")
                    .append("  *  Extension: ").append(this.name == null?  " ? ": this.name ).append('\n')
                    .append("  * ").append(func.comment==null?  " ": func.comment ).append('\n')
                    .append("  * C Prototype:")
                    .append(addIdentLines(cProto,"* "))   // create function to add * after new line
                    .append("\n  **/ \n");
                    
                    sb.append(" public final native static ");
                    sb.append(asJavaString)
                    .append("/*\n")
                    .append("\t // jnigen - native goes here")
                    .append(addIdentLines(cCall,"\t"))
                    .append("   */\n");            
                }                
            }            
        }
        
        return sb.toString();
    }
    
    /**
     * Unmap/unroll a n:1 mapping to a 1:n.<br>
     * On Map database, we have n javaStringFunc mapped to 1 GLFunction.
     * This method select those  javaStringFunc, using func 
     * 
     * @param func - key function to select javaStringFunctions
     * @param database - where the query javaStringFunctions
     * @param resultSet - where to put result
     * 
     * @return resulting javaStringFuntions related to func
     */
    public static List<String> getJavaStringFunction(GLFunction func, 
                                                     Map<String, GLFunction> database, 
                                                     List<String> resultSet ){
        resultSet.clear();
        Set<String> keys = database.keySet();
        for (String javaString : keys) {
            GLFunction value = database.get(javaString);
            if(func == value){
                resultSet.add(javaString);
            }            
        }                
        return resultSet;
    }
    
    
    
    /**
     * 
     * @param txt
     * @param identMark
     * @return
     */
    protected static String addIdentLines(String txt, String identMark){
        String mark = "\n  " + identMark;        
        txt = txt.replace("\n", mark);     
        //txt = txt + mark;
        return txt;
    }
    
    /**
     * TODO return a List !
     * 
     * Get Java enumerations for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public String getJavaEnum(String api){
        String enums = "";
        for(GLExtensionRequire require : requires){
            
            if(api == EGL && require.isEGL()){
                enums += require.getJavaEnums();
            }
            
            if(api == GL && require.isGL()){
                enums += require.getJavaEnums();
            }
            
            if(api == GLES1 && require.isGLES1()){
                enums += require.getJavaEnums();
            }
            
            if(api == GLES2 && require.isGLES2()){
                enums += require.getJavaEnums();
            }
        }        
        return enums;
    }
    
    /**
     * Get a list of all GLenum of this extension
     * @param api - api to query
     * @return 
     */
    public List<GLenum> getGLEnumList(String api){
        List<GLenum> list = new ArrayList<GLenum>();
        for(GLExtensionRequire require : requires){
            
            if(api == EGL && require.isEGL()){
                list = require.getEnumsList(list);
            }
            
            if(api == GL && require.isGL()){
                list = require.getEnumsList(list);
            }
            
            if(api == GLES1 && require.isGLES1()){
                list = require.getEnumsList(list);
            }
            
            if(api == GLES2 && require.isGLES2()){
                list = require.getEnumsList(list);
            }
        }        
        return list;
    }
   
    
    /**
     * Get Java functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getJavaStringFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){                                
                functions.addAll(require.getJavaStringFunctions());
            }
        }        
        return functions;
    }
    
    /**
     * Get Java functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public Map<String, GLFunction> getJavaStringFunctionsMap(String api){
        Map<String, GLFunction> functions = new HashMap<String, GLFunction>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GL && require.isGL()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GLES1 && require.isGLES1()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GLES2 && require.isGLES2()){                                
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
        }        
        return functions;
    }
    
    /**
     * Get C prototype functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getCprototypeFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){
                functions.addAll(require.getCprototypeFunctions());
            }
        }        
        return functions;
    }
    
    /**
     * Get C prototype functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<GLExtensionRequire> getRequires(String api){
        List<GLExtensionRequire> listRequires = new ArrayList<GLExtensionRequire>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                listRequires.add(require);
            }
            
            if(api == GL && require.isGL()){
                listRequires.add(require);
            }
            
            if(api == GLES1 && require.isGLES1()){
                listRequires.add(require);
            }
            
            if(api == GLES2 && require.isGLES2()){
                listRequires.add(require);
            }
        }        
        return listRequires;
    }
    
    
    /**
     * Get C calling functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getCcallFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){
                functions.addAll(require.getCcallFunctions());
            }
        }        
        return functions;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 20;
        StringBuilder builder = new StringBuilder();
        builder.append("GLExtension [");
        if (name != null) {
            builder.append("extName=");
            builder.append(name);
            builder.append(", ");
        }
        if (supportedAPI != null) {
            builder.append("supported=");
            builder.append(supportedAPI);
            builder.append(", ");
        }
        if (protect != null) {
            builder.append("protect=");
            builder.append(protect);
            builder.append(", ");
        }
        if (requires != null) {
            builder.append("requires=");
            builder.append(requires.subList(0, Math.min(requires.size(), maxLen)));
            builder.append(", ");
        }
        if (isGL != null) {
            builder.append("isGL=");
            builder.append(isGL);
        }
        builder.append("]");
        return builder.toString();
    }


    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
       
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(" ");
            builder.append(iterator.next());
           // builder.append("\n");
        }
       // builder.append("]\n");
        return builder.toString();
    }

    /**
     * Get the number of GLEnum of this GLExtension
     * @return count of GLEnum
     * 
     * @see GLExtensionRequire#getEnumsCount()
     */
    public int getEnumCount(){
        int count = 0;
        for(GLExtensionRequire req : this.requires){
            count = req.getEnumsCount();
        }
        return count;
    }
    
    /**
     * Get the number of Functions of this GLExtension
     * @return count of GLFunctions 
     * 
     * @see GLExtensionRequire#getFunctionCount()
     */
    public int getFunctionCount(){
        int count = 0;
        for(GLExtensionRequire req : this.requires){
            count = req.getFunctionCount();
        }
        return count;
    }
    

    /**
     * Set GLCommands definitions to saved GL functions names
     * @param cmdSet collected function definitions
     */
    public void setCommands(GLcmdSet cmdSet) {
       for(GLExtensionRequire require : requires){
           require.setCommands(cmdSet);
       }        
    }

   
    /**
     * Set GLEnum definitions to saved GL enumeration names
     * @param enumAll collected definitions
     */
    public void setEnumerations(GLenumAll enumAll) {
        for(GLExtensionRequire require : requires){
            require.setEnumerations(enumAll);
        }        
    }


    @Override
    public int compareTo(GLExtension other) {
            return this.name.compareTo(other.name);       
    }
    
}
