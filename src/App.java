import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Auth.clearConsole();
        try (Scanner input = new Scanner(System.in)) {
            boolean exit = false;
            do {
                Banners.printMainLogo();
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║            O N L I - B A N K          ║");
                System.out.println("╠═══════════════════════════════════════╣");
                System.out.println("║  1. Sign In                           ║");
                System.out.println("║  2. Login                             ║");
                System.out.println("║  3. Logout/Exit                       ║");
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.print("Please select an option: ");
                
                String choice = input.nextLine().trim();
                
                if (null == choice) {
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║        Invalid option selected.       ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    Auth.pause(input);
                    Auth.clearConsole();
                } else switch (choice) {
                    case "1":
                        Auth.clearConsole();
                        SignIn signin = new SignIn();
                        signin.signIn();
                        Auth.clearConsole();
                        break;
                    case "2":
                        Auth.clearConsole();
                        Auth auth = new Auth("", 0);
                        auth.login(input);
                        Auth.clearConsole();
                        break;
                    case "3":
                        System.out.println("╔═══════════════════════════════════════╗");
                        System.out.println("║   Exiting the application. Goodbye!   ║");
                        System.out.println("╚═══════════════════════════════════════╝");
                        Auth.pause(input);
                        Auth.clearConsole();
                        exit = true;
                        break;
                    default:
                        System.out.println("╔═══════════════════════════════════════╗");
                        System.out.println("║        Invalid option selected.       ║");
                        System.out.println("╚═══════════════════════════════════════╝");
                        Auth.pause(input);
                        Auth.clearConsole();
                        break;
                }
                
            } while (!exit);
        }
    }
}