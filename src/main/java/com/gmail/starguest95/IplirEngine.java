package com.gmail.starguest95;

import com.gmail.starguest95.Impls.SearchEngine;
import com.gmail.starguest95.InOut.InOut;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by isv on 20.09.17.
 */
public class IplirEngine implements SearchEngine {

    private final String CONF_FILE_DIVIDER = ".*";
    private final int SEARCH_STRINGS_BEGINS_AT = 2;
    private String confID = "";
    private List<String> confFile = new ArrayList<>();
    private List<String> iplirFile = new ArrayList<>();
    private List<List<String >> fracturedIplir = new ArrayList<List<String>>();
    private String pathToConf;

    private List<List<String>> getFracturedIplir() {
        return fracturedIplir;
    }

    private String getConfID() {
        return confID;
    }

    public IplirEngine(String pathToConf) {
        this.pathToConf = pathToConf;
    }

    private List<String> getConfFile() {
        return confFile;
    }

    private void setConfFile(List<String> confFile) {
        this.confFile = confFile;
    }

    private List<String> getIplirFile() {
        return iplirFile;
    }

    private void setIplirFile(List<String> iplirFile) {
        this.iplirFile = iplirFile;
    }

    @Override
    public void search() {
        //read conf.txt file
        setConfFile(InOut.readFile(new File(pathToConf)));
        //delete all spaces
        setConfFile(Arrays.asList(getConfFile().stream().flatMap((p)
                -> Arrays.asList(p.replace(" ", "")).stream()).toArray(String[]::new)));
        //read .iplir file
        setIplirFile(InOut.readFile(new File(getIplirPath())));
        //delete all spaces
        setIplirFile(Arrays.asList(getIplirFile().stream().flatMap((p)
                -> Arrays.asList(p.replace(" ", "")).stream()).toArray(String[]::new)));
        fractureByID();
        readConfID();
        searchForLines();
    }

    public IplirEngine() {
        this.pathToConf = "unknown";
    }

    private String getIplirPath(){
        //return path to iplir file
        return getConfFile().get(0).substring(5);
    }

    private void searchForLines(){

        //iterate through fractured list to find needed ID
        for (int i = 0; i < getFracturedIplir().size(); i++){
            //if ID was found
            if (getFracturedIplir().get(i).get(0).substring(3).equals(getConfID())){
                //call function to process ID section
                processIDSection(getFracturedIplir().get(i));
                //stop cycle
                break;

            }
        }
    }

    private void processIDSection(List<String> idSection){
        //create new temporary variables that store adjacent data format
        List<String> tempConfFile = new ArrayList<>();
        List<String> tempIDSectionFile = new ArrayList<>();

        tempConfFile = Arrays.asList(getConfFile().stream().flatMap((p)
                -> Arrays.asList(p.replace(CONF_FILE_DIVIDER, "")).stream()).toArray(String[]::new));
        tempIDSectionFile = Arrays.asList(idSection.stream().flatMap((p)
                -> Arrays.asList(p.replace("to", "")).stream()).toArray(String[]::new));
        tempIDSectionFile = Arrays.asList(tempIDSectionFile.stream().flatMap((p)
                -> Arrays.asList(p.replace("=", "")).stream()).toArray(String[]::new));

        boolean finalFlag = true;
        //find needed strings, they begins from 3 string, so
        for (int i = SEARCH_STRINGS_BEGINS_AT; i < tempConfFile.size(); i++){
            //if needed string is found then
            if (tempIDSectionFile.stream().anyMatch(tempConfFile.get(i)::equals)){
                //do nothing
            } else {
                //if it wasn`t found, then
                finalFlag = false;
                System.out.println("String: " + getConfFile().get(i) + "; wasn`t found");
            }
        }
        if (finalFlag){
            System.out.println("Search succeded!");
        }

    }

    private void fractureByID(){
        List<String> idSection = new ArrayList<>();

        //iterate through file lines
        for (int i = 1; i < getIplirFile().size(); i++){
            //if new id section touched then add it to the list
            if (getIplirFile().get(i).equals("[id]") && (idSection.size() > 0)){
                getFracturedIplir().add(idSection);
                //clear id section from last records
                idSection = new ArrayList<>();
            } else {
                //accumulate id section records
                idSection.add(getIplirFile().get(i));
            }
        }

    }

    private void readConfID(){
        //read id from conf
        this.confID = getConfFile().get(1).substring(3);
    }

}
