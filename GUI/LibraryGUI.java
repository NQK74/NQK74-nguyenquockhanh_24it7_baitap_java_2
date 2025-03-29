package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;  // Thêm import cho List

public class LibraryGUI extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, authorField, yearField, publisherField,
        pagesField, genreField, priceField, isbnField;

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Title", "Author", "Year", "Publisher",
            "Pages", "Genre", "Price", "ISBN"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();
        publisherField = new JTextField();
        pagesField = new JTextField();
        genreField = new JTextField();
        priceField = new JTextField();
        isbnField = new JTextField();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Publisher:"));
        inputPanel.add(publisherField);
        inputPanel.add(new JLabel("Pages:"));
        inputPanel.add(pagesField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(isbnField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearFields());

        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bookTable.getSelectedRow() != -1) {
                fillFieldsFromSelection();
            }
        });

        loadBookData();
    }

    private void addBook() {
        try {
            Book book = getBookFromFields();
            XMLHandler.saveBook(book);
            loadBookData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Pages and Price fields",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần cập nhật",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String originalIsbn = (String) tableModel.getValueAt(selectedRow, 7);
            Book updatedBook = getBookFromFields();
            XMLHandler.updateBook(updatedBook, originalIsbn); // Gọi phương thức cập nhật từ XMLHandler
            loadBookData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Đã cập nhật sách thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Số trang và Giá",
                "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần xóa",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa sách này?", "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String isbn = (String) tableModel.getValueAt(selectedRow, 7);
            XMLHandler.deleteBookByIsbn(isbn);
            loadBookData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Đã xóa sách thành công!");
        }
    }

    private void loadBookData() {
        tableModel.setRowCount(0);
        List<Book> books = XMLHandler.loadBooks();
        for (Book book : books) {
            Object[] row = {
                book.getTitle(),
                book.getAuthor(),
                String.valueOf(book.getYear()),
                book.getPublisher(),
                String.valueOf(book.getPages()),
                book.getGenre(),
                String.format("%.2f", book.getPrice()),
                book.getIsbn()
            };
            tableModel.addRow(row);
        }
    }

    private void fillFieldsFromSelection() {
        int selectedRow = bookTable.getSelectedRow();
        titleField.setText((String) tableModel.getValueAt(selectedRow, 0));
        authorField.setText((String) tableModel.getValueAt(selectedRow, 1));
        yearField.setText((String) tableModel.getValueAt(selectedRow, 2));
        publisherField.setText((String) tableModel.getValueAt(selectedRow, 3));
        pagesField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        genreField.setText((String) tableModel.getValueAt(selectedRow, 5));
        priceField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        isbnField.setText((String) tableModel.getValueAt(selectedRow, 7));
    }

    private Book getBookFromFields() {
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
            yearField.getText().isEmpty() || publisherField.getText().isEmpty() ||
            pagesField.getText().isEmpty() || genreField.getText().isEmpty() ||
            priceField.getText().isEmpty() || isbnField.getText().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin");
        }

        try {
            int year = Integer.parseInt(yearField.getText());
            int pages = Integer.parseInt(pagesField.getText());
            double price = Double.parseDouble(priceField.getText());

            return new Book(
                titleField.getText().trim(),
                authorField.getText().trim(),
                year,
                publisherField.getText().trim(),
                pages,
                genreField.getText().trim(),
                price,
                isbnField.getText().trim()
            );
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Vui lòng nhập đúng định dạng số cho Năm, Số trang và Giá");
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        publisherField.setText("");
        pagesField.setText("");
        genreField.setText("");
        priceField.setText("");
        isbnField.setText("");
    }
}