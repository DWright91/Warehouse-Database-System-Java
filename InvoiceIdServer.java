import java.io.*;

// A class representing a server for generating unique invoice IDs.
public class InvoiceIdServer implements Serializable {
    private int idCounter;
    private static InvoiceIdServer invoiceIdServer;

    private InvoiceIdServer() {
        idCounter = 1;
    }

    // Method to get an instance of the InvoiceIdServer (singleton pattern).
    public static InvoiceIdServer instance() {
        if (invoiceIdServer == null) {
            return (invoiceIdServer = new InvoiceIdServer());
        } else {
            return invoiceIdServer;
        }
    }

    // Method to get a unique invoice ID.
    public int getId() {
        return idCounter++;
    }

    // Method to provide a string representation of the InvoiceIdServer.
    public String toString() {
        return "InvoiceIdServer " + idCounter;
    }

    // Custom deserialization method for retrieving the object from a file.
    public static void retrieve(ObjectInputStream input) {
        try {
            invoiceIdServer = (InvoiceIdServer) input.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    // Custom serialization method for writing the object to a file.
    private void writeObject(java.io.ObjectOutputStream output) throws IOException {
        try {
            output.defaultWriteObject();
            output.writeObject(invoiceIdServer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Custom deserialization method for reading the object from a file.
    private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
        try {
            input.defaultReadObject();
            if (invoiceIdServer == null) {
                invoiceIdServer = (InvoiceIdServer) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
