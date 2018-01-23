# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# 加载整个文件夹的所有文件
file(GLOB bzip2 src/main/cpp/bzip2/*.c)

find_library( log-lib
              log )

# 配置AxeBsPatch动态库
add_library( AxeBsPatch

             SHARED

             src/main/cpp/bspatch.c
             ${bzip2}
             )


# 链接AxeBsPatch动态库
target_link_libraries( AxeBsPatch


                       ${log-lib} )