package Backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {

    private static final long serialVersionUID = 5429667519936553852L;
    private static final int SHA_256_HEX_LENGTH = 64;
    
    //For the frontend and access responsibilty a user must have the following things to be ale to access the system
    private String username;
    // Stores the SHA-256 hash of the password, never the plaintext password.
    private String password;
    private String role; //Admin, Student, Teacher 

    //Argument Constructor
    public User(String username, String password, String role) {

        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    //Setters
    public final void setRole(String role) {

        if(role != null && !role.isBlank()) {
            this.role = role;
        }else{
            this.role = null;
        }
    }

    public final void setPassword(String password) {

        validatePassword(password);
        this.password = hashPassword(password);
    }

    public boolean verifyPassword(String input) {
        return input != null && password != null && hashPassword(input).equals(password);
    }

    public final void setUsername(String username) {

        if(username != null && !username.isBlank()) {
            this.username = username;
        }else{
            this.username = null;
        }
    }

    @Override
    public String toString() {
        return ("Username : " +username+"\nRole : " +role);
    }

    //method to give permsiion to access dependng upon the role 
    public boolean grantAccess(String action) {

        if(role.equalsIgnoreCase("Admin")) {
            return true;
        }else if(role.equalsIgnoreCase("Teacher")) {
            return action.equals("scheduleclass") || action.equals("modifyDeadline") || action.equals("uploadAssignment") ;
        }else if(role.equalsIgnoreCase("Student")) {
            return action.equals("enrollCourse") || action.equals("generateReport") || action.equals("dropCourse") || action.equals("viewTimeTable") || action.equals("generateSchedule");
        }else{
            return false;
        }
    }

    // A tab is restricted to Admin only when ALL features within it
    // are already gated behind admin-only grantAccess actions.
    // Students tab: Teacher can view enrolled courses (unrestricted btn),
    //               Student can enroll + view courses → not admin-only.
    // Courses tab:  only add/delete exist, both admin-only → safe to restrict.
    // Reports tab:  Student has grantAccess("generateReport") and no
    //               button-level checks → not admin-only.
    public boolean canViewStudents() {
        return true;
    }

    public boolean canViewCourses() {
        return role.equalsIgnoreCase("Admin");
    }

    public boolean canViewFacilities() {
        return true;
    }

    public boolean canViewReports() {
        return true;
    }

    public boolean canViewSchedules() {
        return true;
    }

    private static void validatePassword(String password) {
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank.");
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hash.length * 2);

            for(byte b : hash) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing is not available.", e);
        }
    }

    private static boolean isSha256Hash(String value) {
        return value != null && value.matches("[0-9a-fA-F]{" + SHA_256_HEX_LENGTH + "}");
    }

    // Converts old serialized plaintext passwords to hashes when loading saved data.
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        if(password != null && !isSha256Hash(password)) {
            password = hashPassword(password);
        }
    }

}//end of User Class 
