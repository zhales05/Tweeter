package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTask;

public class Execute {
    public static void handleThread(BackgroundTask task){ //tried Runnable
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
}
