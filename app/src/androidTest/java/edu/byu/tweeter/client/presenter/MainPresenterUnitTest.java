package edu.byu.tweeter.client.presenter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenterUnitTest {
    private MainPresenter.View mockMainView;
    private MainService mockMainService;

    private MainPresenter mainPresenterSpy;

    User user;
    AuthToken authToken;
    String post;
    String date;
    List<String> parseURLs;
    List<String> parseMentions;

    @Before
    public void setup() throws ParseException, MalformedURLException {
        mockMainView = Mockito.mock(MainPresenter.View.class);
        mockMainService = Mockito.mock(MainService.class);

        user = new User();
        authToken = new AuthToken();
        post = "Test";
        date =  "1/11/11";
        parseURLs = new ArrayList<>();
        parseMentions = new ArrayList<>();

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockMainView, authToken, user));
        Mockito.doReturn(mockMainService).when(mainPresenterSpy).getMainService();
        Mockito.doReturn(date).when(mainPresenterSpy).getFormattedDateTime();
        Mockito.doReturn(parseURLs).when(mainPresenterSpy).parseURLs(date);
        Mockito.doReturn(parseMentions).when(mainPresenterSpy).parseMentions(date);
    }

    @Test
    public void testPostStatus_succeeds() {
        Answer<Void> _logoutSucceededAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.PostStatusObserver observer = invocation.getArgument(6);
                observer.handleSuccess();
                assertEquals(invocation.getArgument(0), authToken);
                assertEquals(invocation.getArgument(1), post);
                assertEquals(invocation.getArgument(2), user);
                assertEquals(invocation.getArgument(3), date);
                assertEquals(invocation.getArgument(4), parseURLs);
                assertEquals(invocation.getArgument(5), parseMentions);
                return null;
            }
        };


        Mockito.doAnswer(_logoutSucceededAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any(),Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any() );
        mainPresenterSpy.postStatus(post);
        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).displayInfoMessage("Successfully Posted!");
    }

    @Test
    public void testPostStatus_failed() {
        Answer<Void> _failedAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.PostStatusObserver observer = invocation.getArgument(6);
                observer.handleFailure("Well this failed!");
                assertEquals(invocation.getArgument(0), authToken);
                assertEquals(invocation.getArgument(1), post);
                assertEquals(invocation.getArgument(2), user);
                assertEquals(invocation.getArgument(3), date);
                assertEquals(invocation.getArgument(4), parseURLs);
                assertEquals(invocation.getArgument(5), parseMentions);
                return null;
            }
        };

        Mockito.doAnswer(_failedAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any(),Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any() );
        mainPresenterSpy.postStatus(post);

        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).displayErrorMessage("Well this failed!");
    }

    @Test
    public void testPostStatus_exception() {
        Answer<Void> _exceptionAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.PostStatusObserver observer = invocation.getArgument(6);
                Exception exception = new Exception("Well this exception failed!");
                observer.handleFailure(exception.getMessage());
                assertEquals(invocation.getArgument(0), authToken);
                assertEquals(invocation.getArgument(1), post);
                assertEquals(invocation.getArgument(2), user);
                assertEquals(invocation.getArgument(3), date);
                assertEquals(invocation.getArgument(4), parseURLs);
                assertEquals(invocation.getArgument(5), parseMentions);
                return null;
            }
        };

        Mockito.doAnswer(_exceptionAnswer).when(mockMainService).postStatus(Mockito.any(), Mockito.any(),Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any() );
        mainPresenterSpy.postStatus(post);

        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).displayErrorMessage("Well this exception failed!");
    }
}
