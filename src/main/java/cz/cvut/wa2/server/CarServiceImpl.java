package cz.cvut.wa2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import cz.cvut.wa2.client.CarService;
import cz.cvut.wa2.client.entitites.Car;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
public class CarServiceImpl extends RemoteServiceServlet implements CarService {
    @Override
    public List<CarDTO> getCars() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Car> cars = new ArrayList<Car>(session.createQuery("from Car").list());

        List<CarDTO> result = new ArrayList<CarDTO>(
                cars != null ? cars.size() : 0);
        if (cars != null) {
            for (Car car : cars) {
                result.add(new CarDTO(car.getId(), car.getName()));
            }
        }
        session.getTransaction().commit();
        return result;
    }

    @Override
    public Long saveCar(CarDTO car) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Car c = new Car(car);
        session.save(c);
        session.flush();
        session.getTransaction().commit();
        return car.getId();
    }
}