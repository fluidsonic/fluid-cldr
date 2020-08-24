import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.7"
}

fluidLibrary(name = "cldr", version = "0.9.0-37")

fluidLibraryModule(description = "Kotlin library for making CLDR data more easily accessible") {
	targets {
		jvmJdk8 {
			dependencies {
				implementation(project(":fluid-cldr-data"))
			}
		}
	}
}
