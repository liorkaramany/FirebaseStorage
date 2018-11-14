package com.example.liorkaramany.firebasestorage;

public class Upload {
    public String id;
    public String name;
    public String url;

    public Upload(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Upload() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
