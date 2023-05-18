package com.ase.notification.model.data;

import java.util.Date;

/**
 * Implemented by {@link RawEvent}.
 */
public interface IUnmodifiableRawEvent {

    String getId();

    String getName();

    String getDescription();

    Date getEventDate();
}
