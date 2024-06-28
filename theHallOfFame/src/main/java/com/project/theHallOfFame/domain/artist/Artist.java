package com.project.theHallOfFame.domain.artist;

import org.bson.types.ObjectId;

public class Artist {
    ObjectId id;
    String name;
    String href;

//    public artist(String id, String name, String href) {
//        this.id = id;
//        this.name = name;
//        this.href = href;
//    }

    // getter and setter
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }



}
