import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Auth.clearConsole();
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        do {
            System.out.println("Welcome to E-Wallet Control Panel");
            System.out.println("1. Sign In");
            System.out.println("2. Login");
            System.out.println("3. Logout/Exit");
            System.out.print("Please select an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    Auth.clearConsole();
                    SignIn signin = new SignIn("", 0);
                    signin.signIn();
                    Auth.clearConsole();
                }
                case 2 -> {
                    Auth.clearConsole();
                    Auth auth = new Auth("", 0);
                    auth.login();
                    Auth.clearConsole();

                }
                case 3 -> {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Exiting the application. Goodbye!");
                    Auth.pause(input);
                    if(input.nextLine()!=null){
                        Auth.clearConsole();
                        exit = true;
                    }
                }
                default -> System.out.println("Invalid option selected.");
            }
        } while (!exit);
        input.close();
        input.close();

    }
}
