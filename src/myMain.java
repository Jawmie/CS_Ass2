import javax.swing.*;

/**
 * Created by Jawmie on 05/12/2015.
 * Modified version of Ass_1 : Streams
 * Ass_2 : Streams & Thread Safe Swing
 *
 * Some notes on SwingWorker:
 * Using ThreadSafe SwingWorker which handles all the necessary synchronisation
 * in the background which simplifies the code. SwingWorker is meant for long
 * background tasks that might otherwise hold up the event dispatch thread.
 *
 * Listener starts background task by calling the execute() method in the SwingWorker
 * and returns immediately so the event dispatch thread is free to keep UI responisive.
 */
public class myMain {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fileSearchGUI.guiSetUp();
                fileSearchGUI.drawMyGUI();
            }
        });
    }
}
