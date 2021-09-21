/*
    This class will set properties of newly created users
    to a default setting
    such as: status, isActive, userRole and enable
    This class is currently used in the UserController
 */

package com.example.BookingManager.user;

public class UserAutoConfig{

    private User user;

    public UserAutoConfig(User user) {
        user.setStatus("online");
        user.setActive(true);
        user.setUserRole(UserRole.USER);
        user.setEnable(true);
        this.user = user;
    }


    public User getUser() {
        return this.user;
    }
}
