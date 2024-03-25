import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import java.io.*;

// A class representing a Warehouse that manages products, clients, and wishlists.
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ProductCatalog productCatalog; // Catalog of products.
    private ClientList clientList; // List of clients.
    private InvoiceList invoiceList;
    private static Warehouse warehouse; // Singleton instance of the Warehouse.
    private static ProductIdServer productIdServer; // Singleton instance of the ProductIdServer.
    private static ClientIdServer clientIdServer; // Singleton instance of the ClientIdserver.
    private static InvoiceIdServer invoiceIdServer;// Singleton instance of the inoviceIdServer.

    // Constants for warehouse operations or conditions.
    // These constants help identify the outcome of various operations.
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int CLIENT_NOT_FOUND = 2;
    public static final int PRODUCT_ADDED = 3;
    public static final int CLIENT_ADDED = 4;
    public static final int CLIENT_UPDATED = 5;
    public static final int PRODUCT_REMOVED = 6;
    public static final int CLIENT_REMOVED = 7;
    public static final int OPERATION_COMPLETED = 8;
    public static final int OPERATION_FAILED = 9;
    public static final int WISHLIST_PRODUCT_ALREADY_EXISTS = 10;
    public static final int ADD_PRODUCT_TO_WISHLIST_SUCCESS = 11;
    public static final int WISHLIST_PRODUCT_NOT_FOUND = 12;
    public static final int CLIENT_ALREADY_IN_WAITLIST = 13;
    public static final int ADD_CLIENT_TO_WAITLIST_SUCCESS = 14;
    public static final int CLIENT_NOT_FOUND_IN_WAITLIST = 15;
    public static final int REMOVE_CLIENT_FROM_WAITLIST_SUCCESS = 16;
    public static final int REMOVE_PRODUCT_FROM_WISHLIST_SUCCESS = 17;

    // Private constructor to initialize the Warehouse with productCatalog,
    // clientList, and server instances.
    private Warehouse() {
        productCatalog = ProductCatalog.instance();
        clientList = ClientList.instance();
        productIdServer = ProductIdServer.instance();
        clientIdServer = ClientIdServer.instance();
        invoiceIdServer = InvoiceIdServer.instance();
        invoiceList = InvoiceList.instance(); // Initialize the ClientIdServer
    }

    // Singleton method to get or create an instance of the Warehouse.
    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance(); // Instantiate all singletons (if not already done).
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    // custom pair class since putty doesnt like the javafx.util.Pair
    public class Pair<K, V> implements Serializable {
        private static final long serialVersionUID = 1L;
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    // Method to get a client by their ID.
    public Client getClientById(String clientId) {
        return clientList.getClientById(clientId);
    }

    // Method to get a product by its ID.
    public Product getProductById(String productId) {
        return productCatalog.getProductById(productId);
    }

    // Method to add a new product to the catalog.
    public Product addProduct(String productName, double price, int quantity) {
        Product product = new Product(productName, price, quantity);
        if (productCatalog.addProduct(product)) {
            return product;
        }
        return null;
    }

    // Method to add a new client to the list.
    public Client addClient(String clientName, String address, String phone) {
        Client client = new Client(clientName, address, phone);
        if (clientList.addClient(client)) {
            return client;
        }
        return null;
    }

    // Method to get an iterator for the products in the catalog.
    public Iterator<Product> getProducts() {
        return productCatalog.getProducts();
    }

    // Method to get an iterator for the clients in the list.
    public Iterator<Client> getClients() {
        return clientList.getClients();
    }

    // Method to get an iterator for the invoices in the list.
    public Iterator<Invoice> getInvoices() {
        return invoiceList.getInvoices();
    }

    // Method to add a product to a client's wishlist.
    public int addProductToWishlist(Client client, Product product, int quantity) {
        Wishlist clientWishlist = client.getWishlist();

        // Check if the product is already in the wishlist
        if (clientWishlist.containsProduct(product.getProductId())) {
            return WISHLIST_PRODUCT_ALREADY_EXISTS;
        }

        // Add the product to the client's wishlist
        clientWishlist.addProductToWishlist(product, quantity);
        return ADD_PRODUCT_TO_WISHLIST_SUCCESS;
    }

    // Method to remove a product from a client's wishlist.
    public int removeProductFromWishlist(Client client, Product product) {
        Wishlist clientWishlist = client.getWishlist();

        // Check if the product is in the wishlist
        if (clientWishlist.containsProduct(product.getProductId())) {
            clientWishlist.removeProductFromWishlist(product.getProductId());
            return REMOVE_PRODUCT_FROM_WISHLIST_SUCCESS;
        }

        // Product not found in the wishlist
        return WISHLIST_PRODUCT_NOT_FOUND;
    }

    // Method to add a client to a product's waitlist.
    public int addClientToWaitlist(Client client, Product product, int quantity) {
        Waitlist productWaitlist = product.getWaitlist();

        // Check if the client is already in the waitlist
        if (productWaitlist.getClients().contains(client)) {
            return CLIENT_ALREADY_IN_WAITLIST;
        }

        // Add the client to the product's waitlist
        productWaitlist.addClientToWaitlist(client, quantity);
        return ADD_CLIENT_TO_WAITLIST_SUCCESS;
    }

    // Method to remove a client from a product's waitlist.
    public int removeClientFromWaitlist(Client client, Product product) {
        Waitlist productWaitlist = product.getWaitlist();

        // Check if the client is in the waitlist
        if (productWaitlist.getClients().contains(client)) {
            productWaitlist.removeClientFromWaitlist(client);
            return REMOVE_CLIENT_FROM_WAITLIST_SUCCESS;
        }

        // Client not found in the waitlist
        return CLIENT_NOT_FOUND_IN_WAITLIST;
    }

    // Method to create an invoice for a client.
    public Invoice createInvoice(Client client, List<Product> products, List<Integer> quantities) {
        List<Double> unitPrices = new ArrayList<>();

        // Calculate unit prices based on the products' prices
        for (Product product : products) {
            unitPrices.add(product.getPrice());
        }

        Invoice invoice = new Invoice(client, products, quantities, unitPrices);

        // Add the invoice to the invoice list
        invoiceList.addInvoice(invoice);

        return invoice;
    }

    // Method to retrieve an invoice by its ID.
    public Invoice getInvoiceById(String invoiceId) {
        return invoiceList.getInvoiceById(invoiceId);
    }

    // Method to get invoices for a specific client ID.
    public List<Invoice> getInvoicesForClient(String clientId) {
        return invoiceList.getInvoicesForClient(clientId);
    }

    // Method to save the current state of the Warehouse to a file.
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            output.writeObject(InvoiceIdServer.instance()); // Add InvoiceIdServer instance
            output.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a previously saved Warehouse instance from a file.
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            warehouse = (Warehouse) input.readObject(); // Set the retrieved instance
            ClientIdServer.retrieve(input);
            return warehouse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Custom serialization method for writing the object to a stream.
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Custom deserialization method for reading the object from a stream.
    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Override the toString method to provide a string representation of the
    // Warehouse.
    public String toString() {
        return productCatalog + "\n" + clientList;
    }

    private String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Updated processOrder method
    public void processOrder(Client client) {
        System.out.println("Processing order for client: " + client.getClientName());

        // Step 2: Display each item in the wishlist
        System.out.println("Items in the wishlist:");
        Wishlist clientWishlist = client.getWishlist();
        List<Product> wishlistItems = clientWishlist.getProducts();

        // Lists to store quantities and unit prices
        List<Integer> quantities = new ArrayList<>();
        List<Double> unitPrices = new ArrayList<>();

        // Create a list to collect items marked for removal
        List<String> itemsToRemove = new ArrayList<>();

        for (Product product : wishlistItems) {
            System.out.println("Item:");
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            int itemQuantity = clientWishlist.getProductQuantity(product.getProductId());
            System.out.println("Product Quantity: " + itemQuantity);

            // Step 3: User selects an action for each item
            String action = getInput(
                    "Select an action for this item (a: Remove, b: Order existing quantity, c: Order different quantity): ");
            switch (action.toLowerCase()) {
                case "a":
                    // Step 3a: Remove from wishlist
                    itemsToRemove.add(product.getProductId());
                    break;
                case "b":
                    // Step 3b: Order with existing quantity
                    Pair<Integer, Integer> orderResult = orderProduct(client, product, itemQuantity);
                    int orderedQuantity = orderResult.getKey();
                    int remainingQuantity = orderResult.getValue();
                    if (orderedQuantity > 0) {
                        System.out.println("Item ordered with existing quantity: " + orderedQuantity);
                        // Add quantity and unit price to the lists
                        quantities.add(orderedQuantity);
                        unitPrices.add(product.getPrice());
                        itemsToRemove.add(product.getProductId());

                        if (remainingQuantity > 0) {
                            // Put the remaining quantity into the product waitlist
                            addClientToWaitlist(client, product, remainingQuantity);
                        }
                    } else {
                        System.out.println("Order failed: Insufficient stock.");
                    }
                    break;
                case "c":
                    // Step 3c: Order with a different quantity
                    int quantity = Integer.parseInt(getInput("Enter the quantity to order: "));
                    Pair<Integer, Integer> customOrderResult = orderProduct(client, product, quantity);
                    int customOrderedQuantity = customOrderResult.getKey();
                    int customRemainingQuantity = customOrderResult.getValue();
                    if (customOrderedQuantity > 0) {
                        System.out.println("Item ordered with custom quantity: " + customOrderedQuantity);
                        // Add quantity and unit price to the lists
                        quantities.add(customOrderedQuantity);
                        unitPrices.add(product.getPrice());
                        itemsToRemove.add(product.getProductId());

                        if (customRemainingQuantity > 0) {
                            // Put the remaining quantity into the product waitlist
                            addClientToWaitlist(client, product, customRemainingQuantity);
                        }
                    } else {
                        System.out.println("Order failed: Insufficient stock or invalid quantity.");
                    }
                    break;
                default:
                    System.out.println("Invalid action. Please select a valid action (a, b, or c).");
            }
        }

        // Step 4: User confirms the wishlist for processing (assuming confirmation)
        String confirm = getInput("Do you want to confirm the order? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            // Step 6: System checks the availability of items in the warehouse
            List<Product> orderedProducts = wishlistItems.stream()
                    .filter(product -> product.getQuantity() > 0)
                    .collect(Collectors.toList());

            boolean isAvailabilityValid = checkAvailability(orderedProducts);

            if (isAvailabilityValid) {

                // Step 8: System calculates the total price for invoiced items and updates the
                // client's balance
                double totalPrice = calculateTotalPrice(orderedProducts, quantities);
                client.setBalance(client.getBalance() - totalPrice);

                // Step 9: System places any remaining items and quantities in a waitlist
                int totalOrderedQuantity = wishlistItems.stream()
                        .mapToInt(product -> clientWishlist.getProductQuantity(product.getProductId()))
                        .sum();

                if (totalOrderedQuantity > 0) {
                    List<Product> remainingProducts = wishlistItems.stream()
                            .filter(product -> product.getQuantity() > 0)
                            .collect(Collectors.toList());

                    for (Product remainingProduct : remainingProducts) {
                        int itemQuantity = clientWishlist.getProductQuantity(remainingProduct.getProductId());
                        if (itemQuantity > remainingProduct.getQuantity()) {
                            // Client ordered more than what is available, put the remaining quantity into
                            // the product waitlist
                            int remainingQuantity = itemQuantity - remainingProduct.getQuantity();
                            warehouse.addClientToWaitlist(client, remainingProduct, remainingQuantity);
                            client.getWishlist().decreaseProductQuantity(remainingProduct.getProductId(),
                                    remainingProduct.getQuantity());
                        }
                        client.getWishlist().decreaseProductQuantity(remainingProduct.getProductId(),
                                totalOrderedQuantity);
                    }
                }

                // Step 10: System generates an invoice, shows it to the user, and saves the
                // invoice
                Invoice invoice = new Invoice(client, orderedProducts, quantities, unitPrices);

                invoiceList.addInvoice(invoice);

                System.out.println("Order successfully processed. Invoice generated.");
                // Remove items marked for removal from the wishlist
                for (String productIdToRemove : itemsToRemove) {
                    clientWishlist.removeProductFromWishlist(productIdToRemove);
                }
            } else {
                System.out.println("Order failed: Insufficient stock.");
            }
        } else {
            System.out.println("Order canceled.");
        }

    }

    // Updated orderProduct method to return the actual quantity ordered and
    // remaining quantity.
    private Pair<Integer, Integer> orderProduct(Client client, Product product, int quantity) {
        Product orderedProduct = warehouse.getProductById(product.getProductId());

        int availableQuantity = orderedProduct.getQuantity(); // Get the available quantity in the warehouse

        int orderedQuantity = Math.min(quantity, availableQuantity); // Calculate the actual quantity to order

        return new Pair<>(orderedQuantity, quantity - orderedQuantity); // Return the actual quantity ordered
                                                                        // and remaining quantity
    }

    // Method to check availability of ordered products in the warehouse
    private boolean checkAvailability(List<Product> orderedProducts) {
        for (Product product : orderedProducts) {
            Product warehouseProduct = warehouse.getProductById(product.getProductId());
            if (warehouseProduct == null || warehouseProduct.getQuantity() < product.getQuantity()) {
                return false; // Insufficient stock for at least one product
            }
        }
        return true; // All ordered products are available in sufficient quantities
    }

    // Method to calculate the total price of ordered products
    private double calculateTotalPrice(List<Product> orderedProducts, List<Integer> quantities) {
        double totalPrice = 0.0;
        for (int i = 0; i < orderedProducts.size(); i++) {
            double productPrice = orderedProducts.get(i).getPrice();
            int productQuantity = quantities.get(i);
            double productTotalPrice = productPrice * productQuantity;
            totalPrice += productTotalPrice;
        }
        return totalPrice;
    }

    public void supplyProducts(Product selectedProduct, int quantityToAdd) {
        // Add the received quantity to the product in the warehouse
        selectedProduct.setQuantity(selectedProduct.getQuantity() + quantityToAdd);
    
        Waitlist productWaitlist = selectedProduct.getWaitlist();    
        for (Client waitlistClient : productWaitlist.getClients()) {

            String action = getInput("Client " + waitlistClient.getClientName()
                    + " is on the waitlist. What do you want to do? (a: Leave, b: Order with existing quantity, c: Order with different quantity): ");
    
            switch (action.toLowerCase()) {
                case "a":
                    // Do nothing, leave the client on the waitlist
                    System.out.println("Client " + waitlistClient.getClientName() + " is left on the waitlist.");
                    break;
                case "b":
                    int orderedQuantity = productWaitlist.getClientQuantity(waitlistClient);
                    if (orderedQuantity > 0) {
                        // Process the order for the client
                        processOrder_supply(waitlistClient, selectedProduct, orderedQuantity);
    
                    }
                    break;
                case "c":
                    int newQuantity = Integer.parseInt(getInput("Enter a different quantity for client " + waitlistClient.getClientName() + ": "));
                    if (newQuantity > 0) {
                        int customOrderedQuantity = productWaitlist.getClientQuantity(waitlistClient);
                        if (customOrderedQuantity > 0) {
                            // Process the order for the client
                            processOrder_supply(waitlistClient, selectedProduct, customOrderedQuantity);    
                        }
                    } else {
                        System.out.println("Invalid quantity. No action taken.");
                    }
                    break;
                default:
                    System.out.println("Invalid action. Please select a valid action (a, b, or c).");
            }
        }
    }
    
    
    
    private void processOrder_supply(Client client, Product selectedProduct, int orderedQuantity) {
        // Remove the client from the waitlist
        selectedProduct.getWaitlist().removeClientFromWaitlist(client);
    
        // Deduct the ordered quantity from the product
        selectedProduct.setQuantity(selectedProduct.getQuantity() - orderedQuantity);
    
        List<Product> products = new ArrayList<>();
        products.add(selectedProduct);
        List<Integer> quantities = new ArrayList<>();
        quantities.add(orderedQuantity);
        List<Double> unitPrices = new ArrayList<>();
        unitPrices.add(selectedProduct.getPrice());

        double totalPrice = calculateTotalPrice(products, quantities);
        client.setBalance(client.getBalance() - totalPrice);
    
        Invoice invoice = new Invoice(client, products, quantities, unitPrices);
        invoiceList.addInvoice(invoice);
    
        System.out.println("Order successfully processed. Invoice generated.");
        System.out.println("Client " + client.getClientName() + " ordered " + orderedQuantity + " of " + selectedProduct.getProductName());
    }

}