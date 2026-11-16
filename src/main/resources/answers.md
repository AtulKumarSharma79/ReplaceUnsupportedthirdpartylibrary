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