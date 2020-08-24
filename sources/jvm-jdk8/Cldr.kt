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

	public val territoryIds: Set<String> = codes.getAvailableCodes("territory")
	public val localeIds: Set<String> = factory.available


	public fun countryName(localeId: String, territoryId: String, alternative: CldrCountryNameAlternative = CldrCountryNameAlternative.normal): String? =
		factory.make(localeId, false).getName(CLDRFile.TERRITORY_NAME, territoryId, alternative.transform)
}
