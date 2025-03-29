package XML.Bai2;

import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Locale;

public class XMLTreeViewer {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("D:\\BaiTapJava2\\XML\\Bai1\\products.xml");

            DefaultMutableTreeNode root = createTree(document.getDocumentElement());
            JTree tree = new JTree(root);
            JFrame frame = new JFrame("XML Tree Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane( tree));
            frame.setSize(300,400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DefaultMutableTreeNode createTree(Node node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.getNodeName());
        NodeList nodeList= node.getChildNodes();
        for(int i=0; i<nodeList.getLength(); i++){
            Node child = nodeList.item(i);
            if(child.getNodeType()==Node.ELEMENT_NODE){
                treeNode.add(createTree(child));
            }
        }
        return treeNode;
    }
}
