package hibernate;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class Repository<T> {
	
	private Class<T> clazz;
	
	public Repository(Class<T> type) {
		this.clazz = type;
	}
	
	public T byId(String id) {
		
		Session s = null;
		T result = null;
		
		try {
			s = HibernateInitializr.getSessionFactory().openSession();
			result = (T)s.createCriteria(clazz).add(Restrictions.eq("id", id)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (s != null) {
				s.close();
			}
		}
		return result;
	}
	
	public T byAttribute(String attribute, String value) {
		Session s = null;
		T result = null;
		try {
			s = HibernateInitializr.getSessionFactory().openSession();
			result = (T)s.createCriteria(clazz).add(Restrictions.eq(attribute, value)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(s != null && s.isOpen()) {
				s.close();
			}
		}
		return result;
	}
	
	public List<T> list(){
		Session s = null;
		List<T> result = null;
		
		try {
			s = HibernateInitializr.getSessionFactory().openSession();
			result = s.createCriteria(clazz).list();
			s.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (s != null && s.isOpen()) {
				s.close();
			}
		}
		
		return result;
	}
	
	public boolean save(T object) {
		Session s = null;
		Transaction t = null;
		
		System.out.println("Saving: " + object);
		
		try {
			s = HibernateInitializr.getSessionFactory().openSession();
			t = s.beginTransaction();
			s.persist(object);
			t.commit();
			s.close();
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if (s != null) {
				s.flush();
			}
	        if (t != null) {
	        	t.rollback();
	        }
			
			if (s != null && s.isOpen()) {
				s.close();
			}
			
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean exists(String key, Object value) {
		Session s = null;
		T result = null;
		try {
			s = HibernateInitializr.getSessionFactory().openSession();
			result = (T)s.createCriteria(clazz).add(Restrictions.eq(key, value)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(s != null && s.isOpen()) {
				s.close();
			}
		}
		return result != null;
	}

}