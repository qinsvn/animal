-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontshrink 
-dontoptimize
#-dontobfuscate

-dontwarn ***

# keep inner class,some apis depend it
#-keepattributes InnerClasses
-keepattributes  **

-keep class org.apache.*** {  *;}
-keep class com.thingmagic.*** {  *;}
-keep class com.senter.*** {  *;}

#keep these classes any time
-keep class ***KeepJustThisClass{*;}
-keep class *** implements ***KeepJustThisClass{*;}


