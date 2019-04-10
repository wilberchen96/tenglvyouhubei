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

##---------------状态栏混淆--------------##
 -keep class com.gyf.barlibrary.* {*;}
 -dontwarn com.gyf.barlibrary.**

##---------------RxLifecycle混淆--------------##
-keep class com.trello.rxlifecycle2.** { *; }
-keep interface com.trello.rxlifecycle2.** { *; }
##---------------RxLifecycle混淆--------------##


##---------------materialdialogs混淆--------------##
-keep class com.afollestad.materialdialogs.** { *; }
-keep interface com.afollestad.materialdialogs.** { *; }
##---------------materialdialogs混淆--------------##

##---------------rxpermissions混淆--------------##
-keep class com.tbruyelle.rxpermissions2.** { *; }
##---------------rxpermissions混淆--------------##

# http中上传下载进度 keep
-keep class com.hbtenglv.shangfu.http.RqAndRsWrapper.** { *; }
-keep interface com.hbtenglv.shangfu.http.RqAndRsWrapper.** { *; }

##---------------友盟混淆--------------##
-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}
##-------------------友盟混淆结束-----------------##

##------model不混淆-----##
-keep class com.wuhan.tl.activity.modles.** { *; }
##------model不混淆-----##



##--------------搬山ekt-------------------##

-keep public class cn.bertsir.zbar.** {*;}
-keep public class com.ivsign.android.IDCReader.** {*;}
-keep public class com.otg.idcard.** {*;}
-keep public class com.hbtl.ekt.chain.** {*;}
-keep public class com.hbtl.ekt.bean.** {*;}
-keep class com.hbtl.ekt.bean.** { *; }
-keep class com.hbtl.ekt.db.db.models.** { *; }

############## ax6737 身份证硬解相关代码不做混淆 ############
-keep public class com.handheld.** {*;}
-keep public class com.pci.pca.readcard.** {*;}
-keep public class com.synjones.bluetooth.** {*;}
-keep public class com.zkteco.android.** {*;}
############## ax6737 身份证硬解相关代码不做混淆 ############
############## ax6737 二维码硬解相关代码不做混淆 ############
-keep public class cn.pda.** {*;}
-keep public class com.demo.scan.** {*;}
############## ax6737 二维码硬解相关代码不做混淆 ############





-keep class com.lidroid.** { *; }
-keep class * extends java.lang.annotation.Annotation { *; }



-keep public class com.tencent.** {*;}
-keep public class org.apache.http.** {*;}
-keep public class com.nineoldandroids.** {*;}

-keepclassmembers public class * {
   public void onResponse(***);
}

##----------------start----Picasso混淆配置----start----
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}
##----------------end----Picasso混淆配置----end----

##----------------start----RxJava、RxAndroid混淆配置----start----
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
##----------------end----RxJava、RxAndroid混淆配置----end----


##----------------start----okhttp3混淆配置----start----
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
##----------------end----okhttp3混淆配置----end----


##----------------start----zxing混淆配置----start----
-dontwarn  com.google.zxing.**
-keep class com.google.zxing.** {*;}
-dontwarn  demo.**
-keep class demo.** {*;}
-keep public interface com.google.zxing.**
-keep public class com.google.zxing.** { *; }
##----------------end----zxing混淆配置----end----


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
##---------------End: proguard configuration for Gson  ----------

##----------------start----数据库 greenDao混淆配置----start----
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
##----------------end------数据库 greenDao混淆配置----end------



##----------------start----glide混淆配置----start----
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
##----------------end------glide混淆配置----end------

##----------------start------阿里云oss混淆配置----start------
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**
##----------------end------阿里云oss混淆配置----end------


##----------------start------retrofit2混淆配置----start------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
##----------------end------retrofit2混淆配置----end------

##----------------start------eventbus混淆配置----start------
-keep class org.greenrobot.** {*;}
-keep class de.greenrobot.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
##----------------end------eventbus混淆配置----end------

##----------------start------butterknife混淆配置----start------
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keep public class * implements butterknife.Unbinder { public <init>(...); }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
##----------------end------butterknife混淆配置----end------

##----------------start------bugly混淆配置----start------
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }
-keep class android.support.**{*;}
##----------------end------bugly混淆配置----end------

##----------------start------虹软人脸检测混淆配置----start------
-keep class com.arcsoft.** {*;}
##----------------end------虹软人脸检测混淆配置----end------


##---------------Begin: proguard configuration for fastjson  ----------

#-keepnames class * implements java.io.Serializable
-keep class com.alibaba.fastjson.** {*; }
-dontwarn com.alibaba.fastjson.**
-keep public class * implements java.io.Serializable {
        public *;
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers class * {
public <methods>;
}
-keepclassmembers class **.R$* {
  public static <fields>;
}

-keep class okhttp3.** { *; }
-keep class com.jakewharton.picasso.** { *; }
-keep class com.apache.http.**
-keep interface com.apache.http.**
-keep enum com.apache.http.**

-keep interface okhttp3.** { *; }

##---------------End: proguard configuration for fastjson  ----------

#---------------------------------google------------------------------------
#support-v4包
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
#support-v7包
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }
#support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }



#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#---------------------------------基本指令区----------------------------------
-ignorewarnings
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*

-keepattributes Signature
-keepattributes Exceptions
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes *Annotation*
-keepattributes SourceFile
-keepattributes LineNumberTable
-keepattributes *JavascriptInterface*

-optimizationpasses 7 #压缩比
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-dontpreverify#不预校验
-dontusemixedcaseclassnames
#----------------------------------------------------------------------------
#---------------------------------默认保留区---------------------------------

-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------


##-----------------end--------------##