package vn.edu.vhu.ltdd.a4events.a4_231a290089;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A4_231A290089";

    private EditText edtSoA, edtSoB, edtCanNang, edtChieuCao;
    private TextView tvKetQua, tvBmi, tvPhanLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // 1. Ánh xạ View
        edtSoA = findViewById(R.id.edtSoA);
        edtSoB = findViewById(R.id.edtSoB);
        tvKetQua = findViewById(R.id.tvKetQua);

        edtCanNang = findViewById(R.id.edtCanNang);
        edtChieuCao = findViewById(R.id.edtChieuCao);
        tvBmi = findViewById(R.id.tvBmi);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);

        Button btnCong = findViewById(R.id.btnCong);
        Button btnTru = findViewById(R.id.btnTru);
        Button btnNhan = findViewById(R.id.btnNhan);
        Button btnChia = findViewById(R.id.btnChia);
        Button btnXoa = findViewById(R.id.btnXoa);
        Button btnTinhBmi = findViewById(R.id.btnTinhBmi);

        // Ánh xạ 2 nút nâng cao NC1
        Button btnPhanTram = findViewById(R.id.btnPhanTram);
        Button btnDaoDau = findViewById(R.id.btnDaoDau);

        // 2. Gán sự kiện
        // Cách 1: Lambda cho từng nút
        btnCong.setOnClickListener(v -> tinhToan('+'));
        btnTru.setOnClickListener(v -> tinhToan('-'));

        // Cách 2: Listener dùng chung
        View.OnClickListener chung = v -> {
            int id = v.getId();
            if (id == R.id.btnNhan) {
                tinhToan('*');
            } else if (id == R.id.btnChia) {
                tinhToan('/');
            }
        };
        btnNhan.setOnClickListener(chung);
        btnChia.setOnClickListener(chung);

        btnXoa.setOnClickListener(v -> xoaTrang());
        btnTinhBmi.setOnClickListener(v -> tinhBmi());

        // Bắt sự kiện NC1
        btnPhanTram.setOnClickListener(v -> tinhPhanTram());
        btnDaoDau.setOnClickListener(v -> daoDau());
    }

    private void tinhToan(char phepToan) {
        String chuoiA = edtSoA.getText().toString().trim();
        String chuoiB = edtSoB.getText().toString().trim();

        if (chuoiA.isEmpty()) {
            edtSoA.setError(getString(R.string.err_empty));
            edtSoA.requestFocus();
            return;
        }
        if (chuoiB.isEmpty()) {
            edtSoB.setError(getString(R.string.err_empty));
            edtSoB.requestFocus();
            return;
        }

        double a, b;
        try {
            a = Double.parseDouble(chuoiA);
            b = Double.parseDouble(chuoiB);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi định dạng", e);
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
            return;
        }

        if (phepToan == '/' && b == 0) {
            edtSoB.setError(getString(R.string.err_divide_zero));
            Toast.makeText(this, R.string.err_divide_zero, Toast.LENGTH_SHORT).show();
            return;
        }

        double ketQua;
        switch (phepToan) {
            case '+':
                ketQua = a + b;
                break;
            case '-':
                ketQua = a - b;
                break;
            case '*':
                ketQua = a * b;
                break;
            default:
                ketQua = a / b;
                break;
        }

        tvKetQua.setText(String.format(Locale.getDefault(), "%.2f %c %.2f = %.2f", a, phepToan, b, ketQua));
    }

    // NC1: Tính phần trăm A % của B (hoặc A / 100 nếu B rỗng)
    private void tinhPhanTram() {
        String chuoiA = edtSoA.getText().toString().trim();
        if (chuoiA.isEmpty()) {
            edtSoA.setError(getString(R.string.err_empty));
            edtSoA.requestFocus();
            return;
        }
        try {
            double a = Double.parseDouble(chuoiA);
            String chuoiB = edtSoB.getText().toString().trim();
            if (chuoiB.isEmpty()) {
                double ketQua = a / 100.0;
                tvKetQua.setText(String.format(Locale.getDefault(), "%.2f%% = %.4f", a, ketQua));
            } else {
                double b = Double.parseDouble(chuoiB);
                double ketQua = (a * b) / 100.0;
                tvKetQua.setText(String.format(Locale.getDefault(), "%.2f%% của %.2f = %.2f", a, b, ketQua));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
        }
    }

    // NC1: Đảo dấu số đang có ở ô A
    private void daoDau() {
        String chuoiA = edtSoA.getText().toString().trim();
        if (chuoiA.isEmpty()) {
            edtSoA.setError(getString(R.string.err_empty));
            edtSoA.requestFocus();
            return;
        }
        try {
            double a = Double.parseDouble(chuoiA);
            a = a * -1;
            edtSoA.setText(String.valueOf(a));
            edtSoA.setSelection(edtSoA.getText().length());
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaTrang() {
        edtSoA.setText("");
        edtSoB.setText("");
        edtSoA.setError(null);
        edtSoB.setError(null);
        tvKetQua.setText(R.string.result_placeholder);
        edtSoA.requestFocus();
    }

    private void tinhBmi() {
        String chuoiNang = edtCanNang.getText().toString().trim();
        String chuoiCao = edtChieuCao.getText().toString().trim();

        if (chuoiNang.isEmpty()) {
            edtCanNang.setError(getString(R.string.err_empty));
            edtCanNang.requestFocus();
            return;
        }
        if (chuoiCao.isEmpty()) {
            edtChieuCao.setError(getString(R.string.err_empty));
            edtChieuCao.requestFocus();
            return;
        }

        try {
            double canNang = Double.parseDouble(chuoiNang);
            double chieuCao = Double.parseDouble(chuoiCao);

            if (canNang <= 0 || chieuCao <= 0) {
                Toast.makeText(this, R.string.err_positive, Toast.LENGTH_SHORT).show();
                return;
            }

            if (chieuCao > 3) {
                chieuCao = chieuCao / 100.0;
            }

            double bmi = canNang / (chieuCao * chieuCao);
            tvBmi.setText(String.format(Locale.getDefault(), "BMI = %.1f", bmi));

            // NC3: Cập nhật chữ và đổi màu cảnh báo
            capNhatPhanLoaiBmi(bmi);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi định dạng BMI", e);
            Toast.makeText(this, R.string.err_not_number, Toast.LENGTH_SHORT).show();
        }
    }

    // NC3: Phân loại kèm đổi màu chữ trực tiếp theo tiêu chuẩn WHO Châu Á
    private void capNhatPhanLoaiBmi(double bmi) {
        if (bmi < 18.5) {
            tvPhanLoai.setText(getString(R.string.bmi_under));
            tvPhanLoai.setTextColor(ContextCompat.getColor(this, R.color.bmi_warning_color));
        } else if (bmi < 23.0) {
            tvPhanLoai.setText(getString(R.string.bmi_normal));
            tvPhanLoai.setTextColor(ContextCompat.getColor(this, R.color.bmi_normal_color));
        } else if (bmi < 25.0) {
            tvPhanLoai.setText(getString(R.string.bmi_over));
            tvPhanLoai.setTextColor(ContextCompat.getColor(this, R.color.bmi_warning_color));
        } else {
            tvPhanLoai.setText(getString(R.string.bmi_obese));
            tvPhanLoai.setTextColor(ContextCompat.getColor(this, R.color.bmi_danger_color));
        }
    }
}