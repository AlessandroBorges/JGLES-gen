Java Bindings to OpenGL-ES - Code Generator 
============================================

*Hi !*

Welcome to our code generator for binding ***EGL*** and ***OpenGL-ES*** to Java !
This tool aims help create bridges between a Java application and EGL/GLES API.
In usual way, using JNI, this kind of work was boring, slow and prone error task.
Now, using JGLES-gen, is quiet simple!

For example, to create use a function from an EGL extension, like eglQueryDevicesEXT from *EGL\_EXT\_device\_enumeration*, you should type few lines and then generate a Java class with embedded C++ code.

This Java code: 

```java
    GLmain gl = new GLmain(GL_API.EGL);    
    String javaClass = gl.asJavaClassExt("EGL_EXT_device_enumeration");
    System.out.println(javaClass);

```

Will generate this source code:

```java
//Single Extension Java Class Creation
  /**
   * Place holder for license disclaimer.
   **/

    package gles.generated;
    import android.opengl.*;
  /**
   * <pre>
   * Extension: EGL_EXT_device_enumeration API: EGL, Enumerations: 0, Functions: 1 <br>
   * </pre>
   **/
   public class EGLEGLEXTDeviceEnumerationExt{
  // jnigen header
  //@OFF 
  /*JNI 
  // include section
  // EGL is included for all GL-ES api
    #define EGL_EGLEXT_PROTOTYPES 1
    #include <EGL/egl.h>
    #include <EGL/eglext.h>

 ///////////////////////////////////
  // PFN_PROC functions declaration 
  // Extension: EGL_EXT_device_enumeration
  // API: egl
 //////////////////////////////////
 static PFNEGLQUERYDEVICESEXTPROC	eglQueryDevicesEXT;  // extension loaders
 
  // function loader
  #define myFuncLoader(x) eglGetProcAddress(x)

 /// ====================================
  // PFN_PROC Extensions functions Loader. 
  // Extension: EGL_EXT_device_enumeration
  // API: egl
  /// =====================================/
  static  int loadExtFuncEGL_EXT_device_enumeration(){
      eglQueryDevicesEXT = (PFNEGLQUERYDEVICESEXTPROC) myFuncLoader("eglQueryDevicesEXT");
      return 1;
     } // end of loadExtFuncEGL_EXT_device_enumeration()


 // Declaration of loadALL(), to call all PFN_PROC pointers 
 // Extension key "EGL_EXT_device_enumeration" 
 static int loadALL(){
	 loadExtFuncEGL_EXT_device_enumeration();
       return 1;
   } 
  */ // end of jnigen header

  static protected native void init();/* 
    loadAll();
  */

  /** loading native stuff */
  static{
    init();
  }

 // Function(s) for extension EGL_EXT_device_enumeration, API: egl   

 /**<pre>
  *  Extension: EGL_EXT_device_enumeration
  *  
  * C Prototype:
  *   EGLBoolean eglQueryDevicesEXT ( 
  * 		 EGLint   max_devices,
  * 		 EGLDeviceEXT  * devices,
  * 		 EGLint  * num_devices
  * 		 );
  * 
  *</pre>
  * See also https://www.khronos.org/registry/egl/extensions/EXT/EGL_EXT_device_enumeration.txt
  **/ 
 public final native static 
  boolean eglQueryDevicesEXT( int max_devices,
                              long[]  devices, int devicesOffset,
                              int[] num_devices, int num_devicesOffset);/*
	 // jnigen - native goes here
  	   return (jboolean)  eglQueryDevicesEXT( (EGLint) max_devices,
  	                                          (EGLDeviceEXT  *) (devices + devicesOffset),
  	                                          (EGLint  *) (num_devices + num_devicesOffset));
  	   */

 /**<pre>
  *  Extension: EGL_EXT_device_enumeration
  *  
  * C Prototype:
  *   EGLBoolean eglQueryDevicesEXT ( 
  * 		 EGLint   max_devices,
  * 		 EGLDeviceEXT  * devices,
  * 		 EGLint  * num_devices
  * 		 );
  * 
  *</pre>
  * See also https://www.khronos.org/registry/egl/extensions/EXT/EGL_EXT_device_enumeration.txt
  **/ 
 public final native static 
 boolean eglQueryDevicesEXT( int max_devices,
                             java.nio.LongBuffer devices, int devicesOffset,
                             java.nio.IntBuffer num_devices, int num_devicesOffset); /*
	 // jnigen - native goes here
  	   return (jboolean)  eglQueryDevicesEXT( (EGLint) max_devices,
  	                                          (EGLDeviceEXT  *) (devices + devicesOffset),
  	                                          (EGLint  *) (num_devices + num_devicesOffset));
  	   */

   }// end of class EGLEGLEXTDeviceEnumerationExt



```

Today there are several tools to bind C/C++/Java to OpenGL, as GLEW and GLAD and even some more complete solutions as GLFW, SDL, JOGL and LWJGL. They are great, with years of experience added, but none of them are mainly focused to EGL and GL-ES. Their main task is supporting the complex and heavy weight desktop OpenGL.

My proposal is supporting **EGL** and **GL-ES**, with focus on **Java** native bindings through **C++** and **jnigen**.

How it Works ?
--------------

This tool loads a local copy of egl.xml and gl.xml, the official **XML API registry of reserved Enumerants and Functions**, maintained by Khonus ( <http://www.khronos.org> ), and extract enumerations and functions from core and extensions of the following APIS:

-   EGL
-   GL-ES 1.x
-   GL-ES 2.0
-   GL-ES 3.x

With this collection of information, we can output Java and C++ source code to access the following:

-   Core API enumerations
-   Core API functions
-   Extensions enumerations
-   Extension functions
-   Function pointers declaration, as **PFN_functionName_PROC**
-   Core and extension function loaders
-   Generate Java bindings to EGL and GL-ES, using **libGDX’s** **jnigen** pattern.

The Native Binding
------------------

Native binding for Java is provided with source code following **jnigen** pattern. This is a simple, widely adopted way to create JNI code without pain.

Nevertheless, our generator is also capable to output C++ classes for each API, with core functions and/or extensions, to be used by your **jnigen** code.

**@See** more about jnigen here: <https://github.com/libgdx/libgdx/wiki/jnigen>

What it is not
--------------

This is not:

-   Replacement for EGL/GLES headers. You need then.
-   IA optimized source code generator. You may need to adjust generated code to fit your needs and possibly fix some minor errors.

License
-------

This will use Apache 2.0 license, the same one used by Android OS.
I try to keep it as a widely open source project, but still with reserves about responsibility.
