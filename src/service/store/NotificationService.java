package service.store;

import config.Config;
import model.store.Notification;
import service.IGenericService;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements IGenericService<Notification,Integer> {
    List<Notification> notifications = new config.Config<Notification>().readFromFile(Config.PATH_NOTIFICATION);

    @Override
    public List<Notification> findAll() {
        return notifications;
    }

    @Override
    public void save(Notification notification) {
        if(findById(notification.getId())== null){
            // add
            notifications.add(notification);
        }else {
            // update
            notifications.set(notifications.indexOf(findById(notification.getId())),notification);
        }
        new Config<Notification>().writeFromFile(Config.PATH_NOTIFICATION, notifications);
    }

    @Override
    public Notification findById(Integer id) {
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
                return notifications.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
               notifications.get(i).setStatus(false);
                new Config<Notification>().writeFromFile(Config.PATH_NOTIFICATION, notifications);
            }
        }
    }

    public void deleteByStatus() {
        List<Notification> notificationsToRemove = new ArrayList<>();

        for (Notification notification : notifications) {
            if (!notification.isStatus()) {
                notificationsToRemove.add(notification);
            }
        }

        notifications.removeAll(notificationsToRemove);
        new Config<Notification>().writeFromFile(Config.PATH_NOTIFICATION, notifications);
    }

    public void deleteNews(){
        for (int i = notifications.size() - 1; i >= 0; i--) {
            Notification notification = notifications.get(i);
            if (notification.getTitle().equals("News")){
                notifications.remove(i);
            }
        }
        new Config<Notification>().writeFromFile(Config.PATH_NOTIFICATION, notifications);
    }

    public int newID(){
        int max = 0;
        for (Notification notification : notifications) {
            if(notification.getId()>max)
                max = notification.getId();
        }
        return max+1;
    }
}
