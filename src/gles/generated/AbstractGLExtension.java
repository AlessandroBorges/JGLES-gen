/**
 * 
 */
package gles.generated;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.*;

/**
 * Abstract class for GLExtension
 * @author Alessandro Borges
 *
 */
public abstract class AbstractGLExtension {

    private boolean available;
    private String name;
    
    protected static List<String> glExtensions;
    protected static List<String> eglExtensions;
    
    
    /**
     * 
     */
    protected AbstractGLExtension() {
       if(glExtensions == null){
           loadExtensionNames();
       }
    }
    
    /**
     * Ctor
     * @param name - extension name
     */
    protected AbstractGLExtension(String name){
        this();
        this.name = name.trim().toUpperCase();
        available = supportedExtension(name);
    }
    
    /**
     * Return true if this extensions is available in this run.
     * @return true or false
     */
    public boolean isAvailable(){
        return available;
    }
    
    /**
     * Runtime check for operation
     * @throws UnsupportedOperationException
     */
    protected final void check() throws UnsupportedOperationException{
        if(available) 
            return;
        throw new UnsupportedOperationException ("Extension " + name + " is not available");        
    }
    
    /**
     * Check if a extensions is supported
     * @param extensionName - extension name
     * @return true if OpenGL does support extensionName.
     */
    protected static boolean supportedExtension(String extensionName){
        String name = extensionName.trim().toUpperCase();
        if(glExtensions != null && glExtensions.contains(name))
            return true;
        
        if(eglExtensions != null && eglExtensions.contains(name))
            return true;
        
        return false;        
    }
    
    
    
    /**
     * 
     */
    private void loadExtensionNames(){
        EGL10 egl = (EGL10)EGLContext.getEGL();
        String eglAll =  egl.eglQueryString(egl.eglGetCurrentDisplay(), EGL10.EGL_EXTENSIONS);
        String[] eglExtNames = split(eglAll);
        for (int i = 0; i < eglExtNames.length; i++) {
            eglExtNames[i]  = eglExtNames[i].toUpperCase();            
        }
        eglExtensions = Collections.unmodifiableList(Arrays.asList(eglExtNames));
        
        
        EGLContext ctx = egl.eglGetCurrentContext(); 
        GL10 gl = (GL10) ctx.getGL();
        String extGLall = gl.glGetString(GL11.GL_EXTENSIONS);
        String[] glExtNames = split(extGLall);
        for (int i = 0; i < glExtNames.length; i++) {
            glExtNames[i]  = glExtNames[i].toUpperCase();            
        }
        glExtensions = Collections.unmodifiableList(Arrays.asList(glExtNames));
    }
    
    
    
    /**
     * extract names from a comma separated list.
     * @param src - names separated by space
     * @return String[]
     */
    private static String[] split(String src){
       while(src.contains("  ")){
          src = src.replaceAll("  "," ");
       }
       String[] arr =  src.split(" ");
       for (int i = 0; i < arr.length; i++) {
        arr[i] = arr[i].trim();        
       }
       Arrays.sort(arr);       
       return arr;
    }
    
    

}
