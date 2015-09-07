package gles.generator.internal;

public class GLParam implements JavaPrinter{

    
    private static JavaTypeMap javaTypeMap;
    
    public String name, ptype="", group, len;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ptype
     */
    public String getPtype() {
        return ptype;
    }

    /**
     * @param ptype the ptype to set
     */
    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

   
    /**
     * get JType from CType
     * @param cType
     * @return
     */
   static private String getJavaType(String cType){
       if(cType != null){
           cType = cType.trim();
           // remove blanck spaces
           cType = cType.replace("    ", " ").replace("   ", " ").replace("  ", " ");
       }
       return JavaTypeMap.getJType(cType);
   }
   
   /**
    * get the Buffer type to wrap the this jType.
    * @param jType - one of byte, int, float, short, etc
    * 
    * @return ByteBuffer, IntBuffer, FloatBuffer, ShortBuffer
    */
   static private String getJavaBufferType(String jType){ 
       return JavaTypeMap.getBufferType(jType);
   }
   
   /**
    * get comment for param
    * @return
    */
   public  String getComment(){       
       if(group == null && len==null)
           return null;
       
       String cmm = "";       
       if(group != null)
           cmm += " group="+group;
       
       if(len!=null)
           cmm += "  len = " + len;
       
       return cmm;
       
   }
    
   /**
    * 
    * Returns param as standard asJavaString, i.e.,<br>
    * <pre>
    *  GLint as int,
    *  GLenum as int,
    *  GLfloat as float, 
    *  etc.,
    * </pre>
    * 
    * 
    * @return this param as Java valid type
    */
   public String asJavaString(){       
       String jType = getJavaType(ptype);
       String r =  IDENT + jType + " " + name;
       
       String cmm = getComment();
       if(cmm != null){
           r += "\t/*  " + cmm + " */";
       }       
       return r;       
   }
    
   public String asJavaString(String ident){       
       String jType = getJavaType(ptype);
       String r =  ident + jType + " " + name;
       
       String cmm = getComment();
       if(cmm != null){
           r += "\t/*  " + cmm + " */";
       }       
       return r;       
   }
    
    /**
     * Check if this GLparam is a pointer of any kind,
     * i.e. something like int *
     * 
     * @return
     */
    public boolean hasPointer() {        
        return (ptype!=null && ptype.contains(POINTER));
    }
    
    /**
     * Check if this GLParam is a pointer to pointers, 
     * as void **, GLfloat **, etc. 
     * @return
     */
    public boolean hasPointerPointer() {
        if(hasPointer()){
            int pos = ptype.indexOf(POINTER);
            int lastPos = ptype.lastIndexOf(POINTER);
            return pos != lastPos;
        }else
            return false;
    }
    
    /**
     * Check if this type is a String
     * @return
     */
    public boolean isString(){
        if(hasPointer()){
            return ptype.contains("char");
        }
        
        return false;
    }
    
    public boolean isVoidPointer(){
      if(hasPointer() && ptype.contains("void")){
          return true;
      }
      return false;
        
    }
    /**
     * 
     */
    private static final String POINTER = "*";
    private static final String IDENT = "\n\t\t";
    static final String OFFSET = "Offset";
    private static final String OFFSET_TYPE = "int ";
    
    /**
     * If applicable, returns this GLparam as a java.nio.Buffer.
     *  java.nio.Buffer and it's offset.<br>
     *  <pre>
     *  Example
     *  </pre>
     *    
     * @return
     */
    public String asJavaStringBufferAndOffset(String ident){  
        if(!hasPointer() || isString()){
            return asJavaString(ident);
        }
        String offsetName = this.name + OFFSET;
        String jType = getJavaType(this.ptype);
        String bufferType = getJavaBufferType(jType);
        
        String jTypeAndOffset = ident + bufferType + " " + name + ", "+
                                /*ident +*/ OFFSET_TYPE + offsetName;
        return jTypeAndOffset;
    }
    
   /**
    * If this param is <b>pointer</b>, it will convert to a 
    * valid array and add a extra param for <b>offset(/b>. <br>
    * 
    * Example
    * <pre>
    *  GLint * data  => int[] data, int dataOffset
    *    
    *  GLfloat * vertex => float[] vertex, int vertexOffset  
    * </pre>
    *  
    * @return converted this param to Java type
    */
   public String asJavaStringArray(String ident){
       if(!hasPointer()){
           return asJavaString(ident);
       }
       String offsetName = this.name + OFFSET;
       String jTypeAndOffset = asJavaString(ident) + ", " +
                              /*ident +*/ OFFSET_TYPE + offsetName;
       return jTypeAndOffset;
    }
   
   /**
    * Return this GLParam as CType string
    */
    public String asCProto(boolean applyComma){
        StringBuilder builder = new StringBuilder();        
        
        
        
        if (ptype != null) { 
            builder.append(IDENT);            
            builder.append(ptype);
            builder.append(" ");
        }
        
        if (name != null) {           
            builder.append(name);
           // builder.append(" ");
        }
        if(applyComma){
            builder.append(",");
        }
        
        if (group != null) {
            builder.append(" // group=");
            builder.append(group);
            builder.append("  ");
        }
       
        return builder.toString();
    }
   
   /**
    * Return this GLParam as CType string
    */
    public String toString(){
        StringBuilder builder = new StringBuilder();        
        
        if (ptype != null) { 
            builder.append(IDENT);
            builder.append(ptype);
            builder.append(" ");
        }
        
        if (name != null) {           
            builder.append(name);
           // builder.append(" ");
        }
        if (group != null) {
            builder.append(" /* group=");
            builder.append(group);
            builder.append(" */ ");
        }
       
        return builder.toString();
    }
   
   
   /**
    * Testes
    * @param args
    */
   public static void main(String[] args) {
       GLParam param = new GLParam();
       param.name = "buffer";
       param.ptype ="GLfloat *";
       param.group = "group data";
       
       System.out.println("GLparam.toString(): CType " + param.toString());
       System.out.println("GLparam.asJavaString(): " + param.asJavaString());
       System.out.println("GLparam.asJavaStringBufferAndOffset(): " + param.asJavaStringBufferAndOffset("  "));
       System.out.println("GLparam.asJavaStringArray(): " + param.asJavaStringArray(" "));
       
    
 }
   
   
}


