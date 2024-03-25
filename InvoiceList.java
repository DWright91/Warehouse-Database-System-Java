import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// A class representing a list of invoices.
public class InvoiceList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Invoice> invoices = new ArrayList<>();
    private static InvoiceList invoiceList;

    private InvoiceList() {
        // Private constructor to enforce singleton pattern.
    }

    // Method to get an instance of the InvoiceList (singleton pattern).
    public static InvoiceList instance() {
        if (invoiceList == null) {
            return (invoiceList = new InvoiceList());
        } else {
            return invoiceList;
        }
    }

    // Method to add an invoice to the list.
    public boolean addInvoice(Invoice invoice) {
        invoices.add(invoice);
        return true;
    }

    // Method to remove an invoice from the list.
    public boolean removeInvoice(Invoice invoice) {
        return invoices.remove(invoice);
    }

    // Method to get an iterator for all invoices in the list.
    public Iterator<Invoice> getInvoices() {
        return invoices.iterator();
    }

    // Method to get an invoice by its ID from the list.
    public Invoice getInvoiceById(String invoiceId) {
        return invoices.stream()
                .filter(invoice -> invoice.getInvoiceId().equals(invoiceId))
                .findFirst()
                .orElse(null);
    }

    // Method to get invoices for a specific client ID.
    public List<Invoice> getInvoicesForClient(String clientId) {
        return invoices.stream()
                .filter(invoice -> invoice.getClient().getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    // Custom serialization method for writing the object to a file.
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(invoiceList);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Custom deserialization method for reading the object from a file.
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (invoiceList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (invoiceList == null) {
                    invoiceList = (InvoiceList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    // Method to provide a string representation of the invoice list.
    public String toString() {
        return invoices.toString();
    }
}