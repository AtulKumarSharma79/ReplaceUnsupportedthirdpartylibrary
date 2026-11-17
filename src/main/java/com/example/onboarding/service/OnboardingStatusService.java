package com.example.onboarding.service;

import com.example.onboarding.dto.OnboardingStatus;
import com.example.onboarding.model.Employee;

/*import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;*/
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

    /*public OnboardingStatus statusFor(Employee employee, java.time.LocalDate today) {
        LocalDate start = toJoda(employee.getOnboardingDate());
        LocalDate now = toJoda(today);*/
    public OnboardingStatus statusFor(Employee employee, LocalDate today) {
        LocalDate start = employee.getOnboardingDate();

        //int days = Days.daysBetween(now, start).getDays();
        int days = (int) ChronoUnit.DAYS.between(today, start);
        String status = days > 0 ? "UPCOMING" : (days == 0 ? "STARTS_TODAY" : "STARTED");

        // Working days (Monday to Friday) after today, up to and including the onboarding date.
        int workingDays = 0;
        /*for (LocalDate d = now.plusDays(1); !d.isAfter(start); d = d.plusDays(1)) {
            if (d.getDayOfWeek() < DateTimeConstants.SATURDAY) {*/
        for (LocalDate d = today.plusDays(1); !d.isAfter(start); d = d.plusDays(1)) {
            DayOfWeek dow = d.getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }

        /*String label = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy")
                .withLocale(Locale.ENGLISH)
                .print(start);*/
        String label = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)
                .format(start);

        return new OnboardingStatus(employee.getId(), employee.getName(), employee.getOnboardingDate(),
                label, days, workingDays, status);
    }    
}
