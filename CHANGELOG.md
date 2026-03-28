# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/), and this project adheres to
[Semantic Versioning](https://semver.org/).

## [0.10.0-48] - 2026-03-28

### Changed

- Migrated to fluid-gradle 3.0.0, Kotlin 2.3.20, Gradle 9.4.1, JDK 21+
- Updated CLDR data from version 37 to 48
- Updated ICU4J from 67.1 to 78.3
- Updated Guava from 29.0-jre to 33.4.8-jre
- Updated Gson from 2.8.5 to 2.13.1
- Updated Ant from 1.10.8 to 1.10.15
- Updated Trang from 20220510 to 20241231
- Renamed default branch from `master` to `main`
- Public collections (`localeIds`, `regionIds`) now use deterministic iteration order

### Added

- KDoc documentation for all public APIs
- Unit tests for all public APIs
- GitHub Actions CI workflow
- Dependencies: Trang, Semver4j, JSR 305 (required by CLDR 48 tools)

### Removed

- Debug `println()` statements from production code
- ICU4J test framework and tools/misc source compilation (no longer needed by CLDR 48)
