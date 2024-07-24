package com.project.theHallOfFame.repository.user;

import com.project.theHallOfFame.domain.artist.Artist;
import com.project.theHallOfFame.domain.user.UserDetails;
import com.project.theHallOfFame.domain.user.UserJoinInput;
import com.project.theHallOfFame.domain.user.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class MongoTemplateUserRepository implements UserRepository{

private final MongoTemplate mongoTemplate;
private final String COLLECTION_NAME = "userAccount";

    @Override
    public boolean getUserExist(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.exists(query, COLLECTION_NAME);
    }

    @Override
    public UserDetails getUserDetails(String userId) {

        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, UserDetails.class, COLLECTION_NAME);

    }

    @Override
    public UserSecurity getUserSecurity(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, UserSecurity.class, COLLECTION_NAME);
    }

    @Override
    public void saveUserAccount(UserJoinInput userJoinInput) {
        mongoTemplate.save(userJoinInput, COLLECTION_NAME);
        System.out.println("[Insert] DB에 user 정보를 저장 했습니다. userId = " + userJoinInput.getUserId());
    }


}
