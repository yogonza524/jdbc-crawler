package hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SQLManager {
	
	public static boolean createContentTableIfNotExists() {
		if (!existsTable("content")) {
			return createTable("CREATE TABLE public.content\n" + 
					"(\n" + 
					"  id character varying(64) NOT NULL,\n" + 
					"  url text,\n" + 
					"  content text,\n" + 
					"  created character varying(64),\n" + 
					"  CONSTRAINT content_pk PRIMARY KEY (id)\n" + 
					")");
		}
		else {
			return true;
		}
	}

	public static boolean existsTable(String table) {
		
		try {
			Session session = HibernateInitializr.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(
					"   SELECT COUNT(*) > 0 \n" + 
					"   FROM   information_schema.tables \n" + 
					"   WHERE  table_schema = 'public'\n" + 
					"   AND    table_name = '" + table +"'\n" + 
					"   ;");
			
			@SuppressWarnings("unchecked")
			List<Boolean> results = q.list();
			
			System.out.println("Exists table " + table + "?" + (results != null? results.get(0) : "NO"));
			
			session.close();
			
			return !results.isEmpty() ? results.get(0) : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean createTable(String query) {
		Session session = null;
		Transaction t = null;
		try {
			session = HibernateInitializr.getSessionFactory().openSession();
			t = session.beginTransaction();
			SQLQuery q = session.createSQLQuery(query);
			
			int results = q.executeUpdate();
			t.commit();
			session.close();
			
			return results == 0;
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.flush();
			}
	        if (t != null) {
	        	t.rollback();
	        }
			
			if (session != null && session.isOpen()) {
				session.close();
			}
			
			return false;
		}
	}
}
