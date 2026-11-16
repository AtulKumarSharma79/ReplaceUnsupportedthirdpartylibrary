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

## Audit Table (for Core Task 1)

| Library | Declared in | Used in | Version release date (year) | Project status | Replacement | LTS support for current version? |
|---|---|---|---|---|---|---|
| joda-time 2.1 | `build.gradle` | `.../service/OnboardingStatusService.java` | 2012 | Active | `java.time` | Yes, but only for compatability and only until 3.x |
| log4j 1.2.17 | `build.gradle` | `.../controller/EmployeeController.java`,  | 2012 | Active (Relocated on MVN repos) | SLF4J API with Logback | No (last support for 1.x was 2015) |
| opencsv 2.3 | `build.gradle` | `.../service/EmployeeCsvExporter.java` | 2011 | Active (Relocated on MVN repos) | opencsv 5.x (`com.opencsv`) | No (not even mentioned on upgrade support notes on the official website) |

## Dependecy Graph Output and grep Results (for Core Task 6)

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

`grep -rE "org.joda|org.apache.log4j|au.com.bytecode" src`

Returns no results

### joda-time

`./gradlew dependencyInsight --dependency joda-time --configuration runtimeClasspath`

```
> Task :dependencyInsight
No dependencies matching given input were found in configuration ':runtimeClasspath'
```

### log4j

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



### net.sf.opencsv

`./gradlew dependencyInsight --dependency net.sf.opencsv --configuration runtimeClasspath`

```
> Task :dependencyInsight
No dependencies matching given input were found in configuration ':runtimeClasspath'
```

## Change Steps and Notes (for Core Task)