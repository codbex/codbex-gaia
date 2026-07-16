# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Gaia is a packaging/edition of the **codbex platform**, itself built on **Eclipse Dirigible**. It is not a standalone application with much logic of its own — it is a thin Spring Boot launcher that assembles a curated set of Dirigible `dirigible-components-*` dependencies into a runnable backend platform (the "Gaia Edition" — all standard backend components except the Web IDE ones). Most behavior lives in the Dirigible and `codbex-platform-parent` (`com.codbex.platform:codbex-platform-parent` — see the root `pom.xml` for the current version, e.g. `14.0.0`) artifacts, not in this repo.

## Build & run

Java 21 (Amazon Corretto). Multi-module Maven build.

```shell
# Fast build (skips checks/tests) — produces application/target/codbex-gaia-*.jar
mvn -T 1C clean install -P quick-build

# Run the standalone jar (the --add-opens flags are required)
java \
    --add-opens=java.base/java.lang=ALL-UNNAMED \
    --add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
    --add-opens=java.base/java.nio=ALL-UNNAMED \
    -jar application/target/codbex-gaia-*.jar

# Debug on port 8000: add -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
```

App serves on **port 80**; default login `admin` / `admin`. Swagger at `http://localhost/swagger-ui/index.html`.

Docker: `cd application && docker build . --tag <image>` (requires the jar to be built first), then `docker run -p 80:80 <image>`.

## Tests

```shell
mvn clean install -P unit-tests          # unit tests only
mvn clean install -P integration-tests   # integration tests (Selenium-based UI ITs, slow)
mvn clean install -P tests               # all tests
```

Run a single test class with the standard Maven selector, e.g. `mvn test -P unit-tests -Dtest=GaiaApplicationTest -pl application`. Integration tests (`*IT`) extend Dirigible base classes (`UserInterfaceIntegrationTest`) and largely re-run Dirigible's own common test suites (see `integration-tests/.../DirigibileCommonTestSuiteIT.java`) rather than testing Gaia code directly.

## Formatting

```shell
mvn verify -P format
```

Formatting is enforced; run this before committing Java changes.

## Spring profiles

`application.properties` sets `spring.profiles.active=common,app-default`. **Dirigible is only active when both `common` and `app-default` profiles are present** — when activating another profile you must add them explicitly, e.g. `SPRING_PROFILES_ACTIVE=common,snowflake,app-default`.

## Module layout

- **application/** — the Spring Boot app. `GaiaApplication` (`@SpringBootApplication` scanning `com.codbex.gaia` + `org.eclipse.dirigible.components`) is the entry point. Its `pom.xml` is the real definition of the edition: the list of `dirigible-components-*` dependencies there determines which engines/APIs/UI resources are bundled (core, security basic/keycloak/cognito/snowflake, database, BPM flowable, CMS, dashboard, inbox, documents, the Harmonia app shells `resources-application`/`resources-my`/`resources-partner` served at `/services/web/{application,my,partner}/`, etc.). To add or remove a platform capability, edit this dependency list. Gaia cherry-picks individual artifacts rather than pulling Dirigible's `group-ui`/`group-ide` poms (which Atlas uses), because those groups also drag in Web IDE pieces and `dirigible-components-ui-menu-help` (replaced here by the custom `codbex-gaia-components-ui-menu-help`). When a `/services/web/<x>/` path 404s that works on Atlas, the fix is usually adding the matching `dirigible-components-resources-<x>` (content jars ship `META-INF/dirigible/<x>/`); the shared webjars (alpinejs, @codbex/harmonia, lucide, pinecone-router, i18next) come transitively from `resources-application-core` in `group-resources-ui`.
- **branding/** — logo/favicon and `project.json` packaged as a Dirigible content project under `META-INF/dirigible/gaia-branding/`.
- **components/** — Gaia-specific Dirigible content components (currently only `ui/menu-help`). New custom UI/content lives here.
- **integration-tests/** — UI/integration tests against the assembled platform.

## Dirigible content convention

`branding` and `components/*` are not normal Java modules — they ship **Dirigible content** under `src/main/resources/META-INF/dirigible/<guid>/`. Each has a `project.json` (with a `guid`) and may contain `extensions/*.extension`, `configs/*.js`, `translations/<locale>/translation.json`, HTML, images, etc. These are scanned and served at runtime by the Dirigible engine. When adding content, follow this directory structure and register menu items / behavior via `.extension` files.

## Product context

Per the README, Gaia is the **basis platform for products built with and for codbex tools and runtimes** — it bundles the standard backend components (minus the Web IDE) that customer-facing codbex products extend. The codbex product catalog is at https://www.codbex.com/products/. Gaia itself is not one of those end products; it is the shared foundation they inherit, so changes here affect that foundation rather than a single application.

## Versioning

This repo's version is `3.0.0-SNAPSHOT`; releases bump it and the `codbex-platform-parent` version together (see recent commits). Bumping platform capabilities usually means bumping the parent version in the root `pom.xml`.
