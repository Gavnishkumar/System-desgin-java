package main.notification;

public class ScheduledNotification implements NotificationStrategy {
    private long delayMillis;
    
    public ScheduledNotification(long delayMillis) {
        this.delayMillis = delayMillis;
    }
    
    @Override
    public void send(Notification notification) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
                System.out.println("Sending scheduled notification to " + notification.getRecipient() + ": " + notification.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Notification scheduling interrupted: " + e.getMessage());
            }
        }).start();
    }
    
}
