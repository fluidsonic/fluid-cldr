# fluid-cldr

Kotlin JVM library for accessing Unicode CLDR (Common Locale Data Repository) data. Provides locale and region name lookups.

## Build

```shell
./gradlew build
```

## Test

```shell
./gradlew allTests
```

## Architecture

Two modules:
- **fluid-cldr** (root) — public API: `Cldr` object, `CldrRegionNameAlternative` enum
- **fluid-cldr-data** — CLDR data extraction from embedded JAR resources

The data module compiles Java sources from CLDR and ICU git submodules. Initialize them with:
```shell
git submodule update --init --recursive
```

## Conventions

- Tags have no `v` prefix (e.g. `0.10.0-48`)
- Version format: `<library-version>-<CLDR-version>` (e.g. `0.10.0-48` = library 0.10.0, CLDR 48)
- JVM-only project (no JS/multiplatform targets)
- Tab indentation
