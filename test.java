public class test {

    /**
     * FUNC_DES: 处理客户订单
     */
    public void processOrder(Order order) {
        // [cb] 验证订单有效性
        if (!order.isValid()) {
            throw new IllegalArgumentException("Invalid order");
        }
        // [ce]

        // [cb] 计算订单总价
        double totalPrice = calculateTotalPrice(order);
        // [ce]

        // [cb] 扣减库存
        boolean stockUpdated = updateStock(order);
        if (!stockUpdated) {
            throw new IllegalStateException("Stock update failed");
        }
        // [ce]

        // [cb] 生成订单确认信息
        String confirmation = generateConfirmation(order, totalPrice);
        sendConfirmation(confirmation);
        // [ce]
    }

    /**
     * FUNC_DES: 计算订单总价
     */
    private double calculateTotalPrice(Order order) {
        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    /**
     * FUNC_DES: 扣减库存
     */
    private boolean updateStock(Order order) {
        for (OrderItem item : order.getItems()) {
            boolean success = InventorySystem.decreaseStock(item.getProductId(), item.getQuantity());
            if (!success) {
                return false;
            }
        }
        return true;
    }

    /**
     * FUNC_DES: 生成订单确认信息
     */
    private String generateConfirmation(Order order, double totalPrice) {
        StringBuilder confirmation = new StringBuilder();
        confirmation.append("Order ID: ").append(order.getId()).append("\n");
        confirmation.append("Total Price: ").append(totalPrice).append("\n");
        confirmation.append("Items:\n");
        for (OrderItem item : order.getItems()) {
            confirmation.append(item.getProductName()).append(" x").append(item.getQuantity()).append("\n");
        }
        return confirmation.toString();
    }

    /**
     * FUNC_DES: 发送订单确认信息
     */
    private void sendConfirmation(String confirmation) {
        // 模拟发送订单确认信息
        System.out.println("Sending confirmation: \n" + confirmation);
    }
}
