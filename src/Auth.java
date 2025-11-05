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

    public static class Banner {
        public static void emptyBannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|                                       |");
            System.out.println("O---------------------------------------O");
        }

        public static void bannerSingleOpt() {
            System.out.println("|               [1] Back                |");
            System.out.println("O---------------------------------------O");
        }

        public static void bannerDoubleOpt() {
            System.out.println("|           [1] Back [2] Exit           |");
            System.out.println("O---------------------------------------O");
        }
    }

    public static class LoginBanner extends Auth.Banner {
        public static void loginBannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|              L O G I N                |");
            System.out.println("O---------------------------------------O");
        }
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

        outer: while (!isLoggedIn) {
            try {
                int inputPin = 0;
                boolean validPin = false;
                String inputUsername;
                do {
                    Auth.clearConsole();
                    Auth.LoginBanner.loginBannerShow();
                    Auth.LoginBanner.bannerSingleOpt();

                    System.out.print("Enter username: ");
                    inputUsername = input.nextLine();

                    if ("1".equals(inputUsername)) {
                        return;
                    }

                    Auth.clearConsole();
                    Auth.LoginBanner.loginBannerShow();
                    Auth.LoginBanner.bannerDoubleOpt();
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
                        pause(input);
                        clearConsole();
                        Auth.LoginBanner.loginBannerShow();
                        System.out.print("Enter pin again: ");
                    }
                } while (!validPin);

                setUsername(inputUsername);
                setPin(inputPin);

                if (isAuthenticated()) {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Login successful!");
                    isLoggedIn = true;
                    pause(input);
                    clearConsole();
                } else {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Invalid username or pin. Please try again.");
                    pause(input);
                    clearConsole();
                }
            } catch (Exception e) {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid input. Please try again.");
                pause(input);
                clearConsole();
            }
        }
    }

}
