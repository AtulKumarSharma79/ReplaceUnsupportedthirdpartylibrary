package com.example.onboarding.service;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.example.onboarding.model.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Writes employees as CSV (every field quoted, "\n" line endings), using opencsv.
 */
@Component
public class EmployeeCsvExporter {

    public String export(List<Employee> employees) {
        StringWriter out = new StringWriter();

        try (ICSVWriter writer = new CSVWriterBuilder(out)
                .withSeparator(',')
                .withQuoteChar('"')
                .withEscapeChar('"')
                .withLineEnd("\n")
                .build()) {

            writer.writeNext(
                    new String[]{"id", "name", "role", "onboardingDate"},
                    true
            );

            for (Employee e : employees) {
                writer.writeNext(
                        new String[]{
                                String.valueOf(e.getId()),
                                e.getName(),
                                e.getRole(),
                                String.valueOf(e.getOnboardingDate())
                        },
                        true
                );
            }

        } catch (IOException ex) {
            throw new IllegalStateException("Could not write CSV", ex);
        }

        return out.toString();
    
    }
}
