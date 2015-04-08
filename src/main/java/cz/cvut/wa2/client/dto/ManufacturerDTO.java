package cz.cvut.wa2.client.dto;

import java.io.Serializable;
import java.util.List;

/**
 * ManufacturerDTO
 *
 * @author Michal Vlček<mychalvlcek@gmail.com>
 * @date 01.04.15
 */
public class ManufacturerDTO implements Serializable {
    private Long id;
    private String title;
    private List<CarDTO> cars;

    public ManufacturerDTO() {
    }

    public ManufacturerDTO(Long id, String title, List<CarDTO> cars) {
        this.id = id;
        this.title = title;
        this.cars = cars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTile() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
}