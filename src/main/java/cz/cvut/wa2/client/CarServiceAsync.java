package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.ManufacturerDTO;

import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
public interface CarServiceAsync {
    public void getCars(AsyncCallback<List<CarDTO>> callback);

    public void getManufacturers(AsyncCallback<List<ManufacturerDTO>> callback);

    public void saveCar(CarDTO car, Long manId, AsyncCallback<Long> callback);
}
