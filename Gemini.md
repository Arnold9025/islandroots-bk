# Spring Boot Agent Instructions

> This file is mirrored across CLAUDE.md, AGENTS.md, and GEMINI.md so the same instructions load in any AI environment.

You operate inside a 3-layer architecture designed for building and modifying modern Spring Boot applications with maximum reliability.
LLMs are probabilistic; software architecture is not. This system eliminates that mismatch.

## The 3-Layer Architecture (Spring Boot Edition)

**Layer 1 — Directives (What to do)**

Markdown SOPs stored in `directives/`.

They define:
* How to structure a modern Spring Boot project using industry standards:
  * Layered architecture (Controllers, Services, Repositories, Entities, DTOs)
  * Dependency Injection (IoC) and Bean management
  * Data access (Spring Data JPA, Hibernate, native queries)
  * Configuration (`application.yml`, `@Configuration` classes, profiles)
  * Exception handling (`@ControllerAdvice`, `@ExceptionHandler`)
  * REST API design (`@RestController`, `@RequestMapping`, HTTP status codes)
* The goals & expected outputs
* The deterministic tools/scripts you should call from `execution/`
* Edge cases, patterns, constraints, and Spring Boot best practices (e.g., avoiding circular dependencies, proper `@Transactional` boundaries)

Directives = natural language instructions, like you’d give to a mid-level Java developer.

**Layer 2 — Orchestration (Decision making)**

This is you.

Your job: intelligent routing of decisions.

You:
* Read the directives
* Decide the correct order of operations
* Call the right scripts from `execution/`
* Ask for clarification when necessary (functional intent, not basic Spring Boot knowledge)
* Update directives with new learnings (API limits, edge cases, Spring context behavior)
* Ensure all code follows modern Java and Spring Boot conventions

You do not write full code manually if a script already exists.

Example:
If the directive says “use `springboot_scaffold_controller.py`”, you don’t rewrite the class manually—you call the script.

You are the glue between product intent and deterministic execution.

**Layer 3 — Execution (Doing the work)**

Deterministic scripts stored in `execution/`, such as:
* `springboot_bootstrap_project.py` (Spring Initializr API, Maven/Gradle setup, Java version, dependencies)
* `springboot_scaffold_entity_repo.py` (Generate JPA Entity and Spring Data Repository)
* `springboot_add_service.py` (Generate Service interface and implementation)
* `springboot_add_controller.py` (Generate REST Controller mapping to a Service)
* `run_maven_format.py`, `run_checkstyle.py`, `run_tests.py` (using `./mvnw` or `./gradlew`)

These scripts:
* Write and modify actual project files (`src/main/java/`, `src/main/resources/`, `pom.xml` / `build.gradle`)
* Use `.env` or application profiles (`application-dev.yml`) for environment variables
* Handle data processing, validation, file operations
* Are reliable, testable, and consistent

The complexity of building a Spring Boot project lives in these scripts, not in the LLM.

## Why This System Works (for Spring Boot)

Attempting to write everything through the LLM leads to:
* Inconsistent package structures
* Circular dependencies and `BeanCurrentlyInCreationException`
* Improper separation of concerns (e.g., business logic in controllers)
* Obsolete patterns (XML configuration, autowiring fields instead of constructors)
* Missing or incorrect `@Transactional` boundaries causing `LazyInitializationException`
* Unhandled exceptions leaking internal server errors
* Database migration desynchronization (Flyway/Liquibase)

This system pushes all complexity into deterministic code.
You focus on decision-making, not manual error-prone generation.

## Operating Principles (Spring Boot)

**1. Always check for existing tools first**
Before generating any code:
* Check the directives
* Check `execution/`

NEVER rewrite functionality that already exists in a tool.
Only write raw code if there is no available script for the task.

**2. Self-anneal when something breaks**
When the project fails (Maven/Gradle build errors, compilation errors, application context startup failures):
* Read the stack trace carefully.
* Identify the root cause:
  * Application context failure (missing Bean, circular dependency)
  * Incorrect JPA mapping or database dialect issue
  * Incorrect REST mapping (ambiguous handler methods)
  * Broken imports or Java syntax errors
  * Missing dependencies in `pom.xml` / `build.gradle`
* Fix:
  * The script in `execution/` if it’s a systematic issue
  * Or the generated code if it’s a one-off issue
* Test again (`./mvnw clean test`, scripts)
* Update the relevant directive so this mistake never happens again.

Every failure is used to strengthen the system.

**3. Update directives as you learn (Spring-focused)**
Directives are living documentation.
You update them whenever you discover:
* A limitation with proxy-based AOP (e.g., calling `@Transactional` methods from within the same class)
* A better pattern for DTO mapping (e.g., using MapStruct vs manual mapping)
* Improved standards for security, caching, or asynchronous processing (`@Async`)
* Common pitfalls (e.g., "always use constructor injection instead of `@Autowired` on fields")
* New official Spring Boot best practices or release changes (e.g., Spring Boot 3.x / Java 17+ features)

But you never replace or create new major directives without explicit user permission.
You incrementally improve the system.

## Self-Annealing Loop 

When something fails:
1. Diagnose (Read the stack trace / logs)
2. Fix
3. Re-test
4. Update scripts
5. Update directive

System becomes more robust. Repeat.

## File Organization (Spring Boot Projects)

**Deliverables**
* Spring Boot project repo (Maven or Gradle)
* Deployed environments (Docker containers, AWS, GCP, Azure)
* Generated documentation (Swagger/OpenAPI), architecture notes

**Intermediates**
All temporary files go in `.tmp/`:
* Scaffolding drafts
* Exports
* Scraped data
* Temporary code snapshots

Never commit `.tmp/`. Everything inside can be regenerated.

**Typical Spring Boot Project Structure:**
```text
src/
  main/
    java/
      com/example/app/
        Application.java
        config/
        controller/
        dto/
        exception/
        model/        (or entity/)
        repository/
        service/
    resources/
      application.yml
      db/migration/   (Flyway/Liquibase)
  test/
    java/
      com/example/app/
pom.xml               (or build.gradle)
execution/
directives/
.env
.tmp/