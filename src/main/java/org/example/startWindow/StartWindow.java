package org.example.startWindow;
import org.example.appConfig.WindowIconSetter;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class StartWindow extends JFrame {

    JButton asortyment = new JButton("ASORTYMENT");
    JButton kontrahenci = new JButton("KONTRAHENCI");
    JButton dokumenty = new JButton("DOKUMENTY");
    JButton wyjscie = new JButton("WYJŚCIE");
    //start window
    int widthScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
    int heightScreen = Toolkit.getDefaultToolkit().getScreenSize().height;
    int frameWidth = (widthScreen / 2);
    int frameHeight = (heightScreen / 2);

    public StartWindow(StartWindowButtonListener buttonListener) {
        super("DFSC SYSTEM");
        WindowIconSetter.setWindowIcon(this, "src/main/resources/dfsc syst.png");

        this.setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon backgroundImage = new ImageIcon("src/main/java/org/example/startWindow/tłoGR.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);
        initComponents();

        //these are the buttons in our window - further on they are set to listen

//        asortyment.setIcon(new ImageIcon("src/main/java/org/example/startWindow/image/assortment.pgn"));
//        kontrahenci.setIcon(new ImageIcon("src/main/java/org/example/startWindow/image/contractor.png"));
//        dokumenty.setIcon(new ImageIcon("src/main/java/org/example/startWindow/image/document.png"));
//        wyjscie.setIcon(new ImageIcon("src/main/java/org/example/startWindow/image/exit.png"));


        //listening on buttons

        asortyment.addActionListener(buttonListener::onAsortymentButtonClicked);
        kontrahenci.addActionListener(buttonListener::onKontrahenciButtonClicked);
        dokumenty.addActionListener(e -> buttonListener.onDokumentyButtonClicked());
        wyjscie.addActionListener(e -> buttonListener.onWyjscieButtonClicked());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        int buttonWidth = frameWidth / 4;
        int buttonHeight = frameHeight / 4;
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        asortyment.setPreferredSize(buttonSize);
        kontrahenci.setPreferredSize(buttonSize);
        dokumenty.setPreferredSize(buttonSize);
        wyjscie.setPreferredSize(buttonSize);

        Font font = new Font("Impact", Font.PLAIN, 25);
        asortyment.setFont(font);
        kontrahenci.setFont(font);
        dokumenty.setFont(font);
        wyjscie.setFont(font);

        Color colorGreen = new Color(165, 250, 177);
        Color colorGreenDark = new Color(2, 75, 14);
        Color mouseGreen = new Color(11, 154, 35);
        Color colorYellow = new Color(245, 234, 137);
        Color colorYellowDark = new Color(66, 60, 1);
        Color mouseYellow = new Color(168, 153, 9);
        Color colorPurple = new Color(155, 159, 239);
        Color colorPurpleDark = new Color(31, 33, 82);
        Color mousePurple= new Color(73, 78, 178);
        Color colorRed = new Color(238, 127, 146);
        Color colorRedDark = new Color(66, 1, 12);
        Color mouseRed = new Color(173, 31, 55);

        colorOfButton(colorGreen, colorGreenDark, mouseGreen, asortyment);
        colorOfButton(colorYellow, colorYellowDark, mouseYellow, kontrahenci);
        colorOfButton(colorPurple, colorPurpleDark, mousePurple, dokumenty);
        colorOfButton(colorRed,colorRedDark,mouseRed,wyjscie);



        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(0, 0, 0, 20);
        add(asortyment, constraints);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 20, 0, 20);
        add(kontrahenci, constraints);


        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.insets = new Insets(0, 20, 0, 0);
        add(dokumenty, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(50, 0, 0, 0);
        add(wyjscie, constraints);

    }

    private void colorOfButton(Color colorYellow, Color colorYellowDark, Color mouseYellow, JButton kontrahenci) {
        kontrahenci.setBackground(colorYellow);
        Border border1 = BorderFactory.createLineBorder(colorYellowDark,6);
        kontrahenci.setBorder(border1);
        kontrahenci.setForeground(colorYellowDark);

        kontrahenci.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            kontrahenci.setBackground(mouseYellow);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            kontrahenci.setBackground(colorYellow);
            }
        });
    }

}
