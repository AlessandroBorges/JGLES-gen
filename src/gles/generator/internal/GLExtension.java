package gles.generator.internal;



import gles.generator.GLmain;
import gles.generator.GLmain.GL_API;

import java.util.*;

public class GLExtension
 implements JavaPrinter, Comparable<GLExtension>

{   
    /**
     * Prefix for mangled function
     */
    public static final String MANGLES_PREFIX ="gles_";
    
    protected static final String KHRONOS_GL = "https://www.khronos.org/registry/gl/extensions/";
    protected static final String KHRONOS_GLES = "https://www.khronos.org/registry/gles/extensions/";
    protected static final String KHRONOS_EGL  = "https://www.khronos.org/registry/egl/extensions/";
    protected static final String[] GLES_EXTENSION_GROUP = {"AMD","ANDROID","ANGLE","APPLE",
                                                            "ARM","DMP","EXT","FJ","IMG","INTEL",
                                                            "KHR","NV","OES","OVR","QCOM","VIV" };
    
    /**
     * Must values are strict, but some values are guessed, as INTEL, APPLE and QCOM. They are major players, 
     * and may add extension to EGL api soon or later.
     */
    protected static final String[] EGL_EXTENSION_GROUP = {"ANDROID","ANGLE","APPLE",
                                                            "ARM","EXT","HI","IMG","KHR","INTEL",
                                                            "MESA","NOK","NV","TIZEN",
                                                            "NV","OES","QCOM","VIV" };
    
    /**
     * extension names with buggy filenames at https://www.khronos.org/registry/
     */
    public static final String[] NAME_BUG = { "AMD_performance_monitor", "APPLE_rgb_422", 
            "EXT_blend_minmax",
            "EXT_draw_instanced", "EXT_multi_draw_arrays", "EXT_post_depth_coverage", "EXT_raster_multisample",
            "EXT_shader_integer_mix", "EXT_texture_compression_dxt1", "EXT_texture_compression_s3tc",
            "EXT_texture_filter_anisotropic", "EXT_texture_filter_minmax", "EXT_texture_lod_bias",
            "EXT_texture_sRGB_decode", 
            "FJ_shader_binary_GCCSO", 
            "INTEL_framebuffer_CMAA", "INTEL_performance_query",
            "KHR_blend_equation_advanced", "KHR_context_flush_control", "KHR_debug", "KHR_no_error", "KHR_robustness",
            "KHR_robust_buffer_access_behavior", "KHR_texture_compression_astc_hdr", "NV_bindless_texture",
            "NV_blend_equation_advanced", "NV_conditional_render", "NV_conservative_raster", "NV_draw_texture",
            "NV_EGL_NV_coverage_sample", "NV_EGL_NV_depth_nonlinear", "NV_fence", "NV_fill_rectangle",
            "NV_fragment_coverage_to_color", "NV_fragment_shader_interlock", "NV_framebuffer_mixed_samples",
            "NV_geometry_shader_passthrough", "NV_internalformat_sample_query", "NV_NV_3dvision_settings", "NV_NV_bgr",
            "NV_NV_copy_buffer", "NV_NV_draw_buffers", "NV_NV_draw_instanced", "NV_NV_EGL_stream_consumer_external",
            "NV_NV_explicit_attrib_location", "NV_NV_fbo_color_attachments", "NV_NV_framebuffer_blit",
            "NV_NV_framebuffer_multisample", "NV_NV_generate_mipmap_sRGB", "NV_NV_image_formats",
            "NV_NV_instanced_arrays", "NV_NV_non_square_matrices", "NV_NV_packed_float", "NV_NV_pack_subimage",
            "NV_NV_pixel_buffer_object", "NV_NV_platform_binary", "NV_NV_polygon_mode", "NV_NV_read_buffer",
            "NV_NV_read_depth_stencil", "NV_NV_shader_noperspective_interpolation", "NV_NV_shadow_samplers_array",
            "NV_NV_shadow_samplers_cube", "NV_NV_sRGB_formats", "NV_NV_texture_array", "NV_NV_texture_border_clamp",
            "NV_NV_texture_compression_latc", "NV_NV_texture_compression_s3tc",
            "NV_NV_texture_compression_s3tc_update", "NV_NV_texture_npot_2D_mipmap", "NV_NV_viewport_array",
            "NV_path_rendering", "NV_path_rendering_shared_edge", "NV_sample_locations",
            "NV_sample_mask_override_coverage", "NV_viewport_array2",
            "OES_EGL_KHR_fence_sync",
            "OVR_multiview","OVR_multiview2", 
    };
    
    /**
     * String key for Android AEP<br> 
     * See more: <a href="https://www.khronos.org/registry/gles/extensions/ANDROID/ANDROID_extension_pack_es31a.txt">ANDROID_extension_pack_es31a</a>
     * <br>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String ANDROID_EXTENSION_PACK = "GL_ANDROID_extension_pack_es31a";
    
    /**
     * String key for extensions used in GL10 interface, as defined by JSR 239<br>
     * See more: <a href="http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/jb/jsr239/">JSR 239</a>
     * @see #JSR239_EXTENSIONS_GL10
     * @see #extensionGroupMap 
     * @see #getExtensionGroup(String)   
     */
    public static final String GL10_EXT_GROUP = "GL10";
    
    /**
      * String key for extensions used in GL10Ext interface, as defined by JSR 239<br>
     * See more: <a href="http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/jb/jsr239/">JSR 239</a>
     * @see #JSR239_EXTENSIONS_GL10Ext  
     * @see #extensionGroupMap 
     * @see #getExtensionGroup(String)
     */
    public static final String GL10Ext_EXT_GROUP = "GL10Ext";
    
    /**
      * String key for extensions used in GL11 interface, as defined by JSR 239<br>
     * See more: <a href="http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/jb/jsr239/">JSR 239</a>
     * @see #JSR239_EXTENSIONS_GL11 
     * @see #extensionGroupMap 
     * @see #getExtensionGroup(String) 
     */
    public static final String GL11_EXT_GROUP = "GL11";
    
    /**
      * String key for extensions used in GL11Ext interface, as defined by JSR 239<br>
     * See more: <a href="http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/jb/jsr239/">JSR 239</a>
     * @see #JSR239_EXTENSIONS_GL11Ext 
     * @see #extensionGroupMap 
     * @see #getExtensionGroup(String)  
     */
    public static final String GL11Ext_EXT_GROUP = "GL11Ext";
    
    /**
     * String key for extensions used in GL11ExtensionPack, as defined by JSR 239<br>
     * See more: <a href="http://docs.oracle.com/javame/config/cldc/opt-pkgs/api/jb/jsr239/">JSR 239</a>
     * @see #JSR239_EXTENSIONS_GL11ExtensionPack  
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String) 
     */
    public static final String GL11ExtensionPack_EXT_GROUP = "GL11ExtensionPack";
    
    /**
     * Android Extension Pack - list of required extensions.<br>   
     * See more: <a href="https://www.khronos.org/registry/gles/extensions/ANDROID/ANDROID_extension_pack_es31a.txt">ANDROID_extension_pack_es31a</a>
     * <br>
     * @see GLExtension#ANDROID_EXTENSION_PACK
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] AEP_EXTENSIONS = { "GL_KHR_debug",
        "GL_KHR_texture_compression_astc_ldr",
        "GL_KHR_blend_equation_advanced",
        "GL_OES_sample_shading",
        "GL_OES_sample_variables",
        "GL_OES_shader_image_atomic",
        "GL_OES_shader_multisample_interpolation",
        "GL_OES_texture_stencil8",
        "GL_OES_texture_storage_multisample_2d_array" ,
        "GL_EXT_copy_image",
        "GL_EXT_draw_buffers_indexed",
        "GL_EXT_geometry_shader",
        "GL_EXT_gpu_shader5",
        "GL_EXT_primitive_bounding_box",
        "GL_EXT_shader_io_blocks",
        "GL_EXT_tessellation_shader",
        "GL_EXT_texture_border_clamp",
        "GL_EXT_texture_buffer",
        "GL_EXT_texture_cube_map_array",
        "GL_EXT_texture_sRGB_decode"};
    
    /**
     * JSR 239 defined extensions to GL10 and GL11 api.<br>
     * GL10 has the following extensions: 
     * <pre> 
     * OES_byte_coordinates
     * OES_single_precision
     * OES_fixed_point
     * OES_read_format
     * OES_compressed_paletted_texture
     * </pre>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] JSR239_EXTENSIONS_GL10 = {"OES_byte_coordinates",
        "OES_single_precision",
        "OES_fixed_point",
        "OES_read_format",
        "OES_compressed_paletted_texture",};
    
    /**
     * JSR 239 defined extensions to GL10 and GL11 api.<br>
     * GL10Ext has the following extensions: 
     * <p> 
     *  OES_query_matrix
     * </p>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] JSR239_EXTENSIONS_GL10Ext= {"OES_query_matrix"};
    
    /**
     * JSR 239 defined extensions to GL10 and GL11 api.<br>
     * GL11 has the following extensions:
     * <pre>
     * OES_matrix_get
     * OES_point_sprite
     * OES_point_size_array
     * </pre>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] JSR239_EXTENSIONS_GL11= {"OES_matrix_get",
        "OES_point_sprite",
        "OES_point_size_array"};
    
    /**
     * JSR 239 defined extensions to GL10 and GL11 api.<br>
     * GL11ext has the following extensions: 
     * <pre>
     * OES_draw_texture
     * OES_matrix_palette
     * </pre>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] JSR239_EXTENSIONS_GL11Ext= {"OES_draw_texture","OES_matrix_palette"};
    
    /**
     * JSR 239 defined extensions to GL10 and GL11 api.<br>
     * GL11ExtensionPack has the following extensions:
     * <pre>
     *     OES_texture_env_crossbar
     *     OES_texture_mirrored_repeat
     *     OES_texture_cube_map
     *     OES_blend_subtract
     *     OES_blend_func_separate
     *     OES_blend_equation_separate
     *     OES_stencil_wrap
     *     OES_extended_matrix_palette
     *     OES_framebuffer_object
     * </pre>
     * @see #extensionGroupMap
     * @see #getExtensionGroup(String)
     */
    public static final String[] JSR239_EXTENSIONS_GL11ExtensionPack= { "OES_texture_env_crossbar",
        "OES_texture_mirrored_repeat",
        "OES_texture_cube_map",
        "OES_blend_subtract",
        "OES_blend_func_separate",
        "OES_blend_equation_separate",
        "OES_stencil_wrap",
        "OES_extended_matrix_palette",
        "OES_framebuffer_object",};
    
    
   
    /**
     * Maps Extension per API. Currently we have the following extension key groups:
     * <ul><li>{@link #ANDROID_EXTENSION_PACK}, 
     * <li>{@link #GL10_EXT_GROUP}, 
     * <li>{@link #GL10Ext_EXT_GROUP},
     * <li>{@link #GL11_EXT_GROUP},
     * <li>{@link #GL11Ext_EXT_GROUP}
     * <li>{@link #GL11ExtensionPack_EXT_GROUP}
     * </ul>  
     * 
     * @see #getExtensionGroup(String)
     * 
     */
    public static Map<String, String[]> extensionGroupMap = new HashMap<String, String[]>();
    

   
    public String name;   
    
    public String supportedAPI, protect;
    
    public static final String FUNC_LOADER = "myFuncLoader";
    
    public static final String GL = "gl", GLES1 = "gles1", GLES2="gles2", EGL="egl";
   
    public List<GLExtensionRequire> requires = new ArrayList<GLExtensionRequire>(2);
    
    private Boolean isGL = null;
    
    static{
        extensionGroupMap.put(ANDROID_EXTENSION_PACK, AEP_EXTENSIONS );
        extensionGroupMap.put("aep", AEP_EXTENSIONS );
        extensionGroupMap.put(GL10_EXT_GROUP, JSR239_EXTENSIONS_GL10 );
        extensionGroupMap.put(GL10Ext_EXT_GROUP, JSR239_EXTENSIONS_GL10Ext );
        extensionGroupMap.put(GL11_EXT_GROUP, JSR239_EXTENSIONS_GL11 );
        extensionGroupMap.put(GL11Ext_EXT_GROUP, JSR239_EXTENSIONS_GL11Ext );
        extensionGroupMap.put(GL11ExtensionPack_EXT_GROUP, JSR239_EXTENSIONS_GL11ExtensionPack );
        
    }
    
    /**
     * Get a group of extensions, by name.<br>
     * Khronos and Android defined some set of extensions, as following:
     * <table border=2>
     * <tr>
     * <TH>API</TH> <TH>Extensions</TH>
     * </tr>
     * <tr>
     * <td>AEP / ANDROID_EXTENSION_PACK</td>
     * <td><pre>
     *  KHR_debug,
     *  KHR_texture_compression_astc_ldr,
     *  KHR_blend_equation_advanced,
     *  OES_sample_shading,
     *  OES_sample_variables,
     *  OES_shader_image_atomic,
     *  OES_shader_multisample_interpolation,
     *  OES_texture_stencil8,
     *  OES_texture_storage_multisample_2d_array,
     *  EXT_copy_image,
     *  EXT_draw_buffers_indexed,
     *  EXT_geometry_shader,
     *  EXT_gpu_shader5,
     *  EXT_primitive_bounding_box,
     *  EXT_shader_io_blocks,
     *  EXT_tessellation_shader,
     *  EXT_texture_border_clamp,
     *  EXT_texture_buffer,
     *  EXT_texture_cube_map_array,
     *  EXT_texture_sRGB_decode</pre>
     *  </td>
     * </tr>
     * <TR>
     * <TD> GL10 </td> 
     * <TD><pre> OES_byte_coordinates
     * OES_single_precision,
     * OES_fixed_point,
     * OES_read_format,
     * OES_compressed_paletted_texture</pre></td>
     * </TR>
     * <tr>
     * <td>GL10Ext</td>
     * <td> OES_query_matrix</td>
     * </tr>
     * <tr>
     * <td>GL11</td>
     * <td><pre> OES_matrix_get,
     * OES_point_sprite,
     * OES_point_size_array</pre></td>
     * </tr>
     * <tr>
     * <td>GL11Ext</td>
     * <td> OES_draw_texture,
     * OES_matrix_palette</td>
     * </tr>
     * <tr>
     * <td>GL11ExtensionPack</td>
     * <td><pre>
     * OES_texture_env_crossbar,
     * OES_texture_mirrored_repeat,
     * OES_texture_cube_map,
     * OES_blend_subtract,
     * OES_blend_func_separate,
     * OES_blend_equation_separate,
     * OES_stencil_wrap,
     * OES_extended_matrix_palette,
     * OES_framebuffer_object</pre>
     * </td>
     * </tr>     
     * </table>
     * @param groupKey - key to extensions. One of 
     * <ul><li>{@link #ANDROID_EXTENSION_PACK}, 
     * <li>{@link #GL10_EXT_GROUP}, 
     * <li>{@link #GL10Ext_EXT_GROUP},
     * <li>{@link #GL11_EXT_GROUP},
     * <li>{@link #GL11Ext_EXT_GROUP}
     * <li>{@link #GL11ExtensionPack_EXT_GROUP}
     * </ul>
     * 
     * @return String array with extension names
     */
    public static String[] getExtensionGroup(String groupKey){
        return extensionGroupMap.get(groupKey);
    }
    
    /**
     * Add require to this Extension
     * @param require extension require to add
     */
    public void add(GLExtensionRequire require){
        requires.add(require);
        require.owner = this;
        require.extName  = this.name;
    }
        
       
    public boolean isEGL(){
        return supportedAPI != null && supportedAPI.contains(EGL);
    }
    
    public boolean isGL(){
      if(supportedAPI == null) return false;
      if(this.isGL == null){
          String s = supportedAPI.replace(EGL, "   ").replace(GLES1, "     ").replace(GLES2,"     ");
          isGL = s.contains(GL) ;
      }      
      return  isGL.booleanValue();
    }
    
    public boolean isGLES1(){
       return  supportedAPI != null && supportedAPI.contains(GLES1);
    }
    
    public boolean isGLES2(){
        return  supportedAPI != null && supportedAPI.contains(GLES2);
     }
    
    boolean verbose = false;
    
    /**
     * hold the api constant value, as GLmain.GLES3,
     */
    private String apiMode;
    
    /**
     * set API mode for printing
     * @param api - GL_API in process.
     * 
     * @see GLmain
     */
    public void setJavaAPIMode(GL_API api){
        switch (api) {
        case EGL:   apiMode = GLmain.EGL;break;
        case GLES1: apiMode = GLmain.GLES1;break;
       // GLES 2.0 to 3.2 uses the same base
        case GLES2:
        case GLES3:
        case GLES31:    
        case GLES32: apiMode = GLmain.GLES2; break;        
        case GL: apiMode = GLmain.GL; break;
        }
    }
    
    /**
     * set API mode for printing
     * @param api - API in process. One of GLmain.EGL, GLmain.GLES1,GLmain.GLES2,GLmain.GLES3,
     * GLmain.GLES31,GLmain.GLES32.
     * 
     * @see GLmain
     */
    public void setJavaAPIMode(String api){  
        // GLES 2.0 to 3.2 uses the same base
        if(api == GLmain.GLES3 || api == GLmain.GLES31 || api==GLmain.GLES32 ){
            this.apiMode = GLmain.GLES2;
        }else{
            this.apiMode = api;
        }
    }
    
    /**
     * 
     */
//    @Deprecated
//    public String asJavaString2(){
//        if(apiMode==null) 
//            throw new UnsupportedOperationException("apiMode not set. Please call setJavaAPIMode(api)");
//        //TODO check apiModeValues
//        String enums = getJavaEnum(apiMode);        
//        List<String> functionCproto = getCprototypeFunctions(apiMode);
//        List<String> functionCcall = getCcallFunctions(apiMode);
//        
//        //TODO - build mapping from Cproto to functionJava ( 1:n) 
//        //List<String> functionJava = getJavaStringFunctions(apiMode);
//        Map<String, GLFunction> mapDatabase = getJavaStringFunctionsMap(apiMode);
//        
//        StringBuilder sb = new StringBuilder(64*1024);
//        sb.append("/************************************************************************/\n");
//        sb.append("/** Extensions ").append(this.name).append("  ***/\n");
//        sb.append("/***********************************************************************/\n");
//        sb.append(enums);
//        sb.append('\n');
//        
//        List<String> functionsJava = new ArrayList<String>(8);
//        
//        int size = functionCproto.size();
//        for(int i=0; i<size; i++){            
//            String cProto = functionCproto.get(i);
//            String cCall= functionCcall.get(i);
//            
//            functionsJava = getJavaStringFunction(null, mapDatabase, functionsJava);
//            
//            String javaFunc = functionsJava.get(i);
//            
//            sb.append("\n /**\n")
//            .append("  * C Prototype:")
//            .append(addIdentLines(cProto,"* "))   // create function to add * after new line
//            .append("\n  **/ \n");
//            
//            sb.append("  public static ");
//            sb.append(javaFunc)
//            .append("/*\n")
//            .append("\t // jnigen - native goes here")
//            .append(addIdentLines(cCall,"\t"))
//            .append("\n\t */\n");               
//        }
//        return sb.toString();
//    }
    
    /**
     * Create declaration for function procedure pointers.
     * @param asStatic - true to create static function pointers
     * @param mangled - true to create mangled name and later redefine
     * @return source code in C for function pointers
     */
    public String asCfunctionPointers(boolean asStatic, boolean mangled){
        String NTAB = "\n\t";
        StringBuilder sb = new StringBuilder(5 * 1024);

        for (GLExtensionRequire require : requires) {
            
          //skip non matching API
            if(!require.api.contains(apiMode) ){
                continue;
            }
            
            List<GLFunction> funcs = require.getAllFunctions();
            // skip functionLESS extensions
            if(funcs==null || funcs.isEmpty())
                continue;
            
            sb.append("\n\n");
            sb.append(" ///////////////////////////////////\n");
            sb.append(" // PFN_PROC functions declaration ");
            {
                if (require.extName != null) {
                    sb.append("\n // Extension: ").append(require.extName);
                }
                if (require.api != null) {
                    sb.append("\n // API: ").append(require.api);
                }
                if (require.profile != null) {
                    sb.append("\n // Profile: ").append(require.profile);
                }
            }
            sb.append("\n //////////////////////////////////");
            sb.append("");

            
            for (GLFunction func : funcs) {
                String funcName = func.name;
                sb.append(NTAB);
                if(asStatic){
                    sb.append(" static ");
                }else{
                    sb.append("  ");
                } 
                if(mangled){
                    // pfn mangled function name
                    sb.append(pfnproc(funcName))
                    .append("\t")
                    .append(MANGLES_PREFIX).append(funcName)
                    .append(";"); 
                    // redefine mangled to normal name
                    sb.append(NTAB)
                    .append(" #define\t")
                    .append(funcName).append('\t')
                    .append(MANGLES_PREFIX).append(funcName);
                }else{
                sb.append(pfnproc(funcName))
                .append("\t")
                .append(funcName)
                .append(";");
                }
            }// for Loops
        }// requires

        return sb.toString();
    }
    
    /**
     * get plain function loader name
     * @return name of extension function loader
     */
    public String getFunctionLoaderName(){
        return "loadExtFunc" + name;
    }
    
    /**
     * Create method to load extensions
     * @param asStatic - as static loader
     * @param asMangled - load function with mangled name
     * 
     * @return string with loader source code
     */
    public String asCfunctionLoaders(boolean asStatic, boolean asMangled){
        
        String NTAB = "\n\t  ";
        StringBuilder sb = new StringBuilder(5 * 1024);

        for (GLExtensionRequire require : requires) {
            //skip non matching API
            if(!require.api.contains(apiMode) ){
                continue;
            }
            
            List<GLFunction> funcs = require.getAllFunctions();
            // skip extensions without functions
            if(funcs.isEmpty())
                continue;
            
            sb.append("\n");
            sb.append("  /// ====================================\n")
              .append("  // PFN_PROC Extensions functions Loader. ");
            {
                if (require.extName != null) {
                    sb.append("\n  // Extension: ").append(require.extName);
                }
                if (require.api != null) {
                    sb.append("\n  // API: ").append(require.api);
                }
                if (require.profile != null) {
                    sb.append("\n  // Profile: ").append(require.profile);
                }
            }
            sb.append("\n  /// =====================================\n");
            sb.append("");
            
            if(asStatic){
                sb.append("  static");
            }else{
                sb.append("  ");
            }

            sb.append("  int loadExtFunc").append(require.extName).append("(){");
            for (GLFunction func : funcs) {
                String funcName = func.name.trim();
                String mangledFuncName = asMangled ? mangle(funcName) : funcName;
                String pfnName = pfnproc(funcName);
                sb.append(NTAB)
                .append(mangledFuncName)
                .append(" = (")
                .append("")
                .append(pfnName)
                .append(") ").append(FUNC_LOADER).append("(")
                .append("\"").append(funcName).append("\");");
            }// for func
            sb.append("\n")
            .append(NTAB)
            .append("return 1;\n")
            .append("")
            .append("     } // end of loadExtFunc").append(require.extName).append("()\n");
        }// requires

       
        return sb.toString();
    }
    
    /**
     * Mangle a name, by adding gles_ prefix
     * @param funcName - function name to mangle
     * @return function name mangled
     */
    public static String mangle(String funcName){
        return MANGLES_PREFIX + funcName;
    }
    
    
    /**
     * Create list with functionLoader names
     * @return
     */
    public List<String> asCextFuncLoaderNames(List<String> loaderNames){
       // List<String> loaderNames = new ArrayList<String>();
        if(loaderNames==null){
            loaderNames = new ArrayList<String>();
        }
        checkRequestForGroupExtensions(loaderNames);
        
        for (GLExtensionRequire require : requires) {
            List<GLFunction> funcs = require.getAllFunctions();
            // skip extensions without functions
            if(funcs.isEmpty())
                continue;
            String name = "loadExtFunc"+require.extName +"()";
            loaderNames.add(name);
        }// requires
        return loaderNames;
    }
    
    /**
     * <p>On GL-ES there are several groups of extensions, as
     *  GL_ANDROID_extension_pack_es31a, GL10, GL10Ext, GL11Ext, GL11ExtensionsPack, etc.</p>
     *  <p>
     *  This method check if the extension list contains name of one of this group.
     *  If found, the extension names are added to extension list. 
     *  </p>    
     *  <p>
     *  
     *  </p>
     *  <p>    
     * <b>GL_ANDROID_extension_pack_es31a</b> or <b>ANDROID_extension_pack_es31a</b> is 
     *  a set of OpenGL ES extensions available for some OpenGL ES 3.1 renderers.<br>
     *  
     *  It is a umbrella extension, because it doesn't define any enumeration or function,
     *  but if available, it means the following extensions are also supported:<br>
     *  <pre>
     *  KHR_debug
     *  KHR_texture_compression_astc_ldr
     *  KHR_blend_equation_advanced
     *  OES_sample_shading
     *  OES_sample_variables
     *  OES_shader_image_atomic
     *  OES_shader_multisample_interpolation
     *  OES_texture_stencil8
     *  OES_texture_storage_multisample_2d_array
     *  EXT_copy_image
     *  EXT_draw_buffers_indexed
     *  EXT_geometry_shader
     *  EXT_gpu_shader5
     *  EXT_primitive_bounding_box
     *  EXT_shader_io_blocks
     *  EXT_tessellation_shader
     *  EXT_texture_border_clamp
     *  EXT_texture_buffer
     *  EXT_texture_cube_map_array
     *  EXT_texture_sRGB_decode
     * </pre>
     * </p>
     * 
     * @param extensionNames
     *            - Collection of extensions names to load. If it contains
     *            ANDROID_EXTENSION_PACK or &quot;aep&quot; , then all 20
     *            required AEP extensions are loaded:
     * 
     * @return updated Collection with all extensions
     */
    public static Collection<String> checkRequestForGroupExtensions(Collection<String> extensionNames){
        
        Set<String> keys = extensionGroupMap.keySet();        
        List<String> groupKeys = contains(extensionNames, keys);
        // skip if nothing to do
        if(groupKeys.size()==0){
            return extensionNames;
        }
        // remove duplicates and put then in order
        Set<String> temp = new TreeSet<String>(extensionNames); 
        
        // check AEP
//         if(groupKeys.contains(ANDROID_EXTENSION_PACK)
//                 || groupKeys.contains("AEP")
//                 || groupKeys.contains("aep"))
//         {
//             for(String aepExtension : GLExtension.AEP){                 
//                 temp.add(aepExtension);
//             }
//             extensionNames.clear();
//             extensionNames.addAll(temp);
//             temp.clear();            
//         }
        
         // process all groups
         for(String key : groupKeys){
             String[] extensions = extensionGroupMap.get(key);
             for(String ext : extensions){
                 temp.add(ext);
             }             
         }
         extensionNames.clear();
         extensionNames.addAll(temp);
         temp.clear();            
         
         return extensionNames;
     }
    
    /**
     * Look for common values is two list.
     * @param list - list to query
     * @param subList - list of searching names
     * @return Collection of names in both sublist and list. 
     */
     private static List<String> contains(Collection<String> list, Collection<String> subList){
         List<String> result = new ArrayList<String>();
         for (String value : subList) {
            if(list.contains(value)){
                result.add(value);
            }
        }         
         return result;
     }
   
//    public List<String> asCforAllLoaders(String apiName){
//        List<String> loaderNames = new ArrayList<String>();
//       // String funcName = "loadAllExtensions" + apiName.toUpperCase();
//        
//        // get all extensions names
//        for (GLExtensionRequire require : requires) {
//            List<GLFunction> funcs = require.getAllFunctions();
//            // skip extensions without functions
//            if(funcs.isEmpty()) continue;
//            
//            loaderNames.add(" require.extName");
//            
//        }//         
//       
//        return loaderNames;
//    }
    
    private static String PFN = "PFN";
    private static String PROC = "PROC"; 
    /**
     * PFNie and PROCfie function.
     *  
     * @param funcName - a function name as glUseProgram
     * @return a function pointer, as PFNGLUSEPROGRAMPROC
     */
    private static String pfnproc(String funcName){        
        return PFN + funcName.trim().toUpperCase() + PROC;
    }
    
   
    /**
     * Get 
     * @return
     */
    public String getKhronosReference() {
        String nameExt = this.name;
        String url = "";
        if (this.apiMode == GLmain.EGL) {
            url += KHRONOS_EGL;
        } else if (this.apiMode == GLmain.GL) {
            url += KHRONOS_GL;
        } else {
            url += KHRONOS_GLES;
        }
        
        String extGroup = null;       
        if (this.apiMode != GLmain.EGL) {
            //GLES & GL
            for (String group : GLES_EXTENSION_GROUP) {
                if (nameExt.contains(group)) {
                    extGroup = group;
                    break;
                }
            }
        } else {
            //EGL
            for (String group : EGL_EXTENSION_GROUP) {
                if (nameExt.contains(group)) {
                    extGroup = group;
                    break;
                }
            }
        }

        url += extGroup;
        url += "/";
        String extName = getExtensionNameSufix();
        extName = fixExtensionNameBug(extName, extGroup);
        url += extName;
        url += ".txt";

        return url;
    }
    
    /**
     * This a few nasty extension name without correct prefix
     * @param extensionName - extensions name
     * @param group - group name, as "KHR", "EXT", etc
     * @return extension name without prefix
     */
    protected String fixExtensionNameBug(String extensionName, String group){  
        if(!group.endsWith("_")){
            group += "_";
        }
        for(String bugName : NAME_BUG){
            if(bugName.equals(extensionName)){
                String fixed = extensionName.replace(group, "");
                return fixed;
            }
        }        
        return extensionName;
    }
    /**
     * Remove GL_ or EGL_ prefix from extension name
     * @return
     */
    protected String getExtensionNameSufix(){
        String nameExt = name;
        String GL_  = "GL_";
       // String EGL_ = "EGL_";
        if(name.startsWith(GL_)){
            nameExt = name.replace(GL_, "");            
        }
        // EGL_ prefix is kept        
        // if(name.startsWith(EGL_)){
        //    nameExt = name.replace(EGL_, "");            
        // }
        return nameExt.trim();
    }
    
    /**
     * Print this Extension set as String
     */
    public String asJavaString(){
        if(apiMode==null) 
            throw new UnsupportedOperationException("apiMode not set. Please call setJavaAPIMode(api)");
        
        //TODO check apiModeValues
        Map<String, GLFunction> mapDatabase = getJavaStringFunctionsMap(apiMode);
        //enumeration section
        StringBuilder sb = new StringBuilder(2*1024);
        // skip enumLESS extensions
        if (getEnumCount() > 0) {
            sb.append("    /**\n");
            sb.append("     * Enumeration for extension: ").append(this.name).append("\n");
            sb.append("     */\n");
            String enums = getJavaEnum(apiMode);
            sb.append(enums);
            sb.append('\n');
        }
        List<GLExtensionRequire> requires = getRequires(apiMode);
        List<String> jFuncStringList = new ArrayList<String>();
        // function section
        for (GLExtensionRequire require : requires) {
            List<GLFunction> funcs = require.getAllFunctions();
            //skip functionLESS extension
            if(funcs==null || funcs.size()==0){
                continue;
            }
            sb.append(" // Function(s) for extension ");
            {
                if(require.extName != null){
                    sb.append(require.extName);
                }
                if(require.api != null){
                    sb.append(", API: ").append(require.api);
                }
                if(require.profile != null){
                    sb.append(", profile: ").append(require.profile);
                } 
            }
            sb.append("   \n");
                       
            
            for(GLFunction func : funcs){
                jFuncStringList = getJavaStringFunction(func, mapDatabase, jFuncStringList);
                String cProto = func.asCProto();
                String cCall = func.asCcallFunction();
                String khronosReg = require.owner.getKhronosReference();
                
                // loop on possible java versions of this func
                for(String asJavaString : jFuncStringList){
                    sb.append("\n")
                    .append(" /**<pre>\n")
                    .append("  *  Extension: ").append(this.name == null?  " ? ": this.name ).append('\n')
                    .append("  * ").append(func.comment==null?  " ": func.comment ).append('\n')
                    .append("  * C Prototype:")
                    .append(addIdentLines(cProto,"* "))   // create function to add * after new line
                    .append("\n")
                    .append("  *</pre>\n")
                    .append("  * See also <a href=\"").append(khronosReg).append("\">").append(this.name).append("</a> ")
                    .append("\n  **/ \n");
                    
                    sb.append(" public final native static ");
                    sb.append(asJavaString)
                    .append("/*\n")
                    .append("\t // jnigen - native goes here")
                    .append(addIdentLines(cCall,"\t"))
                    .append("   */\n");            
                }                
            }            
        }
        
        return sb.toString();
    }
    
    /**
     * Unmap/unroll a n:1 mapping to a 1:n.<br>
     * On Map database, we have n javaStringFunc mapped to 1 GLFunction.
     * This method select those  javaStringFunc, using func 
     * 
     * @param func - key function to select javaStringFunctions
     * @param database - where the query javaStringFunctions
     * @param resultSet - where to put result
     * 
     * @return resulting javaStringFuntions related to func
     */
    public static List<String> getJavaStringFunction(GLFunction func, 
                                                     Map<String, GLFunction> database, 
                                                     List<String> resultSet ){
        resultSet.clear();
        Set<String> keys = database.keySet();
        for (String javaString : keys) {
            GLFunction value = database.get(javaString);
            if(func == value){
                resultSet.add(javaString);
            }            
        }                
        return resultSet;
    }
    
    
    
    /**
     * 
     * @param txt
     * @param identMark
     * @return
     */
    protected static String addIdentLines(String txt, String identMark){
        String mark = "\n  " + identMark;        
        txt = txt.replace("\n", mark);     
        //txt = txt + mark;
        return txt;
    }
    
    /**
     * TODO return a List !
     * 
     * Get Java enumerations for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public String getJavaEnum(String api){
        String enums = "";
        for(GLExtensionRequire require : requires){
            
            if(api == EGL && require.isEGL()){
                enums += require.getJavaEnums();
            }
            
            if(api == GL && require.isGL()){
                enums += require.getJavaEnums();
            }
            
            if(api == GLES1 && require.isGLES1()){
                enums += require.getJavaEnums();
            }
            
            if(api == GLES2 && require.isGLES2()){
                enums += require.getJavaEnums();
            }
        }        
        return enums;
    }
    
    /**
     * Get a list of all GLenum of this extension
     * @param api - api to query
     * @return 
     */
    public List<GLenum> getGLEnumList(String api){
        List<GLenum> list = new ArrayList<GLenum>();
        for(GLExtensionRequire require : requires){
            
            if(api == EGL && require.isEGL()){
                list = require.getEnumsList(list);
            }
            
            if(api == GL && require.isGL()){
                list = require.getEnumsList(list);
            }
            
            if(api == GLES1 && require.isGLES1()){
                list = require.getEnumsList(list);
            }
            
            if(api == GLES2 && require.isGLES2()){
                list = require.getEnumsList(list);
            }
        }        
        return list;
    }
   
    
    /**
     * Get Java functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getJavaStringFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getJavaStringFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){                                
                functions.addAll(require.getJavaStringFunctions());
            }
        }        
        return functions;
    }
    
    /**
     * Get Java functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public Map<String, GLFunction> getJavaStringFunctionsMap(String api){
        Map<String, GLFunction> functions = new HashMap<String, GLFunction>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GL && require.isGL()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GLES1 && require.isGLES1()){
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
            
            if(api == GLES2 && require.isGLES2()){                                
                Map<String, GLFunction> map = require.getJavaMapFunctions();
                functions.putAll(map);
            }
        }        
        return functions;
    }
    
    /**
     * Get C prototype functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getCprototypeFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getCprototypeFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){
                functions.addAll(require.getCprototypeFunctions());
            }
        }        
        return functions;
    }
    
    /**
     * Get C prototype functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<GLExtensionRequire> getRequires(String api){
        List<GLExtensionRequire> listRequires = new ArrayList<GLExtensionRequire>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                listRequires.add(require);
            }
            
            if(api == GL && require.isGL()){
                listRequires.add(require);
            }
            
            if(api == GLES1 && require.isGLES1()){
                listRequires.add(require);
            }
            
            if(api == GLES2 && require.isGLES2()){
                listRequires.add(require);
            }
        }        
        return listRequires;
    }
    
    
    /**
     * Get C calling functions for a given API
     * @param api one of EGL, GL, GLES1 or GLES2.
     * @return
     */
    public List<String> getCcallFunctions(String api){
        List<String> functions = new ArrayList<String>();
        
        for(GLExtensionRequire require : requires){           
            if(api == EGL && require.isEGL()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GL && require.isGL()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GLES1 && require.isGLES1()){
                functions.addAll(require.getCcallFunctions());
            }
            
            if(api == GLES2 && require.isGLES2()){
                functions.addAll(require.getCcallFunctions());
            }
        }        
        return functions;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 20;
        StringBuilder builder = new StringBuilder();
        builder.append("GLExtension [");
        if (name != null) {
            builder.append("extName=");
            builder.append(name);
            builder.append(", ");
        }
        if (supportedAPI != null) {
            builder.append("supported=");
            builder.append(supportedAPI);
            builder.append(", ");
        }
        if (protect != null) {
            builder.append("protect=");
            builder.append(protect);
            builder.append(", ");
        }
        if (requires != null) {
            builder.append("requires=");
            builder.append(requires.subList(0, Math.min(requires.size(), maxLen)));
            builder.append(", ");
        }
        if (isGL != null) {
            builder.append("isGL=");
            builder.append(isGL);
        }
        builder.append("]");
        return builder.toString();
    }


    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
       
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(" ");
            builder.append(iterator.next());
           // builder.append("\n");
        }
       // builder.append("]\n");
        return builder.toString();
    }

    /**
     * Get the number of GLEnum of this GLExtension
     * @return count of GLEnum
     * 
     * @see GLExtensionRequire#getEnumsCount()
     */
    public int getEnumCount(){
        int count = 0;
        for(GLExtensionRequire req : this.requires){
            count = req.getEnumsCount();
        }
        return count;
    }
    
    /**
     * Get the number of Functions of this GLExtension
     * @return count of GLFunctions 
     * 
     * @see GLExtensionRequire#getFunctionCount()
     */
    public int getFunctionCount(){
        int count = 0;
        for(GLExtensionRequire req : this.requires){
            count = req.getFunctionCount();
        }
        return count;
    }
    

    /**
     * Set GLCommands definitions to saved GL functions names
     * @param cmdSet collected function definitions
     */
    public void setCommands(GLcmdSet cmdSet) {
       for(GLExtensionRequire require : requires){
           require.setCommands(cmdSet);
       }        
    }

   
    /**
     * Set GLEnum definitions to saved GL enumeration names
     * @param enumAll collected definitions
     */
    public void setEnumerations(GLenumAll enumAll) {
        for(GLExtensionRequire require : requires){
            require.setEnumerations(enumAll);
        }        
    }


    @Override
    public int compareTo(GLExtension other) {
            return this.name.compareTo(other.name);       
    }
    
}
