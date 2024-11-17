import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class OOPlab7 {
    private JFrame bookList;
    private DefaultTableModel model;
    private JButton save, add, edit, delete, load, savePdf, saveHtml;
    private JScrollPane scroll;
    private JTable books;

    public void show() {
        bookList = new JFrame("Информация о книгах");
        bookList.setSize(600, 400);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Название книги", "Автор", "Шрифт", "Закреплена?"};
        String[][] data = {
            {"Война и мир", "Лев Толстой", "Arial", "Нет"},
            {"1984", "Джордж Оруэлл", "Calibri", "Да"},
            {"Прощай оружие!", "Эрнест Хемингуэй", "Garamond", "Нет"},
            {"Убить пересмешника", "Харпер Ли", "Fraktur", "Да"},
            {"На дороге", "Джек Керуак", "Papyrus", "Нет"}
        };
        model = new DefaultTableModel(data, columns);
        books = new JTable(model);
        books.setAutoCreateRowSorter(true);
        scroll = new JScrollPane(books);
        bookList.getContentPane().add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        save = new JButton("Сохранить");
        load = new JButton("Загрузить");
        add = new JButton("Добавить");
        edit = new JButton("Редактировать");
        delete = new JButton("Удалить");
        savePdf = new JButton("Сохранить PDF");
        saveHtml = new JButton("Сохранить HTML");

        buttonPanel.add(save);
        buttonPanel.add(load);
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(savePdf);
        buttonPanel.add(saveHtml);
        bookList.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        save.addActionListener(e -> saveToXMLFile());
        load.addActionListener(e -> loadFromXMLFile());
        add.addActionListener(e -> addBook());
        edit.addActionListener(e -> editBook());
        delete.addActionListener(e -> deleteBook());
        savePdf.addActionListener(e -> saveToPDF());
        saveHtml.addActionListener(e -> saveToHTML());

        bookList.setVisible(true);
    }

    private void saveToPDF() {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./data/PdfData.pdf"));
            document.open();

            BaseFont bfComic = BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font1 = new Font(bfComic, 12);

            PdfPTable t = new PdfPTable(4);
            t.addCell(new PdfPCell(new Phrase("Название книги", font1)));
            t.addCell(new PdfPCell(new Phrase("Автор", font1)));
            t.addCell(new PdfPCell(new Phrase("Шрифт", font1)));
            t.addCell(new PdfPCell(new Phrase("Закреплена?", font1)));

            for (int i = 0; i < model.getRowCount(); i++) {
                t.addCell(new PdfPCell(new Phrase((String) model.getValueAt(i, 0), font1)));
                t.addCell(new PdfPCell(new Phrase((String) model.getValueAt(i, 1), font1)));
                t.addCell(new PdfPCell(new Phrase((String) model.getValueAt(i, 2), font1)));
                t.addCell(new PdfPCell(new Phrase((String) model.getValueAt(i, 3), font1)));
            }

            document.add(t);
            document.close();
            JOptionPane.showMessageDialog(bookList, "Данные сохранены в PDF.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToHTML() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("./data/HtmlData.html"))) {
            pw.println("<table border='1'><tr><th>Название книги</th><th>Автор</th><th>Шрифт</th><th>Закреплена?</th></tr>");
            for (int i = 0; i < model.getRowCount(); i++) {
                pw.println("<tr><td>" + model.getValueAt(i, 0) + "</td>");
                pw.println("<td>" + model.getValueAt(i, 1) + "</td>");
                pw.println("<td>" + model.getValueAt(i, 2) + "</td>");
                pw.println("<td>" + model.getValueAt(i, 3) + "</td></tr>");
            }
            pw.println("</table>");
            JOptionPane.showMessageDialog(bookList, "Данные сохранены в HTML.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new OOPlab7().show();
    }
}