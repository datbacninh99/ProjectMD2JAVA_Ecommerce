package business.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static final String USER_PATH = "src/business/data/user.txt";
    public static final String CATALOG_PATH = "src/business/data/catalog.txt";
    public static final String PRODUCT_PATH = "src/business/data/product.txt";
    public static final String ORDER_PATH = "src/business/data/order.txt";
    public static final String CART_PATH = "src/business/data/cart.txt";

    public static <T> List<T> readFromFile(String PATH) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(PATH);
            ois = new ObjectInputStream(fis);
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ArrayList<>();
    }


    public static <T> void writeToFile(String PATH, List<T> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(PATH);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}