# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/thom/Android/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes Signature
-keepattributes *Annotation*

# For RoboSpice
#Results classes that only extend a generic should be preserved as they will be pruned by Proguard
#as they are "empty", others are kept
#-keep class <your REST POJOs package>.**


#RoboSpice requests should be preserved in most cases
#-keepclassmembers class <your RoboSpice requests package>.** {
#  public void set*(***);
#  public *** get*();
#  public *** is*();
#}

#Warnings to be removed. Otherwise maven plugin stops, but not dangerous
-dontwarn android.support.**
-dontwarn com.sun.xml.internal.**
-dontwarn com.sun.istack.internal.**
-dontwarn org.codehaus.jackson.**
-dontwarn org.springframework.**
-dontwarn java.awt.**
-dontwarn javax.security.**
-dontwarn java.beans.**
-dontwarn javax.xml.**
-dontwarn java.util.**
-dontwarn org.w3c.dom.**
-dontwarn com.google.common.**
-dontwarn com.octo.android.robospice.persistence.**

-dontwarn com.octo.android.robospice.request.simple.**


## fastjson
-dontwarn com.alibaba.fastjson.**

## retrofit2
-dontwarn retrofit2.**

## baidu map sdk
-dontwarn com.baidu.pano.platform.http.**

## copia-tools
-dontwarn com.cornucopia.tools.CopiaModule

# copia-kotlin
# can't find referenced class com.beloo.widget.chipslayoutmanager.Orientation
-dontwarn com.beloo.widget.chipslayoutmanager.**
-dontwarn jack.hive.**  # annotation

# fragment with navigation
-keep public class * extends android.support.v4.app.Fragment



## for aidl service
-keep class com.cornucopia.service.** {
  *;
}

## for hotfix load classname
-keep class com.cornucopia.hotfix.** {
  *;
}