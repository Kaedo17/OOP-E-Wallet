import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TransacHistory {
    private String amount;
    private String username;

    public TransacHistory(String amount, String username) {
        setAmount(amount);
        setUsername(username);
    }

    public String getAmount() {
        return amount;
    }
    public String getUsername() {
        return username;
    }
    public final void setAmount(String amount) {
        this.amount = amount;
    }
    public final void setUsername(String username) {
        this.username = username;
    }

    public void addTransac() {
        LocalDate today = LocalDate.now();
        String todayDate = today.toString();
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file))
            return;

        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("pin"))
                    continue;

                String storedUser = u.get("username").getAsString();

                if (storedUser.equals(Auth.getLoggedInUsername())) {
                    String history = u.get("History").getAsString();
                    String newHistory = todayDate + ": " + getAmount() + " - Transaction made\n";
                    u.addProperty("History", history + newHistory);

                    try (FileWriter writer = new FileWriter(file.toFile())) {
                        new GsonBuilder().setPrettyPrinting().create().toJson(users, writer);
                    } catch (Exception e1) {
                        System.out.println("Error updating transaction history: " + e1.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public void shoeHistory() {
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file)) {
            System.out.println("Database not found");
            return;
        }

        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("History"))
                    continue;

                String storedUser = u.get("username").getAsString();

                if (storedUser.equals(Auth.getLoggedInUsername())) {
                    String history = u.get("History").getAsString();
                    System.out.println("Transaction History for " + storedUser + ":");
                    System.out.println(history);
                    return;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error retrieving transaction history: " + ex.getMessage());
        }
    }
}
