package io.fluidsonic.cldr

import org.unicode.cldr.util.*


public object Cldr {

	public const val version: String = CldrData.version

	init {
		System.setProperty("CLDR_DIR", CldrData.export().toString())

		println("Parsing CLDR standard codes…")
	}

	private val codes = StandardCodes.make()

	init {
		println("Parsing CLDR files…")
	}

	private val factory = Factory.make(CLDRPaths.MAIN_DIRECTORY, ".*")

	public val localeIds: Set<String> = factory.available
	public val regionIds: Set<String> = codes.getAvailableCodes("territory")


	public fun regionName(localeId: String, regionId: String, alternative: CldrRegionNameAlternative = CldrRegionNameAlternative.normal): String? {
		val file = factory.make(localeId, false)
		val key = CLDRFile.getKey(CLDRFile.TERRITORY_NAME, regionId)
		val path = when (alternative) {
			CldrRegionNameAlternative.normal -> key
			CldrRegionNameAlternative.short -> "$key[@alt=\"short\"]"
			CldrRegionNameAlternative.variant -> "$key[@alt=\"variant\"]"
		}

		return file.getStringValue(path)?.takeIf { it != CldrUtility.INHERITANCE_MARKER }
	}
}
