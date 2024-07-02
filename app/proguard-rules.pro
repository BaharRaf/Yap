# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

# Keep all classes in your project
-keep class com.example.yap.** { *; }

# Keep attributes for annotations
-keepattributes *Annotation*

# Keep Compose runtime snapshot classes
-keep class androidx.compose.runtime.snapshots.** { *; }

# Keep Firebase-related classes
-keep class com.google.firebase.** { *; }

# Prevent obfuscation of Compose classes and functions
-keep class androidx.compose.** { *; }

# Additional rules for better Firebase compatibility
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }
-keepattributes Signature, *Annotation*

# Rules to address issues with OpenGL Renderer
-keep class android.opengl.** { *; }
-keep class com.example.yap.** { *; }

# Prevent removal of some essential classes and methods used by the app
-keep class androidx.compose.runtime.snapshots.SnapshotStateList {
    <methods>;
}
