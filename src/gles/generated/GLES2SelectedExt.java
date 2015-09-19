//Java ext

  /**
   * Place holder for license disclaimer.
   **/

    package gles.generated;

   // import android.opengl.*;


  /**
   * 
   * Main extension: selection<br>
   * Included extensions: <br>
   * Extension: GL_AMD_compressed_ATC_texture API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 3, Functions: 0 <br>
   * Extension: GL_AMD_performance_monitor API: GL, GL-ES 2.0+, Enumerations: 7, Functions: 11 <br>
   * Extension: GL_AMD_compressed_3DC_texture API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 2, Functions: 0 <br>
   * Extension: GL_QCOM_extended_get API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 11, Functions: 8 <br>
   * Extension: GL_QCOM_extended_get2 API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 0, Functions: 4 <br>
   * Extension: GL_QCOM_tiled_rendering API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 32, Functions: 2 <br>
   * Extension: GL_QCOM_perfmon_global_mode API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 1, Functions: 0 <br>
   * Extension: GL_QCOM_binning_control API: GL-ES 2.0+, Enumerations: 4, Functions: 0 <br>
   * Extension: GL_QCOM_alpha_test API: GL-ES 2.0+, Enumerations: 3, Functions: 1 <br>
   * Extension: GL_QCOM_writeonly_rendering API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 1, Functions: 0 <br>
   * Extension: GL_QCOM_driver_control API: GL-ES 1.x, GL-ES 2.0+, Enumerations: 0, Functions: 4 <br>
   * Extension: GL_AMD_program_binary_Z400 API: GL-ES 2.0+, Enumerations: 1, Functions: 0 <br>
   * 
   **/
   public class GLES2SelectedExt{
  // jnigen header
  //@OFF 
  /*JNI 
  // include section
  // EGL is included for all GL-ES api
    #define EGL_EGLEXT_PROTOTYPES 1
    #include <EGL/egl.h>
    #include <EGL/eglext.h>

  // GLES 2.0+ api
    #define  GL_GLEXT_PROTOTYPES 1 
    #include <GLES2/gl2.h> 
    #include <GLES2/gl2ext.h> 

  // function pointers section 
   // Declaration of FuncPointer vars 
   // GL_AMD_compressed_3DC_texture
   // GL_AMD_compressed_ATC_texture
   // GL_AMD_performance_monitor
   // GL_AMD_program_binary_Z400
   // GL_QCOM_alpha_test
   // GL_QCOM_binning_control
   // GL_QCOM_driver_control
   // GL_QCOM_extended_get
   // GL_QCOM_extended_get2
   // GL_QCOM_perfmon_global_mode
   // GL_QCOM_tiled_rendering
   // GL_QCOM_writeonly_rendering


 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_AMD_performance_monitor
  // API: gl|gles2
 //////////////////////////////////
        static PFNGLGETPERFMONITORGROUPSAMDPROC glGetPerfMonitorGroupsAMD;
        static PFNGLGETPERFMONITORCOUNTERSAMDPROC   glGetPerfMonitorCountersAMD;
        static PFNGLGETPERFMONITORGROUPSTRINGAMDPROC    glGetPerfMonitorGroupStringAMD;
        static PFNGLGETPERFMONITORCOUNTERSTRINGAMDPROC  glGetPerfMonitorCounterStringAMD;
        static PFNGLGETPERFMONITORCOUNTERINFOAMDPROC    glGetPerfMonitorCounterInfoAMD;
        static PFNGLGENPERFMONITORSAMDPROC  glGenPerfMonitorsAMD;
        static PFNGLDELETEPERFMONITORSAMDPROC   glDeletePerfMonitorsAMD;
        static PFNGLSELECTPERFMONITORCOUNTERSAMDPROC    glSelectPerfMonitorCountersAMD;
        static PFNGLBEGINPERFMONITORAMDPROC glBeginPerfMonitorAMD;
        static PFNGLENDPERFMONITORAMDPROC   glEndPerfMonitorAMD;
        static PFNGLGETPERFMONITORCOUNTERDATAAMDPROC    glGetPerfMonitorCounterDataAMD;

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_QCOM_alpha_test
  // API: gles2
 //////////////////////////////////
        static PFNGLALPHAFUNCQCOMPROC   glAlphaFuncQCOM;

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_QCOM_driver_control
  // API: gles1|gles2
 //////////////////////////////////
        static PFNGLGETDRIVERCONTROLSQCOMPROC   glGetDriverControlsQCOM;
        static PFNGLGETDRIVERCONTROLSTRINGQCOMPROC  glGetDriverControlStringQCOM;
        static PFNGLENABLEDRIVERCONTROLQCOMPROC glEnableDriverControlQCOM;
        static PFNGLDISABLEDRIVERCONTROLQCOMPROC    glDisableDriverControlQCOM;

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_QCOM_extended_get
  // API: gles1|gles2
 //////////////////////////////////
        static PFNGLEXTGETTEXTURESQCOMPROC  glExtGetTexturesQCOM;
        static PFNGLEXTGETBUFFERSQCOMPROC   glExtGetBuffersQCOM;
        static PFNGLEXTGETRENDERBUFFERSQCOMPROC glExtGetRenderbuffersQCOM;
        static PFNGLEXTGETFRAMEBUFFERSQCOMPROC  glExtGetFramebuffersQCOM;
        static PFNGLEXTGETTEXLEVELPARAMETERIVQCOMPROC   glExtGetTexLevelParameterivQCOM;
        static PFNGLEXTTEXOBJECTSTATEOVERRIDEIQCOMPROC  glExtTexObjectStateOverrideiQCOM;
        static PFNGLEXTGETTEXSUBIMAGEQCOMPROC   glExtGetTexSubImageQCOM;
        static PFNGLEXTGETBUFFERPOINTERVQCOMPROC    glExtGetBufferPointervQCOM;

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_QCOM_extended_get2
  // API: gles1|gles2
 //////////////////////////////////
        static PFNGLEXTGETSHADERSQCOMPROC   glExtGetShadersQCOM;
        static PFNGLEXTGETPROGRAMSQCOMPROC  glExtGetProgramsQCOM;
        static PFNGLEXTISPROGRAMBINARYQCOMPROC  glExtIsProgramBinaryQCOM;
        static PFNGLEXTGETPROGRAMBINARYSOURCEQCOMPROC   glExtGetProgramBinarySourceQCOM;

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: GL_QCOM_tiled_rendering
  // API: gles1|gles2
 //////////////////////////////////
        static PFNGLSTARTTILINGQCOMPROC glStartTilingQCOM;
        static PFNGLENDTILINGQCOMPROC   glEndTilingQCOM;  // extension loaders
  // function loader
  #define myFuncLoader(x) eglGetProcAddress(x)

   // Declaration of LOADERS for function pointers - PFN_PROC 
   // GL_AMD_compressed_3DC_texture
   // GL_AMD_compressed_ATC_texture
   // GL_AMD_performance_monitor
   // GL_AMD_program_binary_Z400
   // GL_QCOM_alpha_test
   // GL_QCOM_binning_control
   // GL_QCOM_driver_control
   // GL_QCOM_extended_get
   // GL_QCOM_extended_get2
   // GL_QCOM_perfmon_global_mode
   // GL_QCOM_tiled_rendering
   // GL_QCOM_writeonly_rendering

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_AMD_performance_monitor
  // API: gl|gles2
  /// =====================================/
  static  int loadExtFuncGL_AMD_performance_monitor(){
      glGetPerfMonitorGroupsAMD = (PFNGLGETPERFMONITORGROUPSAMDPROC) myFuncLoader("glGetPerfMonitorGroupsAMD");
      glGetPerfMonitorCountersAMD = (PFNGLGETPERFMONITORCOUNTERSAMDPROC) myFuncLoader("glGetPerfMonitorCountersAMD");
      glGetPerfMonitorGroupStringAMD = (PFNGLGETPERFMONITORGROUPSTRINGAMDPROC) myFuncLoader("glGetPerfMonitorGroupStringAMD");
      glGetPerfMonitorCounterStringAMD = (PFNGLGETPERFMONITORCOUNTERSTRINGAMDPROC) myFuncLoader("glGetPerfMonitorCounterStringAMD");
      glGetPerfMonitorCounterInfoAMD = (PFNGLGETPERFMONITORCOUNTERINFOAMDPROC) myFuncLoader("glGetPerfMonitorCounterInfoAMD");
      glGenPerfMonitorsAMD = (PFNGLGENPERFMONITORSAMDPROC) myFuncLoader("glGenPerfMonitorsAMD");
      glDeletePerfMonitorsAMD = (PFNGLDELETEPERFMONITORSAMDPROC) myFuncLoader("glDeletePerfMonitorsAMD");
      glSelectPerfMonitorCountersAMD = (PFNGLSELECTPERFMONITORCOUNTERSAMDPROC) myFuncLoader("glSelectPerfMonitorCountersAMD");
      glBeginPerfMonitorAMD = (PFNGLBEGINPERFMONITORAMDPROC) myFuncLoader("glBeginPerfMonitorAMD");
      glEndPerfMonitorAMD = (PFNGLENDPERFMONITORAMDPROC) myFuncLoader("glEndPerfMonitorAMD");
      glGetPerfMonitorCounterDataAMD = (PFNGLGETPERFMONITORCOUNTERDATAAMDPROC) myFuncLoader("glGetPerfMonitorCounterDataAMD");

      return 1;
     } // end of loadExtFuncGL_AMD_performance_monitor()

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_QCOM_alpha_test
  // API: gles2
  /// =====================================/
  static  int loadExtFuncGL_QCOM_alpha_test(){
      glAlphaFuncQCOM = (PFNGLALPHAFUNCQCOMPROC) myFuncLoader("glAlphaFuncQCOM");

      return 1;
     } // end of loadExtFuncGL_QCOM_alpha_test()

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_QCOM_driver_control
  // API: gles1|gles2
  /// =====================================/
  static  int loadExtFuncGL_QCOM_driver_control(){
      glGetDriverControlsQCOM = (PFNGLGETDRIVERCONTROLSQCOMPROC) myFuncLoader("glGetDriverControlsQCOM");
      glGetDriverControlStringQCOM = (PFNGLGETDRIVERCONTROLSTRINGQCOMPROC) myFuncLoader("glGetDriverControlStringQCOM");
      glEnableDriverControlQCOM = (PFNGLENABLEDRIVERCONTROLQCOMPROC) myFuncLoader("glEnableDriverControlQCOM");
      glDisableDriverControlQCOM = (PFNGLDISABLEDRIVERCONTROLQCOMPROC) myFuncLoader("glDisableDriverControlQCOM");

      return 1;
     } // end of loadExtFuncGL_QCOM_driver_control()

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_QCOM_extended_get
  // API: gles1|gles2
  /// =====================================/
  static  int loadExtFuncGL_QCOM_extended_get(){
      glExtGetTexturesQCOM = (PFNGLEXTGETTEXTURESQCOMPROC) myFuncLoader("glExtGetTexturesQCOM");
      glExtGetBuffersQCOM = (PFNGLEXTGETBUFFERSQCOMPROC) myFuncLoader("glExtGetBuffersQCOM");
      glExtGetRenderbuffersQCOM = (PFNGLEXTGETRENDERBUFFERSQCOMPROC) myFuncLoader("glExtGetRenderbuffersQCOM");
      glExtGetFramebuffersQCOM = (PFNGLEXTGETFRAMEBUFFERSQCOMPROC) myFuncLoader("glExtGetFramebuffersQCOM");
      glExtGetTexLevelParameterivQCOM = (PFNGLEXTGETTEXLEVELPARAMETERIVQCOMPROC) myFuncLoader("glExtGetTexLevelParameterivQCOM");
      glExtTexObjectStateOverrideiQCOM = (PFNGLEXTTEXOBJECTSTATEOVERRIDEIQCOMPROC) myFuncLoader("glExtTexObjectStateOverrideiQCOM");
      glExtGetTexSubImageQCOM = (PFNGLEXTGETTEXSUBIMAGEQCOMPROC) myFuncLoader("glExtGetTexSubImageQCOM");
      glExtGetBufferPointervQCOM = (PFNGLEXTGETBUFFERPOINTERVQCOMPROC) myFuncLoader("glExtGetBufferPointervQCOM");

      return 1;
     } // end of loadExtFuncGL_QCOM_extended_get()

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_QCOM_extended_get2
  // API: gles1|gles2
  /// =====================================/
  static  int loadExtFuncGL_QCOM_extended_get2(){
      glExtGetShadersQCOM = (PFNGLEXTGETSHADERSQCOMPROC) myFuncLoader("glExtGetShadersQCOM");
      glExtGetProgramsQCOM = (PFNGLEXTGETPROGRAMSQCOMPROC) myFuncLoader("glExtGetProgramsQCOM");
      glExtIsProgramBinaryQCOM = (PFNGLEXTISPROGRAMBINARYQCOMPROC) myFuncLoader("glExtIsProgramBinaryQCOM");
      glExtGetProgramBinarySourceQCOM = (PFNGLEXTGETPROGRAMBINARYSOURCEQCOMPROC) myFuncLoader("glExtGetProgramBinarySourceQCOM");

      return 1;
     } // end of loadExtFuncGL_QCOM_extended_get2()

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: GL_QCOM_tiled_rendering
  // API: gles1|gles2
  /// =====================================/
  static  int loadExtFuncGL_QCOM_tiled_rendering(){
      glStartTilingQCOM = (PFNGLSTARTTILINGQCOMPROC) myFuncLoader("glStartTilingQCOM");
      glEndTilingQCOM = (PFNGLENDTILINGQCOMPROC) myFuncLoader("glEndTilingQCOM");

      return 1;
     } // end of loadExtFuncGL_QCOM_tiled_rendering()


   // Declaration of loadALL(), to call all PFN_PROC pointers 
 static int loadALL(){
     loadExtFuncGL_AMD_compressed_3DC_texture();
     loadExtFuncGL_AMD_compressed_ATC_texture();
     loadExtFuncGL_AMD_performance_monitor();
     loadExtFuncGL_AMD_program_binary_Z400();
     loadExtFuncGL_QCOM_alpha_test();
     loadExtFuncGL_QCOM_binning_control();
     loadExtFuncGL_QCOM_driver_control();
     loadExtFuncGL_QCOM_extended_get();
     loadExtFuncGL_QCOM_extended_get2();
     loadExtFuncGL_QCOM_perfmon_global_mode();
     loadExtFuncGL_QCOM_tiled_rendering();
     loadExtFuncGL_QCOM_writeonly_rendering();
       return 1;
   } // loadALL()
  */
 // end of jnigen header

  static protected native void init();/* 
    loadAll();
  */

  /** loading native stuff */
  static{
    init();
  }

    /**
     * Enumeration for extension: GL_AMD_compressed_3DC_texture
     */
    // enumerations: 
     public static final int   GL_3DC_X_AMD = 0x87F9;
     public static final int   GL_3DC_XY_AMD = 0x87FA;

 
    /**
     * Enumeration for extension: GL_AMD_compressed_ATC_texture
     */
    // enumerations: 
     public static final int   GL_ATC_RGB_AMD = 0x8C92;
     public static final int   GL_ATC_RGBA_EXPLICIT_ALPHA_AMD = 0x8C93;
     public static final int   GL_ATC_RGBA_INTERPOLATED_ALPHA_AMD = 0x87EE;

 
    /**
     * Enumeration for extension: GL_AMD_performance_monitor
     */
    // enumerations: 
     public static final int   GL_COUNTER_TYPE_AMD = 0x8BC0;
     public static final int   GL_COUNTER_RANGE_AMD = 0x8BC1;
     public static final int   GL_UNSIGNED_INT64_AMD = 0x8BC2;
     public static final int   GL_PERCENTAGE_AMD = 0x8BC3;
     public static final int   GL_PERFMON_RESULT_AVAILABLE_AMD = 0x8BC4;
     public static final int   GL_PERFMON_RESULT_SIZE_AMD = 0x8BC5;
     public static final int   GL_PERFMON_RESULT_AMD = 0x8BC6;

 
 // Function(s) for extension GL_AMD_performance_monitor, API: gl|gles2   

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorGroupsAMD ( 
  *          GLint  * numGroups,
  *          GLsizei   groupsSize,
  *          GLuint  * groups
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorGroupsAMD( java.nio.IntBuffer numGroups, int numGroupsOffset,
                                 int groupsSize,
                                 java.nio.IntBuffer groups, int groupsOffset); /*
     // jnigen - native goes here
     glGetPerfMonitorGroupsAMD( (GLint  *) (numGroups + numGroupsOffset),
                                (GLsizei) groupsSize,
                                (GLuint  *) (groups + groupsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorGroupsAMD ( 
  *          GLint  * numGroups,
  *          GLsizei   groupsSize,
  *          GLuint  * groups
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGetPerfMonitorGroupsAMD( int[] numGroups, int numGroupsOffset,
                                  int groupsSize,
                                  int[] groups, int groupsOffset);/*
     // jnigen - native goes here
     glGetPerfMonitorGroupsAMD( (GLint  *) (numGroups + numGroupsOffset),
                                (GLsizei) groupsSize,
                                (GLuint  *) (groups + groupsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCountersAMD ( 
  *          GLuint   group,
  *          GLint  * numCounters,
  *          GLint  * maxActiveCounters,
  *          GLsizei   counterSize,
  *          GLuint  * counters
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorCountersAMD( int group,
                                   java.nio.IntBuffer numCounters, int numCountersOffset,
                                   java.nio.IntBuffer maxActiveCounters, int maxActiveCountersOffset,
                                   int counterSize,
                                   java.nio.IntBuffer counters, int countersOffset); /*
     // jnigen - native goes here
     glGetPerfMonitorCountersAMD( (GLuint) group,
                                  (GLint  *) (numCounters + numCountersOffset),
                                  (GLint  *) (maxActiveCounters + maxActiveCountersOffset),
                                  (GLsizei) counterSize,
                                  (GLuint  *) (counters + countersOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCountersAMD ( 
  *          GLuint   group,
  *          GLint  * numCounters,
  *          GLint  * maxActiveCounters,
  *          GLsizei   counterSize,
  *          GLuint  * counters
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGetPerfMonitorCountersAMD( int group,
                                    int[] numCounters, int numCountersOffset,
                                    int[] maxActiveCounters, int maxActiveCountersOffset,
                                    int counterSize,
                                    int[] counters, int countersOffset);/*
     // jnigen - native goes here
     glGetPerfMonitorCountersAMD( (GLuint) group,
                                  (GLint  *) (numCounters + numCountersOffset),
                                  (GLint  *) (maxActiveCounters + maxActiveCountersOffset),
                                  (GLsizei) counterSize,
                                  (GLuint  *) (counters + countersOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorGroupStringAMD ( 
  *          GLuint   group,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * groupString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorGroupStringAMD( int group,
                                      int bufSize,
                                      java.nio.IntBuffer length, int lengthOffset,
                                      String groupString); /*
     // jnigen - native goes here
     glGetPerfMonitorGroupStringAMD( (GLuint) group,
                                     (GLsizei) bufSize,
                                     (GLsizei  *) (length + lengthOffset),
                                     (GLchar  *) (groupString + groupStringOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorGroupStringAMD ( 
  *          GLuint   group,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * groupString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGetPerfMonitorGroupStringAMD( int group,
                                       int bufSize,
                                       int[] length, int lengthOffset,
                                       String groupString, int groupStringOffset);/*
     // jnigen - native goes here
     glGetPerfMonitorGroupStringAMD( (GLuint) group,
                                     (GLsizei) bufSize,
                                     (GLsizei  *) (length + lengthOffset),
                                     (GLchar  *) (groupString + groupStringOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCounterStringAMD ( 
  *          GLuint   group,
  *          GLuint   counter,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * counterString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGetPerfMonitorCounterStringAMD( int group,
                                         int counter,
                                         int bufSize,
                                         int[] length, int lengthOffset,
                                         String counterString, int counterStringOffset);/*
     // jnigen - native goes here
     glGetPerfMonitorCounterStringAMD( (GLuint) group,
                                       (GLuint) counter,
                                       (GLsizei) bufSize,
                                       (GLsizei  *) (length + lengthOffset),
                                       (GLchar  *) (counterString + counterStringOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCounterStringAMD ( 
  *          GLuint   group,
  *          GLuint   counter,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * counterString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorCounterStringAMD( int group,
                                        int counter,
                                        int bufSize,
                                        java.nio.IntBuffer length, int lengthOffset,
                                        String counterString); /*
     // jnigen - native goes here
     glGetPerfMonitorCounterStringAMD( (GLuint) group,
                                       (GLuint) counter,
                                       (GLsizei) bufSize,
                                       (GLsizei  *) (length + lengthOffset),
                                       (GLchar  *) (counterString + counterStringOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCounterInfoAMD ( 
  *          GLuint   group,
  *          GLuint   counter,
  *          GLenum   pname,
  *          void * data
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorCounterInfoAMD( int group,
                                      int counter,
                                      int pname,
                                      java.nio.Buffer data, int dataOffset); /*
     // jnigen - native goes here
     glGetPerfMonitorCounterInfoAMD( (GLuint) group,
                                     (GLuint) counter,
                                     (GLenum) pname,
                                     (void *) (data + dataOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGenPerfMonitorsAMD ( 
  *          GLsizei   n,
  *          GLuint  * monitors
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGenPerfMonitorsAMD( int n,
                             int[] monitors, int monitorsOffset);/*
     // jnigen - native goes here
     glGenPerfMonitorsAMD( (GLsizei) n,
                           (GLuint  *) (monitors + monitorsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGenPerfMonitorsAMD ( 
  *          GLsizei   n,
  *          GLuint  * monitors
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGenPerfMonitorsAMD( int n,
                            java.nio.IntBuffer monitors, int monitorsOffset); /*
     // jnigen - native goes here
     glGenPerfMonitorsAMD( (GLsizei) n,
                           (GLuint  *) (monitors + monitorsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glDeletePerfMonitorsAMD ( 
  *          GLsizei   n,
  *          GLuint  * monitors
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glDeletePerfMonitorsAMD( int n,
                                int[] monitors, int monitorsOffset);/*
     // jnigen - native goes here
     glDeletePerfMonitorsAMD( (GLsizei) n,
                              (GLuint  *) (monitors + monitorsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glDeletePerfMonitorsAMD ( 
  *          GLsizei   n,
  *          GLuint  * monitors
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glDeletePerfMonitorsAMD( int n,
                               java.nio.IntBuffer monitors, int monitorsOffset); /*
     // jnigen - native goes here
     glDeletePerfMonitorsAMD( (GLsizei) n,
                              (GLuint  *) (monitors + monitorsOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glSelectPerfMonitorCountersAMD ( 
  *          GLuint   monitor,
  *          GLboolean   enable, // group=Boolean  
  *          GLuint   group,
  *          GLint   numCounters,
  *          GLuint  * counterList
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glSelectPerfMonitorCountersAMD( int monitor,
                                       boolean enable   /*   group=Boolean */,
                                       int group,
                                       int numCounters,
                                       int[] counterList, int counterListOffset);/*
     // jnigen - native goes here
     glSelectPerfMonitorCountersAMD( (GLuint) monitor,
                                     (GLboolean) enable,
                                     (GLuint) group,
                                     (GLint) numCounters,
                                     (GLuint  *) (counterList + counterListOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glSelectPerfMonitorCountersAMD ( 
  *          GLuint   monitor,
  *          GLboolean   enable, // group=Boolean  
  *          GLuint   group,
  *          GLint   numCounters,
  *          GLuint  * counterList
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glSelectPerfMonitorCountersAMD( int monitor,
                                      boolean enable    /*   group=Boolean */,
                                      int group,
                                      int numCounters,
                                      java.nio.IntBuffer counterList, int counterListOffset); /*
     // jnigen - native goes here
     glSelectPerfMonitorCountersAMD( (GLuint) monitor,
                                     (GLboolean) enable,
                                     (GLuint) group,
                                     (GLint) numCounters,
                                     (GLuint  *) (counterList + counterListOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glBeginPerfMonitorAMD ( 
  *          GLuint   monitor
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glBeginPerfMonitorAMD (int monitor);/*
     // jnigen - native goes here
     glBeginPerfMonitorAMD( (GLuint) monitor);
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glEndPerfMonitorAMD ( 
  *          GLuint   monitor
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glEndPerfMonitorAMD (int monitor);/*
     // jnigen - native goes here
     glEndPerfMonitorAMD( (GLuint) monitor);
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCounterDataAMD ( 
  *          GLuint   monitor,
  *          GLenum   pname,
  *          GLsizei   dataSize,
  *          GLuint  * data,
  *          GLint  * bytesWritten
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
  void glGetPerfMonitorCounterDataAMD( int monitor,
                                       int pname,
                                       int dataSize,
                                       int[] data, int dataOffset,
                                       int[] bytesWritten, int bytesWrittenOffset);/*
     // jnigen - native goes here
     glGetPerfMonitorCounterDataAMD( (GLuint) monitor,
                                     (GLenum) pname,
                                     (GLsizei) dataSize,
                                     (GLuint  *) (data + dataOffset),
                                     (GLint  *) (bytesWritten + bytesWrittenOffset));
       */

 /**<pre>
  *  Extension: GL_AMD_performance_monitor
  *  
  * C Prototype:
  * void  glGetPerfMonitorCounterDataAMD ( 
  *          GLuint   monitor,
  *          GLenum   pname,
  *          GLsizei   dataSize,
  *          GLuint  * data,
  *          GLint  * bytesWritten
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt">https://www.khronos.org/registry/gles/extensions/AMD/performance_monitor.txt</a> 
  **/ 
 public final native static 
 void glGetPerfMonitorCounterDataAMD( int monitor,
                                      int pname,
                                      int dataSize,
                                      java.nio.IntBuffer data, int dataOffset,
                                      java.nio.IntBuffer bytesWritten, int bytesWrittenOffset); /*
     // jnigen - native goes here
     glGetPerfMonitorCounterDataAMD( (GLuint) monitor,
                                     (GLenum) pname,
                                     (GLsizei) dataSize,
                                     (GLuint  *) (data + dataOffset),
                                     (GLint  *) (bytesWritten + bytesWrittenOffset));
       */
    /**
     * Enumeration for extension: GL_AMD_program_binary_Z400
     */
    // enumerations: 
     public static final int   GL_Z400_BINARY_AMD = 0x8740;

 
    /**
     * Enumeration for extension: GL_QCOM_alpha_test
     */
    // enumerations: 
     public static final int   GL_ALPHA_TEST_QCOM = 0x0BC0;
     public static final int   GL_ALPHA_TEST_FUNC_QCOM = 0x0BC1;
     public static final int   GL_ALPHA_TEST_REF_QCOM = 0x0BC2;

 
 // Function(s) for extension GL_QCOM_alpha_test, API: gles2   

 /**<pre>
  *  Extension: GL_QCOM_alpha_test
  *  
  * C Prototype:
  * void  glAlphaFuncQCOM ( 
  *          GLenum   func,
  *          GLclampf   ref
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_alpha_test.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_alpha_test.txt</a> 
  **/ 
 public final native static 
  void glAlphaFuncQCOM (int func,
                         float ref);/*
     // jnigen - native goes here
     glAlphaFuncQCOM( (GLenum) func,
                      (GLclampf) ref);
       */
    /**
     * Enumeration for extension: GL_QCOM_binning_control
     */
    // enumerations: 
     public static final int   GL_BINNING_CONTROL_HINT_QCOM = 0x8FB0;
     public static final int   GL_CPU_OPTIMIZED_QCOM = 0x8FB1;
     public static final int   GL_GPU_OPTIMIZED_QCOM = 0x8FB2;
     public static final int   GL_RENDER_DIRECT_TO_FRAMEBUFFER_QCOM = 0x8FB3;

 
 // Function(s) for extension GL_QCOM_driver_control, API: gles1|gles2   

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glGetDriverControlsQCOM ( 
  *          GLint  * num,
  *          GLsizei   size,
  *          GLuint  * driverControls
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
 void glGetDriverControlsQCOM( java.nio.IntBuffer num, int numOffset,
                               int size,
                               java.nio.IntBuffer driverControls, int driverControlsOffset); /*
     // jnigen - native goes here
     glGetDriverControlsQCOM( (GLint  *) (num + numOffset),
                              (GLsizei) size,
                              (GLuint  *) (driverControls + driverControlsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glGetDriverControlsQCOM ( 
  *          GLint  * num,
  *          GLsizei   size,
  *          GLuint  * driverControls
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
  void glGetDriverControlsQCOM( int[] num, int numOffset,
                                int size,
                                int[] driverControls, int driverControlsOffset);/*
     // jnigen - native goes here
     glGetDriverControlsQCOM( (GLint  *) (num + numOffset),
                              (GLsizei) size,
                              (GLuint  *) (driverControls + driverControlsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glGetDriverControlStringQCOM ( 
  *          GLuint   driverControl,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * driverControlString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
  void glGetDriverControlStringQCOM( int driverControl,
                                     int bufSize,
                                     int[] length, int lengthOffset,
                                     String driverControlString, int driverControlStringOffset);/*
     // jnigen - native goes here
     glGetDriverControlStringQCOM( (GLuint) driverControl,
                                   (GLsizei) bufSize,
                                   (GLsizei  *) (length + lengthOffset),
                                   (GLchar  *) (driverControlString + driverControlStringOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glGetDriverControlStringQCOM ( 
  *          GLuint   driverControl,
  *          GLsizei   bufSize,
  *          GLsizei  * length,
  *          GLchar  * driverControlString
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
 void glGetDriverControlStringQCOM( int driverControl,
                                    int bufSize,
                                    java.nio.IntBuffer length, int lengthOffset,
                                    String driverControlString); /*
     // jnigen - native goes here
     glGetDriverControlStringQCOM( (GLuint) driverControl,
                                   (GLsizei) bufSize,
                                   (GLsizei  *) (length + lengthOffset),
                                   (GLchar  *) (driverControlString + driverControlStringOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glEnableDriverControlQCOM ( 
  *          GLuint   driverControl
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
  void glEnableDriverControlQCOM (int driverControl);/*
     // jnigen - native goes here
     glEnableDriverControlQCOM( (GLuint) driverControl);
       */

 /**<pre>
  *  Extension: GL_QCOM_driver_control
  *  
  * C Prototype:
  * void  glDisableDriverControlQCOM ( 
  *          GLuint   driverControl
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_driver_control.txt</a> 
  **/ 
 public final native static 
  void glDisableDriverControlQCOM (int driverControl);/*
     // jnigen - native goes here
     glDisableDriverControlQCOM( (GLuint) driverControl);
       */
    /**
     * Enumeration for extension: GL_QCOM_extended_get
     */
    // enumerations: 
     public static final int   GL_TEXTURE_WIDTH_QCOM = 0x8BD2;
     public static final int   GL_TEXTURE_HEIGHT_QCOM = 0x8BD3;
     public static final int   GL_TEXTURE_DEPTH_QCOM = 0x8BD4;
     public static final int   GL_TEXTURE_INTERNAL_FORMAT_QCOM = 0x8BD5;
     public static final int   GL_TEXTURE_FORMAT_QCOM = 0x8BD6;
     public static final int   GL_TEXTURE_TYPE_QCOM = 0x8BD7;
     public static final int   GL_TEXTURE_IMAGE_VALID_QCOM = 0x8BD8;
     public static final int   GL_TEXTURE_NUM_LEVELS_QCOM = 0x8BD9;
     public static final int   GL_TEXTURE_TARGET_QCOM = 0x8BDA;
     public static final int   GL_TEXTURE_OBJECT_VALID_QCOM = 0x8BDB;
     public static final int   GL_STATE_RESTORE = 0x8BDC;

 
 // Function(s) for extension GL_QCOM_extended_get, API: gles1|gles2   

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetTexturesQCOM ( 
  *          GLuint  * textures,
  *          GLint   maxTextures,
  *          GLint  * numTextures
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetTexturesQCOM( java.nio.IntBuffer textures, int texturesOffset,
                            int maxTextures,
                            java.nio.IntBuffer numTextures, int numTexturesOffset); /*
     // jnigen - native goes here
     glExtGetTexturesQCOM( (GLuint  *) (textures + texturesOffset),
                           (GLint) maxTextures,
                           (GLint  *) (numTextures + numTexturesOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetTexturesQCOM ( 
  *          GLuint  * textures,
  *          GLint   maxTextures,
  *          GLint  * numTextures
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtGetTexturesQCOM( int[] textures, int texturesOffset,
                             int maxTextures,
                             int[] numTextures, int numTexturesOffset);/*
     // jnigen - native goes here
     glExtGetTexturesQCOM( (GLuint  *) (textures + texturesOffset),
                           (GLint) maxTextures,
                           (GLint  *) (numTextures + numTexturesOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetBuffersQCOM ( 
  *          GLuint  * buffers,
  *          GLint   maxBuffers,
  *          GLint  * numBuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetBuffersQCOM( java.nio.IntBuffer buffers, int buffersOffset,
                           int maxBuffers,
                           java.nio.IntBuffer numBuffers, int numBuffersOffset); /*
     // jnigen - native goes here
     glExtGetBuffersQCOM( (GLuint  *) (buffers + buffersOffset),
                          (GLint) maxBuffers,
                          (GLint  *) (numBuffers + numBuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetBuffersQCOM ( 
  *          GLuint  * buffers,
  *          GLint   maxBuffers,
  *          GLint  * numBuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtGetBuffersQCOM( int[] buffers, int buffersOffset,
                            int maxBuffers,
                            int[] numBuffers, int numBuffersOffset);/*
     // jnigen - native goes here
     glExtGetBuffersQCOM( (GLuint  *) (buffers + buffersOffset),
                          (GLint) maxBuffers,
                          (GLint  *) (numBuffers + numBuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetRenderbuffersQCOM ( 
  *          GLuint  * renderbuffers,
  *          GLint   maxRenderbuffers,
  *          GLint  * numRenderbuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtGetRenderbuffersQCOM( int[] renderbuffers, int renderbuffersOffset,
                                  int maxRenderbuffers,
                                  int[] numRenderbuffers, int numRenderbuffersOffset);/*
     // jnigen - native goes here
     glExtGetRenderbuffersQCOM( (GLuint  *) (renderbuffers + renderbuffersOffset),
                                (GLint) maxRenderbuffers,
                                (GLint  *) (numRenderbuffers + numRenderbuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetRenderbuffersQCOM ( 
  *          GLuint  * renderbuffers,
  *          GLint   maxRenderbuffers,
  *          GLint  * numRenderbuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetRenderbuffersQCOM( java.nio.IntBuffer renderbuffers, int renderbuffersOffset,
                                 int maxRenderbuffers,
                                 java.nio.IntBuffer numRenderbuffers, int numRenderbuffersOffset); /*
     // jnigen - native goes here
     glExtGetRenderbuffersQCOM( (GLuint  *) (renderbuffers + renderbuffersOffset),
                                (GLint) maxRenderbuffers,
                                (GLint  *) (numRenderbuffers + numRenderbuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetFramebuffersQCOM ( 
  *          GLuint  * framebuffers,
  *          GLint   maxFramebuffers,
  *          GLint  * numFramebuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetFramebuffersQCOM( java.nio.IntBuffer framebuffers, int framebuffersOffset,
                                int maxFramebuffers,
                                java.nio.IntBuffer numFramebuffers, int numFramebuffersOffset); /*
     // jnigen - native goes here
     glExtGetFramebuffersQCOM( (GLuint  *) (framebuffers + framebuffersOffset),
                               (GLint) maxFramebuffers,
                               (GLint  *) (numFramebuffers + numFramebuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetFramebuffersQCOM ( 
  *          GLuint  * framebuffers,
  *          GLint   maxFramebuffers,
  *          GLint  * numFramebuffers
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtGetFramebuffersQCOM( int[] framebuffers, int framebuffersOffset,
                                 int maxFramebuffers,
                                 int[] numFramebuffers, int numFramebuffersOffset);/*
     // jnigen - native goes here
     glExtGetFramebuffersQCOM( (GLuint  *) (framebuffers + framebuffersOffset),
                               (GLint) maxFramebuffers,
                               (GLint  *) (numFramebuffers + numFramebuffersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetTexLevelParameterivQCOM ( 
  *          GLuint   texture,
  *          GLenum   face,
  *          GLint   level,
  *          GLenum   pname,
  *          GLint  * params
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtGetTexLevelParameterivQCOM( int texture,
                                        int face,
                                        int level,
                                        int pname,
                                        int[] params, int paramsOffset);/*
     // jnigen - native goes here
     glExtGetTexLevelParameterivQCOM( (GLuint) texture,
                                      (GLenum) face,
                                      (GLint) level,
                                      (GLenum) pname,
                                      (GLint  *) (params + paramsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetTexLevelParameterivQCOM ( 
  *          GLuint   texture,
  *          GLenum   face,
  *          GLint   level,
  *          GLenum   pname,
  *          GLint  * params
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetTexLevelParameterivQCOM( int texture,
                                       int face,
                                       int level,
                                       int pname,
                                       java.nio.IntBuffer params, int paramsOffset); /*
     // jnigen - native goes here
     glExtGetTexLevelParameterivQCOM( (GLuint) texture,
                                      (GLenum) face,
                                      (GLint) level,
                                      (GLenum) pname,
                                      (GLint  *) (params + paramsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtTexObjectStateOverrideiQCOM ( 
  *          GLenum   target,
  *          GLenum   pname,
  *          GLint   param
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
  void glExtTexObjectStateOverrideiQCOM (int target,
                                          int pname,
                                          int param);/*
     // jnigen - native goes here
     glExtTexObjectStateOverrideiQCOM( (GLenum) target,
                                       (GLenum) pname,
                                       (GLint) param);
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetTexSubImageQCOM ( 
  *          GLenum   target,
  *          GLint   level,
  *          GLint   xoffset,
  *          GLint   yoffset,
  *          GLint   zoffset,
  *          GLsizei   width,
  *          GLsizei   height,
  *          GLsizei   depth,
  *          GLenum   format,
  *          GLenum   type,
  *          void * texels
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetTexSubImageQCOM( int target,
                               int level,
                               int xoffset,
                               int yoffset,
                               int zoffset,
                               int width,
                               int height,
                               int depth,
                               int format,
                               int type,
                               java.nio.Buffer texels, int texelsOffset); /*
     // jnigen - native goes here
     glExtGetTexSubImageQCOM( (GLenum) target,
                              (GLint) level,
                              (GLint) xoffset,
                              (GLint) yoffset,
                              (GLint) zoffset,
                              (GLsizei) width,
                              (GLsizei) height,
                              (GLsizei) depth,
                              (GLenum) format,
                              (GLenum) type,
                              (void *) (texels + texelsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get
  *  
  * C Prototype:
  * void  glExtGetBufferPointervQCOM ( 
  *          GLenum   target,
  *          void ** params
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get.txt</a> 
  **/ 
 public final native static 
 void glExtGetBufferPointervQCOM( int target,
                                  java.nio.Buffer params, int paramsOffset); /*
     // jnigen - native goes here
     glExtGetBufferPointervQCOM( (GLenum) target,
                                 (void **) (params + paramsOffset));
       */
 // Function(s) for extension GL_QCOM_extended_get2, API: gles1|gles2   

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetShadersQCOM ( 
  *          GLuint  * shaders,
  *          GLint   maxShaders,
  *          GLint  * numShaders
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
 void glExtGetShadersQCOM( java.nio.IntBuffer shaders, int shadersOffset,
                           int maxShaders,
                           java.nio.IntBuffer numShaders, int numShadersOffset); /*
     // jnigen - native goes here
     glExtGetShadersQCOM( (GLuint  *) (shaders + shadersOffset),
                          (GLint) maxShaders,
                          (GLint  *) (numShaders + numShadersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetShadersQCOM ( 
  *          GLuint  * shaders,
  *          GLint   maxShaders,
  *          GLint  * numShaders
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
  void glExtGetShadersQCOM( int[] shaders, int shadersOffset,
                            int maxShaders,
                            int[] numShaders, int numShadersOffset);/*
     // jnigen - native goes here
     glExtGetShadersQCOM( (GLuint  *) (shaders + shadersOffset),
                          (GLint) maxShaders,
                          (GLint  *) (numShaders + numShadersOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetProgramsQCOM ( 
  *          GLuint  * programs,
  *          GLint   maxPrograms,
  *          GLint  * numPrograms
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
 void glExtGetProgramsQCOM( java.nio.IntBuffer programs, int programsOffset,
                            int maxPrograms,
                            java.nio.IntBuffer numPrograms, int numProgramsOffset); /*
     // jnigen - native goes here
     glExtGetProgramsQCOM( (GLuint  *) (programs + programsOffset),
                           (GLint) maxPrograms,
                           (GLint  *) (numPrograms + numProgramsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetProgramsQCOM ( 
  *          GLuint  * programs,
  *          GLint   maxPrograms,
  *          GLint  * numPrograms
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
  void glExtGetProgramsQCOM( int[] programs, int programsOffset,
                             int maxPrograms,
                             int[] numPrograms, int numProgramsOffset);/*
     // jnigen - native goes here
     glExtGetProgramsQCOM( (GLuint  *) (programs + programsOffset),
                           (GLint) maxPrograms,
                           (GLint  *) (numPrograms + numProgramsOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  *   GLboolean glExtIsProgramBinaryQCOM ( 
  *          GLuint   program
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
  boolean glExtIsProgramBinaryQCOM (int program);/*
     // jnigen - native goes here
       return (jboolean)  glExtIsProgramBinaryQCOM( (GLuint) program);
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetProgramBinarySourceQCOM ( 
  *          GLuint   program,
  *          GLenum   shadertype,
  *          GLchar  * source,
  *          GLint  * length
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
  void glExtGetProgramBinarySourceQCOM( int program,
                                        int shadertype,
                                        String source, int sourceOffset,
                                        int[] length, int lengthOffset);/*
     // jnigen - native goes here
     glExtGetProgramBinarySourceQCOM( (GLuint) program,
                                      (GLenum) shadertype,
                                      (GLchar  *) (source + sourceOffset),
                                      (GLint  *) (length + lengthOffset));
       */

 /**<pre>
  *  Extension: GL_QCOM_extended_get2
  *  
  * C Prototype:
  * void  glExtGetProgramBinarySourceQCOM ( 
  *          GLuint   program,
  *          GLenum   shadertype,
  *          GLchar  * source,
  *          GLint  * length
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_extended_get2.txt</a> 
  **/ 
 public final native static 
 void glExtGetProgramBinarySourceQCOM( int program,
                                       int shadertype,
                                       String source,
                                       java.nio.IntBuffer length, int lengthOffset); /*
     // jnigen - native goes here
     glExtGetProgramBinarySourceQCOM( (GLuint) program,
                                      (GLenum) shadertype,
                                      (GLchar  *) (source + sourceOffset),
                                      (GLint  *) (length + lengthOffset));
       */
    /**
     * Enumeration for extension: GL_QCOM_perfmon_global_mode
     */
    // enumerations: 
     public static final int   GL_PERFMON_GLOBAL_MODE_QCOM = 0x8FA0;

 
    /**
     * Enumeration for extension: GL_QCOM_tiled_rendering
     */
    // enumerations: 
     public static final int   GL_COLOR_BUFFER_BIT0_QCOM = 0x00000001;
     public static final int   GL_COLOR_BUFFER_BIT1_QCOM = 0x00000002;
     public static final int   GL_COLOR_BUFFER_BIT2_QCOM = 0x00000004;
     public static final int   GL_COLOR_BUFFER_BIT3_QCOM = 0x00000008;
     public static final int   GL_COLOR_BUFFER_BIT4_QCOM = 0x00000010;
     public static final int   GL_COLOR_BUFFER_BIT5_QCOM = 0x00000020;
     public static final int   GL_COLOR_BUFFER_BIT6_QCOM = 0x00000040;
     public static final int   GL_COLOR_BUFFER_BIT7_QCOM = 0x00000080;
     public static final int   GL_DEPTH_BUFFER_BIT0_QCOM = 0x00000100;
     public static final int   GL_DEPTH_BUFFER_BIT1_QCOM = 0x00000200;
     public static final int   GL_DEPTH_BUFFER_BIT2_QCOM = 0x00000400;
     public static final int   GL_DEPTH_BUFFER_BIT3_QCOM = 0x00000800;
     public static final int   GL_DEPTH_BUFFER_BIT4_QCOM = 0x00001000;
     public static final int   GL_DEPTH_BUFFER_BIT5_QCOM = 0x00002000;
     public static final int   GL_DEPTH_BUFFER_BIT6_QCOM = 0x00004000;
     public static final int   GL_DEPTH_BUFFER_BIT7_QCOM = 0x00008000;
     public static final int   GL_STENCIL_BUFFER_BIT0_QCOM = 0x00010000;
     public static final int   GL_STENCIL_BUFFER_BIT1_QCOM = 0x00020000;
     public static final int   GL_STENCIL_BUFFER_BIT2_QCOM = 0x00040000;
     public static final int   GL_STENCIL_BUFFER_BIT3_QCOM = 0x00080000;
     public static final int   GL_STENCIL_BUFFER_BIT4_QCOM = 0x00100000;
     public static final int   GL_STENCIL_BUFFER_BIT5_QCOM = 0x00200000;
     public static final int   GL_STENCIL_BUFFER_BIT6_QCOM = 0x00400000;
     public static final int   GL_STENCIL_BUFFER_BIT7_QCOM = 0x00800000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT0_QCOM = 0x01000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT1_QCOM = 0x02000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT2_QCOM = 0x04000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT3_QCOM = 0x08000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT4_QCOM = 0x10000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT5_QCOM = 0x20000000;
     public static final int   GL_MULTISAMPLE_BUFFER_BIT6_QCOM = 0x40000000;
     public static final long  GL_MULTISAMPLE_BUFFER_BIT7_QCOM = 2147483648L;

 
 // Function(s) for extension GL_QCOM_tiled_rendering, API: gles1|gles2   

 /**<pre>
  *  Extension: GL_QCOM_tiled_rendering
  *  
  * C Prototype:
  * void  glStartTilingQCOM ( 
  *          GLuint   x,
  *          GLuint   y,
  *          GLuint   width,
  *          GLuint   height,
  *          GLbitfield   preserveMask
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_tiled_rendering.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_tiled_rendering.txt</a> 
  **/ 
 public final native static 
  void glStartTilingQCOM (int x,
                           int y,
                           int width,
                           int height,
                           int preserveMask);/*
     // jnigen - native goes here
     glStartTilingQCOM( (GLuint) x,
                        (GLuint) y,
                        (GLuint) width,
                        (GLuint) height,
                        (GLbitfield) preserveMask);
       */

 /**<pre>
  *  Extension: GL_QCOM_tiled_rendering
  *  
  * C Prototype:
  * void  glEndTilingQCOM ( 
  *          GLbitfield   preserveMask
  *          );
  * 
  *</pre>
  * See also <a href="https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_tiled_rendering.txt">https://www.khronos.org/registry/gles/extensions/QCOM/QCOM_tiled_rendering.txt</a> 
  **/ 
 public final native static 
  void glEndTilingQCOM (int preserveMask);/*
     // jnigen - native goes here
     glEndTilingQCOM( (GLbitfield) preserveMask);
       */
    /**
     * Enumeration for extension: GL_QCOM_writeonly_rendering
     */
    // enumerations: 
     public static final int   GL_WRITEONLY_RENDERING_QCOM = 0x8823;

 

   }// end of class GLES2SelectedExt

  


   /** <pre>
    * 
    * Main extension: selection<br>
    * Included extensions: <br>
    * Extension: EGL_ANGLE_window_fixed_size API: EGL, Enumerations: 1, Functions: 0 <br>
    * Extension: EGL_ANGLE_surface_d3d_texture_2d_share_handle API: EGL, Enumerations: 1, Functions: 0 <br>
    * Extension: EGL_ANGLE_d3d_share_handle_client_buffer API: EGL, Enumerations: 1, Functions: 0 <br> ok
    * Extension: EGL_ANGLE_query_surface_pointer API: EGL, Enumerations: 0, Functions: 1 <br>
    * Extension: EGL_ANGLE_device_d3d API: EGL, Enumerations: 2, Functions: 0 <br> ok
    *</pre> 
    **/
     class EGLAngleSelected{
     //@OFF 
     /*JNI 
     // EGL includes
     #define EGL_EGLEXT_PROTOTYPES 1
     #include <EGL/egl.h>
     #include <EGL/eglext.h>

   // function pointers section 
    // Declaration of FuncPointer vars 


  ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: EGL_ANGLE_query_surface_pointer
  // API: egl
  //////////////////////////////////
      static PFNEGLQUERYSURFACEPOINTERANGLEPROC  eglQuerySurfacePointerANGLE;
   // extension loaders
   // function loader
   #define myFuncLoader(x) eglGetProcAddress(x)

    // Declaration of LOADERS for function pointers - PFN_PROC 

   /// ====================================
   // PFN_PROC Extensions functions Loader. 
   // Extension: EGL_ANGLE_query_surface_pointer
   // API: egl
   /// =====================================
   static  int loadExtFuncEGL_ANGLE_query_surface_pointer(){
       eglQuerySurfacePointerANGLE = (PFNEGLQUERYSURFACEPOINTERANGLEPROC) myFuncLoader("eglQuerySurfacePointerANGLE");

       return 1;
      } // end of loadExtFuncEGL_ANGLE_query_surface_pointer()


   // Declaration of loadALL(), to call all PFN_PROC pointers 
  static int loadALL(){
      loadExtFuncEGL_ANGLE_query_surface_pointer();
        return 1;
    } 

    */ // JNI  header

   static protected native void init();/* 
     loadAll();
   */

   /** loading native stuff */
   static{
     init();
   }

     /**
      * Enumeration for extension: EGL_ANGLE_d3d_share_handle_client_buffer
      */
     // enumerations: 
      public static final int   EGL_D3D_TEXTURE_2D_SHARE_HANDLE_ANGLE = 0x3200;

  
     /**
      * Enumeration for extension: EGL_ANGLE_device_d3d
      */
     // enumerations: 
      public static final int   EGL_D3D9_DEVICE_ANGLE = 0x33A0;
      public static final int   EGL_D3D11_DEVICE_ANGLE = 0x33A1;

  
  // Function(s) for extension EGL_ANGLE_query_surface_pointer, API: egl   

  /**<pre>
   *  Extension: EGL_ANGLE_query_surface_pointer
   *  
   * C Prototype:
   *   EGLBoolean eglQuerySurfacePointerANGLE ( 
   *          EGLDisplay   dpy,
   *          EGLSurface   surface,
   *          EGLint   attribute,
   *          void ** value
   *          );
   * 
   *</pre>
   * See also <a href="https://www.khronos.org/registry/egl/extensions/ANGLE/EGL_ANGLE_query_surface_pointer.txt">EGL_ANGLE_query_surface_pointer</a> 
   **/ 
  public final native static 
  boolean eglQuerySurfacePointerANGLE( long dpy,
                                       long surface,
                                       int attribute,
                                       java.nio.Buffer value, int valueOffset); /*
      // jnigen - native goes here
        return (jboolean)  eglQuerySurfacePointerANGLE( (EGLDisplay) dpy,
                                                        (EGLSurface) surface,
                                                        (EGLint) attribute,
                                                        (void **) (value + valueOffset));
        */
     /**
      * Enumeration for extension: EGL_ANGLE_surface_d3d_texture_2d_share_handle
      */
     // enumerations: 
    //  public static final int   EGL_D3D_TEXTURE_2D_SHARE_HANDLE_ANGLE = 0x3200;

  
     /**
      * Enumeration for extension: EGL_ANGLE_window_fixed_size
      */
     // enumerations: 
      public static final int   EGL_FIXED_SIZE_ANGLE = 0x3201;

  

    }// end of class EGLAngleSelected
