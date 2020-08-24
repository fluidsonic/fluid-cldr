package io.fluidsonic.cldr

import java.net.*
import java.nio.file.*
import org.unicode.cldr.util.*


public object CldrData {

	private val resourceClass = CLDRFile::class.java
	private val commonResourcesPath = Paths.get(resourceClass.`package`.name.replace('.', '/'), "common")

	public const val version: String = "37"


	public fun export(): Path {
		val cldrDirectory = Paths.get(System.getProperty("java.io.tmpdir")).resolve("fluid-cldr")
		Files.createDirectories(cldrDirectory)

		val versionDirectory = cldrDirectory.resolve(version)

		if (Files.isDirectory(versionDirectory)) {
			println("Exported CLDR data found at $versionDirectory…")

			return versionDirectory
		}

		val resourcesLocation = resourceClass.protectionDomain.codeSource.location?.toURI() ?: error("Cannot find resources.")
		val resourcesPath = Paths.get(resourcesLocation)
		val temporaryDirectory = Files.createTempDirectory("cldr-export-")

		println("Exporting CLDR data from $resourcesLocation to $temporaryDirectory…")

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
			exportDirectory(path = basePath, destination = temporaryDirectory.resolve("common"))

			println("Moving CLDR data to $versionDirectory…")

			Files.move(temporaryDirectory, versionDirectory)
		}

		println("CLDR data export completed.")

		return versionDirectory
	}


	@Suppress("NAME_SHADOWING")
	private fun exportDirectory(path: Path, destination: Path) {
		Files.createDirectories(destination)

		Files.list(path).use { files ->
			files.forEach { childPath ->
				val childName = childPath.fileName.toString()
				val childDestination = destination.resolve(childName)

				if (childName.contains('.'))
					exportFile(path = childPath, destination = childDestination)
				else
					exportDirectory(path = childPath, destination = childDestination)
			}
		}
	}


	private fun exportFile(path: Path, destination: Path) {
		Files.copy(path, destination)
	}
}
