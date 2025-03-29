package GUI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLHandler {
    private static final String FILE_PATH = "D:\\BaiTapJava2\\GUI\\books.xml";

    public static  void saveBook(Book book){
        try{
            File file = new File(FILE_PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (!file.exists()){
                doc = builder.newDocument();
                Element rootElement = doc.createElement("books");
                doc.appendChild(rootElement);
            }else{
                doc = builder.parse(file);
            }

            Element bookElement = doc.createElement("book");
            Element titleElement = doc.createElement("title");
            titleElement.setTextContent(book.getTitle());
            bookElement.appendChild(titleElement);

            Element authorElement = doc.createElement("author");
            authorElement.setTextContent(book.getAuthor());
            bookElement.appendChild(authorElement);

            Element yearElement = doc.createElement("year");
            yearElement.setTextContent(String.valueOf(book.getYear()));
            bookElement.appendChild(yearElement);

            Element publisherElement = doc.createElement("publisher");
            publisherElement.setTextContent(book.getPublisher());
            bookElement.appendChild(publisherElement);

            Element pagesElement = doc.createElement("pages");
            pagesElement.setTextContent(String.valueOf(book.getPages()));
            bookElement.appendChild(pagesElement);

            Element genreElement = doc.createElement("genre");
            genreElement.setTextContent(book.getGenre());
            bookElement.appendChild(genreElement);

            Element priceElement = doc.createElement("price");
            priceElement.setTextContent(String.valueOf(book.getPrice()));
            bookElement.appendChild(priceElement);

            Element isbnElement = doc.createElement("isbn");
            isbnElement.setTextContent(book.getIsbn());
            bookElement.appendChild(isbnElement);

            doc.getDocumentElement().appendChild(bookElement);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source,result);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Book> loadBooks(){
        List<Book> books = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()){
                return books;
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList bookNodes = doc.getElementsByTagName("book");

            for(int i = 0; i < bookNodes.getLength(); i++){
                Element bookElement = (Element) bookNodes.item(i);

                Book book = new Book(
                    getElementText(bookElement, "title"),
                    getElementText(bookElement, "author"),
                    Integer.parseInt(getElementText(bookElement, "year")),
                    getElementText(bookElement, "publisher"),
                    Integer.parseInt(getElementText(bookElement, "pages")),
                    getElementText(bookElement, "genre"),
                    Double.parseDouble(getElementText(bookElement, "price")),
                    getElementText(bookElement, "isbn")
                );
                books.add(book);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return books;

    }

    public static void deleteBookByIsbn(String isbn) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList bookNodes = doc.getElementsByTagName("book");
            Element rootElement = doc.getDocumentElement();

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);
                String bookIsbn = getElementText(bookElement, "isbn");

                if (bookIsbn.equals(isbn)) {
                    rootElement.removeChild(bookElement);
                    break;
                }
            }

            saveDocumentToFile(doc, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Book updatedBook, String isbn) {
        try {
            File file = new File(FILE_PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList bookList = doc.getElementsByTagName("book");
            for (int i = 0; i < bookList.getLength(); i++) {
                Element bookElement = (Element) bookList.item(i);
                String currentIsbn = getElementText(bookElement, "isbn");

                if (currentIsbn.equals(isbn)) {
                    updateElement(bookElement, "title", updatedBook.getTitle());
                    updateElement(bookElement, "author", updatedBook.getAuthor());
                    updateElement(bookElement, "year", String.valueOf(updatedBook.getYear()));
                    updateElement(bookElement, "publisher", updatedBook.getPublisher());
                    updateElement(bookElement, "pages", String.valueOf(updatedBook.getPages()));
                    updateElement(bookElement, "genre", updatedBook.getGenre());
                    updateElement(bookElement, "price", String.valueOf(updatedBook.getPrice()));
                    updateElement(bookElement, "isbn", updatedBook.getIsbn());
                    break;
                }
            }

           saveDocumentToFile(doc, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveDocumentToFile(Document doc, File file) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }

    private static void updateElement(Element parent, String elementName, String value) {
        NodeList elements = parent.getElementsByTagName(elementName);
        Element element = (Element) elements.item(0);
        element.setTextContent(value);
    }

    private  static String getElementText(Element parentElement, String elementName){
        return parentElement.getElementsByTagName(elementName).item(0).getTextContent();
    }
}
