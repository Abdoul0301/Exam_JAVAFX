package sn.groupeisi.examfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sn.groupeisi.examfx.dao.DBConnexion;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import sn.groupeisi.examfx.tools.Notification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentController {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;

    @FXML
    void excelbtn(ActionEvent event) {
        try {
            String sql = "SELECT * FROM produit";
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Produits");
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            int row = 0;
            while (rs.next()) {
                org.apache.poi.ss.usermodel.Row excelRow = sheet.createRow(row++);
                excelRow.createCell(0).setCellValue(rs.getString(1));
                excelRow.createCell(1).setCellValue(rs.getString(2));
                excelRow.createCell(2).setCellValue(rs.getString(3));
                excelRow.createCell(3).setCellValue(rs.getString(4));
                excelRow.createCell(4).setCellValue(rs.getString(5));
            }
            FileOutputStream file = new FileOutputStream("products.xlsx");
            workbook.write(file);
            file.close();
            workbook.close();
            Notification.NotifSuccess("Succés","Fichier Téléchager");

        } catch (SQLException e){
            throw new RuntimeException();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void pdfbtn(ActionEvent event) {

        try {
            String sql = "SELECT * FROM produit";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("product.pdf"));
            document.open();
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                document.add(new Paragraph("ID: " + rs.getInt("idP")));
                document.add(new Paragraph("Libelle: " + rs.getString("libelle")));
                document.add(new Paragraph("Quantité: " + rs.getInt("quantite")));
                document.add(new Paragraph("Prix: " + rs.getDouble("prix")));
                document.add(new Paragraph("Categorie: " + rs.getInt("idCat")));
                document.add(new Paragraph("\n"));
            }
            document.close();
            Notification.NotifSuccess("Succés","Fichier Téléchager");
        } catch (SQLException | IOException | com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }

    }

}
