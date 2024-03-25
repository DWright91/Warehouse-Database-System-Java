import java.io.Serializable;
import java.util.*;
import java.io.*;

// A class representing a list of clients.
public class ClientList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Client> clients = new LinkedList<>();
    private static ClientList clientList;

    private ClientList() {
        // Private constructor to enforce singleton pattern.
    }

    // Method to get an instance of the ClientList (singleton pattern).
    public static ClientList instance() {
        if (clientList == null) {
            return (clientList = new ClientList());
        } else {
            return clientList;
        }
    }

    // Method to add a client to the list.
    public boolean addClient(Client client) {
        clients.add(client);
        return true;
    }

    // Method to remove a client from the list.
    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    // Method to update client information in the list.
    public boolean updateClient(Client updatedClient) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientId().equals(updatedClient.getClientId())) {
                clients.set(i, updatedClient);
                return true;
            }
        }
        return false;
    }

    // Method to get a client by their ID from the list.
    public Client getClientById(String clientId) {
        for (Client client : clients) {
            if (client.getClientId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    // Method to get an iterator for all clients in the list.
    public Iterator<Client> getClients() {
        return clients.iterator();
    }

    // Custom serialization method for writing the object to a file.
    private void writeObject(java.io.ObjectOutputStream output) throws IOException {
        try {
            output.defaultWriteObject();
            output.writeObject(clientList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Custom deserialization method for reading the object from a file.
    private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
        try {
            if (clientList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (clientList == null) {
                    clientList = (ClientList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Method to provide a string representation of the client list.
    public String toString() {
        return clients.toString();
    }
}
