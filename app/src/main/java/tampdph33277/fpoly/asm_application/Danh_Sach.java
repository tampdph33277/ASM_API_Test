package tampdph33277.fpoly.asm_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tampdph33277.fpoly.asm_application.Adapter.Student_Adapter;
import tampdph33277.fpoly.asm_application.DTO.Student_DTO;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Danh_Sach extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Student_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        recyclerView = findViewById(R.id.recyclerViewStudents);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.18.170:3005/") // Thay your-server-ip và port bằng thông tin máy chủ Node.js của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo một instance của interface API
        MyAPI api = retrofit.create(MyAPI.class);

        // Gửi yêu cầu HTTP đến máy chủ Node.js để lấy danh sách sinh viên
        Call<List<Student_DTO>> call = api.getStudents();
        call.enqueue(new Callback<List<Student_DTO>>() {
            @Override
            public void onResponse(Call<List<Student_DTO>> call, Response<List<Student_DTO>> response) {
                if (response.isSuccessful()) {
                    // Nhận danh sách sinh viên từ phản hồi
                    List<Student_DTO> studentList = response.body();

                    // Tạo Adapter và thiết lập danh sách sinh viên
                    adapter = new Student_Adapter(Danh_Sach.this, studentList);

                    // Thiết lập Adapter cho RecyclerView
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Danh_Sach.this));
                } else {
                    Toast.makeText(Danh_Sach.this, "Không thể lấy dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student_DTO>> call, Throwable t) {
                Toast.makeText(Danh_Sach.this, "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Danh_Sach", "Đã xảy ra lỗi: " + t.getMessage(), t);
            }
        });
    }
}