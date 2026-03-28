import io.fluidsonic.gradle.*
import org.gradle.api.tasks.compile.JavaCompile

fluidLibraryModule(description = "CLDR data used by fluid-cldr") {
	targets {
		jvm {
			dependencies {
				implementation("com.google.code.gson:gson:2.13.1")
				implementation("com.google.guava:guava:33.4.8-jre")
				implementation("com.google.myanmartools:myanmar-tools:1.1.1")
				implementation("com.vdurmont:semver4j:3.1.0")
				implementation("org.apache.ant:ant:1.10.15")
				implementation("org.relaxng:trang:20241231")

				compileOnly("com.google.code.findbugs:jsr305:3.0.2")

				api("com.ibm.icu:icu4j:78.3")
			}

			custom {
				project.tasks.withType<JavaCompile>().configureEach {
					sourceCompatibility = "21"
					targetCompatibility = "21"
				}

				compilations.getByName("main") {
					val cldrSourceDir = "external/cldr/tools/cldr-code/src/main/java"
					val sourceExcludes = listOf(
						".settings",
						"libs",
						"org/unicode/cldr/util/data",
					)

					defaultSourceSet.kotlin.srcDir(cldrSourceDir)
					defaultSourceSet.kotlin.exclude(sourceExcludes)

					compileJavaTaskProvider!!.configure {
						source(project.fileTree(cldrSourceDir).matching { exclude(sourceExcludes) })
					}

					tasks.named<Copy>(processResourcesTaskName) {
						from("external/cldr/tools/cldr-code/src/main/resources") {
							include("org/unicode/cldr/**")
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
