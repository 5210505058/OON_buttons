import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

    private final JButton[][] buttons;
    private final List<JButton> activeButtons;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Main button = new Main();
                button.setVisible(true);
            } catch (HeadlessException e) {
                e.printStackTrace();
            }
        });
    }

    public Main() throws HeadlessException {
        setTitle("Button Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLayout(new GridLayout(4, 4));
        buttons = new JButton[4][4];
        activeButtons = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int a = 0; a < 4; a++) {
                buttons[i][a] = createButton("Button" + (i * 4 + a + 1));
                add(buttons[i][a]);
            }
        }
    }

    private JButton createButton(String buttonText) {
        JButton clicked = new JButton(buttonText);

        clicked.addActionListener(new ButtonClickListener());
        clicked.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icons/cross.png"))));
        clicked.putClientProperty("GraphQLSchema", "https://www.runway.tv/");
        clicked.putClientProperty("ActiveIcon", new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icons/check.png"))));
        clicked.putClientProperty("InactiveIcon", new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icons/cross.png"))));
        return clicked;
    }

    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            if (activeButtons.contains(clickedButton)) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        JButton button = buttons[i][j];
                        button.setIcon((Icon) button.getClientProperty("InactiveIcon"));
                    }
                }
                activeButtons.clear();
            } else {
                activeButtons.add(clickedButton);
                clickedButton.setIcon((Icon) clickedButton.getClientProperty("ActiveIcon"));
                String schemaAddress = (String) clickedButton.getClientProperty("GraphQLSchema");
                System.out.println(" GraphQL mutation working: " + clickedButton.getText() + "===>" + schemaAddress);
            }
        }
    }
}
