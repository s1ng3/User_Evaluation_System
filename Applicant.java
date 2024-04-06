import java.time.LocalDateTime;
import java.util.regex.Pattern;

/*
 * Author: Tudor-Cristian Sîngerean
 * Date: 03.04.2024
 */
class Applicant {
    private String name;
    private String email;
    private LocalDateTime deliveryDateTime;
    private double score;
    private double initialScore;

    public Applicant(String name, String email, LocalDateTime deliveryDateTime, double score) {
        this.name = name;
        this.email = email;
        this.deliveryDateTime = deliveryDateTime;
        this.score = score;
        this.initialScore = score;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public double getScore() {
        return score;
    }

    public double getInitialScore() {
        return initialScore;
    }

    void adjustScore(LocalDateTime firstDay, LocalDateTime lastDay) {
        if (deliveryDateTime.toLocalDate().equals(firstDay.toLocalDate())) {
            this.score += 1;
        } else if (deliveryDateTime.toLocalDate().equals(lastDay.toLocalDate()) && deliveryDateTime.getHour() >= 12) {
            this.score -= 1;
        }
    }

    String getLastName() {
        return name.substring(name.lastIndexOf(' ') + 1);
    }

    boolean isValid() {
        return isValidName() && isValidEmail() && isValidScore();
    }

    private boolean isValidName() {
        return name != null && !name.trim().isEmpty() && name.trim().split(" ").length >= 2;
    }

    private boolean isValidEmail() {
        String emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return email != null && Pattern.matches(emailRegex, email);
    }

    private boolean isValidScore() {
        return score >= 0 && score <= 10;
    }
}