import java.io.Serializable;
import java.io.*;

// A class representing a server for generating unique client IDs.
public class ClientIdServer implements Serializable {
  private int idCounter;
  private static ClientIdServer clientIdServer;

  private ClientIdServer() {
    idCounter = 1;
  }

  // Method to get an instance of the ClientIdServer (singleton pattern).
  public static ClientIdServer instance() {
    if (clientIdServer == null) {
      return (clientIdServer = new ClientIdServer());
    } else {
      return clientIdServer;
    }
  }

  // Method to get a unique client ID.
  public int getId() {
    return idCounter++;
  }

  // Method to provide a string representation of the ClientIdServer.
  public String toString() {
    return "ClientIdServer " + idCounter;
  }

  // Custom deserialization method for retrieving the object from a file.
  public static void retrieve(ObjectInputStream input) {
    try {
      clientIdServer = (ClientIdServer) input.readObject();
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
      output.writeObject(clientIdServer);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  // Custom deserialization method for reading the object from a file.
  private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
    try {
      input.defaultReadObject();
      if (clientIdServer == null) {
        clientIdServer = (ClientIdServer) input.readObject();
      } else {
        input.readObject();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
