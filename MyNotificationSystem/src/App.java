import main.notification.EmailNotifier;
import main.notification.ImmediateNotification;
import main.notification.Notification;
import main.notification.NotificationService;
import main.notification.SmsNotifier;

public class App {
    public static void main(String[] args) throws Exception {
        NotificationService service = NotificationService.getNotificationService();
        EmailNotifier emailNotifier = new EmailNotifier(new ImmediateNotification());
        SmsNotifier smsNotifier = new SmsNotifier(new ImmediateNotification());
        service.addOberserver(emailNotifier);
        service.addOberserver(smsNotifier);
        
        System.out.println("=== Synchronous Notification ===");
        service.sendNotification(new Notification(" I am logged in","Gavnish kumar"));
        
        System.out.println("\n=== Asynchronous Notifications (Multi-threaded) ===");
        for (int i = 1; i <= 5; i++) {
            service.sendNotificationAsync(new Notification("Notification " + i, "User " + i));
        }
        
        Thread.sleep(2000);
        
        service.shutdown();
        System.out.println("\nNotification service shutdown completed.");
    }
}
