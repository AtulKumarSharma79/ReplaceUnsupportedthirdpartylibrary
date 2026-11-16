package com.example.onboarding.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.example.onboarding.dto.OnboardingStatus;
import com.example.onboarding.model.Employee;

/**
 * Works out how far away an employee's onboarding date is.
 * Date arithmetic is done with Joda-Time.
 */
@Service
public class OnboardingStatusService {

    public OnboardingStatus statusFor(Employee employee, java.time.LocalDate today) {
        LocalDate start = employee.getOnboardingDate();
        LocalDate now = today;

        // Calculate days between
        int days = (int) ChronoUnit.DAYS.between(now, start);

        String status = days > 0 ? "UPCOMING" : (days == 0 ? "STARTS_TODAY" : "STARTED");

        // Working days (Monday to Friday) after today, up to and including the
        // onboarding date.
        int workingDays = 0;
        for (LocalDate d = now.plusDays(1); !d.isAfter(start); d = d.plusDays(1)) {

            if (d.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                workingDays++;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH);

        String label = start.format(formatter);

        return new OnboardingStatus(employee.getId(), employee.getName(), employee.getOnboardingDate(),
                label, days, workingDays, status);
    }
}
