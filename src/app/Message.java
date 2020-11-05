package app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import helper.Operations;
import helper.Status;

public class Message implements Serializable {
    private Operations operation;
    private Status status;
    private Map<String, Object> parameters;

    public Message(Operations _operation) {
        operation = _operation;
        parameters = new HashMap<>();
    }

    public void setStatus(Status _status) {
        status = _status;
    }

    public Status getStatus() {
        return status;
    }

    public Operations getOperation() {
        return operation;
    }

    public void setParameters(String _key, Object _value) {
        parameters.put(_key, _value);
    }

    public Object getParameters(String _key) {
        return parameters.get(_key);
    }
}
