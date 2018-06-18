package sda.pl.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sda.pl.domain.Color;
import sda.pl.HibernateUtil;
import sda.pl.domain.Product;
import sda.pl.domain.ProductType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static boolean saveOrUpdateProduct(Product product) {
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
            if (session != null && session.isOpen()) {
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
            if (session != null && session.isOpen()) {
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
            if (session != null && session.isOpen()) {
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

    public static List<Product> findByNameCriteriaQuery(String name) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> from = query.from(Product.class);
            query.select(from);
            Predicate whereNameLike = cb.like(from.get("name"), "%" + name + "%");
            Predicate redProduct = cb.equal(from.get("color"), Color.RED);
            Predicate whereNameLikeAndColorRed = cb.and(whereNameLike, redProduct);
//            cb.and();
            query.where(whereNameLikeAndColorRed);
            return session.createQuery(query).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Product> findAllByCategory(ProductType category) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT p FROM Product p where p.productType = :category";
            Query query = session.createQuery(hql);
            query.setParameter("category", category);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

//IMPORTANT - DIRTY CHECKING: jesli jestesmy w transakcji i pobieramy encje to nawet
// bez zapisania transakcji, dane zostana dodane/zmienione
//    public static boolean findProductWithMagic(Long id){
//        Session session = null;
//
//        try {
//            session = HibernateUtil.openSession();
//            session.getTransaction().begin();
//            Product product = session.find(Product.class, id);
//            product.setName(product.getName()+" ++");
//          // product.addStock(WarehouseName.COMPLAINT, new BigDecimal(6));
//            product.getStockSet().clear();
//            session.getTransaction().commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (session != null && session.isOpen() && session.getTransaction().isActive()) {
//                session.getTransaction().rollback();
//            }
//            return false;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
}
