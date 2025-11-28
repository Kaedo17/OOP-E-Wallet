import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class ShowProfile {
    private String username;
    private boolean accountDeleted = false;

    public ShowProfile(String username) {
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    private String formatLine(String label, String value) {
        // Define the maximum label width
        int maxLabelWidth = 15; // Longest label is "Date of Birth" (13 chars) + 2 padding

        // Pad the label to make all labels the same width
        String paddedLabel = String.format("%-" + maxLabelWidth + "s", label + ":");

        // Calculate available space for value
        int totalWidth = 41;
        int valueWidth = totalWidth - maxLabelWidth - 4; // 4 = "║ " + " ║"

        // Truncate value if too long
        String formattedValue = value.length() > valueWidth ? value.substring(0, valueWidth - 3) + "..." : value;

        return String.format("║ %s%-" + valueWidth + "s ║", paddedLabel, formattedValue);
    }

    public boolean isAccountDeleted() {
        return accountDeleted;
    }

    public void showProfile() {
        Scanner input = new Scanner(System.in);
        boolean backToDashboard = false;

        while (!backToDashboard) {
            Auth.clearConsole();
            Path file = Paths.get("database").resolve("user.json");
            if (!Files.exists(file)) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║             User not found            ║");
                System.out.println("╚═══════════════════════════════════════╝");
                Auth.pause(input);
                return;
            }

            try (Reader r = Files.newBufferedReader(file)) {
                JsonElement root = JsonParser.parseReader(r);
                JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("                 PROFILE                 ");
                System.out.println("╠═══════════════════════════════════════╣");

                boolean found = false;
                for (JsonElement e : users) {
                    JsonObject t = e.getAsJsonObject();
                    if (!t.has("username"))
                        continue;

                    String storedUser = t.get("username").getAsString();

                    if (storedUser.equals(getUsername())) {
                        String storedID = t.get("id").getAsString();
                        String storedName = t.get("Name").getAsString();
                        String storedPhone = t.get("phone").getAsString();
                        String storedEmail = t.get("email").getAsString();
                        String storedBenef = t.get("beneficiary").getAsString();
                        String storedAddress = t.get("address").getAsString();
                        String storedDob = t.get("dob").getAsString();
                        String storedSex = t.get("sex").getAsString();

                        System.out.println(formatLine("ID", storedID));
                        System.out.println(formatLine("Name", storedName.toUpperCase()));
                        System.out.println(formatLine("Date of Birth", storedDob));
                        System.out.println(formatLine("Sex", storedSex));
                        System.out.println(formatLine("Phone Number", storedPhone));
                        System.out.println(formatLine("Email", storedEmail));
                        System.out.println(formatLine("Address", storedAddress));
                        System.out.println(formatLine("Beneficiary", storedBenef));
                        
                        // Display credit card information if exists
                        CreditCard card = CreditCard.loadFromFile(username);
                        if (card != null) {
                            System.out.println("╠═══════════════════════════════════════╣");
                            System.out.println("║           CREDIT CARD INFO            ║");
                            System.out.println("╠═══════════════════════════════════════╣");
                            System.out.println(formatLine("Card Number", card.getCardNumber()));
                            System.out.println(formatLine("Expiry Date", card.getExpiryDate()));
                            System.out.println(formatLine("Credit Limit", "₱" + String.format("%.2f", card.getCreditLimit())));
                            System.out.println(formatLine("Available Credit", "₱" + String.format("%.2f", card.getAvailableCredit())));
                            System.out.println(formatLine("Current Balance", "₱" + String.format("%.2f", card.getCurrentBalance())));
                        }
                        
                        System.out.println("╠═══════════════════════════════════════╣");

                        // Add delete account option
                        System.out.println("║  1. Back to Dashboard                 ║");
                        System.out.println("║  2. Request Delete Account            ║");
                        System.out.println("╚═══════════════════════════════════════╝");
                        System.out.print("Select an option: ");

                        String choice = input.nextLine().trim();

                        switch (choice) {
                            case "1":
                                backToDashboard = true;
                                break;
                            case "2":
                                if (confirmAccountDeletion(input)) {
                                    deleteAccount(users, storedUser);
                                    System.out.println("╔═══════════════════════════════════════╗");
                                    System.out.println("║      Account deleted successfully!     ║");
                                    System.out.println("╚═══════════════════════════════════════╝");
                                    Auth.pause(input);
                                    accountDeleted = true;
                                    backToDashboard = true;
                                }
                                break;
                            default:
                                System.out.println("╔═══════════════════════════════════════╗");
                                System.out.println("║            Invalid option!            ║");
                                System.out.println("╚═══════════════════════════════════════╝");
                                Auth.pause(input);
                                break;
                        }

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("║           Invalid Profile             ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    Auth.pause(input);
                    backToDashboard = true;
                }
            } catch (Exception ex) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║        Error retrieving Profile       ║");
                System.out.println("╚═══════════════════════════════════════╝");
                Auth.pause(input);
                backToDashboard = true;
            }
        }
    }

    private boolean confirmAccountDeletion(Scanner input) {
        Auth.clearConsole();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         ACCOUNT DELETION              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  WARNING: This action is irreversible!║");
        System.out.println("║  All your data will be permanently    ║");
        System.out.println("║  lost.                                ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  Type 'CONFIRM' in all caps to        ║");
        System.out.println("║  delete your account:                 ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Confirmation: ");

        String confirmation = input.nextLine().trim();

        if ("CONFIRM".equals(confirmation)) {
            return true;
        } else {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║    Account deletion cancelled.        ║");
            System.out.println("╚═══════════════════════════════════════╝");
            Auth.pause(input);
            return false;
        }
    }

    private void deleteAccount(JsonArray users, String usernameToDelete) {
        try {
            // Remove user from users array
            for (int i = 0; i < users.size(); i++) {
                JsonObject user = users.get(i).getAsJsonObject();
                if (user.has("username") && user.get("username").getAsString().equals(usernameToDelete)) {
                    users.remove(i);
                    break;
                }
            }

            // Save updated users array back to file
            Path file = Paths.get("database").resolve("user.json");
            try (FileWriter writer = new FileWriter(file.toFile())) {
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(users, writer);
            }

            // Also remove user's transaction history
            deleteTransactionHistory(usernameToDelete);
            
            // Also remove user's credit card
            deleteCreditCard(usernameToDelete);

        } catch (JsonIOException | IOException ex) {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║    Error deleting account: " + ex.getMessage());
            System.out.println("╚═══════════════════════════════════════╝");
        }
    }

    private void deleteTransactionHistory(String usernameToDelete) {
        Path transacFile = Paths.get("database").resolve("transacHistory.json");
        if (!Files.exists(transacFile)) {
            return;
        }

        try (Reader r = Files.newBufferedReader(transacFile)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray transactions = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            // Remove all transactions for this user
            for (int i = transactions.size() - 1; i >= 0; i--) {
                JsonObject transaction = transactions.get(i).getAsJsonObject();
                if (transaction.has("username") &&
                        transaction.get("username").getAsString().equals(usernameToDelete)) {
                    transactions.remove(i);
                }
            }

            // Save updated transactions back to file
            try (FileWriter writer = new FileWriter(transacFile.toFile())) {
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(transactions, writer);
            }
        } catch (Exception ex) {
            // If we can't delete transaction history, just continue
            System.out.println("Note: Could not delete transaction history");
        }
    }

    private void deleteCreditCard(String usernameToDelete) {
        Path cardFile = Paths.get("database").resolve("creditCards.json");
        if (!Files.exists(cardFile)) {
            return;
        }

        try (Reader r = Files.newBufferedReader(cardFile)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray cards = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            // Remove credit card for this user
            for (int i = cards.size() - 1; i >= 0; i--) {
                JsonObject card = cards.get(i).getAsJsonObject();
                if (card.has("username") && card.get("username").getAsString().equals(usernameToDelete)) {
                    cards.remove(i);
                    break;
                }
            }

            // Save updated cards back to file
            try (FileWriter writer = new FileWriter(cardFile.toFile())) {
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(cards, writer);
            }
        } catch (Exception ex) {
            System.out.println("Note: Could not delete credit card");
        }
    }
}