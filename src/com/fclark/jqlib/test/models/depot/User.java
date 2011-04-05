package com.fclark.jqlib.test.models.depot;

/**
 * This class was generated by the JQLib EntityImporter 
 * by Frederick on Sat Apr 02 16:09:57 GMT-04:00 2011
 *
 */
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.column.*;

public class User extends Entity<User> {

	public final NUMBER id = new NUMBER("id",this,1);
	public final VARCHAR name = new VARCHAR("name",this);
	public final VARCHAR hashedPassword = new VARCHAR("hashed_password",this);
	public final VARCHAR salt = new VARCHAR("salt",this);

	public User() {
 		 super();
		 this.setName("users");
	}

	public User(String alias) {
 		 super(alias);
		 this.setName("users");
	}

	 public static User alias(String alias) {
		 return new User(alias);
	}
}