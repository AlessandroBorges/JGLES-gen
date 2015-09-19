package gles.generator.internal;
import gles.generator.GLFeatureEnum;

import java.util.*;

public class TestEnum {

    public static void main(String[] args) {
       printbackwards(GLFeatureEnum.EGL_VERSION_1_4);
       printbackwards(GLFeatureEnum.GL_VERSION_1_4);
       printbackwards(GLFeatureEnum.GL_ES_VERSION_3_0);
       printbackwards(GLFeatureEnum.GL_VERSION_ES_CM_1_0);

    }
    
    public static void printbackwards(GLFeatureEnum n){
        List<?> list = n.getBackwardsList();
        System.out.println("\nBackward list of: " + n + " is:\n\t" + Arrays.toString(list.toArray())); 
    }

}
