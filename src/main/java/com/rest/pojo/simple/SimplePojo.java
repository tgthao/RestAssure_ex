package com.rest.pojo.simple;

public class SimplePojo {
    public SimplePojo(String key1, String key2){
        this.key1=key1;
        this.key2=key2;
    }
    public SimplePojo(){
        super();
    }
    public String getKey1() {
        return key1;
    }

    private String key1;

    public String getKey2() {
        return key2;
    }
    private String key2;
}
