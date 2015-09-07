package gles.generator.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerations for GL, GL-ES and EGL features. 
 * @author Alessandro Borges
 *
 */
public enum FeatureNameEnum{
        
    //GL
    GL_VERSION_1_0("1.0", 0),
    GL_VERSION_1_1("1.1", 0),
    GL_VERSION_1_2("1.2", 0),
    GL_VERSION_1_3("1.3", 0),
    GL_VERSION_1_4("1.4", 0),
    GL_VERSION_1_5("1.4", 0),
    GL_VERSION_2_0("2.0", 0),
    GL_VERSION_2_1("2.1", 0),
    GL_VERSION_3_0("3.0", 0),
    GL_VERSION_3_1("3.1", 0),
    GL_VERSION_3_2("3.2", 0),
    GL_VERSION_3_3("3.3", 0),
    GL_VERSION_4_0("4.0", 0),
    GL_VERSION_4_1("4.1", 0),
    GL_VERSION_4_2("4.2", 0),
    GL_VERSION_4_3("4.3", 0),
    GL_VERSION_4_4("4.4", 0),
    GL_VERSION_4_5("4.5", 0),
    
    //GL-ES
    GL_VERSION_ES_CM_1_0("1.0", 1),
    
    GL_ES_VERSION_2_0("2.0", 2),
    GL_ES_VERSION_3_0("3.0", 2),
    GL_ES_VERSION_3_1("3.1", 2),
    GL_ES_VERSION_3_2("3.2", 2),
    GL_ES_VERSION_3_3("3.3", 2),
    
    //EGL
    EGL_VERSION_1_0("1.0", 3),
    EGL_VERSION_1_1("1.1", 3),
    EGL_VERSION_1_2("1.2", 3),
    EGL_VERSION_1_3("1.3", 3),
    EGL_VERSION_1_4("1.4", 3),
    EGL_VERSION_1_5("1.5", 3);
    
    
    
    private static final int GL = 0,  GLES1 = 1, GLES2 = 2, EGL=3;
    private float numb;
    private String number;
    private int type;
    
    private FeatureNameEnum(String number, int type){
        this.number = number;
        this.type = type;
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
    public List<FeatureNameEnum> getBackwardsList(){
        List<FeatureNameEnum> list = new ArrayList<FeatureNameEnum>(20);
        
        for(FeatureNameEnum f : FeatureNameEnum.values()){
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
}