import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BalanceManager {
    private String balanceUsername;
    private String balanceRealName;
    private int balancePin;
    private long balanceBalance;
    private long transferBalance;

    public BalanceManager(String balanceRealName, String balanceUsername, int balancePin) {
        setUsername(balanceUsername);
        setRealName(balanceRealName);
        setPin(balancePin);
        setBalance(0);
        setTransfer(0);
    }

    public String getUsername() {
        return balanceUsername;
    }

    public String getRealName() {
        return balanceRealName;
    }

    public int getPin() {
        return balancePin;
    }

    public long getBalance() {
        return balanceBalance;
    }

    public long getTransfer() {
        return transferBalance;
    }

    public final void setUsername(String balanceUsername) {
        this.balanceUsername = balanceUsername;
    }

    public final void setRealName(String balanceRealName) {
        this.balanceRealName = balanceRealName;
    }

    public final void setPin(int balancePin) {
        this.balancePin = balancePin;
    }

    public final void setBalance(long balanceBalance) {
        this.balanceBalance = balanceBalance;
    }

    public final void setTransfer(long transferBalance) {
        this.transferBalance = transferBalance;
    }

    public boolean userChecker() {
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file))
            return false;

        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("pin"))
                    continue;

                String storedUser = u.get("username").getAsString();

                if (storedUser.equals(getUsername())) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    public void showBalance() {
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file)) {
            System.out.println("Database not found");
            System.out.println("O---------------------------------------O");
            return;
        }
        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            boolean userFound = false;

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("pin") || !u.has("balance"))
                    continue;

                String storedUser = u.get("username").getAsString();
                String storedPin = u.get("pin").getAsString();
                String enteredPin = String.format("%04d", getPin());

                // Check if username AND pin match
                if (storedUser.equals(getUsername()) && storedPin.equals(enteredPin)) {
                    long balance = u.get("balance").getAsLong();
                    System.out.println("        Current Balance: $" + balance);
                    System.out.println("O---------------------------------------O");
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                System.out.println("User not found or invalid credentials.");
                System.out.println("O---------------------------------------O");
            }
        } catch (Exception ex) {
            System.out.println("O---------------------------------------O");
            System.out.println("Error retrieving balance.");
            System.out.println("O---------------------------------------O");
        }

    }

    public void addBalance() {
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file)) {
            System.out.println("Database not found");
            System.out.println("O---------------------------------------O");
            return;
        }
        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.isJsonArray() ? root.getAsJsonArray() : new JsonArray();

            boolean userFound = false;

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("pin") || !u.has("balance"))
                    continue;

                String storedUser = u.get("username").getAsString();

                // Check if username AND pin match
                if (storedUser.equals(getUsername())) {
                    long currentBalance = u.get("balance").getAsLong();
                    long newBalance = getBalance() + currentBalance;

                    u.addProperty("balance", newBalance);
                    System.out.println("O---------------------------------------O");
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                try (FileWriter writer = new FileWriter(file.toFile())) {
                    new com.google.gson.GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(users, writer);
                }
            }

            if (!userFound) {
                System.out.println("User not found or invalid credentials.");
                System.out.println("O---------------------------------------O");
            }
        } catch (Exception ex) {
            System.out.println("O---------------------------------------O");
            System.out.println("Error retrieving balance.");
            System.out.println("O---------------------------------------O");
        }

    }

    public void transferBalance() {
        Path file = Paths.get("database").resolve("user.json");
        if (!Files.exists(file)) {
            System.out.println("Database not found");
            System.out.println("O---------------------------------------O");
            return;
        }
        try (Reader r = Files.newBufferedReader(file)) {
            JsonElement root = JsonParser.parseReader(r);
            JsonArray users = root.getAsJsonArray();

            boolean userFound = false;
            boolean hasSufficientBalance = false; // Add this flag

            for (JsonElement e : users) {
                JsonObject u = e.getAsJsonObject();
                if (!u.has("username") || !u.has("pin") || !u.has("balance"))
                    continue;

                String storedUser = u.get("username").getAsString();

                if (storedUser.equals(getUsername())) {
                    long currentBalance = u.get("balance").getAsLong();
                    userFound = true;

                    if (currentBalance < getTransfer()) { // Change > to <
                        System.out.println("O---------------------------------------O");
                        System.out.println("Insufficient balance for transfer!");
                        System.out.println("Current balance: $" + currentBalance);
                        System.out.println("O---------------------------------------O");
                        hasSufficientBalance = false;
                    } else {
                        long newBalance = currentBalance - getTransfer();
                        u.addProperty("balance", newBalance);
                        System.out.println("O---------------------------------------O");
                        System.out.println("Transfer successful! New balance: $" + newBalance);
                        System.out.println("O---------------------------------------O");
                        hasSufficientBalance = true;
                    }
                    break;
                }
            }

            // Only write to file if transfer was successful
            if (userFound && hasSufficientBalance) {
                try (FileWriter writer = new FileWriter(file.toFile())) {
                    new com.google.gson.GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(users, writer);
                }
            }

            if (!userFound) {
                System.out.println("User not found or invalid credentials.");
                System.out.println("O---------------------------------------O");
            }
        } catch (Exception ex) {
            System.out.println("O---------------------------------------O");
            System.out.println("Error processing transfer.");
            System.out.println("O---------------------------------------O");
        }
    }

}
