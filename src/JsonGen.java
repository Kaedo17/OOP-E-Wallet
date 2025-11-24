import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonGen {

    private String username;
    private String realName;
    private int pin;
    private String phone;
    private String email;
    private String beneficiary;
    private String address;
    private String dob;
    private String sex;

    public JsonGen(String realName, String username, int pin) {
        setUsername(username);
        setRealName(realName);
        setPin(pin);
    }

    public JsonGen(String realName, String username, int pin, String phone, String email, String beneficiary, String address, String dob, String sex) {
        setUsername(username);
        setRealName(realName);
        setPin(pin);
        setPhone(phone);
        setEmail(email);
        setBeneficiary(beneficiary);
        setAddress(address);
        setDob(dob);
        setSex(sex);
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }

    public int getPin() {
        return pin;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public final void setRealName(String realName) {
        this.realName = realName;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final void setPin(int pin) {
        this.pin = pin;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final void setDob(String dob) {
        this.dob = dob;
    }

    public final void setSex(String sex) {
        this.sex = sex;
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
        newUser.addProperty("Name", getRealName());
        newUser.addProperty("username", getUsername());
        String pinStr = String.format("%04d", pin);
        newUser.addProperty("pin", pinStr);
        newUser.addProperty("balance", 0);
        newUser.addProperty("phone", getPhone());
        newUser.addProperty("email", getEmail());
        newUser.addProperty("beneficiary", getBeneficiary());
        newUser.addProperty("address", getAddress());
        newUser.addProperty("dob", getDob());
        newUser.addProperty("sex", getSex());
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