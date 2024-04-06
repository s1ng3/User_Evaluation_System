import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/*
 * Author: Tudor-Cristian Sîngerean
 * Date: 03.04.2024
 */
public class ApplicantsProcessor {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public String processApplicants(InputStream csvStream) throws IOException {
        Reader in = new InputStreamReader(csvStream);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);

        List<Applicant> applicants = new ArrayList<>();
        LocalDateTime firstDay = LocalDateTime.MAX;
        LocalDateTime lastDay = LocalDateTime.MIN;
        for (CSVRecord record : records) {
            if (record.size() < 4) continue;
            String name = record.get(0);
            String email = record.get(1);
            LocalDateTime deliveryDateTime;
            try {
                deliveryDateTime = LocalDateTime.parse(record.get(2), formatter);
            } catch (Exception e) {
                continue;
            }
            double score;
            try {
                score = Double.parseDouble(record.get(3));
            } catch (NumberFormatException e) {
                continue;
            }

            Applicant applicant = new Applicant(name, email, deliveryDateTime, score);
            if (!applicant.isValid()) continue;

            firstDay = deliveryDateTime.isBefore(firstDay) ? deliveryDateTime : firstDay;
            lastDay = deliveryDateTime.isAfter(lastDay) ? deliveryDateTime : lastDay;

            applicants.add(applicant);
        }

        LocalDateTime finalFirstDay = firstDay;
        LocalDateTime finalLastDay = lastDay;
        applicants.forEach(applicant -> applicant.adjustScore(finalFirstDay, finalLastDay));
        applicants.sort(Comparator.comparing(Applicant::getScore).reversed()
                .thenComparing(Applicant::getDeliveryDateTime)
                .thenComparing(Applicant::getEmail));

        // Extract required information
        int uniqueApplicants = (int) applicants.stream().map(Applicant::getEmail).distinct().count();
        List<String> topApplicants = applicants.stream().limit(Math.min(3, uniqueApplicants)).map(Applicant::getLastName).collect(Collectors.toList()); // Limit the number of top applicants to the number of unique applicants if there are fewer than 3 unique applicants
        double averageScore = applicants.stream().limit((applicants.size() + 1) / 2).mapToDouble(Applicant::getInitialScore).average().orElse(0);

        // Convert to JSON
        Map<String, Object> result = new HashMap<>();
        result.put("uniqueApplicants", uniqueApplicants);
        result.put("averageScore", Math.round(averageScore * 100.0) / 100.0);
        result.put("topApplicants", topApplicants);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }
}