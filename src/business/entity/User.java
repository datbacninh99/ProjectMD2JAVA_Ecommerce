package business.entity;

import business.utils.InputMethods;

import java.io.Serializable;
import java.util.List;

import static business.implement.AuthenticationImplement.userList;

public class User implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private List<CartItem> cart;
    private RoleName role;
    private boolean status = true;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String userName, String password, String email, String phoneNumber, String address, List<CartItem> cart, RoleName role, boolean status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cart = cart;
        this.role = role;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public RoleName getRole() {
        return role;
    }



    public void setRole(RoleName role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputDataAdmin() {
        this.userId = getInputUserId();

        boolean isExit = true;
        System.out.println("Nhập họ: ");
        this.firstName = InputMethods.getString();

        System.out.println("Nhập tên: ");
        this.lastName = InputMethods.getString();

        System.out.println("Nhập tên đăng nhập: ");
        while (isExit) {
            this.userName = InputMethods.getString();
            if (userName.length() > 6 && userName.matches("^[A-Za-z][A-Za-z0-9]{5,29}$")) {
                isExit = false;
            } else {
                System.out.println("Tên đăng nhập phải có ít nhất 6 ký tự, không chứa ký tự đặc biệt");
            }
        }
        System.out.println("Nhập mật khẩu: ");
        isExit = true;
        while (isExit) {
            this.password = InputMethods.getString();
            if (password.length() >= 8) {
                isExit = false;
            } else {
                System.out.println("Mật khẩu phải có ít nhất 8 ký tự");
            }
        }

        System.out.println("Nhập địa chỉ email: ");
        isExit = true;
        while (isExit) {
            this.email = InputMethods.getString();
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                isExit = false;
            } else {
                System.out.println("Định dạng email không hợp lệ");
            }
        }

        System.out.println("Nhập số điện thoại: ");
        isExit = true;
        while (isExit) {
            this.phoneNumber = InputMethods.getString();
            if (phoneNumber.matches("((^(\\\\+84|84|0|0084){1})([35789]))+([0-9]{8})$")) {
                isExit = false;
            } else {
                System.out.println("Định dạng số điện thoại Việt Nam không hợp lệ");
            }
        }

        System.out.println("Nhập địa chỉ: ");
        this.address = InputMethods.getString();

        this.role = RoleName.ROLEADMIN;
    }

    public void inputData() {
        this.userId = getInputUserId();

        boolean isExit = true;
        System.out.println("Nhập họ: ");
        this.firstName = InputMethods.getString();

        System.out.println("Nhập tên: ");
        this.lastName = InputMethods.getString();

        System.out.println("Nhập tên đăng nhập: ");
        while (isExit) {
            this.userName = InputMethods.getString();
            if (userName.length() > 6 && userName.matches("^[A-Za-z][A-Za-z0-9]{5,29}$")) {
                isExit = false;
            } else {
                System.out.println("Tên đăng nhập phải có ít nhất 6 ký tự, không chứa ký tự đặc biệt");
            }
        }
        System.out.println("Nhập mật khẩu: ");
        isExit = true;
        while (isExit) {
            this.password = InputMethods.getString();
            if (password.length() >= 8) {
                isExit = false;
            } else {
                System.out.println("Mật khẩu phải có ít nhất 8 ký tự");
            }
    }

        System.out.println("Nhập địa chỉ email: ");
        isExit = true;
        while (isExit) {
            this.email = InputMethods.getString();
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                isExit = false;
            } else {
                System.out.println("Định dạng email không hợp lệ");
            }
        }

        System.out.println("Nhập số điện thoại: ");
        isExit = true;
        while (isExit) {
            this.phoneNumber = InputMethods.getString();
            if (phoneNumber.matches("((^(\\\\+84|84|0|0084){1})([35789]))+([0-9]{8})$")) {
                isExit = false;
            } else {
                System.out.println("Định dạng số điện thoại Việt Nam không hợp lệ");
            }
        }

        System.out.println("Nhập địa chỉ: ");
        this.address = InputMethods.getString();

        this.role = RoleName.ROLEUSER;
    }

    public void displayData() {
        System.out.printf("| Mã ID: %d | Họ: %s | Tên: %s | Tên đăng nhập: %s | Mật khẩu: %s | Địa chỉ email: %s | Số điện thoại: %s | Địa chỉ: %s | Quyền: %s | Trạng thái tài khoản: %s \n",
                userId, firstName, lastName, userName, password, email, phoneNumber, address, role, status ? "Block" : "Unblock");
    }

    private int getInputUserId() {
        //Nếu list chưa có phần tử nào thì đây chính là phần tử đầu tiên
        if (userList.isEmpty()) {
            return 1;
        } else {
            int maxId = userList.get(0).getUserId();
            for (User user : userList) {
                //Tìm ra id lớn nhất hiện có trong list
                if (user.getUserId() > maxId)
                    maxId = user.getUserId();
            }
            return ++maxId;
        }
    }
}