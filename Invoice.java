import java.io.Serializable;
import java.util.*;

// A class representing an invoice.
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private String invoiceId;
    private Date date;
    private Client client;
    private List<Product> products;
    private List<Integer> quantities;
    private List<Double> unitPrices;
    private double totalPrice;
    private static final String INVOICE_STRING = "INV";

    // Constructor to initialize an invoice with a client, products, quantities, and
    // unit prices.
    public Invoice(Client client, List<Product> products, List<Integer> quantities, List<Double> unitPrices) {
        this.invoiceId = INVOICE_STRING + (InvoiceIdServer.instance()).getId();
        this.client = client;
        this.date = new Date();
        this.products = products;
        this.quantities = quantities;
        this.unitPrices = unitPrices;
        this.totalPrice = getTotalAmount();
    }

    // Method to get the invoice ID.
    public String getInvoiceId() {
        return invoiceId;
    }

    // Method to get the invoice date.
    public Date getDate() {
        return date;
    }

    // Method to get the client associated with the invoice.
    public Client getClient() {
        return client;
    }

    // Method to get the list of products on the invoice.
    public List<Product> getProducts() {
        return products;
    }

    // Method to get the list of quantities for each product.
    public List<Integer> getQuantities() {
        return quantities;
    }

    // Method to get the list of unit prices for each product.
    public List<Double> getUnitPrices() {
        return unitPrices;
    }

    // Method to calculate the total amount of the invoice.
    public double getTotalAmount() {
        double total = 0.0;
        for (int i = 0; i < products.size(); i++) {
            total += quantities.get(i) * unitPrices.get(i);
        }
        return total;
    }

    // Method to provide a string representation of the invoice.
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invoice ID: ").append(invoiceId).append("\n");
        stringBuilder.append("Date: ").append(date).append("\n");
        stringBuilder.append("Client: ").append(client).append("\n");

        stringBuilder.append("Products:\n");
        for (int i = 0; i < products.size(); i++) {
            stringBuilder.append("  Product: ").append(products.get(i).getProductName());
            stringBuilder.append(" | Quantity: ").append(quantities.get(i));
            stringBuilder.append(" | Unit Price: $").append(unitPrices.get(i));
            stringBuilder.append("\n");
        }

        stringBuilder.append("Total Amount: $").append(totalPrice).append("\n");

        return stringBuilder.toString();
    }
}
