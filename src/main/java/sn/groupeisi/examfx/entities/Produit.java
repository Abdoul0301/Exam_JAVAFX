package sn.groupeisi.examfx.entities;

public class Produit {
    private int idP;
    private String libelle;
    private int quantite;
    private int prix;
    private Categorie categorie = new Categorie();


    public Produit(int idP, String libelle, int quantite, int prix, Categorie categorie) {
        this.idP = idP;
        this.libelle = libelle;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Produit() {
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
