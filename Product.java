import java.io.Serializable;

// A class representing a product.
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productName;
    private String productId;
    private double price;
    private int quantity; // New field to store the quantity of the product
    private Waitlist waitlist; // A waitlist for clients who want this product
    private static final String PRODUCT_STRING = "P";

    // Constructor to initialize a product with name, ID, price, and quantity.
    public Product(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productId = PRODUCT_STRING + (ProductIdServer.instance()).getId();
        this.waitlist = new Waitlist(); // Initialize the waitlist for this product
    }

    // Method to get the product name.
    public String getProductName() {
        return productName;
    }

    // Method to get the product ID.
    public String getProductId() {
        return productId;
    }

    // Method to get the product price.
    public double getPrice() {
        return price;
    }

    // Method to get the product quantity.
    public int getQuantity() {
        return quantity;
    }

    // Method to set the product quantity.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean updateQuantity(int quantity) {
        if (quantity >= 0) { // Ensure the new quantity is non-negative
            this.quantity = quantity;
            return true; // Quantity updated successfully
        } else {
            return false; // Invalid quantity
        }
    }

    // Method to add a client to the waitlist for this product.
    public void addClientToWaitlist(Client client, int quantity) {
        waitlist.addClientToWaitlist(client, quantity);
    }

    // Method to remove a client from the waitlist for this product.
    public void removeClientFromWaitlist(Client client) {
        waitlist.removeClientFromWaitlist(client);
    }

    // Method to get the waitlist for this product.
    public Waitlist getWaitlist() {
        return waitlist;
    }

    // Method to provide a string representation of the product.
    public String toString() {
        return "Product Name: " + productName + " | Product ID: " + productId + " | Price: $" + price + " | Quantity: "
                + quantity;
    }
}
