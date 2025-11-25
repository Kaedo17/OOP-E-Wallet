import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ShowProfile {
    private String username;

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

    public void showProfile() {
        Auth.clearConsole();
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file)) {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║             User not found            ║");
            System.out.println("╚═══════════════════════════════════════╝");
            return;
        }

        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray transactions = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("                 PROFILE                 ");
            System.out.println("╠═══════════════════════════════════════╣");

            boolean found = false;
            for (JsonElement e : transactions) {
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
                    System.out.println("╠═══════════════════════════════════════╣");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("║           Invalid Profile             ║");
                System.out.println("╚═══════════════════════════════════════╝");
            } else {
                System.out.println("║                                       ║");
                System.out.println("╚═══════════════════════════════════════╝");
            }
        } catch (Exception ex) {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║        Error retrieving Profile       ║");
            System.out.println("╚═══════════════════════════════════════╝");
        }
    }
}
