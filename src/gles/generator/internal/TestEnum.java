package gles.generator.internal;
import java.util.*;

public class TestEnum {

    public static void main(String[] args) {
       printbackwards(FeatureNameEnum.EGL_VERSION_1_4);
       printbackwards(FeatureNameEnum.GL_VERSION_1_4);
       printbackwards(FeatureNameEnum.GL_ES_VERSION_3_0);
       printbackwards(FeatureNameEnum.GL_VERSION_ES_CM_1_0);

    }
    
    public static void printbackwards(FeatureNameEnum n){
        List<?> list = n.getBackwardsList();
        System.out.println("\nBackward list of: " + n + " is:\n\t" + Arrays.toString(list.toArray())); 
    }

}
