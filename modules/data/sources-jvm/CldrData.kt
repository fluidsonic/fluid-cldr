package io.fluidsonic.cldr

import java.net.*
import java.nio.file.*
import org.unicode.cldr.util.*


/**
 * Handles extraction and caching of CLDR data from embedded resources.
 */
public object CldrData {

	private val resourceClass = CLDRFile::class.java

	/**
	 * The CLDR data version included in this build.
	 */
	public const val version: String = "48"


	/**
	 * Extracts CLDR data to a temporary directory and returns the path.
	 *
	 * If the data has already been extracted for the current version, the cached directory is returned.
	 * Thread-safe: concurrent callers will wait for the first export to complete.
	 */
	public fun export(): Path {
		val cldrDirectory = Paths.get(System.getProperty("java.io.tmpdir")).resolve("fluid-cldr")
		Files.createDirectories(cldrDirectory)

		val versionDirectory = cldrDirectory.resolve(version)

		if (Files.isDirectory(versionDirectory))
			return versionDirectory

		synchronized(this) {
			if (Files.isDirectory(versionDirectory))
				return versionDirectory

			val commonResourceUrl = requireNotNull(resourceClass.getResource("common")) {
				"Cannot locate CLDR resource files."
			}
			val temporaryDirectory = Files.createTempDirectory("cldr-export-")

			val fileSystem: FileSystem?
			val basePath = when (commonResourceUrl.protocol) {
				"file" -> {
					fileSystem = null
					Paths.get(commonResourceUrl.toURI())
				}
				"jar" -> {
					val jarUri = URI.create(commonResourceUrl.toString().substringBefore("!"))
					fileSystem = FileSystems.newFileSystem(jarUri, emptyMap<String, Any?>())
					val resourcePath = commonResourceUrl.toString().substringAfter("!")
					fileSystem.getPath(resourcePath)
				}
				else -> error("Unsupported resource protocol: ${commonResourceUrl.protocol}")
			}

			fileSystem.use {
				exportDirectory(source = basePath, destination = temporaryDirectory.resolve("common"))
				Files.move(temporaryDirectory, versionDirectory)
			}
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
