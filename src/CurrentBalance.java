import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrentBalance {
    private String balanceUsername;
    private int balancePin;
    private long balanceBalance;
    public CurrentBalance(String balanceUsername, int balancePin) {
        setUsername(balanceUsername);
        setPin(balancePin);
        setBalance(0);
    }

    public String getUsername() {
        return balanceUsername;
    }

    public int getPin() {
        return balancePin;
    }

    public long getBalance() {
        return balanceBalance;
    }

    public final void setUsername(String balanceUsername) {
        this.balanceUsername = balanceUsername;
    }

    public final void setPin(int balancePin) {
        this.balancePin = balancePin;
    }

    public final void setBalance(long balanceBalance) {
        this.balanceBalance = balanceBalance;
    }

    public long currentBalance() {
    long theBalance = 0;
    Path file = Paths.get("database").resolve("user.json");
    if (!Files.exists(file)) {
        return 0;
    }
    try (Reader r = Files.newBufferedReader(file)) {
        JsonElement root = JsonParser.parseReader(r);
        JsonArray users = root.getAsJsonArray();

        for (JsonElement e : users) {
            JsonObject u = e.getAsJsonObject();
            if (!u.has("username") || !u.has("pin") || !u.has("balance"))
                continue;

            String storedUser = u.get("username").getAsString();
            String storedPin = u.get("pin").getAsString();
            String enteredPin = String.format("%04d", getPin()); // Add PIN validation

            // Check if username AND pin match
            if (storedUser.equals(getUsername()) && storedPin.equals(enteredPin)) {
                long currentBalance = u.get("balance").getAsLong();
                theBalance = currentBalance;
                break; // Move break inside the if statement
            }
        }
    } catch (Exception ex) {
        return 0;
    }
    return theBalance;
}
}
