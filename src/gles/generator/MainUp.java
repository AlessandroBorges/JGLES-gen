/**
 * 
 */
package gles.generator;

import java.util.*;

import gles.generator.GLmain.GL_API;

/**
 * High level calls to Main
 * @author Alessandro Borges
 *
 */
public class MainUp {

    
    protected GLmain main;
    
    /**
     * 
     */
    public MainUp(String path, GL_API api) { 
        main = new GLmain(path, api, true);        
    }
    
    /**
     * 
     */
    public void loadXML(){
        main.go();
    }
    
    
    /**
     * Generate classes for extensions; 
     * @param extensionsNames - list of extensions names
     * @return Map of extension to java class source code.
     */
    public Map<String,String> generateExtensionClasses(String... extensionsNames){
        Map<String,String> map = new TreeMap<String, String>();
        
         for (String extName : extensionsNames) {
            extName = extName.trim();
            String src = main.asJavaClassExt(extName, extName);
            if(src != null && src.length()>10)
                map.put(extName, src);
        }             
        return map;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
       MainUp up = new MainUp("", GL_API.GLES1);
       up.loadXML();
       
       Map<String, String> map = up.generateExtensionClasses("GL_EXT_multisampled_render_to_texture");
       Set<String> keys = map.keySet();
       for (String extName : keys) {
        String src = map.get(extName);
        
        System.out.println("Extension: "+extName);
        System.out.println("source: \n"+src);
        
    }
       

    }

}
