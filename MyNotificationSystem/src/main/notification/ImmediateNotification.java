package main.notification;

public class ImmediateNotification implements NotificationStrategy {
   
    @Override
    public void send(Notification notification) {
        System.out.println("Sending immediate notification to " + notification.getRecipient() + ": " + notification.getMessage());
    }
    
}
