package io.fluidsonic.cldr

import org.unicode.cldr.util.*


/**
 * Main entry point for accessing CLDR (Common Locale Data Repository) data.
 */
public object Cldr {

	/**
	 * The CLDR data version.
	 */
	public const val version: String = CldrData.version

	init {
		System.setProperty("CLDR_DIR", CldrData.export().toString())
	}

	private val codes = StandardCodes.make()

	private val factory = Factory.make(CLDRPaths.MAIN_DIRECTORY, ".*")

	/**
	 * Set of all available locale identifiers.
	 */
	public val localeIds: Set<String> = factory.available.toCollection(linkedSetOf())

	/**
	 * Set of all available region/territory identifiers.
	 */
	public val regionIds: Set<String> = codes.getAvailableCodes("territory").toCollection(linkedSetOf())


	/**
	 * Retrieves the localized name of a region.
	 *
	 * @param localeId the locale to use for the localized name
	 * @param regionId the identifier of the region to look up
	 * @param alternative the alternative name variant to retrieve
	 * @return the localized region name, or `null` if not available
	 */
	public fun regionName(localeId: String, regionId: String, alternative: CldrRegionNameAlternative = CldrRegionNameAlternative.normal): String? {
		val file = factory.make(localeId, false)
		val basePath = "//ldml/localeDisplayNames/territories/territory[@type=\"$regionId\"]"
		val path = when (alternative) {
			CldrRegionNameAlternative.normal -> basePath
			CldrRegionNameAlternative.short -> "$basePath[@alt=\"short\"]"
			CldrRegionNameAlternative.variant -> "$basePath[@alt=\"variant\"]"
		}

		return file.getStringValue(path)?.takeIf { it != CldrUtility.INHERITANCE_MARKER }
	}
}
