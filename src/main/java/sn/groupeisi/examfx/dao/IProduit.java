package sn.groupeisi.examfx.dao;

import sn.groupeisi.examfx.entities.Produit;

import java.util.List;

public interface IProduit {
    public  int add(Produit produit);
    public List<Produit> getAll();
}
