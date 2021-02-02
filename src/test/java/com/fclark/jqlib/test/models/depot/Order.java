package com.fclark.jqlib.test.models.depot;

/**
 * This class was generated by the JQLib EntityImporter 
 * by Frederick on Sat Apr 02 16:09:57 GMT-04:00 2011
 *
 */
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.column.*;

public class Order extends Entity<Order> {

	public final NUMBER id = new NUMBER("id",this,1);
	public final VARCHAR name = new VARCHAR("name",this);
	public final VARCHAR address = new VARCHAR("address",this);
	public final VARCHAR email = new VARCHAR("email",this);
	public final VARCHAR payType = new VARCHAR("pay_type",this);

	public Order() {
 		 super();
		 this.setName("orders");
	}

	public Order(String alias) {
 		 super(alias);
		 this.setName("orders");
	}

        public Iterable<LineItem> getLineItems() {
            LineItem item = LineItem.alias("l");
            return item.findWhere(item.orderId.equal(this.id));
        }

	 public static Order alias(String alias) {
		 return new Order(alias);
	}
}