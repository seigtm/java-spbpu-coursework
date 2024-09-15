cmake_minimum_required(VERSION 3.30.3)

project(java-course-work-seigtm LANGUAGES Java)

find_package(Java 22.0.2 REQUIRED COMPONENTS Development)
include(UseJava)

set(root_directory "${CMAKE_CURRENT_SOURCE_DIR}")
set(external_directory "${root_directory}/external")

# Build the main application.
file(GLOB_RECURSE java_sources_external "${external_directory}/*.java")

add_jar(coursework
    SOURCES
        "${java_sources_external}"
        "${root_directory}/App.java"
    OUTPUT_NAME
        app
    ENTRY_POINT
        App
)
