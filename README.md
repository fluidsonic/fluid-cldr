fluid-cldr
==========

[![Maven Central](https://img.shields.io/maven-central/v/io.fluidsonic.cldr/fluid-cldr?label=Maven%20Central)](https://search.maven.org/artifact/io.fluidsonic.cldr/fluid-cldr)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20%20(JVM)-blue.svg)](https://github.com/JetBrains/kotlin/releases/v2.3.20)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin JVM library for accessing [Unicode CLDR](https://cldr.unicode.org/) (Common Locale Data Repository) data.
Currently provides locale identifiers, region/territory identifiers, and localized region names.

Used by [fluid-i18n](https://github.com/fluidsonic/fluid-i18n).


Usage
-----

```kotlin
import io.fluidsonic.cldr.*

// CLDR data version included in this build
println(Cldr.version) // "48"

// Available locale identifiers (e.g. "en", "de", "ja")
val locales: Set<String> = Cldr.localeIds

// Available region/territory identifiers (e.g. "US", "DE", "JP")
val regions: Set<String> = Cldr.regionIds

// Get the localized name of a region
Cldr.regionName("en", "US") // "United States"
Cldr.regionName("de", "US") // "Vereinigte Staaten"
Cldr.regionName("ja", "JP") // "日本"

// Short and variant alternatives
Cldr.regionName("en", "US", CldrRegionNameAlternative.short)   // "US"
Cldr.regionName("en", "US", CldrRegionNameAlternative.variant) // null (not available for all regions)
```


Installation
------------

`build.gradle.kts`:

```kotlin
dependencies {
	implementation("io.fluidsonic.cldr:fluid-cldr:0.10.0-48")
}
```


Requirements
------------

- JDK 21+
- Kotlin 2.3+


License
-------

Apache 2.0
