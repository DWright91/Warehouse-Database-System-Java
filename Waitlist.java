import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A class representing a Waitlist that stores a list of clients and their requested quantities.
public class Waitlist implements Serializable {
    private Map<Client, Integer> clientsWithQuantity; // Map to store clients and their requested quantities.

    // Constructor to initialize an empty waitlist.
    public Waitlist() {
        this.clientsWithQuantity = new HashMap<>();
    }

    // Method to add a client to the waitlist with their requested quantity.
    public void addClientToWaitlist(Client client, int quantity) {
        if (quantity > 0) {
            clientsWithQuantity.put(client, quantity);
        }
    }

    // Method to remove a client from the waitlist.
    public void removeClientFromWaitlist(Client client) {
        clientsWithQuantity.remove(client);
    }

    // Method to get the list of clients in the waitlist.
    public List<Client> getClients() {
        return new ArrayList<>(clientsWithQuantity.keySet());
    }

    // Method to get the requested quantity for a specific client in the waitlist.
    public int getClientQuantity(Client client) {
        return clientsWithQuantity.getOrDefault(client, 0);
    }

    // Method to check if a client is already in the waitlist.
    public boolean containsClient(Client client) {
        return clientsWithQuantity.containsKey(client);
    }

    // Override the toString method to provide a string representation of the
    // waitlist.
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Client, Integer> entry : clientsWithQuantity.entrySet()) {
            Client client = entry.getKey();
            int quantity = entry.getValue();
            stringBuilder.append(client.toString()).append(" (Requested Quantity: ").append(quantity).append(")\n");
        }
        return stringBuilder.toString();
    }
}
