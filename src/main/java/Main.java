import cz.cvut.wa2.client.entitites.Car;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by michalvlcek on 18.03.15.
 */
public class Main {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            org.hibernate.Transaction tr = session.beginTransaction();
            Car car = new Car();
            car.setName("testz");
            session.save(car);
            session.flush();

//            String queryString = "SELECT c FROM Car c WHERE c.manufacturer_id IN (:value)";
//            return session.createQuery(queryString).setParameter("value", value).getResultList();

            tr.commit();

        } finally {
            session.close();
        }
    }
}
