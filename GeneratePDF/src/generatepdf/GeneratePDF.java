/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generatepdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GeneratePDF {

    private static Connection connection;

    public static void main(String[] args) {
        try {
            DBconnect();
            getData();
//            OutputStream file = new FileOutputStream(new File("A:\\Test.pdf"));
//
//            Document document = new Document();
//            PdfWriter.getInstance(document, file);
//
//            document.open();
//            document.add(new Paragraph("Hello World, iText"));
//            document.add(new Paragraph("My Report, iText"));
//            document.add(new Paragraph(new Date().toString()));
//
//            document.close();
//            file.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void DBconnect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmer_sys_db", "root", "");
    }

    public static String getData() throws Exception {
        DBconnect();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM expert");
        OutputStream file = new FileOutputStream(new File("A:\\Test.pdf"));

        Document document = new Document();
        PdfWriter.getInstance(document, file);

        document.open();
        document.add(new Paragraph("Hello World, iText"));
        document.add(new Paragraph("My Table, iText"));
        document.add(new Paragraph(new Date().toString()));

        
        
        System.out.println("Name\tEmail\tPhone\tLocation\tRegDate");
        while (rs.next()) {
            document.add(new Paragraph(rs.getString("name")+"\t"+rs.getString("email")+"\t"+rs.getString("phone")+"\t"+rs.getString("addr")+"\t"+rs.getString("reg_date")));
        }
        document.close();
        file.close();
        return "";
    }
}
