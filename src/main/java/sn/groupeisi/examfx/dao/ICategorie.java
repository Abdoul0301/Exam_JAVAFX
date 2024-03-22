package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.Categorie;

import java.util.List;

public interface ICategorie {
    public Categorie get(int id);
    public int add (Categorie categorie);
    public List<Categorie> getAll();
}
