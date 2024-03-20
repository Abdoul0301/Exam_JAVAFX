package sn.groupeisi.examfx.entities;

public class Categorie {

    private int idC;
    private  String libelle;

    public Categorie() {
    }

    public Categorie(int idC, String libelle) {
        this.idC = idC;
        this.libelle = libelle;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return  libelle ;
    }
}
