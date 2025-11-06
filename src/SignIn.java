import java.util.Scanner;

public class SignIn {
  private String newUsrName;
  private int newPass;

  public class SignInBanner extends Auth.Banner {
    public static void signInBannerShow() {
      System.out.println("O---------------------------------------O");
      System.out.println("|             S I G N   I N             |");
      System.out.println("O---------------------------------------O");
    }

  }

  public SignIn(String newUsrName, int newPass) {
    setNewUsername(newUsrName);
    setNewPass(newPass);
  }

  public String getNewUsername() {
    return newUsrName;
  }

  public int getNewPass() {
    return newPass;
  }

  public final void setNewUsername(String newUsrName) {
    this.newUsrName = newUsrName;
  }

  public final void setNewPass(int newPass) {
    if (newPass < 0 || newPass > 9999) {
      throw new IllegalArgumentException("PIN must be a 4-digit number between 0000 and 9999");
    }
    this.newPass = newPass;
  }

  public void signIn() {
    Scanner input = new Scanner(System.in);
    boolean isSignedIn = false;

    outer: while (!isSignedIn) {
      try {
        Auth.clearConsole();
        String inputUsername;
        int inputPass = 0;
        boolean isValidUsername;

        do {
          SignInBanner.signInBannerShow();
          SignInBanner.bannerSingleOpt();
          System.out.println();

          System.out.print("Enter Username: ");
          inputUsername = input.nextLine().trim();

          if ("1".equals(inputUsername)) {
            return;
          }else if (inputUsername.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.err.println("Username cannot be empty. Please enter a username.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
            continue outer; // re-prompt username
          } else {
            isValidUsername = true;
          }

          
        } while (!isValidUsername);

        boolean validPin = false;

        do {
          Auth.clearConsole();
          SignInBanner.signInBannerShow();
          SignInBanner.bannerDoubleOpt();

          System.out.println("Note: 4 digit positive integers");
          System.out.print("Enter Pin: ");

          String pinStr = input.nextLine().trim();

          if ("1".equals(pinStr)) {
            // Go back to username prompt without recursion
            continue outer;
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
            validPin = true;
          }
        } while (!validPin);

        // After a valid PIN is entered, save and finish sign-in
        setNewUsername(inputUsername);
        setNewPass(inputPass);
        System.out.println("O---------------------------------------O");
        System.out.println("Sign in successful!");
        System.out.println("O---------------------------------------O");
        isSignedIn = true;
        Auth.pause(input);
        Auth.clearConsole();

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
