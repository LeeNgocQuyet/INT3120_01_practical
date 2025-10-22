buildscript {
    extra.apply {
        set("nav_version", "2.9.5")
        set("room_version", "2.7.2")
        set("arch_lifecycle_version", "2.9.2")
    }
}

plugins {
    id("com.android.library") version "8.11.2" apply false
    id("androidx.navigation.safeargs") version "2.9.5" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}