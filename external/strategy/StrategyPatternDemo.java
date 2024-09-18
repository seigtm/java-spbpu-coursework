import java.util.Scanner;

public final class StrategyPatternDemo {

    private static final String WALKING_OPTION = "1";
    private static final String HORSE_RIDING_OPTION = "2";
    private static final String FLYING_OPTION = "3";
    private static final String EXIT_OPTION = "4";

    // Helper method for printing menu.
    private static void printMenu() {
        System.out.println("\n- Choose a movement strategy: -");
        System.out.println("1. Walking");
        System.out.println("2. Horse riding");
        System.out.println("3. Flying");
        System.out.println("4. Exit");
    }

    public static void main(String[] args) {
        System.out.println("---- Strategy Pattern Demo ----");

        System.err.println("\n-- Null pointer exception demo: --");
        try {
            new Hero(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        System.err.println("\n-- Strategy selection demo: --");
        try (final var scanner = new Scanner(System.in)) {
            final var hero = new Hero(new WalkingStrategy());
            var shouldExit = false;

            // Main loop for user input and strategy selection.
            while (!shouldExit) {
                printMenu();

                // Handling user input with proper validation
                switch (scanner.nextLine()) {
                    case WALKING_OPTION -> hero.setMovementStrategy(new WalkingStrategy());
                    case HORSE_RIDING_OPTION -> hero.setMovementStrategy(new HorseRidingStrategy());
                    case FLYING_OPTION -> hero.setMovementStrategy(new FlyingStrategy());
                    case EXIT_OPTION -> {
                        shouldExit = true;
                        System.out.println("Exiting...");
                        continue;
                    }
                    default -> {
                        System.out.println("Invalid input. Please try again.");
                        continue;
                    }
                }

                System.out.println(hero.move());
            }
        }
    }
}
