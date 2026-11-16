package com.example.onboarding.service;

import com.example.onboarding.dto.OnboardingStatus;
import com.example.onboarding.model.Employee;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;



/**
 * Works out how far away an employee's onboarding date is.
 * Date arithmetic is done with Joda-Time.
 */
@Service
public class OnboardingStatusService {

    public OnboardingStatus statusFor(Employee employee, LocalDate today) {

    LocalDate start = employee.getOnboardingDate();

    int days = (int) ChronoUnit.DAYS.between(today, start);

    String status = days > 0
            ? "UPCOMING"
            : (days == 0 ? "STARTS_TODAY" : "STARTED");

    int workingDays = 0;

    for (LocalDate d = today.plusDays(1);
         !d.isAfter(start);
         d = d.plusDays(1)) {

        if (d.getDayOfWeek() != DayOfWeek.SATURDAY
                && d.getDayOfWeek() != DayOfWeek.SUNDAY) {
            workingDays++;
        }
    }

    String label = start.format(
            DateTimeFormatter.ofPattern(
                    "EEEE, dd MMMM yyyy",
                    Locale.ENGLISH
            )
    );

    return new OnboardingStatus(
            employee.getId(),
            employee.getName(),
            employee.getOnboardingDate(),
            label,
            days,
            workingDays,
            status
    );
}

}
