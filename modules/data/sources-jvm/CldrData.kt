package io.fluidsonic.cldr

import java.net.*
import java.nio.file.*
import org.unicode.cldr.util.*


/**
 * Handles extraction and caching of CLDR data from embedded resources.
 */
public object CldrData {

	private val resourceClass = CLDRFile::class.java
	private val commonResourcesPath = Paths.get(resourceClass.`package`.name.replace('.', '/'), "common")

	/**
	 * The CLDR data version included in this build.
	 */
	public const val version: String = "48"


	/**
	 * Extracts CLDR data to a temporary directory and returns the path.
	 *
	 * If the data has already been extracted for the current version, the cached directory is returned.
	 */
	public fun export(): Path {
		val cldrDirectory = Paths.get(System.getProperty("java.io.tmpdir")).resolve("fluid-cldr")
		Files.createDirectories(cldrDirectory)

		val versionDirectory = cldrDirectory.resolve(version)

		if (Files.isDirectory(versionDirectory))
			return versionDirectory

		val resourcesLocation = requireNotNull(resourceClass.protectionDomain.codeSource.location?.toURI()) {
			"Cannot locate CLDR resource files."
		}
		val resourcesPath = Paths.get(resourcesLocation)
		val temporaryDirectory = Files.createTempDirectory("cldr-export-")

		val fileSystem: FileSystem?
		val basePath = when (Files.isDirectory(resourcesPath)) {
			true -> {
				fileSystem = null
				resourcesPath.resolve(commonResourcesPath)
			}
			false -> {
				fileSystem = FileSystems.newFileSystem(URI.create("jar:$resourcesLocation"), emptyMap<String, Any?>())
				fileSystem.getPath(commonResourcesPath.toString())
			}
		}

		fileSystem.use {
			exportDirectory(source = basePath, destination = temporaryDirectory.resolve("common"))

			Files.move(temporaryDirectory, versionDirectory)
		}

		return versionDirectory
	}


	private fun exportDirectory(source: Path, destination: Path) {
		Files.createDirectories(destination)

		Files.list(source).use { files ->
			files.forEach { childPath ->
				val childName = childPath.fileName.toString()
				val childDestination = destination.resolve(childName)

				if (childName.contains('.'))
					exportFile(path = childPath, destination = childDestination)
				else
					exportDirectory(source = childPath, destination = childDestination)
			}
		}
	}


	private fun exportFile(path: Path, destination: Path) {
		Files.copy(path, destination)
	}
}
