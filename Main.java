import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

/*
 * Author: Tudor-Cristian Sîngerean
 * Date: 03.04.2024
*/

public class Main {
    public static void main(String[] args) {
        try {
            InputStream csvStream = new FileInputStream("applications.csv");
            ApplicantsProcessor processor = new ApplicantsProcessor();
            String result = processor.processApplicants(csvStream);

            try (FileWriter file = new FileWriter("output.json")) {
                file.write(result);
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}