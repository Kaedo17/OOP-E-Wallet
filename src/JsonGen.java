import com.google.gson.*;
import java.io.*;
//import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonGen {

    private String username;
    private int pin;

    public JsonGen(String username, int pin) {
        setUsername(username);
        setPin(pin);

    }

    public String getUsername() {
        return username;
    }

    public int getPin() {
        return pin;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final void setPin(int pin) {
        this.pin = pin;
    }

    public void updateJson() {
        JsonArray users;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int nextId = 1;

        try (Reader reader = new FileReader("database/user.json")) {

            users = gson.fromJson(reader, JsonArray.class);

            if (users.size() > 0) {
                JsonObject lastUser = users.get(users.size() - 1).getAsJsonObject();
                nextId = lastUser.get("id").getAsInt() + 1;
            }

        } catch (Exception e) {
            users = new JsonArray();
        }

        JsonObject newUser = new JsonObject();
        newUser.addProperty("id", nextId);
        newUser.addProperty("username", getUsername());
        // store PIN as a zero-padded 4-digit string so leading zeros are preserved
        String pinStr = String.format("%04d", pin);
        newUser.addProperty("pin", pinStr);
        newUser.addProperty("balance", 0);
        users.add(newUser);

        try (FileWriter writer = new FileWriter("database/user.json")) {
            gson.toJson(users, writer);
            writer.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
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

}
