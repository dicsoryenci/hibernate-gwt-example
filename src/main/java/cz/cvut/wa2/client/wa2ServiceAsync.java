package cz.cvut.wa2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface wa2ServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
