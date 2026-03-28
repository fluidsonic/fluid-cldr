package io.fluidsonic.cldr

import kotlin.test.*


class CldrTest {

	@Test
	fun version_returnsExpectedValue() {
		assertEquals(actual = Cldr.version, expected = "48")
	}

	@Test
	fun localeIds_isNotEmpty() {
		assertTrue(Cldr.localeIds.isNotEmpty())
	}

	@Test
	fun localeIds_containsKnownLocales() {
		assertTrue("en" in Cldr.localeIds)
		assertTrue("de" in Cldr.localeIds)
		assertTrue("ja" in Cldr.localeIds)
		assertTrue("fr" in Cldr.localeIds)
	}

	@Test
	fun regionIds_isNotEmpty() {
		assertTrue(Cldr.regionIds.isNotEmpty())
	}

	@Test
	fun regionIds_containsKnownRegions() {
		assertTrue("US" in Cldr.regionIds)
		assertTrue("DE" in Cldr.regionIds)
		assertTrue("JP" in Cldr.regionIds)
		assertTrue("FR" in Cldr.regionIds)
	}

	@Test
	fun regionName_returnsCorrectName() {
		assertEquals(actual = Cldr.regionName("en", "US"), expected = "United States")
		assertEquals(actual = Cldr.regionName("en", "DE"), expected = "Germany")
		assertEquals(actual = Cldr.regionName("de", "US"), expected = "Vereinigte Staaten")
		assertEquals(actual = Cldr.regionName("de", "DE"), expected = "Deutschland")
	}

	@Test
	fun regionName_withShortAlternative() {
		val result = Cldr.regionName("en", "US", CldrRegionNameAlternative.short)
		// Short name may be "US" or null depending on CLDR data
		if (result != null) {
			assertTrue(result.isNotEmpty())
		}
	}

	@Test
	fun regionName_withVariantAlternative() {
		val result = Cldr.regionName("en", "US", CldrRegionNameAlternative.variant)
		// Variant may or may not exist
		if (result != null) {
			assertTrue(result.isNotEmpty())
		}
	}

	@Test
	fun regionName_returnsNullForInvalidRegion() {
		assertNull(Cldr.regionName("en", "INVALID_REGION"))
	}
}
