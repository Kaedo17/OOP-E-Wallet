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
    this.newPass = newPass;
  }

  public void signIn() {
    Scanner scanner = new Scanner(System.in);
    boolean isSignedIn = false;

    while(!isSignedIn) {
      try {
        String inputUsername;
        int inputPass;

        Auth myAuth = new Auth("",0);

        System.out.println("Sign In");
        System.out.println();

        System.out.print("Enter Username: ");
        inputUsername = scanner.nextLine();

      

        System.out.println("Entern Pin: ");
        System.out.print("Note: 4 digit positive integers");

        boolean validPin = false;

        while (!validPin) {
          try {
            if (inputPass > 9999 || inputPass < 0) {
              System.err.println("Please enter 4 digit positive integers!");
            } else {
              inputPass = Integer.parseInt(scanner.nextLine());
              validPin = true;

            }

          } catch (NumberFormatException e) {
            System.out.println("Error: Pin must be a number!");
            Auth.pause(scanner);
            Auth.clearConsole();
            System.out.println("Enter pin again: ");
            System.out.print("Note: 4 digit positive integers");
          }
        }


      } catch (Exception e) {
        // TODO: handle exception
      }
  }
}

