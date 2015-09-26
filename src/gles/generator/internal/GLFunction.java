package gles.generator.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Encapsulates a GL function.<br>
 * It's created at XML parsing and stored at GLcmdSet.<br>
 * 
 * 
 * 
 * @author Alessandro Borges
 *
 */
public class GLFunction implements JavaPrinter, Comparable {

    public String proto, ptype, name, alias, comment;
    public List<GLParam> params = new ArrayList<GLParam>(4);    
    
    protected boolean isAlias = false;
    
   /**
    * Ctor
    */
    public GLFunction(){        
    }
    
    /**
     * Add a new GLparam
     * @param curGLparam
     */
    public void add(GLParam curGLparam) {
        params.add(curGLparam);        
    }
   
    /**
     * get Alias of this command
     * @return
     */
    public  GLFunction getAlias() {
        if(alias==null) 
            return null;
        
           GLFunction aliasCmd = new GLFunction();
           aliasCmd.isAlias = true;
           aliasCmd.name = alias;
           aliasCmd.proto = proto;
           aliasCmd.params = this.params;
           aliasCmd.ptype = ptype; 
           if(this.comment == null){
               aliasCmd.comment = " alias of " + this.name;
           }else{
               aliasCmd.comment = this.comment + " alias of " + this.name;
           }
           return aliasCmd;
    }
    
    /**
     * Check if this function as pointer parameters
     * @return true if this GLFunction has pointer parameters 
     */
    public boolean hasPointerParam(){        
        for(GLParam param : params){
            if(param.hasPointer()){
                return true;
            }
        }        
        return false;
    }
    
    /**
     * Check if this function as pointer parameters, Except String type
     * @return true if this GLFunction has pointer parameters 
     */
    public boolean hasPointerParamButString(){        
        for(GLParam param : params){
            if(param.hasPointer() && !param.isString() ){
                return true;
            }
        }        
        return false;
    }
  
    
    /**
     * Check if one of parameters has a void *
     * @return
     */
    public boolean hasVoidPointer(){
        for(GLParam param : params){
            if(param.isVoidPointer()){
                return true;
            }
        }        
        return false;
    }
    
    
    /**
     * 
     * @return
     */
    @Deprecated
    public String getCCall(){
        //skipPrintAliasOnce();
        String cCall = asCcallFunction();
        return cCall;
    }
    
    /**
     * Get this function replacing pointers by arrays and offset as parameters  
     * @return
     */
    public String asJavaStringArrayAndOffset(){
        // Ptr less does'nt neet arrays
        // void * doesn't match primitive type arrays
        if(!hasPointerParam() || hasVoidPointer()){
            return null;
        }
                
        StringBuilder builder = new StringBuilder(256);       
        builder.append("\n  ");
        
         if (proto != null && proto.trim().length()>1) {            
             String jProto = JavaTypeMap.getJType(proto);
             builder.append(jProto);
             builder.append(" ");
         }
         if (ptype != null) {           
             String jType = JavaTypeMap.getJType(ptype);
             builder.append(jType);
             builder.append(" ");
         }
         if (name != null) {            
             builder.append(name);
             builder.append("");
         }
         if (params != null) {
             builder.append("( ");
             int count = 0;
             String ident = getBlankSpaceString(builder.length()-1); 
             for(GLParam param : params){                 
                 count++;
                 if(count>1){
                     builder.append(",\n");
                     builder.append(param.asJavaStringArray(ident));
                 }else{
                     builder.append(param.asJavaStringArray(""));
                 }
                
              }  
             builder.append(");");
         }   else{
             builder.append("();").append(" ");
         }
         
//         GLFunction aliasCmd = this.getAlias();
//         if(aliasCmd != null){
//             builder.append("\n/* alias  of "+ name +" */");
//             builder.append(aliasCmd.asJavaString());            
//         } 
         
         
         return builder.toString();
    }
    
    /**
     * Get this function replacing pointers by arrays and offset as parameters  
     * @return
     */
    public String asJavaStringBufferAndOffset(){
        if(!hasPointerParam()){
            return null;
        }
        
        StringBuilder builder = new StringBuilder(128);
        // builder.append("GLcommand [");
         builder.append("\n ");
         if (proto != null && proto.trim().length()>1) {
            // builder.append("proto=");
             String jProto = JavaTypeMap.getJType(proto);
             builder.append(jProto);
             builder.append(" ");
         }
         if (ptype != null) {
            // builder.append("ptype=");
             String jType = JavaTypeMap.getJType(ptype);
             builder.append(jType);
             builder.append(" ");
         }
         if (name != null) {
            // builder.append("name=");
             builder.append(name);
             builder.append("");
         }
         if (params != null) {
             builder.append("(");
             int count = 0;
             String ident = getBlankSpaceString(builder.length() ); 
             for(GLParam param : params){                 
                 count++;
                 if(count > 1){
                     builder.append(",\n");
                     builder.append(param.asJavaStringBufferAndOffset(ident));
                 } else{
                     builder.append(param.asJavaStringBufferAndOffset(" "));    
                 }                 
              }  
             builder.append("); ");
         }   else{
             builder.append("();").append(" ");
         }
//         GLFunction aliasCmd = this.getAlias();
//         if(aliasCmd != null){
//             builder.append("\n/* alias  of "+ name +" */");
//             builder.append(aliasCmd.asJavaString());            
//         }        
         return builder.toString();
    }
    
    /**
     * Return this function as a Java String
     */
    public String asJavaString(){      
        StringBuilder builder = new StringBuilder(256);
       
         builder.append("\n  ");
         if (proto != null && proto.trim().length()>1) {
            // builder.append("proto=");
             String jProto = JavaTypeMap.getJType(proto);
             builder.append(jProto);
             builder.append(" ");
         }
         if (ptype != null) {
            // builder.append("ptype=");
             String jType = JavaTypeMap.getJType(ptype);
             builder.append(jType);
             builder.append(" ");
         }
         if (name != null) {
            // builder.append("name=");
             builder.append(name);
             builder.append(" ");
         }
         if (params != null) {
             builder.append("(");
             int count = 0;
             String ident = getBlankSpaceString(builder.length()); 
             for(GLParam param : params){             
                 count++;
                 if(count>1){
                     builder.append(",\n"); 
                     builder.append(param.asJavaString(ident));
                 }else{
                     builder.append(param.asJavaString("")); 
                 }                 
              }             
             //builder.append(toString(params, Math.min(params.size(), maxLen)));
             
             builder.append(");");
         }   else{
             builder.append("();").append(" ");
         }
    
//         GLFunction aliasCmd = this.getAlias();
//         if(aliasCmd != null){
//             builder.append("\n/* alias  of "+ name +" */");
//             builder.append(aliasCmd.asJavaString());            
//         }     
    
         return builder.toString();        
    }
    
    private boolean skipPrintAliasOnce = false;
    private boolean printAsCprotoOnce = false;
    
    /**
     * Skip printing alias functions once
     */
    public void skipPrintAliasOnce(){
        skipPrintAliasOnce = true;
    }
    
   /**
    * Print this Function
    */
    public String toString() {
       // final int maxLen = 32;
        StringBuilder sb = new StringBuilder();      
        sb.append("\n");
        if (proto != null) {
            sb.append(proto);
            sb.append(" ");
        }
        if (ptype != null) {         
            sb.append(ptype);
            sb.append(" ");
        } 
        if (name != null) {
            sb.append(name);           
        }
        // param
        if (params != null || params.size() > 0) {
            sb.append(" ( ");
            if (printAsCprotoOnce) {
                boolean useComma = false;
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    GLParam par = params.get(i);
                    useComma = (i + 1 != size);
                    sb.append(par.asCProto(useComma));
                }
                printAsCprotoOnce = false;
            } else {
                sb.append(toString(params, params.size()));
            }
            sb.append("\n\t\t").append(" );\n");
        } else {
            sb.append("(); ").append("\n");
        }
        
        
        GLFunction aliasCmd = this.getAlias();
        if(aliasCmd != null && !skipPrintAliasOnce){
            sb.append("\n// alias  of "+ name +" ");
            sb.append(aliasCmd.toString());            
        }        
        skipPrintAliasOnce = false;
        return sb.toString();
    }
    
    /**
     * print Collections
     * @param collection
     * @param maxLen
     * @return
     */
    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder sb = new StringBuilder();
       // builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                sb.append(" ,");
            sb.append(iterator.next());           
        }
        return sb.toString();
    }
    
    /**
     * Return the C prototype of this function
     * @return cProto
     */
    public String asCProto(){
        skipPrintAliasOnce();
        printAsCprotoOnce = true;
        String cProto = toString();
        return cProto;
    }
    
    /**
     * Return typedef PFN_PROC and optional function prototype declaration; 
     * <pre>
     * Output:
     * typedef void (APIENTRYP PFNGLACTIVETEXTUREPROC)(GLenum texture);
     * 
     *  If addVar is set true, you got the following function declaration.
     *  Please note this is not necessary for core functions, as it's already 
     *  declared  on gl.h , gl2.h , egl.h, etc.
     *   
     * GLAPI PFNGLACTIVETEXTUREPROC mangled_glActiveTexture; 
     * </pre>
     * @param isEGL - set true for E|GL api
     * @param addVar - set true to add declaration of function variable
     * @param mangle - set mangle prefix to function name
     * 
     * @return string with typedef and optional 
     */
    public String asCTypedef(boolean isEGL, boolean addVar, String  mangle){
       // throw new UnsupportedOperationException("Not implemented yet");
        
        StringBuilder sb = new StringBuilder();
        sb.append("typedef ");
        
        if (proto != null) {
            sb.append(proto);
            sb.append(" ");
        }
        if (ptype != null) {         
            sb.append(ptype);
            sb.append(" ");
        } 
        if (name != null) {
            String apientryP = isEGL ? "EGLAPIENTRYP " : "GL_APIENTRYP ";
            sb.append("(").append(apientryP);            
            sb.append(getPFNPROCname());
            sb.append(") ");
        }
        // param
        if (params != null || params.size() > 0) {
            sb.append(" ( ")
              .append(toString(params, params.size()))        
              .append("); ");
        } else {
            sb.append("(void); ");
        }
        
        if(addVar){
           sb.append("\n");
           String apicall = isEGL ? "EGLAPI " :"GL_APICALL ";
           sb.append(apicall);
           if (proto != null) {
               sb.append(proto);
               sb.append(" ");
           }
           if (ptype != null) {         
               sb.append(ptype);
               sb.append(" ");
           } 
           if (name != null) {
               String apientryP = isEGL ? "EGLAPIENTRYP " : "GL_APIENTRYP ";
               sb.append(apientryP).append(" ");  
               if(mangle!=null){
                   sb.append(mangle);
               }
               sb.append(name);
               sb.append("");
           }
           // param
           if (params != null || params.size() > 0) {
               sb.append(" ( ")
                 .append(toString(params, params.size()))        
                 .append("); ");
           } else {
               sb.append("(void); ");
           }
        }
        
        return sb.toString();
    }
    
    
    /**
     * PFN-PROCfy function name.
     * 
     * Adds prefix PFN and sufix PROC to uppercase function name. 
     * 
     * @return string with PFN + name uppercase + PROC
     */
    public String getPFNPROCname(){
        return "PFN" + name.toUpperCase() +"PROC";
    }
    
    /**
     * Return this functions as calling C funtion, i.e., 
     *  with Type Cast applied for each parameter.
     * @return 
     */
    public String asCcallFunction() {
        StringBuilder sb = new StringBuilder(128);         
         sb.append("\n");
         
         // TODO create return type for String and EGL types
         boolean returnString = false;
       //  boolean returnPtr = false;
         
         if (proto != null) {               
             String jniReturn = "";
             String pproto = proto.trim();
              if(pproto.length()>1){
                  if(pproto.contains("void") && pproto.contains("*") ){
                          jniReturn = "return (jlong) "   ;               
                  }else{
                      // string case
                      if (pproto.contains("const") && pproto.contains("char") && pproto.contains("*")){
                          returnString = true;
                          jniReturn =  "\t const char *_txt = (const char *)";
                      }
                  }
              }              
              sb.append(jniReturn);
              //builder.append(ptype);
              sb.append(" ");             
         }
         
         ///////////////////////////////////////
         if (ptype != null) {
          // adjust proto to JNI 
             String pptype = ptype.trim();
             String jniReturn = "";
             
             if(pptype.contains("void")){
                 if(pptype.contains("*")){
                     jniReturn = "   return (jlong)";
                 }
             }else{
                String type =  JavaTypeMap.getJNItype(pptype);
                jniReturn = "  return ("+type+") ";                
             }
             
             sb.append(jniReturn);
             //builder.append(ptype);
             sb.append(" ");
         }
         if (name != null) {
             sb.append(name);
             sb.append("");
         }   
         // parameters
         if (params != null) {
             sb.append("(");          
             
             String ident = getBlankSpaceString(sb.length()-1);             
             int count = 0;
             for(GLParam param : params){
                 String type = param.ptype.trim();
                 String name = param.name;
                 boolean addOffset = param.hasPointer();
                 count++;
                 if(count>1){
                     sb.append(",\n")
                            .append(ident);                    
                 }                 
                 sb.append(" (").append(type).append(") ");
                 
                 if(addOffset){
                   sb.append("(")
                   .append(name)
                   .append(" + ")
                   .append(name)
                   .append(GLParam.OFFSET)
                   .append(")");                  
                   
                 }else{
                     sb.append(name);
                 }                 
              }             
             //builder.append(toString(params, Math.min(params.size(), maxLen)));             
             sb.append(");\n");
         }   else{
             sb.append("();").append("\n");
         }
         
         if(returnString){
             // create string and return it
             sb.append("return (jstring) env->NewStringUTF(_txt);\n");             
             
         }
         
         
//         GLFunction aliasCmd = this.getAlias();
//         if(aliasCmd != null){
//             builder.append("\n/* alias  of "+ name +" */");
//             builder.append(aliasCmd.asJavaString());            
//         }   
         
        return sb.toString();
    }
    
    
    /**
     * Creates a String of blank space
     * @param n length of blank space string
     * @return a blank space string with length n.
     */
    public String getBlankSpaceString(int n){
        StringBuilder sb = new StringBuilder(n);
        
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }        
        return sb.toString();        
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {       
        int result = (name == null) ? 0 : name.hashCode();
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GLFunction other = (GLFunction) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if(o != null && o instanceof GLFunction){
            return this.name.compareTo(((GLFunction)o).name);
        }
        
        return 0;
    }
    
    
}
