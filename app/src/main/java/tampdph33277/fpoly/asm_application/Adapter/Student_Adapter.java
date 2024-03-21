package tampdph33277.fpoly.asm_application.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tampdph33277.fpoly.asm_application.DTO.Student_DTO;
import tampdph33277.fpoly.asm_application.Danh_Sach;
import tampdph33277.fpoly.asm_application.MyAPI;
import tampdph33277.fpoly.asm_application.R;

public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.Viewholder> {
    Context context;
    List<Student_DTO> list_student;

    public Student_Adapter(Context context, List<Student_DTO> list_student) {
        this.context = context;
        this.list_student = list_student;
    }
    public void setStudentList(List<Student_DTO> newList) {
        this.list_student = newList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Student_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danh_sach,parent,false);
        Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Student_Adapter.Viewholder holder, int position) {

   Student_DTO student_dto= list_student.get(position);
        holder.tvIdStudent.setText("Mã Sinh Vien: " + student_dto.getName());
        holder.tvTenStudent.setText("Tên Loại: " + student_dto.getStudentId());
        holder.tvDtbStudent.setText("Nhà Cung Cấp: " + student_dto.getAverageScore());
        // Gán hình ảnh từ URL vào ImageView sử dụng thư viện Picasso
        Picasso.get().load(student_dto.getAvatar()).into(holder.imgAvatar);

        // Đăng ký sự kiện cho nút xóa và nút sửa
        holder.imgXoaStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi click vào nút xóa
            }
        });

        holder.imgEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student_DTO student = list_student.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_edit_danhsach, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                EditText editTextStudentId = dialogView.findViewById(R.id.editTextStudentId);
                EditText editTextStudentDtb = dialogView.findViewById(R.id.editTextStudentDtb);

                Button buttonChooseImage = dialogView.findViewById(R.id.buttonChooseImage);
                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
                AlertDialog alertDialog = dialogBuilder.create();
                editTextName.setText(student.getName());
                editTextStudentId.setText(student.getStudentId());
                editTextStudentDtb.setText(String.valueOf(student.getAverageScore()));
                buttonSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Xử lý lưu thông tin và đóng dialog
        // Ví dụ: lấy thông tin từ EditTexts
        String name = editTextName.getText().toString();
        String studentId = editTextStudentId.getText().toString();
String diemtb = editTextStudentDtb.getText().toString();
        Student_DTO updatedStudent = new Student_DTO();
        updatedStudent.setName(name);
        updatedStudent.setStudentId(studentId);
        updatedStudent.setAverageScore(Double.parseDouble(diemtb));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.18.170:3005/") // Địa chỉ URL cơ sở của máy chủ
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPI api = retrofit.create(MyAPI.class);

        Call<Student_DTO> call = api.updateStudent(student.getId(), updatedStudent);

        call.enqueue(new Callback<Student_DTO>() {
            @Override
            public void onResponse(Call<Student_DTO> call, Response<Student_DTO> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công

                    Student_DTO updatedStudent = response.body();
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                    // Cập nhật dữ liệu trong danh sách hoặc hiển thị thông báo thành công
                } else {
                    Toast.makeText(context, "Lõi if", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss(); // Đóng dialog sau khi hoàn thành yêu cầu
            }

            @Override
            public void onFailure(Call<Student_DTO> call, Throwable t) {
                Toast.makeText(context, "Lõi if ònifa", Toast.LENGTH_SHORT).show();

                alertDialog.dismiss(); // Đóng dialog khi gặp lỗi
            }
        });
    }
});
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_student.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView tvIdStudent,tvTenStudent,tvDtbStudent;

        public ImageView imgAvatar,imgXoaStudent,imgEditStudent;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvIdStudent = itemView.findViewById(R.id.tv_id_student);
            tvTenStudent = itemView.findViewById(R.id.tv_ten_student);
            tvDtbStudent = itemView.findViewById(R.id.tv_dtb_student);
            imgAvatar = itemView.findViewById(R.id.image_avatar);
            imgXoaStudent = itemView.findViewById(R.id.img_xoa_student);
            imgEditStudent = itemView.findViewById(R.id.img_edit_student);
        }
    }
}
