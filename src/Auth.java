import java.io.Reader;
import java.util.Scanner;
import java.nio.file.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Auth {
    Banners loginBanner = new Banners();
    private static String loggedInUsername;
    private static int loggedInPin;

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine();
    }

    private String username;
    private int pin;

    public Auth(String username, int pin) {
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

    public void login() {
        Scanner input = new Scanner(System.in);
        boolean isLoggedIn = false;

        outer: while (!isLoggedIn) {
            try {
                int inputPin = 0;
                boolean validPin = false;
                String inputUsername;
                do {
                    Auth.clearConsole();
                    loginBanner.new LoginBanner().bannerShow();
                    loginBanner.new LoginBanner().bannerSingleOpt();

                    System.out.print("Enter username: ");
                    inputUsername = input.nextLine().trim();

                    if ("1".equals(inputUsername)) {
                        return;
                    }

                    if (inputUsername.isEmpty()) {
                        System.out.println("O------------------------------------------------O");
                        System.err.println("Username cannot be empty. Please enter a username.");
                        System.out.println("O------------------------------------------------O");
                        Auth.pause(input);
                        continue outer; // re-prompt username
                    }

                    Auth.clearConsole();
                    loginBanner.new LoginBanner().bannerShow();
                    loginBanner.new LoginBanner().bannerDoubleOpt();
                    System.out.print("Enter pin: ");

                    try {
                        inputPin = Integer.parseInt(input.nextLine());
                        if (inputPin == 1) {
                            continue outer;
                        } else if (inputPin == 2) {
                            return;
                        }
                        validPin = true;
                    } catch (NumberFormatException e) {
                        System.out.println("O---------------------------------------O");
                        System.out.println("Error: PIN must be a number!");
                        System.out.println("O---------------------------------------O");
                        pause(input);
                        clearConsole();
                        loginBanner.new LoginBanner().bannerShow();
                        System.out.print("Enter pin again: ");
                    }
                } while (!validPin);

                setUsername(inputUsername);
                setPin(inputPin);
                BalanceManager balanceManager = new BalanceManager("", inputUsername, inputPin);

                if (userValidator()) {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Login successful!");
                    System.out.println("O---------------------------------------O");
                    // Store the logged-in credentials
                    loggedInUsername = getUsername();
                    loggedInPin = getPin();
                    isLoggedIn = true;
                    pause(input);
                    clearConsole();
                    Dashboard dashboard = new Dashboard();
                    dashboard.displayDashboard(input);
                } else {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Invalid username or pin. Please try again.");
                    System.out.println("O---------------------------------------O");
                    pause(input);
                    clearConsole();
                }
            } catch (Exception e) {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid input. Please try again.");
                System.out.println("O---------------------------------------O");
                pause(input);
                clearConsole();
            }
        }

    }

    public boolean userValidator() {
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
                String storedPin = u.get("pin").getAsString(); // <-- string

                // The user typed the PIN as a string (e.g. "0000")
                String enteredPin = String.format("%04d", getPin());

                if (storedUser.equals(getUsername()) && storedPin.equals(enteredPin)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    // Add getters for the logged-in user
    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static int getLoggedInPin() {
        return loggedInPin;
    }

}
