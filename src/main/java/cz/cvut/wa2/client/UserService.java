package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import cz.cvut.wa2.client.dto.UserDTO;
import cz.cvut.wa2.client.entitites.User;

import java.util.List;

/**
 * Created by michalvlcek on 18.03.15.
 */
@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {

    public List<UserDTO> getUsers();

    public Long saveUser(UserDTO user);

    public void saveBorrow(Long userId, Long carId);
}
