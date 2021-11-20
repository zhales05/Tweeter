package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.util.FakeData;

public class DynamoFeedDAO implements FeedDAO{

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public FeedResponse getFeed(FeedRequest request) {
        assert request.getLimit() > 0;
        assert request.getTargetAlias() != null;

        List<Status> completeFeed = getDummyFeed();
        List<Status> responseFeed = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (completeFeed != null) {
                int statusIndex = getFeedStartingIndex(request.getLastPost(), completeFeed);

                for(int limitCounter = 0; statusIndex < completeFeed.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                    responseFeed.add(completeFeed.get(statusIndex));
                }

                hasMorePages = statusIndex < completeFeed.size();
            }
        }

        return new FeedResponse(responseFeed, hasMorePages);
    }


    List<Status> getDummyFeed() {
        return getFakeData().getFakeStatuses();
    }

    FakeData getFakeData() {
        return new FakeData();
    }

    private int getFeedStartingIndex(String lastFeedPost, List<Status> completeFeed) {
        int statusIndex = 1;

        if(lastFeedPost != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < completeFeed.size(); i++) {
                if(lastFeedPost.equals(completeFeed.get(i).getPost())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }

        return statusIndex;
    }


}
