import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "3.0.0"
}

fluidLibrary(name = "cldr", version = "0.10.0-48")

fluidLibraryModule(description = "Kotlin library for making CLDR data more easily accessible") {
	targets {
		jvm {
			dependencies {
				implementation(project(":fluid-cldr-data"))
			}
		}
	}
}
