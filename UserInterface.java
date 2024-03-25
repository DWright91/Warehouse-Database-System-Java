import java.io.*;
import java.util.*;

// A class representing the user interface for managing the warehouse.
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_CLIENT = 2;
    private static final int SHOW_PRODUCTS = 3;
    private static final int SHOW_CLIENTS = 4;
    private static final int SAVE = 5;
    private static final int RETRIEVE = 6;
    private static final int HELP = 7;
    private static final int ADD_TO_WISHLIST = 8;
    private static final int REMOVE_FROM_WISHLIST = 9;
    private static final int ADD_TO_WAITLIST = 10;
    private static final int REMOVE_FROM_WAITLIST = 11;
    private static final int SHOW_WISHLIST = 12;
    private static final int SHOW_WAITLIST = 13;
    private static final int PROCESS_ORDER = 14;
    private static final int SHOW_INVOICES = 15;
    private static final int SHOW_CLIENT_INVOICE = 16;
    private static final int SUPPLY_PRODUCT = 17;

    private UserInterface() {
        if (yesOrNo("Look for saved data and use it?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }

    // Singleton method to get or create an instance of the UserInterface.
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }

    // Method to get a token (user input) with a prompt.
    public String getToken(String prompt) {
        String line = null;
        do {
            try {
                System.out.println(prompt);
                line = reader.readLine();
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (line == null);

        return line.trim(); // Trim whitespace
    }

    // Method to get a yes or no answer from the user.
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        return (more.charAt(0) == 'y' || more.charAt(0) == 'Y');
    }

    // Method to get a string from the user.
    public String getString(String prompt) {
        do {
            try {
                System.out.println(prompt);
                return reader.readLine().trim(); // Read and trim whitespace
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    // Method to show the products in the wishlist.
    public void showWishlist() {
        System.out.println("Wishlist:");

        Iterator<Client> allClients = warehouse.getClients();
        while (allClients.hasNext()) {
            Client client = allClients.next();
            Wishlist wishlist = client.getWishlist();

            if (!wishlist.getProducts().isEmpty()) {
                System.out.println("Client: " + client.getClientName());
                System.out.println("Products in Wishlist:");

                for (Product product : wishlist.getProducts()) {
                    double totalPrice = wishlist.getProductQuantity(product.getProductId()) * product.getPrice();

                    System.out.println("  Product ID: " + product.getProductId());
                    System.out.println("  Product Name: " + product.getProductName());
                    System.out.println("  Quantity: " + wishlist.getProductQuantity(product.getProductId()));
                    System.out.println("  Product Price: " + product.getPrice());
                    System.out.println("  Total Amount: " + totalPrice);
                    System.out.println();
                }
            }
        }
    }

    // Method to show the products in the waitlist.
    public void showWaitlist() {
        System.out.println("Waitlist:");

        Iterator<Product> allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = allProducts.next();
            Waitlist waitlist = product.getWaitlist();

            if (!waitlist.getClients().isEmpty()) {
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Product Name: " + product.getProductName());

                System.out.println("Clients in Waitlist:");

                for (Client client : waitlist.getClients()) {
                    System.out.println("  Client ID: " + client.getClientId());
                    System.out.println("  Client Name: " + client.getClientName());
                    System.out.println("  Client Address: " + client.getAddress());
                    System.out.println("  Client Phone: " + client.getPhone());
                    System.out.println("  Product Quantity: " + waitlist.getClientQuantity(client));
                    System.out.println();
                }
            }
        }
    }

    // Method to set the client's balance.
    public void setClientBalance() {
        String clientId = getString("Enter client ID");
        double newBalance = Double.parseDouble(getToken("Enter new balance"));

        Client client = warehouse.getClientById(clientId);

        if (client != null) {
            client.setBalance(newBalance);
            System.out.println("Balance updated successfully.");
            System.out.println("New Balance: $" + newBalance);
        } else {
            System.out.println("Client not found.");
        }
    }

    // Method to get the client's balance.
    public void getClientBalance() {
        String clientId = getString("Enter client ID");

        Client client = warehouse.getClientById(clientId);

        if (client != null) {
            double balance = client.getBalance();
            System.out.println("Client Balance: $" + balance);
        } else {
            System.out.println("Client not found.");
        }
    }

    // Method to get a number from the user.
    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer num = Integer.valueOf(item);
                return num;
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number");
            }
        } while (true);
    }

    // Method to get a valid command from the user.
    public String getCommand() {
        do {
            try {
                String input = getToken("Enter command:" + HELP + " for help").toLowerCase();
                if (isValidCommand(input)) {
                    return input;
                } else {
                    System.out.println("Invalid command. Enter a valid command.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a valid command.");
            }
        } while (true);
    }

    // Method to validate if a command is valid.
    private boolean isValidCommand(String command) {
        switch (command) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
                return true;
            default:
                return false;
        }
    }

    // Method to display the available commands.
    public void help() {
        System.out.println("Enter a number between 0 and 16 as explained below:");
        System.out.println(EXIT + " to Exit");
        System.out.println(ADD_PRODUCT + " to add a product");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(SHOW_PRODUCTS + " to show products");
        System.out.println(SHOW_CLIENTS + " to show clients");
        System.out.println(SAVE + " to save data");
        System.out.println(RETRIEVE + " to retrieve");
        System.out.println(HELP + " for help");
        System.out.println(ADD_TO_WISHLIST + " to add a product to wishlist");
        System.out.println(REMOVE_FROM_WISHLIST + " to remove a product from wishlist");
        System.out.println(ADD_TO_WAITLIST + " to add a client to waitlist");
        System.out.println(REMOVE_FROM_WAITLIST + " to remove a client from waitlist");
        System.out.println(SHOW_WISHLIST + " to show wishlist");
        System.out.println(SHOW_WAITLIST + " to show waitlist");
        System.out.println(PROCESS_ORDER + " to process an order");
        System.out.println(SHOW_INVOICES + " to show invoices");
        System.out.println(SHOW_CLIENT_INVOICE + " to show client invoice");
        System.out.println(SUPPLY_PRODUCT + " to supply product");
    }

    // Method to add a product to the warehouse.
    public void addProduct() {
        String productName = getToken("Enter product name");
        double price = Double.parseDouble(getToken("Enter product price"));
        int quantity = Integer.parseInt(getToken("Enter product quantity")); // New line for quantity input

        Product product = warehouse.addProduct(productName, price, quantity); // Update the method call
        if (product != null) {
            System.out.println("Product added successfully:");
            System.out.println(product);
        } else {
            System.out.println("Product could not be added.");
        }
    }

    // Method to add a client to the warehouse.
    public void addClient() {
        String clientName = getToken("Enter client name");
        String address = getToken("Enter address");
        String phone = getToken("Enter phone");

        Client client = warehouse.addClient(clientName, address, phone);
        if (client != null) {
            System.out.println("Client added successfully:");
            System.out.println(client);
        } else {
            System.out.println("Client could not be added.");
        }
    }

    // Method to show all products in the warehouse.
    public void showProducts() {
        Iterator<Product> allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = allProducts.next();
            System.out.println(product.toString());
        }
    }

    // Method to show all clients in the warehouse.
    public void showClients() {
        Iterator<Client> allClients = warehouse.getClients();
        while (allClients.hasNext()) {
            Client client = allClients.next();
            System.out.println(client.toString());
        }
    }

    // Method to save warehouse data to a file.
    private void save() {
        if (Warehouse.save()) {
            System.out.println("The warehouse data has been successfully saved in the file WarehouseData");
        } else {
            System.out.println("There has been an error in saving the warehouse data");
        }
    }

    // Method to retrieve warehouse data from a file.
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println("The warehouse data has been successfully retrieved from the file WarehouseData");
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesn't exist; creating a new warehouse");
                warehouse = Warehouse.instance();
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    public void addProductToWishlist() {
        String clientId = getString("Enter client ID");
        String productId = getString("Enter product ID");
        int quantity = getInt("Enter quantity");

        Client client = warehouse.getClientById(clientId);
        Product product = warehouse.getProductById(productId);

        if (client == null || product == null) {
            System.out.println("Client or product not found.");
            return;
        }

        int result = warehouse.addProductToWishlist(client, product, quantity);
        switch (result) {
            case Warehouse.ADD_PRODUCT_TO_WISHLIST_SUCCESS:
                System.out.println("Product added to the wishlist successfully.");
                break;
            case Warehouse.WISHLIST_PRODUCT_ALREADY_EXISTS:
                System.out.println("Product is already in the wishlist.");
                break;
            case Warehouse.CLIENT_ALREADY_IN_WAITLIST:
                System.out.println("Client is already in the waitlist for this product.");
                break;
            default:
                System.out.println("Failed to add the product to the wishlist.");
                break;
        }
    }

    private int getInt(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method to remove a product from a client's wishlist.
    public void removeProductFromWishlist() {
        String clientId = getString("Enter client ID");
        String productId = getString("Enter product ID");

        Client client = warehouse.getClientById(clientId);
        Product product = warehouse.getProductById(productId);

        if (client == null || product == null) {
            System.out.println("Client or product not found.");
            return;
        }

        int result = warehouse.removeProductFromWishlist(client, product);
        switch (result) {
            case Warehouse.REMOVE_PRODUCT_FROM_WISHLIST_SUCCESS:
                System.out.println("Product removed from the wishlist successfully.");
                break;
            case Warehouse.WISHLIST_PRODUCT_NOT_FOUND:
                System.out.println("Product not found in the wishlist.");
                break;
            default:
                System.out.println("Failed to remove the product from the wishlist.");
                break;
        }
    }

    // Method to add a client to a product's waitlist.
    public void addClientToWaitlist() {
        String clientId = getString("Enter client ID");
        String productId = getString("Enter product ID");
        int quantity = getInt("Enter quantity");

        Client client = warehouse.getClientById(clientId);
        Product product = warehouse.getProductById(productId);

        if (client == null || product == null) {
            System.out.println("Client or product not found.");
            return;
        }

        int result = warehouse.addClientToWaitlist(client, product, quantity);
        switch (result) {
            case Warehouse.ADD_CLIENT_TO_WAITLIST_SUCCESS:
                System.out.println("Client added to the waitlist for the product.");
                break;
            case Warehouse.CLIENT_ALREADY_IN_WAITLIST:
                System.out.println("Client is already in the waitlist for the product.");
                break;
            default:
                System.out.println("Failed to add the client to the waitlist.");
                break;
        }
    }

    // Method to remove a client from a product's waitlist.
    public void removeClientFromWaitlist() {
        String clientId = getString("Enter client ID");
        String productId = getString("Enter product ID");

        Client client = warehouse.getClientById(clientId);
        Product product = warehouse.getProductById(productId);

        if (client == null || product == null) {
            System.out.println("Client or product not found.");
            return;
        }

        int result = warehouse.removeClientFromWaitlist(client, product);
        switch (result) {
            case Warehouse.REMOVE_CLIENT_FROM_WAITLIST_SUCCESS:
                System.out.println("Client removed from the waitlist for the product.");
                break;
            case Warehouse.CLIENT_NOT_FOUND_IN_WAITLIST:
                System.out.println("Client not found in the waitlist for the product.");
                break;
            default:
                System.out.println("Failed to remove the client from the waitlist.");
                break;
        }
    }

    // Method to process an order for a client.
    public void processOrder() {
        String clientId = getString("Enter client ID");
        Client client = warehouse.getClientById(clientId);

        if (client != null) {
            warehouse.processOrder(client);
        } else {
            System.out.println("Client not found.");
        }
    }

    // Method to show all invoices.
    public void showInvoices() {
        Iterator<Invoice> allInvoices = warehouse.getInvoices();
        while (allInvoices.hasNext()) {
            Invoice invoice = allInvoices.next();
            System.out.println(invoice);
        }
    }

    // Method to show the invoice for a specific client.
    public void showClientInvoice() {
        String clientId = getString("Enter client ID");
        Client client = warehouse.getClientById(clientId);

        if (client != null) {
            List<Invoice> clientInvoices = warehouse.getInvoicesForClient(client.getClientId());
            if (!clientInvoices.isEmpty()) {
                System.out.println("Invoices for " + client.getClientName() + ":");
                for (Invoice invoice : clientInvoices) {
                    System.out.println(invoice);
                }
            } else {
                System.out.println("No invoices found for " + client.getClientName());
            }
        } else {
            System.out.println("Client not found.");
        }
    }

    public void supplyProductsInWarehouse() {
        Iterator<Product> productIterator = warehouse.getProducts();
        List<Product> products = new ArrayList<>();

        // Convert the Iterator to a List
        while (productIterator.hasNext()) {
            products.add(productIterator.next());
        }

        if (products.isEmpty()) {
            System.out.println("No products available in the warehouse.");
            return;
        }

        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductId() + " | " + product.getProductName()
                    + " | Quantity: " + product.getQuantity());
        }

        String productId = getToken("Enter the Product ID to supply: ");

        Product selectedProduct = null;
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Invalid Product ID. No product found.");
            return;
        }

        int quantityToAdd = getNumber("Enter the quantity to add:");

        warehouse.supplyProducts(selectedProduct, quantityToAdd); // Supply the product

    }

    // Method to handle user interactions and process commands.
    public void process() {
        String command;
        help();
        while (!(command = getCommand()).equals("0")) {
            switch (command) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    addClient();
                    break;
                case "3":
                    showProducts();
                    break;
                case "4":
                    showClients();
                    break;
                case "5":
                    save();
                    break;
                case "6":
                    retrieve();
                    break;
                case "7":
                    help();
                    break;
                case "8":
                    addProductToWishlist();
                    break;
                case "9":
                    removeProductFromWishlist();
                    break;
                case "10":
                    addClientToWaitlist();
                    break;
                case "11":
                    removeClientFromWaitlist();
                    break;
                case "12":
                    showWishlist();
                    break;
                case "13":
                    showWaitlist();
                    break;
                case "14":
                    processOrder();
                    break;
                case "15":
                    showInvoices();
                    break;
                case "16":
                    showClientInvoice();
                    break;
                case "17":
                    supplyProductsInWarehouse();
                    break;
                default:
                    System.out.println("Invalid command. Enter a valid command.");
            }
        }
    }

    // Main method to start the user interface.
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
}