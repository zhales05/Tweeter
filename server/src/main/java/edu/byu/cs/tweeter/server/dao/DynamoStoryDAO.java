package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.Request;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public class DynamoStoryDAO implements StoryDAO{
    private static final String TableName = "stories"; //might change names

    private static final String HandleAttr = "alias";
    private static final String TimestampAttr = "timestamp";
    private static final String ContentAttribute = "content";
    private static final String FNameAttr = "first_name";
    private static final String LNameAttr = "last_name";
    private static final String ImageAttr = "image_url";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public Response postStatus(PostStatusRequest request) {
        Table table = dynamoDB.getTable(TableName);
       /* User user = new User();
        user.setAlias("@test");
        request = new PostStatusRequest();
        request.setUser(user);*/
        User tweeter = request.getUser();


        Item item = new Item()
                .withPrimaryKey(HandleAttr, tweeter.getAlias())
                .withString(FNameAttr, tweeter.getFirstName())
                .withString(LNameAttr, tweeter.getLastName())
                .withString(ImageAttr, tweeter.getImageUrl())
                .withString(ContentAttribute, request.getStatus().getPost())
                .withString(TimestampAttr, request.getStatus().getDate());
        table.putItem(item);

        return new Response(true, null);
    }

}
