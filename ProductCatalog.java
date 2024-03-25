import java.util.*;
import java.io.*;

// A class representing a catalog of products.
public class ProductCatalog implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> products = new LinkedList<>();
    private static ProductCatalog productCatalog;

    private ProductCatalog() {
        // Private constructor to enforce singleton pattern.
    }

    // Method to get an instance of the ProductCatalog (singleton pattern).
    public static ProductCatalog instance() {
        if (productCatalog == null) {
            return (productCatalog = new ProductCatalog());
        } else {
            return productCatalog;
        }
    }

    // Method to add a product to the catalog with a quantity.
    public boolean addProduct(Product product) {
        if (product != null) {
            products.add(product);
            return true; // Product added successfully
        }
        return false; // Product is null, cannot be added
    }

    // Method to remove a product from the catalog.
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    // Method to update product information in the catalog.
    public boolean updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(updatedProduct.getProductId())) {
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    // Method to get a product by its ID from the catalog.
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // Method to get an iterator for all products in the catalog.
    public Iterator<Product> getProducts() {
        return products.iterator();
    }

    // Custom serialization method for writing the object to a file.
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(productCatalog);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Custom deserialization method for reading the object from a file.
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (productCatalog != null) {
                return;
            } else {
                input.defaultReadObject();
                if (productCatalog == null) {
                    productCatalog = (ProductCatalog) input.readObject();
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

    // Method to provide a string representation of the product catalog.
    public String toString() {
        return products.toString();
    }
}
