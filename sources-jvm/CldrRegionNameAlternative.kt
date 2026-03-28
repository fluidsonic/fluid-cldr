package io.fluidsonic.cldr


/**
 * Specifies which alternative name variant to use when retrieving a region name.
 */
public enum class CldrRegionNameAlternative {

	/** The standard, default region name. */
	normal,

	/** A shorter variant of the region name, if available. */
	short,

	/** An alternative variant of the region name, if available. */
	variant,
}
