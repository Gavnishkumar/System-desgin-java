package main.notification;

public class SmsNotifier implements Notifier {
    private NotificationStrategy strategy;
    public SmsNotifier(NotificationStrategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public void sendNotification(Notification notification) {
        System.out.println("Sending SMS...");
        strategy.send(notification);
    }
    
}
