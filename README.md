### eclipse environment ###

1. eclipse configure 

maven plugin "mvn" Nodeclipse

and "Android Configurator for M2E" http://rgladwell.github.com/m2e-android/updates/

2. maven android sdk deployer

config ${android.home} in pom.xml

$ git clone http://github.com/simpligility/maven-android-sdk-deployer.git

$ cd maven-android-sdk-deployer/

$ mvn install -P 5.1 # need platforms/android-22, add-ons/addon-google_apis-google-22

$ mvn install -P android-extras # need android support libary, google play licensing library, google play apk expansion library, all sdk platform(ignore)

$ mvn install -P repositories # need android suppport repository, google repository

3. eclipse import cornucopia

maven clean and generate-sources
 
4. import library 

* cas_appcompat-v7

* cas_support-v4

* cff_drawee

* cff_fbcore

* cff_fresco

* cff_imagepipeline

configure android version

appcompat drawee and imagepipeline is library

reopen cornucopia project


5. maven run

$ mvn install:install-file -Dfile=libs/BaiduLBS_Android.jar -DgroupId=com.baidu.android -DartifactId=baidulbs -Dversion=5.0.0 -Dpackaging=jar

$ mvn clean compile

$ mvn package android:deploy android:run