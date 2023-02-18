plugins {
	id ("com.android.application")
	id ("org.jetbrains.kotlin.android")
	id ("org.jetbrains.kotlin.kapt")
	id ("dagger.hilt.android.plugin")
	id ("com.google.gms.google-services")
	id ("com.google.firebase.appdistribution")
}

kapt {
 correctErrorTypes = true
}

android {
	compileSdk = 33

	buildFeatures {
		viewBinding = true
	}

	defaultConfig {
		applicationId = "com.lampotrias.seeweather"
		minSdk = 21
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		javaCompileOptions {
			annotationProcessorOptions {
				arguments += mapOf(
					"room.schemaLocation" to "$projectDir/schemas",
					"room.incremental" to "true"
				)
			}
		}

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFile(getDefaultProguardFile("proguard-android.txt"))
			// global proguard settings
			proguardFile(file("proguard-rules.pro"))

			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

			firebaseAppDistribution {
              artifactType="APK"
              testers="bitrixman@gmail.com"
          }
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
    namespace = "com.lampotrias.seeweather"
}

dependencies {

	val nav_version = "2.5.3"
	val lifecycle_version = "2.5.1"
	val hilt_version = "2.44.2"
	val retrofit_version = "2.9.0"
	val okhttp_version = "4.10.0"
	val fresco_version = "2.6.0"

	implementation ("androidx.core:core-ktx:1.9.0")
	implementation ("androidx.appcompat:appcompat:1.6.1")
	implementation ("com.google.android.material:material:1.8.0")
	implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation ("junit:junit:4.13.2")
	androidTestImplementation ("androidx.test.ext:junit:1.1.5")
	androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

	implementation ("com.google.android.gms:play-services-auth:20.4.1")

	implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
	kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

	implementation("androidx.preference:preference:1.2.0")

	implementation("androidx.fragment:fragment-ktx:1.5.5")

	implementation(platform("com.google.firebase:firebase-bom:31.2.2"))
	implementation ("com.google.firebase:firebase-analytics-ktx")
	// Navigation
	implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
	implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

	// Coroutines
	implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

	// Lifecycle
	implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

	// Hilt
	implementation ("com.google.dagger:hilt-android:$hilt_version")
	kapt ("com.google.dagger:hilt-compiler:$hilt_version")

	// Network
	implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
	implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
	implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

	// room
	implementation ("androidx.room:room-runtime:2.5.0")
	kapt ("androidx.room:room-compiler:2.5.0")

	// Graphics
	implementation ("com.facebook.fresco:fresco:$fresco_version")
}