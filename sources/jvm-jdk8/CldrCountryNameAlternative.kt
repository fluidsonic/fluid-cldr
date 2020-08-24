package io.fluidsonic.cldr

import com.ibm.icu.text.*


public enum class CldrCountryNameAlternative(
	internal val transform: Transform<String, String>?
) {

	normal(transform = null),
	short(transform = { "short" }),
	variant(transform = { "variant" })
}
