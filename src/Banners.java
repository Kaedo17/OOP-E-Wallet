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
            System.out.println("4. Logout");
            System.out.println("O---------------------------------------O");
        }
    }

    public void showLoginBanner() {
        new LoginBanner().bannerShow();
    }

    public void showSignInBanner() {
        new SignInBanner().bannerShow();
    }

    public void showBalanceBanner() {
        new BalanceBanner().bannerShow();
    }

    public Banner getLoginBanner() {
        return new LoginBanner();
    }

    public Banner getSignInBanner() {
        return new SignInBanner();
    }

    public Banner getBalanceBanner() {
        return new BalanceBanner();
    }
}
