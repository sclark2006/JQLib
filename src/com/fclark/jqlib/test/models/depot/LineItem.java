package com.fclark.jqlib.test.models.depot;

/**
 * This class was generated by the JQLib EntityImporter 
 * by Frederick on Sat Apr 02 16:09:57 GMT-04:00 2011
 *
 */
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.column.*;

public class LineItem extends Entity<LineItem> {

	public final NUMBER id = new NUMBER("id",this,1);
	public final NUMBER productId = new NUMBER("product_id",this);
	public final NUMBER orderId = new NUMBER("order_id",this);
	public final NUMBER quantity = new NUMBER("quantity",this);
	public final NUMBER totalPrice = new NUMBER("total_price",this);

	public LineItem() {
 		 super();
		 this.setName("line_items");
	}

	public LineItem(String alias) {
 		 super(alias);
		 this.setName("line_items");
	}

        public Product getProduct() {
            return Product.alias("p").find(this.productId.get());
        }
        
        public Order getOrder() {
            return Order.alias("o").find(this.orderId.get());
        }

	 public static LineItem alias(String alias) {
		 return new LineItem(alias);
	}
}