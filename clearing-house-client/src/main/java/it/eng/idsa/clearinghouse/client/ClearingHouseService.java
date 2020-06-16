package it.eng.idsa.clearinghouse.client;

import it.eng.idsa.clearinghouse.model.NotificationContent;

import java.util.List;

public interface ClearingHouseService {

    void registerNotification(NotificationContent message) throws  Exception;
    NotificationContent retrieveNotification(String id) throws Exception;

    List<NotificationContent> retrieveNotifications(String correlatedMessageId) throws Exception;

    List<NotificationContent> retrieveAllNotifications() throws Exception;
}
