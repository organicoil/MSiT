package ua.nure.msit.dvortsov.bookTrading;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Giovanni Caire - TILAB
 */
class BookSellerGui extends JFrame {

    private BookSellerAgent myAgent;
    private JTextField titleField;
    private JTextField priceField;

    BookSellerGui(BookSellerAgent bookSellerAgent) {
        super(bookSellerAgent.getLocalName());

        myAgent = bookSellerAgent;

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 2));
        jPanel.add(new JLabel("Book title:"));
        titleField = new JTextField(15);
        jPanel.add(titleField);
        jPanel.add(new JLabel("Price:"));
        priceField = new JTextField(15);
        jPanel.add(priceField);
        getContentPane().add(jPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(actionEvent -> {
            try {
                String title = titleField.getText().trim();
                String price = priceField.getText().trim();
                myAgent.updateCatalogue(title, Integer.parseInt(price));
                titleField.setText("");
                priceField.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        jPanel = new JPanel();
        jPanel.add(addButton);
        getContentPane().add(jPanel, BorderLayout.SOUTH);

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                myAgent.doDelete();
            }
        });

        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

}
