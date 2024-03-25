import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// A class representing a Wishlist that stores a list of products.
public class Wishlist implements Serializable, Iterable<Product> {
    private List<Product> products; // List to store the products in the wishlist.
    private List<Integer> quantities; // List to store the quantities of the corresponding products.

    // Constructor to initialize an empty wishlist.
    public Wishlist() {
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    // Method to add a product to the wishlist with a specified quantity.
    public void addProductToWishlist(Product product, int quantity) {
        if (!containsProduct(product.getProductId())) {
            products.add(product);
            quantities.add(quantity);
        }
    }

    // Method to remove a product from the wishlist using the product ID.
    public void removeProductFromWishlist(String productId) {
        int index = getProductIndexById(productId);
        if (index != -1) {
            products.remove(index);
            quantities.remove(index);
        }
    }

    public void decreaseProductQuantity(String productId, int quantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                int currentQuantity = product.getQuantity();
                if (currentQuantity >= quantity) {
                    product.setQuantity(currentQuantity - quantity);
                }
                break; // Exit the loop after updating the quantity
            }
        }
    }

    // Method to get the list of products in the wishlist.
    public List<Product> getProducts() {
        return products;
    }

    // Method to get the quantity of a specific product in the wishlist using the
    // product ID.
    public int getProductQuantity(String productId) {
        int index = getProductIndexById(productId);
        if (index != -1) {
            return quantities.get(index);
        }
        return 0; // Product not found in the wishlist.
    }

    // Method to check if a product is already in the wishlist using the product ID.
    public boolean containsProduct(String productId) {
        return getProductIndexById(productId) != -1;
    }

    // Helper method to get the index of a product in the wishlist using its ID.
    private int getProductIndexById(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return i;
            }
        }
        return -1; // Product not found.
    }

    // Override the iterator method to provide an iterator for the products.
    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    // Override the toString method to provide a string representation of the
    // wishlist.
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : products) {
            stringBuilder.append(product.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
