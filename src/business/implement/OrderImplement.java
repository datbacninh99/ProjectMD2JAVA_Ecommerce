package business.implement;

import business.design.IOrderDesign;
import business.entity.Order;
import business.entity.OrderStatus;
import business.utils.IOFile;
import business.utils.InputMethods;

import java.util.List;

public class OrderImplement implements IOrderDesign {
    public static List<Order> orderList;

    static {
        orderList = IOFile.readFromFile(IOFile.ORDER_PATH);
    }

    @Override
    public void displayUncomfirmedOrderList() {
        int unconfirmedCount = 0;
        System.out.println("Danh sách hóa đơn chưa xác nhận:");
        for (Order order : orderList) {
            if (order.getOrderStatus() == OrderStatus.WAITING) {
                order.displayData();
                unconfirmedCount++;
            }
        }
        System.out.println("Tổng số hóa đơn chưa xác nhận: " + unconfirmedCount);
    }

    @Override
    public void displayComfirmedOrderList() {
        System.out.println("Danh sách hóa đơn đã xác nhận:");
        for (Order order : orderList) {
            if (order.getOrderStatus() != OrderStatus.WAITING) {
                order.displayData();
            }
        }
    }

    @Override
    public void confirmOrderWaiting() {
        System.out.println("Nhập mã hóa đơn cần xác nhận: ");
        long orderId = InputMethods.getLong();
        for (Order order : orderList) {
            if (order.getOrderId() == orderId && order.getOrderStatus() == OrderStatus.WAITING) {
                order.setOrderStatus(OrderStatus.PROCESSING);
                System.out.println("Đã xác nhận hóa đơn thành công!");
                IOFile.writeToFile(IOFile.ORDER_PATH, orderList);
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn chưa xác nhận có mã " + orderId);
    }

    @Override
    public void cancelOrder() {
        System.out.println("Nhập mã hóa đơn cần hủy: ");
        long orderId = InputMethods.getLong();
        for (Order order : orderList) {
            if (order.getOrderId() == orderId && order.getOrderStatus() != OrderStatus.DELIVERED && order.getOrderStatus() != OrderStatus.CANCELED) {
                order.setOrderStatus(OrderStatus.CANCELED);
                System.out.println("Đã hủy đơn hàng thành công!");
                IOFile.writeToFile(IOFile.ORDER_PATH, orderList);
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn có thể hủy với mã " + orderId);
    }

    public void historyOrderData() {
        if (orderList.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
        } else {
            System.out.println("Lịch sử đơn hàng:");
            for (Order order : orderList) {
                order.displayData();
            }
        }
    }
}