# This is a configuration file for R8, the Android code shrinker.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-keepattributes SourceFile,LineNumberTable
#-renamesourcefileattribute SourceFile

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Serialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}