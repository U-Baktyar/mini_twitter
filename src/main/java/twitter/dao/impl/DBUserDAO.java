package twitter.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.NoResultException;
import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.configuration.Profile;
import twitter.dao.UserDAO;
import twitter.entity.User;
import twitter.entity.UserType;
import twitter.exception.TwitterCommandException;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;

import java.util.List;

@Component
@Profile(active = {"prod", "default"})
public class DBUserDAO implements UserDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Injection
    public DBUserDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public User getByLogin(String login) throws UserNotFoundException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        }catch (NoResultException e){
            throw new UserNotFoundException("Пользователь с логином " + login + " не найден");
        }
        catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public User getById(int id) throws UserNotFoundException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();

        }catch (NoResultException e){
            throw new UserNotFoundException("Пользователь с id " + id + " не найден");
        }
        catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public User saveNewUser(User user) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                return user;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw new TwitterCommandException(e.getMessage());
            }
        }
    }

    @Override
    public User[] getAllUsers() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<User> users = entityManager
                    .createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public User[] getAllUsersByType(int userType) throws UnknowUserTypeException {
        UserType type = UserType.getValue(userType);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<User> users = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.typeUser = :type", User.class)
                    .setParameter("type", type)
                    .getResultList();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }
}





//@Override
//public User getByLogin(String login) throws UserNotFoundException {
//    try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
//        User user = entityManager
//                .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
//                .setParameter("login", login)
//                .getSingleResult();
//        if (Objects.isNull(user)) {
//            System.out.println("kjhgfvdrcfvghbjnkm");
//            throw new UserNotFoundException("----------------------");
//        }
//        return user;
//
//    }catch (UserNotFoundException e){
//        System.out.println("1");
//        throw e;
//    } catch (NoResultException e) {
//        System.out.println("2");
//        throw e;
//    } catch (NonUniqueResultException e) {
//        System.out.println("3");
//        throw e;
//    } catch (Exception e) {
//        System.out.println("4");
//        throw e;
//    }
//}