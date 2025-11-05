import java.util.Scanner;

public class Auth {

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine();
    }

    private final String authUser = "admin";
    private final int authPin = 1234;
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

    public boolean isAuthenticated() {
        return authUser.equals(username) && authPin == pin;
    }

    public void login() {
        Scanner input = new Scanner(System.in);
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            try {
                System.out.print("Enter username: ");
                String inputUsername = input.nextLine();
                System.out.print("Enter pin: ");
                
                int inputPin = 0;
                boolean validPin = false;
                
                while (!validPin) {
                    try {
                        inputPin = Integer.parseInt(input.nextLine());
                        validPin = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: PIN must be a number!");
                        pause(input);
                        clearConsole();
                        System.out.print("Enter pin again: ");
                    }
                }

                setUsername(inputUsername);
                setPin(inputPin);

                if (isAuthenticated()) {
                    System.out.println("Login successful!");
                    isLoggedIn = true;
                    pause(input);
                    clearConsole();
                } else {
                    System.out.println("Invalid username or pin. Please try again.");
                    pause(input);
                    clearConsole();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                pause(input);
                clearConsole();
            }
        }
    }

}
