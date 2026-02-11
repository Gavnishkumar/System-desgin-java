package main.notification;

public class EmailNotifier  implements Notifier {
    private NotificationStrategy strategy;
    public EmailNotifier(NotificationStrategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public void sendNotification(Notification notification) {
        System.out.println("Sending email...");
        strategy.send(notification);
    }
}
