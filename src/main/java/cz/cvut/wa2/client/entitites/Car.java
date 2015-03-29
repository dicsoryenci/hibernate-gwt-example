package cz.cvut.wa2.client.entitites;

import cz.cvut.wa2.client.dto.CarDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends AbstractEntity {

    @Column
    private String name;

    @ManyToMany(mappedBy = "cars", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<User> users;

    public Car() {

    }

    public Car(CarDTO record) {
        id = record.getId();
        name = record.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
