package edu.byu.cs.tweeter.model.net.response;

public class CountResponse extends Response{
    private int count;

    CountResponse(boolean success) {
        super(success);
    }

    public CountResponse(boolean success, String message) {
        super(success, message);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
