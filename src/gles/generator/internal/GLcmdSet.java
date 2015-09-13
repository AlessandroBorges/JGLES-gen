package gles.generator.internal;

import java.util.*;

/**
 * Store all GL Commands and Functions. <br>
 * 
 * @author Alessandro Borges
 *
 */
public class GLcmdSet {
    /**
     * general info
     */
    public String vendor, group,comments,namespace, type; 
    public int start, end; 
    
    
    /**
     * Mapping of name and Command/Function
     */
    public Map<String, GLFunction> cmds = new HashMap<String, GLFunction>(16);
    
    
    public void addGLcmd(GLFunction cmd){
        cmds.put(cmd.name, cmd);
        if(cmd.alias != null){
            GLFunction alias = cmd.getAlias();
            cmds.put(alias.name, alias);
        }
        
    }
    
    /**
     * Get a function by unique name
     * @param cmdName
     * @return
     */
    public GLFunction getGLFunction(String cmdName){
        return cmds.get(cmdName);        
    }
    
    /**
     * get all Functions
     * @return
     */
    public Collection<GLFunction> getAllFunctions(){
        return cmds.values();
    }
    
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 20;
        StringBuilder builder = new StringBuilder();
        builder.append("GLcmdSet [");
        if (vendor != null) {
            builder.append("vendor=");
            builder.append(vendor);
            builder.append(", ");
        }
        if (group != null) {
            builder.append("group=");
            builder.append(group);
            builder.append(", ");
        }
        if (comments != null) {
            builder.append("comments=");
            builder.append(comments);
            builder.append(", ");
        }
        if (namespace != null) {
            builder.append("namespace=");
            builder.append(namespace);
            builder.append(", ");
        }
        if (type != null) {
            builder.append("type=");
            builder.append(type);
            builder.append(", ");
        }
        builder.append("start=");
        builder.append(start);
        builder.append(", end=");
        builder.append(end);
        builder.append(", ");
        if (cmds != null) {
            builder.append("/* Start of GL/EGL commands */");
            builder.append(toString(cmds.values(), 100000));
        }
        builder.append("/* End of GL/EGL commands */");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append("\t");
            builder.append(iterator.next());
            builder.append("\n");
        }
        builder.append("\n\n");
        return builder.toString();
    }
    
    
    
    
}
