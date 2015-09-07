package gles.generator.internal;

import java.util.*;

public class JavaTypeMap {
    protected static final String BUFFER = "Buffer";
    private static  Map<String,String> mapTypes = new HashMap<String, String>();
    private static Map<String,String> mapModTypes = new HashMap<String, String>();
    
    private static  Map<String,String> mapBufferTypes = new HashMap<String, String>();
    
    private static  Map<String,String> jniTypes = new HashMap<String, String>();
    
    static {
        init();
    }
    /**
     * initialize maps
     */
    public static void init(){
        // EGL
        add("EGLBoolean","boolean");
        add("EGLBoolean *","boolean[]");
        add("EGLint","int");
        
        add("EGLint *","int[]");
        
        add("EGLuint64KHR","long");
        add("EGLuint64KHR *","long[]");
        
        add("EGLenum","int");
        add("EGLenum *","int[]");
        
        add("EGLAttrib","int");
        add("EGLAttrib *","int[]");
        
        // EGL types
        add("EGLDisplay","long");
        add("EGLDisplay *","long[]");
        
        add("EGLDeviceEXT", "long");
        add("EGLDeviceEXT *", "long[] ");
        
        add("EGLDevice", "long");
        
        add("EGLContext", "long");
        add("EGLContext *", "long[]");
        
        add("EGLConfig", "long");
        add("EGLConfig *", "long[]");
        
        add("EGLClientBuffer", "long");
        
        add("EGLImage", "long");
        add("EGLSurface", "long");
        add("EGLSync", "long");
        add("EGLClientBuffer","java.nio.Buffer");//EGLClientBuffer
        
        add("EGLAttribKHR", "int");
        add("EGLAttribKHR *", "int[]");
        
        
        add("__eglMustCastToProperFunctionPointerType", "long");
        
        // time types
        add("EGLTimeKHR","long");
        add("EGLTime","long");
        add("EGLTimeNV","long");
        add("EGLSyncKHR", "long");
       // add("","long");
        
        add("EGLuint64NV","long");
        add("EGLuint64KHR","long");
        add("Eint64KHR","long");
        add("Euint64KHR","long");
          //Eint64KHR
        
        add("GLenum","int");
        add("GLenum *","int[]");
        
        add("GLsync","int");
        add("GLsync *","int[]");
        
        add("GLbitfield","int");
        
        add("GLfloat","float");
        add("GLfloat *","float[]");
        
        add("GLclampf","float");
        add("GLclampf *","float[]");
        
        add("GLdouble","double");
        add("GLdouble *","double[]");
        
        add("GLclampd","double");
        add("GLchar","byte");
        
        // String ??
        add("GLchar *","String");
        
        add("GLcharARB","byte");
        add("GLbyte","byte");
        add("GLbyte *","byte[]");
        
        add("GLshort","short");
        add("GLshort *","short[]");
        
        add("GLint","int");
        add("GLint *","int[]");
        
        add("GLuint","int");
        add("GLuint *","int[]");
        
        add("GLclampx","int");
        add("GLubyte","byte");
        add("GLushort","short");
       
        add("GLsizei","int");
        
        add("GLubyte *","byte[]");
        add("GLushort *","short[]");
        add("GLuint *","int[]");
        add("GLsizei *","int[]");
        
        add("GLcharARB","byte");
        add("GLhalfARB","short");
        add("GLhalf","short");
        add("GLfixed","int");
        
        add("GLcharARB *","byte[]");
        add("GLhalfARB *","short[]");
        add("GLhalf *","short[]");
        add("GLfixed *","int[]");
        
        add("GLint64","long");
        add("GLuint64","long");
        add("GLint64 *","long[]");
        add("GLuint64 *","long[]");
        
        // long ptr ?
        add("GLintptr","int[]");
        
        add("size_t","int");
        add("GLintptr","int[]");
        
        //long ptr
        add("GLsizeiptr","int[]");
        add("GLintptrARB","int[]");
        
        add("GLint64EXT","long");
        add("GLuint64EXT","long");
        
        add("void *",JavaTypeMap.BUFFER);
        add("GLvoid *",JavaTypeMap.BUFFER);
        
        add("const char *","String");
        add("char *","String");
        
        add("const void *",JavaTypeMap.BUFFER);
        add("void","void");
        add("void **","java.io.LongBuffer /* as void** */");
        
        add("GLboolean","boolean");
        add("GLboolean *","boolean[]");
        
        
        add("const void * const *", "LongBuffer /* as void** */");
        
        mapModTypes.put("*","[]");
        mapModTypes.put("const","final");
        
        mapBufferTypes.put(JavaTypeMap.BUFFER, "java.nio.Buffer");  
        mapBufferTypes.put("LongBuffer", "java.nio.LongBuffer"); 
        mapBufferTypes.put("float", "java.nio.FloatBuffer");     
        mapBufferTypes.put("int", "java.nio.IntBuffer");
        mapBufferTypes.put("short", "java.nio.ShortBuffer");
        mapBufferTypes.put("long", "java.nio.LongBuffer");
        mapBufferTypes.put("double", "java.nio.DoubleBuffer");
        mapBufferTypes.put("byte", "java.nio.ByteBuffer");
        
        mapBufferTypes.put("EGLClientBuffer", "java.nio.Buffer");
        
        
        jniTypes.put("boolean", "jboolean");
        jniTypes.put("byte", "jbyte");
        jniTypes.put("char", "jchar");
        jniTypes.put("short", "jshort");
        jniTypes.put("int", "jint");
        jniTypes.put("long", "jlong");
        jniTypes.put("float", "jfloat");
        jniTypes.put("double", "jdouble");
        
        //EGLClientBuffer
        
        jniTypes.put("EGLBoolean", "jboolean");
        jniTypes.put("EGLenum", "jint");
        jniTypes.put("EGLAttrib", "int");
        jniTypes.put("EGLAttrib *", "int[]");
        
        jniTypes.put("EGLImageKHR", "jlong");
        jniTypes.put("EGLImage", "jlong");
        
        jniTypes.put("", "");
        
        
        jniTypes.put("", "");
        jniTypes.put("EGLNativeFileDescriptorKHR", "jlong");
        jniTypes.put("EGLDisplay", "jlong");
        jniTypes.put("EGLSurface", "jlong");
        jniTypes.put("EGLContext", "jlong");
        
        jniTypes.put("EGLSyncKHR", "jlong");
        jniTypes.put("EGLSync", "jlong"); 
        
        jniTypes.put("EGLStreamKHR", "jlong"); 
        jniTypes.put("EGLStream", "jlong"); 
        jniTypes.put("__eglMustCastToProperFunctionPointerType", "jlong"); 
        jniTypes.put("EGLNativeFileDescriptorKHR", "jlong");
        
        jniTypes.put("EGLuint64NV", "jlong");
       // jniTypes.put("", "jlong");
       // jniTypes.put("", "jlong");
      
        
        jniTypes.put("void *", "long");
        
        
    }
    
    public static boolean contains(String cType){
        return mapTypes.containsKey(cType);
    }
    
    /**
     * Get a direct mapping of a CType to Java Type.
     * 
     * @param cType - cType, as GLint, float*, etc
     * @return equivalent Java type, as int, float[], etc.
     */
    public static String getJType(String cType) {
        if (cType == null) {
            return cType;
        } else {
            cType = trim(cType);
        }
        String jtype = mapTypes.get(cType);

        // now the modifiers
        if (jtype == null) {
           // System.err.println("\t# cType not found!: " + cType);
            // return cType;

            jtype = new String(cType);
            for (String mod : mapModTypes.keySet()) {
                if (jtype.contains(mod)) {
                    String novo = mapModTypes.get(mod);
                    jtype = jtype.replace(mod, novo);
                }
            }

            for (String mod : mapTypes.keySet()) {
                if (jtype.contains(mod)) {
                    String novo = mapTypes.get(mod);
                    jtype = jtype.replace(mod, novo);
                }
            }
           // System.err.println("\t#  Match: " + cType + " -> " + jtype);
        }
        return jtype;
    }

    public static void add(String cType, String jType){
       mapTypes.put(cType, jType);        
    }
    
    public static String trim(String s){
        if(s==null)
            return s;
        s = s.trim();
        s = s.replace("   ", " ");
        s = s.replace("  ", " ");       
        return s;
    }
    
    /**
     * translate a array type to BufferType.
     * 
     * @param jType
     * @return
     */
    public static String getBufferType(String jType){
        if(jType==null || 
          (!jType.contains(JavaTypeMap.BUFFER) && !jType.contains("[]")))
        {
            throw new IllegalArgumentException("not a valid Java array type!" + jType);    
        }
        
        String bufferType = JavaTypeMap.BUFFER;
        Set<String> keys = mapBufferTypes.keySet();
        for(String key : keys){
            if(jType.contains(key)){
                return mapBufferTypes.get(key);
            }
        }
        return bufferType;
    }
    
    /**
     * Get a JNI type
     * @param jType - type to query, as int, boolean, etc
     * @return
     */
    public static String getJNItype(String proto){
        // first, convert it to jType
        String jType = getJType(proto);
        
        if(jType.toLowerCase().contains("egl")){
            return "jlong";
        }
       
       // now, reduces to primitive  
       return jniTypes.get(jType);        
    }
}
