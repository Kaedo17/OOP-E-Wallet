import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Auth.clearConsole();
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        do {
            System.out.println("O---------------------------------------O");
            System.out.println("|            O N L I - B A N K           |");
            System.out.println("O---------------------------------------O");
            System.out.println("1. Sign In");
            System.out.println("2. Login");
            System.out.println("3. Logout/Exit");
            System.out.print("Please select an option: ");
            // int choice = input.nextInt();

            String choice = input.nextLine().trim();

            if ("1".equals(choice)) {
                Auth.clearConsole();
                SignIn signin = new SignIn();
                signin.signIn();
                Auth.clearConsole();
            } else if ("2".equals(choice)) {
                Auth.clearConsole();
                Auth auth = new Auth("", 0);
                auth.login();
                Auth.clearConsole();
            } else if ("3".equals(choice)) {
                System.out.println("O---------------------------------------O");
                System.out.println("Exiting the application. Goodbye!");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
                    Auth.clearConsole();
                    exit = true;
            } else {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid option selected.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
                Auth.clearConsole();
            }

        } while (!exit);
        input.close();
        input.close();

    }
}
