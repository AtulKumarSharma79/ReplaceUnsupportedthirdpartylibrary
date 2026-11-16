package com.example.onboarding.service;

import com.example.onboarding.dto.OnboardingStatus;
import com.example.onboarding.model.Employee;
// import org.joda.time.DateTimeConstants;
// import org.joda.time.format.DateTimeFormat;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Works out how far away an employee's onboarding date is.
 * Date arithmetic is done with java-Time.
 */
@Service
public class OnboardingStatusService {

    public OnboardingStatus statusFor(Employee employee, java.time.LocalDate today) {
        LocalDate start = employee.getOnboardingDate();
        LocalDate now = today;

        int days = (int) ChronoUnit.DAYS.between(now, start);
        String status = days > 0 ? "UPCOMING" : (days == 0 ? "STARTS_TODAY" : "STARTED");

        // Working days (Monday to Friday) after today, up to and including the onboarding date.
        int workingDays = 0;
        for (LocalDate d = now.plusDays(1); !d.isAfter(start); d = d.plusDays(1)) {
            if (d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY){
                workingDays++;
            }
        }

        DateTimeFormatter DTFlabel = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH);
        String label = start.format(DTFlabel);

        return new OnboardingStatus(employee.getId(), employee.getName(), employee.getOnboardingDate(),
                label, days, workingDays, status);
    }

}
