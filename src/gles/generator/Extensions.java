/**
 * 
 */
package gles.generator;

import java.util.HashMap;
import java.util.Map;

//import sun.nio.cs.ext.PCK;

/**
 * API to load and used GL and EGL extensions
 * @author Alessandro Borges
 *
 */
public class Extensions {
    /**     
     * name of this package
     */
    private String thisPkg;
    
    /**
     * Maps String to GlesExtension
     */
    protected Map<String,GlesExtension> lazyLoadedExt = new HashMap<String, GlesExtension>();    
      
    
    /**
     * loaded list of supported extensions and corresponding class names
     * keys are lower case.
     * it must be in txt file in this very same package.
     */
    protected Map<String,String> extClassNames;
    
    /**
     * Ctor
     */
    public Extensions(){
        thisPkg =  this.getClass().getPackage().getName();
        loadMapNames();
    }
    
    private void loadMapNames(){
        extClassNames = new HashMap<String, String>();
        // loads
    }
    
    /**
     * Perform lazy loading of Extensions
     */
    public GlesExtension getExtension(String extensionName){
        try{
            String name = extensionName.trim().toLowerCase();
            String cName = extClassNames.get(name);
            String className = thisPkg + "." +  cName;
            @SuppressWarnings("unchecked")
            Class<GlesExtension> clazz = (Class<GlesExtension>) Class.forName(className);
            GlesExtension ext =  clazz.newInstance();
            return ext;
        }catch(Exception e){}
        
        return null;
        
    }
    
    /**
     *  //check it against glGetString(GL_Extensions)
     * @param extensionName
     * @return
     */
    public boolean isExtSupported(String extensionName){
       
        
        return false;
    }
    
 
}
