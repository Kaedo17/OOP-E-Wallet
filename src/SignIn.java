import java.util.Scanner;

public class SignIn {
  Banners signBanner = new Banners();
  private JsonGen jsonGen;
  
  public void signIn() {
    Scanner input = new Scanner(System.in);
    boolean isSignedIn = false;

    outer: while (!isSignedIn) {
      try {
        Auth.clearConsole();
        String inputUsername = "";
        String inputRealName = "";
        String inputPhone = "";
        String inputEmail = "";
        String inputBeneficiary = "";
        String inputAddress = "";
        String inputDob = "";
        String inputSex = "";
        int inputPass = 0;

        name: while (true) {
          Auth.clearConsole();
          signBanner.new SignInBanner().bannerShow();
          signBanner.new SignInBanner().bannerSingleOpt();
          System.out.print("Enter Full Name: ");
          inputRealName = input.nextLine().trim();

          if ("1".equals(inputRealName))
            return;
          if (inputRealName.isEmpty()) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Name cannot be empty. Please enter your full name.");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
            continue;
          }
          if (inputRealName.matches("[0-9-]+")) {
            System.out.println("O------------------------------------------------O");
            System.out.println("Real must contain only characters!");
            System.out.println("O------------------------------------------------O");
            Auth.pause(input);
            continue;
          }

          phone: while (true) {
            Auth.clearConsole();
            signBanner.new SignInBanner().bannerShow();
            signBanner.new SignInBanner().bannerDoubleOpt();
            System.out.print("Enter Phone Number: ");
            inputPhone = input.nextLine().trim();

            if ("1".equals(inputPhone))
              continue name;
            if ("2".equals(inputPhone))
              return;
            if (inputPhone.isEmpty()) {
              System.out.println("O------------------------------------------------O");
              System.out.println("Phone number cannot be empty.");
              System.out.println("O------------------------------------------------O");
              Auth.pause(input);
              continue;
            }

            email: while (true) {
              Auth.clearConsole();
              signBanner.new SignInBanner().bannerShow();
              signBanner.new SignInBanner().bannerDoubleOpt();
              System.out.print("Enter Email: ");
              inputEmail = input.nextLine().trim();

              if ("1".equals(inputEmail))
                continue phone;
              if ("2".equals(inputEmail))
                return;
              if (inputEmail.isEmpty()) {
                System.out.println("O------------------------------------------------O");
                System.out.println("Email cannot be empty.");
                System.out.println("O------------------------------------------------O");
                Auth.pause(input);
                continue;
              }

              beneficiary: while (true) {
                Auth.clearConsole();
                signBanner.new SignInBanner().bannerShow();
                signBanner.new SignInBanner().bannerDoubleOpt();
                System.out.print("Enter Beneficiary: ");
                inputBeneficiary = input.nextLine().trim();

                if ("1".equals(inputBeneficiary))
                  continue email;
                if ("2".equals(inputBeneficiary))
                  return;
                if (inputBeneficiary.isEmpty()) {
                  System.out.println("O------------------------------------------------O");
                  System.out.println("Beneficiary cannot be empty.");
                  System.out.println("O------------------------------------------------O");
                  Auth.pause(input);
                  continue;
                }

                address: while (true) {
                  Auth.clearConsole();
                  signBanner.new SignInBanner().bannerShow();
                  signBanner.new SignInBanner().bannerDoubleOpt();
                  System.out.print("Enter Address: ");
                  inputAddress = input.nextLine().trim();

                  if ("1".equals(inputAddress))
                    continue beneficiary;
                  if ("2".equals(inputAddress))
                    return;
                  if (inputAddress.isEmpty()) {
                    System.out.println("O------------------------------------------------O");
                    System.out.println("Address cannot be empty.");
                    System.out.println("O------------------------------------------------O");
                    Auth.pause(input);
                    continue;
                  }

                  dobLoop: while (true) {
                    Auth.clearConsole();
                    signBanner.new SignInBanner().bannerShow();
                    signBanner.new SignInBanner().bannerDoubleOpt();
                    System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                    inputDob = input.nextLine().trim();

                    if ("1".equals(inputDob))
                      continue address;
                    if ("2".equals(inputDob))
                      return;
                    if (inputDob.isEmpty()) {
                      System.out.println("O------------------------------------------------O");
                      System.out.println("Date of birth cannot be empty.");
                      System.out.println("O------------------------------------------------O");
                      Auth.pause(input);
                      continue;
                    }
                    if (!inputDob.matches("[0-9-]+")) {
                      System.out.println("O------------------------------------------------O");
                      System.out.println("Date of birth must contain only numbers and hyphens!");
                      System.out.println("O------------------------------------------------O");
                      Auth.pause(input);
                      continue;
                    }

                    sexLoop: while (true) {
                      Auth.clearConsole();
                      signBanner.new SignInBanner().bannerShow();
                      signBanner.new SignInBanner().bannerDoubleOpt();
                      System.out.print("Enter Sex (Male/Female): ");
                      inputSex = input.nextLine().trim();

                      if ("1".equals(inputSex))
                        continue dobLoop;
                      if ("2".equals(inputSex))
                        return;
                      if (inputSex.isEmpty()) {
                        System.out.println("O------------------------------------------------O");
                        System.out.println("Sex cannot be empty.");
                        System.out.println("O------------------------------------------------O");
                        Auth.pause(input);
                        continue;
                      }
                      if (!inputSex.equalsIgnoreCase("male") && !inputSex.equalsIgnoreCase("female")) {
                        System.out.println("O------------------------------------------------O");
                        System.out.println("Please enter either 'Male' or 'Female'.");
                        System.out.println("O------------------------------------------------O");
                        Auth.pause(input);
                        continue;
                      }

                      userNamePoint: while (true) {
                        Auth.clearConsole();
                        signBanner.new SignInBanner().bannerShow();
                        signBanner.new SignInBanner().bannerDoubleOpt();
                        System.out.print("Enter Username: ");
                        inputUsername = input.nextLine().trim();

                        if ("1".equals(inputUsername))
                          continue sexLoop;
                        if ("2".equals(inputUsername))
                          return;
                        if (inputUsername.isEmpty()) {
                          System.out.println("O------------------------------------------------O");
                          System.out.println("Username cannot be empty. Please enter a username.");
                          System.out.println("O------------------------------------------------O");
                          Auth.pause(input);
                          continue;
                        }

                        JsonGen checker = new JsonGen(inputRealName, inputUsername, 0);
                        if (checker.userChecker()) {
                          System.out.println("O----------------------------------------------------------O");
                          System.out.println("Username already exists. Please choose a different username.");
                          System.out.println("O----------------------------------------------------------O");
                          Auth.pause(input);
                          continue;
                        }

                        pinLoop: while (true) {
                          Auth.clearConsole();
                          signBanner.new SignInBanner().bannerShow();
                          signBanner.new SignInBanner().bannerDoubleOpt();

                          System.out.println("Note: 4 digit positive integers");
                          System.out.print("Enter Pin: ");

                          String pinStr = input.nextLine().trim();

                          if ("1".equals(pinStr))
                            continue userNamePoint;
                          if ("2".equals(pinStr))
                            return;

                          if (!pinStr.matches("\\d{4}")) {
                            System.out.println("O-------------------------------------------------O");
                            System.out.println("Error: PIN must be exactly 4 digits (numbers only)!");
                            System.out.println("O-------------------------------------------------O");
                            Auth.pause(input);
                            continue;
                          }

                          inputPass = Integer.parseInt(pinStr);

                          if (this.jsonGen == null) {
                            this.jsonGen = new JsonGen(inputRealName, inputUsername, inputPass, inputPhone, inputEmail,
                                inputBeneficiary, inputAddress, inputDob, inputSex);
                            this.jsonGen.updateJson();
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
                            this.jsonGen.updateJson();
                          }

                          System.out.println("O---------------------------------------O");
                          System.out.println("Sign in successful!");
                          System.out.println("O---------------------------------------O");
                          isSignedIn = true;
                          Auth.pause(input);
                          Auth.clearConsole();
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

      } catch (NumberFormatException e) {
        System.out.println("O---------------------------------------O");
        System.out.println("Unexpected error: " + e.getMessage());
        System.out.println("O---------------------------------------O");
        Auth.pause(input);
        Auth.clearConsole();
      }
    }
  }
}
