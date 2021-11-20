package edu.byu.cs.tweeter.server.dao.factory;

import edu.byu.cs.tweeter.server.dao.StoryDAO;

public interface DaoFactory {
    StoryDAO getStoryDAO();
}
