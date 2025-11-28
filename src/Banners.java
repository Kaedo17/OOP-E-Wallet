public class Banners {
    public static void printMainLogo() {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                        ║");
        System.out.println("║  ██████╗ ███╗   ██╗██╗     ██╗      ██████╗  █████╗ ███╗   ██╗██╗  ██╗ ║");
        System.out.println("║ ██╔═══██╗████╗  ██║██║     ██║      ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝ ║");
        System.out.println("║ ██║   ██║██╔██╗ ██║██║     ██║█████╗██████╔╝███████║██╔██╗ ██║█████╔╝  ║");
        System.out.println("║ ██║   ██║██║╚██╗██║██║     ██║╚════╝██╔══██╗██╔══██║██║╚██╗██║██╔═██╗  ║");
        System.out.println("║ ╚██████╔╝██║ ╚████║███████╗██║      ██████╔╝██║  ██║██║ ╚████║██║  ██╗ ║");
        System.out.println("║  ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ║");
        System.out.println("║                                                                        ║");
        System.out.println("║                  Your Trusted Digital Banking Partner                  ║");
        System.out.println("║                                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    public abstract class Banner {
        public abstract void bannerShow();

        public void bannerSingleOpt() {
            System.out.println("║              [1] Back                 ║");
            System.out.println("╚═══════════════════════════════════════╝");
        }

        public void bannerDoubleOpt() {
            System.out.println("║          [1] Back  [2] Exit           ║");
            System.out.println("╚═══════════════════════════════════════╝");
        }

    }

    public class LoginBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║              L O G I N                ║");
            System.out.println("╠═══════════════════════════════════════╣");
        }
    }

    public class SignInBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║             S I G N   I N             ║");
            System.out.println("╠═══════════════════════════════════════╣");
        }

    }

    public class BalanceBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║             B A L A N C E             ║");
            System.out.println("╠═══════════════════════════════════════╣");
        }

        public void balanceBannerOpts() {
            System.out.println("║  1. Refresh balance                   ║");
            System.out.println("║  2. Deposit                           ║");
            System.out.println("║  3. Send/Transfer                     ║");
            System.out.println("║  4. Transaction History               ║");
            System.out.println("║  5. Profile                           ║");
            System.out.println("║  6. Logout                            ║");
            System.out.println("╚═══════════════════════════════════════╝");
        }

    }

    public class DepositBanner extends Banner {
        @Override
        public void bannerShow() {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║             D E P O S I T             ║");
            System.out.println("╠═══════════════════════════════════════╣");
        }

        @Override
        public void bannerSingleOpt() {
            System.out.println("║              [:1] Back                ║");
            System.out.println("╚═══════════════════════════════════════╝");
        }
    }

    public class TransferBanner extends DepositBanner {
        @Override
        public void bannerShow() {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║            T R A N S F E R            ║");
            System.out.println("╠═══════════════════════════════════════╣");
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