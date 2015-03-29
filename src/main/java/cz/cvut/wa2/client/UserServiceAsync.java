package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import cz.cvut.wa2.client.dto.UserDTO;

import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
public interface UserServiceAsync {
    public void getUsers(AsyncCallback<List<UserDTO>> callback);

    public void saveUser(UserDTO user, AsyncCallback<Long> callback);

    public void saveBorrow(Long userId, Long carId, AsyncCallback callback);
}
