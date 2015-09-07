package gles.generator.internal;
import java.util.*;

/**
 * Class to store all Features
 * @author Alessandro Borges
 *
 */
public class GLFeature 
  // extends GLExtension
   implements Comparable<GLFeature> 
   {
    
    private FeatureNameEnum featureName;
    public static GLFeatureMap mapFeatures = new GLFeatureMap();
    
    public String api, name,  number, comment;
    public List<GLFeature.Require> listRequires = new ArrayList<GLFeature.Require>();
    
    
    
    private List<GLFunction> allFunctions;
   
    
    private boolean hasCMdSet = false;
    private boolean hasEnumSet = false;
    /**     
     * store features
     *
     */
    
    public void add(Require require){
        listRequires.add(require);
    }
    
    /**
     * Get all Requires from this feature
     * @return
     */
    public List<GLFeature.Require> getRequires(){
         return listRequires;
    }
    
    /**
     * Get or create GLfeature
     * @param name
     * @return
     */
    public static GLFeature getGLFeature(String name){
        GLFeature feature = mapFeatures.get(name);
        if(feature == null){
            feature = new GLFeature();
            feature.name = name;
            mapFeatures.put(name,feature);
        }
        
        return feature;        
    }
    
    /**
     * get all enumeration names from this Feature, across all requires
     */
    public List<String> getAllEnumNames(){
        List<String> allEnum = new ArrayList<String>();
        for(GLFeature.Require req : listRequires ){
            List<String> list = req.enumNames;
            allEnum.addAll(list);            
        }        
       return allEnum;
    }
    
    /**
     * get all Command/Function names from this Feature, across all requires
     */
    public List<String> getAllCmdNames(){
        List<String> allCmd = new ArrayList<String>();
        for(GLFeature.Require req : listRequires ){
            List<String> list = req.cmdNames;
            allCmd.addAll(list);            
        }        
       return allCmd;
    }
    
    /**
     * get ALL functions and alias
     * @return
     */
    public List<GLFunction> getAllFunctions() {
        if (allFunctions != null)
            return allFunctions;

        List<GLFunction> functions = new ArrayList<GLFunction>(256);
        for (Require require : listRequires) {

            if (require.commands == null || require.commands.size() == 0) {
               continue;
            }
            int size = require.commands.size();

            for (int i = 0; i < size; i++) {
                GLFunction function = require.commands.get(i);
                functions.add(function);
                // the alias
                GLFunction alias = function.getAlias();
                if (alias != null) {
                    // System.out.println("// Added alias: " + alias.name +
                    // "\t to \t"+ function.name);
                    functions.add(alias);
                }
            }
        }
        allFunctions = functions;
        return allFunctions;
    }
    
    /**
     * all Enum
     */
    private List<GLenum> allEnum = null; 
    
    /**
     * get ALL functions and alias
     * @return
     */
    public List<GLenum> getAllEnumerations() {
        if (allEnum != null)
            return allEnum;

        List<GLenum> enums = new ArrayList<GLenum>();
        for (Require require : listRequires) {

            if (require.enumerations == null || require.enumerations.size() == 0) {
                continue;
            }
            int size = require.enumerations.size();
            for (int i = 0; i < size; i++) {
                GLenum _enum = require.enumerations.get(i);
                enums.add(_enum);
            }
        }
        allEnum = enums;
        return allEnum;
    }
    
    /**
     * get all Type names from this Feature, across all requires
     */
    public List<String> getAllTypeNames(){
        List<String> allTypes = new ArrayList<String>();
        for(GLFeature.Require req : listRequires ){
            List<String> list = req.typeNames;
            allTypes.addAll(list);            
        }        
       return allTypes;
    }
    
    
   
    /**
     * Set commands for Core features
     * @param cmds
     */
    public void setCommands(GLcmdSet cmds){
        hasCMdSet = true;
       // List<String> cmd_names = getAllCmdNames();
        
        for(Require req : listRequires ){
            List<String> cmd_names = req.cmdNames;
            
            for(String cmdName : cmd_names ){            
                GLFunction cmd =  cmds.getGLFunction(cmdName);
                req.commands.add(cmd);
             }  
        }        
    }
    
    
    
    
    /**
     * Get ALL Java functions for a given API.<br>
     * To get functions from a require, use 
     *  Require.getJavaMapFunctions
     * 
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return 
     * 
     * @see Require#getJavaMapFunctions()
     */
    public Map<String, GLFunction> getJavaStringFunctionsMap(String api) {
        Map<String, GLFunction> functions = new HashMap<String, GLFunction>();

        for (Require require : listRequires) {
            Map<String, GLFunction> map = require.getJavaMapFunctions();
            functions.putAll(map);
        }
        return functions;
    }
    
    
    
   
    
    
    /**
     * 
     * Set all GL enumerations across all Requires
     *   
     * 
     * @param enumAll all enumerations
     */
    public void setEnumerations(GLenumAll enumAll){  
        hasEnumSet = true;
        
        for(Require req : listRequires ){
            List<String> enum_names = req.enumNames;
            
            for(String enumName : enum_names ){            
                GLenum glEnum = enumAll.getEnum(enumName);
                if(glEnum==null){
                    System.err.printf("GLfeature error : GLenum %s not found!",  enumName);
                }else{
                    req.enumerations.add(glEnum);
                }
             }  
        } 
    }
    
    
    
    /**
     * Get available GLFeatures names.
     * @return list of all loaded GLFeatures names as GL_ES_VERSION_2_0, GL_VERSION_4_2, etc
     * 
     */
    public static Set<String> getGLFeaturesNames(){
       Set<String> keySet = mapFeatures.keySet();
       Set<String> list = new TreeSet<String>(keySet);
       return list;       
    }
    
    /**
     * Get Feature by name
     * @param featureName - FEATURE_NAME to query
     * @return null or GLFeature instance, if available
     */
    public static GLFeature getGLFeature(FeatureNameEnum featureName){
        String featName = featureName.getName();
        
        Set<String> keySet = mapFeatures.keySet();
        for (String name : keySet) {
            if(featName.equalsIgnoreCase(name)){
                return mapFeatures.get(name);
            }            
        }        
        return null;
    }
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 20;
        StringBuilder builder = new StringBuilder();
        builder.append("GLFeature [");
        if (api != null) {
            builder.append("api=");
            builder.append(api);
            builder.append("\n ");
        }
        if (name != null) {
            builder.append("name=");
            builder.append(name);
            builder.append("\n ");
        }
        if (number != null) {
            builder.append("number=");
            builder.append(number);
            builder.append("\n ");
        }
        if (comment != null) {
            builder.append("comment=");
            builder.append(comment);
            builder.append("\n ");
        }
        if (listRequires != null) {
            builder.append("listRequires=");
            builder.append(listRequires.subList(0, Math.min(listRequires.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }

    private String apiMode;
    /**
     * set API mode for printing
     * @param api
     */
    public void setJavaAPIMode(String api){
        this.apiMode = api;
    }
    
    /**
     * Print this  as JavaString
     */
    public String asJavaString(){
        if(apiMode==null) 
            throw new UnsupportedOperationException("apiMode not set. Please call setJavaAPIMode(api)");
        
        //TODO check apiModeValues
        
        //enumeration section
        StringBuilder sb = new StringBuilder(2*1024);
        sb.append("    /**\n");
        sb.append("     * Core : ").append(this.name).append("\n");
        sb.append("     */\n");
        
        
        List<Require> requires = listRequires;// getRequires(apiMode);
      //ENUMS
        for(Require req : requires){
            String javaEnum = req.getJavaEnums();//getJavaEnum(apiMode);            
            sb.append(javaEnum);
        }

        sb.append('\n');
       
        List<String> jFuncStringList = new ArrayList<String>();
        // function section
        for (Require require : requires) {
            sb.append("    /* Core Require ");
                if(require.profile != null){
                    sb.append(", profile: ").append(require.profile);
                } 
                if(require.comment != null){
                    sb.append(" comment: ").append(require.comment);
                }
            
            sb.append("   */\n");
            
            
            Map<String, GLFunction> mapDatabase = require.getJavaMapFunctions();//getJavaStringFunctionsMap(apiMode);
            List<GLFunction> funcs = require.getFunctions();
            
            for(GLFunction func : funcs){
                jFuncStringList = GLExtension.getJavaStringFunction(func, mapDatabase, jFuncStringList);
                String cProto = func.asCProto();
                String cCall = func.asCcallFunction();
                
                // loop on possible java versions of this func
                for(String asJavaString : jFuncStringList){
                    sb.append("\n")
                    .append(" /**\n")
                    .append("  *  Core: ").append(this.name == null?  " ? ": this.name ).append('\n');
                     
                    if(require.comment != null)
                        sb.append("  *  Require: ").append(require.comment).append('\n');
                    
                    if(require.profile != null)
                        sb.append("  *  Profile: ").append(require.profile).append('\n');
                    
                    if(func.comment != null)
                        sb.append("  * ").append(func.comment==null?  " ": func.comment ).append('\n');
                    
                    sb   .append("  *  C Prototype:")
                    .append(GLExtension.addIdentLines(cProto,"* "))   // create function to add * after new line
                    .append("\n  **/ \n");
                    
                    sb.append(" public final native static ");
                    sb.append(asJavaString)
                    .append("/*\n")
                    .append("\t // jnigen - native goes here")
                    .append(GLExtension.addIdentLines(cCall,"\t"))
                    .append("   */\n");            
                }                
            }            
        }// for require
        
        return sb.toString();
    }
    
    
    
    
    
   
    /**
     * TODO return a List !
     * 
     * Get Java enumerations for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public String getJavaEnum(){
        String enums = "";
        
        for(Require require : listRequires){
            enums += require.getJavaEnums();
        }    
        
        return enums;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * class to store Requirements
     * @author Alessandro Borges
     *
     */
    public class Require 
    // extends GLExtensionRequire    
    {
        public String comment;
        public String profile;
        public List<String> enumNames = new ArrayList<String>();
        public List<String> cmdNames = new ArrayList<String>();
        public List<String> typeNames = new ArrayList<String>();
        
     // TODO move it to Require
        public List<GLFunction> commands =  new ArrayList<GLFunction>(400);
        public List<GLenum> enumerations =  new ArrayList<GLenum>(1024);
       
       
                        
        public void addEnumName(String _enum){
            enumNames.add(_enum);
        }
        
        public void addCmdName(String _cmd){
            cmdNames.add(_cmd);
        }
        
        public void addTypeName(String _type){
            typeNames.add(_type);
        }
        
        /**
         * Return GLEnum of this extensions as a JavaString
         * @return
         */
        public String getJavaEnums(){
            StringBuilder sb = new StringBuilder();
            if (this.enumerations != null && enumerations.size()>0) {
                sb.append("    // enumerations ");
                
                if(comment != null) 
                    sb.append(this.comment);
                if(this.profile != null)
                    sb.append(" Profile: ").append(this.profile);
                
                sb.append("\n");
                
                for(GLenum glObject : enumerations){
                 sb.append(glObject.asJavaString());   
                }            
                sb.append("\n");
            }        
           return sb.toString();        
        }
        
        /**
         * Return the functions as Java methods, with related GLFunction attached
         * @return String[] with one command per position. 
         */
        public Map<String,GLFunction> getJavaMapFunctions() {
            Map<String,GLFunction> mapFunctions = new HashMap<String, GLFunction>(8);
            List<GLFunction> commands = getFunctions();
            if (commands == null || commands.size() == 0) {
                return mapFunctions;
            }
            // test it
            List<GLFunction> allFunctions =  getAllFunctions();
            int size = allFunctions.size();
            
            for (int i = 0; i < size; i++) {
                GLFunction function = allFunctions.get(i);            
                // process function
                GLExtensionRequire.processFunctionJava(function, mapFunctions);            
            }
            return mapFunctions;
        } ////////////////
        
               
        /**
         * return a list of Core enums<br>
         * Call {@link #setEnumerations(GLenumAll)} before calling this method.
         * @return list of enumerations
         */
        public List<GLenum> getEnumerations(){
            if(!hasEnumSet){
                throw new UnsupportedOperationException("Cmd not set. Call setEnumerations(GLenumAll) before");
            }
            return enumerations;
        }

        
        
        /**
         * return a list of Core commands / functions.<br>
         * Call {@link #setCommands(GLcmdSet)} before calling this
         * @return list of GLFunction
         */
        public List<GLFunction> getFunctions(){
            if(!hasCMdSet){
                throw new UnsupportedOperationException("Cmd not set. Call setCommands(GLcmdSet) before");
            }
            return commands;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            final int maxLen = 2000;
            return "\n /**\n"
                    + (comment != null ? " * comment=" + comment + "\n" : " *\n")
                    + (profile != null ? " * profile=" + profile + "\n" : " *\n")
                    + (enumNames != null && cmdNames.size() > 0 ? "enumNames=" + enumNames.subList(0, Math.min(enumNames.size(), maxLen))
                            + ", " : "")
                    + (cmdNames != null  && cmdNames.size() > 0? "cmdNames=" + cmdNames.subList(0, Math.min(cmdNames.size(), maxLen)) : "")
                    + "]";
        }
    }



    public int compareTo(GLFeature o) {
        if(o != null)
            return this.name.compareToIgnoreCase(o.name);
        return 0;
    }
}
