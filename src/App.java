import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        Player player = new Player(name);

        boolean playing = true;

        while (playing && player.getHealth() > 0) {

            Room room = new Room();
            System.out.println("\n--- New Room ---");
            System.out.println(room.describe());

            room.enter(player);

            System.out.println("\nContinue exploring? (yes/no)");
            String choice = scanner.nextLine();

            if (!choice.equalsIgnoreCase("yes")) {
                playing = false;
            }
        }

        System.out.println("\nGame Over.");
    }
}
