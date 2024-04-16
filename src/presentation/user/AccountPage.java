package presentation.user;

import business.implement.AuthenticationImplement;
import business.utils.InputMethods;

public class AccountPage {
    AuthenticationImplement authenticationImplement = new AuthenticationImplement();

    public void AccountPageMenu() {
        while (true) {
            System.out.println("==========TRANG THÔNG TIN CÁ NHÂN==========");
            System.out.println("1. Đổi mật khẩu");
            System.out.println("2. Hiển thị thông tin cá nhân");
            System.out.println("3. Chỉnh sửa thông tin cá nhân");
            System.out.println("4. Thoát");
            System.out.println("Nhập lựa chọn: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authenticationImplement.changePassword();
                    break;
                case 2:
                    authenticationImplement.displayUserInfo();
                    break;
                case 3:
                    authenticationImplement.editUserInfo();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
            }
        }
    }
}