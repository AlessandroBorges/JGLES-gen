package gles.generator.internal;

import java.util.*;

/**
 * Store all GLenumGroup 
 * @author Alessandro Borges
 *
 */
public class GLenumAll {
    
    private List<GLenumGroup> groups = new ArrayList<GLenumGroup>();
    
    public GLenumAll(){        
    }
    
    /**
     * Add a GLenumGroup to this
     * @param enumGroup
     */
   public void addGroup(GLenumGroup enumGroup){
       groups.add(enumGroup);
   }
   
   /**
    * Search for a GLenum across all GLenumGroup stored
    * @param enumName GLenum name
    * @return GLenum instance or null if not found
    */
   public GLenum getEnum(String enumName){
       
       for(GLenumGroup group :  groups){
           GLenum enume = group.get(enumName);
           if(enume != null){
               return enume;
           }
       }       
       return null;
   }
   
   /**
    * Get all GLenumGroups
    * @return
    */
   public List<GLenumGroup> getGroups(){
       return this.groups;
   }
   
   
}
