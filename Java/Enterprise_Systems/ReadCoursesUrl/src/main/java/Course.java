import java.util.Objects;

//class Course that implements a Comparable based on number
public class Course implements Comparable<Course> {
    //initialize variables for the class Course
    private String number;
    private String title;
    private String instructor;
    private int hours;

    //this creates the constructor for class Course
    public Course(String num, String title, String instructor, int hrs) {
        setNumber(num);
        setTitle(title);
        setInstructor(instructor);
        setHours(hrs);
    }//end of Course constructor

    //this initializes the getters and setters for class Course
    public String getNum() {
        return number;
    }

    public void setNumber(String num) {
        this.number = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hrs) {
        this.hours = hrs;
    }
    //end of getters and setters

    //override toString method to print episodes with specific format
    @Override
    public String toString() {
        String string;
        string = String.format("%-12s %-32s %-13s %2d", number, title, instructor, hours);
        return string;
    } // end of toString

    //generated equals() by IDE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return number.equals(course.number) && instructor.equals(course.instructor);
    }

    //generated hashCode() by IDE
    @Override
    public int hashCode() {
        return Objects.hash(number, instructor);
    }

    //compareTo function to compare courses based on course number
    @Override
    public int compareTo(Course o) {
        int result = this.number.compareTo(o.number);
        if(result == 0){
            result = this.number.compareTo(o.number);
        }
        if(result == 0){
            result = this.instructor.compareTo(o.instructor);
        }
        return result;
    }//end of compareTo
}//end of class Course
