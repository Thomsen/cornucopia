package com.cornucopia.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class RealmTickets extends RealmObject {

    @Required
    private String name;
    private boolean complete = false;
    private long id;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    public void toggleComplete() {
        complete = ! complete;
    }
    
}
