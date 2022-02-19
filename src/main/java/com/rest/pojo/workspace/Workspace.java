package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;


public class Workspace {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int i;
    private String name;
    private String type;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private HashMap<String,String> hashMap;

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public Workspace(){

    }
    public Workspace(String name, String type, String description){
        this.name=name;
        this.type=type;
        this.description=description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }


}
