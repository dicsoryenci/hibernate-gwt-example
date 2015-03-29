package cz.cvut.wa2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import cz.cvut.wa2.client.UserService;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.UserDTO;
import cz.cvut.wa2.client.entitites.Car;
import cz.cvut.wa2.client.entitites.User;
import cz.cvut.wa2.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {
    @Override
    public List<UserDTO> getUsers() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<User> users = new ArrayList<User>(session.createQuery("from User").list());

        List<UserDTO> result = new ArrayList<UserDTO>(
                users != null ? users.size() : 0);
        if (users != null) {
            for (User user : users) {
                List<CarDTO> tmp = new ArrayList<CarDTO>(
                        user.getCars() != null ? user.getCars().size() : 0);
                if (user.getCars() != null) {
                    for (Car car : user.getCars()) {
                        tmp.add(new CarDTO(car.getId(), car.getName()));
                    }
                }

                result.add(new UserDTO(user.getId(), user.getName(), tmp));
            }
        }

        session.getTransaction().commit();
        return result;
    }

    @Override
    public Long saveUser(UserDTO user) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User u = new User(user);
        session.save(u);
        session.flush();
        session.getTransaction().commit();
        return user.getId();
    }

    @Override
    public void saveBorrow(Long userId, Long carId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        User user = (User) session.get(User.class, userId);
        Car car = (Car) session.get(Car.class, carId);
        user.addCar(car);

        session.save(user);
        session.flush();

        session.getTransaction().commit();
    }
}