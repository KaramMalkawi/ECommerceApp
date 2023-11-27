package eCommerceApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Product> productDatabase = new HashMap<>();
    private static List<User> users = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeProducts();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View Products\n2. Add to Cart\n3. View Cart\n4. Checkout\n5. Login\n6. Create Account\n7. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    checkout(scanner);
                    break;
                case 5:
                    login(scanner);
                    break;
                case 6:
                	createUser(scanner);
                	break;
                case 7:
                	return;
                    //System.exit(0);
            }
        }
    }

    private static void initializeProducts() {
        productDatabase.put("1", new Product("HP", "High-performance laptop", 999.99, "laptop_image.jpg"));
        productDatabase.put("2", new Product("Dill", "High-performance laptop", 499.99, "laptop_image.jpg"));
        productDatabase.put("3", new Product("Lenovo", "High-performance laptop", 699.99, "laptop_image.jpg"));
        productDatabase.put("4", new Product("IOS", "High-performance laptop", 799.99, "laptop_image.jpg"));
    }

    private static void viewProducts() {
        System.out.println("Product Catalog:");
        for (Map.Entry<String, Product> entry : productDatabase.entrySet()) {
            Product product = entry.getValue();
            System.out.println(
                    entry.getKey() + ". " + product.getName() + " - " + product.getDescription() + " - $" + product.getPrice());
        }
        System.out.println("");
    }

    private static void addToCart(Scanner scanner) {
        viewProducts();
        System.out.println("Enter the product number to add to cart:");
        String productNumber = scanner.next();
        Product selectedProduct = productDatabase.get(productNumber);

        if (selectedProduct != null) {
            System.out.println("Enter the quantity:");
            int quantity = scanner.nextInt();

            if (quantity > 0) {
                if (currentUser == null) {
                    System.out.println("Please login first.\n");
                } else {
                    currentUser.addOrder(new Order(new ShoppingCart(), ""));
                    currentUser.getOrders().get(currentUser.getOrders().size() - 1).getCart().addToCart(selectedProduct, quantity);
                    System.out.println("Product added to cart successfully.\n");
                }
            } else {
                System.out.println("Invalid quantity.\n");
            }
        } else {
            System.out.println("Invalid product number.\n");
        }
    }

    private static void viewCart() {
        if (currentUser != null && !currentUser.getOrders().isEmpty()) {
            ShoppingCart cart = currentUser.getOrders().get(currentUser.getOrders().size() - 1).getCart();
            Map<Product, Integer> items = cart.getItems();

            System.out.println("Shopping Cart:");
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(
                        product.getName() + " - $" + product.getPrice() + " - Quantity: " + quantity);
            }

            System.out.println("Total: $" + cart.calculateTotal() + "\n");
        } else {
            System.out.println("Your cart is empty.\n");
        }
    }

    private static void checkout(Scanner scanner) {
        if (currentUser != null && !currentUser.getOrders().isEmpty()) {
            ShoppingCart cart = currentUser.getOrders().get(currentUser.getOrders().size() - 1).getCart();

            System.out.println("Enter shipping information:");
            String shippingInfo = scanner.next();

            Order order = new Order(cart, shippingInfo);
            currentUser.getOrders().add(order);

            System.out.println("Order placed successfully.\n");
        } else {
            System.out.println("Your cart is empty.\n");
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.next();
        System.out.println("Enter your password:");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful.\n");
                return;
            }
        }

        System.out.println("Invalid username or password.\n");
    }
    
    private static void createUser(Scanner scanner) {
        System.out.println("Enter a new username:");
        String newUsername = scanner.next();
        System.out.println("Enter a password:");
        String newPassword = scanner.next();

        User newUser = new User(newUsername, newPassword);
        users.add(newUser);

        System.out.println("User account created successfully.\n");
    }

}
