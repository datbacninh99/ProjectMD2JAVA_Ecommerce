package business.implement;

import business.design.IProductDesign;
import business.entity.Product;
import business.utils.IOFile;
import business.utils.InputMethods;

import java.time.LocalDate;
import java.util.*;

public class ProductImplement implements IProductDesign {
    public static List<Product> productList;

    static {
        productList = IOFile.readFromFile(IOFile.PRODUCT_PATH);
    }

    @Override
    public void displayProductList() {
        productList.sort(Comparator.comparing(Product::getUnitPrice).reversed());
        System.out.println("==========DANH SÁCH SẢN PHẨM==========");
        for (Product product : productList) {
            product.displayData();
        }
    }

    @Override
    public void addNewProduct() {
        Product newProduct = new Product();
        newProduct.inputData();
        productList.add(newProduct);
        IOFile.writeToFile(IOFile.PRODUCT_PATH, productList);
        System.out.println("Sản phẩm mới đã được thêm thành công.");
    }

    @Override
    public void editProductInfo() {
        System.out.println("Nhập mã sản phẩm cần chỉnh sửa: ");
        int productId = InputMethods.getInteger();

        for (Product product : productList) {
            if (product.getProductId() == productId) {
                product.inputData();
                product.setUpdatedAt(LocalDate.now());
                IOFile.writeToFile(IOFile.PRODUCT_PATH, productList);
                System.out.println("Thông tin sản phẩm đã được cập nhật thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy sản phẩm có mã: " + productId);
    }

    @Override
    public void hideProductById() {
        System.out.println("Nhập mã sản phẩm cần ẩn: ");
        int productId = InputMethods.getInteger();

        for (Product product : productList) {
            if (product.getProductId() == productId) {
                product.setStatus(false);
                IOFile.writeToFile(IOFile.PRODUCT_PATH, productList);
                System.out.println("Sản phẩm đã được ẩn thành công.");
                return;
            }
        }

        System.out.println("Không tìm thấy sản phẩm có mã: " + productId);
    }

    @Override
    public void searchProductByName() {
        System.out.println("==========TÌM KIẾM SẢN PHẨM==========");
        System.out.println("Nhập tên sản phẩm cần tìm kiếm: ");
        String searchName = InputMethods.getString().toLowerCase();

        boolean check = false;
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(searchName)) {
                product.displayData();
                check = true;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy sản phẩm nào phù hợp với từ khóa: " + searchName);
        }
    }

    public void searchProduct() {
        System.out.println("==========TÌM KIẾM SẢN PHẨM==========");
        System.out.println("Nhập tên sản phẩm cần tìm kiếm: ");
        String searchName = InputMethods.getString().toLowerCase();

        boolean check = false;
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(searchName)) {
                product.displayData();
                check = true;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy sản phẩm nào phù hợp với từ khóa: " + searchName);
        }
    }

    public void displayTop10Product() {
        System.out.println("==========TOP 10 SẢN PHẨM NỔI BẬT==========");
        Collections.sort(productList, Comparator.comparingInt(this::getProductPopularity).reversed()); // :: methods reference: tham chiếu đến phương thức của đối tượng

        int count = 0;
        for (Product product : productList) {
            if (count >= 10) {
                break;
            }
            product.displayData();
            count++;
        }
    }

    public void displayProductGroupByCatalog() {
        System.out.println("==========HIỂN THỊ NHÓM SẢN PHẨM THEO DANH MỤC==========");
        Map<String, List<Product>> productMap = new HashMap<>();
        for (Product product : productList) {
            String category = product.getCategoryId();
            List<Product> products = productMap.getOrDefault(category, new ArrayList<>());
            products.add(product);
            productMap.put(category, products);
        }

        for (Map.Entry<String, List<Product>> entry : productMap.entrySet()) {
            System.out.println("Danh mục: " + entry.getKey());
            for (Product product : entry.getValue()) {
                product.displayData();
            }
        }
    }

    public void showProductList() {
        System.out.println("==========DANH SÁCH SẢN PHẨM==========");
        for (Product product : productList) {
            product.displayData();
        }
    }

    public void displayProductByPrice() {
        System.out.println("==========DANH SÁCH SẢN PHẨM THEO GIÁ==========");
        Collections.sort(productList, Comparator.comparingDouble(Product::getUnitPrice));

        for (Product product : productList) {
            product.displayData();
        }
    }

    private int getProductPopularity(Product product) {
        return product.getStock();
    }

    public void displayProductById() {
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
        System.out.println("Nhập ID sản phẩm: ");
        int productId = InputMethods.getInteger();

        boolean check = false;
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.displayData();
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy sản phẩm.");
        }
    }
}