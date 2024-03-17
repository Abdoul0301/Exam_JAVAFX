package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.User;

public interface IUser {
    public User seConnecter(String email, String password);
}
