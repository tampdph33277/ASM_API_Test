package tampdph33277.fpoly.asm_application;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tampdph33277.fpoly.asm_application.DTO.Student_DTO;

public interface MyAPI {
    @PUT("students/{id}")
    Call<Student_DTO> updateStudent(@Path("id") int studentId, @Body Student_DTO student);

    @GET("students")
    Call<List<Student_DTO>> getStudents();


}
