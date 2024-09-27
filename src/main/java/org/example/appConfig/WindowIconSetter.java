package org.example.appConfig;

import org.example.assortment.EditDialog;
import org.example.contractor.EditDialogFromContractor;

import javax.swing.*;

public class WindowIconSetter {
    public static void setWindowIcon(JFrame window, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        window.setIconImage(icon.getImage());

    }

    public static void setWindowIcon(EditDialog editDialog, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        editDialog.setIconImage(icon.getImage());
    }

    public static void setWindowIcon(EditDialogFromContractor editDialogFromContractor, String iconPath) {

        ImageIcon icon = new ImageIcon(iconPath);
        editDialogFromContractor.setIconImage(icon.getImage());
    }
}
