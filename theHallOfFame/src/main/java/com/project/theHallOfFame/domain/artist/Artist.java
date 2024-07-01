package com.project.theHallOfFame.domain.artist;

import org.bson.types.ObjectId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Artist {

    @Id
    ObjectId id; // mongoTemplate 고유 id

    String artistId;
    String name;
    String href;
    String images;


//    public artist(String id, String name, String href) {
//        this.id = id;
//        this.name = name;
//        this.href = href;
//    }

    // getter and setter (lombok)

}
