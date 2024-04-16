package business.implement;

import business.design.ICatalogDesign;
import business.entity.Catalog;
import business.utils.IOFile;
import business.utils.InputMethods;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogImplement implements ICatalogDesign {
    public static List<Catalog> catalogList;

    static {
        catalogList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    @Override
    public void displayCatalogList() {

        System.out.println("Danh sách danh mục:");

        for (Catalog catalog : catalogList) {
            catalog.displayData();
        }
    }

    public void addNewCatalog() {
        Catalog newCatalog = new Catalog();
        System.out.println("Nhập mã danh mục: ");
        String catalogId = InputMethods.getString();
        if (catalogId.matches("^[C][A-Za-z0-9]{3}$")) {
            newCatalog.setCatalogId(catalogId);

        } else {
            System.out.println("Mã danh mục phải bắt đầu bằng ký tự 'C' và có tổng cộng 4 ký tự");
        }

        newCatalog.setCreatedAt(LocalDateTime.now());
        System.out.println("Nhập tên danh mục: ");
        newCatalog.setCatalogName(InputMethods.getString());

        System.out.println("Nhập mô tả: ");
        newCatalog.setDescription(InputMethods.getString());
        catalogList.add(newCatalog);
        IOFile.writeToFile(IOFile.CATALOG_PATH, catalogList);
        System.out.println("Danh mục mới đã được thêm thành công.");
        newCatalog.displayData();
    }

    @Override
    public void searchCatalogByName() {
        System.out.println("Nhập tên danh mục cần tìm:");
        String name = InputMethods.getString().toLowerCase();
        boolean check = false;

        System.out.println("Kết quả tìm kiếm:");
        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogName().toLowerCase().contains(name)) {
                catalog.displayData();
                check = true;
            }
        }
        if (!check) {
            System.out.println("Không tìm thấy danh mục.");
        }
    }

    @Override
    public void editCatalogInfo() {
        System.out.println("Nhập mã danh mục cần chỉnh sửa:");
        String id = InputMethods.getString();
        boolean check = false;

        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId().equals(id)) {
                System.out.println("Nhập thông tin mới cho danh mục:");
                catalog.inputData();
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy danh mục với mã: " + id);
        } else {
            IOFile.writeToFile(IOFile.CATALOG_PATH, catalogList);
            System.out.println("Thông tin danh mục đã được cập nhật thành công.");
        }
    }

    @Override
    public void hideCatalogbyId() {
        System.out.println("Nhập mã danh mục cần ẩn:");
        String id = InputMethods.getString();
        boolean check = false;

        for (Catalog catalog : catalogList) {
            if (catalog.getCatalogId().equals(id)) {
                catalog.setStatus(false);
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("Không tìm thấy danh mục với mã: " + id);
        } else {
            IOFile.writeToFile(IOFile.CATALOG_PATH, catalogList);
            System.out.println("Danh mục đã được ẩn thành công.");
        }
    }
}