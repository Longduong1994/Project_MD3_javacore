package controller;

import model.store.Notification;
import service.IGenericService;
import service.store.NotificationService;

import java.util.List;

public class NotificationController implements IGenericService<Notification,Integer> {
    NotificationService notificationService = new NotificationService();
    @Override
    public List<Notification> findAll() {
        return notificationService.findAll();
    }

    @Override
    public void save(Notification notification) {
        notificationService.save(notification);
    }

    @Override
    public Notification findById(Integer id) {
        return notificationService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        notificationService.deleteById(id);
    }

    public void deleteByStatus() {
        notificationService.deleteByStatus();
    }

    public int newId(){
        return notificationService.newID();
    }
    public void deleteNews(){
        notificationService.deleteNews();
    }
}
