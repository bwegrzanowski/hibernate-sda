package sda.pl.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sda.pl.HibernateUtil;
import sda.pl.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public static boolean saveProduct(Product product) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static boolean saveOrUpdate(Product product) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.isOpen() && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static Optional<Product> findProduct(Long id) {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            Product product = session.find(Product.class, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static List<Product> findAll() {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT p FROM Product p ";
            Query query = session.createQuery(hql);
            List resultList = query.getResultList();

            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static List<Product> findAllWithPriceNetLessThan(BigDecimal price) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT p from Product p where p.price.priceNet < :price " +
                    "order by p.price.priceNet desc ";
            Query query = session.createQuery(hql);
            query.setParameter("price", price);
            query.setMaxResults(100);

            List resultList = query.getResultList();
            return resultList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Long countAll() {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "Select count(p) from Product p";
            Query query = session.createQuery(hql);
            Long singleResult = (Long) query.getSingleResult();
            return singleResult;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
