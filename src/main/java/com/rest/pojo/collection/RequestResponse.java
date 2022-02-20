package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse extends RequestBase{
    Url url;
    public RequestResponse(){

    }
    public RequestResponse(Url url, String method, List<Header> header, Body body, String description){
        super(method, header, body, description);
        this.url=url;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }



}
