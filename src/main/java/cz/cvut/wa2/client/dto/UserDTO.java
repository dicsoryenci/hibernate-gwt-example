package cz.cvut.wa2.client.dto;

import java.io.Serializable;
import java.util.List;

/**
 * UserDTO
 *
 * @author Michal Vlček<mychalvlcek@gmail.com>
 * @date 28.03.15
 */
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private List<CarDTO> cars;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, List<CarDTO> cars) {
        this.id = id;
        this.name = name;
        this.cars = cars;
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

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
}
