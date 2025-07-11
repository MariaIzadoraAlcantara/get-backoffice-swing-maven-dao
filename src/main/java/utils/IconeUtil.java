package util;

import javax.swing.*;
import java.awt.*;

public class IconeUtil extends JFrame {

    public static void aplicarIcone(JFrame frame) {
        ImageIcon icon = new ImageIcon(IconeUtil.class.getResource("/assets/images/icone.png"));
        frame.setIconImage(icon.getImage());
    }
}