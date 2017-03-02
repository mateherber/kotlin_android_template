import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.builder.core.DefaultApiVersion
import com.android.builder.core.DefaultProductFlavor
import com.android.builder.model.ApiVersion

import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

buildscript {
    //Temporary hack until Android plugin has proper support
    System.setProperty("com.android.build.gradle.overrideVersionCheck",  "true")

    repositories {
        jcenter()
        gradleScriptKotlin()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:2.2.3")
        classpath(kotlinModule("gradle-plugin"))
    }
}

repositories {
    jcenter()
    gradleScriptKotlin()
}


apply {
    plugin<AppPlugin>()
    plugin<KotlinAndroidPluginWrapper>()
}

android {
    buildToolsVersion("25.0.1")
    compileSdkVersion(25)

    sourceSets {
       getByName("main"){
           java.setSrcDirs(listOf("src/main/kotlin"))
       }
    }

    defaultConfig {
        setMinSdkVersion(19)
        setTargetSdkVersion(25)

        applicationId = "org.kotlin.sample"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    compile("com.android.support:appcompat-v7:25.2.0")
    compile(kotlinModule("stdlib"))
}

//Extension functions to allow comfortable references
fun Project.android(configuration: AppExtension.() -> Unit) = configure(configuration)

fun DefaultProductFlavor.setMinSdkVersion(value: Int) = setMinSdkVersion(value.asApiVersion())

fun DefaultProductFlavor.setTargetSdkVersion(value: Int) = setTargetSdkVersion(value.asApiVersion())

fun Int.asApiVersion(): ApiVersion = DefaultApiVersion.create(this)