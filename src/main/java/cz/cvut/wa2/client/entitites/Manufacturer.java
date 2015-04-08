package cz.cvut.wa2.client.entitites;

import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.ManufacturerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manufacturer
 *
 * @author Michal Vlček<mychalvlcek@gmail.com>
 * @date 01.04.15
 */
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends AbstractEntity  {
    private String title;

    @OneToMany(mappedBy="manufacturer", cascade= CascadeType.REMOVE, fetch= FetchType.LAZY)
    private List<Car> cars;

    public Manufacturer() {
    }

    public Manufacturer(ManufacturerDTO record) {
        id = record.getId();
        title = record.getTile();

        List<CarDTO> recordDTOs = record.getCars();
        if (recordDTOs != null) {
            ArrayList<Car> records = new ArrayList<Car>(recordDTOs.size());
            for (CarDTO recordDTO : recordDTOs) {
                records.add(new Car(recordDTO));
            }
            this.cars = records;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
