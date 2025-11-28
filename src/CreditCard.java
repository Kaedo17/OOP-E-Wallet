import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;

public class CreditCard {
    private String username;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double creditLimit;
    private double availableCredit;
    private double currentBalance;

    public CreditCard(String username) {
        this.username = username;
        generateCardDetails();
        this.creditLimit = 50000.00; // Default credit limit
        this.availableCredit = creditLimit;
        this.currentBalance = 0.00;
    }

    public CreditCard(String username, String cardNumber, String expiryDate, String cvv, 
                     double creditLimit, double availableCredit, double currentBalance) {
        this.username = username;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
        this.currentBalance = currentBalance;
    }

    private void generateCardDetails() {
        SecureRandom random = new SecureRandom();
        
        // Generate 16-digit card number (4 groups of 4 digits)
        StringBuilder cardNumberBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) cardNumberBuilder.append(" ");
            for (int j = 0; j < 4; j++) {
                cardNumberBuilder.append(random.nextInt(10));
            }
        }
        this.cardNumber = cardNumberBuilder.toString();

        // Generate expiry date (3 years from now)
        LocalDate expiry = LocalDate.now().plusYears(3);
        this.expiryDate = expiry.format(DateTimeFormatter.ofPattern("MM/yy"));

        // Generate 3-digit CVV
        StringBuilder cvvBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvvBuilder.append(random.nextInt(10));
        }
        this.cvv = cvvBuilder.toString();
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getCardNumber() { return cardNumber; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public double getCreditLimit() { return creditLimit; }
    public double getAvailableCredit() { return availableCredit; }
    public double getCurrentBalance() { return currentBalance; }

    public void setCreditLimit(double creditLimit) { this.creditLimit = creditLimit; }
    public void setAvailableCredit(double availableCredit) { this.availableCredit = availableCredit; }
    public void setCurrentBalance(double currentBalance) { this.currentBalance = currentBalance; }

    public boolean makePayment(double amount) {
        if (amount <= 0 || amount > currentBalance) {
            return false;
        }
        this.currentBalance -= amount;
        this.availableCredit += amount;
        return true;
    }

    public boolean makePurchase(double amount) {
        if (amount <= 0 || amount > availableCredit) {
            return false;
        }
        this.currentBalance += amount;
        this.availableCredit -= amount;
        return true;
    }

    public void saveToFile() {
        Path file = Paths.get("database").resolve("creditCards.json");
        JsonArray cardsArray;

        // Read existing cards or create new array
        try {
            if (Files.exists(file)) {
                try (Reader reader = Files.newBufferedReader(file)) {
                    cardsArray = JsonParser.parseReader(reader).getAsJsonArray();
                }
            } else {
                cardsArray = new JsonArray();
                Files.createDirectories(file.getParent());
            }
        } catch (Exception e) {
            cardsArray = new JsonArray();
        }

        // Remove existing card for this user if it exists
        for (int i = 0; i < cardsArray.size(); i++) {
            JsonObject card = cardsArray.get(i).getAsJsonObject();
            if (card.get("username").getAsString().equals(username)) {
                cardsArray.remove(i);
                break;
            }
        }

        // Add new card
        JsonObject cardObject = new JsonObject();
        cardObject.addProperty("username", username);
        cardObject.addProperty("cardNumber", cardNumber);
        cardObject.addProperty("expiryDate", expiryDate);
        cardObject.addProperty("cvv", cvv);
        cardObject.addProperty("creditLimit", creditLimit);
        cardObject.addProperty("availableCredit", availableCredit);
        cardObject.addProperty("currentBalance", currentBalance);

        cardsArray.add(cardObject);

        // Write back to file
        try (FileWriter writer = new FileWriter(file.toFile())) {
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(cardsArray, writer);
        } catch (Exception e) {
            System.out.println("Error saving credit card: " + e.getMessage());
        }
    }

    public static CreditCard loadFromFile(String username) {
        Path file = Paths.get("database").resolve("creditCards.json");
        if (!Files.exists(file)) {
            return null;
        }

        try (Reader reader = Files.newBufferedReader(file)) {
            JsonArray cardsArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : cardsArray) {
                JsonObject cardObject = element.getAsJsonObject();
                if (cardObject.get("username").getAsString().equals(username)) {
                    return new CreditCard(
                            cardObject.get("username").getAsString(),
                            cardObject.get("cardNumber").getAsString(),
                            cardObject.get("expiryDate").getAsString(),
                            cardObject.get("cvv").getAsString(),
                            cardObject.get("creditLimit").getAsDouble(),
                            cardObject.get("availableCredit").getAsDouble(),
                            cardObject.get("currentBalance").getAsDouble()
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading credit card: " + e.getMessage());
        }

        return null;
    }

    public static boolean cardExists(String username) {
        Path file = Paths.get("database").resolve("creditCards.json");
        if (!Files.exists(file)) {
            return false;
        }

        try (Reader reader = Files.newBufferedReader(file)) {
            JsonArray cardsArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : cardsArray) {
                JsonObject cardObject = element.getAsJsonObject();
                if (cardObject.get("username").getAsString().equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static boolean validateCardForUser(String username, String cardNumber) {
        CreditCard card = loadFromFile(username);
        if (card == null) {
            return false;
        }
        // Remove spaces for comparison
        String cleanCardNumber = cardNumber.replaceAll("\\s+", "");
        String cleanStoredCardNumber = card.getCardNumber().replaceAll("\\s+", "");
        return cleanStoredCardNumber.equals(cleanCardNumber);
    }
}