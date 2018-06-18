package sda.pl.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sda.pl.HibernateUtil;
import sda.pl.domain.Cart;
import sda.pl.domain.Product;

import java.util.Optional;

public class CartRepository {

    public static boolean saveCart(Cart cart) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.save(cart);
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

    public static boolean saveOrUpdateCart(Cart cart) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(cart);
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

    public static Optional<Cart> findCart(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Cart cart = session.find(Cart.class, id);
            return Optional.ofNullable(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Optional<Cart> findCartByUserId(Long userId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "Select c from Cart c join fetch c.cartDetailSet where c.user.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            return Optional.ofNullable((Cart) query.getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static void deleteCart(Cart cartByUserId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();

            String removeCartDetail = "delete from CartDetail cd where cd.cart.id = :cartId";
            String removeCart = "delete from Cart c where c.id = :cartId";

            Query query = session.createQuery(removeCartDetail);
            query.setParameter("cartId", cartByUserId.getId());
            query.executeUpdate();

            Query removeCartQuery = session.createQuery(removeCart);
            removeCartQuery.setParameter("cartId", cartByUserId.getId());
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.isOpen() && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static boolean deleteProductFromCart(Cart cart, Long productId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();

            String hql = "delete from CartDetail cd where cd.product.id = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            query.executeUpdate();
//            ??? czy updateowac koszyk w metodzie usuwajacej? czy w servlecie
//            session.saveOrUpdate(cart);
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
}