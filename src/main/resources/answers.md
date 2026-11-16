# Exercise 4 - Unmaintained Libraries

## Example Request Responses:

`GET /api/employees`

```json
[
  {
    "id": 1,
    "name": "Asha Rao",
    "role": "Engineer",
    "onboardingDate": "2026-11-10"
  }
]
```
`GET /api/employees/{id}/onboarding-status?asOf=2026-11-04 (id = 1)`

```json
{
  "employeeId": 1,
  "name": "Asha Rao",
  "onboardingDate": "2026-11-10",
  "onboardingDateLabel": "Tuesday, 10 November 2026",
  "daysUntilOnboarding": 6,
  "workingDaysUntilOnboarding": 4,
  "status": "UPCOMING"
}
```

`GET /api/employees/export`

"id","name","role","onboardingDate"
"1","Asha Rao","Engineer","2026-11-10"

```csv
"id","name","role","onboardingDate"
"1","Asha Rao","Engineer","2026-11-10"
```

## C1: Audit Table

| Library | Declared in | Used in | Version release date (year) | Project status | Replacement | LTS support for current version? |
|---|---|---|---|---|---|---|
| joda-time 2.1 | `build.gradle` | `.../service/OnboardingStatusService.java` | 2012 | Active | `java.time` | Yes, but only for compatability and only until 3.x |
| log4j 1.2.17 | `build.gradle` | `.../controller/EmployeeController.java`,  | 2012 | Active (Relocated on MVN repos) | SLF4J API with Logback | No (last support for 1.x was 2015) |
| opencsv 2.3 | `build.gradle` | `.../service/EmployeeCsvExporter.java` | 2011 | Active (Relocated on MVN repos) | opencsv 5.x (`com.opencsv`) | No (not even mentioned on upgrade support notes on the official website) |

## C6: Dependecy Graph Output and grep Results

### Dependecy graph

```
+--- org.springframework.boot:spring-boot-starter-web -> 3.5.7
|    +--- org.springframework.boot:spring-boot-starter:3.5.7
|    |    +--- org.springframework.boot:spring-boot:3.5.7
|    |    |    +--- org.springframework:spring-core:6.2.12
|    |    |    |    \--- org.springframework:spring-jcl:6.2.12
|    |    |    \--- org.springframework:spring-context:6.2.12
|    |    |         +--- org.springframework:spring-aop:6.2.12
|    |    |         |    +--- org.springframework:spring-beans:6.2.12
|    |    |         |    |    \--- org.springframework:spring-core:6.2.12 (*)
|    |    |         |    \--- org.springframework:spring-core:6.2.12 (*)
|    |    |         +--- org.springframework:spring-beans:6.2.12 (*)
|    |    |         +--- org.springframework:spring-core:6.2.12 (*)
|    |    |         +--- org.springframework:spring-expression:6.2.12
|    |    |         |    \--- org.springframework:spring-core:6.2.12 (*)
|    |    |         \--- io.micrometer:micrometer-observation:1.14.12 -> 1.15.5
|    |    |              \--- io.micrometer:micrometer-commons:1.15.5
|    |    +--- org.springframework.boot:spring-boot-autoconfigure:3.5.7
|    |    |    \--- org.springframework.boot:spring-boot:3.5.7 (*)
|    |    +--- org.springframework.boot:spring-boot-starter-logging:3.5.7
|    |    |    +--- ch.qos.logback:logback-classic:1.5.20
|    |    |    |    +--- ch.qos.logback:logback-core:1.5.20
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.17
|    |    |    +--- org.apache.logging.log4j:log4j-to-slf4j:2.24.3
|    |    |    |    +--- org.apache.logging.log4j:log4j-api:2.24.3
|    |    |    |    \--- org.slf4j:slf4j-api:2.0.16 -> 2.0.17
|    |    |    \--- org.slf4j:jul-to-slf4j:2.0.17
|    |    |         \--- org.slf4j:slf4j-api:2.0.17
|    |    +--- jakarta.annotation:jakarta.annotation-api:2.1.1
|    |    +--- org.springframework:spring-core:6.2.12 (*)
|    |    \--- org.yaml:snakeyaml:2.4
|    +--- org.springframework.boot:spring-boot-starter-json:3.5.7
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.7 (*)
|    |    +--- org.springframework:spring-web:6.2.12
|    |    |    +--- org.springframework:spring-beans:6.2.12 (*)
|    |    |    +--- org.springframework:spring-core:6.2.12 (*)
|    |    |    \--- io.micrometer:micrometer-observation:1.14.12 -> 1.15.5 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2
|    |    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.19.2 (c)
|    |    |    |         +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2 (c)
|    |    |    |         \--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.19.2 (c)
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2
|    |    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2
|    |    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |    |    +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |    |    \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    |    \--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.19.2
|    |         +--- com.fasterxml.jackson.core:jackson-core:2.19.2 (*)
|    |         +--- com.fasterxml.jackson.core:jackson-databind:2.19.2 (*)
|    |         \--- com.fasterxml.jackson:jackson-bom:2.19.2 (*)
|    +--- org.springframework.boot:spring-boot-starter-tomcat:3.5.7
|    |    +--- jakarta.annotation:jakarta.annotation-api:2.1.1
|    |    +--- org.apache.tomcat.embed:tomcat-embed-core:10.1.48
|    |    +--- org.apache.tomcat.embed:tomcat-embed-el:10.1.48
|    |    \--- org.apache.tomcat.embed:tomcat-embed-websocket:10.1.48
|    |         \--- org.apache.tomcat.embed:tomcat-embed-core:10.1.48
|    +--- org.springframework:spring-web:6.2.12 (*)
|    \--- org.springframework:spring-webmvc:6.2.12
|         +--- org.springframework:spring-aop:6.2.12 (*)
|         +--- org.springframework:spring-beans:6.2.12 (*)
|         +--- org.springframework:spring-context:6.2.12 (*)
|         +--- org.springframework:spring-core:6.2.12 (*)
|         +--- org.springframework:spring-expression:6.2.12 (*)
|         \--- org.springframework:spring-web:6.2.12 (*)
+--- org.springframework.boot:spring-boot-starter-data-jpa -> 3.5.7
|    +--- org.springframework.boot:spring-boot-starter:3.5.7 (*)
|    +--- org.springframework.boot:spring-boot-starter-jdbc:3.5.7
|    |    +--- org.springframework.boot:spring-boot-starter:3.5.7 (*)
|    |    +--- com.zaxxer:HikariCP:6.3.3
|    |    |    \--- org.slf4j:slf4j-api:2.0.17
|    |    \--- org.springframework:spring-jdbc:6.2.12
|    |         +--- org.springframework:spring-beans:6.2.12 (*)
|    |         +--- org.springframework:spring-core:6.2.12 (*)
|    |         \--- org.springframework:spring-tx:6.2.12
|    |              +--- org.springframework:spring-beans:6.2.12 (*)
|    |              \--- org.springframework:spring-core:6.2.12 (*)
|    +--- org.hibernate.orm:hibernate-core:6.6.33.Final
|    |    +--- jakarta.persistence:jakarta.persistence-api:3.1.0
|    |    +--- jakarta.transaction:jakarta.transaction-api:2.0.1
|    |    +--- org.jboss.logging:jboss-logging:3.5.0.Final -> 3.6.1.Final
|    |    +--- org.hibernate.common:hibernate-commons-annotations:7.0.3.Final
|    |    +--- io.smallrye:jandex:3.2.0
|    |    +--- com.fasterxml:classmate:1.5.1 -> 1.7.1
|    |    +--- net.bytebuddy:byte-buddy:1.15.11 -> 1.17.8
|    |    +--- jakarta.xml.bind:jakarta.xml.bind-api:4.0.0 -> 4.0.4
|    |    |    \--- jakarta.activation:jakarta.activation-api:2.1.4
|    |    +--- org.glassfish.jaxb:jaxb-runtime:4.0.2 -> 4.0.6
|    |    |    \--- org.glassfish.jaxb:jaxb-core:4.0.6
|    |    |         +--- jakarta.xml.bind:jakarta.xml.bind-api:4.0.4 (*)
|    |    |         +--- jakarta.activation:jakarta.activation-api:2.1.4
|    |    |         +--- org.eclipse.angus:angus-activation:2.0.3
|    |    |         |    \--- jakarta.activation:jakarta.activation-api:2.1.4
|    |    |         +--- org.glassfish.jaxb:txw2:4.0.6
|    |    |         \--- com.sun.istack:istack-commons-runtime:4.1.2
|    |    +--- jakarta.inject:jakarta.inject-api:2.0.1
|    |    \--- org.antlr:antlr4-runtime:4.13.0
|    +--- org.springframework.data:spring-data-jpa:3.5.5
|    |    +--- org.springframework.data:spring-data-commons:3.5.5
|    |    |    +--- org.springframework:spring-core:6.2.12 (*)
|    |    |    +--- org.springframework:spring-beans:6.2.12 (*)
|    |    |    \--- org.slf4j:slf4j-api:2.0.2 -> 2.0.17
|    |    +--- org.springframework:spring-orm:6.2.12
|    |    |    +--- org.springframework:spring-beans:6.2.12 (*)
|    |    |    +--- org.springframework:spring-core:6.2.12 (*)
|    |    |    +--- org.springframework:spring-jdbc:6.2.12 (*)
|    |    |    \--- org.springframework:spring-tx:6.2.12 (*)
|    |    +--- org.springframework:spring-context:6.2.12 (*)
|    |    +--- org.springframework:spring-aop:6.2.12 (*)
|    |    +--- org.springframework:spring-tx:6.2.12 (*)
|    |    +--- org.springframework:spring-beans:6.2.12 (*)
|    |    +--- org.springframework:spring-core:6.2.12 (*)
|    |    +--- org.antlr:antlr4-runtime:4.13.0
|    |    +--- jakarta.annotation:jakarta.annotation-api:2.0.0 -> 2.1.1
|    |    \--- org.slf4j:slf4j-api:2.0.2 -> 2.0.17
|    \--- org.springframework:spring-aspects:6.2.12
|         \--- org.aspectj:aspectjweaver:1.9.22.1 -> 1.9.24
+--- org.springframework.boot:spring-boot-starter-validation -> 3.5.7
|    +--- org.springframework.boot:spring-boot-starter:3.5.7 (*)
|    +--- org.apache.tomcat.embed:tomcat-embed-el:10.1.48
|    \--- org.hibernate.validator:hibernate-validator:8.0.3.Final
|         +--- jakarta.validation:jakarta.validation-api:3.0.2
|         +--- org.jboss.logging:jboss-logging:3.4.3.Final -> 3.6.1.Final
|         \--- com.fasterxml:classmate:1.5.1 -> 1.7.1
+--- com.opencsv:opencsv:5.12.0
|    +--- org.apache.commons:commons-lang3:3.18.0 -> 3.17.0
|    +--- org.apache.commons:commons-text:1.13.1
|    |    \--- org.apache.commons:commons-lang3:3.17.0
|    +--- commons-beanutils:commons-beanutils:1.11.0
|    |    +--- commons-logging:commons-logging:1.3.5
|    |    \--- commons-collections:commons-collections:3.2.2
|    \--- org.apache.commons:commons-collections4:4.5.0
\--- com.h2database:h2 -> 2.3.232
```

`grep -rE "org.joda|org.apache.log4j|au.com.bytecode" src` returns no results

### joda-time

`./gradlew dependencyInsight --dependency joda-time --configuration runtimeClasspath`

```
> Task :dependencyInsight
No dependencies matching given input were found in configuration ':runtimeClasspath'
```

### log4j

`./gradlew dependencyInsight --dependency log4j:log4j --configuration runtimeClasspath`    

```
> Task :dependencyInsight
org.apache.logging.log4j:log4j-api:2.24.3 (selected by rule)
  Variant runtime:
    | Attribute Name                 | Provided     | Requested    |
    |--------------------------------|--------------|--------------|
    | org.gradle.status              | release      |              |
    | org.gradle.category            | library      | library      |
    | org.gradle.libraryelements     | jar          | jar          |
    | org.gradle.usage               | java-runtime | java-runtime |
    | org.gradle.dependency.bundling |              | external     |
    | org.gradle.jvm.environment     |              | standard-jvm |
    | org.gradle.jvm.version         |              | 17           |

org.apache.logging.log4j:log4j-api:2.24.3
\--- org.apache.logging.log4j:log4j-to-slf4j:2.24.3
     \--- org.springframework.boot:spring-boot-starter-logging:3.5.7
          \--- org.springframework.boot:spring-boot-starter:3.5.7
               +--- org.springframework.boot:spring-boot-starter-web:3.5.7
               |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-web)
               +--- org.springframework.boot:spring-boot-starter-data-jpa:3.5.7
               |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-data-jpa)
               +--- org.springframework.boot:spring-boot-starter-validation:3.5.7
               |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-validation)
               +--- org.springframework.boot:spring-boot-starter-json:3.5.7
               |    \--- org.springframework.boot:spring-boot-starter-web:3.5.7 (*)
               \--- org.springframework.boot:spring-boot-starter-jdbc:3.5.7
                    \--- org.springframework.boot:spring-boot-starter-data-jpa:3.5.7 (*)

org.apache.logging.log4j:log4j-to-slf4j:2.24.3 (selected by rule)
  Variant runtime:
    | Attribute Name                 | Provided     | Requested    |
    |--------------------------------|--------------|--------------|
    | org.gradle.status              | release      |              |
    | org.gradle.category            | library      | library      |
    | org.gradle.libraryelements     | jar          | jar          |
    | org.gradle.usage               | java-runtime | java-runtime |
    | org.gradle.dependency.bundling |              | external     |
    | org.gradle.jvm.environment     |              | standard-jvm |
    | org.gradle.jvm.version         |              | 17           |

org.apache.logging.log4j:log4j-to-slf4j:2.24.3
\--- org.springframework.boot:spring-boot-starter-logging:3.5.7
     \--- org.springframework.boot:spring-boot-starter:3.5.7
          +--- org.springframework.boot:spring-boot-starter-web:3.5.7
          |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-web)
          +--- org.springframework.boot:spring-boot-starter-data-jpa:3.5.7
          |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-data-jpa)
          +--- org.springframework.boot:spring-boot-starter-validation:3.5.7
          |    \--- runtimeClasspath (requested org.springframework.boot:spring-boot-starter-validation)
          +--- org.springframework.boot:spring-boot-starter-json:3.5.7
          |    \--- org.springframework.boot:spring-boot-starter-web:3.5.7 (*)
          \--- org.springframework.boot:spring-boot-starter-jdbc:3.5.7
               \--- org.springframework.boot:spring-boot-starter-data-jpa:3.5.7 (*)
```

`./gradlew dependencyInsight --dependency log4j:log4j --configuration runtimeClasspath | grep -i "1.2.17"` returns no results  

### net.sf.opencsv

`./gradlew dependencyInsight --dependency net.sf.opencsv --configuration runtimeClasspath`

```
> Task :dependencyInsight
No dependencies matching given input were found in configuration ':runtimeClasspath'
```

## S1: Compare CSV Library Code Changes

### Original (OpenCSV 2.3)

```java
import au.com.bytecode.opencsv.CSVWriter;

@Component
public class EmployeeCsvExporter {

    public String export(List<Employee> employees) {
        StringWriter out = new StringWriter();
        CSVWriter writer = new CSVWriter(out);
        try {
            writer.writeNext(new String[] {"id", "name", "role", "onboardingDate"});
            for (Employee e : employees) {
                writer.writeNext(new String[] {
                        String.valueOf(e.getId()), e.getName(), e.getRole(), String.valueOf(e.getOnboardingDate())
                });
            }
            writer.close();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not write CSV", ex);
        }
        return out.toString();
    }
}
```

### OpenCSV 5.12

```java
import com.opencsv.CSVWriter;

@Component 
EmployeeCsvExporter {
    // no class changes required
}
```

### Apache Commons CSV 1.14.1

```java
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

@Component
public class EmployeeCsvExporter {
    public String export(List<Employee> employees) {
        StringWriter out = new StringWriter();
        CSVFormat format = CSVFormat.DEFAULT.builder()
            .setQuoteMode(QuoteMode.ALL)
            .setRecordSeparator("\n")
            .get();

        try (CSVPrinter printer = new CSVPrinter(out, format)){
            printer.printRecord("id", "name", "role", "onboardingDate");
            for (Employee e : employees) {
                printer.printRecord(
                        String.valueOf(e.getId()),
                        e.getName(),
                        e.getRole(),
                        String.valueOf(e.getOnboardingDate())
                );
            }
            printer.close();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not write CSV", ex);
        }
        return out.toString();
    }
}
```

Apache Commons CSV has many more imports and defaults to configure to match the old output, however its verbosity is also transparency in its behaviour which can be a positive trade off for how Java is designed.

## S2: Old Libraries - Implicitly Imported

` ./gradlew dependencies --configuration runtimeClasspath | grep -iE "log4j|joda-time|opencsv"`

```
|    |    |    +--- org.apache.logging.log4j:log4j-to-slf4j:2.24.3
|    |    |    |    +--- org.apache.logging.log4j:log4j-api:2.24.3
+--- com.opencsv:opencsv:5.12.0
```

From the above dependency graph for Core Task 6, we can see that `org.springframework.boot:spring-boot-starter-logging` brings it in as a part of `org.springframework.boot:spring-boot-starter-web`, with it being used to convert from log4j to SLF4J by `org.apache.logging.log4j:log4j-to-slf4j`

## S3: Dependency Scanning (Trivy)

Installed via `brew install trivy`

### Against finished remediation project

`trivy fs ~/Documents/citi-java-workshop/javaworkshops/exercise-4/ReplaceUnsupportedthirdpartylibrary --list-all-pkgs`

```        
Report Summary

┌─────────────────┬────────┬─────────────────┬─────────┐
│     Target      │  Type  │ Vulnerabilities │ Secrets │
├─────────────────┼────────┼─────────────────┼─────────┤
│ gradle.lockfile │ gradle │       64        │    -    │
└─────────────────┴────────┴─────────────────┴─────────┘
Legend:
- '-': Not scanned
- '0': Clean (no security findings detected)


gradle.lockfile (gradle)

Total: 64 (UNKNOWN: 0, LOW: 8, MEDIUM: 25, HIGH: 22, CRITICAL: 9)

┌────────────────────────────────────────────────────┬────────────────┬──────────┬────────┬───────────────────┬───────────────────────────────┬──────────────────────────────────────────────────────────────┐
│                      Library                       │ Vulnerability  │ Severity │ Status │ Installed Version │     Fixed Version         │                            Title                             │
├────────────────────────────────────────────────────┼────────────────┼──────────┼────────┼───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ ch.qos.logback:logback-core                        │ CVE-2026-10532 │ LOW      │ fixed  │ 1.5.20            │ 1.5.34                        │ Deserialization of untrusted data vulnerability in QOS.CH    │
│                                                    │                │          │        │                   │                           │ Sarl logback ...                                             │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-10532                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-1225  │          │        │                   │ 1.5.25                        │ ch.qos.logback/logback-core: Malicious logback.xml           │
│                                                    │                │          │        │                   │                           │ configuration file allows instantiation of arbitrary classes │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-1225                    │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-9828  │          │        │                   │ 1.5.33                        │ Deserialization of untrusted data vulnerability in QOS.CH    │
│                                                    │                │          │        │                   │                           │ Sarl logback ...                                             │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-9828                    │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ com.fasterxml.jackson.core:jackson-core            │ CVE-2026-68494 │ HIGH     │        │ 2.19.2            │ 2.18.8, 2.21.4                │ com.fasterxml.jackson.core/jackson-core:                     │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-core: jackson-core: Denial of     │
│                                                    │                │          │        │                   │                           │ Service via incomplete fix in async JSON...                  │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-68494                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-89407 │          │        │                   │ 2.18.11, 2.21.7, 2.22.3       │ com.fasterxml.jackson/jackson-core:                          │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-core: Jackson-core: Denial of     │
│                                                    │                │          │        │                   │                           │ Service via regular expression backtracking                  │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-89407                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-89425 │          │        │                   │ 2.21.7, 2.22.3, 2.18.11       │ com.fasterxml.jackson.core/jackson-core: Jackson-core:       │
│                                                    │                │          │        │                   │                           │ Denial of Service via unbounded StringBuilder growth during  │
│                                                    │                │          │        │                   │                           │ malformed token...                                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-89425                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-18401 │ MEDIUM   │        │                   │ 2.21.1, 2.18.6                │ tools.jackson.core/jackson-core:                             │
│                                                    │                │          │        │                   │                           │ com.fasterxml.jackson.core/jackson-core: jackson-core:       │
│                                                    │                │          │        │                   │                           │ Denial of Service due to number length bypass in...          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-18401                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ com.fasterxml.jackson.core:jackson-databind        │ CVE-2026-54512 │ HIGH     │        │                   │ 2.18.8, 3.1.4, 2.21.4         │ jackson-databind: jackson-databind: Arbitrary code execution │
│                                                    │                │          │        │                   │                           │ via PolymorphicTypeValidator bypass                          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-54512                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54513 │          │        │                   │ 2.18.8, 2.21.4, 3.1.4         │ jackson-databind: Jackson-databind: Security bypass allows   │
│                                                    │                │          │        │                   │                           │ arbitrary code execution                                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-54513                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-68497 │          │        │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-databind: jackson-databind: CPU   │
│                                                    │                │          │        │                   │                           │ Denial of Service via unbounded numeric parsing              │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-68497                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-91776 │          │        │                   │ 2.18.11, 2.21.7, 2.22.3       │ jackson-databind: com.fasterxml.jackson/jackson-core:        │
│                                                    │                │          │        │                   │                           │ jackson-databind: Denial of Service via unbounded cache      │
│                                                    │                │          │        │                   │                           │ growth in TypeDeserializerBase...                            │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-91776                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-91777 │          │        │                   │ 2.21.7, 2.18.11, 2.22.3       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ tools.jackson.core:jackson-databind: Jackson-databind:       │
│                                                    │                │          │        │                   │                           │ Denial of Service via quadratic forward-reference completion │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-91777                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-19032 │ MEDIUM   │        │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-databind: Jackson-databind:       │
│                                                    │                │          │        │                   │                           │ Uncontrolled URI scheme resolution in Path deserialization   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-19032                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54514 │          │        │                   │ 2.18.8, 2.21.4                │ jackson-databind: jackson-databind: Information Disclosure   │
│                                                    │                │          │        │                   │                           │ via Eager DNS Resolution                                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-54514                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54515 │          │        │                   │ 3.1.4, 2.18.9, 2.21.5, 2.22.1 │ jackson-databind: jackson-databind: Ignored properties can   │
│                                                    │                │          │        │                   │                           │ be unexpectedly modified                                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-54515                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-59888 │          │        │                   │ 2.18.8, 2.21.4                │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-databind: jackson-databind:       │
│                                                    │                │          │        │                   │                           │ @JsonIgnore bypass in Java Records                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-59888                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-59889 │          │        │                   │ 2.21.5, 2.18.9, 2.22.1        │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ Jackson-databind: Privilege escalation via improper handling │
│                                                    │                │          │        │                   │                           │ of @JsonUnwrapped properties                                 │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-59889                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-77310 │          │        │                   │ 2.18.9, 2.21.5, 2.22.1        │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ jackson-databind: Server-Side Request Forgery and internal   │
│                                                    │                │          │        │                   │                           │ host enumeration via eager DNS...                            │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-77310                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-83557 │          │        │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │        │                   │                           │ tools.jackson.core/jackson-databind: jackson-databind: Path  │
│                                                    │                │          │        │                   │                           │ traversal via incomplete type validation                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-83557                   │
├────────────────────────────────────────────────────┼────────────────┤          │        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.apache.commons:commons-lang3                   │ CVE-2025-48924 │          │        │ 3.17.0            │ 3.18.0                        │ commons-lang/commons-lang: org.apache.commons/commons-lang3: │
│                                                    │                │          │        │                   │                           │ Uncontrolled Recursion vulnerability in Apache Commons Lang  │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2025-48924                   │
├────────────────────────────────────────────────────┼────────────────┤          │        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.apache.logging.log4j:log4j-api                 │ CVE-2026-49844 │          │        │ 2.24.3            │ 2.25.5, 2.26.1                │ org.apache.logging.log4j/log4j-api: Apache Log4j API:        │
│                                                    │                │          │        │                   │                           │ Malformed JSON output due to improper encoding of...         │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-49844                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.apache.tomcat.embed:tomcat-embed-core          │ CVE-2026-41293 │ CRITICAL │        │ 10.1.48           │ 9.0.118, 10.1.55, 11.0.22     │ tomcat-coyote: Apache Tomcat: HTTP/2 request headers not     │
│                                                    │                │          │        │                   │                           │ validated                                                    │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41293                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43512 │          │        │                   │                           │ tomcat-coyote: Apache Tomcat: Authentication bypass via      │
│                                                    │                │          │        │                   │                           │ digest authentication                                        │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-43512                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43515 │          │        │                   │                           │ tomcat-coyote: tomcat: Improper Authorization allows         │
│                                                    │                │          │        │                   │                           │ security bypass                                              │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-43515                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-55276 │          │        │                   │ 11.0.23, 10.1.56, 9.0.119     │ tomcat: Apache Tomcat: Misleading security logs due to       │
│                                                    │                │          │        │                   │                           │ incorrect control flow                                       │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-55276                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-65182 │          │        │                   │ 11.0.25, 10.1.58, 9.0.121     │ tomcat: Apache Tomcat: Security constraint bypass due to     │
│                                                    │                │          │        │                   │                           │ improper access control                                      │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-65182                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-65905 │          │        │                   │                           │ tomcat: Apache Tomcat: Authentication bypass via limited     │
│                                                    │                │          │        │                   │                           │ replay attack in DIGEST authenticator...                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-65905                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-68525 │          │        │                   │                           │ tomcat: Apache Tomcat: Unauthorized resource access via FORM │
│                                                    │                │          │        │                   │                           │ authentication bypass                                        │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-68525                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-24734 │ HIGH     │        │                   │ 11.0.18, 10.1.52, 9.0.115     │ tomcat: Apache Tomcat: Certificate revocation bypass due to  │
│                                                    │                │          │        │                   │                           │ improper OCSP response validation...                         │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-24734                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-24880 │          │        │                   │ 9.0.116, 10.1.52, 11.0.20     │ Apache Tomcat: Apache Tomcat: HTTP Request/Response          │
│                                                    │                │          │        │                   │                           │ Smuggling via invalid chunk extension                        │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-24880                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-34483 │          │        │                   │ 9.0.116, 10.1.54, 11.0.21     │ Apache Tomcat: Apache Tomcat: Information disclosure due to  │
│                                                    │                │          │        │                   │                           │ improper encoding in JsonAccessLogValve...                   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-34483                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-34487 │          │        │                   │ 9.0.117, 10.1.54, 11.0.21     │ Apache Tomcat: Apache Tomcat: Information disclosure via     │
│                                                    │                │          │        │                   │                           │ sensitive data in log files...                               │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-34487                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41284 │          │        │                   │ 9.0.118, 10.1.55, 11.0.22     │ tomcat: Apache Tomcat: Denial of Service due to uncontrolled │
│                                                    │                │          │        │                   │                           │ resource allocation                                          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41284                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-42498 │          │        │                   │                           │ tomcat-coyote: Apache Tomcat: Information disclosure due to  │
│                                                    │                │          │        │                   │                           │ HTTP Authentication Header exposure during...                │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-42498                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43513 │          │        │                   │                           │ tomcat-catalina: Apache Tomcat: Improper Handling of Case    │
│                                                    │                │          │        │                   │                           │ Sensitivity in LockOutRealm                                  │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-43513                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-53404 │          │        │                   │ 11.0.23, 10.1.56, 9.0.119     │ Apache Tomcat: Apache Tomcat: Incorrect control flow in      │
│                                                    │                │          │        │                   │                           │ rewrite valve allows unexpected...                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-53404                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2025-66614 │ MEDIUM   │        │                   │ 11.0.15, 10.1.50, 9.0.113     │ tomcat: Client certificate verification bypass due to        │
│                                                    │                │          │        │                   │                           │ virtual host mapping                                         │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2025-66614                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-25854 │          │        │                   │ 9.0.116, 10.1.53, 11.0.20     │ Apache Tomcat: Apache Tomcat: Open Redirect vulnerability    │
│                                                    │                │          │        │                   │                           │ via LoadBalancerDrainingValve                                │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-25854                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-55956 │          │        │                   │ 11.0.23, 10.1.56, 9.0.119     │ tomcat: Apache Tomcat: Improper Authorization Allows         │
│                                                    │                │          │        │                   │                           │ Security Constraint Bypass                                   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-55956                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43514 │ LOW      │        │                   │ 9.0.118, 10.1.55, 11.0.22     │ tomcat: Apache Tomcat: Information disclosure via AJP secret │
│                                                    │                │          │        │                   │                           │ timing discrepancy                                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-43514                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.boot:spring-boot               │ CVE-2026-40973 │ HIGH     │        │ 3.5.7             │ 4.0.6, 3.5.14                 │ Spring Boot: Spring Boot: Arbitrary Code Execution and       │
│                                                    │                │          │        │                   │                           │ Session Hijacking via predictable...                         │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-40973                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.boot:spring-boot-autoconfigure │ CVE-2026-41001 │ MEDIUM   │        │                   │ 4.0.7, 3.5.15                 │ spring-boot: Spring Boot: Local attacker can manipulate data │
│                                                    │                │          │        │                   │                           │ directory due to predictable...                              │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41001                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.data:spring-data-commons       │ CVE-2026-41695 │ HIGH     │        │ 3.5.5             │ 4.0.6, 3.5.12                 │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │        │                   │                           │ via crafted property...                                      │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41695                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41716 │          │        │                   │                           │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │        │                   │                           │ due to cache...                                              │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41716                   │
│                                                    ├────────────────┼──────────┤        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41711 │ MEDIUM   │        │                   │                           │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │        │                   │                           │ via parsing Sort...                                          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41711                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41721 │          │        │                   │                           │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │        │                   │                           │ via specially crafted...                                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41721                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-core                    │ CVE-2026-41848 │ LOW      │        │ 6.2.12            │ 7.0.8, 6.2.19                 │ spring-framework: Spring Framework: Regular Expression       │
│                                                    │                │          │        │                   │                           │ Denial of Service in AntPathMatcher                          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41848                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        │                   │                           ├──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-expression              │ CVE-2026-41850 │ HIGH     │        │                   │                           │ spring-framework: Spring Framework: Denial of Service via    │
│                                                    │                │          │        │                   │                           │ specially crafted SpEL expressions                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41850                   │
│                                                    ├────────────────┼──────────┤        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41851 │ MEDIUM   │        │                   │                           │ Spring Framework: Spring Framework: Denial of Service via    │
│                                                    │                │          │        │                   │                           │ unbounded cache growth in...                                 │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41851                   │
│                                                    ├────────────────┼──────────┤        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41852 │ LOW      │        │                   │                           │ spring-framework: org.springframework/spring-expression:     │
│                                                    │                │          │        │                   │                           │ Spring Framework: SpEL vulnerability allows unintended       │
│                                                    │                │          │        │                   │                           │ application logic invocation                                 │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41852                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        │                   │                           ├──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-web                     │ CVE-2026-41854 │ MEDIUM   │        │                   │                           │ spring-framework: Spring Framework: Server-Side Request      │
│                                                    │                │          │        │                   │                           │ Forgery via incorrect host parsing                           │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41854                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-webmvc                  │ CVE-2026-47884 │ CRITICAL │        │                   │ 7.0.9                         │ org.springframework/spring-webmvc: Spring Framework: Remote  │
│                                                    │                │          │        │                   │                           │ Code Execution via improper path limitation in XsltView...   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-47884                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-47890 │          │        │                   │                           │ org.springframework/spring-webmvc:                           │
│                                                    │                │          │        │                   │                           │ org.springframework/spring-webflux: Spring Framework: Stream │
│                                                    │                │          │        │                   │                           │ corruption in Server-Sent Events (SSE) with view...          │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-47890                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41842 │ HIGH     │        │                   │ 7.0.8, 6.2.19                 │ spring-framework: Spring Framework: Denial of Service when   │
│                                                    │                │          │        │                   │                           │ resolving static resources                                   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41842                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41845 │          │        │                   │                           │ org.springframework: Spring Framework: Cross-site scripting  │
│                                                    │                │          │        │                   │                           │ (XSS) via incorrect JavaScript escaping                      │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41845                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22737 │ MEDIUM   │        │                   │ 7.0.6, 6.2.17                 │ Spring Framework: Spring Framework: Information disclosure   │
│                                                    │                │          │        │                   │                           │ via Java scripting engine enabled template...                │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-22737                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22745 │          │        │                   │ 7.0.7, 6.2.18                 │ spring-webflux: Spring MVC and Spring WebFlux: Denial of     │
│                                                    │                │          │        │                   │                           │ Service via slow static...                                   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-22745                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41841 │          │        │                   │ 7.0.8, 6.2.19                 │ Spring Framework: Spring MVC and WebFlux applications:       │
│                                                    │                │          │        │                   │                           │ Information Disclosure via static resource...                │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41841                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41843 │          │        │                   │                           │ spring-webflux: spring-webmvc: Spring Framework: Information │
│                                                    │                │          │        │                   │                           │ Disclosure via Path Traversal                                │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41843                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41844 │          │        │                   │                           │ Spring Framework: Spring Framework: Open Redirect via        │
│                                                    │                │          │        │                   │                           │ crafted link                                                 │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41844                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41846 │          │        │                   │                           │ Spring Framework: Spring Framework: Cross-site scripting     │
│                                                    │                │          │        │                   │                           │ (XSS) via user-supplied values in JSP...                     │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41846                   │
│                                                    ├────────────────┤          │        │                   │                           ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41853 │          │        │                   │                           │ Spring Framework: Spring Framework: Request smuggling        │
│                                                    │                │          │        │                   │                           │ vulnerability in Spring MVC and WebFlux...                   │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-41853                   │
│                                                    ├────────────────┼──────────┤        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22735 │ LOW      │        │                   │ 7.0.6, 6.2.17                 │ org.springframework/spring-webmvc:                           │
│                                                    │                │          │        │                   │                           │ org.springframework/spring-webflux: Spring MVC and WebFlux:  │
│                                                    │                │          │        │                   │                           │ Stream corruption vulnerability when using Server-Sent...    │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-22735                   │
│                                                    ├────────────────┤          │        │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22741 │          │        │                   │ 7.0.7, 6.2.18                 │ Spring MVC: Spring WebFlux: Spring MVC and Spring WebFlux:   │
│                                                    │                │          │        │                   │                           │ Denial of Service...                                         │
│                                                    │                │          │        │                   │                           │ https://avd.aquasec.com/nvd/cve-2026-22741                   │
└────────────────────────────────────────────────────┴────────────────┴──────────┴────────┴───────────────────┴───────────────────────────────┴──────────────────────────────────────────────────────────────┘
```

### Against original source project pre-remediation

Created a clone in a seperate adjacent directory of the original source:

`trivy fs ~/Documents/citi-java-workshop/javaworkshops/exercise-4.1/ReplaceUnsupportedthirdpartylibrary --list-all-pkgs`

```
Report Summary

┌─────────────────┬────────┬─────────────────┬─────────┐
│     Target      │  Type  │ Vulnerabilities │ Secrets │
├─────────────────┼────────┼─────────────────┼─────────┤
│ gradle.lockfile │ gradle │       69        │    -    │
└─────────────────┴────────┴─────────────────┴─────────┘
Legend:
- '-': Not scanned
- '0': Clean (no security findings detected)


gradle.lockfile (gradle)

Total: 69 (UNKNOWN: 0, LOW: 8, MEDIUM: 24, HIGH: 25, CRITICAL: 12)

┌────────────────────────────────────────────────────┬────────────────┬──────────┬──────────┬───────────────────┬───────────────────────────────┬──────────────────────────────────────────────────────────────┐
│                      Library                       │ Vulnerability  │ Severity │  Status  │ Installed Version │         Fixed Version         │                            Title                             │
├────────────────────────────────────────────────────┼────────────────┼──────────┼──────────┼───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ ch.qos.logback:logback-core                        │ CVE-2026-10532 │ LOW      │ fixed    │ 1.5.20            │ 1.5.34                        │ Deserialization of untrusted data vulnerability in QOS.CH    │
│                                                    │                │          │          │                   │                               │ Sarl logback ...                                             │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-10532                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-1225  │          │          │                   │ 1.5.25                        │ ch.qos.logback/logback-core: Malicious logback.xml           │
│                                                    │                │          │          │                   │                               │ configuration file allows instantiation of arbitrary classes │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-1225                    │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-9828  │          │          │                   │ 1.5.33                        │ Deserialization of untrusted data vulnerability in QOS.CH    │
│                                                    │                │          │          │                   │                               │ Sarl logback ...                                             │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-9828                    │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ com.fasterxml.jackson.core:jackson-core            │ CVE-2026-68494 │ HIGH     │          │ 2.19.2            │ 2.18.8, 2.21.4                │ com.fasterxml.jackson.core/jackson-core:                     │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-core: jackson-core: Denial of     │
│                                                    │                │          │          │                   │                               │ Service via incomplete fix in async JSON...                  │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-68494                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-89407 │          │          │                   │ 2.18.11, 2.21.7, 2.22.3       │ com.fasterxml.jackson/jackson-core:                          │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-core: Jackson-core: Denial of     │
│                                                    │                │          │          │                   │                               │ Service via regular expression backtracking                  │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-89407                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-89425 │          │          │                   │ 2.21.7, 2.22.3, 2.18.11       │ com.fasterxml.jackson.core/jackson-core: Jackson-core:       │
│                                                    │                │          │          │                   │                               │ Denial of Service via unbounded StringBuilder growth during  │
│                                                    │                │          │          │                   │                               │ malformed token...                                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-89425                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-18401 │ MEDIUM   │          │                   │ 2.21.1, 2.18.6                │ tools.jackson.core/jackson-core:                             │
│                                                    │                │          │          │                   │                               │ com.fasterxml.jackson.core/jackson-core: jackson-core:       │
│                                                    │                │          │          │                   │                               │ Denial of Service due to number length bypass in...          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-18401                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ com.fasterxml.jackson.core:jackson-databind        │ CVE-2026-54512 │ HIGH     │          │                   │ 2.18.8, 3.1.4, 2.21.4         │ jackson-databind: jackson-databind: Arbitrary code execution │
│                                                    │                │          │          │                   │                               │ via PolymorphicTypeValidator bypass                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-54512                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54513 │          │          │                   │ 2.18.8, 2.21.4, 3.1.4         │ jackson-databind: Jackson-databind: Security bypass allows   │
│                                                    │                │          │          │                   │                               │ arbitrary code execution                                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-54513                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-68497 │          │          │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-databind: jackson-databind: CPU   │
│                                                    │                │          │          │                   │                               │ Denial of Service via unbounded numeric parsing              │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-68497                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-91776 │          │          │                   │ 2.18.11, 2.21.7, 2.22.3       │ jackson-databind: com.fasterxml.jackson/jackson-core:        │
│                                                    │                │          │          │                   │                               │ jackson-databind: Denial of Service via unbounded cache      │
│                                                    │                │          │          │                   │                               │ growth in TypeDeserializerBase...                            │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-91776                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-91777 │          │          │                   │ 2.21.7, 2.18.11, 2.22.3       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ tools.jackson.core:jackson-databind: Jackson-databind:       │
│                                                    │                │          │          │                   │                               │ Denial of Service via quadratic forward-reference completion │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-91777                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-19032 │ MEDIUM   │          │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-databind: Jackson-databind:       │
│                                                    │                │          │          │                   │                               │ Uncontrolled URI scheme resolution in Path deserialization   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-19032                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54514 │          │          │                   │ 2.18.8, 2.21.4                │ jackson-databind: jackson-databind: Information Disclosure   │
│                                                    │                │          │          │                   │                               │ via Eager DNS Resolution                                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-54514                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-54515 │          │          │                   │ 3.1.4, 2.18.9, 2.21.5, 2.22.1 │ jackson-databind: jackson-databind: Ignored properties can   │
│                                                    │                │          │          │                   │                               │ be unexpectedly modified                                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-54515                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-59888 │          │          │                   │ 2.18.8, 2.21.4                │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-databind: jackson-databind:       │
│                                                    │                │          │          │                   │                               │ @JsonIgnore bypass in Java Records                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-59888                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-59889 │          │          │                   │ 2.21.5, 2.18.9, 2.22.1        │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ Jackson-databind: Privilege escalation via improper handling │
│                                                    │                │          │          │                   │                               │ of @JsonUnwrapped properties                                 │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-59889                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-77310 │          │          │                   │ 2.18.9, 2.21.5, 2.22.1        │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ jackson-databind: Server-Side Request Forgery and internal   │
│                                                    │                │          │          │                   │                               │ host enumeration via eager DNS...                            │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-77310                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-83557 │          │          │                   │ 2.18.10, 2.21.6, 2.22.2       │ com.fasterxml.jackson.core/jackson-databind:                 │
│                                                    │                │          │          │                   │                               │ tools.jackson.core/jackson-databind: jackson-databind: Path  │
│                                                    │                │          │          │                   │                               │ traversal via incomplete type validation                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-83557                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┼──────────┼───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ log4j:log4j                                        │ CVE-2019-17571 │ CRITICAL │ affected │ 1.2.17            │                               │ log4j: deserialization of untrusted data in SocketServer     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2019-17571                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2022-23305 │          │          │                   │                               │ log4j: SQL injection in Log4j 1.x when application is        │
│                                                    │                │          │          │                   │                               │ configured to use...                                         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2022-23305                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2022-23307 │          │          │                   │                               │ log4j: Unsafe deserialization flaw in Chainsaw log viewer    │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2022-23307                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2021-4104  │ HIGH     │          │                   │                               │ log4j: Remote code execution in Log4j 1.x when application   │
│                                                    │                │          │          │                   │                               │ is configured to...                                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2021-4104                    │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2022-23302 │          │          │                   │                               │ log4j: Remote code execution in Log4j 1.x when application   │
│                                                    │                │          │          │                   │                               │ is configured to...                                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2022-23302                   │
│                                                    ├────────────────┤          ├──────────┤                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2023-26464 │          │ fixed    │                   │ 2.0                           │ log4j1-socketappender: DoS via hashmap logging               │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2023-26464                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.apache.logging.log4j:log4j-api                 │ CVE-2026-49844 │ MEDIUM   │          │ 2.24.3            │ 2.25.5, 2.26.1                │ org.apache.logging.log4j/log4j-api: Apache Log4j API:        │
│                                                    │                │          │          │                   │                               │ Malformed JSON output due to improper encoding of...         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-49844                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.apache.tomcat.embed:tomcat-embed-core          │ CVE-2026-41293 │ CRITICAL │          │ 10.1.48           │ 9.0.118, 10.1.55, 11.0.22     │ tomcat-coyote: Apache Tomcat: HTTP/2 request headers not     │
│                                                    │                │          │          │                   │                               │ validated                                                    │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41293                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43512 │          │          │                   │                               │ tomcat-coyote: Apache Tomcat: Authentication bypass via      │
│                                                    │                │          │          │                   │                               │ digest authentication                                        │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-43512                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43515 │          │          │                   │                               │ tomcat-coyote: tomcat: Improper Authorization allows         │
│                                                    │                │          │          │                   │                               │ security bypass                                              │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-43515                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-55276 │          │          │                   │ 11.0.23, 10.1.56, 9.0.119     │ tomcat: Apache Tomcat: Misleading security logs due to       │
│                                                    │                │          │          │                   │                               │ incorrect control flow                                       │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-55276                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-65182 │          │          │                   │ 11.0.25, 10.1.58, 9.0.121     │ tomcat: Apache Tomcat: Security constraint bypass due to     │
│                                                    │                │          │          │                   │                               │ improper access control                                      │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-65182                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-65905 │          │          │                   │                               │ tomcat: Apache Tomcat: Authentication bypass via limited     │
│                                                    │                │          │          │                   │                               │ replay attack in DIGEST authenticator...                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-65905                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-68525 │          │          │                   │                               │ tomcat: Apache Tomcat: Unauthorized resource access via FORM │
│                                                    │                │          │          │                   │                               │ authentication bypass                                        │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-68525                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-24734 │ HIGH     │          │                   │ 11.0.18, 10.1.52, 9.0.115     │ tomcat: Apache Tomcat: Certificate revocation bypass due to  │
│                                                    │                │          │          │                   │                               │ improper OCSP response validation...                         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-24734                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-24880 │          │          │                   │ 9.0.116, 10.1.52, 11.0.20     │ Apache Tomcat: Apache Tomcat: HTTP Request/Response          │
│                                                    │                │          │          │                   │                               │ Smuggling via invalid chunk extension                        │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-24880                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-34483 │          │          │                   │ 9.0.116, 10.1.54, 11.0.21     │ Apache Tomcat: Apache Tomcat: Information disclosure due to  │
│                                                    │                │          │          │                   │                               │ improper encoding in JsonAccessLogValve...                   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-34483                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-34487 │          │          │                   │ 9.0.117, 10.1.54, 11.0.21     │ Apache Tomcat: Apache Tomcat: Information disclosure via     │
│                                                    │                │          │          │                   │                               │ sensitive data in log files...                               │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-34487                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41284 │          │          │                   │ 9.0.118, 10.1.55, 11.0.22     │ tomcat: Apache Tomcat: Denial of Service due to uncontrolled │
│                                                    │                │          │          │                   │                               │ resource allocation                                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41284                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-42498 │          │          │                   │                               │ tomcat-coyote: Apache Tomcat: Information disclosure due to  │
│                                                    │                │          │          │                   │                               │ HTTP Authentication Header exposure during...                │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-42498                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43513 │          │          │                   │                               │ tomcat-catalina: Apache Tomcat: Improper Handling of Case    │
│                                                    │                │          │          │                   │                               │ Sensitivity in LockOutRealm                                  │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-43513                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-53404 │          │          │                   │ 11.0.23, 10.1.56, 9.0.119     │ Apache Tomcat: Apache Tomcat: Incorrect control flow in      │
│                                                    │                │          │          │                   │                               │ rewrite valve allows unexpected...                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-53404                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2025-66614 │ MEDIUM   │          │                   │ 11.0.15, 10.1.50, 9.0.113     │ tomcat: Client certificate verification bypass due to        │
│                                                    │                │          │          │                   │                               │ virtual host mapping                                         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2025-66614                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-25854 │          │          │                   │ 9.0.116, 10.1.53, 11.0.20     │ Apache Tomcat: Apache Tomcat: Open Redirect vulnerability    │
│                                                    │                │          │          │                   │                               │ via LoadBalancerDrainingValve                                │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-25854                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-55956 │          │          │                   │ 11.0.23, 10.1.56, 9.0.119     │ tomcat: Apache Tomcat: Improper Authorization Allows         │
│                                                    │                │          │          │                   │                               │ Security Constraint Bypass                                   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-55956                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-43514 │ LOW      │          │                   │ 9.0.118, 10.1.55, 11.0.22     │ tomcat: Apache Tomcat: Information disclosure via AJP secret │
│                                                    │                │          │          │                   │                               │ timing discrepancy                                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-43514                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.boot:spring-boot               │ CVE-2026-40973 │ HIGH     │          │ 3.5.7             │ 4.0.6, 3.5.14                 │ Spring Boot: Spring Boot: Arbitrary Code Execution and       │
│                                                    │                │          │          │                   │                               │ Session Hijacking via predictable...                         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-40973                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.boot:spring-boot-autoconfigure │ CVE-2026-41001 │ MEDIUM   │          │                   │ 4.0.7, 3.5.15                 │ spring-boot: Spring Boot: Local attacker can manipulate data │
│                                                    │                │          │          │                   │                               │ directory due to predictable...                              │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41001                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework.data:spring-data-commons       │ CVE-2026-41695 │ HIGH     │          │ 3.5.5             │ 4.0.6, 3.5.12                 │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │          │                   │                               │ via crafted property...                                      │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41695                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41716 │          │          │                   │                               │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │          │                   │                               │ due to cache...                                              │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41716                   │
│                                                    ├────────────────┼──────────┤          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41711 │ MEDIUM   │          │                   │                               │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │          │                   │                               │ via parsing Sort...                                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41711                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41721 │          │          │                   │                               │ Spring Data Commons: Spring Data Commons: Denial of Service  │
│                                                    │                │          │          │                   │                               │ via specially crafted...                                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41721                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          ├───────────────────┼───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-core                    │ CVE-2026-41848 │ LOW      │          │ 6.2.12            │ 7.0.8, 6.2.19                 │ spring-framework: Spring Framework: Regular Expression       │
│                                                    │                │          │          │                   │                               │ Denial of Service in AntPathMatcher                          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41848                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          │                   │                               ├──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-expression              │ CVE-2026-41850 │ HIGH     │          │                   │                               │ spring-framework: Spring Framework: Denial of Service via    │
│                                                    │                │          │          │                   │                               │ specially crafted SpEL expressions                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41850                   │
│                                                    ├────────────────┼──────────┤          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41851 │ MEDIUM   │          │                   │                               │ Spring Framework: Spring Framework: Denial of Service via    │
│                                                    │                │          │          │                   │                               │ unbounded cache growth in...                                 │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41851                   │
│                                                    ├────────────────┼──────────┤          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41852 │ LOW      │          │                   │                               │ spring-framework: org.springframework/spring-expression:     │
│                                                    │                │          │          │                   │                               │ Spring Framework: SpEL vulnerability allows unintended       │
│                                                    │                │          │          │                   │                               │ application logic invocation                                 │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41852                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          │                   │                               ├──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-web                     │ CVE-2026-41854 │ MEDIUM   │          │                   │                               │ spring-framework: Spring Framework: Server-Side Request      │
│                                                    │                │          │          │                   │                               │ Forgery via incorrect host parsing                           │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41854                   │
├────────────────────────────────────────────────────┼────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ org.springframework:spring-webmvc                  │ CVE-2026-47884 │ CRITICAL │          │                   │ 7.0.9                         │ org.springframework/spring-webmvc: Spring Framework: Remote  │
│                                                    │                │          │          │                   │                               │ Code Execution via improper path limitation in XsltView...   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-47884                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-47890 │          │          │                   │                               │ org.springframework/spring-webmvc:                           │
│                                                    │                │          │          │                   │                               │ org.springframework/spring-webflux: Spring Framework: Stream │
│                                                    │                │          │          │                   │                               │ corruption in Server-Sent Events (SSE) with view...          │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-47890                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41842 │ HIGH     │          │                   │ 7.0.8, 6.2.19                 │ spring-framework: Spring Framework: Denial of Service when   │
│                                                    │                │          │          │                   │                               │ resolving static resources                                   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41842                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41845 │          │          │                   │                               │ org.springframework: Spring Framework: Cross-site scripting  │
│                                                    │                │          │          │                   │                               │ (XSS) via incorrect JavaScript escaping                      │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41845                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22737 │ MEDIUM   │          │                   │ 7.0.6, 6.2.17                 │ Spring Framework: Spring Framework: Information disclosure   │
│                                                    │                │          │          │                   │                               │ via Java scripting engine enabled template...                │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-22737                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22745 │          │          │                   │ 7.0.7, 6.2.18                 │ spring-webflux: Spring MVC and Spring WebFlux: Denial of     │
│                                                    │                │          │          │                   │                               │ Service via slow static...                                   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-22745                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41841 │          │          │                   │ 7.0.8, 6.2.19                 │ Spring Framework: Spring MVC and WebFlux applications:       │
│                                                    │                │          │          │                   │                               │ Information Disclosure via static resource...                │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41841                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41843 │          │          │                   │                               │ spring-webflux: spring-webmvc: Spring Framework: Information │
│                                                    │                │          │          │                   │                               │ Disclosure via Path Traversal                                │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41843                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41844 │          │          │                   │                               │ Spring Framework: Spring Framework: Open Redirect via        │
│                                                    │                │          │          │                   │                               │ crafted link                                                 │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41844                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41846 │          │          │                   │                               │ Spring Framework: Spring Framework: Cross-site scripting     │
│                                                    │                │          │          │                   │                               │ (XSS) via user-supplied values in JSP...                     │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41846                   │
│                                                    ├────────────────┤          │          │                   │                               ├──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-41853 │          │          │                   │                               │ Spring Framework: Spring Framework: Request smuggling        │
│                                                    │                │          │          │                   │                               │ vulnerability in Spring MVC and WebFlux...                   │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-41853                   │
│                                                    ├────────────────┼──────────┤          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22735 │ LOW      │          │                   │ 7.0.6, 6.2.17                 │ org.springframework/spring-webmvc:                           │
│                                                    │                │          │          │                   │                               │ org.springframework/spring-webflux: Spring MVC and WebFlux:  │
│                                                    │                │          │          │                   │                               │ Stream corruption vulnerability when using Server-Sent...    │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-22735                   │
│                                                    ├────────────────┤          │          │                   ├───────────────────────────────┼──────────────────────────────────────────────────────────────┤
│                                                    │ CVE-2026-22741 │          │          │                   │ 7.0.7, 6.2.18                 │ Spring MVC: Spring WebFlux: Spring MVC and Spring WebFlux:   │
│                                                    │                │          │          │                   │                               │ Denial of Service...                                         │
│                                                    │                │          │          │                   │                               │ https://avd.aquasec.com/nvd/cve-2026-22741                   │
└────────────────────────────────────────────────────┴────────────────┴──────────┴──────────┴───────────────────┴───────────────────────────────┴──────────────────────────────────────────────────────────────┘
```

### Comparison

5 more vulnerabilities, 3 of them critical in nature, can be located in the original source

## S4: Add a Test - Double Quote in Name

Created the following user and added it to the DB with Bruno:

```json
{
  "name": "Aoife O\"Neil",
  "role": "TPM",
  "onboardingDate": "2026-10-05"
}
```

Result from `GET http://localhost:8080/api/employees/export`:

"id","name","role","onboardingDate"
"1","Aoife O""Neil","TPM","2026-10-05"

CSV plaintest includes `""` for where the double quote is. To escape a double quote in a CSV field value, you provided a second as the escape delimiter

```csv
"id","name","role","onboardingDate"
"1","Aoife O""Neil","TPM","2026-10-05"
```

Added 2 tests to `src/test/java/com/example/onboarding/EmployeeApiTests.java`:

```java
@Test 
void creatingAnEmployeeWithDoubleQuotesInNameIsLogged(CapturedOutput output) throws Exception {
    String id = create("Aoife O\\\"Neil", "TPM", "2026-10-05");
    assertThat(output.getAll()).contains("Employee created: id=" + id);
}

@Test 
void exportsCsvWithDoubleQuotesInName(CapturedOutput output) throws Exception {
    create("Aoife O\\\"Neil", "TPM", "2026-10-05");
    String csv = mvc.perform(get("/api/employees/export"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith("text/csv"))
            .andReturn().getResponse().getContentAsString();
    assertThat(csv).matches("(?s)\"id\",\"name\",\"role\",\"onboardingDate\"\n"
            + "\"\\d+\",\"Aoife O\"\"Neil\",\"TPM\",\"2026-10-05\"\n");
}
```

Both tests pass and match this expected behaviour of rendering a double quote with escaping it

## S5: Library replacement checklist

1. Validate the status of your libraries by:
  a. Looking up on your libraries through the Maven and Gradle repositories and seeing if any vulnarabilites are noted
  b. Using a dependency checking tool such as OWASP Dependency-Check or Trivy to validate against CVE databases what needs replacing
2. See if any libraries need replacing rather then upgrading, or are hosted differently on the repositories, and note those down
3. Ensure you have adequate testing in place to check the functionality pre-remediation and post-remediation
4. One by one, for each library that needs replacing, do the following:
  a. Update its dependency in `build.gradle` or `pom.xml` to the new version or library
  b. Locate all references to it in the code base
  c. Update all import statements to point to the new library version
  d. Update the main body of each file where required to comply with any functional changes from the new version or library
  e. Execute your test suite
  f. Commit your changes to your version control system
5. Publish your branch with your fixes once all tests past after the last library remediation