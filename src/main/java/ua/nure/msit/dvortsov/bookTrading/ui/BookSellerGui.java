package ua.nure.msit.dvortsov.bookTrading.ui;

import ua.nure.msit.dvortsov.bookTrading.agent.BookSellerAgent;

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

public class BookSellerGui extends JFrame {

    private BookSellerAgent bookSellerAgent;

    public BookSellerGui(BookSellerAgent bookSellerAgent) {
        super(bookSellerAgent.getLocalName());
        this.bookSellerAgent = bookSellerAgent;
    }

    public void start() {
        createGuiComponents();
        showGui();
    }

    private void createGuiComponents() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Book title:"));
        JTextField titleField = new JTextField(15);
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(15);
        inputPanel.add(priceField);

        getContentPane().add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(actionEvent -> {
            try {
                String title = titleField.getText().trim();
                String price = priceField.getText().trim();
                bookSellerAgent.updateCatalogue(title, Integer.parseInt(price));
                titleField.setText("");
                priceField.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(addButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        addTerminateOnCloseWindowListener();
    }

    private void addTerminateOnCloseWindowListener() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                bookSellerAgent.doDelete();
            }
        });
    }

    private void showGui() {
        setResizable(false);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

}
