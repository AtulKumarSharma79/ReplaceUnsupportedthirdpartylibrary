# Workshop: Replace an Unmaintained Library with a Supported Alternative

## 1. Overview
The Onboarding Tracker (the Spring Boot REST API from the earlier exercises) has grown. It now has two new features, and they depend on **older third-party libraries**:

| Feature | Where | Library it uses today |
|---|---|---|
| Onboarding status: days left, working days left, date label | `OnboardingStatusService` | **Joda-Time 2.1** |
| Logging of created employees and exports | `EmployeeController`, `log4j.properties` | **log4j 1.2.17** |
| CSV export of all employees | `EmployeeCsvExporter` | **opencsv 2.3** (`net.sf.opencsv`) |

These libraries are old or unmaintained. They no longer receive security fixes, they trigger warnings in vulnerability scanners, and the projects behind some of them tell you to move on.

**Your job is to replace each one with a supported alternative that gives the same business result and keeps receiving updates.** The application must behave exactly as before: the same API responses, the same CSV output, the same log messages. The tests describe that behaviour and must pass **without changes**.

| | |
|---|---|
| **Starting point** | Spring Boot 3.5, Java 17+, Gradle, H2, three older libraries |
| **Goal** | The same features, built on supported libraries, with the old ones removed |
| **Tools** | JDK 17 or newer, Gradle (wrapper included), Bruno, an IDE |
| **Time budget** | **4 hours in total, spread over 2 days** |

## 2. Learning objectives
By the end you will be able to:
- Judge whether a library is maintained, using evidence (release dates, project status, advisories).
- Choose a replacement using clear criteria, not just the first search result.
- Replace a library while keeping behaviour identical, using tests as a safety net.
- Spot differences between libraries that look alike (defaults, quoting, line endings, date handling).
- Confirm the old library is really gone, including from the dependency tree.

## 3. Prerequisites
- **JDK 17 or newer.** Check with `java -version`.
- **Internet access** on the first build.
- **Bruno** and an IDE (IntelliJ IDEA, or VS Code with the Extension Pack for Java).

## 4. Getting started
Clone the starter project, then run the tests and start the app:

```bash
gradlew.bat test
gradlew.bat bootRun
```

On macOS or Linux, use `./gradlew` instead of `gradlew.bat`. `test` should end in `BUILD SUCCESSFUL`. If port 8080 is busy, add `--args="--server.port=8081"` to `bootRun`.

### 4.1 The API
| Request | What it does |
|---|---|
| `POST /api/employees` | Creates an employee: `{ "name": "Asha Rao", "role": "Engineer", "onboardingDate": "2026-11-10" }` returns `201`. |
| `GET /api/employees` | Lists all employees. |
| `GET /api/employees/{id}/onboarding-status?asOf=2026-11-04` | Returns days and working days until onboarding. `asOf` replaces "today" so results are repeatable. Unknown id returns `404`. |
| `GET /api/employees/export` | Downloads all employees as CSV. |

Example status response for the employee above with `asOf=2026-11-04`:

```json
{ "employeeId": 1, "name": "Asha Rao", "onboardingDate": "2026-11-10",
  "onboardingDateLabel": "Tuesday, 10 November 2026",
  "daysUntilOnboarding": 6, "workingDaysUntilOnboarding": 4, "status": "UPCOMING" }
```

### 4.2 Record the baseline
Before changing anything, call each API in Bruno and **save the responses** (including the CSV text). You will compare against them at the end.

## 5. Your task

### 5.1 Core tasks (everyone)
| # | Task | What "done" looks like |
|---|---|---|
| C1 | **Audit the three libraries.** For each one, find where it is declared and used, when it was last released, and whether the project is maintained. | The audit table in section 5.3 is filled in. |
| C2 | **Replace Joda-Time.** Choose an approved replacement and update `OnboardingStatusService`. | No `org.joda` imports. Status tests pass. Joda-Time removed from `build.gradle`. |
| C3 | **Replace log4j 1.x.** Update the logging in `EmployeeController`, and move its configuration. | No `org.apache.log4j` imports. `log4j.properties` is gone. Log message text unchanged. |
| C4 | **Replace opencsv 2.3.** Choose a maintained CSV library and update `EmployeeCsvExporter`. | No `au.com.bytecode` imports. CSV output byte-for-byte the same. |
| C5 | **Prove nothing changed.** | `gradlew.bat test` is `BUILD SUCCESSFUL` with the tests untouched. Bruno responses match your baseline. |
| C6 | **Prove the old libraries are gone.** | The dependency report shows none of the three old libraries (see Appendix A.7). |

### 5.2 Approved replacements
Use one of these. If you want something else, check with your instructor and justify it with the criteria in Appendix A.1.

| Old library | Approved replacement |
|---|---|
| Joda-Time | `java.time`, built into the JDK |
| log4j 1.x | SLF4J API with Logback (both already part of Spring Boot) |
| opencsv 2.3 | opencsv 5.x (`com.opencsv`) **or** Apache Commons CSV |

### 5.3 Audit table (for C1)

| Library | Declared in | Used in | Last release (year) | Project status | Replacement |
|---|---|---|---|---|---|
| joda-time 2.1 | | | | | |
| log4j 1.2.17 | | | | | |
| opencsv 2.3 | | | | | |

### 5.4 Stretch tasks (optional)
- **S1: Compare two CSV libraries.** Do C4 with both opencsv 5.x and Apache Commons CSV. Which defaults differ? Which needs more configuration to match the old output?
- **S2: Hunt for hidden old libraries.** Read the dependency tree for older libraries that arrive indirectly (not declared in `build.gradle`). Report each one and what pulls it in.
- **S3: Scan.** Run a dependency scanner (OWASP Dependency-Check, Trivy, or your company tool) before and after, and report what changed.
- **S4: Add a test.** Write a test that exports a name containing a double quote (for example `O"Neil`) and decide what the right output is. Does it match the old library?
- **S5: Write a checklist.** Turn what you learned into a one-page checklist your team can use for the next library replacement.

## 6. Suggested time plan (4 hours total)

| Day | Time | Steps |
|---|---|---|
| **Day 1** | 30 min | Setup, run tests, record the Bruno baseline (4.2) |
| | 30 min | Audit the libraries (C1) |
| | 60 min | Replace Joda-Time (C2) |
| **Day 2** | 30 min | Replace log4j 1.x (C3) |
| | 45 min | Replace opencsv 2.3 (C4) |
| | 30 min | Prove it works and the old libraries are gone (C5, C6) |
| | 15 min | Finish the audit table and wrap up |

Stop at 4 hours and submit what you have, with a note on what is left.

## 7. Background facts
- **log4j 1.x** reached end of life in August 2015. Its maintainers recommend moving to Log4j 2 or to SLF4J with Logback. Spring Boot already uses SLF4J with Logback by default.
- **Joda-Time** was the standard date library before Java 8. Its own project page now asks users to migrate to `java.time`. A very old version such as 2.1 also contains outdated time zone data.
- **opencsv 2.3** dates from around 2011 (confirm the exact date in task C1). The project continued under a new package name (`com.opencsv`) and still releases new versions.
- **Maintained** does not mean "has a recent commit". Look at release dates, whether security issues get fixed, who maintains it, and whether its own authors point you elsewhere.
- A replacement must provide **equivalent business functionality**. Check defaults carefully. Two libraries can both "write CSV" and still differ in quoting, line endings or escaping.
- A library you declare can also pull in older libraries of its own. These are **transitive dependencies**.

## 8. Rules
- Do not change or weaken the tests. They do not mention the old libraries, so they should pass as they are.
- Do not add another unmaintained library to replace one.
- Do not change the API paths, field names or output formats.
- Stay within 4 hours.

---

## Appendix A: Guide with examples

The examples use made-up names (`Invoice`, `Product`) so you can see the pattern. Apply it to your own code.

### A.1 How to judge and choose a library
Check these, and write down the evidence:
1. **Last release date.** Look on Maven Central (search.maven.org) or mvnrepository.com. No release for several years is a warning sign.
2. **Project status.** Does the repository say "archived", "end of life" or "use X instead"?
3. **Security.** Are known vulnerabilities fixed, or left open? Does the project publish a security policy?
4. **Fit.** Does it already ship with the JDK or Spring Boot? That means one less dependency to track.
5. **Licence.** Is it compatible with how you use it?
6. **Community.** Is it widely used and documented?

### A.2 How to work
1. Replace **one** library at a time. Run the tests after each.
2. Commit after each task.
3. Read the **first** compile error.
4. Tests are your safety net. If a test fails, the replacement behaves differently. Find out why.
5. If you are stuck for 15 minutes, ask a colleague or the instructor.

### A.3 Dates: Joda-Time to `java.time`
`java.time` is built into the JDK, so this replacement removes a dependency instead of swapping one.

| Joda-Time | `java.time` |
|---|---|
| `new org.joda.time.LocalDate(2026, 11, 10)` | `java.time.LocalDate.of(2026, 11, 10)` |
| `Days.daysBetween(a, b).getDays()` | `ChronoUnit.DAYS.between(a, b)` (returns a `long`) |
| `d.getDayOfWeek()` returns a number, 1 to 7 | `d.getDayOfWeek()` returns a `DayOfWeek` such as `DayOfWeek.SATURDAY` |
| `DateTimeConstants.SATURDAY` | `DayOfWeek.SATURDAY` |
| `DateTimeFormat.forPattern("EEEE").withLocale(l).print(d)` | `DateTimeFormatter.ofPattern("EEEE", l).format(d)` |
| `a.plusDays(1)`, `a.isAfter(b)` | The same method names |

Things to watch:
- The service receives a `java.time.LocalDate` already. See how it is converted and whether that step is still needed.
- `ChronoUnit.DAYS.between` returns a `long`. The response field is an `int`.
- Always give a formatter an explicit `Locale`, or day and month names will depend on the machine.

### A.4 Logging: log4j 1.x to SLF4J
```java
// BEFORE (log4j 1.x)
import org.apache.log4j.Logger;
private static final Logger log = Logger.getLogger(InvoiceService.class);
log.info("Invoice paid: id=" + invoice.getId());

// AFTER (SLF4J)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);
log.info("Invoice paid: id={}", invoice.getId());
```
- The `{}` placeholder is filled in only when the message is actually logged, so it is cheaper than joining strings.
- log4j 1.x read its settings from `log4j.properties`. Spring Boot reads logging settings from `application.properties`, for example `logging.level.com.example=INFO` and `logging.pattern.console=...`. Move what you need, then **delete `log4j.properties`**.
- Remove the `log4j:log4j` dependency. SLF4J and Logback come with `spring-boot-starter`, so you add nothing.

### A.5 CSV: opencsv 2.3 to a maintained library
Option 1, the maintained opencsv. The project moved to a new package name:
```java
// BEFORE
import au.com.bytecode.opencsv.CSVWriter;
// AFTER
import com.opencsv.CSVWriter;
```
```groovy
// build.gradle: remove net.sf.opencsv:opencsv:2.3, add the new coordinates
implementation 'com.opencsv:opencsv:5.x'   // use a current 5.x version from Maven Central
```
Option 2, Apache Commons CSV. Its defaults are different from the old library, so you must configure it to match the old output:
```java
CSVFormat format = CSVFormat.DEFAULT.builder()
        .setQuoteMode(QuoteMode.ALL)      // old output quotes every field
        .setRecordSeparator("\n")         // the default is "\r\n"
        .build();
try (CSVPrinter printer = new CSVPrinter(writer, format)) {
    printer.printRecord("id", "name");
}
```
Whichever you choose, the exporter test tells you if the output differs, so read its expected text carefully.

### A.6 Common problems

| What you see | Usual cause | What to try |
|---|---|---|
| `package org.joda.time does not exist` | The dependency is gone but an import remains | Find all uses: search the project for `joda` |
| `incompatible types: possible lossy conversion from long to int` | `ChronoUnit.DAYS.between` returns a `long` | Cast it, or change the type |
| CSV test fails with `\r\n` or missing quotes | The new library has different defaults | Configure quoting and line endings (A.5) |
| No log lines appear | `log4j.properties` was deleted but nothing replaces it, or the wrong logger was imported | Check you import `org.slf4j.Logger` and set the level in `application.properties` |
| Log test cannot find the message | The text of the log message changed | Keep it exactly: `Employee created: id=<id>` |
| Old library still in the dependency report | Another library pulls it in | Use `dependencyInsight` (A.7) |

### A.7 Prove the old libraries are gone
```bash
gradlew.bat dependencies --configuration runtimeClasspath
gradlew.bat dependencyInsight --dependency joda-time --configuration runtimeClasspath
```
Search the output for `joda-time`, `log4j:log4j` and `net.sf.opencsv`. Also search your source for old imports:
- Windows: `findstr /s /i "org.joda org.apache.log4j au.com.bytecode" src\*.java`
- macOS or Linux: `grep -rE "org.joda|org.apache.log4j|au.com.bytecode" src`

Note that Spring Boot already includes a bridge named `log4j-to-slf4j`. That is a modern, maintained component and is not the old log4j 1.x.

### A.8 Glossary
- **Dependency:** a library your project uses, declared in `build.gradle`.
- **Transitive dependency:** a library that arrives because another library needs it.
- **End of life (EOL):** the vendor no longer publishes fixes, including security fixes.
- **Unmaintained:** nobody is releasing updates or fixing problems.
- **Drop-in replacement:** a library that can replace another with almost no code changes. Few are truly drop-in, so test.
- **CVE:** a public ID for a known security vulnerability.

### A.9 Final checklist
- [ ] `gradlew.bat test` ends with `BUILD SUCCESSFUL`, and the tests are unchanged.
- [ ] No `org.joda`, `org.apache.log4j` or `au.com.bytecode` left in `src`.
- [ ] `log4j.properties` is deleted.
- [ ] `joda-time`, `log4j:log4j` and `net.sf.opencsv` are gone from `build.gradle` and from the dependency report.
- [ ] Bruno responses and the CSV text match your baseline.
- [ ] The audit table is filled in with evidence.
