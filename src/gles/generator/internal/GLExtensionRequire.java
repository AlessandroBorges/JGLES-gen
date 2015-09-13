/**
 * 
 */
package gles.generator.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;

/**
 * @author Alessandro Borges
 *
 */
public class GLExtensionRequire {
    /**
     * Owner of this Require
     */
    protected GLExtension owner;
    
    public static final int GLMODE = 1, GLCOREMODE  = 2,  EGLMODE = 4, GLES1MODE = 8, GLES2MODE = 16;
    
   // public enum OutputMode{
   //    GLMODE = 1, GLCOREMODE  = 2,  EGLMODE = 4, GLES1MODE = 8, GLES2MODE = 16;
   //     
   // }
    
    /**
     * Notes:<br>
     * <ul>
     *  <li> <b>extName</b> comes from owner GLextension.
     *  <li> if <b>supported</b> is undefined, it gets value from belonging GLExtension's supported.<br>
     *  <ul> 
     */
    public String extName, api, protect, profile, comment;
    
    public List<String> enum_names = new ArrayList<String>(4); 
    public List<String> cmd_names = new ArrayList<String>(4);
    
    public List<GLFunction> commands =  new ArrayList<GLFunction>(4);
    public List<GLenum> enumerations =  new ArrayList<GLenum>(4);
    
    private Boolean isGL = null;
    
    public boolean verbose = false;
    
    private boolean glMode, eglMode, glCoreMode,gles1Mode,gles2Mode;
    
    private List<GLFunction> allFunctions;
    
    /**
     * helper for processing XML
     * @param att
     */
    public void process(Attributes att){
        api = att.getValue("api");
        protect = att.getValue("protect");
        profile = att.getValue("profile");
        comment = att.getValue("comment");
        
        // fix the issue with supported API
        if(owner!= null){
            if(this.api==null){
                api = owner.supportedAPI;
            }
        }
        
    }
    
    public void setSelectMode(int outputMode){
        glMode  = (outputMode & GLMODE)  != 0;
        eglMode = (outputMode & EGLMODE) != 0;
        glCoreMode = (outputMode & GLCOREMODE) != 0;
        gles1Mode  = (outputMode & GLES1MODE)  != 0;
        gles2Mode  = (outputMode & GLES2MODE)  != 0;
    }
    
    
    public boolean isEGL(){
        return api != null && api.contains("egl");
    }
    
    public boolean isGL(){
      if(api == null) return false;
      if(this.isGL == null){
          String s = api.replace("egl", "   ").replace("gles1", "     ").replace("gles2","     ");
          isGL = s.contains("gl") ;
      }      
      return  isGL.booleanValue();
    }
    
    public boolean isGLES1(){
       return  api != null && api.contains("gles1");
    }
    
    public boolean isGLES2(){
        return  api != null && api.contains("gles2");
     }

    /**
     * XML process.
     * Add enum name
     * @param enumName
     */
    public void addEnumName(String enumName){
        enum_names.add(enumName);
    }
    
    /**
     * XML Process
     * Add cmd name
     * @param cmdName
     */
    public void addCmdName(String cmdName){
        cmd_names.add(cmdName);
    }
    
    /**
     * Set commands for this extensions
     * @param cmds
     */
    public void setCommands(GLcmdSet cmds){
        for(String cmdName : cmd_names ){            
           GLFunction cmd =  cmds.getGLFunction(cmdName);
           this.commands.add(cmd);
        }        
    }
    
    public void setEnumerations(GLenumAll enumAll){        
        for (String enumName : enum_names) {
            
           GLenum glEnum = enumAll.getEnum(enumName);
           if(glEnum==null){
               System.err.printf("GlExt %s error : GLenum %s not found!", extName, enumName);
           }else{
               enumerations.add(glEnum);
           }
        }        
    }
    

    /**
     * Get a string with basic information about this Extensions,
     * as extension name, number of enumerations and number of functions.
     * <pre>Example:
     *  API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 4, Functions: 3
     * </pre>
     * @return a resume of this extension.
     */
    public String getResume(){
        String api = " API: ";
        int count =0;
        if(isEGL())
            api +="EGL"; 
        else 
            if(isGL()){
              api +="GL";
              count++;
        }
        if(isGLES1()){
            api += (count>0 ? ", ":"") + "GL-ES 1.x";
            count++;
        }
        if(isGLES2()){
            api += (count>0 ? ", ":"") + "GL-ES 2.0+";
            count++;
        }
           
        return extName +
                api +
                ", Enumerations: " + enum_names.size() +
                ", Functions: " + cmd_names.size() ;
        
    }
    
    
    /**
     * Return GLEnum of this extensions as a JavaString
     * @return
     */
    public String getJavaEnums(){
        StringBuilder builder = new StringBuilder();
        if (this.enumerations != null && enumerations.size()>0) {
            builder.append("    // enumerations: \n");
            for(GLenum glEnum : enumerations){
             builder.append(glEnum.asJavaString());   
            }            
            builder.append("\n ");
        }        
       return builder.toString();        
    }
    
    /**
     * Get Enumeration count
     * @return GLEnum count of this Extension
     */
    public int getEnumsCount(){       
        if (this.enumerations != null ) {           
             return enumerations.size();            
        }        
       return 0;        
    }
    
    /**
     * Get the number of available functions  
     * @return count of functions
     */
    public int getFunctionCount(){
        if(this.commands != null){
            return commands.size();
        }
        return 0;
    }
    
    /**
     * Return list of GLEnum of this extensions as a JavaString
     * @return
     */
    public List<GLenum> getEnumsList(List<GLenum> list){        
        if (this.enumerations != null && enumerations.size()>0) {           
            for(GLenum glObject : enumerations){
             list.add(glObject);   
            }
        }        
       return list;        
    }
    
    /**
     * Return the functions as Java methods, in a List
     * @return List of String with one command per position. 
     */  
    public List<String> getJavaStringFunctions() {
        List<String> functions = new ArrayList<String>(8);
        if (commands == null || commands.size() == 0) {
            return functions;
        }
        int size = commands.size();
        
        for (int i = 0; i < size; i++) {
            GLFunction function = commands.get(i);
            // process function
            processFunctionJava(function, functions);
            
            functions.add(function.asJavaString());
            GLFunction alias = function.getAlias();
            if(alias != null){
                functions.add(alias.asJavaString());
            }
        }
        return functions;
    }
    
    /**
     * Return the functions as Java methods, with related GLFunction attached
     * @return String[] with one command per position. 
     */
    public Map<String,GLFunction> getJavaMapFunctions() {
        Map<String,GLFunction> mapFunctions = new HashMap<String, GLFunction>(8);
        
        if (commands == null || commands.size() == 0) {
            return mapFunctions;
        }
        List<GLFunction> allFunctions = getAllFunctions();
        int size = allFunctions.size();
        
        for (int i = 0; i < size; i++) {
            GLFunction function = allFunctions.get(i);            
            // process function
            processFunctionJava(function, mapFunctions);            
        }
        return mapFunctions;
    }
    
    
    /**
     * get the functions and alias
     * @return
     */
    public List<GLFunction> getAllFunctions() {
        if(allFunctions != null)
            return allFunctions;
        
        List<GLFunction> functions = new ArrayList<GLFunction>(8);
        if (commands == null || commands.size() == 0) {
            return functions;
        }
        int size = commands.size();
        
        for (int i = 0; i < size; i++) {
            GLFunction function = commands.get(i);
            functions.add(function);            
            // the alias
            GLFunction alias = function.getAlias();
            if(alias != null){
               // System.out.println("// Added alias: " + alias.name + "\t to \t"+ function.name);
                functions.add(alias);
            }
        }        
        allFunctions = functions;
        return allFunctions;
    }
    
    /**
     * Get the function code as java code, including functions 
     * with pointer and alias.
     * 
     * 
     * @param func GLFunction to query
     * @param result where to store the resulting function code
     */
    @Deprecated
    public void processFunctionJava(GLFunction func, List<String> result){
        if(func.hasPointerParam()){
            //the alternate version:
            String javaFuncArray = func.asJavaStringArrayAndOffset();            
            if(javaFuncArray != null)
                result.add(javaFuncArray);
            
            String javaFuncBuffer = func.asJavaStringBufferAndOffset();
            if(javaFuncBuffer != null)
                result.add(javaFuncBuffer);            
        }else{
            String javaFunc = func.asJavaString();
            result.add(javaFunc);
        }
        // does it have alias ?
        GLFunction alias = func.getAlias();
        if(alias != null){
            processFunctionJava(alias, result);
        }        
    }
    
    /**
     * Creates Java String Functions and Maps to its GLFunction.
     * It also handles pointer and alias.
     * 
     * 
     * @param func GLFunction to generate Java String Functions
     * @param result map to store resulting Java String functions mapped to its owner.
     */
    public static void processFunctionJava(GLFunction func, Map<String, GLFunction> result){
        if(func.hasPointerParamButString()){
            //the alternate version:
            String javaFuncArray = func.asJavaStringArrayAndOffset();            
            if(javaFuncArray != null)
                result.put(javaFuncArray, func);
            
            String javaFuncBuffer = func.asJavaStringBufferAndOffset();
            if(javaFuncBuffer != null)
                result.put(javaFuncBuffer, func);            
        }else{
            String javaFunc = func.asJavaString();
            result.put(javaFunc, func);
        }
//        // does it have alias ?
//        GLFunction alias = func.getAlias();
//        if(alias != null){
//            processFunctionJava(alias, result);
//        }        
    }
    
    
    
    /**
     * Return the functions as C prototype functions, in a String[]
     * @return String[] with one command per position. 
     */
    @Deprecated
    public List<String> getCprototypeFunctions() {
        List<String> functions = new ArrayList<String>();
        if (commands == null || commands.size() == 0) {
            return functions;
        }
        int size = commands.size();        
       
        for (int i = 0; i < size; i++) {
            GLFunction func = commands.get(i);
            func.skipPrintAliasOnce();
            functions.add(func.toString());
            GLFunction glAlias = func.getAlias();
            if(glAlias != null)
                functions.add(glAlias.toString());
        }
        return functions;
    }
    
    /**
     * Return the functions as C call functions,i.e., with type cast on parameters type in a String[]
     * @return List of String with one command per position. 
     */
    @Deprecated
    public List<String> getCcallFunctions() {
        //  functions[i] = glObject.asCcallFunction();
        List<String> functions = new ArrayList<String>();
        if (commands == null || commands.size() == 0) {
            return functions;
        }
        int size = commands.size();        
       
        for (int i = 0; i < size; i++) {
            GLFunction glObject = commands.get(i);
            glObject.skipPrintAliasOnce();
            functions.add(glObject.asCcallFunction());
            GLFunction glAlias = glObject.getAlias();
            if(glAlias != null)
                functions.add(glObject.asCcallFunction());
        }
        return functions;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 200;
        StringBuilder builder = new StringBuilder();
        builder.append("\n  /**  \n");
        if (extName != null) {
            builder.append("   * Extension:  ");
            builder.append(getResume());
            builder.append("\n");
        }
                
        if (api != null) {
            builder.append("   * GL API = ");
            builder.append(api);
            builder.append("\n");
        }
        if (protect != null) {
            builder.append("   * protect=");
            builder.append(protect);
            builder.append("\n  **/ \n");
        }else{
            builder.append("   **/ \n");
        }
        if (verbose && enum_names != null) {
            builder.append("enum_names=");
            builder.append(enum_names.subList(0, Math.min(enum_names.size(), maxLen)));
            builder.append("\n ");
        }
        if (verbose && cmd_names != null) {
            builder.append("cmd_names=");
            builder.append(cmd_names.subList(0, Math.min(cmd_names.size(), maxLen)));
            builder.append("\n ");
        }
        
        if (this.enumerations != null && enumerations.size()>0) {
            builder.append("   // enumerations \n");
            builder.append(toString(enumerations, maxLen));
            builder.append("\n ");
        }
        
        if (commands != null && commands.size()>0) {
            builder.append("   // commands \n");
            builder.append(toString(commands, maxLen));
            builder.append("\n ");
        }
        if (verbose && api != null) {
            builder.append("isGL=");
            builder.append(isGL());
            builder.append("\n ");
            
            builder.append("isGLES1=");
            builder.append(isGLES1());
            builder.append("\n ");
            
            builder.append("isGLES2=");
            builder.append(isGLES2());
            builder.append("\n ");
            
            
            builder.append("isEGL=");
            builder.append(isEGL());
            builder.append("\n ");
        }
        builder.append("/** end extension **/ \n\n");
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
    
    
}
