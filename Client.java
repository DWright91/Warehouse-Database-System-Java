import java.io.Serializable;

// A class representing a client with a wishlist.
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientName;
    private String address;
    private String phone;
    private String clientId;
    private double balance; // Balance variable to represent account balance
    private static final String CLIENT_STRING = "C";

    private Wishlist wishlist; // Each client has one wishlist

    // Constructor to create a new client with the given name, address, and phone
    // number.
    public Client(String clientName, String address, String phone) {
        this.clientName = clientName;
        this.address = address;
        this.phone = phone;
        this.clientId = CLIENT_STRING + (ClientIdServer.instance()).getId();
        this.balance = 0.0; // Initialize balance to zero
        this.wishlist = new Wishlist(); // Initialize the wishlist
    }

    // Method to get the client's name.
    public String getClientName() {
        return clientName;
    }

    // Method to get the client's address.
    public String getAddress() {
        return address;
    }

    // Method to get the client's phone number.
    public String getPhone() {
        return phone;
    }

    // Method to get the client's unique ID.
    public String getClientId() {
        return clientId;
    }

    // Method to get the client's account balance.
    public double getBalance() {
        return balance;
    }

    // Method to set the client's balance.
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to set the client's address.
    public void setAddress(String newAddress) {
        address = newAddress;
    }

    // Method to set the client's phone number.
    public void setPhone(String newPhone) {
        phone = newPhone;
    }

    // Method to check if this client has the same ID as the provided one.
    public boolean equals(String id) {
        return this.clientId.equals(id);
    }

    // Method to get the client's wishlist.
    public Wishlist getWishlist() {
        return wishlist;
    }

    // Method to provide a string representation of the client.
    public String toString() {
        String string = "Client Name: " + clientName + " | Address: " + address + " | ID: " + clientId + " | Phone: "
                + phone + " | Balance: $" + balance;
        return string;
    }
}
