package com.company;

public class FakeAction extends FluentAction {
    private final String outputStatus;

    public FakeAction(String outputStatus) {
        this.outputStatus=outputStatus;
    }

    public FakeAction() {
        this.outputStatus="";
    }

    @Override
    StatusPayload Run(StatusPayload payload) {
        FakePayload fakePayload=(FakePayload)payload;
        fakePayload.Response+="->" + outputStatus;
        if (!outputStatus.equals("")) {
            fakePayload.Status = outputStatus;
        }
        return fakePayload;
    }
}
