package com.wbteam.app.base.interf;

import java.io.Serializable;
import java.util.List;

import com.wbteam.onesearch.app.model.Entity;

public interface ListEntity<T extends Entity> extends Serializable {

    public List<T> getList();
}
