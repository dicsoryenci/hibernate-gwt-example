package cz.cvut.wa2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import cz.cvut.wa2.client.CarService;
import cz.cvut.wa2.client.dto.ManufacturerDTO;
import cz.cvut.wa2.client.entitites.Car;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.entitites.Manufacturer;
import cz.cvut.wa2.client.entitites.User;
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
                result.add(new CarDTO(car.getId(), car.getName(), car.getManufacturer()));
            }
        }
        session.getTransaction().commit();
        return result;
    }

    @Override
    public List<ManufacturerDTO> getManufacturers() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Manufacturer> mans = new ArrayList<Manufacturer>(session.createQuery("from Manufacturer").list());

        List<ManufacturerDTO> result = new ArrayList<ManufacturerDTO>(
                mans != null ? mans.size() : 0);
        if (mans != null) {
            for (Manufacturer m : mans) {
//                List<CarDTO> tmp = new ArrayList<CarDTO>(
//                        m.getCars() != null ? m.getCars().size() : 0);
//                if (m.getCars() != null) {
//                    for (Car car : m.getCars()) {
//                        tmp.add(new CarDTO(car.getId(), car.getName(), car.getManufacturer()));
//                    }
//                }

                result.add(new ManufacturerDTO(m.getId(), m.getTitle(), null));
            }
        }
        session.getTransaction().commit();
        return result;
    }

    @Override
    public Long saveCar(CarDTO car, Long manId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Manufacturer man = (Manufacturer) session.get(Manufacturer.class, manId);
        ManufacturerDTO dto = new ManufacturerDTO(man.getId(), man.getTitle(), null);
        car.setManufacturer(dto);

        Car c = new Car(car);
        session.save(c);
        session.flush();
        session.getTransaction().commit();
        return car.getId();
    }
}