package com.task_userservice.constant;

import java.io.Serializable;

public interface CommonEntityInterface<E> extends Serializable {

    public E getId();

    public String getName();
}