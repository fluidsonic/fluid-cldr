package io.fluidsonic.cldr

import java.nio.file.*
import kotlin.test.*


class CldrDataTest {

	@Test
	fun version_returnsExpectedValue() {
		assertEquals(actual = CldrData.version, expected = "48")
	}

	@Test
	fun export_returnsValidPath() {
		val path = CldrData.export()
		assertTrue(Files.isDirectory(path))
	}

	@Test
	fun export_isIdempotent() {
		val first = CldrData.export()
		val second = CldrData.export()
		assertEquals(actual = first, expected = second)
	}

	@Test
	fun export_containsCommonDirectory() {
		val path = CldrData.export()
		assertTrue(Files.isDirectory(path.resolve("common")))
	}

	@Test
	fun export_containsMainLocaleData() {
		val path = CldrData.export()
		assertTrue(Files.isDirectory(path.resolve("common/main")))
	}
}
