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

    System.out.println("Sign In");
    System.out.println();

    System.out.println("Enter Username");
    inputUsername = scanner.nextLine();
  }
}

