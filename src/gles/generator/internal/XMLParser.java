package gles.generator.internal;

import gles.generator.internal.GLFeature.Require;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

/**
 * Class to Parse Khronos XML files, as egl.xml and gl.xml.
 * 
 * 
 * @see https://www.khronos.org/opengl/
 * @see https://www.khronos.org/egl
 * @see https://www.khronos.org/opengles/
 * 
 * @author Alessandro Borges
 *
 */
public class XMLParser {
    
    private static String ENUMS = "enums", ENUM = "enum";
    private static String COMMANDS = "commands", COMMAND = "command";
    private static String PROTO = "proto", NAME = "name", PARAM = "param", PTYPE="ptype";
    private static String ALIAS = "alias";
    private static String GROUPS = "groups", GROUP = "group";
    
    private static String FEATURES = "feature", REQUIRE = "require";
    private static String EXTENSIONS = "extensions", EXTENSION = "extension";
    
    protected List<GLExtension> extensions = new ArrayList<GLExtension>(1024);
    protected GLExtension curGLext = null;
    
    /*
     * where to store the GL/GLES/EGL commands
     */
    public GLcmdSet cmdSet = new GLcmdSet();
    
    private String xml = null;
    
    protected GLFeature curFeature ;
    
    /**
     * Place to store GL/GLES/EGL enumerations
     */
   // protected List<GLenumGroup> enums = new ArrayList<GLenumGroup>();
    public GLenumAll enumAll = new GLenumAll();
    
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        parser.process("gl.xml");
        //parser.print();
        
        GLenumAll enumAll = parser.enumAll;
        Set<GLExtension> glesExt = parser.filterExtensions(false, true, true, true);
        
        for (GLExtension glExt : glesExt) {
            glExt.setCommands(parser.cmdSet);
            glExt.setEnumerations(enumAll);
        }        
        //System.out.println(parser.toString(glesExt, 20));
        
        // Extensions as JavaString
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(GLExtension.GLES1);
            String extensionsAsJavaString = glExt.asJavaString();
            System.out.println(extensionsAsJavaString);
        }
        
        System.out.println("///////////////////////////////////");
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(GLExtension.GLES1);
            String cFPNProc = glExt.asCfunctionPointers(true);
            System.err.println(cFPNProc);
        }
        
        System.out.println("///////////////////////////////////");
        for (GLExtension glExt : glesExt) {
            glExt.setJavaAPIMode(GLExtension.GLES1);
            String txt = glExt.asCfunctionLoaders(true);
            System.out.println(txt);
        }
        
        System.out.println("///////////////////////////////////");
//        for (GLExtension glExt : glesExt) {
//            glExt.setJavaAPIMode(GLExtension.GLES1);
//            String txt = glExt.asCforAllLoaders("GLES1");
//            System.out.println(txt);
//        }

    }// main

    /**
     * get all enumerations
     * @return
     */
//    public Map<String, GLenum> getGLEnumAsMap(){
//        Map<String, GLenum> map = new HashMap<String, GLenum>();
//        
//        for (GLenumGroup gLenums : enums) {            
//            map.putAll(gLenums.enums);
//        }
//        return map;
//    }
    
    /**
     * Filter Extensions by API
     * @param gl
     * @param gles1
     * @param gles2
     * @param egl
     * @return set of extensions
     */
    public Set<GLExtension> filterExtensions(boolean gl, boolean gles1, boolean gles2, boolean egl) {
        Set<GLExtension> set = new TreeSet<GLExtension>();

        for (GLExtension glExt : extensions) {           
            if (egl & glExt.isEGL()) {
                set.add(glExt);
            }

            if (gl && glExt.isGL()) {
                set.add(glExt);
            }
            if (gles1 && glExt.isGLES1()) {
                set.add(glExt);
            }

            if ((gles2 && glExt.isGLES2())) {
                set.add(glExt);
            }

        }
        return set;
    }
    
    
    
    
    public static String printExtensionJava(GLExtension ext, GLcmdSet cmdSet, Map<String, GLenum> enumMap){
        String s=null;
        
        
        
        return s;
    }
    
    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
       // builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(" ,");
            builder.append(iterator.next());
           // builder.append("\n");
        }
       // builder.append("]\n");
        return builder.toString();
    }
    
    /**
     * Ctor
     */
    public XMLParser() {
    }

    public void process(String file) {
        try {
             // read twice ?
            xml = readUsingFiles(file);            
            File inputFile = new File(file);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new XMLParser.UserHandler();
            saxParser.parse(inputFile, userhandler);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get reading XML source
     * @return
     */
    public String getReadingXML(){
        return xml;
    }
    
    private static String readUsingBufferedReader(File fileName) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(fileName));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }
        //delete the last ls
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
 
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));        
    }
    
   

    public void print(){
        System.out.println(enumAll);
        System.out.println(cmdSet);
        
        System.out.println("Extensions \n" + extensions);
    }
    
    private void processGL(String uri, String localName, String qName, Attributes attributes) {

    }

    /**
     * Class to process GL.xml
     * @author Alessandro Borges
     *
     */
     class UserHandler extends DefaultHandler {
        boolean isParam = false;
        boolean isProto = false, isProtoName = false, isProtoPtype = false;
        boolean isParamType = false;
        boolean isParamName = false;        
        boolean isAlias = false;
        
        boolean isExtension = false;        
        boolean isRequire = false;
        
        String[] ignoreList = { GROUPS, GROUP };
        
        String mode = null;
        String cmdMode = null;

       
        GLenumGroup currentGLEnums;
        GLFunction curGLcommand;
        GLParam curGLparam;
        GLFeature.Require curRequire;
        GLExtensionRequire curExtRequire;
        
        int countCmd = 0;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            //System.err.println("qname: " + qName);   
            if (qName.equalsIgnoreCase(ENUMS)) {
                mode = ENUMS;
                currentGLEnums = new GLenumGroup(attributes);
                enumAll.addGroup(currentGLEnums);
                return;
            } // ENUMS
            if (mode == ENUMS) {
                if (qName.equalsIgnoreCase(ENUM)) {
                    if (mode == ENUMS) {
                        GLenum glEnum = new GLenum(attributes);
                        currentGLEnums.add(glEnum);
                    }
                    return;
                }
            }
            if (qName.equalsIgnoreCase(COMMANDS)) {
                mode = COMMANDS;
                return;
            } 
            
            if(qName.equalsIgnoreCase(EXTENSIONS)){
                mode = EXTENSIONS;
                return;
            }
            
            if(mode == EXTENSIONS){
                
                if(qName.equalsIgnoreCase(EXTENSION)){
                    curGLext = new GLExtension();
                    curGLext.name = attributes.getValue(NAME);
                    curGLext.supportedAPI = attributes.getValue("supported");
                    extensions.add(curGLext);
                    return;
                }
                
                if(qName.equalsIgnoreCase(REQUIRE)){
                    isRequire = true;
                    curExtRequire = new GLExtensionRequire();
                    curGLext.add(curExtRequire);
                    curExtRequire.process(attributes);
                }
                
                if(isRequire && qName.equalsIgnoreCase(ENUM)){
                    String enumName = attributes.getValue(NAME);
                    curExtRequire.addEnumName(enumName);
                    return;
                }
                
                if(isRequire && qName.equalsIgnoreCase(COMMAND)){
                    String cmdName = attributes.getValue(NAME);
                    curExtRequire.addCmdName(cmdName);
                    return;
                }
                return;
                
            }// EXTENSIONS
            ////////////////////////////////////////////////////////////////////
            ///
            ////////////////////////////////////////////////////////////////////
            
            if(mode==COMMANDS){                
                if (qName.equalsIgnoreCase(COMMAND)) {
                    curGLcommand = new GLFunction(); 
                    cmdMode = COMMAND;
                    ++countCmd;
                    //System.out.print("Processing GL/EGL command #" + (countCmd) +" ... ");
                    return;
                }   
                
                if(qName.equalsIgnoreCase(ALIAS)){
                    String aliasName = attributes.getValue(NAME);
                    curGLcommand.alias = aliasName;
                    return;
                }
                
                if (qName.equalsIgnoreCase(PROTO)) {                    
                    isProto = true;
                    return;
                }
                
                
                if(qName.equalsIgnoreCase(PARAM)){                     
                    isParam = true;
                    String group = attributes.getValue(GROUP);
                    curGLparam = new GLParam();
                    curGLparam.group = group;
                    curGLcommand.add(curGLparam); 
                    return;
                }               
                
                if(isProto && qName.equalsIgnoreCase(NAME)){
                    isProtoName = true;
                    return;
                }
                
                if(isProto && qName.equalsIgnoreCase(PTYPE)){
                    isProtoPtype = true;
                    return;
                }
                
                if(isParam && qName.equalsIgnoreCase(NAME)){
                   isParamName = true;
                   return;
                }
                
                if(isParam && qName.equalsIgnoreCase(PTYPE)){
                    isParamType= true;
                    return;
                }       
                return;
            }// commands
            
            ////////////////////////////////////////////////
            /// FEATURES
            //////////////////////////////////////////////
            if(qName.equalsIgnoreCase(FEATURES)) {
                mode = FEATURES;
                String api = attributes.getValue("api");
                String name = attributes.getValue(NAME);
                String number = attributes.getValue("number");
                String comment = attributes.getValue("comment");
                
                curFeature = GLFeature.getGLFeature(name);
                curFeature.api = api;
                curFeature.number = number;
                curFeature.comment = comment;                
                return;
            }
            
            if(mode==FEATURES){                
                if(qName.equalsIgnoreCase(REQUIRE)){
                    isRequire = true;
                    curRequire =  curFeature.new Require();
                    curFeature.add(curRequire);                    
                    String comments = attributes.getValue("comment");
                    String profile = attributes.getValue("profile");
                    curRequire.comment = comments;
                    curRequire.profile = profile;
                    return;
                }
                
                if (isRequire) {
                    if (qName.equalsIgnoreCase(ENUM)) {
                        String name = attributes.getValue(NAME);
                        curRequire.addEnumName(name);
                        return;
                    }

                    if (qName.equalsIgnoreCase(COMMAND)) {
                        String name = attributes.getValue(NAME);
                        curRequire.addCmdName(name);
                        return;
                    }
                }// require                                
            }            

        } ///////////////////

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            
            
           
            
            if (qName.equalsIgnoreCase(COMMANDS)) {
                cmdMode = null;
                mode = null;
            }
            //////////////////////////////////////////////////////////
            
             if(mode == EXTENSIONS){                
                if(qName.equalsIgnoreCase(EXTENSION)){
                    curGLext = null;
                }
                
                if(qName.equalsIgnoreCase(REQUIRE)){
                    isRequire = false;
                } 
            }// EXTENSIONS
            
             if(qName.equalsIgnoreCase(EXTENSIONS)){
                 mode = null;
             }
            /////////////////////////////////////////////////////////
            
            if(mode==COMMANDS){                
//                if (qName.equalsIgnoreCase(PARAM)) { 
//                    isParam = false;
//                    curGLparam = null;
//                } 
                
                if (qName.equalsIgnoreCase(PROTO)) {
                    isProto = false;
                }
                               
                if(isProto && qName.equalsIgnoreCase(NAME)){
                    isProtoName = false;
                }
                
                // PARAM may have symbol before and after PTYPE 
                if(isProto && qName.equalsIgnoreCase(PTYPE)){
                    isProtoPtype = false;                   
                }
                
                if(isParam && qName.equalsIgnoreCase(NAME)){
                   isParamName = false;
                }
                
                if(isParam && qName.equalsIgnoreCase(PTYPE)){
                    isParamType= false;
                } 
                
                if(isParam && qName.equalsIgnoreCase(PARAM)){
                    isParam = false;
                    isParamType= false;
                    isParamName = false;
                    curGLparam = null;
                } 
                
                if(qName.equalsIgnoreCase(COMMAND)){
                    
                  if(curGLcommand != null){
                    cmdSet.addGLcmd(curGLcommand);                  
                    //System.out.println("#\t Processed GL/EGL command #" + (countCmd) +" "+ curGLcommand.name);
                    curGLcommand = null;
                    }else{
                        System.out.println("#\t Error!");
                    }
                  isParam = false;
                  isParamName = false;
                  isParamType = false;
                  isProto = false;
                  isProtoName=false;
                  isProtoPtype = false;
                  curGLparam= null;
                }
                
                
            }//COMMANDS
            
            if(qName.equalsIgnoreCase(FEATURES)) {
                mode = null;
                isRequire = false;
            }            
            
            if(mode==FEATURES){
                if(qName.equalsIgnoreCase(REQUIRE)){
                    isRequire = false;
                }
            }
            
            if (qName.equalsIgnoreCase(ENUMS)) {
                mode = null;
                //System.out.println(currentGLEnums);
                return;
            }
            
            if(qName.equalsIgnoreCase(EXTENSIONS)){
                mode = null;
                return;
            }
            
            
            
        }

        /**
         * Characters
         */
        @Override
        public void characters(char ch[], int start, int length)
            throws SAXException {
            
            if(mode==COMMANDS){
                String txt = new  String(ch, start, length);
                
                               
                if(isParamName){                  
                    curGLparam.name = txt;
                }
                
                if(isParam && !isParamType && !isParamName){
                    if(curGLparam==null){
                        System.out.println("issue");
                    }else
                        curGLparam.ptype += " " + txt;
                }
                
                if(isParamType){   
                    
                    curGLparam.ptype += " " + txt;
                }
                
                if(isProtoName){                   
                    curGLcommand.name = txt;
                }
                
                if(isProtoPtype){                   
                    curGLcommand.ptype= txt;
                }
                
                if(isProto && !isProtoName && !isProtoPtype){
                    curGLcommand.proto = txt;
                }
                
            }
              
        }

        /**
         * 
         * @param att
         */
        public void printAtt(Attributes att) {
            int size = att.getLength();
            for (int i = 0; i < size; i++) {
                String nm = att.getLocalName(i);
                String vl = att.getValue(i);
                System.out.println(nm + " = " + vl);
            }
        }

    }
}
