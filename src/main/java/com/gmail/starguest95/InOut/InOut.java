package com.gmail.starguest95.InOut;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isv on 20.09.17.
 */
public class InOut {

    public static List<String> readFile(File file){
        //read all lines of the file
        List<String> fileLines = new ArrayList<>();
        try {
            fileLines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println("error with reading file");
            e.printStackTrace();
        }
        //return file stored in list
        return fileLines;
    }



}
