package Frontend;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import Backend.*;


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
        tabs.addTab("Courses", buildCoursePanel());
        tabs.addTab("Departments", departmentsPanel);
        tabs.addTab("Facilities", facilitiesPanel);
        tabs.addTab("Services", servicesPanel);
        //Set Visible
        setVisible(true);
        //Add the tabs to our mainframe, and we follow BorderLayout
        add(tabs, BorderLayout.CENTER);
}

    //---------Create the Students Tab Contents--------
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
        //Add Button to Enroll Course for Selected Student
        JButton enrollBtn = new JButton("Enroll Course");
        //Add Button to Display enrolled courses for Selected Student
        JButton viewEnrolledBtn = new JButton("View Courses");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addBtn); buttonsPanel.add(deleteBtn); buttonsPanel.add(enrollBtn); buttonsPanel.add(viewEnrolledBtn);
        //Match Labels with the Text Fields
        form.add(new Label("Student Name: ")); form.add(nameField);
        form.add(new Label("Registration #: ")); form.add(regField);
        form.add(new Label("Program: ")); form.add(programField);
        form.add(buttonsPanel);
        

    //2) Create table to display student information
    //2.1)First Create the Column Names for the table
    String[] columns = {"Name", "Reg #", "Program", "Total Courses"};
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
                    student.getProgram(),
                    student.getCourseList().size()
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
                JOptionPane.showMessageDialog(panel, "Please Select a Row to DELETE it");
                return;
            }
            //First remove the student from backend
            data.students.remove(selectedRow); //basically we remove a row like this since the row is actually just a Student object distributed under the Columns
            //Now remove from the gui
            model.removeRow(selectedRow);
        }
    });
    //5) Add Functionality to Enroll Button
    enrollBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            //A row needs to be selected first
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1)
            {
                JOptionPane.showMessageDialog(panel, "Please SELECT a Student First.");
                return;
            }
            //SelectedRow is an integer used for the Student Object inside that row. We can use it to search for our Student Object in backend that we saved earlier.
            Student selectedStudent = data.students.get(selectedRow);
            //Display Available Courses in PopUp
            //Check if courses are available in saved file
            if(data.courses == null || data.courses.isEmpty())
            {
                JOptionPane.showMessageDialog(panel,"No Courses Available. Please ADD courses first.");
                return;
            }
            //If available, retrieve courses (names only) array from backend for the popup
            String[] courseNames = new String[data.courses.size()];
            //Fill array
            for(int i = 0; i < data.courses.size(); i++)
            {
                courseNames[i] = data.courses.get(i).getCourseName();
            }
            //JComboBox to select a course from available courses
            JComboBox<String> courseDropDown = new JComboBox<String>(courseNames);
            //Put it in the PopUp message (result integer holds value of button picked by user (yes, no , X))
            int result = JOptionPane.showConfirmDialog(panel, courseDropDown, "Select a COURSE to ENROLL", JOptionPane.OK_CANCEL_OPTION);

            //Check which course the user picked from dropdown
            if(result == JOptionPane.OK_OPTION)
            {
                int pickedIndex = courseDropDown.getSelectedIndex();
                Course pickedCourse = data.courses.get(pickedIndex);
            //Safety Check : Student must not be enrolled in course already
            if(selectedStudent.getCourseList().contains(pickedCourse))
            {
                JOptionPane.showMessageDialog(panel, "Student is already enrolled in this Course.");
                return;
            }
            //Update courses in students class (Backend), then save it again to data
            int index = data.students.indexOf(selectedStudent);
            data.students.get(index).enroll(pickedCourse);

            //Update the Total Courses cell in the Table (new value, row, column)
            model.setValueAt(selectedStudent.getCourseList().size(), selectedRow, 3);
            JOptionPane.showMessageDialog(panel, "Student Enrolled Successfully!");
        }

        }

    });
    viewEnrolledBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
        // Check a student is selected
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Please select a student first.");
            return;
        }

        Student selectedStudent = data.students.get(selectedRow);

        // Check if student is enrolled in anything
        if (selectedStudent.getCourseList().isEmpty()) {
            JOptionPane.showMessageDialog(panel, selectedStudent.getStname() + " is not enrolled in any courses yet.");
            return;
        }
        
        //Store all courses neatly in a string and display
        String courseList = "Courses Enrolled By " + selectedStudent.getStname() + " : ";
        for(int i = 0; i < selectedStudent.getCourseList().size(); i++)
        {
            Course c = selectedStudent.getCourseList().get(i);
            courseList += (i + 1) + ". " + c.getCourseName() + " ( " + c.getCourseID() + " )\n";
        }
        //Show the display message
        JOptionPane.showMessageDialog(panel, courseList, "Enrolled Courses", JOptionPane.INFORMATION_MESSAGE); //we need to write the INFORMATION_MESSAGE since this is how the method argument is
        }
    });

        //Add the form to the panel
        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;

    }
    
    //---------Create the Courses Tab Contents--------
    private JPanel buildCoursePanel()
    {
        //1) Create the Course Form
        JPanel panel = new JPanel(new BorderLayout()); //Main Panel
        JPanel form = new JPanel(); //default FlowLayout
        JTextField nameField = new JTextField(12);
        JTextField IDField = new JTextField(12);
        JTextField creditHrsField = new JTextField("0", 5);
        form.add(new Label("Course Name: ")); form.add(nameField);
        form.add(new Label("Course ID: ")); form.add(IDField);
        form.add(new Label("Credit Hours: ")); form.add(creditHrsField);
        
        //2)Create the Buttons
        JButton addBtn = new JButton("Add Course");
        JButton deleteBtn = new JButton("Delete Course");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn); buttonPanel.add(deleteBtn);

        //3)Create the Table
        String[] columns = {"Course Name", "Course ID", "Credit Hours"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        //4)Retrieve Saved Data from File
        if(data.courses != null && !data.courses.isEmpty())
        {   for(int i = 0; i < data.courses.size(); i++)
            {
                Course course = data.courses.get(i);
                model.addRow(new Object[]{
                    course.getCourseName(),
                    course.getCourseID(),
                    course.getCreditHours(),
                });
            }
        }

        //5)Add Functionality to Buttons
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //Retrieve the information in textboxes
                String crsName = nameField.getText().trim();
                String crsID = IDField.getText().trim();
                String creditHrs = creditHrsField.getText().trim();
                //Check if any box is empty
                if(crsName.isEmpty() || crsID.isEmpty() || creditHrs.equals("0"))
                {
                    JOptionPane.showMessageDialog(panel, "Please Enter Information Completely in All Fields.");
                    return; //Dont allow to add empty entry
                }
                //Create new Course Object
                int creditHRS = Integer.parseInt(creditHrs);
                Course course = new Course(crsName, crsID, creditHRS, null);
                //Save to Backend
                data.courses.add(course);
                //Display on Frontend
                model.addRow(new Object[]{
                    course.getCourseName(),
                    course.getCourseID(),
                    course.getCreditHours(),
                });
                //Make fields empty after saving
                nameField.setText("");
                IDField.setText("");
                creditHrsField.setText("");

            }
        });

        deleteBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            //Find if a row is selected from the table
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1)
            {
                JOptionPane.showMessageDialog(panel, "Please Select a Row to DELETE it");
            }
            data.courses.remove(selectedRow); 
            model.removeRow(selectedRow);
        }
    });
        form.add(buttonPanel);
        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
        
    }
}

