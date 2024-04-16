package business.entity;

import business.utils.IOFile;
import business.utils.InputMethods;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Catalog implements Serializable {
    private String catalogId;
    private String catalogName;
    private String description;
    private boolean status = true;
    private LocalDateTime createdAt;

    public Catalog() {
    }

    public Catalog(String catalogId, String catalogName, String description, boolean status, LocalDateTime createdAt) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void inputData() {
        boolean isExit = true;
        while (isExit) {
            System.out.println("Nhập mã danh mục: ");
            this.catalogId = InputMethods.getString();
            if (catalogId.matches("^[C][A-Za-z0-9]{3}$")) {
                isExit = false;
            } else {
                System.out.println("Mã danh mục phải bắt đầu bằng ký tự 'C' và có tổng cộng 4 ký tự");
            }
        }

        System.out.println("Nhập tên danh mục: ");
        while (isExit) {
            this.catalogName = InputMethods.getString();
            if (catalogName.isEmpty()) {
                isExit = false;
            } else {
                System.out.println("Tên danh mục không được để trống");
            }
        }

        System.out.println("Nhập mô tả: ");
        this.description = InputMethods.getString();


    }

    public void displayData() {
        System.out.printf("| Mã danh mục: %s | Tên danh mục: %s | Mô tả: %s | Trạng thái: %s \n",
                catalogId, catalogName, description, status ? "Hoạt động" : "Không hoạt động");
    }
}