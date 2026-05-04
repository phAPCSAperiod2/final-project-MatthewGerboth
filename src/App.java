import java.util.Scanner;

/**
 * The main entry point for the dungeon crawler game.
 * This class handles player creation, the exploration loop,
 * and room generation. The game continues until the player
 * chooses to stop or their health reaches zero.
 */
public class App {

    /**
     * Starts the game, prompts the user for their name,
     * and runs the main exploration loop.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        // Create the player with the chosen name
        Player player = new Player(name);

        boolean playing = true;

        // Main game loop: continues until player quits or dies
        while (playing && player.getHealth() > 0) {

            // Generate a new room and display its description
            Room room = new Room();
            System.out.println("\n--- New Room ---");
            System.out.println(room.describe());

            // Trigger the room's event (combat, shop, heal, etc.)
            room.enter(player);

            // Ask the player if they want to continue
            System.out.println("\nContinue exploring? (yes/no)");
            String choice = scanner.nextLine();

            if (!choice.equalsIgnoreCase("yes")) {
                playing = false;
            }
        }

        System.out.println("\nGame Over.");
    }
}
