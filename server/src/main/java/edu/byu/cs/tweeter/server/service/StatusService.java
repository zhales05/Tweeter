package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.dao.DynamoStoryDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.factory.DaoFactory;

public class StatusService {
    DaoFactory factory;

    public StatusService() {
    }

    public StatusService(DaoFactory factory) {
        this.factory = factory;
    }

    public Response postStatus(PostStatusRequest request) {
        StoryDAO storyDAO = factory.getStoryDAO();
        // validateToken(request.getAuthToken());
        return storyDAO.postStatus(request);
        // return new Response(true, null);
    }

    public FeedResponse getFeed(FeedRequest request) {
        return getFeedDAO().getFeed(request);
    }

    FeedDAO getFeedDAO() {
        return new DynamoFeedDAO();
    } //will need to be deleted

}
