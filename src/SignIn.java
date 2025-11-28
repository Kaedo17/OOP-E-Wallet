import java.util.Scanner;

public class SignIn {
  Banners signBanner = new Banners();
  private JsonGen jsonGen;

  public void signIn() {
    Scanner input = new Scanner(System.in);

    String inputUsername = "";
    String inputRealName = "";
    String inputPhone = "";
    String inputEmail = "";
    String inputBeneficiary = "";
    String inputAddress = "";
    String inputDob = "";
    String inputSex = "";
    int inputPass = 0;

    int step = 0;

    while (true) {
      Auth.clearConsole();
      Banners.printMainLogo();
      signBanner.new SignInBanner().bannerShow();
      if (step == 0)
        signBanner.new SignInBanner().bannerSingleOpt();
      else
        signBanner.new SignInBanner().bannerDoubleOpt();

      switch (step) {
        case 0:
          System.out.print("Enter Full Name: ");
          inputRealName = input.nextLine().trim();
          if ("1".equals(inputRealName))
            return;
          if (inputRealName.isEmpty() || inputRealName.matches("[0-9-]+")) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Invalid name.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 1:
          System.out.print("Enter Phone Number: ");
          inputPhone = input.nextLine().trim();
          if ("1".equals(inputPhone))
            step--;
          else if ("2".equals(inputPhone))
            return;
          else if (inputPhone.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Phone cannot be empty.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else if (inputPhone.isEmpty() || !inputPhone.matches("[0-9-]+")) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Invalid date.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 2:
          System.out.print("Enter Email: ");
          inputEmail = input.nextLine().trim();
          if ("1".equals(inputEmail))
            step--;
          else if ("2".equals(inputEmail))
            return;
          else if (inputEmail.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Email cannot be empty.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 3:
          System.out.print("Enter Beneficiary: ");
          inputBeneficiary = input.nextLine().trim();
          if ("1".equals(inputBeneficiary))
            step--;
          else if ("2".equals(inputBeneficiary))
            return;
          else if (inputBeneficiary.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Beneficiary cannot be empty.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 4:
          System.out.print("Enter Address: ");
          inputAddress = input.nextLine().trim();
          if ("1".equals(inputAddress))
            step--;
          else if ("2".equals(inputAddress))
            return;
          else if (inputAddress.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Address cannot be empty.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 5:
          System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
          inputDob = input.nextLine().trim();
          if ("1".equals(inputDob))
            step--;
          else if ("2".equals(inputDob))
            return;
          else if (inputDob.isEmpty() || !inputDob.matches("[0-9-]+")) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Invalid date.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 6:
          System.out.print("Enter Sex (Male/Female): ");
          inputSex = input.nextLine().trim();
          if ("1".equals(inputSex))
            step--;
          else if ("2".equals(inputSex))
            return;
          else if (inputSex.isEmpty() ||
              (!inputSex.equalsIgnoreCase("male") && !inputSex.equalsIgnoreCase("female"))) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Invalid input.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else
            step++;
          break;

        case 7:
          System.out.print("Enter Username: ");
          inputUsername = input.nextLine().trim();
          if ("1".equals(inputUsername))
            step--;
          else if ("2".equals(inputUsername))
            return;
          else if (inputUsername.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Username cannot be empty.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
          } else {
            JsonGen checker = new JsonGen(inputRealName, inputUsername, 0);
            if (checker.userChecker()) {
              System.out.println("O----------------------------------------------------------O");
              System.out.println("Username already exists.");
              System.out.println("O----------------------------------------------------------O");
              Auth.pause(input);
            } else
              step++;
          }
          break;

        case 8:
          System.out.println("Note: 4 digit positive integers");
          System.out.print("Enter Pin: ");
          String pinStr = input.nextLine().trim();
          if ("1".equals(pinStr))
            step--;
          else if ("2".equals(pinStr))
            return;
          else if (!pinStr.matches("\\d{4}")) {
            System.out.println("O-------------------------------------------------O");
            System.out.println("PIN must be exactly 4 digits.");
            System.out.println("O-------------------------------------------------O");
            Auth.pause(input);
          } else {
            inputPass = Integer.parseInt(pinStr);
            if (this.jsonGen == null) {
              this.jsonGen = new JsonGen(inputRealName, inputUsername, inputPass, inputPhone, inputEmail,
                  inputBeneficiary, inputAddress, inputDob, inputSex);
            } else {
              this.jsonGen.setRealName(inputRealName);
              this.jsonGen.setUsername(inputUsername);
              this.jsonGen.setPin(inputPass);
              this.jsonGen.setPhone(inputPhone);
              this.jsonGen.setEmail(inputEmail);
              this.jsonGen.setBeneficiary(inputBeneficiary);
              this.jsonGen.setAddress(inputAddress);
              this.jsonGen.setDob(inputDob);
              this.jsonGen.setSex(inputSex);
            }
            this.jsonGen.updateJson();
            System.out.println("O---------------------------------------O");
            System.out.println("Sign in successful!");
            System.out.println("O---------------------------------------O");
            Auth.pause(input);
            Auth.clearConsole();
            return;
          }
          break;
      }
    }
  }
}
