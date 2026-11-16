package com.example.onboarding.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.example.onboarding.model.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Writes employees as CSV (every field quoted, "\n" line endings), using Apache Commons CSV.
 */
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
