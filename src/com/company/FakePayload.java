package com.company;

public class FakePayload implements StatusPayload {

    public String Request;
    public String Response;
    public String Status;


    public FakePayload(String request, String response, String status) {
        this.Request = request;
        this.Response = response;
        this.Status = status;
    }

    @Override
    public String GetStatus() {
        return this.Status;
    }
}

