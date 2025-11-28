import java.util.Scanner;

public class CardManager {
    private String username;
    private Banners banners = new Banners();

    public CardManager(String username) {
        this.username = username;
    }

    public void showCardManagement(Scanner input) {
        boolean backToDashboard = false;

        while (!backToDashboard) {
            Auth.clearConsole();
            banners.new CardManagementBanner().bannerShow();

            CreditCard card = CreditCard.loadFromFile(username);

            if (card == null) {
                System.out.println("║           No Credit Card Found        ║");
                System.out.println("╠═══════════════════════════════════════╣");
                System.out.println("║  1. Generate New Credit Card          ║");
                System.out.println("║  2. Back to Dashboard                 ║");
                System.out.println("╚═══════════════════════════════════════╝");
            } else {
                displayCardInfo(card);
                System.out.println("╠═══════════════════════════════════════╣");
                System.out.println("║  1. Make Payment                      ║");
                System.out.println("║  2. Make Purchase                     ║");
                System.out.println("║  3. Disable Generate New Card         ║");
                System.out.println("║  4. Back to Dashboard                 ║");
                System.out.println("╚═══════════════════════════════════════╝");
            }

            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    if (card == null) {
                        generateNewCard(input);
                    } else {
                        makePayment(input, card);
                    }
                    break;
                case "2":
                    if (card == null) {
                        backToDashboard = true;
                    } else {
                        makePurchase(input, card);
                    }
                    break;
                case "3":
                    if (card != null) {
                        generateNewCard(input);
                    } else {
                        backToDashboard = true;
                    }
                    break;
                case "4":
                    backToDashboard = true;
                    break;
                default:
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║            Invalid option!            ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    Auth.pause(input);
                    break;
            }
        }
    }

    private void displayCardInfo(CreditCard card) {
        System.out.println("  Card Number: " + String.format("%-25s", card.getCardNumber()));
        System.out.println("  Expiry Date: " + String.format("%-25s", card.getExpiryDate()));
        System.out.println("  CVV: " + String.format("%-31s", card.getCvv()));
        System.out.println("  Credit Limit: ₱" + String.format("%-24.2f", card.getCreditLimit()));
        System.out.println("  Available Credit: ₱" + String.format("%-20.2f", card.getAvailableCredit()));
        System.out.println("  Current Balance: ₱" + String.format("%-21.2f", card.getCurrentBalance()));
    }

    private void generateNewCard(Scanner input) {
        Auth.clearConsole();
        banners.new CardManagementBanner().bannerShow();
        
        System.out.println("║      Generating new credit card...    ║");
        System.out.println("╠═══════════════════════════════════════╣");

        CreditCard newCard = new CreditCard(username);
        newCard.saveToFile();

        System.out.println("║    New card generated successfully!   ║");
        System.out.println("╠═══════════════════════════════════════╣");
        displayCardInfo(newCard);
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║     Keep your card details secure!    ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        Auth.pause(input);
    }

    private void makePayment(Scanner input, CreditCard card) {
        boolean backToCardMenu = false;
        
        while (!backToCardMenu) {
            Auth.clearConsole();
            banners.new CardManagementBanner().bannerShow();
            displayCardInfo(card);
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║              MAKE PAYMENT             ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ Enter amount to pay ([:1] Back):      ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Amount: ₱");

            String amountInput = input.nextLine().trim();
            
            if (":1".equalsIgnoreCase(amountInput)) {
                backToCardMenu = true;
                continue;
            }

            try {
                double amount = Double.parseDouble(amountInput);
                if (card.makePayment(amount)) {
                    card.saveToFile();
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║         Payment successful!           ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    
                    // Record transaction
                    TransacHistory history = new TransacHistory(String.valueOf(amount), username, "credit card payment");
                    history.addTransac();
                    Auth.pause(input);
                    backToCardMenu = true;
                } else {
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║        Invalid payment amount!        ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    Auth.pause(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║         Invalid amount format!        ║");
                System.out.println("╚═══════════════════════════════════════╝");
                Auth.pause(input);
            }
        }
    }

    private void makePurchase(Scanner input, CreditCard card) {
        boolean backToCardMenu = false;
        
        while (!backToCardMenu) {
            Auth.clearConsole();
            banners.new CardManagementBanner().bannerShow();
            displayCardInfo(card);
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║              MAKE PURCHASE            ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ Enter purchase amount (or 'back'):    ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Amount: ₱");

            String amountInput = input.nextLine().trim();
            
            if (":1".equalsIgnoreCase(amountInput)) {
                backToCardMenu = true;
                continue;
            }

            try {
                double amount = Double.parseDouble(amountInput);
                if (card.makePurchase(amount)) {
                    card.saveToFile();
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║         Purchase successful!          ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    
                    // Record transaction
                    TransacHistory history = new TransacHistory(String.valueOf(-amount), username, "credit card purchase");
                    history.addTransac();
                    Auth.pause(input);
                    backToCardMenu = true;
                } else {
                    System.out.println("╔═══════════════════════════════════════╗");
                    System.out.println("║ Insufficient credit or invalid amount ║");
                    System.out.println("╚═══════════════════════════════════════╝");
                    Auth.pause(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("║         Invalid amount format!        ║");
                System.out.println("╚═══════════════════════════════════════╝");
                Auth.pause(input);
            }
        }
    }
}