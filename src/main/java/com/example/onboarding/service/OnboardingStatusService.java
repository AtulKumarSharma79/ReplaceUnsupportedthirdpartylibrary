package com.example.onboarding.service;

import com.example.onboarding.dto.OnboardingStatus;
import com.example.onboarding.model.Employee;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Works out how far away an employee's onboarding date is.
 * Date arithmetic is done with Joda-Time.
 */
@Service
public class OnboardingStatusService {

    public OnboardingStatus statusFor(Employee employee, LocalDate today) {
        LocalDate start = employee.getOnboardingDate();
        LocalDate now = today;

        int days = (int) ChronoUnit.DAYS.between(now, start);
        String status = days > 0 ? "UPCOMING" : (days == 0 ? "STARTS_TODAY" : "STARTED");

        // Working days (Monday to Friday) after today, up to and including the onboarding date.
        int workingDays = 0;
        for (LocalDate d = now.plusDays(1); !d.isAfter(start); d = d.plusDays(1)) {
            if (d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }

        String label = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
                .withLocale(Locale.ENGLISH)
                .format(start);

        return new OnboardingStatus(employee.getId(), employee.getName(), employee.getOnboardingDate(),
                label, days, workingDays, status);
    }

}
