import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Called from main to create GUI and perform the background tasks
 * Created by Jawmie on 05/12/2015.
 */

public abstract class fileSearchGUI implements ActionListener{
    private static JFrame baseFrame;
    private static JButton loadButt;
    private static JButton reverseButt;
    private static JButton revPairButt;
    private static JButton countButt;
    private static JTextArea loadText;
    private static JTextArea reverseText;
    private static JTextArea revPairText;
    private static JTextArea countText;

    //make the file available to all the buttons ActionListeners
    public static File textFile;

    public static void guiSetUp(){
        //put borders on textAreas to make them more noticeable
        Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        //set up the main frame
        baseFrame = new JFrame ("MyStreamSwingWorkerPanel");
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseFrame.setSize(700, 570);
        baseFrame.setLayout(null);

        //construct components
        //Buttons for file manipulation
        loadButt = new JButton("Load");
        reverseButt = new JButton("Reverse");
        revPairButt = new JButton("Reverse Pair");
        countButt = new JButton("Word Count");

        //TextAreas to show output of button presses
        loadText = new JTextArea (5, 5);
        reverseText = new JTextArea (5, 5);
        revPairText = new JTextArea (5, 5);
        countText = new JTextArea (5, 5);

        //Set line wrap on text area
        loadText.setLineWrap(true);
        reverseText.setLineWrap(true);
        revPairText.setLineWrap(true);
        countText.setLineWrap(true);

        //Put Borders on text areas
        loadText.setBorder(loweredEtched);
        reverseText.setBorder(loweredEtched);
        revPairText.setBorder(loweredEtched);
        countText.setBorder(loweredEtched);

        //set component bounds (only needed by Absolute Positioning)
        loadButt.setBounds (45, 45, 115, 20);
        reverseButt.setBounds (210, 45, 115, 20);
        revPairButt.setBounds (365, 45, 115, 20);
        countButt.setBounds (520, 45, 115, 20);
        loadText.setBounds (35, 75, 135, 425);
        reverseText.setBounds (200, 75, 135, 425);
        revPairText.setBounds (355, 75, 135, 425);
        countText.setBounds (510, 75, 135, 425);

        //add components
        baseFrame.add(loadButt);
        baseFrame.add(reverseButt);
        baseFrame.add(revPairButt);
        baseFrame.add(countButt);
        baseFrame.add(loadText);
        baseFrame.add(reverseText);
        baseFrame.add(revPairText);
        baseFrame.add(countText);

        //Add ActionListeners for buttons
        //Load File into the GUI
        loadButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                textFile = fileChooser.getSelectedFile();
                try{
                    SwingWorker<String, Void> loadData = new SwingWorker<String, Void>() {
                        @Override
                        protected String doInBackground() throws Exception {
                            loadText.setText(null);
                            //Reads the file from the file chooser
                            //Breakpoint here
                            BufferedReader br = new BufferedReader(new FileReader(textFile));
                            //reads the lines of the text file
                            String fileLines = br.readLine();
                            //will be inserted into the textArea
                            String line = "";

                            while(fileLines != null){
                                //make string filled with words from file
                                line = line + fileLines;
                                //move the fileLines string to the next line
                                fileLines = br.readLine();
                                //break out once the readLine finds a null
                            }
                            return line;

                        }
                        protected void done() {
                            String textData;
                            try {
                                //set the text of the file in the textArea
                                loadText.setText("");
                                textData = get();
                                loadText.append(textData);
                                loadText.requestFocus(); //set the textArea active
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            } catch (ExecutionException exe) {
                                exe.printStackTrace();
                            }
                        }

                    };//End of SwingWorker

                    loadData.execute();
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });//End of LoadFile ActionListener

        //Reverse the words in the file
        reverseButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try{
                    SwingWorker<String, Void> loadData = new SwingWorker<String, Void>() {
                        @Override
                        protected String doInBackground() throws Exception {
                            reverseText.setText(null);
                            //Reads the file from the file chooser
                            BufferedReader br = new BufferedReader(new FileReader(textFile));
                            //reads line by line and are now strings
                            String fileLine = br.readLine();

                            //while there is still text in the file
                            String lineReverse = "";
                            while (fileLine != null) {
                                String[] werds = fileLine.split(" "); //split the string on a space
                                for (int i = werds.length - 1; i >= 0; i--) {
                                    lineReverse = lineReverse + " " + werds[i];
                                }
                                fileLine = br.readLine();
                            }
                            return lineReverse;
                        }
                        protected void done() {
                            String getReverse = "";
                            try {
                                //set the text of the reverse lines in the textArea
                                reverseText.setText("");
                                getReverse = get();
                                reverseText.append(getReverse);
                                reverseText.requestFocus();
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            } catch (ExecutionException exe) {
                                exe.printStackTrace();
                            }
                        }
                    };
                    loadData.execute();
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });//End of Reverse Line ActionListener

        //Reverse a pair of words
        revPairButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try{
                    SwingWorker<String, Void> loadData = new SwingWorker<String, Void>() {
                        @Override
                        protected String doInBackground() throws Exception {
                            revPairText.setText(null);
                            //Reads the file from the file chooser
                            //Breakpoint here
                            BufferedReader br = new BufferedReader(new FileReader(textFile));
                            //reads line by line and are now strings
                            String fileLine = br.readLine();
                            String revPair = "";
                            //while there is still text in the file
                            while(fileLine != null){
                                String[] fileLines = fileLine.split("\\.");
                                for(int x = 0; x < fileLines.length; x++) {
                                    String[] fileWerds = fileLines[x].split(" "); //split the string on a space
                                    //have it always less than the length
                                    for (int i = 0; i < fileWerds.length; i++) {
                                        //check for pairs of words as well as checking you are at the end of the line
                                        if (i % 2 == 0 && i < fileWerds.length - 1) {
                                            revPair = revPair + fileWerds[i + 1] + " ";
                                            revPair = revPair + fileWerds[i] + " ";
                                        }
                                        i++;
                                    }
                                }
                                if (fileLine.compareTo("") == 0)
                                    revPair = revPair +"\r\n";
                                fileLine = br.readLine();
                            }
                            return revPair;
                        }
                        protected void done() {
                            String getPair = "";
                            try {
                                revPairText.setText("");
                                getPair = get();
                                revPairText.setText(getPair);
                                //set the text of the file in the textArea
                                revPairText.requestFocus();
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            } catch (ExecutionException exe) {
                                exe.printStackTrace();
                            }
                        }
                    };
                    loadData.execute();
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });//End of Reverse Pair ActionListener

        //Perform a word count on all the words
        countButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try
                {
                    SwingWorker<Map<String, Void>, Void> loadData = new SwingWorker<Map<String, Void>,Void>() {
                        @Override
                        protected Map doInBackground() throws Exception {
                            countText.setText(null);
                            Map<String, Integer> wordCount = new HashMap<>();
                            BufferedReader br = new BufferedReader(new FileReader(textFile));
                            String currentLine = br.readLine();

                            while (currentLine != null) {
                                //Make everything lower case to make grouping words easier
                                currentLine = currentLine.replaceAll("[^a-z/A-Z ]", "").toLowerCase();
                                //Split words by the spaces between them and put them into an array
                                String[] words = currentLine.split(" ");
                                for (int i = 0; i < words.length; i++) {
                                    if (wordCount.containsKey(words[i]))
                                        //If the word already exists in the HashMap the increase the value of
                                        //the one in the HashMap
                                        wordCount.put(words[i], wordCount.get(words[i]) + 1);
                                    else
                                        //If it is a new word, just insert it as a key, with a value 1
                                        wordCount.put(words[i], 1);
                                }
                                //Moves the file onto the next line
                                currentLine = br.readLine();
                            }
                            return wordCount;
                        }
                        protected void done() {
                            try {
                                countText.setText("");
                                Map<String, Integer> wordCount = doInBackground();
                                //set the text of the file in the textArea
                                //Goes through HashMap and inserts the values into the textArea
                                for(Map.Entry<String, Integer> word : wordCount.entrySet())
                                {
                                    String key = word.getKey().toString();
                                    Integer value = word.getValue();
                                    countText.append(key + " " + value + "\n");
                                }
                                countText.requestFocus();
                            } catch(Exception ee){ee.printStackTrace();}
                        }
                    };
                    loadData.execute();
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
            }
        });//End of Word Count Action Listener

    }//End of GUI set up

    public static void drawMyGUI(){
        baseFrame.setVisible(true);
    }
}
