package com.article.demos;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @author a
 */
public class MainTabEntity implements CustomTabEntity{

    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public MainTabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
