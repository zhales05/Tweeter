package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.factory.DaoFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, Response> {

    @Override
    public Response handleRequest(PostStatusRequest request, Context context) {
        DaoFactory factory = HandlerConfig.getInstance().getFactory();
        StatusService statusService= new StatusService(factory);
        return statusService.postStatus(request);
    }
}
