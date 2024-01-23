import java.io.*;
import java.util.*;

public class FileReaderDir {
    public static void main(String[] args) throws FileNotFoundException {
        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            assert files != null;
            System.out.println("The total number of files : " + files.length);

            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                    System.out.println("Content :");
                    printFileContent(file);
                    System.out.println("Total number of words: " + countWords(file));
                    System.out.println();
                    System.out.println("Words Frequency : ");
                    printWordFrequency(file);
                    System.out.println();
                    System.out.println();
                    remove_Stop_words_and_print_content(file);

                }
            }
        }
        }

      static int countWords(File file) throws FileNotFoundException {
        int wordCount = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] words = scanner.nextLine().split(" ");
            wordCount += words.length;
        }
        return wordCount;
    }
     private static void printFileContent(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
    private static void printWordFrequency(File file) throws FileNotFoundException {
        HashMap<String, Integer> wordFrequency = new HashMap<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String temp_word = scanner.next();
            String word = remove_panctuation(temp_word);
            if (wordFrequency.containsKey(word)) {
                int currentFrequency = wordFrequency.get(word);
                wordFrequency.put(word, currentFrequency + 1);
            } else {
                wordFrequency.put(word, 1);
            }
        }
        System.out.println(wordFrequency);
    }
    private static String remove_panctuation(String temp_word){
        ArrayList<Character> punctuationMarks = new ArrayList<>(Arrays.asList(
                '.', ',', '?', '!', ':', ';', '"', '-', '–', '—',':',
                '(', ')', '[', ']', '{', '}', '/', '&', '%', '$', '@', '_',
                '+', '='
        ));
        String word="";
        for (int i = 0; i < temp_word.length(); i++) {
            if (!punctuationMarks.contains(temp_word.charAt(i))){
                word = word + temp_word.charAt(i);
            }
        }
        return word;
    }
    static void remove_Stop_words_and_print_content(File file) throws FileNotFoundException {
        String[] stop_words_array =  {
                "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your",
                "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her",
                "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves",
                "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is",
                "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
                "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if",
                "or", "because", "as", "until", "while", "of", "at", "by", "for", "with",
                "about", "against", "between", "into", "through", "during", "before", "after", "above", "below",
                "to", "from", "up", "down", "in", "out", "on", "off", "over", "under",
                "again", "further", "then", "once", "here", "there", "when", "where", "why", "how",
                "all", "any", "both", "each", "few", "more", "most", "other", "some", "such",
                "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very",
                "s", "t", "can", "will", "just", "don", "should", "now", "d", "ll", "m", "o", "re", "ve", "y",
                "ain", "aren", "couldn", "didn", "doesn", "hadn", "hasn", "haven", "isn", "ma", "mightn", "mustn", "needn",
                "shan", "shouldn", "wasn", "weren", "won", "wouldn"
        };
        ArrayList<String> stop_word_list = new ArrayList<>(List.of(stop_words_array));
        Scanner scanner = new Scanner(file);
        String content = "";
        while (scanner.hasNext()){
            String word= scanner.next();
            if (!stop_word_list.contains(word)){
                content= content + word + " ";
            }
        }
        System.out.println(content);
    }

}

