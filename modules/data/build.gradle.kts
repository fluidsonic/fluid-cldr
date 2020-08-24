import io.fluidsonic.gradle.*

plugins {
	`java-library`
}

fluidLibraryModule(description = "CLDR data used by fluid-cldr") {
	targets {
		jvmJdk8 {
			withJava()

			dependencies {
				implementation("com.google.guava:guava:29.0-jre")
				implementation("com.google.code.gson:gson:2.8.5")
				implementation("com.google.myanmartools:myanmar-tools:1.1.1")
				implementation("org.apache.ant:ant:1.10.8")

				api("com.ibm.icu:icu4j:67.1")
			}

			custom {
				compilations.getByName(org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.MAIN_COMPILATION_NAME) {
					defaultSourceSet.kotlin.setUp()

					tasks.named<Copy>(processResourcesTaskName) {
						from("external/cldr/tools/java/org/unicode/cldr/util") {
							include("data/**")

							into("org/unicode/cldr/util")
						}

						from("external/cldr") {
							include("common/**/*.dtd")
							include("common/**/*.xml")

							into("org/unicode/cldr/util")
						}
					}
				}
			}
		}
	}
}

sourceSets {
	main {
		java.setUp()
	}
}


// FIXME Why do we have to call this twice? One of the two modules will fail building depending on where we set it up.
fun SourceDirectorySet.setUp() {
	val filesToExclude: Set<File> = listOf(
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/AbstractTestLog.java",
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/TestBoilerplate.java",
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/TestFmwk.java",
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/TestLog.java",
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/TestUtil.java",
		"external/icu/icu4j/main/tests/framework/src/com/ibm/icu/dev/test/UTF16Util.java"
	).mapTo(hashSetOf(), ::file)

	srcDirs(listOf(
		"external/cldr/tools/java",
		"external/icu/icu4j/main/tests/framework/src/",
		"external/icu/icu4j/tools/misc/"
	))
	exclude(
		".settings",
		"libs",
		"org/unicode/cldr/util/data"
	)
	exclude { element ->
		filesToExclude.contains(element.file)
	}
}
