package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import cz.cvut.wa2.client.dto.CarDTO;

import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
@RemoteServiceRelativePath("CarService")
public interface CarService extends RemoteService {

    public List<CarDTO> getCars();

    public Long saveCar(CarDTO car);

//    /**
//     * Utility/Convenience class.
//     * Use CarService.App.getInstance() to access static instance of CarServiceAsync
//     */
//    public static class App {
//        private static final CarServiceAsync ourInstance = (CarServiceAsync) GWT.create(CarService.class);
//
//        public static CarServiceAsync getInstance() {
//            return ourInstance;
//        }
//    }
}
