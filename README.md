# INT4211 - LẬP TRÌNH TRÊN CÁC THIẾT BỊ DI ĐỘNG
## BÁO CÁO THỰC HÀNH LAB A4: TEXTVIEW, BUTTON, EDITTEXT & XỬ LÝ SỰ KIỆN

---

### THÔNG TIN SINH VIÊN
* **Họ và tên:** Đỗ Lê Tuấn Đạt
* **Mã số sinh viên (MSSV):** 231A290089
* **Lớp học phần:** Lập trình thiết bị di động (INT4211)
* **Môi trường thực thi:** Android Studio trên macOS (Apple Silicon)
* **Thiết bị thử nghiệm:** Máy ảo Pixel 8 (Android 17, API 37.1 – aarch64)
* **Chế độ Repository:** Private (Đã cấp quyền Collaborator cho giảng viên)

---

### CÁC MỤC ĐÃ HOÀN THÀNH

#### 1. Máy tính bốn phép toán & Đa dạng hóa bộ lắng nghe sự kiện (Phần 1, 2, 3)
* **Quy trình xử lý sự kiện 3 bước chuẩn:**
  1. *Ánh xạ View an toàn:* Toàn bộ `findViewById()` được gọi sau `setContentView()`, đặt tên biến trùng khớp 100% với ID trong XML để tránh lỗi `NullPointerException`.
  2. *Đa dạng hóa bộ lắng nghe sự kiện (Listener):*
     * **Cách 1 (Biểu thức Lambda):** Áp dụng cho nút Cộng (`btnCong`) và Trừ (`btnTru`) để xử lý logic độc lập, cú pháp tường minh và ngắn gọn.
     * **Cách 2 (Listener dùng chung):** Tạo một đối tượng `View.OnClickListener chung` duy nhất cho nút Nhân (`btnNhan`) và Chia (`btnChia`), phân nhánh xử lý bằng `v.getId()` kết hợp cấu trúc `if/else` (chuẩn hóa theo AGP 8+).
  3. *Xử lý logic toán học:* Hỗ trợ số thực dấu phẩy động (`double`), hiển thị kết quả làm tròn 2 chữ số thập phân bằng `String.format(Locale.getDefault(), "%.2f %c %.2f = %.2f", ...)` để đồng bộ dấu ngăn cách số thực theo từng ngôn ngữ hệ thống.
  4. *Nút Xóa trắng:* Xóa sạch nội dung 2 ô nhập, xóa cảnh báo lỗi (`setError(null)`), đưa kết quả về trạng thái mặc định và chủ động focus con trỏ về ô A (`requestFocus()`).

#### 2. Quy trình kiểm tra dữ liệu 4 lớp & Bẫy lỗi an toàn (Phần 3.1)
Ứng dụng kiểm soát chặt chẽ dữ liệu đầu vào theo 4 tầng bảo vệ, đảm bảo **tuyệt đối không crash** trong mọi tình huống nhập liệu:
* **Lớp 1 – Kiểm tra rỗng:** Sử dụng `chuoi.isEmpty()`. Nếu rỗng, hiển thị cảnh báo tại chỗ bằng `setError(getString(R.string.err_empty))` và chuyển con trỏ về ô đó qua `requestFocus()`.
* **Lớp 2 – Kiểm tra định dạng:** Bọc quá trình chuyển chuỗi sang số trong khối `try { Double.parseDouble(...) } catch (NumberFormatException e)`. Nếu người dùng dán chuỗi chữ hoặc nhập số sai cú pháp (như `1.5.5`), bắt ngoại lệ và thông báo bằng `Toast`.
* **Lớp 3 – Kiểm tra miền giá trị:** Với phép chia, kiểm tra điều kiện mẫu số `b == 0` để chặn lỗi chia cho 0, hiển thị `setError` ngay tại ô B kết hợp `Toast` cảnh báo. Với bài toán BMI, kiểm tra cân nặng và chiều cao phải $> 0$.
* **Lớp 4 – Nghiệp vụ bài toán:** Trong tính BMI, kiểm tra nếu `chieuCao > 3` thì tự động hiểu là đơn vị centimet và chia cho 100 để đổi sang mét trước khi tính toán.

#### 3. Chức năng tính chỉ số BMI theo khuyến nghị WHO Châu Á (Phần 4)
* **Công thức chuẩn:** $\text{BMI} = \frac{\text{Cân nặng (kg)}}{(\text{Chiều cao (m)})^2}$.
* **Hỗ trợ thông minh đơn vị:** Cho phép nhập cả dạng mét (ví dụ `1.70`) và dạng centimet (ví dụ `170`), hệ thống tự nhận diện và quy đổi mượt mà.
* **Tách rời phương thức logic:** Hàm `phanLoai(double bmi)` được viết độc lập không đụng chạm đến giao diện (phục vụ Unit Test trong tương lai), phân loại kết quả theo đúng chuẩn khuyến nghị của WHO dành riêng cho khu vực Châu Á:
  * BMI $< 18.5$: Thiếu cân.
  * $18.5 \le \text{BMI} < 23.0$: Bình thường.
  * $23.0 \le \text{BMI} < 25.0$: Thừa cân.
  * $\text{BMI} \ge 25.0$: Béo phì.

#### 4. Bảng 10 Test Cases kiểm thử thực tế (Phần 5)
* Đã thực nghiệm đầy đủ 10 kịch bản bao gồm tính toán hợp lệ, chia cho 0, để trống dữ liệu, dán ký tự chữ, số thập phân âm, 2 dấu chấm liên tiếp, quy đổi centimet và chặn giá trị âm. Toàn bộ 10/10 trường hợp đều đạt kết quả mong đợi, bắt lỗi chính xác và giữ ứng dụng hoạt động ổn định.

#### 5. Bài nâng cao NC1: Nút tính phần trăm (%) và đảo dấu (±)
* Bổ sung nút **`%`**: Đọc giá trị ô A; nếu ô B rỗng thì tính $A / 100$, nếu ô B có giá trị thì tính tỷ lệ phần trăm $(A \times B) / 100$.
* Bổ sung nút **`±`**: Đọc số hiện có ở ô A, nhân với $-1$, cập nhật ngược lại vào ô nhập và tự động đưa con trỏ về cuối văn bản bằng `setSelection()`.

#### 6. Bài nâng cao NC3: Đổi màu dòng phân loại BMI theo mức cảnh báo y tế
* Sử dụng bảng màu Material:
  * Màu xanh lá (`#2E7D32`): Trạng thái Bình thường.
  * Màu vàng cam (`#F57C00`): Trạng thái Cảnh báo (Thiếu cân / Thừa cân).
  * Màu đỏ (`#D32F2F`): Trạng thái Nguy hiểm (Béo phì).
* Tự động cập nhật màu chữ của `tvPhanLoai` thông qua phương thức `setTextColor(ContextCompat.getColor(this, ...))` tương ứng với từng kết quả phân loại.

---

### CẤU TRÚC THƯ MỤC NGUỒN CHÍNH
```text
app/src/main/
├── AndroidManifest.xml
├── java/vn/edu/vhu/ltdd/a4events/
│   └── MainActivity.java           # Xử lý sự kiện máy tính, 4 lớp kiểm tra, BMI, NC1 & NC3
└── res/
    ├── layout/
    │   └── activity_main.xml       # Giao diện ScrollView, Máy tính 4 phép toán + %/± và BMI
    └── values/
        ├── colors.xml              # Định nghĩa màu cảnh báo BMI (NC3)
        └── strings.xml             # Khai báo toàn bộ chuỗi đa ngôn ngữ và thông báo lỗi
