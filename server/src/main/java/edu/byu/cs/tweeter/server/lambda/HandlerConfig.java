package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.dao.factory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.factory.DynamoDBFactory;

public class HandlerConfig {
    private static HandlerConfig config;

    public static HandlerConfig getInstance() {
        if (config == null) {
            config = new HandlerConfig();
        }
        return  config;
    }

 public DaoFactory getFactory() {return new DynamoDBFactory();}
}
