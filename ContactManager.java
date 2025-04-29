import java.io.*;
import java.util.*;

public class ContactManager {

    static final String FILE_NAME = "contacts.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== CONTACT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Delete Contact");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact(sc);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    deleteContact(sc);
                    break;
                case 4:
                    System.out.println("Exiting... Bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addContact(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(name + "," + phone + "," + email);
            writer.newLine();
            System.out.println("Contact saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving contact: " + e.getMessage());
        }
    }

    static void viewContacts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No contacts found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 1;
            System.out.println("\nSaved Contacts:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(count++ + ". Name: " + parts[0] + " | Phone: " + parts[1] + " | Email: " + parts[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading contacts: " + e.getMessage());
        }
    }

    static void deleteContact(Scanner sc) {
        List<String> contacts = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No contacts to delete.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contacts.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading contacts: " + e.getMessage());
            return;
        }

        System.out.print("Enter the name of the contact to delete: ");
        String nameToDelete = sc.nextLine();
        boolean removed = contacts.removeIf(contact -> contact.split(",")[0].equalsIgnoreCase(nameToDelete));

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (String contact : contacts) {
                    writer.write(contact);
                    writer.newLine();
                }
                System.out.println("Contact deleted successfully!");
            } catch (IOException e) {
                System.out.println("Error writing contacts: " + e.getMessage());
            }
        } else {
            System.out.println("Contact not found.");
        }
    }
}





