import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class TransacHistory {
    private String amount;
    private String username;
    private String type;

    public TransacHistory(String amount, String username, String type) {
        setAmount(amount);
        setUsername(username);
        setType(type);
    }

    public String getAmount() {
        return amount;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getType() {
        return type;
    }
    
    public final void setAmount(String amount) {
        this.amount = amount;
    }
    
    public final void setUsername(String username) {
        this.username = username;
    }
    
    public final void setType(String type) {
        this.type = type;
    }

    public void addTransac() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        
        JsonArray transactions;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int nextId = 1;

        // Read existing transactions or create new array
        Path file = Paths.get("database").resolve("transacHistory.json");
        try {
            if (Files.exists(file)) {
                try (Reader reader = Files.newBufferedReader(file)) {
                    transactions = gson.fromJson(reader, JsonArray.class);
                    if (transactions.size() > 0) {
                        JsonObject lastTransac = transactions.get(transactions.size() - 1).getAsJsonObject();
                        nextId = lastTransac.get("id").getAsInt() + 1;
                    }
                }
            } else {
                transactions = new JsonArray();
            }
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            transactions = new JsonArray();
        }

        // Create new transaction entry
        JsonObject newTransaction = new JsonObject();
        newTransaction.addProperty("id", nextId);
        newTransaction.addProperty("username", getUsername());
        newTransaction.addProperty("type", getType());
        newTransaction.addProperty("amount", getAmount());
        newTransaction.addProperty("timestamp", timestamp);
        transactions.add(newTransaction);

        
        try (FileWriter writer = new FileWriter(file.toFile())) {
            gson.toJson(transactions, writer);
            System.out.println("Transaction recorded successfully!");
        } catch (Exception e) {
            System.out.println("Error saving transaction history: " + e.getMessage());
        }
    }

    public void showHistory() {
        Auth.clearConsole();
        Path file = Paths.get("database").resolve("transacHistory.json");
        if (!Files.exists(file)) {
            System.out.println("O---------------------------------------O");
            System.out.println("No transaction history found.");
            System.out.println("O---------------------------------------O");
            return;
        }

        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray transactions = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            System.out.println("O---------------------------------------O");
            System.out.println("      TRANSACTION HISTORY FOR " + getUsername().toUpperCase());
            System.out.println("O---------------------------------------O");
            
            boolean found = false;
            for (JsonElement e : transactions) {
                JsonObject t = e.getAsJsonObject();
                if (!t.has("username") || !t.has("type") || !t.has("amount") || !t.has("timestamp"))
                    continue;

                String storedUser = t.get("username").getAsString();
                
                if (storedUser.equals(getUsername())) {
                    String transcaType = t.get("type").getAsString();
                    String transacAmount = t.get("amount").getAsString();
                    String timestamp = t.get("timestamp").getAsString();
                    
                    System.out.println("Date: " + timestamp);
                    System.out.println("Type: " + transcaType.toUpperCase());
                    System.out.println("Amount: $" + transacAmount);
                    System.out.println("O---------------------------------------O");
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("No transactions found for your account.");
                System.out.println("O---------------------------------------O");
            }
        } catch (Exception ex) {
            System.out.println("O---------------------------------------O");
            System.out.println("Error retrieving transaction history: " + ex.getMessage());
            System.out.println("O---------------------------------------O");
        }
    }
}