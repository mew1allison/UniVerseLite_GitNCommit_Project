package Frontend;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import Backend.CampusData;
import Backend.FileHandler;
import Backend.Student;

public class MainFrame extends JFrame {
    //These will be used in methods
    CampusData data = new CampusData();
    FileHandler filehandler = new FileHandler();

    //(all window functions inside constructor)
    //Segment 1: -----WINDOW SETUP------ 
    public MainFrame(){
        //Window Properties
        setTitle("University Management System");
        setSize(800, 550);
        setLocationRelativeTo(null); //Opens screen in center
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //File load on Window Opening
        data = filehandler.loadOnStartup("campusdata.dat");
        //Save file on Window Closing
        //We use abstract class 'WindowAdapter' that provides us with WindowListening events and abstract methods to be overriden (e.g. windowClosing, windowOpening)
        //The following is a COMPLETE variable declaration statement
        WindowAdapter listener = new WindowAdapter(){ 
        @Override //JFrame method windowClosing
        public void windowClosing(WindowEvent e)
        {
            filehandler.saveToFile(data, "campusdata.dat");
            dispose(); //closes the window
        }
    };
        addWindowListener(listener);

        //Segment 2: -----Tabs for campus entity data------ 
        //The following creates the tab bar on the top menu of our frame
        JTabbedPane tabs = new JTabbedPane();
        //Individual Tabs
        JPanel studentsPanel = new JPanel();
        JPanel coursesPanel = new JPanel();
        JPanel departmentsPanel = new JPanel();
        JPanel facilitiesPanel = new JPanel();
        JPanel servicesPanel = new JPanel();
        //Add tabs to tab bar
        tabs.addTab("Students", buildStudentPanel());
        tabs.addTab("Courses", coursesPanel);
        tabs.addTab("Departments", departmentsPanel);
        tabs.addTab("Facilities", facilitiesPanel);
        tabs.addTab("Services", servicesPanel);
        //Set Visible
        setVisible(true);
        //Add the tabs to our mainframe, and we follow BorderLayout
        add(tabs, BorderLayout.CENTER);
}

    //Create the Students Tab Contents
    //1) Students Form : Fill Student Information
    private JPanel buildStudentPanel(){
        //First create the main panel in form of BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        //Now create the submission form
        JPanel form = new JPanel();
        //Text Fields & Labels
        JTextField nameField = new JTextField(12);
        JTextField regField = new JTextField(12);
        JTextField programField = new JTextField(12);
        //Add Button to Save the Student Info
        JButton addBtn = new JButton("Add Student");
        //Add Button to Delete a Selected Student from the Table
        JButton deleteBtn = new JButton("Delete Student");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addBtn); buttonsPanel.add(deleteBtn);
        //Match Labels with the Text Fields
        form.add(new Label("Student Name: ")); form.add(nameField);
        form.add(new Label("Registration #: ")); form.add(regField);
        form.add(new Label("Program: ")); form.add(programField);
        form.add(buttonsPanel);
        

    //2) Create table to display student information
    //2.1)First Create the Column Names for the table
    String[] columns = {"Name", "Reg #", "Program"};
    DefaultTableModel model = new DefaultTableModel(columns, 0); //rows '0' since initially no entry is there
    //Create a table based on the model
    JTable table = new JTable(model);
    //Create a scrollpane to easily navigate the table
    JScrollPane scrollPane = new JScrollPane(table);

    //On loading, if there is existing data, this code will run
    //Display currently enrolled students' information from loaded file
        if(data.students != null && !data.students.isEmpty())
        {   for(int i = 0; i < data.students.size(); i++)
            {
                //For each column of our table, we pass a value that can be of different data type (e.g Name is String, Roll No can be Integer). Thus, we create an Object Array store objects inside that array, and pass it as an argument to addRow() for 'model'.
                //Retrieve the existing data from CampusData
                Student student = data.students.get(i);
                model.addRow(new Object[]{
                    student.getStname(),
                    student.getRegnum(),
                    student.getProgram()
                });
            }
        }
    
    //3)Add Functionality to addBtn
    addBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //The Button will read user's input in text field and save it to program data
            String stName = nameField.getText().trim();
            String regNo = regField.getText().trim();
            String program = programField.getText().trim();
            //First Check if user has typed anything or its empty
            if(stName.isEmpty() || regNo.isEmpty() || program.isEmpty())
            {
                JOptionPane.showMessageDialog(panel, "Please Enter Information Completely in All Fields.");
                return;
            }
            //Create a new Student object (we store it in our backend)
            Student s = new Student(stName, regNo, program);
            data.students.add(s);
            //Now add this student to our table and display on interface
            model.addRow(new Object[]{stName, regNo, program});
            //Clear Fields after adding
            nameField.setText("");
            regField.setText("");
            programField.setText("");
        }
    });

    //4) Add Functionality to Delete Button
    deleteBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            //Write delete button logic here
            //Find if a row is selected from the table
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1)
            {
                JOptionPane.showMessageDialog(panel, "Please Selected a Row to DELETE it");
            }
            //First remove the student from backend
            data.students.remove(selectedRow); //basically we remove a row like this since the row is actually just a Student object distributed under the Columns
            //Now remove from the gui
            model.removeRow(selectedRow);
        }
    });
        //Add the form to the panel
        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;

    }
}

