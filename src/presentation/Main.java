package presentation;

import business.entity.RoleName;
import business.entity.User;
import business.implement.AuthenticationImplement;
import business.utils.IOFile;
import business.utils.InputMethods;

public class Main {
    private static MenuUser menuUser = new MenuUser();
    private static AuthenticationImplement authenticationImplement = new AuthenticationImplement();

    public static User user = null;


    public static void main(String[] args) {
        while (true) {
            System.out.println("==========MENU=========");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký admin");
            System.out.println("3. Đăng ký user");
            System.out.println("4. Thoát");
            System.out.println("Nhập chức năng: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register(true);
                    break;
                case 3:
                    register(false);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
            if (choice == 3) {
                break;
            }
        }
    }

    public static void login() {
        System.out.println("==========ĐĂNG NHẬP==========");
        System.out.println("Nhập username: ");
        String username = InputMethods.getString();
        System.out.println("Nhập password: ");
        String password = InputMethods.getString();

        User userLogin = authenticationImplement.login(username, password);
        if (userLogin == null) {
            System.err.println("Tài khoản hoặc mật khẩu không chính xác");
            System.out.println("1. Tiếp tục đăng nhập");
            System.out.println("2. Bạn chưa có tài khoản, đăng ký ngay user");
            System.out.println("3. Bạn chưa có tài khoản, đăng ký ngay admin");
            System.out.println("4. Thoát");
            System.out.println("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register(true);
                    break;
                case 3:
                    register(false);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Nhập lựa chọn không chính xác");
            }
        } else {
            if (userLogin.getRole().equals(RoleName.ROLEADMIN)) {
                user = userLogin;
                MenuAdmin.getInstance().displayMenuAdmin();
            } else if (userLogin.getRole().equals(RoleName.ROLEUSER)) {
                if (!userLogin.isStatus()) {
                    System.err.println("Tài khoản đã bị khóa, vui lòng liên hệ admin (0365981802)");
                } else {
                    user = userLogin;
                    menuUser.displayMenuUser();

                }
            } else {
                System.err.println("Không có quyền truy cập");
            }
        }
    }

    public static void register(boolean isAdmin) {
        System.out.println("==========ĐĂNG KÝ==========");
        User user = new User();
        if (isAdmin) {
            checkAdmin();
            user.inputDataAdmin();
        } else {
            user.inputData();
        }
        AuthenticationImplement.userList.add(user);
        IOFile.writeToFile(IOFile.USER_PATH, AuthenticationImplement.userList);
        System.out.println("Đăng ký thành công");
        login();
    }

    public static void checkAdmin() {
        System.out.println("Để đăng ký Admin bạn phải nhập mã: ");
        int ADMINKEY = 1802;
        System.out.println("Mời bạn nhập mã: ");
        int InputKey = InputMethods.getInteger();
        if (InputKey == ADMINKEY) {
            System.out.println("Xin chào Admin, mời bạn nhập thông tin");
        } else {
            System.out.println("Sai mã, yêu cầu không truy cập trái phép");
            System.exit(0);
        }
    }
}