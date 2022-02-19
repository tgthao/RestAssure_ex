package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {
    Info info;
    List<Object> item;
    public Collection(){

    }
    public Collection(Info info, List<Object> item){
        this.info=info;
        this.item=item;
    }
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Object> getItem() {
        return item;
    }

    public void setItem(List<Object> item) {
        this.item = item;
    }



}
