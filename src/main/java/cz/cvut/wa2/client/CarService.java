package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.ManufacturerDTO;

import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
@RemoteServiceRelativePath("CarService")
public interface CarService extends RemoteService {

    public List<CarDTO> getCars();

    public List<ManufacturerDTO> getManufacturers();

    public Long saveCar(CarDTO car, Long manId);
}
