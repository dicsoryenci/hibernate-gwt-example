package cz.cvut.wa2.client.entitites;

import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.UserDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    private List<Car> cars;

    public User() {

    }

    public User(UserDTO dto) {
        id = dto.getId();
        name = dto.getName();
        List<CarDTO> recordDTOs = dto.getCars();
        if (recordDTOs != null) {
            ArrayList<Car> records = new ArrayList<Car>(recordDTOs.size());
            for (CarDTO recordDTO : recordDTOs) {
                records.add(new Car(recordDTO));
            }
            this.cars = records;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        if(this.cars == null){
            cars = new ArrayList<Car>();
        }

        cars.add(car);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
