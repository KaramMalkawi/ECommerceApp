package eCommerceApp;

public class Order {
    private ShoppingCart cart;
    private String shippingInfo;

    public Order(ShoppingCart cart, String shippingInfo) {
        this.cart = cart;
        this.shippingInfo = shippingInfo;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
}
