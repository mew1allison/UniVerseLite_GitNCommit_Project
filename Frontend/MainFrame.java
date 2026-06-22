package Frontend;

import Backend.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame {

    //These will be used in methods
    final CampusData data;
    FileHandler filehandler = new FileHandler();
    User currentUser; 

    //(all window functions inside constructor)
    //Segment 1: -----WINDOW SETUP------ 
    public MainFrame(User currentUser, CampusData data){

        this.currentUser = currentUser;
        this.data = data;

        //Window Properties
        setTitle("University Management System");
        setSize(1000, 550);
        setLocationRelativeTo(null); //Opens screen in center
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Save file on Window Closing
        //We use abstract class 'WindowAdapter' that provides us with WindowListening events and abstract methods to be overriden (e.g. windowClosing, windowOpening)
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
        //Add tabs to tab bar
        tabs.addTab("Students", buildStudentPanel());
        tabs.addTab("Courses", buildCoursePanel());
        //tabs.addTab("Departments", buildDepartmentPanel());
        tabs.addTab("Facilities", buildFacilityPanel());
        tabs.addTab("Reports", buildReportPanel());
       tabs.addTab("Schedules", buildSchedulePanel());
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

        //Main Heading
        
        //Text Fields & Labels
        JTextField nameField = AppTheme.styledField(12);
        JTextField regField = AppTheme.styledField(12);
        JTextField programField = AppTheme.styledField(12);
        JTextField cgpaField = AppTheme.styledField(5);

        //Add Button to Save the Student Info
        JButton addBtn = AppTheme.primaryButton("Add Student");
        //Add Button to Delete a Selected Student from the Table
        JButton deleteBtn = AppTheme.dangerButton("Delete Student");
        //Add Button to Enroll Course for Selected Student
        JButton enrollBtn = AppTheme.secondaryButton("Enroll Course");
        //Add Button to Display enrolled courses for Selected Student
        JButton viewEnrolledBtn = AppTheme.secondaryButton("View Courses");

        //Adding checks to ensure correct accesss is granted 

        if(!currentUser.grantAccess("addStudent")) {
            addBtn.setVisible(false);
        }

        if(!currentUser.grantAccess("removeStudent")) {
            deleteBtn.setVisible(false);
        }

        if(currentUser.grantAccess("enrollCourse")) {
            enrollBtn.setVisible(true);
        }


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addBtn); buttonsPanel.add(deleteBtn); buttonsPanel.add(enrollBtn); buttonsPanel.add(viewEnrolledBtn);
        //Match Labels with the Text Fields
        form.add(new JLabel("Student Name: ")); form.add(nameField);
        form.add(new JLabel("Registration #: ")); form.add(regField);
        form.add(new JLabel("Program: ")); form.add(programField);
        form.add(new JLabel("CGPA: ")); form.add(cgpaField);
        

    //2) Create table to display student information
    //2.1)First Create the Column Names for the table
    String[] columns = {"Name", "Reg #", "Program", "CGPA", "Total Courses"};
    DefaultTableModel model = new DefaultTableModel(columns, 0); //rows '0' since initially no entry is there
    //Create a table based on the model
    JTable table = new JTable(model);
    AppTheme.styledTable(table);
    //To centralise the elements of our table, we use DefaultTableCellRenderer
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for(int i = 0; i < columns.length; i++)
    {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
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
                    student.getCGPA(),
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
            String cgpa1 = cgpaField.getText().trim();


            //First Check if user has typed anything or its empty
            if(stName.isEmpty() || regNo.isEmpty() || program.isEmpty() || cgpa1.isEmpty())
            {
                JOptionPane.showMessageDialog(panel, "Please Enter Information Completely in All Fields.");
                return;
            }
            //Then check if student already exists
            for(Student student : data.students)
            {
                if(student.getStname().equalsIgnoreCase(stName) || student.getRegnum().equalsIgnoreCase(regNo))
                {
                    JOptionPane.showMessageDialog(panel, "Student already enrolled. Please add a new student.");
                    return;
                }
            }
            //Create a new Student object (we store it in our backend)
            double cgpa = Double.valueOf(cgpa1);
            Student s = new Student(stName, regNo, program, cgpa);
            data.students.add(s);
            //Now add this student to our table and display on interface
            model.addRow(new Object[]{stName, regNo, program, cgpa, 0});
            //Clear Fields after adding
            nameField.setText("");
            regField.setText("");
            programField.setText("");
            cgpaField.setText("");
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
            JComboBox<String> courseDropDown = new JComboBox<>(courseNames);
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
            model.setValueAt(selectedStudent.getCourseList().size(), selectedRow, 4);
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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;

    }
    
    //---------Create the Courses Tab Contents--------
    private JPanel buildCoursePanel()
    {
        //1) Create the Course Form
        JPanel panel = new JPanel(new BorderLayout()); //Main Panel
        JPanel form = new JPanel(); //default FlowLayout
        JTextField nameField = AppTheme.styledField(12);
        JTextField IDField = AppTheme.styledField(12);
        JTextField creditHrsField = AppTheme.styledField(5);
        form.add(new JLabel("Course Name: ")); form.add(nameField);
        form.add(new JLabel("Course ID: ")); form.add(IDField);
        form.add(new JLabel("Credit Hours: ")); form.add(creditHrsField);
        
        //2)Create the Buttons
        JButton addBtn = AppTheme.primaryButton("Add Course");
        JButton deleteBtn = AppTheme.dangerButton("Delete Course");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn); buttonPanel.add(deleteBtn);

        //Access hecks are added 

        if(!currentUser.grantAccess("addCourse")) {
            addBtn.setVisible(false);
        }

        if(!currentUser.grantAccess("Delete Course")) {
            deleteBtn.setVisible(false);
        }
        //3)Create the Table
        String[] columns = {"Course Name", "Course ID", "Credit Hours"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        AppTheme.styledTable(table);
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
                Course course = new Course(crsName, crsID, creditHRS, null); //check it 
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
    
        //form.add(buttonPanel);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
        
    }
    // FACILITIES TAB (inner tabs for Library, Cafeteria, Hostel)
    private JPanel buildFacilityPanel() {
 
        JPanel panel = new JPanel(new BorderLayout());
 
        // Inner tab bar nested inside the Facilities tab
        JTabbedPane innerTabs = new JTabbedPane();
        innerTabs.addTab("Library",   buildLibraryPanel());
        innerTabs.addTab("Cafeteria", buildCafeteriaPanel());
        innerTabs.addTab("Hostel",    buildHostelPanel());
 
        panel.add(innerTabs, BorderLayout.CENTER);
        return panel;
    }
 
    // LIBRARY SUB-TAB

    private JPanel buildLibraryPanel() {
 
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form  = new JPanel();
 
        JTextField bookNameField = AppTheme.styledField(10);
        JTextField authorField   = AppTheme.styledField(10);
        JTextField editionField  = AppTheme.styledField(6);
 
        form.add(new JLabel("Book Name:")); form.add(bookNameField);
        form.add(new JLabel("Author:"));    form.add(authorField);
        form.add(new JLabel("Edition:"));   form.add(editionField);
 
        JButton addBookBtn    = AppTheme.primaryButton("Add Book");
        JButton deleteBookBtn = AppTheme.dangerButton("Delete Book");
        JButton opCostBtn     = AppTheme.secondaryButton("Show Op. Cost");
 
        JPanel btnPanel = new JPanel();
        btnPanel.add(addBookBtn); btnPanel.add(deleteBookBtn);
        btnPanel.add(opCostBtn);
        //form.add(btnPanel);
 
        // Columns matching Books attributes
        String[] columns = {"Book Name", "Author", "Edition", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0); //rows '0' since initially no entry is there
        //Create a table based on the model
            JTable table = new JTable(model);
        AppTheme.styledTable(table);
        //To centralise the elements of our table, we use DefaultTableCellRenderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++)
        {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        //Create a scrollpane to easily navigate the table
        JScrollPane sp = new JScrollPane(table);
        // ── LOAD BOOKS FROM FILE ──────────────────────────────────────────
        boolean doesExist = false;
        // data.library.getBooks() returns the b1 ArrayList inside Library
        if (data.library != null && data.library.getBooks() != null
                && !data.library.getBooks().isEmpty()) {
            for (int i = 0; i < data.library.getBooks().size(); i++) {
                Books b = data.library.getBooks().get(i);
                model.addRow(new Object[]{
                    b.getBookName(),
                    b.getAuthorName(),
                    b.getEdition(),
                    b.getAvailability() // true = available, false = borrowed
                });
                doesExist = true;
            }
        }
        //If there is no library, we create a new one
        if(!doesExist)
        {Library library = new Library("Main Library", "E-Block", 121, 500, 20);
        data.library = library;}
 
        // ── ADD BOOK ─────
        addBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bName   = bookNameField.getText().trim();
                String author  = authorField.getText().trim();
                String edition = editionField.getText().trim();
 
                if (bName.isEmpty() || author.isEmpty() || edition.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill all book fields.");
                    return;
                }
 
                Books book = new Books(bName, author, edition);
                data.library.add(book); 
 
                model.addRow(new Object[]{bName, author, edition, true});
 
                bookNameField.setText(""); authorField.setText(""); editionField.setText("");
            }
        });
 
        // ── DELETE BOOK ─────
        deleteBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel, "Please select a book first.");
                    return;
                }
                // Get the actual Books object from library and remove it
                Books book = data.library.getBooks().get(selectedRow);
                data.library.remove(book); 
                model.removeRow(selectedRow);
            }
        });
 
        // ── OPERATIONAL COST ─────
        opCostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = data.library.calculateOperationalCost();
                JOptionPane.showMessageDialog(panel,
                    "Library Operational Cost : Rs. " + cost,
                    "Operational Cost", JOptionPane.INFORMATION_MESSAGE);
            }
        });
 
        //Creating a top panel to ensure that buttons are seen 
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        topPanel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(sp,   BorderLayout.CENTER);
        return panel;
    }
 
    // CAFETERIA SUB-TAB 
    private JPanel buildCafeteriaPanel() {
 
        JPanel panel = new JPanel(new BorderLayout());
 
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel(
            "Main Cafeteria  |  Location: Block E  |  Food Items: 20  |  Avg Meal Cost: Rs.250"
        ));
        panel.add(infoPanel, BorderLayout.NORTH);
 
        String[] columns = {"#", "Menu Item", "Price (Rs.)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table   = new JTable(model);
        AppTheme.styledTable(table);
        JScrollPane sp = new JScrollPane(table);
 
        model.addRow(new Object[]{"1", "Biryani", "150"});
        model.addRow(new Object[]{"2", "Daal Chawal", "100"});
        model.addRow(new Object[]{"3", "Chicken Karahi", "200"});
        model.addRow(new Object[]{"4", "Paratha", "30"});
        model.addRow(new Object[]{"5", "Omelette", "50"});
        model.addRow(new Object[]{"6", "Tea", "20"});
        model.addRow(new Object[]{"7", "Cold Drink", "60"});

        // Create cafeteria if it doesn't exist
       if(data.cafeteria == null) {
            data.cafeteria = new Cafeteria("Main Cafeteria", "Block E", 122, 500, 20, 20, 250);
        }
 
        // Operational cost button — calls backend method
        JButton opCostBtn = AppTheme.primaryButton("Show Operational Cost");
        JPanel btnPanel = new JPanel();
        btnPanel.add(opCostBtn);
        opCostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = data.cafeteria.calculateOperationalCost();
                JOptionPane.showMessageDialog(panel,
                    "Cafeteria Operational Cost : Rs. " + cost,
                    "Operational Cost", JOptionPane.INFORMATION_MESSAGE);
            }
        });
 
        panel.add(sp, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
 
    // HOSTEL SUB-TAB 
    private JPanel buildHostelPanel() {

        JPanel panel = new JPanel(new BorderLayout());
 
        // Static info label at top
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel(
            "HOSTELS"));
        panel.add(infoPanel, BorderLayout.NORTH);
 
        String[] columns = {"#", "Hostel Name", "Total Rooms"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table   = new JTable(model);
        AppTheme.styledTable(table);
        JScrollPane sp = new JScrollPane(table);
 
        // ── BUTTONS ─────────
        JButton opCostBtn = AppTheme.primaryButton("Show Operational Cost");
        JPanel btnPanel = new JPanel();
        btnPanel.add(opCostBtn);
 
        // ── OPERATIONAL COST ───────
        //Create dummy hostels
        Hostel hostel1 = new Hostel("AliGarh Hostel", "Chak Faisal", 131, 5000, 50, 25);
        Hostel hostel2 = new Hostel("Shalimar Hostel", "Chak Shahzad", 132, 5000, 50, 20);
        Hostel hostel3 = new Hostel("Chachu Hostel", "Chak Kharian", 133, 5000, 50, 25);
        data.hostels.add(hostel1); data.hostels.add(hostel2); data.hostels.add(hostel3);
        //Add to the table
        model.addRow(new Object[]{hostel1.getName(), hostel1.getLocation(), hostel1.getTotalRooms()});
        model.addRow(new Object[]{hostel2.getName(), hostel2.getLocation(), hostel2.getTotalRooms()});
        model.addRow(new Object[]{hostel3.getName(), hostel3.getLocation(), hostel3.getTotalRooms()});
        
        opCostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               double cost = 0;
               for(int i = 0; i < data.hostels.size(); i++)
               {
                    cost += data.hostels.get(i).calculateOperationalCost();
               }
                JOptionPane.showMessageDialog(panel,
                    "Hostel Operational Cost : Rs. " + cost,
                    "Operational Cost", JOptionPane.INFORMATION_MESSAGE);
            }
        });
 
        panel.add(sp,       BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
    //Building the Report Panel
    private JPanel buildReportPanel()
    {
        JPanel panel = new JPanel(new BorderLayout()); //main panel
        JLabel infoLabel = new JLabel("REPORTS");
        //1) Library Report
        JButton libraryBtn = AppTheme.primaryButton("Library Report");
        JButton departmentBtn = AppTheme.primaryButton("Department Report");
        panel.add(libraryBtn, BorderLayout.WEST);
        panel.add(departmentBtn, BorderLayout.EAST);
        libraryBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String result = data.library.generateReport();
                JOptionPane.showMessageDialog(panel, result, "Library Report", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //Dummy department data
                Department dept1 = new Department("CS Department", "D-Block", 719);
                Department dept2 = new Department("Humanities Department", "A-Block", 289);
                Department dept3 = new Department("Chemistry Department", "B-Block", 893);
                data.departments.add(dept1); data.departments.add(dept2); data.departments.add(dept3);
        departmentBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                String result = "DEPARTMENT PERFORMANCE DATA\n";
                for(int i = 0; i < data.departments.size(); i++)
                {
                    result += data.departments.get(i).generateReport() + "\n";
                }
                JOptionPane.showMessageDialog(panel, result, "Department Report", JOptionPane.INFORMATION_MESSAGE);
                result = "";
            }
        });
        return panel;
        
    }
    //Build Schedules Panel (Student Courses and Bus Times)
    private JPanel buildSchedulePanel() {

    JPanel panel = new JPanel(new BorderLayout());

    // Create two classrooms
    Classroom room1 = new Classroom("Room 101", "Block A", 1, true, 40);
    Classroom room2 = new Classroom("Room 202", "Block B", 2, true, 35);
    ArrayList<Classroom> classroomList = new ArrayList<>();
    classroomList.add(room1);
    classroomList.add(room2);

    // Create two courses and assign classrooms to them
    // Course(name, id, creditHours, classroomList)
    Course c1 = new Course("Object Oriented Programming", "CS201", 3, classroomList);
    Course c2 = new Course("Discrete Structures",         "CS202", 3, classroomList);

    // It sets the classroom and timeslot inside the Course object
    c1.scheduleClass();
    c2.scheduleClass();

    // Create transport service and add buses to it
    TransportService transport = new TransportService("Campus Transport", "Gate 1", 601);

    // Bus(busNumber, numberPlate, arrivalTime, departureTime, normalRoute, peakHourRoute)
    Bus b1 = new Bus("Bus-01", "LEA-1234", "7:30 AM", "7:45 AM", "Tarlai → Campus",        "Tarlai → Faizabad → Campus");
    Bus b2 = new Bus("Bus-02", "LEA-5678", "7:50 AM", "8:00 AM", "Chandni Chowk → Campus", "Chandni Chowk → Khanna → Campus");
    Bus b3 = new Bus("Bus-03", "LEA-9012", "9:00 AM", "9:15 AM", "Fazal Town → Campus",    "Fazal Town → Scheme 3 → Campus");

    transport.addBus(b1);
    transport.addBus(b2);
    transport.addBus(b3);

    // ── TWO BUTTONS ───────
    JButton classScheduleBtn = AppTheme.primaryButton("View Class Schedule");
    JButton busScheduleBtn   = AppTheme.primaryButton("View Bus Schedule");

    JPanel btnPanel = new JPanel(); // FlowLayout — buttons sit side by side
    btnPanel.add(classScheduleBtn);
    btnPanel.add(busScheduleBtn);
    panel.add(btnPanel, BorderLayout.NORTH);

    // Instead of message box, I used TextArea here

    JTextArea outputArea = new JTextArea();
    outputArea.setEditable(false);  // read only
    outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    JScrollPane sp = new JScrollPane(outputArea);
    panel.add(sp, BorderLayout.CENTER);

    // ── CLASS SCHEDULE BUTTON ──────
    classScheduleBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Clear previous output
            outputArea.setText("");
            outputArea.append(" CLASS SCHEDULE \n");
            // c1 schedule
            if (c1.getAssignedClassroom() != null) {
                outputArea.append("Course     : " + c1.getCourseName() + "\n");
                outputArea.append("Course ID  : " + c1.getCourseID() + "\n");
                outputArea.append("Credit Hrs : " + c1.getCreditHours()+ "\n");
                outputArea.append("Classroom  : " + c1.getAssignedClassroom().getName() + "\n");
                outputArea.append("Location   : " + c1.getAssignedClassroom().getLocation() + "\n");
                outputArea.append("Timeslot   : " + c1.getTimeSlot()+ "\n");
            } else {
                outputArea.append(c1.getCourseName() + " : No classroom assigned.\n");
            }

            outputArea.append("\n------------------------------\n\n");

            // c2 schedule
            if (c2.getAssignedClassroom() != null) {
                outputArea.append("Course     : " + c2.getCourseName()+ "\n");
                outputArea.append("Course ID  : " + c2.getCourseID()+ "\n");
                outputArea.append("Credit Hrs : " + c2.getCreditHours()+ "\n");
                outputArea.append("Classroom  : " + c2.getAssignedClassroom().getName() + "\n");
                outputArea.append("Location   : " + c2.getAssignedClassroom().getLocation() + "\n");
                outputArea.append("Timeslot   : " + c2.getTimeSlot()                    + "\n");
            } else {
                outputArea.append(c2.getCourseName() + " : No classroom assigned.\n");
            }

            outputArea.append("\n==============================\n");
        }
    });

    // ── BUS SCHEDULE BUTTON ─────
    busScheduleBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            outputArea.setText("");
            outputArea.append(" BUS SCHEDULE \n");

            outputArea.append("Service  : " + transport.getName()     + "\n");
            outputArea.append("Location : " + transport.getLocation() + "\n");
            outputArea.append("Total Buses : " + transport.getTotalBuses() + "\n\n");

            // Loop through the buses ArrayList inside transport
            for (int i = 0; i < transport.getBuses().size(); i++) {
                Bus b = transport.getBuses().get(i);
                outputArea.append("--- Bus " + (i + 1) + " ---\n");
                outputArea.append("Bus No.      : " + b.getBusnumber()      + "\n");
                outputArea.append("Number Plate : " + b.getBusNumberPlate() + "\n");
                outputArea.append("Arrival      : " + b.getArrivalTime()    + "\n");
                outputArea.append("Departure    : " + b.getDepartureTime()  + "\n");
                outputArea.append("Route        : " + b.getCurrentRoute()   + "\n\n");
            }

            outputArea.append("==============================\n");
        }
    });

    return panel;
}

}




