import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
            try {
                switch (choice) {
                    case 1 -> LibraryManager.addBook();
                    case 2 -> LibraryManager.registerMember();
                    case 3 -> LibraryManager.issueBook();
                    case 4 -> LibraryManager.returnBook();
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
        sc.close();
    }
}
