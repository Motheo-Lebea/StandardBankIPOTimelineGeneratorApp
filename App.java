import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App extends JFrame {
    private JTextField companyField;
    private JTextField industryField;
    private JTextField dateField;
    private JTable resultTable;
    private JScrollPane scrollPane;

    public App() {
        createUI();
    }

    private void createUI() {
        setTitle("Standard Bank IPO Timeline Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(130, 150, 180));
        JLabel titleLabel = new JLabel("Standard Bank IPO Timeline Generator");
        titleLabel.setFont(new Font("SF Pro", Font.BOLD, 28));
        headerPanel.add(titleLabel);

        // Add your logo PNG here
        // JLabel logoLabel = new JLabel(new ImageIcon("ipo_logo.png"));
        // headerPanel.add(logoLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        companyField = new JTextField();
        industryField = new JTextField();
        dateField = new JTextField();
        JButton generateButton = new JButton("Generate Timeline");

        inputPanel.add(new JLabel("Company Name:"));
        inputPanel.add(companyField);
        inputPanel.add(new JLabel("Industry:"));
        inputPanel.add(industryField);
        inputPanel.add(new JLabel("IPO Date (yyyy-MM-dd):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel());
        inputPanel.add(generateButton);

        // Result Table
        resultTable = new JTable(new Object[][]{}, new String[]{"Phase", "Start Date", "End Date"});
        scrollPane = new JScrollPane(resultTable);

        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        generateButton.addActionListener(this::generateTimeline);
    }

    private void generateTimeline(ActionEvent e) {
        try {
            String company = companyField.getText();
            String industry = industryField.getText();
            LocalDate ipoDate = LocalDate.parse(dateField.getText(), DateTimeFormatter.ISO_DATE);

            List<StandardBankIPOTimelineGenerator.IPOPhase> timeline =
                    StandardBankIPOTimelineGenerator.generateTimeline(ipoDate);

            Object[][] data = new Object[timeline.size()][3];
            for(int i = 0; i < timeline.size(); i++) {
                StandardBankIPOTimelineGenerator.IPOPhase phase = timeline.get(i);
                data[i][0] = phase.phaseName();
                data[i][1] = phase.startDate().format(DateTimeFormatter.ISO_DATE);
                data[i][2] = phase.endDate().format(DateTimeFormatter.ISO_DATE);
            }

            resultTable.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    new String[]{"Phase", "Start Date", "End Date"}
            ));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format! Please use yyyy-MM-dd",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
