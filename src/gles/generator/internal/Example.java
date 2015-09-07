package gles.generator.internal;

//Single Extension Java Class Creation
/**
 * Place holder for license disclaimer.
 **/

 // package myPackage.glstuff;
  //import android.opengl.*;


/**
 * <pre>
 * Main extension: KHR
 * Included extensions: 
 * Extension: GL_KHR_blend_equation_advanced_coherent API: GL, GL-ES 2.0+, Enumerations: 1, Functions: 0
 * Extension: GL_KHR_context_flush_control API: GL, Enumerations: 3, Functions: 0
 * Extension: GL_KHR_blend_equation_advanced API: GL, GL-ES 2.0+, Enumerations: 15, Functions: 1
 * Extension: GL_KHR_texture_compression_astc_hdr API: GL, GL-ES 2.0+, Enumerations: 28, Functions: 0
 * Extension: GL_KHR_texture_compression_astc_ldr API: GL, GL-ES 2.0+, Enumerations: 28, Functions: 0
 * Extension: GL_KHR_robust_buffer_access_behavior, API:  GL GL-ES 2.0+ 
 * Extension: GL_KHR_no_error API: GL, GL-ES 2.0+, Enumerations: 1, Functions: 0
 * Extension: GL_KHR_robustness API: GL, Enumerations: 9, Functions: 5
 * Extension: GL_KHR_debug API: GL, Enumerations: 40, Functions: 11
 * </pre>
 **/
  class GLES2KHRExt{
  /**
   * Enumeration for extension: GL_KHR_blend_equation_advanced
   */
  // enumerations: 
   public static final int   GL_MULTIPLY_KHR = 0x9294;
   public static final int   GL_SCREEN_KHR = 0x9295;
   public static final int   GL_OVERLAY_KHR = 0x9296;
   public static final int   GL_DARKEN_KHR = 0x9297;
   public static final int   GL_LIGHTEN_KHR = 0x9298;
   public static final int   GL_COLORDODGE_KHR = 0x9299;
   public static final int   GL_COLORBURN_KHR = 0x929A;
   public static final int   GL_HARDLIGHT_KHR = 0x929B;
   public static final int   GL_SOFTLIGHT_KHR = 0x929C;
   public static final int   GL_DIFFERENCE_KHR = 0x929E;
   public static final int   GL_EXCLUSION_KHR = 0x92A0;
   public static final int   GL_HSL_HUE_KHR = 0x92AD;
   public static final int   GL_HSL_SATURATION_KHR = 0x92AE;
   public static final int   GL_HSL_COLOR_KHR = 0x92AF;
   public static final int   GL_HSL_LUMINOSITY_KHR = 0x92B0;


// Function(s) for extension GL_KHR_blend_equation_advanced, API: gl|gles2   

/**
*  Extension: GL_KHR_blend_equation_advanced
*  
* C Prototype:
* void  glBlendBarrierKHR ( 
*          );
* 
**/ 
public final native static 
void glBlendBarrierKHR ();/*
   // jnigen - native goes here
   glBlendBarrierKHR();
     */

/**
*  Extension: GL_KHR_blend_equation_advanced
*  alias of glBlendBarrierKHR
* C Prototype:
* void  glBlendBarrier ( 
*          );
* 
**/ 
public final native static 
void glBlendBarrier ();/*
   // jnigen - native goes here
   glBlendBarrier();
     */
  /**
   * Enumeration for extension: GL_KHR_blend_equation_advanced_coherent
   */
  // enumerations: 
   public static final int   GL_BLEND_ADVANCED_COHERENT_KHR = 0x9285;


  /**
   * Enumeration for extension: GL_KHR_context_flush_control
   */
  // enumerations: 
   public static final int   GL_CONTEXT_RELEASE_BEHAVIOR_KHR = 0x82FB;
   public static final int   GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH_KHR = 0x82FC;
   public static final int   GL_NONE = 0;


  /**
   * Enumeration for extension: GL_KHR_debug
   */
  // enumerations: 
   public static final int   GL_DEBUG_OUTPUT_SYNCHRONOUS_KHR = 0x8242;
   public static final int   GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_KHR = 0x8243;
   public static final int   GL_DEBUG_CALLBACK_FUNCTION_KHR = 0x8244;
   public static final int   GL_DEBUG_CALLBACK_USER_PARAM_KHR = 0x8245;
   public static final int   GL_DEBUG_SOURCE_API_KHR = 0x8246;
   public static final int   GL_DEBUG_SOURCE_WINDOW_SYSTEM_KHR = 0x8247;
   public static final int   GL_DEBUG_SOURCE_SHADER_COMPILER_KHR = 0x8248;
   public static final int   GL_DEBUG_SOURCE_THIRD_PARTY_KHR = 0x8249;
   public static final int   GL_DEBUG_SOURCE_APPLICATION_KHR = 0x824A;
   public static final int   GL_DEBUG_SOURCE_OTHER_KHR = 0x824B;
   public static final int   GL_DEBUG_TYPE_ERROR_KHR = 0x824C;
   public static final int   GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_KHR = 0x824D;
   public static final int   GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_KHR = 0x824E;
   public static final int   GL_DEBUG_TYPE_PORTABILITY_KHR = 0x824F;
   public static final int   GL_DEBUG_TYPE_PERFORMANCE_KHR = 0x8250;
   public static final int   GL_DEBUG_TYPE_OTHER_KHR = 0x8251;
   public static final int   GL_DEBUG_TYPE_MARKER_KHR = 0x8268;
   public static final int   GL_DEBUG_TYPE_PUSH_GROUP_KHR = 0x8269;
   public static final int   GL_DEBUG_TYPE_POP_GROUP_KHR = 0x826A;
   public static final int   GL_DEBUG_SEVERITY_NOTIFICATION_KHR = 0x826B;
   public static final int   GL_MAX_DEBUG_GROUP_STACK_DEPTH_KHR = 0x826C;
   public static final int   GL_DEBUG_GROUP_STACK_DEPTH_KHR = 0x826D;
   public static final int   GL_BUFFER_KHR = 0x82E0;
   public static final int   GL_SHADER_KHR = 0x82E1;
   public static final int   GL_PROGRAM_KHR = 0x82E2;
   public static final int   GL_VERTEX_ARRAY_KHR = 0x8074;
   public static final int   GL_QUERY_KHR = 0x82E3;
   public static final int   GL_PROGRAM_PIPELINE_KHR = 0x82E4;
   public static final int   GL_SAMPLER_KHR = 0x82E6;
   public static final int   GL_MAX_LABEL_LENGTH_KHR = 0x82E8;
   public static final int   GL_MAX_DEBUG_MESSAGE_LENGTH_KHR = 0x9143;
   public static final int   GL_MAX_DEBUG_LOGGED_MESSAGES_KHR = 0x9144;
   public static final int   GL_DEBUG_LOGGED_MESSAGES_KHR = 0x9145;
   public static final int   GL_DEBUG_SEVERITY_HIGH_KHR = 0x9146;
   public static final int   GL_DEBUG_SEVERITY_MEDIUM_KHR = 0x9147;
   public static final int   GL_DEBUG_SEVERITY_LOW_KHR = 0x9148;
   public static final int   GL_DEBUG_OUTPUT_KHR = 0x92E0;
   public static final int   GL_CONTEXT_FLAG_DEBUG_BIT_KHR = 0x00000002;
   public static final int   GL_STACK_OVERFLOW_KHR = 0x0503;
   public static final int   GL_STACK_UNDERFLOW_KHR = 0x0504;


// Function(s) for extension GL_KHR_debug, API: gles2   

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glDebugMessageControlKHR ( 
*          GLenum   source,
*          GLenum   type,
*          GLenum   severity,
*          GLsizei   count,
*          const  GLuint  * ids,
*          GLboolean   enabled
*          );
* 
**/ 
public final native static 
void glDebugMessageControlKHR( int source,
                              int type,
                              int severity,
                              int count,
                              java.nio.IntBuffer ids, int idsOffset,
                              boolean enabled); /*
   // jnigen - native goes here
   glDebugMessageControlKHR( (GLenum) source,
                             (GLenum) type,
                             (GLenum) severity,
                             (GLsizei) count,
                             (const  GLuint  *) (ids + idsOffset),
                             (GLboolean) enabled);
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glDebugMessageControlKHR ( 
*          GLenum   source,
*          GLenum   type,
*          GLenum   severity,
*          GLsizei   count,
*          const  GLuint  * ids,
*          GLboolean   enabled
*          );
* 
**/ 
public final native static 
void glDebugMessageControlKHR( int source,
                               int type,
                               int severity,
                               int count,
                               final int [] ids, int idsOffset,
                               boolean enabled);/*
   // jnigen - native goes here
   glDebugMessageControlKHR( (GLenum) source,
                             (GLenum) type,
                             (GLenum) severity,
                             (GLsizei) count,
                             (const  GLuint  *) (ids + idsOffset),
                             (GLboolean) enabled);
     */

/**
*  Extension: GL_KHR_debug
*  alias of glDebugMessageControlKHR
* C Prototype:
* void  glDebugMessageControl ( 
*          GLenum   source,
*          GLenum   type,
*          GLenum   severity,
*          GLsizei   count,
*          const  GLuint  * ids,
*          GLboolean   enabled
*          );
* 
**/ 
public final native static 
void glDebugMessageControl( int source,
                            int type,
                            int severity,
                            int count,
                            final int [] ids, int idsOffset,
                            boolean enabled);/*
   // jnigen - native goes here
   glDebugMessageControl( (GLenum) source,
                          (GLenum) type,
                          (GLenum) severity,
                          (GLsizei) count,
                          (const  GLuint  *) (ids + idsOffset),
                          (GLboolean) enabled);
     */

/**
*  Extension: GL_KHR_debug
*  alias of glDebugMessageControlKHR
* C Prototype:
* void  glDebugMessageControl ( 
*          GLenum   source,
*          GLenum   type,
*          GLenum   severity,
*          GLsizei   count,
*          const  GLuint  * ids,
*          GLboolean   enabled
*          );
* 
**/ 
public final native static 
void glDebugMessageControl( int source,
                           int type,
                           int severity,
                           int count,
                           java.nio.IntBuffer ids, int idsOffset,
                           boolean enabled); /*
   // jnigen - native goes here
   glDebugMessageControl( (GLenum) source,
                          (GLenum) type,
                          (GLenum) severity,
                          (GLsizei) count,
                          (const  GLuint  *) (ids + idsOffset),
                          (GLboolean) enabled);
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glDebugMessageInsertKHR ( 
*          GLenum   source,
*          GLenum   type,
*          GLuint   id,
*          GLenum   severity,
*          GLsizei   length,
*          const  GLchar  * buf
*          );
* 
**/ 
public final native static 
void glDebugMessageInsertKHR (int source,
                               int type,
                               int id,
                               int severity,
                               int length,
                               final byte [] buf);/*
   // jnigen - native goes here
   glDebugMessageInsertKHR( (GLenum) source,
                            (GLenum) type,
                            (GLuint) id,
                            (GLenum) severity,
                            (GLsizei) length,
                            (const  GLchar  *) (buf + bufOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glDebugMessageInsertKHR
* C Prototype:
* void  glDebugMessageInsert ( 
*          GLenum   source,
*          GLenum   type,
*          GLuint   id,
*          GLenum   severity,
*          GLsizei   length,
*          const  GLchar  * buf
*          );
* 
**/ 
public final native static 
void glDebugMessageInsert (int source,
                            int type,
                            int id,
                            int severity,
                            int length,
                            final byte [] buf);/*
   // jnigen - native goes here
   glDebugMessageInsert( (GLenum) source,
                         (GLenum) type,
                         (GLuint) id,
                         (GLenum) severity,
                         (GLsizei) length,
                         (const  GLchar  *) (buf + bufOffset));
     */


class GLDEBUGPROCKHR{}

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glDebugMessageCallbackKHR ( 
*          GLDEBUGPROCKHR   callback,
*          const void * userParam
*          );
* 
**/ 
public final native static 
void glDebugMessageCallbackKHR( GLDEBUGPROCKHR callback,
                               java.nio.Buffer userParam, int userParamOffset); /*
   // jnigen - native goes here
   glDebugMessageCallbackKHR( (GLDEBUGPROCKHR) callback,
                              (const void *) (userParam + userParamOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glDebugMessageCallbackKHR
* C Prototype:
* void  glDebugMessageCallback ( 
*          GLDEBUGPROCKHR   callback,
*          const void * userParam
*          );
* 
**/ 
public final native static 
void glDebugMessageCallback( GLDEBUGPROCKHR callback,
                            java.nio.Buffer userParam, int userParamOffset); /*
   // jnigen - native goes here
   glDebugMessageCallback( (GLDEBUGPROCKHR) callback,
                           (const void *) (userParam + userParamOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
*   GLuint glGetDebugMessageLogKHR ( 
*          GLuint   count,
*          GLsizei   bufSize,
*          GLenum  * sources,
*          GLenum  * types,
*          GLuint  * ids,
*          GLenum  * severities,
*          GLsizei  * lengths,
*          GLchar  * messageLog
*          );
* 
**/ 
public final native static 
int glGetDebugMessageLogKHR( int count,
                            int bufSize,
                            java.nio.IntBuffer sources, int sourcesOffset,
                            java.nio.IntBuffer types, int typesOffset,
                            java.nio.IntBuffer ids, int idsOffset,
                            java.nio.IntBuffer severities, int severitiesOffset,
                            java.nio.IntBuffer lengths, int lengthsOffset,
                            String messageLog); /*
   // jnigen - native goes here
     return (jint)  glGetDebugMessageLogKHR( (GLuint) count,
                                             (GLsizei) bufSize,
                                             (GLenum  *) (sources + sourcesOffset),
                                             (GLenum  *) (types + typesOffset),
                                             (GLuint  *) (ids + idsOffset),
                                             (GLenum  *) (severities + severitiesOffset),
                                             (GLsizei  *) (lengths + lengthsOffset),
                                             (GLchar  *) (messageLog + messageLogOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
*   GLuint glGetDebugMessageLogKHR ( 
*          GLuint   count,
*          GLsizei   bufSize,
*          GLenum  * sources,
*          GLenum  * types,
*          GLuint  * ids,
*          GLenum  * severities,
*          GLsizei  * lengths,
*          GLchar  * messageLog
*          );
* 
**/ 
public final native static 
int glGetDebugMessageLogKHR( int count,
                             int bufSize,
                             int[] sources, int sourcesOffset,
                             int[] types, int typesOffset,
                             int[] ids, int idsOffset,
                             int[] severities, int severitiesOffset,
                             int[] lengths, int lengthsOffset,
                             String messageLog, int messageLogOffset);/*
   // jnigen - native goes here
     return (jint)  glGetDebugMessageLogKHR( (GLuint) count,
                                             (GLsizei) bufSize,
                                             (GLenum  *) (sources + sourcesOffset),
                                             (GLenum  *) (types + typesOffset),
                                             (GLuint  *) (ids + idsOffset),
                                             (GLenum  *) (severities + severitiesOffset),
                                             (GLsizei  *) (lengths + lengthsOffset),
                                             (GLchar  *) (messageLog + messageLogOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetDebugMessageLogKHR
* C Prototype:
*   GLuint glGetDebugMessageLog ( 
*          GLuint   count,
*          GLsizei   bufSize,
*          GLenum  * sources,
*          GLenum  * types,
*          GLuint  * ids,
*          GLenum  * severities,
*          GLsizei  * lengths,
*          GLchar  * messageLog
*          );
* 
**/ 
public final native static 
int glGetDebugMessageLog( int count,
                          int bufSize,
                          int[] sources, int sourcesOffset,
                          int[] types, int typesOffset,
                          int[] ids, int idsOffset,
                          int[] severities, int severitiesOffset,
                          int[] lengths, int lengthsOffset,
                          String messageLog, int messageLogOffset);/*
   // jnigen - native goes here
     return (jint)  glGetDebugMessageLog( (GLuint) count,
                                          (GLsizei) bufSize,
                                          (GLenum  *) (sources + sourcesOffset),
                                          (GLenum  *) (types + typesOffset),
                                          (GLuint  *) (ids + idsOffset),
                                          (GLenum  *) (severities + severitiesOffset),
                                          (GLsizei  *) (lengths + lengthsOffset),
                                          (GLchar  *) (messageLog + messageLogOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetDebugMessageLogKHR
* C Prototype:
*   GLuint glGetDebugMessageLog ( 
*          GLuint   count,
*          GLsizei   bufSize,
*          GLenum  * sources,
*          GLenum  * types,
*          GLuint  * ids,
*          GLenum  * severities,
*          GLsizei  * lengths,
*          GLchar  * messageLog
*          );
* 
**/ 
public final native static 
int glGetDebugMessageLog( int count,
                         int bufSize,
                         java.nio.IntBuffer sources, int sourcesOffset,
                         java.nio.IntBuffer types, int typesOffset,
                         java.nio.IntBuffer ids, int idsOffset,
                         java.nio.IntBuffer severities, int severitiesOffset,
                         java.nio.IntBuffer lengths, int lengthsOffset,
                         String messageLog); /*
   // jnigen - native goes here
     return (jint)  glGetDebugMessageLog( (GLuint) count,
                                          (GLsizei) bufSize,
                                          (GLenum  *) (sources + sourcesOffset),
                                          (GLenum  *) (types + typesOffset),
                                          (GLuint  *) (ids + idsOffset),
                                          (GLenum  *) (severities + severitiesOffset),
                                          (GLsizei  *) (lengths + lengthsOffset),
                                          (GLchar  *) (messageLog + messageLogOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glPushDebugGroupKHR ( 
*          GLenum   source,
*          GLuint   id,
*          GLsizei   length,
*          const  GLchar  * message
*          );
* 
**/ 
public final native static 
void glPushDebugGroupKHR (int source,
                           int id,
                           int length,
                           final byte [] message);/*
   // jnigen - native goes here
   glPushDebugGroupKHR( (GLenum) source,
                        (GLuint) id,
                        (GLsizei) length,
                        (const  GLchar  *) (message + messageOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glPushDebugGroupKHR
* C Prototype:
* void  glPushDebugGroup ( 
*          GLenum   source,
*          GLuint   id,
*          GLsizei   length,
*          const  GLchar  * message
*          );
* 
**/ 
public final native static 
void glPushDebugGroup (int source,
                        int id,
                        int length,
                        final byte [] message);/*
   // jnigen - native goes here
   glPushDebugGroup( (GLenum) source,
                     (GLuint) id,
                     (GLsizei) length,
                     (const  GLchar  *) (message + messageOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glPopDebugGroupKHR ( 
*          );
* 
**/ 
public final native static 
void glPopDebugGroupKHR ();/*
   // jnigen - native goes here
   glPopDebugGroupKHR();
     */

/**
*  Extension: GL_KHR_debug
*  alias of glPopDebugGroupKHR
* C Prototype:
* void  glPopDebugGroup ( 
*          );
* 
**/ 
public final native static 
void glPopDebugGroup ();/*
   // jnigen - native goes here
   glPopDebugGroup();
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glObjectLabelKHR ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   length,
*          const  GLchar  * label
*          );
* 
**/ 
public final native static 
void glObjectLabelKHR (int identifier,
                        int name,
                        int length,
                        final byte [] label);/*
   // jnigen - native goes here
   glObjectLabelKHR( (GLenum) identifier,
                     (GLuint) name,
                     (GLsizei) length,
                     (const  GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glObjectLabelKHR
* C Prototype:
* void  glObjectLabel ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   length,
*          const  GLchar  * label
*          );
* 
**/ 
public final native static 
void glObjectLabel (int identifier,
                     int name,
                     int length,
                     final byte [] label);/*
   // jnigen - native goes here
   glObjectLabel( (GLenum) identifier,
                  (GLuint) name,
                  (GLsizei) length,
                  (const  GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glGetObjectLabelKHR ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectLabelKHR( int identifier,
                          int name,
                          int bufSize,
                          int[] length, int lengthOffset,
                          String label, int labelOffset);/*
   // jnigen - native goes here
   glGetObjectLabelKHR( (GLenum) identifier,
                        (GLuint) name,
                        (GLsizei) bufSize,
                        (GLsizei  *) (length + lengthOffset),
                        (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glGetObjectLabelKHR ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectLabelKHR( int identifier,
                         int name,
                         int bufSize,
                         java.nio.IntBuffer length, int lengthOffset,
                         String label); /*
   // jnigen - native goes here
   glGetObjectLabelKHR( (GLenum) identifier,
                        (GLuint) name,
                        (GLsizei) bufSize,
                        (GLsizei  *) (length + lengthOffset),
                        (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetObjectLabelKHR
* C Prototype:
* void  glGetObjectLabel ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectLabel( int identifier,
                      int name,
                      int bufSize,
                      java.nio.IntBuffer length, int lengthOffset,
                      String label); /*
   // jnigen - native goes here
   glGetObjectLabel( (GLenum) identifier,
                     (GLuint) name,
                     (GLsizei) bufSize,
                     (GLsizei  *) (length + lengthOffset),
                     (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetObjectLabelKHR
* C Prototype:
* void  glGetObjectLabel ( 
*          GLenum   identifier,
*          GLuint   name,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectLabel( int identifier,
                       int name,
                       int bufSize,
                       int[] length, int lengthOffset,
                       String label, int labelOffset);/*
   // jnigen - native goes here
   glGetObjectLabel( (GLenum) identifier,
                     (GLuint) name,
                     (GLsizei) bufSize,
                     (GLsizei  *) (length + lengthOffset),
                     (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glObjectPtrLabelKHR ( 
*          const void * ptr,
*          GLsizei   length,
*          const  GLchar  * label
*          );
* 
**/ 
public final native static 
void glObjectPtrLabelKHR( java.nio.Buffer ptr, int ptrOffset,
                         int length,
                         final byte [] label); /*
   // jnigen - native goes here
   glObjectPtrLabelKHR( (const void *) (ptr + ptrOffset),
                        (GLsizei) length,
                        (const  GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glObjectPtrLabelKHR
* C Prototype:
* void  glObjectPtrLabel ( 
*          const void * ptr,
*          GLsizei   length,
*          const  GLchar  * label
*          );
* 
**/ 
public final native static 
void glObjectPtrLabel( java.nio.Buffer ptr, int ptrOffset,
                      int length,
                      final byte [] label); /*
   // jnigen - native goes here
   glObjectPtrLabel( (const void *) (ptr + ptrOffset),
                     (GLsizei) length,
                     (const  GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glGetObjectPtrLabelKHR ( 
*          const void * ptr,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectPtrLabelKHR( java.nio.Buffer ptr, int ptrOffset,
                            int bufSize,
                            java.nio.IntBuffer length, int lengthOffset,
                            String label); /*
   // jnigen - native goes here
   glGetObjectPtrLabelKHR( (const void *) (ptr + ptrOffset),
                           (GLsizei) bufSize,
                           (GLsizei  *) (length + lengthOffset),
                           (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetObjectPtrLabelKHR
* C Prototype:
* void  glGetObjectPtrLabel ( 
*          const void * ptr,
*          GLsizei   bufSize,
*          GLsizei  * length,
*          GLchar  * label
*          );
* 
**/ 
public final native static 
void glGetObjectPtrLabel( java.nio.Buffer ptr, int ptrOffset,
                         int bufSize,
                         java.nio.IntBuffer length, int lengthOffset,
                         String label); /*
   // jnigen - native goes here
   glGetObjectPtrLabel( (const void *) (ptr + ptrOffset),
                        (GLsizei) bufSize,
                        (GLsizei  *) (length + lengthOffset),
                        (GLchar  *) (label + labelOffset));
     */

/**
*  Extension: GL_KHR_debug
*  
* C Prototype:
* void  glGetPointervKHR ( 
*          GLenum   pname,
*          void ** params
*          );
* 
**/ 
public final native static 
void glGetPointervKHR( int pname,
                      java.nio.Buffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetPointervKHR( (GLenum) pname,
                     (void **) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_debug
*  alias of glGetPointervKHR
* C Prototype:
* void  glGetPointerv ( 
*          GLenum   pname,
*          void ** params
*          );
* 
**/ 
public final native static 
void glGetPointerv( int pname,
                   java.nio.Buffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetPointerv( (GLenum) pname,
                  (void **) (params + paramsOffset));
     */
  /**
   * Enumeration for extension: GL_KHR_no_error
   */
  // enumerations: 
   public static final int   GL_CONTEXT_FLAG_NO_ERROR_BIT_KHR = 0x00000008;


  /**
   * Enumeration for extension: GL_KHR_robustness
   */
  // enumerations: 
   public static final int   GL_NO_ERROR = 0;
   public static final int   GL_CONTEXT_ROBUST_ACCESS_KHR = 0x90F3;
   public static final int   GL_LOSE_CONTEXT_ON_RESET_KHR = 0x8252;
   public static final int   GL_GUILTY_CONTEXT_RESET_KHR = 0x8253;
   public static final int   GL_INNOCENT_CONTEXT_RESET_KHR = 0x8254;
   public static final int   GL_UNKNOWN_CONTEXT_RESET_KHR = 0x8255;
   public static final int   GL_RESET_NOTIFICATION_STRATEGY_KHR = 0x8256;
   public static final int   GL_NO_RESET_NOTIFICATION_KHR = 0x8261;
   public static final int   GL_CONTEXT_LOST_KHR = 0x0507;


// Function(s) for extension GL_KHR_robustness, API: gles2   

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
*   GLenum glGetGraphicsResetStatusKHR ( 
*          );
* 
**/ 
public final native static 
int glGetGraphicsResetStatusKHR ();/*
   // jnigen - native goes here
     return (jint)  glGetGraphicsResetStatusKHR();
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetGraphicsResetStatusKHR
* C Prototype:
*   GLenum glGetGraphicsResetStatus ( 
*          );
* 
**/ 
public final native static 
int glGetGraphicsResetStatus ();/*
   // jnigen - native goes here
     return (jint)  glGetGraphicsResetStatus();
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glReadnPixelsKHR ( 
*          GLint   x, // group=WinCoord  
*          GLint   y, // group=WinCoord  
*          GLsizei   width,
*          GLsizei   height,
*          GLenum   format, // group=PixelFormat  
*          GLenum   type, // group=PixelType  
*          GLsizei   bufSize,
*          void * data
*          );
* 
**/ 
public final native static 
void glReadnPixelsKHR( int x   /*   group=WinCoord */,
                      int y   /*   group=WinCoord */,
                      int width,
                      int height,
                      int format  /*   group=PixelFormat */,
                      int type    /*   group=PixelType */,
                      int bufSize,
                      java.nio.Buffer data, int dataOffset); /*
   // jnigen - native goes here
   glReadnPixelsKHR( (GLint) x,
                     (GLint) y,
                     (GLsizei) width,
                     (GLsizei) height,
                     (GLenum) format,
                     (GLenum) type,
                     (GLsizei) bufSize,
                     (void *) (data + dataOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glReadnPixelsKHR
* C Prototype:
* void  glReadnPixels ( 
*          GLint   x, // group=WinCoord  
*          GLint   y, // group=WinCoord  
*          GLsizei   width,
*          GLsizei   height,
*          GLenum   format, // group=PixelFormat  
*          GLenum   type, // group=PixelType  
*          GLsizei   bufSize,
*          void * data
*          );
* 
**/ 
public final native static 
void glReadnPixels( int x  /*   group=WinCoord */,
                   int y  /*   group=WinCoord */,
                   int width,
                   int height,
                   int format /*   group=PixelFormat */,
                   int type   /*   group=PixelType */,
                   int bufSize,
                   java.nio.Buffer data, int dataOffset); /*
   // jnigen - native goes here
   glReadnPixels( (GLint) x,
                  (GLint) y,
                  (GLsizei) width,
                  (GLsizei) height,
                  (GLenum) format,
                  (GLenum) type,
                  (GLsizei) bufSize,
                  (void *) (data + dataOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformfvKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLfloat  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformfvKHR( int program,
                        int location,
                        int bufSize,
                        java.nio.FloatBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformfvKHR( (GLuint) program,
                       (GLint) location,
                       (GLsizei) bufSize,
                       (GLfloat  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformfvKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLfloat  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformfvKHR( int program,
                         int location,
                         int bufSize,
                         float[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformfvKHR( (GLuint) program,
                       (GLint) location,
                       (GLsizei) bufSize,
                       (GLfloat  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformfvKHR
* C Prototype:
* void  glGetnUniformfv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLfloat  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformfv( int program,
                      int location,
                      int bufSize,
                      float[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformfv( (GLuint) program,
                    (GLint) location,
                    (GLsizei) bufSize,
                    (GLfloat  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformfvKHR
* C Prototype:
* void  glGetnUniformfv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLfloat  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformfv( int program,
                     int location,
                     int bufSize,
                     java.nio.FloatBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformfv( (GLuint) program,
                    (GLint) location,
                    (GLsizei) bufSize,
                    (GLfloat  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformivKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformivKHR( int program,
                        int location,
                        int bufSize,
                        java.nio.IntBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformivKHR( (GLuint) program,
                       (GLint) location,
                       (GLsizei) bufSize,
                       (GLint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformivKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformivKHR( int program,
                         int location,
                         int bufSize,
                         int[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformivKHR( (GLuint) program,
                       (GLint) location,
                       (GLsizei) bufSize,
                       (GLint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformivKHR
* C Prototype:
* void  glGetnUniformiv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformiv( int program,
                      int location,
                      int bufSize,
                      int[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformiv( (GLuint) program,
                    (GLint) location,
                    (GLsizei) bufSize,
                    (GLint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformivKHR
* C Prototype:
* void  glGetnUniformiv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformiv( int program,
                     int location,
                     int bufSize,
                     java.nio.IntBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformiv( (GLuint) program,
                    (GLint) location,
                    (GLsizei) bufSize,
                    (GLint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformuivKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLuint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformuivKHR( int program,
                          int location,
                          int bufSize,
                          int[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformuivKHR( (GLuint) program,
                        (GLint) location,
                        (GLsizei) bufSize,
                        (GLuint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  
* C Prototype:
* void  glGetnUniformuivKHR ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLuint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformuivKHR( int program,
                         int location,
                         int bufSize,
                         java.nio.IntBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformuivKHR( (GLuint) program,
                        (GLint) location,
                        (GLsizei) bufSize,
                        (GLuint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformuivKHR
* C Prototype:
* void  glGetnUniformuiv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLuint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformuiv( int program,
                       int location,
                       int bufSize,
                       int[] params, int paramsOffset);/*
   // jnigen - native goes here
   glGetnUniformuiv( (GLuint) program,
                     (GLint) location,
                     (GLsizei) bufSize,
                     (GLuint  *) (params + paramsOffset));
     */

/**
*  Extension: GL_KHR_robustness
*  alias of glGetnUniformuivKHR
* C Prototype:
* void  glGetnUniformuiv ( 
*          GLuint   program,
*          GLint   location,
*          GLsizei   bufSize,
*          GLuint  * params
*          );
* 
**/ 
public final native static 
void glGetnUniformuiv( int program,
                      int location,
                      int bufSize,
                      java.nio.IntBuffer params, int paramsOffset); /*
   // jnigen - native goes here
   glGetnUniformuiv( (GLuint) program,
                     (GLint) location,
                     (GLsizei) bufSize,
                     (GLuint  *) (params + paramsOffset));
     */
  /**
   * Enumeration for extension: GL_KHR_texture_compression_astc_hdr
   */
  // enumerations: 
   public static final int   GL_COMPRESSED_RGBA_ASTC_4x4_KHR = 0x93B0;
   public static final int   GL_COMPRESSED_RGBA_ASTC_5x4_KHR = 0x93B1;
   public static final int   GL_COMPRESSED_RGBA_ASTC_5x5_KHR = 0x93B2;
   public static final int   GL_COMPRESSED_RGBA_ASTC_6x5_KHR = 0x93B3;
   public static final int   GL_COMPRESSED_RGBA_ASTC_6x6_KHR = 0x93B4;
   public static final int   GL_COMPRESSED_RGBA_ASTC_8x5_KHR = 0x93B5;
   public static final int   GL_COMPRESSED_RGBA_ASTC_8x6_KHR = 0x93B6;
   public static final int   GL_COMPRESSED_RGBA_ASTC_8x8_KHR = 0x93B7;
   public static final int   GL_COMPRESSED_RGBA_ASTC_10x5_KHR = 0x93B8;
   public static final int   GL_COMPRESSED_RGBA_ASTC_10x6_KHR = 0x93B9;
   public static final int   GL_COMPRESSED_RGBA_ASTC_10x8_KHR = 0x93BA;
   public static final int   GL_COMPRESSED_RGBA_ASTC_10x10_KHR = 0x93BB;
   public static final int   GL_COMPRESSED_RGBA_ASTC_12x10_KHR = 0x93BC;
   public static final int   GL_COMPRESSED_RGBA_ASTC_12x12_KHR = 0x93BD;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4_KHR = 0x93D0;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4_KHR = 0x93D1;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5_KHR = 0x93D2;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5_KHR = 0x93D3;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6_KHR = 0x93D4;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5_KHR = 0x93D5;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6_KHR = 0x93D6;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8_KHR = 0x93D7;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5_KHR = 0x93D8;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6_KHR = 0x93D9;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8_KHR = 0x93DA;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10_KHR = 0x93DB;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10_KHR = 0x93DC;
   public static final int   GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12_KHR = 0x93DD;


  



 }// end of class GLES2KHRExt





////////////////////////////////////////////
class Example{
    
    static long v = 0xFFFFFFFFFFFFFFFFL;
    
    public static void main(String[] args) {
        System.out.println("v: " + v);
        System.out.println(Long.toHexString(v));
    }   
    
}