package com.sda.alina.exercises.prisonsmanagement.singleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {

    private static WriteInFile instance;

    public WriteInFile() {

    }

    public static WriteInFile getInstance(){
        if(instance == null){
            instance = new WriteInFile();
        }
        return instance;
    }

    public void writeLogs(String message){
        try {
            File logFile = new File("logFile.txt");
            if(!logFile.exists()){
                logFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(logFile, true);
            fileWriter.write(message+"\n");
            fileWriter.close();
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }

        System.out.println(message);
    }
}
