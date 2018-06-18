package sda.pl.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sda.pl.HibernateUtil;
import sda.pl.domain.Cart;
import sda.pl.domain.Order;
import sun.security.provider.certpath.OCSP;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    public static boolean saveOrder(Order order) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.save(order);
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

    public static List<Order> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o JOIN FETCH o.orderDetailSet ";
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

    public static List<Order> findAllWithProductName(String productName) {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o JOIN FETCH o.orderDetailSet od WHERE UPPER(od.product.name) like :productName ";
            Query query = session.createQuery(hql);
            query.setParameter("productName", "%" + productName.toUpperCase() + "%");
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

    public static List<Order> findAllByUserId(Long userId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "Select o from Order o join fetch o.orderDetailSet where o.user.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
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

    //mpje

    public static Optional<Order> findOrderById (Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            return Optional.ofNullable(session.find(Order.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}

