package business.entity;

import business.utils.InputMethods;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Long productId;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void inputData() {
        System.out.println("Nhập ID sản phẩm: ");
        this.productId = InputMethods.getLong();

        System.out.println("Nhập số lượng: ");
        this.quantity = InputMethods.getInteger();
    }

    public void displayData() {
        System.out.printf("| ID sản phẩm: %d | Số lượng: %d \n", productId, quantity);
    }
}