package cz.cvut.wa2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("wa2Service")
public interface wa2Service extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use wa2Service.App.getInstance() to access static instance of wa2ServiceAsync
     */
    public static class App {
        private static wa2ServiceAsync ourInstance = GWT.create(wa2Service.class);

        public static synchronized wa2ServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
