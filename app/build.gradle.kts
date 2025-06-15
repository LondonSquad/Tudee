plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.london.tudee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.london.tudee"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.register("installGitHooks") {
    doLast {
        // Install commit-msg hook (renamed from commit-message)
        val commitMsgSource = file("../scripts/hooks/commit-message")
        val commitMsgTarget = file("../.git/hooks/commit-msg")
        if (!commitMsgTarget.exists() || commitMsgTarget.readText() != commitMsgSource.readText()) {
            commitMsgTarget.writeText(commitMsgSource.readText())
            commitMsgTarget.setExecutable(true)
            println("✅ commit-msg hook installed and made executable.")
        } else {
            println("✅ commit-msg hook is already up to date.")
        }
        
        // Install pre-push hook (renamed from branch-naming)
        val prePushSource = file("../scripts/hooks/branch-naming")
        val prePushTarget = file("../.git/hooks/pre-push")
        if (!prePushTarget.exists() || prePushTarget.readText() != prePushSource.readText()) {
            prePushTarget.writeText(prePushSource.readText())
            prePushTarget.setExecutable(true)
            println("✅ pre-push hook installed and made executable.")
        } else {
            println("✅ pre-push hook is already up to date.")
        }
    }
}
//gradle.projectsEvaluated {
//    tasks["build"].dependsOn("installGitHooks")
//}