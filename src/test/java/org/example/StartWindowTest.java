package org.example;
import org.example.startWindow.StartWindow;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.startWindow.StartWindowButtonListener;

class StartWindowTest {
    @Test
    public void testStartWindowInitialization() {
        StartWindowButtonListener buttonListener = new StartWindowButtonListener();
        StartWindow startWindow = new StartWindow(buttonListener);
        assertEquals(WindowConstants.EXIT_ON_CLOSE, startWindow.getDefaultCloseOperation());
        assertNotNull(startWindow.getContentPane());
    }
}