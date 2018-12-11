package fi.hut.soberit.agilefant.db.hibernate.notification;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.springframework.context.ApplicationContext;

import fi.hut.soberit.agilefant.business.NotificationBusiness;
import fi.hut.soberit.agilefant.business.impl.NotificationEmailSender;
import fi.hut.soberit.agilefant.core.ApplicationContextHolder;
import fi.hut.soberit.agilefant.model.Story;
import fi.hut.soberit.agilefant.model.notification.NotificationEvent;
import fi.hut.soberit.agilefant.model.notification.NotificationEventType;


/**
 * Hibernate listener which listens to the common "Database" events and handle them
 * via {@link NotificationBusiness}.
 */
public class NotificationEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    private NotificationBusiness notificationBusiness;

    public void onPostInsert(PostInsertEvent postInsertEvent) {
        checkState();
        final NotificationEvent notificationEvent =
                new NotificationEvent(postInsertEvent.getEntity(), NotificationEventType.INSERT);

        // TODO: fill event properties

        notificationBusiness.handleNotificationEvent(notificationEvent);
    }

    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        checkState();

        if (checkIgnore(postUpdateEvent)) {
            return;
        }

        final NotificationEvent notificationEvent =
                new NotificationEvent(postUpdateEvent.getEntity(), NotificationEventType.UPDATE);

        // TODO: fill event properties

        notificationBusiness.handleNotificationEvent(notificationEvent);
    }

    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        checkState();
        notificationBusiness.handleNotificationEvent(
                new NotificationEvent(postDeleteEvent.getEntity(), NotificationEventType.DELETE));
    }

    //--------------------------------------------------- HELPER METHODS -----------------------------------------------

    private void checkState() {
        if (notificationBusiness == null) {
            final ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
            if (applicationContext == null) {
                throw new IllegalStateException("Cannot access Spring application context!");
            }

            notificationBusiness = (NotificationBusiness) applicationContext.getBean(
                    NotificationEmailSender.NOTIFICATION_BUSINESS_BEAN_NAME);
        }
    }

    private boolean checkIgnore(PostUpdateEvent postUpdateEvent) {
        if (postUpdateEvent.getEntity() instanceof Story) {
            int[] dirtyProperties = postUpdateEvent.getDirtyProperties();
            if (dirtyProperties.length == 1) {
                if (postUpdateEvent.getPersister().getPropertyNames()[dirtyProperties[0]] == "treeRank") {
                    return true;
                }
            }
        }

        return false;
    }
}
