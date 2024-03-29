package ra.config;

public enum ProductStatus {
        ACTIVE("Đang bán"),
        BLOCK("Hết hàng"),
        INACTIVE("Không bán");
        private final String status;
        ProductStatus(String status) {
                this.status = status;
        }
        public String getStatus() {
                return status;
        }
}
