/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hebproject;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author david
 */
public class HebProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String text = readFile("C:\\Users\\david\\Desktop\\test.txt");

            printHistogram(text);
        } catch (IOException ex) {
            Logger.getLogger(HebProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     Read the file.
     */
    public static String readFile(String path)
            throws IOException {

        return FileUtils.readFileToString(new File(path));
    }

    /**
     * This will extract the text from file into a List of words.
     *
     * @param text the text to extract the words.
     *
     * @return the {@link List<Word>} of words.
     */
    public static List<Word> extractWordsToList(String text) {

        if (text == null) {
            System.out.println("Exiting because text is null.");
            return null;
        }

        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(text);

        List<Word> wordList = new ArrayList<>();

        while (matcher.find()) {

            if (wordList.isEmpty()) {
                System.out.println("List is empty.");
                System.out.println("Adding new word to list: "
                        + matcher.group().toLowerCase());

                wordList.add(new Word(matcher.group().toLowerCase()));
            } else {
                for (int i = 0; i < wordList.size(); i++) {
                    //Check if Word is already in list.

                    System.out.print("Comparing " + matcher.group().toLowerCase() + 
                            " to ");
                    System.out.println(wordList.get(i).getName());
                    if (wordList.get(i).getName().equals(matcher.group().toLowerCase())) {
                        
                        wordList.get(i).addCount();
                        break;
                    } else if(!containsWord(wordList, matcher.group().toLowerCase())) {
                        System.out.println("Adding new word to list: "
                                + matcher.group().toLowerCase());

                        wordList.add(new Word(matcher.group().toLowerCase()));
                        break;
                    }
                }
            }
        }
        return wordList;
    }
    
    /**
     Check if the word is contained in the List of words.
     
     @param list the list of words.
     @param word the word. 
     
     @return return true if word is found in list, false otherwise.
     */
    public static boolean containsWord(final List<Word> list, final String word){
        return list.stream().map(Word::getName).filter(word::equals).findFirst().isPresent();
}

    /**
     Sorts the list of words by the count.
     
     @param words the list of words.
     
     @return the list, of type {@link List<Word>}.*/
    private static List<Word> sortWordsByCount(List<Word> words) {

        Collections.sort(words, new Comparator<Word>() {
            public int compare(Word g1, Word g2) {
                return Integer.compare(g2.getCount(), g1.getCount());
            }
        });
        
        return words;
    }
    
    /**
     Prints the Histogram.
     
     @param text the text to output, of type {@link String}.
     */
    private static void printHistogram(String text) {

        System.out.println("Extracting words from text file");
        List<Word> wordList = sortWordsByCount(extractWordsToList(text));

        wordList.forEach((word) -> {
            System.out.printf("%10s",word.getName()); 
            System.out.println("| "
                    + Strings.repeat("=", word.getCount())
                    + " (" + word.getCount() + ")");
            
        });
    }
}
