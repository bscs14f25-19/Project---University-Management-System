import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
// ==========================
// ABSTRACT CLASS
// ==========================
abstract class Person {
    protected int id;
    protected String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    abstract String getRole();
}

// ==========================
// INTERFACE
// ==========================
interface Report {
    void generateReport(String data);
}

// ==========================
// STUDENT CLASS
// ==========================
class Student extends Person implements Report {

    private String department;
    private double gpa;

    public Student(int id, String name, String department, double gpa) {
        super(id, name);
        this.department = department;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getGpa() { return gpa; }

    @Override
    String getRole() {
        return "Student";
    }

    @Override
    public void generateReport(String data) {
        JOptionPane.showMessageDialog(null, data);
    }
}

// ==========================
// MAIN CLASS
// ==========================
public class UniversityManagementSystem extends JFrame {

    JTextField txtId, txtName, txtDepartment, txtGpa, txtSearch;
    JButton btnAdd, btnDelete, btnClear, btnReport, btnUpdate, btnSearch, btnExport;

    JTable table;
    DefaultTableModel model;

    JLabel lblCount;

    JPanel topPanel;

    Color darkBlue = new Color(25, 45, 85);
    Color lightBlue = new Color(52, 152, 219);

    public UniversityManagementSystem() {

        setTitle("University Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // ================= TOP PANEL =================
        topPanel = new JPanel();
        topPanel.setBackground(darkBlue);
        topPanel.setBounds(0, 0, 1000, 80);
        topPanel.setLayout(null);

        JLabel title = new JLabel("University Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(250, 20, 600, 40);
        topPanel.add(title);

        add(topPanel);

        // ================= LEFT PANEL =================
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(20, 100, 360, 450);
        leftPanel.setBackground(new Color(245, 245, 245));
        add(leftPanel);

        // Labels
        JLabel l1 = new JLabel("ID");
        JLabel l2 = new JLabel("Name");
        JLabel l3 = new JLabel("Dept");
        JLabel l4 = new JLabel("GPA");

        l1.setBounds(20, 30, 100, 25);
        l2.setBounds(20, 90, 100, 25);
        l3.setBounds(20, 150, 100, 25);
        l4.setBounds(20, 210, 100, 25);

        leftPanel.add(l1);
        leftPanel.add(l2);
        leftPanel.add(l3);
        leftPanel.add(l4);

        // Textfields
        txtId = new JTextField(); txtId.setBounds(120, 30, 200, 30);
        txtName = new JTextField(); txtName.setBounds(120, 90, 200, 30);
        txtDepartment = new JTextField(); txtDepartment.setBounds(120, 150, 200, 30);
        txtGpa = new JTextField(); txtGpa.setBounds(120, 210, 200, 30);

        leftPanel.add(txtId);
        leftPanel.add(txtName);
        leftPanel.add(txtDepartment);
        leftPanel.add(txtGpa);

        // Buttons
        btnAdd = createButton("Add", lightBlue);
        btnUpdate = createButton("Update", new Color(241, 196, 15));
        btnDelete = createButton("Delete", Color.RED);
        btnClear = createButton("Clear", Color.GRAY);
        btnReport = createButton("Report", new Color(46, 204, 113));
        btnExport = createButton("Export", new Color(52, 73, 94));

        btnAdd.setBounds(20, 270, 140, 35);
        btnUpdate.setBounds(180, 270, 140, 35);
        btnDelete.setBounds(20, 320, 140, 35);
        btnClear.setBounds(180, 320, 140, 35);
        btnReport.setBounds(20, 370, 140, 35);
        btnExport.setBounds(180, 370, 140, 35);

        leftPanel.add(btnAdd);
        leftPanel.add(btnUpdate);
        leftPanel.add(btnDelete);
        leftPanel.add(btnClear);
        leftPanel.add(btnReport);
        leftPanel.add(btnExport);

        // ================= SEARCH =================
        txtSearch = new JTextField();
        txtSearch.setBounds(400, 520, 200, 35);
        add(txtSearch);

        btnSearch = createButton("Search", new Color(155, 89, 182));
        btnSearch.setBounds(610, 520, 120, 35);
        add(btnSearch);

        // ================= TABLE =================
        model = new DefaultTableModel(new String[]{
                "ID", "Name", "Department", "GPA", "Role"
        }, 0);

        table = new JTable(model);
        table.setRowHeight(25);

        JTableHeader header = table.getTableHeader();
        header.setBackground(darkBlue);
        header.setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(400, 100, 550, 400);
        add(sp);

        // ================= COUNT =================
        lblCount = new JLabel("Total Students: 0");
        lblCount.setBounds(400, 90, 200, 20);
        add(lblCount);

        // ================= ADD =================
        btnAdd.addActionListener(e -> {
            try {
                Student s = new Student(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        txtDepartment.getText(),
                        Double.parseDouble(txtGpa.getText())
                );

                model.addRow(new Object[]{
                        s.getId(), s.getName(), s.getDepartment(), s.getGpa(), s.getRole()
                });

                updateCount();
                clear();

                JOptionPane.showMessageDialog(null, "Student Added");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
            }
        });

        // ================= UPDATE =================
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                model.setValueAt(txtId.getText(), row, 0);
                model.setValueAt(txtName.getText(), row, 1);
                model.setValueAt(txtDepartment.getText(), row, 2);
                model.setValueAt(txtGpa.getText(), row, 3);
                JOptionPane.showMessageDialog(null, "Updated");
            }
        });

        // ================= DELETE =================
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Delete student?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                    updateCount();
                }
            }
        });

        // ================= CLEAR =================
        btnClear.addActionListener(e -> clear());

        // ================= SEARCH =================
        btnSearch.addActionListener(e -> {
            String key = txtSearch.getText().toLowerCase();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(key) ||
                        model.getValueAt(i, 1).toString().toLowerCase().contains(key)) {
                    table.setRowSelectionInterval(i, i);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Not Found");
        });

        // ================= REPORT (FIXED) =================
        btnReport.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                String report =
                        "===== STUDENT REPORT =====\n\n" +
                                "ID: " + model.getValueAt(row, 0) + "\n" +
                                "Name: " + model.getValueAt(row, 1) + "\n" +
                                "Department: " + model.getValueAt(row, 2) + "\n" +
                                "GPA: " + model.getValueAt(row, 3) + "\n" +
                                "Role: " + model.getValueAt(row, 4) + "\n\n" +
                                "==========================";

                new Student(1,"temp","temp",0).generateReport(report);

            } else {
                JOptionPane.showMessageDialog(null, "Select a student first!");
            }
        });

        // ================= EXPORT =================
        btnExport.addActionListener(e -> {
            try {
                FileWriter fw = new FileWriter("students.txt");

                for (int i = 0; i < model.getRowCount(); i++) {
                    fw.write(model.getValueAt(i,0)+","+
                            model.getValueAt(i,1)+","+
                            model.getValueAt(i,2)+","+
                            model.getValueAt(i,3)+"\n");
                }

                fw.close();
                JOptionPane.showMessageDialog(null, "Exported Successfully");

            } catch(Exception ex){}
        });

        // ================= ROW CLICK =================
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();

                txtId.setText(model.getValueAt(r,0).toString());
                txtName.setText(model.getValueAt(r,1).toString());
                txtDepartment.setText(model.getValueAt(r,2).toString());
                txtGpa.setText(model.getValueAt(r,3).toString());
            }
        });
    }

    // ================= METHODS =================
    JButton createButton(String t, Color c){
        JButton b = new JButton(t);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        return b;
    }

    void clear(){
        txtId.setText("");
        txtName.setText("");
        txtDepartment.setText("");
        txtGpa.setText("");
    }

    void updateCount(){
        lblCount.setText("Total Students: " + model.getRowCount());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->
                new UniversityManagementSystem().setVisible(true));
    }
}