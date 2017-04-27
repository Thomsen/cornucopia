# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
             copia-patch

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/jni/copia-patch.cpp
             src/main/jni/libzip2/blocksort.c
             src/main/jni/libzip2/bzip2.c
             src/main/jni/libzip2/bzlib.c
             src/main/jni/libzip2/compress.c
             src/main/jni/libzip2/crctable.c
             src/main/jni/libzip2/decompress.c
             src/main/jni/libzip2/huffman.c
             src/main/jni/libzip2/randtable.c
             src/main/jni/patch/patch.c)

# Specifies a path to native header files.
include_directories(src/main/jni/libzip2/)
include_directories(src/main/jni/patch/)