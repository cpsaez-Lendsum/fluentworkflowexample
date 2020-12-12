package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FluentWorkflow {

    private HashMap<String, ArrayList<ArrayList<FluentAction>>> chain=new HashMap<>();
    private String lastStatus=null;

    public FluentWorkflow InCaseOf(String status) {

        if (status.isBlank()) {} // TODO return exception

        if (chain.containsKey(status)) {} // TODO return exception, InCaseOf must be call once with the same status

        chain.put(status, new ArrayList<ArrayList<FluentAction>>());
        lastStatus=status;
        return this;
    }

    public FluentWorkflow ContinueWith(FluentAction action) {
        if (lastStatus==null) {} // TODO return exception, ContinueWith must be called after InCaseOf
        if (action==null) return this;

        return this.ContinueWith(new FluentAction[]{action});
    }

    public FluentWorkflow ContinueWith(FluentAction[] actions) {
        if (lastStatus==null) {} // TODO return exception, ContinueWith must be called after InCaseOf
        if (actions==null) return this;
        ArrayList<ArrayList<FluentAction>> level1=chain.get(lastStatus);
        ArrayList<FluentAction> level2=new ArrayList<>();
        level2.addAll(Arrays.asList(actions));
        level1.add(level2);
        return this;
    }

    public StatusPayload Run(StatusPayload initData) {
        StatusPayload currentData=initData;
        boolean jump=false;

        ArrayList<ArrayList<FluentAction>> continueWiths= chain.get(currentData.GetStatus());
        if (continueWiths==null) return initData;
        for (ArrayList<FluentAction> actionGroup:continueWiths) // continuewiths section
        {
            String currentStatus=currentData.GetStatus();
            String nextStatus=currentStatus;
            if (actionGroup!=null) {

                for (FluentAction actionToRun:actionGroup) // actionGroup section
                {
                    currentData=actionToRun.Run(currentData);
                    if (!currentData.GetStatus().equals(nextStatus)) {
                        nextStatus=currentData.GetStatus();
                    }
                }

                // after an actions group, if the status is a new one, just jump to the new one
                if (!currentStatus.equals(nextStatus)) {
                    jump=true;
                    break;
                }
            }
        }

        if (jump) {
            currentData=Run(currentData); // warning here: stack overflow in case of endless loop
        }

        return currentData;


    }
}
