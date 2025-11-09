import java.util.Scanner;

public class SignIn {
  Banners signBanner = new Banners();
  private JsonGen jsonGen;

  public final void setNewPass(int newPass) {
    if (newPass < 0 || newPass > 9999) {
      throw new IllegalArgumentException("PIN must be a 4-digit number between 0000 and 9999");
    }
  }

  public void signIn() {
    Scanner input = new Scanner(System.in);
    boolean isSignedIn = false;

    outer: while (!isSignedIn) {
      try {
        Auth.clearConsole();
        String inputUsername;
        String inputRealName;
        int inputPass = 0;

        System.out.println();
        signBanner.new SignInBanner().bannerShow();
        signBanner.new SignInBanner().bannerSingleOpt();
        System.out.print("Enter Full Name: ");
        inputRealName = input.nextLine().trim();

        if ("1".equals(inputRealName)) {
          return;
        } else if (inputRealName.isEmpty()) {
          System.out.println("O------------------------------------------------O");
          System.err.println("Username cannot be empty. Please enter a username.");
          System.out.println("O------------------------------------------------O");
          Auth.pause(input);
          Auth.clearConsole();
        }

        userNamePoint: while (true) {

          while (true) {
            Auth.clearConsole();
            signBanner.new SignInBanner().bannerShow();
            signBanner.new SignInBanner().bannerDoubleOpt();
            System.out.print("Enter Username: ");
            inputUsername = input.nextLine().trim();

            JsonGen checker = new JsonGen(inputRealName, inputUsername, 0);

            if ("1".equals(inputUsername)) {
              continue outer;
            } else if (inputUsername.isEmpty()) {
              System.out.println("O------------------------------------------------O");
              System.err.println("Username cannot be empty. Please enter a username.");
              System.out.println("O------------------------------------------------O");
              Auth.pause(input);
              continue outer; // re-prompt username
            } else if (checker.userChecker()) {
              System.out.println("O----------------------------------------------------------O");
              System.err.println("Username already exists. Please choose a different username.");
              System.out.println("O----------------------------------------------------------O");
              Auth.pause(input);
              continue userNamePoint; // re-prompt username
            } else if ("2".equals(inputUsername)) {
              return;
            } else {
              break;
            }
          }

          while (true) {
            Auth.clearConsole();
            signBanner.new SignInBanner().bannerShow();
            signBanner.new SignInBanner().bannerDoubleOpt();

            System.out.println("Note: 4 digit positive integers");
            System.out.print("Enter Pin: ");

            String pinStr = input.nextLine().trim();

            if ("1".equals(pinStr)) {
              // Go back to username prompt without recursion
              continue userNamePoint;
            } else if ("2".equals(pinStr)) {
              return;
            }
            // Validate that the input is exactly 4 digits
            if (!pinStr.matches("\\d{4}")) {
              System.out.println("O-------------------------------------------------O");
              System.err.println("Error: PIN must be exactly 4 digits (numbers only)!");
              System.out.println("O-------------------------------------------------O");
              Auth.pause(input);
              Auth.clearConsole();
              System.out.println("Note: 4 digit positive integers");
              System.out.print("Enter pin again: ");

            } else {
              inputPass = Integer.parseInt(pinStr);
              break;
            }
          }

          // Persist new user to JSON using a single JsonGen instance
          if (this.jsonGen == null) {
            this.jsonGen = new JsonGen(inputRealName, inputUsername, inputPass);
            // persist first user
            this.jsonGen.updateJson();
          } else {
            this.jsonGen.setRealName(inputRealName);
            this.jsonGen.setUsername(inputUsername);
            this.jsonGen.setPin(inputPass);
            this.jsonGen.updateJson();
          }

          System.out.println("O---------------------------------------O");
          System.out.println("Sign in successful!");
          System.out.println("O---------------------------------------O");
          isSignedIn = true;
          Auth.pause(input);
          Auth.clearConsole();
          break;
        }

      } catch (NumberFormatException e) {
        System.out.println("O---------------------------------------O");
        System.err.println("Unexpected error: " + e.getMessage());
        System.out.println("O---------------------------------------O");
        Auth.pause(input);
        Auth.clearConsole();
        // continue the loop and allow the user to try again
      }
    }
  }
}
