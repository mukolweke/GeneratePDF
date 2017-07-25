package com.javahonk;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.*;

public class CreatePdf {

    private static Connection connection;

    public static Document createPDF(String file, String type) throws Exception, SQLException {

        Document document = null;

        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        addMetaData(document, type);

        addTitlePage(document, type);

        createTable(document, type);

        document.close();

        return document;

    }

    private static void addMetaData(Document document, String type) {
        if (type.equals("expert")) {
            document.addTitle("FEWS EXPERT REPORT");
            document.addSubject("FEWS EXPERT REPORT");
            document.addAuthor("FEWS APPLICATION ADMIN");
            document.addCreator("FEWS APPLICATION ADMIN");

        } else if (type.equals("farmer")) {
            document.addTitle("FEWS FARMER REPORT");
            document.addSubject("FEWS FARMER REPORT");
            document.addAuthor("FEWS APPLICATION ADMIN");
            document.addCreator("FEWS APPLICATION ADMIN");

        }
    }

    private static void addTitlePage(Document document, String type)
            throws DocumentException {
        if (type.equals("expert")) {
            Paragraph preface = new Paragraph();
            creteEmptyLine(preface, 1);
            preface.add(new Paragraph("FEWS Expert Report"));

            creteEmptyLine(preface, 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            preface.add(new Paragraph("Report created on "
                    + simpleDateFormat.format(new Date())));
            document.add(preface);
        } else if (type.equals("farmer")) {
            Paragraph preface = new Paragraph();
            creteEmptyLine(preface, 1);
            preface.add(new Paragraph("FEWS Farmer Report"));

            creteEmptyLine(preface, 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            preface.add(new Paragraph("Report created on "
                    + simpleDateFormat.format(new Date())));
            document.add(preface);
        }

    }

    private static void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void createTable(Document document, String type) throws DocumentException, Exception {

        if (type.equals("expert")) {
            Paragraph paragraph = new Paragraph();
            creteEmptyLine(paragraph, 2);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(8);

            PdfPCell c1 = new PdfPCell(new Phrase("#"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Full Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Email"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Phone"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Expert Field"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Location"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Acc Status"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("RegDate"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            for (int i = 1; i <= countUser(type); i++) {
                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(i + "");
                table.addCell(getName(i, type));
                table.addCell(getEmail(i, type));
                table.addCell(getPhone(i, type));
                table.addCell(getField(i));
                table.addCell(getLocation(i, type));
                table.addCell(getStatus(i, type));
                table.addCell(getRegdate(i, type));
            }
            document.add(table);
        } else if (type.equals("farmer")) {
            Paragraph paragraph = new Paragraph();
            creteEmptyLine(paragraph, 2);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(7);

            PdfPCell c1 = new PdfPCell(new Phrase("#"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Full Name"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Email"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Phone"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Location"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Acc Status"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("RegDate"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            for (int i = 1; i <= countUser(type); i++) {
                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_LEFT);
                table.addCell(i + "");
                table.addCell(getName(i, type));
                table.addCell(getEmail(i, type));
                table.addCell(getPhone(i, type));
                table.addCell(getLocation(i, type));
                table.addCell(getStatus(i, type));
                table.addCell(getRegdate(i, type));
            }
            document.add(table);
        }

    }

    private static void connectDb() throws SQLException, Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmer_sys_db", "root", "");
    }

    private static String getName(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT name FROM expert WHERE expert_id =" + i);
            rs.next();
            return rs.getString("name");
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT name FROM farmer WHERE farmer_id =" + i);
            rs.next();
            return rs.getString("name");
        }
        return "";
    }

    private static String getEmail(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT email FROM expert WHERE expert_id =" + i);
            rs.next();
            return rs.getString("email");
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT email FROM farmer WHERE farmer_id =" + i);
            rs.next();
            return rs.getString("email");
        }
        return "";
    }

    private static String getPhone(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT phone FROM expert WHERE expert_id =" + i);
            rs.next();
            return rs.getString("phone");
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT phone FROM farmer WHERE farmer_id =" + i);
            rs.next();
            return rs.getString("phone");
        }
        return "";
    }

    private static String getField(int i) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT field FROM expert WHERE expert_id =" + i);
        rs.next();
        return rs.getString("field");

    }

    private static String getLocation(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT addr FROM expert WHERE expert_id =" + i);
            rs.next();
            return rs.getString("addr");
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT addr FROM farmer WHERE farmer_id =" + i);
            rs.next();
            return rs.getString("addr");
        }
        return "";
    }

    private static String getRegdate(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT reg_date FROM expert WHERE expert_id =" + i);
            rs.next();
            return rs.getString("reg_date");
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT reg_date FROM farmer WHERE farmer_id =" + i);
            rs.next();
            return rs.getString("reg_date");
        }
        return "";
    }

    private static String getStatus(int i, String type) throws Exception {
        connectDb();
        Statement st = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = st.executeQuery("SELECT status FROM expert WHERE expert_id =" + i);
            rs.next();
            if (rs.getInt("status") == 0) {
                return "Not Activated";
            } else if (rs.getInt("status") == 1) {
                return "Activated";
            }
        } else if (type.equals("farmer")) {
            ResultSet rs = st.executeQuery("SELECT status FROM farmer WHERE farmer_id =" + i);
            rs.next();
            if (rs.getInt("status") == 0) {
                return "Not Activated";
            } else if (rs.getInt("status") == 1) {
                return "Activated";
            }
        }
        return "";
    }

    private static int countUser(String type) throws SQLException, Exception {
        connectDb();
        Statement stmt = connection.createStatement();
        if (type.equals("expert")) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(expert_id) AS count FROM expert");
            rs.next();
            return rs.getInt("count");
        } else if (type.equals("farmer")) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(farmer_id) AS count FROM farmer");
            rs.next();
            return rs.getInt("count");
        }
        return 0;
    }
}
