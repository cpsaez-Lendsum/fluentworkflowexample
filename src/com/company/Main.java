package com.company;

public class Main {

    public static void main(String[] args) {

        // setup fake actions
        FakeAction actionGo2=new FakeAction("2"); // will go status 2
        FakeAction actionGo3=new FakeAction("3"); // will go status 3
        FakeAction actionGo4=new FakeAction("4"); // will go status 4
        FakeAction actionNoGo=new FakeAction(); // will go same status


        // setup workflow
        FluentWorkflow workflow=new FluentWorkflow()
                .InCaseOf("1").ContinueWith(actionGo3)
                .InCaseOf("2").ContinueWith(new FakeAction[]{actionNoGo, actionNoGo, actionNoGo})
                              .ContinueWith(actionGo4)
                .InCaseOf("3").ContinueWith(actionGo2)
                .InCaseOf("4"); //status 4 is the end

        // TODO .InCaseOf("5).ContinueWith(actionNoGo, actionNoGo, actionNogo)
        //                   .If(FluentEvaluator).ContinueWith(actionGo5)
        //                       ^^^^^

        // Run the process

        FakePayload data=new FakePayload("Request:", "Response:", "1");
        data=(FakePayload) workflow.Run(data);

        System.console().writer().print(data.Response);

    }
}
