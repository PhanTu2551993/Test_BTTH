package ra.business.entity;
import ra.config.ProductStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.util.regex.Pattern;

public class Product {
    private String productId;
    private String productName;
    private float price;
    private String description;
    private Date created;
    private int catalogId;
    private ProductStatus productStatus;

    public Product() {
    }

    public Product(String productId, String productName, float price, String description, Date created, int catalogId, ProductStatus productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.created = created;
        this.catalogId = catalogId;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void inputData(boolean isAdd,Scanner scanner, List<Categories> categoriesList, List<Product> productList) {
        if (isAdd){
            this.productId = inputProductId(scanner, productList);
             }
        this.productName = inputProductName(scanner, productList);
        this.price = inputPrice(scanner);
        this.description = inputDescription(scanner);
        this.created = inputCreated(scanner);
        this.catalogId = inputCatalogId(scanner, categoriesList);
        this.productStatus = inputProductStatus(scanner);
    }

    public String inputProductId(Scanner scanner, List<Product> productList) {
        System.out.println("Nhập vào mã sản phẩm:");
        do {
            String productId = scanner.nextLine();
            // kiểm tra teen nhập vào
            if (Pattern.matches("^[CSA].{3}$",productId)) {
                boolean isExist = false;
                //Kiểm tra trùng lặp
                for (Product product : productList) {
                    if (product.getProductId().equals(productId)) {
                        // Nếu trùng đổi cờ về true và thoát vl
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    //Nếu trùng thông báo
                    System.err.println("Mã sản phẩm đã tồn tại, vui lòng nhập lại");
                } else {
                    //Không trùng trả về ID nhập vào
                    return productId;
                }
            } else {
                System.err.println("Mã sản phẩm không hợp lệ, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputProductName(Scanner scanner, List<Product> productList) {
        System.out.println("Nhập vào tên sản phẩm:");
        do {
            String productName = scanner.nextLine();
            // kiểm tra tên nhập vào lớn hơn 10 ký tự và nhỏ hơn 50 ký tự
            if (productName.length() >= 10 && productName.length() <= 50) {
                boolean isExist = false;
                // Kiểm tra trùng lặp
                for (Product product : productList){
                    if (product.getProductName().equals(productName)) {
                        // Bị trùng => thoát
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    //Trùng tên Sp thông báo ra
                    System.err.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                } else {
                    // Không trùng trả về tên
                    return productName;
                }
            } else {
                System.err.println("Tên sản phẩm có từ 10-50 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }

    public float inputPrice(Scanner scanner) {
        System.out.println("Nhập vào giá sản phẩm:");
        do {
            float price = Float.parseFloat(scanner.nextLine());
            if (price > 0) {
                return price;
            } else {
                System.err.println("Giá sản phẩm phải lớn hơn 0, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println("Nhập vào mô tả sản phẩm:");
        return scanner.nextLine();
    }

    public Date inputCreated(Scanner scanner) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Nhập vào ngày nhập sản phẩm:");
        do {
            String created = scanner.nextLine();
            try {
                Date returnCreated = sdf.parse(created);
                return returnCreated;
            } catch (ParseException e) {
                System.err.println("Định dạng ngày nhập không đúng, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình xử lý, vui lòng nhập lại");
            }

        } while (true);
    }

    public int inputCatalogId(Scanner scanner, List<Categories> categoriesList) {
        System.out.println("Chọn danh mục của sản phẩm:");
         for (Categories category : categoriesList){
            if (category.isCatalogStatus()) {
                System.out.printf("%d.%s\n", category.getCatalogId(), category.getCatalogName());
            }
        }
        System.out.print("Lựa chọn của bạn: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return categoriesList.get(choice - 1).getCatalogId();
    }

    public ProductStatus inputProductStatus(Scanner scanner) {
        System.out.println("Nhập vào trạng thái sản phẩm:");
        do {
            String statusInput = scanner.nextLine().toUpperCase();
            try {
                ProductStatus status = ProductStatus.valueOf(statusInput);
                return status;
            } catch (IllegalArgumentException e) {
                System.err.println("Trạng thái không hợp lệ, vui lòng nhập lại");
            }
        } while (true);
    }

    public void displayData(List<Categories> categoriesList) {
        System.out.printf("Mã sản phẩm: %s | Tên sản phẩm: %s | Giá: %f\n", this.productId, this.productName, this.price);
        System.out.printf("Mô tả: %s | Ngày nhập: %s\n", this.description, this.created.toString());
        String catalogName = "";
        for (Categories category : categoriesList){
            if (category.getCatalogId() == this.catalogId) {
                catalogName = category.getCatalogName();
                break;
            }
        }
        System.out.printf("Danh mục: %s | Trạng thái: %s\n", catalogName,
                productStatus.getStatus());
    }

}
