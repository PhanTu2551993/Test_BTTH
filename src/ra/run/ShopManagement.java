package ra.run;
import ra.business.entity.Categories;
import ra.business.entity.Product;
import ra.config.ProductStatus;

import java.util.*;
import java.util.regex.Pattern;


public class ShopManagement {
    List<Categories> categoriesList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    public ShopManagement() {
        // Thêm giá trị mặc định cho categoriesList
        for (int i = 1; i <= 5; i++) {
            Categories category = new Categories();
            category.setCatalogId(i); // Cài đặt mã danh mục
            category.setCatalogName("Danh mục " + i); // Cài đặt tên danh mục
            category.setDescription("Mô tả danh mục " + i); // Cài đặt mô tả danh mục
            category.setCatalogStatus(true); // Cài đặt trạng thái danh mục
            categoriesList.add(category); // Thêm danh mục vào danh sách
        }

        // Thêm giá trị mặc định cho productList
            for (int i = 1; i <= 5; i++) {
                Product product = new Product(
                        "S00" + i, // Mã sản phẩm
                        "Sản phẩm " + i, // Tên sản phẩm
                        i * 10000, // Giá sản phẩm
                        "Mô tả sản phẩm " + i, // Mô tả sản phẩm
                        new Date(), // Ngày nhập (lấy ngày hiện tại)
                        i, // Mã danh mục sản phẩm
                        ProductStatus.ACTIVE // Trạng thái sản phẩm
                );
                productList.add(product);
            }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShopManagement shopManagement = new ShopManagement();
        do {
            System.out.println("************SHOP MENU************");
            System.out.println("1. Quản lý danh mục sản phẩm");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    shopManagement.displayCategoriesMenu(scanner, shopManagement);
                    break;
                case 2:
                    shopManagement.displayProductMenu(scanner, shopManagement);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1-3");
            }
        } while (true);
    }

    public void displayCategoriesMenu(Scanner scanner, ShopManagement shopManagement) {
        boolean isExit = true;
        do {
            System.out.println("*************CATEGORIES MENU************");
            System.out.println("1. Nhập thông tin các danh mục");
            System.out.println("2. Hiển thị thông tin các danh mục");
            System.out.println("3. Cập nhật thông tin các danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Cập nhật trạng thái danh mục");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    shopManagement.inputListCategories(scanner);
                    break;
                case 2:
                    shopManagement.displayListCategories();
                    break;
                case 3:
                    shopManagement.deleteOrUpdateCategory(scanner, false);
                    break;
                case 4:
                    shopManagement.deleteOrUpdateCategory(scanner, true);

                    break;
                case 5:
                    shopManagement.updateCategorieStatus(scanner);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-6");
            }
        } while (isExit);
    }

    public void inputListCategories(Scanner scanner) {
        System.out.println("Nhập số danh mục cần nhập thông tin:");
        int numberOfCategories = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfCategories; i++) {
            Categories category = new Categories();
            category.inputData(true,scanner, categoriesList);
            categoriesList.add(category);
        }
    }
    public void displayListCategories() {
        for (Categories category : categoriesList){
            category.displayData();
        }
    }
    public Categories getCategoryById(int categoryId) {
        for (Categories category : categoriesList) {
            if (category.getCatalogId() == categoryId) {
                return category;
            }
        }
        System.err.println("Mã danh mục không tìm thấy");
        return null;
    }
    public void deleteOrUpdateCategory(Scanner scanner, boolean isDelete) {
        String action = isDelete ? "xóa" : "cập nhật";
        System.out.println("Nhập vào mã danh mục cần " + action + ":");
        do {
            int  categoryId = Integer.parseInt(scanner.nextLine());
            Categories categoryToUpdateOrDelete = getCategoryById(categoryId);
                if (categoryToUpdateOrDelete != null) {
                        if (isDelete) {
                            // Kiểm tra danh mục có sản phẩm chưa
                    boolean isExistProduct = false;
                    for (Product product : productList) {
                        if (product.getCatalogId() == categoryId) {
                            isExistProduct = true;
                            break;
                        }
                    }
                    if (isExistProduct) {
                        System.err.println("Danh mục đang chứa sản phẩm, không thể xóa");
                    } else {
                        // Thực hiện xóa
                        categoriesList.remove(categoryToUpdateOrDelete);
                        System.out.println("Danh mục đã được xóa thành công.");
                    }
                } else {
                    System.out.println("Thông tin cũ ");
                    categoryToUpdateOrDelete.displayData();
                    System.out.println("Nhập thông tin mới");
                    categoryToUpdateOrDelete.inputData(false,scanner,categoriesList);
                    System.out.println("Cập nhật thành công");
                }
                break;
            }
        } while (true);
    }

    public void updateCategorieStatus(Scanner scanner) {
        System.out.println("Nhập vào mã danh mục cần cập nhật trạng thái:");
        int catalogId = Integer.parseInt(scanner.nextLine());
        Categories categoryToUpdate = getCategoryById(catalogId);
        if (categoryToUpdate != null) {
            categoryToUpdate.setCatalogStatus(!categoryToUpdate.isCatalogStatus());
            System.out.println("Trạng thái của danh mục đã được cập nhật.");
        } else {
            System.err.println("Mã danh mục không tồn tại");
        }
    }


    public void displayProductMenu(Scanner scanner, ShopManagement shopManagement) {
        boolean isExit = true;
        do {
            System.out.println("*************PRODUCT MENU************");
            System.out.println("1. Nhập thông tin các sản phẩm\n" +
                    "2. Hiển thị thông tin các sản phẩm\n" +
                    "3. Sắp xếp các sản phẩm theo giá\n" +
                    "4. Cập nhật thông tin sản phẩm theo mã sản phẩm\n" +
                    "5. Xóa sản phẩm theo mã sản phẩm\n" +
                    "6. Tìm kiếm các sản phẩm theo tên sản phẩm\n" +
                    "7. Tìm kiếm sản phẩm trong khoảng giá a – b (a,b nhập từ bàn\n" +
                    "phím)\n" +
                    "8. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    shopManagement.inputListProduct(scanner);
                    break;
                case 2:
                    shopManagement.displayListProduct();
                    break;
                case 3:
                    shopManagement.sortProductsByPrice();
                    break;
                case 4:
                    shopManagement.deleteOrUpdateProduct(scanner, false);
                    break;
                case 5:
                    shopManagement.deleteOrUpdateProduct(scanner, true);
                    break;
                case 6:
                    shopManagement.searchProductByName(scanner);
                    break;
                case 7:
                    shopManagement.searchProductByPriceRange(scanner);
                    break;
                case 8:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-6");
            }
        } while (isExit);
    }

    public void displayListProduct() {
        for (Product product : productList){
            product.displayData(categoriesList);
        }
    }
    public void inputListProduct(Scanner scanner) {
        System.out.println("Nhập số sản phẩm cần nhập thông tin:");
        int numberOfProduct = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfProduct; i++) {
            Product product = new Product();
            product.inputData(true,scanner,categoriesList,productList);
            productList.add(product);
        }
    }

    public void sortProductsByPrice() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Float.compare(p1.getPrice(), p2.getPrice());
            }
        });
        System.out.println("Đã sắp xếp danh sách sản phẩm theo giá thành công.");
    }

    public Product getProductById(String productId) {
        if (!Pattern.matches("^[CSA].{3}$", productId)) {
            System.err.println("Mã sản phẩm không hợp lệ, vui lòng nhập lại.");
            return null;
        }
        for (Product product : productList) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }

        System.err.println("Mã sản phẩm không tìm thấy");
        return null;
    }
    public void deleteOrUpdateProduct(Scanner scanner, boolean isDelete) {
        String action = isDelete ? "xóa" : "cập nhật";
        System.out.println("Nhập vào mã sản phẩm cần " + action + ":");
        do {
            String  productId = scanner.nextLine();
            Product productToUpdateOrDelete = getProductById(productId);
            if (productToUpdateOrDelete != null) {
                if (isDelete) {
                    productList.remove(productToUpdateOrDelete);
                    System.out.println("Sản phẩm đã được xóa thành công.");
                } else {
                    System.out.println("Thông tin cũ ");
                    productToUpdateOrDelete.displayData(categoriesList);
                    System.out.println("Nhập thông tin mới");
                    productToUpdateOrDelete.inputData(false, scanner, categoriesList, productList);
                    System.out.println("Cập nhật thành công");
                }
                break;
            }
        } while (true);
    }

    public void searchProductByName(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm cần tìm kiếm: ");
        String searchName = scanner.nextLine();
        // dùng Stream  để lọc các sản phẩm có tên trùng khớp
        List<Product> foundProducts = productList.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(searchName))
                .toList();
        // Hiển thị kết quả
        if (!foundProducts.isEmpty()) {
            foundProducts.forEach(product -> product.displayData(categoriesList));
        } else {
            System.out.println("Không tìm thấy sản phẩm nào có tên là \"" + searchName + "\"");
        }
    }

    public void searchProductByPriceRange(Scanner scanner) {
        System.out.print("Nhập giá thấp nhất (a): ");
        float minPrice = Float.parseFloat(scanner.nextLine());
        System.out.print("Nhập giá cao nhất (b): ");
        float maxPrice = Float.parseFloat(scanner.nextLine());
        //lọc các sản phẩm theo khoảng giá
        List<Product> foundProducts = productList.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .toList();
        // Hiển thị kết quả
        if (!foundProducts.isEmpty()) {
            foundProducts.forEach(product -> product.displayData(categoriesList));
        } else {
            System.out.println("Không tìm thấy sản phẩm nào trong khoảng giá từ " + minPrice + " đến " + maxPrice);
        }
    }


}
