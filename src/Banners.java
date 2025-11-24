public class Banners {
    public abstract class Banner {
        public abstract void bannerShow();

        public void bannerSingleOpt() {
            System.out.println("|               [1] Back                |");
            System.out.println("O---------------------------------------O");
        }

        public void bannerDoubleOpt() {
            System.out.println("|           [1] Back [2] Exit           |");
            System.out.println("O---------------------------------------O");
        }

    }

    public class LoginBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|              L O G I N                |");
            System.out.println("O---------------------------------------O");
        }
    }

    public class SignInBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|             S I G N   I N             |");
            System.out.println("O---------------------------------------O");
        }

    }

    public class BalanceBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|             B A L A N C E             |");
            System.out.println("O---------------------------------------O");

        }

        public void balanceBannerOpts() {
            System.out.println("1. Refresh balance");
            System.out.println("2. Deposit");
            System.out.println("3. Send/Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.println("O---------------------------------------O");
        }

    }

    public class DepositBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|             D E P O S I T             |");
            System.out.println("O---------------------------------------O");
        }

        @Override
        public void bannerSingleOpt() {
            System.out.println("|               [:1] Back               |");
            System.out.println("O---------------------------------------O");
        }
    }

    public class TransferBanner extends DepositBanner {
        @Override
        public void bannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|            T R A N S F E R            |");
            System.out.println("O---------------------------------------O");
        }

        
    }

    public Banner getLoginBanner() {
        return new LoginBanner();
    }

    public Banner getSignInBanner() {
        return new SignInBanner();
    }

    public BalanceBanner getBalanceBanner() {
        return new BalanceBanner();
    }

    public Banner getDepositBanner() {
        return new DepositBanner();
    }

    public Banner getTransferBanner() {
        return new TransferBanner();
    }
}