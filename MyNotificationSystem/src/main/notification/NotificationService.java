package main.notification;

public class NotificationService {
    NotificationManager manager;
    static NotificationService instance;
    
    private NotificationService(){
        manager = new NotificationManager();
    }
    
    public static synchronized NotificationService getNotificationService(){
        if(instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void addOberserver(Notifier notifier) {
        manager.addObserver(notifier);
    }
    
    public void removeObserver(Notifier notifier) {
        manager.removeObserver(notifier);
    }

    public void sendNotification(Notification notification) {
        manager.sendNotification(notification);
    }
    
    public void sendNotificationAsync(Notification notification) {
        manager.sendNotificationAsync(notification);
    }
    
    public void shutdown() {
        manager.shutdown();
    }
}
