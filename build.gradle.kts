import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.18"
}

fluidLibrary(name = "cldr", version = "0.9.2-37") {
	allModules {
		language {
			version("1.4")
		}
	}
}

fluidLibraryModule(description = "Kotlin library for making CLDR data more easily accessible") {
	targets {
		jvm {
			dependencies {
				implementation(project(":fluid-cldr-data"))
			}
		}
	}
}
