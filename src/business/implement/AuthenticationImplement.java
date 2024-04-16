package business.implement;

import business.design.IAuthenticationDesign;
import business.entity.CartItem;
import business.entity.Order;
import business.entity.Product;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;

import java.util.Comparator;
import java.util.List;

public class AuthenticationImplement implements IAuthenticationDesign {
    public static List<User> userList;

    static {
        userList = IOFile.readFromFile(IOFile.USER_PATH);
    }

    public User login(String username, String password) {
        User userLogin = getUserFromUsername(username);
        if (userLogin == null) {
            return null;
        }
        boolean checkLogin = userLogin.getPassword().contains(password);
        if (checkLogin) {
            return userLogin;
        }
        return null;
    }

    private User getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUserName().contains(username)).findFirst().orElse(null);
    }

    @Override
    public void displayUserList() {
        userList.sort(new Comparator<User>() { // hàm nặc danh
            @Override
            public int compare(User u1, User u2) {
                int lastNameComp = u1.getLastName().compareToIgnoreCase(u2.getLastName());
                if (lastNameComp != 0) {
                    return lastNameComp;
                }
                return u1.getFirstName().compareToIgnoreCase(u2.getFirstName());
            }
        });
        System.out.println("==========DANH SÁCH NGƯỜI DÙNG==========");
        for (User user : userList) {
            user.displayData();
        }
    }

    @Override
    public void searchUserByName() {
        System.out.println("Nhập tên người dùng cần tìm:");
        String name = InputMethods.getString().toLowerCase();
        boolean check = false;

        System.out.println("Kết quả tìm kiếm:");
        for (User user : userList) {
            if (user.getFirstName().toLowerCase().contains(name) || user.getLastName().toLowerCase().contains(name)) {
                user.displayData();
                check = true;
            }
        }
        if (!check) {
            System.out.println("Không tìm thấy người dùng.");
        }
    }

    @Override
    public void changeUserStatus() {
        System.out.println("Nhập ID người dùng để Block/Unblock:");
        int id = InputMethods.getInteger();
        boolean check = false;

        for (User user : userList) {
            if (user.getUserId() == id) {
                user.setStatus(!user.isStatus());
                System.out.println("Người dùng đã " + (user.isStatus() ? "Unblocked" : "Blocked") + " thành công.");
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy người dùng với ID: " + id);
        }
    }

    public void addToCart() {
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
        List<CartItem> cartItems = IOFile.readFromFile(IOFile.CART_PATH);

        System.out.println("Nhập ID sản phẩm: ");
        long productId = InputMethods.getLong();
        System.out.println("Nhập số lượng: ");
        int quantity = InputMethods.getInteger();

        boolean isAdded = false;
        for (Product product : products) {
            if (product.getProductId() == productId && product.getStock() >= quantity) {
                cartItems.add(new CartItem(productId, quantity));
                IOFile.writeToFile(IOFile.CART_PATH, cartItems);
                System.out.println("Đã thêm sản phẩm vào giỏ hàng thành công.");
                isAdded = true;
                break;
            }
        }

        if (!isAdded) {
            System.out.println("Sản phẩm không có sẵn hoặc không đủ số lượng.");
        }
    }

    public void displayProductListInCart() {
        List<CartItem> cartItems = IOFile.readFromFile(IOFile.CART_PATH);
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);

        if (cartItems.isEmpty()) {
            System.out.println("Giỏ hàng trống.");
        } else {
            for (CartItem item : cartItems) {
                Product product = findProductById(products, item.getProductId());
                if (product != null) {
                    System.out.printf("| ID sản phẩm: %d | Tên sản phẩm: %s | Số lượng: %d | Đơn giá: %.2f | Thành tiền: %.2f\n",
                            product.getProductId(), product.getProductName(), item.getQuantity(), product.getUnitPrice(), product.getUnitPrice() * item.getQuantity());
                }
            }
        }
    }

    private Product findProductById(List<Product> products, Long productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public void changeOrderQuantity() {
        List<CartItem> cartItems = IOFile.readFromFile(IOFile.CART_PATH);
        System.out.println("Nhập ID sản phẩm cần thay đổi số lượng:");
        long productId = InputMethods.getLong();
        System.out.println("Nhập số lượng mới:");
        int newQuantity = InputMethods.getInteger();

        boolean check = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(newQuantity);
                check = true;
                break;
            }
        }

        if (check) {
            IOFile.writeToFile(IOFile.CART_PATH, cartItems);
            System.out.println("Đã cập nhật số lượng sản phẩm trong giỏ hàng.");
        } else {
            System.out.println("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }

    public void deleteProductInQuantity() {
        List<CartItem> cartItems = IOFile.readFromFile(IOFile.CART_PATH);
        System.out.println("Nhập ID sản phẩm cần xóa khỏi giỏ hàng:");
        long productId = InputMethods.getLong();

        boolean check = false;
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProductId().equals(productId)) {
                cartItems.remove(i);
                check = true;
                break;
            }
        }

        if (check) {
            IOFile.writeToFile(IOFile.CART_PATH, cartItems);
            System.out.println("Sản phẩm đã được xóa khỏi giỏ hàng.");
        } else {
            System.out.println("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }

    public void placeOrder() {
        List<CartItem> cartItems = IOFile.readFromFile(IOFile.CART_PATH);
        if (cartItems.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không thể đặt hàng.");
            return;
        }

        cartItems.clear();
        IOFile.writeToFile(IOFile.CART_PATH, cartItems);

        Order order = new Order();
        System.out.println("==========NHẬP THÔNG TIN ĐẶT HÀNG==========");
        order.inputData();

        OrderImplement.orderList.add(order);

        System.out.println("Đơn hàng đã được đặt thành công.");
    }

    public void changePassword() {
        System.out.println("Nhập tên đăng nhập:");
        String username = InputMethods.getString();
        User user = getUserFromUsername(username);
        if (user != null) {
            System.out.println("Nhập mật khẩu cũ:");
            String oldPassword = InputMethods.getString();
            if (user.getPassword().equals(oldPassword)) {
                System.out.println("Nhập mật khẩu mới:");
                String newPassword = InputMethods.getString();
                user.setPassword(newPassword);

                userList.set(userList.indexOf(user), user);

                IOFile.writeToFile(IOFile.USER_PATH, userList);
                System.out.println("Mật khẩu đã được thay đổi thành công.");
            } else {
                System.out.println("Mật khẩu cũ không chính xác.");
            }
        } else {
            System.out.println("Không tìm thấy người dùng.");
        }
    }

    public void displayUserInfo() {
        System.out.println("Nhập tên đăng nhập:");
        String username = InputMethods.getString();
        User user = getUserFromUsername(username);
        if (user != null) {
            user.displayData();
        } else {
            System.out.println("Không tìm thấy người dùng.");
        }
    }

    public void editUserInfo() {
        System.out.println("Nhập tên đăng nhập để cập nhật thông tin người dùng:");
        String username = InputMethods.getString();
        User user = getUserFromUsername(username);

        if (user != null) {
            boolean isExit = true;
            System.out.println("Nhập họ mới: ");
            String newFirstName = InputMethods.getString();
            if (!newFirstName.isEmpty()) {
                user.setFirstName(newFirstName);
            }

            System.out.println("Nhập tên mới: ");
            String newLastName = InputMethods.getString();
            if (!newLastName.isEmpty()) {
                user.setLastName(newLastName);
            }

            System.out.println("Nhập địa chỉ email mới: ");
            while (isExit) {
                String newEmail = InputMethods.getString();
                if (newEmail.isEmpty()) {
                    break;
                } else if (newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    user.setEmail(newEmail);
                    isExit = false;
                } else {
                    System.out.println("Định dạng email không hợp lệ");
                }
            }

            System.out.println("Nhập số điện thoại mới: ");
            String newPhoneNumber = InputMethods.getString();
            while (isExit) {
                if (newPhoneNumber.isEmpty()) {
                    break;
                } else if (newPhoneNumber.matches("((^(\\\\+84|84|0|0084){1})([35789]))+([0-9]{8})$")) {
                    user.setPhoneNumber(newPhoneNumber);
                    isExit = false;
                } else {
                    System.out.println("Định dạng số điện thoại Việt Nam không hợp lệ");
                }
            }

            System.out.println("Nhập địa chỉ mới: ");
            String newAddress = InputMethods.getString();
            if (!newAddress.isEmpty()) {
                user.setAddress(newAddress);
            }

            IOFile.writeToFile(IOFile.USER_PATH, userList);
            System.out.println("Thông tin người dùng đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy người dùng.");
        }
    }
}