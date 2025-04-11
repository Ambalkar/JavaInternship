import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Item {
    String name;
    double price;
    int quantity;

    Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotal() {
        return price * quantity;
    }
}

public class ReceiptCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Item> items = new ArrayList<>();
        final double TAX_RATE = 0.08;     // 8% tax
        final double DISCOUNT_RATE = 0.10; // 10% discount

        System.out.println("Welcome to the Receipt Calculator!");
        while (true) {
            System.out.print("Enter item name (or type 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Enter price of " + name + ": Rs");
            double price = scanner.nextDouble();

            System.out.print("Enter quantity of " + name + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // consume newline

            items.add(new Item(name, price, quantity));
        }

        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.getTotal();
        }

        double tax = subtotal * TAX_RATE;
        double discount = subtotal * DISCOUNT_RATE;
        double total = subtotal + tax - discount;

        StringBuilder receipt = new StringBuilder();
        receipt.append("********** RECEIPT **********\n");
        for (Item item : items) {
            receipt.append(item.name)
                    .append(" x")
                    .append(item.quantity)
                    .append(" - Rs")
                    .append(String.format("%.2f", item.getTotal()))
                    .append("\n");
        }

        receipt.append("----------------------------\n");
        receipt.append("Subtotal: Rs").append(String.format("%.2f", subtotal)).append("\n");
        receipt.append("Tax (8%): Rs").append(String.format("%.2f", tax)).append("\n");
        receipt.append("Discount (10%): -Rs").append(String.format("%.2f", discount)).append("\n");
        receipt.append("Total: Rs").append(String.format("%.2f", total)).append("\n");
        receipt.append("*****************************\n");

        System.out.println("\n" + receipt);

        // Save to file
        try {
            FileWriter writer = new FileWriter("Receipt.txt");
            writer.write(receipt.toString());
            writer.close();
            System.out.println("Receipt saved as 'Receipt.txt'");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the receipt.");
        }

        scanner.close();
    }
}
