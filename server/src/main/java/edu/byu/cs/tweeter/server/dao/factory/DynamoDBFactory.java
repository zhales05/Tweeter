package edu.byu.cs.tweeter.server.dao.factory;

import edu.byu.cs.tweeter.server.dao.DynamoStoryDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class DynamoDBFactory implements DaoFactory{
    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoStoryDAO();
    }
}
