Java Bindings to OpenGL-ES Generator 
=====================================

*Hi !*

Welcome to our code generator for binding ***EGL*** and ***OpenGL-ES***.

The OpenGL-ES, or just **GL-ES**, for short, is the portable, compact **G**raphics **L**ibrary to **E**mbedded **S**ystems.

Today there are several tools to bind C/C++/Java to OpenGL, as GLEW and GLAD and even some more complete solutions as GLFW, SDL, JOGL and LWJGL. They are great tool, with years of experience added, but none of them where mainly focused to EGL and GL-ES. Their main task is supporting the complex and heavy weight desktop OpenGL.

My proposal is supporting **EGL** and **GL-ES**, with focus on **Java** native bindings through **C++** and **jnigen**.

How it Works ?
--------------

This tool loads a local copy of egl.xml and gl.xml, the official **XML API registry of reserved Enumerants and Functions**, maintained by Khonus ( <http://www.khronos.org> ), and extract enumerations and functions from core and extensions of the following APIS:

-   EGL

-   GL-ES 1.x

-   GL-ES 2.0

-   GL-ES 3.x

With this collection of information, we can output Java and C++ source code to access the following:

-   Core API enumerations;

-   Core API functions

-   Extensions enumerations;

-   Extension functions;

-   Function pointers declaration, as **PFN\_**function\_name\_**PROC**

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
