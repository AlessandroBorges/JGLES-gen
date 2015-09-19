package gles.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for GL, GL-ES and EGL features. 
 * @author Alessandro Borges
 *
 */
public enum GLFeatureEnum{
        
    //GL
    GL_VERSION_1_0("1.0", 0,"gl"),
    GL_VERSION_1_1("1.1", 0,"gl"),
    GL_VERSION_1_2("1.2", 0,"gl"),
    GL_VERSION_1_3("1.3", 0,"gl"),
    GL_VERSION_1_4("1.4", 0,"gl"),
    GL_VERSION_1_5("1.4", 0,"gl"),
    GL_VERSION_2_0("2.0", 0,"gl"),
    GL_VERSION_2_1("2.1", 0,"gl"),
    GL_VERSION_3_0("3.0", 0,"gl"),
    GL_VERSION_3_1("3.1", 0,"gl"),
    GL_VERSION_3_2("3.2", 0,"gl"),
    GL_VERSION_3_3("3.3", 0,"gl"),
    GL_VERSION_4_0("4.0", 0,"gl"),
    GL_VERSION_4_1("4.1", 0,"gl"),
    GL_VERSION_4_2("4.2", 0,"gl"),
    GL_VERSION_4_3("4.3", 0,"gl"),
    GL_VERSION_4_4("4.4", 0,"gl"),
    GL_VERSION_4_5("4.5", 0,"gl"),
    
    //GL-ES
    GL_VERSION_ES_CM_1_0("1.0", 1,"gles"),
    
    GL_ES_VERSION_2_0("2.0", 3, "gles2"),
    GL_ES_VERSION_3_0("3.0", 3, "gles3"),
    GL_ES_VERSION_3_1("3.1", 3, "gles31"),
    GL_ES_VERSION_3_2("3.2", 3, "gles32"),
    GL_ES_VERSION_3_3("3.3", 3, "gles33"),
    
    //EGL
    EGL_VERSION_1_0("1.0", 13, "egl"),
    EGL_VERSION_1_1("1.1", 13, "egl"),
    EGL_VERSION_1_2("1.2", 13, "egl"),
    EGL_VERSION_1_3("1.3", 13, "egl"),
    EGL_VERSION_1_4("1.4", 13, "egl"),
    EGL_VERSION_1_5("1.5", 13, "egl");   
    
    /**
     * Type enumeration.
     * @see GLFeatureEnum#getType()
     */
   public static final int GL_Type = 0,  
                           GLES1_Type = 1, 
                           GLES2_Type = 2,
                           GLES3_Type = 3,
                           EGL_Type=10;
    
    private float numb;
    private String number;
    private int type;
    private String api;
    
    private GLFeatureEnum(String number, int type, String api){
        this.number = number;
        this.type = type;
        this.api = api;
        numb = Float.parseFloat(number);
    }
    
    /**
     * the feature number
     * @return
     */
    public String getNumber(){
        return number;
    }
    
    /**
     * Get backwards compatible profiles
     * @return List of backwatds compatible profiles
     */
    public List<GLFeatureEnum> getBackwardsList(){
        List<GLFeatureEnum> list = new ArrayList<GLFeatureEnum>();
        
        for(GLFeatureEnum f : GLFeatureEnum.values()){
            if(f.type==this.type && f.numb<= this.numb){
                list.add(f);
            }
        }
        return list;
    }
    
    /**
     * the feature name
     * @return
     */
    public String getName(){
        return this.toString();
    }
    
    public String getApi(){
        return this.api;
    }
    
    public int getType(){
        return this.type;
    }
    
    /**
     * Enumeration for EGL features
     */
    public static GLFeatureEnum[] EGL_FEATURES = { EGL_VERSION_1_0, EGL_VERSION_1_1, 
                                                   EGL_VERSION_1_2, EGL_VERSION_1_3,
                                                   EGL_VERSION_1_4, EGL_VERSION_1_5 };
    /**
     * Enumeration for Desktop GL features
     */
    public static GLFeatureEnum[] GL_FEATURES = { GL_VERSION_1_0, GL_VERSION_1_1,
                                                  GL_VERSION_1_2, GL_VERSION_1_3,
                                                  GL_VERSION_1_4, GL_VERSION_1_5,
                                                  GL_VERSION_2_0, GL_VERSION_2_1,
                                                  GL_VERSION_3_0, GL_VERSION_3_1,
                                                  GL_VERSION_3_2, GL_VERSION_3_3,
                                                  GL_VERSION_4_0, GL_VERSION_4_1,
                                                  GL_VERSION_4_2, GL_VERSION_4_3,
                                                  GL_VERSION_4_4, GL_VERSION_4_5,                                                  
                                                  };
    
    /**
     * Enumeration for GLES1 features
     */
    public static GLFeatureEnum[] GLES1_FEATURES = { GL_VERSION_ES_CM_1_0 };
    
    /**
     * Enumeration for GLES2 features
     */
    public static GLFeatureEnum[] GLES2_FEATURES = { GL_ES_VERSION_2_0, GL_ES_VERSION_3_0, 
                                                     GL_ES_VERSION_3_1, GL_ES_VERSION_3_2 };
    
    /**
     * Enumeration for GLES3 features
     */
    public static GLFeatureEnum[] GLES3_FEATURES = { GL_ES_VERSION_3_0, 
                                                     GL_ES_VERSION_3_1, GL_ES_VERSION_3_2 };
   
}