package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MENU_TASK")
public class MenuTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MenuTaskId id;

    @ManyToOne
    @JoinColumn(name = "MENU_CODE", referencedColumnName = "MENU_CODE", insertable = false, updatable = false)
    private MainMenu mainMenu;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_CODE", referencedColumnName = "ACTIVITY_CODE", insertable = false, updatable = false)
    private Task task;

    public MenuTask() {}

    public MenuTaskId getId() {
        return id;
    }

    public void setId(MenuTaskId id) {
        this.id = id;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}