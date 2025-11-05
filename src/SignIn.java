import java.util.Scanner;

public class SignIn {
  private String newUsrName;
  private int newPass;

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

    while (!isSignedIn) {
      try {
        String inputUsername;
        int inputPass = 0;

        System.out.println("Sign In");
        System.out.println();

        System.out.print("Enter Username: ");
        inputUsername = input.nextLine();

        System.out.println("Note: 4 digit positive integers");
        System.out.println("Enter Pin: ");
        

        boolean validPin = false;

        while (!validPin) {
          String pinStr = input.nextLine().trim();
          // Validate that the input is exactly 4 digits
          if (!pinStr.matches("\\d{4}")) {
            System.err.println("Error: PIN must be exactly 4 digits (numbers only)!");
            Auth.pause(input);
            Auth.clearConsole();
            System.out.println("Note: 4 digit positive integers");
            System.out.print("Enter pin again: ");
            
          } else {
            inputPass = Integer.parseInt(pinStr);
            validPin = true;
          } 
        }

        // After a valid PIN is entered, save and finish sign-in
        setNewUsername(inputUsername);
        setNewPass(inputPass);
        System.out.println("Sign in successful!");
        isSignedIn = true;
        Auth.pause(input);
        Auth.clearConsole();

      } catch (NumberFormatException e) {
        System.err.println("Unexpected error: " + e.getMessage());
        Auth.pause(input);
        Auth.clearConsole();
        // continue the loop and allow the user to try again
      }
    }
  }
}
