package tampdph33277.fpoly.asm_application.DTO;
import com.google.gson.annotations.SerializedName;
public class Student_DTO {

    private int id;


    private String name;


    private String studentId;


    private double averageScore;


    private String avatar;

    public Student_DTO() {
    }

    public Student_DTO(int id, String name, String studentId, double averageScore, String avatar) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.averageScore = averageScore;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
// Getters and setters
}
