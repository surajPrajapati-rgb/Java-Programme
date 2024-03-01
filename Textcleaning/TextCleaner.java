/*
This code is for extracting the texts from xml and text files and 
cleaning the data as per defined rules.

Authors: Ankita, Suraj, Shadab, Shrawan, Narayan
Date: 4 December, 2023
*/

// Importing necessary modules
import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.nio.file.Files;
import java.io.FileWriter;


public class TextCleaner{
    // Main method
	public static void main(String[] args) throws IOException {

        String path = "file.txt";
        File file = new File(path);
        FileWriter fw = new FileWriter("output.txt");   //writing to output file
        String fileextension = extension(path);
        String text = "";

        if (fileextension.equals(".txt")){
            // txt file extraction
            text = readFileAsString(file);            
        }
        else if (fileextension.equals(".xml")){
            text = XMLParser(file);    // Extracting text from XML file   
        }
        if (text.equals("")){
            System.out.println("There is no text to clean, please enter valid text.");
        }
        else{
            Cleaner obj = new Cleaner();
            String cleanedText = obj.clean(text);
            for (int i = 0; i < cleanedText.length(); i++){
                fw.write(cleanedText.charAt(i));
            }
            fw.close();
        }    
	}

    // Identify extension
    static String extension(String path){
        return path.substring(path.indexOf('.'));
    }

    // txt extraction
    public static String readFileAsString(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    // XML Parser Method
    public static String XMLParser(File file){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("*"); // Get all elements

            StringBuilder textContent = new StringBuilder();
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    textContent.append(node.getTextContent()).append("\n");
                }
            }
            return textContent.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}


// Other cleaner class
class Cleaner{
    char[] removable = {'#', '@', '$', '%', '^', '*', '~', '`', '+', '=', '_', '|', '/', '>', '<', '(', ')', '{', '}', '[', ']', '&'};
    char[] punctuations = {'!', ':', ';', '?', '.', ','};
    char[] spaces = {'\n', ' '};
    char[] delimenaters = {'.', '?'};

    // Methods to clean the data
    public String clean(String text){
        String t5 = text;
        for(int i = 0; i < 3; i++){
            String t1 = removeSpecChar(t5);
            String t2 = updatePunct(t1);
            String t3 = removeDigits(t2);
            String t4 = capitalize(t3);
            t5 = removeSpaces(t4);
        }
        return t5;
    }

    public String removeSpecChar(String text) {
        String updatedText = "";
        for (int i=0; i<=text.length()-1; i++){
            char c = text.charAt(i);
            boolean found = isCharInArray(c, removable);
            if (found){
                continue;
            }
            else{
                updatedText += c;
            }
        }
        return updatedText;
    }

    // Method to remove unncessary punctuations
    public String updatePunct(String text){
        String updatedText = "";
        for (int i=0; i<=text.length()-1; i++){
            char c = text.charAt(i);
            boolean found = isCharInArray(c, punctuations);
            if (found){
                if (i==0){
                    continue;
                }
                else if(i==text.length()-1){
                    updatedText += c;
                }
                else{
                    char prev = text.charAt(i-1);
                    char next = text.charAt(i+1);
                    boolean p = isCharInArray(next, punctuations);
                    if (Character.isLetter(prev) && Character.isLetter(next)){
                        continue;
                    }
                    else if (Character.isDigit(prev) && Character.isDigit(next)){
                        continue;
                    }
                    else if (Character.isDigit(prev) && Character.isLetter(next)){
                        continue;
                    }
                    else if (Character.isLetter(prev) && Character.isDigit(next)){
                        continue;
                    }
                    else if (p){
                        continue;
                    }
                    else if (prev == ' ' && next == ' '){
                        continue;
                    }
                    else if((Character.isLetter(prev) || Character.isDigit(prev)) && next == ' '){
                        updatedText += c;
                    }
                    // in doubt unnecessary
                    else if(!(Character.isLetter(prev)) && Character.isLetter(next)){
                        if(c==','){
                            updatedText = updatedText + c + " ";}
                    }
                    else{
                        updatedText += c;
                    }
                }
            }
            else{
                updatedText += c;
            }
        }
        return updatedText;
    }

    // Combining spaces.
    public String removeDigits(String text){
        String updatedText = "";
        int len = text.length();
        for (int i=0; i<=text.length()-1; i++){
            char c = text.charAt(i);
            if (i == 0 && Character.isDigit(c) && Character.isLetter(text.charAt(i + 1))){
                continue;
            }
            else if(i == 0){
                updatedText +=c;
            }
            else if (i == len - 1 && Character.isDigit(c) && Character.isLetter(text.charAt(i - 1))){
                continue;
            }
            else if(i == len -1){
                updatedText +=c;
            }
            else{
                char prev = text.charAt(i-1);
                char next = text.charAt(i+1);
                boolean p = isCharInArray(next, punctuations);
                if (Character.isDigit(c) && Character.isLetter(prev) && Character.isLetter(next)){
                    continue;
                }
                else if(Character.isLetter(c) && Character.isDigit(prev) && Character.isDigit(next)){
                    continue;
                }
                else if (prev == ' ' && Character.isDigit(c) && Character.isLetter(next)){
                    continue;
                }
                else if (next == ' ' && Character.isDigit(c) && Character.isLetter(prev)){
                    continue;
                }
                else if (Character.isLetter(prev) && Character.isDigit(c) && p){
                    continue;
                }
                else{
                    updatedText += c;
                }    
            }
        }
        return updatedText;
    }

    public String capitalize(String text){
        String updatedText = "";
        int len = text.length();
        for (int i=0; i<=text.length()-1; i++){
            char c = text.charAt(i);
            if (i==0 && Character.isLetter(c)){
                updatedText += Character.toUpperCase(c);
            }else if (i == len -1 && Character.isLetter(c)){
                updatedText = updatedText + c + ".";
            }
            else if (i == 0){
                updatedText += c;
            }
            else{
                char prev = text.charAt(i-1);
                boolean p = isCharInArray(prev, delimenaters);
                if (c == ' ' && p){
                    continue;
                }
                else if ( i >= 2 && isCharInArray(text.charAt(i - 2), delimenaters) && prev == ' ' && Character.isLetter(c)){
                    updatedText = updatedText + " " + Character.toUpperCase(c);
                }
                else{
                    updatedText += c;
                }
            }
        }
        return updatedText;
    }

    public String removeSpaces(String text){
        String updatedText = "";
        int len = text.length();
        for (int i=0; i<=text.length()-1; i++){
            char c = text.charAt(i);
            boolean p = isCharInArray(c, spaces);
            if (i == 0 && p){
                continue;
            }else if(i == len - 1 && p){
                continue;
            }
            else if(i == len -1){
                updatedText +=c;
            }
            else{
                char next = text.charAt(i+1);
                if (c == ' ' && next == ' '){
                    continue;
                }else if(c == '\n' && next == '\n'){
                    continue;
                }else{
                    updatedText += c;
                }
            }
        }
        return updatedText;
    }

    // ------------- helper method----------
    
    public boolean isCharInArray(char c, char[] arr){
            boolean found = false;
            for (char x : arr) {
                if (x == c) {
                    found = true;
                    break;
                }
            }
            return found;
    }
}