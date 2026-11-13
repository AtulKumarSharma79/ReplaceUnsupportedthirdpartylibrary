# Onboarding Tracker (library replacement workshop starter)

Spring Boot 3.5 REST API with embedded H2. It uses three older libraries that you will replace: Joda-Time 2.1, log4j 1.2.17 and opencsv 2.3. Read [PROBLEM_STATEMENT.md](PROBLEM_STATEMENT.md) first.

## Run
```
gradlew.bat bootRun        (macOS/Linux: ./gradlew bootRun)
gradlew.bat test
```

## APIs
- `POST /api/employees` with `{ "name": "...", "role": "...", "onboardingDate": "yyyy-MM-dd" }` returns `201`.
- `GET /api/employees` returns `200` with all employees.
- `GET /api/employees/{id}/onboarding-status?asOf=yyyy-MM-dd` returns days and working days until onboarding. Unknown id returns `404`.
- `GET /api/employees/export` returns all employees as CSV.
- Invalid input returns `400`.
