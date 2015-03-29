package cz.cvut.wa2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import cz.cvut.wa2.client.wa2Service;

public class wa2ServiceImpl extends RemoteServiceServlet implements wa2Service {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}