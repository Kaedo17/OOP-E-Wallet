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
    String inputUsername;
    int inputPass;

    Auth myAuth = new Auth("",0);

    System.out.println("Sign In");
    System.out.println();

    System.out.println("Enter Username: ");
    inputUsername = scanner.nextLine();

    System.out.println("Entern Pin: ");
    System.out.println("Note: 4 digit positive integers");

    boolean validPin = false;

    while (!validPin) {
      try {
        inputPass = Integer.parseInt(scanner.nextLine());
        validPin = true;
      } catch (NumberFormatException e) {
        System.out.println("Error: Pin must be a number!");
        Auth.pause(scanner);
        Auth.clearConsole();
        System.out.println("Enter pin again: ");
        System.out.println("Note: 4 digit positive integers");
      }
    }
  }
}

