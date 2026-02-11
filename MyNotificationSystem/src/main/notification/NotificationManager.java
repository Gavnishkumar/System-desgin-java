package main.notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationManager {
    private List<Notifier> notifiers;
    private ExecutorService executorService;
    private static final int THREAD_POOL_SIZE = 5;
    
    NotificationManager() {
        notifiers = new CopyOnWriteArrayList<>();
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
    
    public void addObserver(Notifier notifier) {
        notifiers.add(notifier);
    }
    
    public void removeObserver(Notifier notifier) {
        notifiers.remove(notifier);
    }
    
    public void sendNotification(Notification notification) {
        for (Notifier notifier : notifiers) {
            notifier.sendNotification(notification);
        }
    }
    
    public void sendNotificationAsync(Notification notification) {
        for (Notifier notifier : notifiers) {
            executorService.submit(() -> {
                notifier.sendNotification(notification);
            });
        }
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
    
    public boolean isActive() {
        return !executorService.isShutdown();
    }
}
