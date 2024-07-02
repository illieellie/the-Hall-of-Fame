package com.project.theHallOfFame.outputView;

public class OutputView {

    private OutputView(){}

    public static void dataNotFound(String data,String methodName){
        System.out.println("[Log] '"+data+"' not found from "+methodName);
    }

}
