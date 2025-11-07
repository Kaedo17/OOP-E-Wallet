import java.util.Scanner;

public class Dashboard {

    Banners balanceBanner = new Banners();

    /**
     * Interactive dashboard loop. Returns when the user chooses to logout/exit.
     */
    public void displayDashboard(Scanner input) {
        boolean logout = false;
        while (!logout) {
            Auth.clearConsole();
            balanceBanner.new BalanceBanner().bannerShow();
            System.out.println("1. Refresh balance");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();
            if ("1".equals(choice)) {
                
            } else if ("2".equals(choice)) {
                logout = true;
            } else {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid option.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
            }

        }
        Auth.clearConsole();
    }
}
