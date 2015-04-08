package cz.cvut.wa2.client.dto;

import cz.cvut.wa2.client.entitites.Car;
import cz.cvut.wa2.client.entitites.Manufacturer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CarDTO
 *
 * @author Michal Vlček<mychalvlcek@gmail.com>
 * @date 28.03.15
 */
public class CarDTO implements Serializable {
    private Long id;
    private String name;
    private ManufacturerDTO manufacturer;

    public CarDTO() {
    }

    public CarDTO(Long id, String name, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;

//        List<CarDTO> tmp = new ArrayList<CarDTO>(manufacturer.getCars() != null ? manufacturer.getCars().size() : 0);
//        if (manufacturer.getCars() != null) {
//            for (Car car : manufacturer.getCars()) {
//                tmp.add(new CarDTO(car.getId(), car.getName(), car.getManufacturer()));
//            }
//        }
        this.manufacturer = new ManufacturerDTO(manufacturer.getId(), manufacturer.getTitle(), null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDTO manufacturer) {
        this.manufacturer = manufacturer;
    }
}
