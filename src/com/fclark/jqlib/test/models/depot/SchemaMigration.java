package com.fclark.jqlib.test.models.depot;

/**
 * This class was generated by the JQLib EntityImporter 
 * by Frederick on Sat Apr 02 16:09:57 GMT-04:00 2011
 *
 */
import com.fclark.jqlib.Entity;
import com.fclark.jqlib.column.*;

public class SchemaMigration extends Entity<SchemaMigration> {

	public final VARCHAR version = new VARCHAR("version",this);

	public SchemaMigration() {
 		 super();
		 this.setName("schema_migrations");
	}

	public SchemaMigration(String alias) {
 		 super(alias);
		 this.setName("schema_migrations");
	}

	 public static SchemaMigration alias(String alias) {
		 return new SchemaMigration(alias);
	}
}