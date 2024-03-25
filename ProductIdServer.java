import java.io.*;

// A class representing a server for generating unique client IDs.
public class ProductIdServer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idCounter;

    private static ProductIdServer productIdServer;

    private ProductIdServer() {
        idCounter = 1; // Initialize the first product ID
    }

    public static ProductIdServer instance() {
        if (productIdServer == null) {
            return (productIdServer = new ProductIdServer());
        } else {
            return productIdServer;
        }
    }

    public int getId() {
        return idCounter++;
    }

    // Method to provide a string representation of the ClientIdServer.
    public String toString() {
        return ("ProductIdServer: " + idCounter);
    }

    // Custom deserialization method for retrieving the object from a file.

    public static void retrieve(ObjectInputStream input) {
        try {
            productIdServer = (ProductIdServer) input.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    // Custom serialization method for writing the object to a file.

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(productIdServer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (productIdServer == null) {
                productIdServer = (ProductIdServer) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
