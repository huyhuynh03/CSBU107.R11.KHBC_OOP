# Giải thích cách làm bài tập Lab 2 (OOP - Java)

Mỗi bài nằm trong một file riêng trong thư mục `src/`. Mỗi file có comment đầu mô tả object/class, thuộc tính, constructor và phương thức.

## Exercise 1: Rectangle
- **Thuộc tính:** `width`, `height` kiểu `double`, khai báo `private`.
- **Constructor:** không tham số gọi `this(1, 1)`; có tham số thay giá trị không dương bằng 1.
- **Phương thức:** `area()` trả về diện tích, `perimeter()` trả về chu vi, `displayInfo()` in thông tin.
- **main:** tạo 3 hình chữ nhật, trong đó có một cạnh âm để kiểm tra việc thay bằng 1.

## Exercise 2: Fraction
- **Thuộc tính:** `numerator`, `denominator` kiểu `int`, `private`. Constructor ném lỗi nếu mẫu bằng 0, và chuyển dấu âm lên tử số.
- **Constructor:** mặc định (0/1), tham số, và copy constructor `Fraction(Fraction other)`.
- **Phương thức:** `simplify()` chia tử và mẫu cho ước chung lớn nhất (thuật toán Euclid). `add`, `subtract`, `multiply`, `divide` tạo và trả về đối tượng `Fraction` mới, đã rút gọn.
- **main:** kiểm tra copy constructor bằng `==` (khác đối tượng) và so sánh giá trị (giống nhau).

## Exercise 3: Time
- **Thuộc tính:** `hour`, `minute`, `second`. Constructor kiểm tra hợp lệ: giờ trong [0, 23], phút và giây trong [0, 59].
- **Cách xử lý vượt ngày:** đổi thời gian thành tổng số giây kể từ 00:00:00, cộng hoặc trừ, rồi lấy `Math.floorMod(total, 86400)` để kết quả luôn nằm trong một ngày và đúng cả khi số âm.
- **main:** demo 23:59:50 + 20s = 00:00:10 và 00:00:10 − 20s = 23:59:50.

## Exercise 4: Circle
- **Thuộc tính:** `centerX`, `centerY`, `radius`. Constructor mặc định tạo hình tròn đơn vị tại (0, 0).
- **Phương thức:** `area()` = π·r², `perimeter()` = 2π·r.
- **`contains(x, y)`:** so sánh bình phương khoảng cách từ điểm đến tâm với r². Có thêm sai số nhỏ để điểm nằm đúng trên biên được tính là bên trong.

## Exercise 5: Dice
- **Thuộc tính:** `value`, khởi tạo bằng 1.
- **`roll()`:** sinh số ngẫu nhiên 1–6 bằng `Random.nextInt(6) + 1`, cập nhật `value` rồi trả về.
- **main:** tung 6000 lần, đếm số lần mỗi mặt bằng mảng `frequency`, rồi tính xác suất thực nghiệm = số lần / tổng số lần. Lỗi đếm ban đầu (bỏ sót 5 lần tung đầu) đã được sửa.

## Exercise 6: Calculator
- **Nạp chồng:** `add(int, int)`, `add(double, double)`, `add(int, int, int)`, `max(int, int)`, `max(double, double)`.
- **Cách Java chọn phương thức:** tìm phiên bản khớp kiểu và số tham số. Nếu không có kiểu khớp chính xác, Java chuyển `int` thành `double` (widening). Ví dụ `add(2, 3.5)` gọi bản `double`.

## Exercise 7: UITStudent
- **Thuộc tính:** `studentId`, `fullName`, `major`, `gpa`, đều `private`. GPA được kiểm tra trong [0.0, 4.0].
- **Constructor chaining:** constructor không tham số gọi `this("N/A", "Unknown")`, constructor hai tham số gọi `this(id, name, "Undeclared", 0.0)`.
- **Phương thức:**
  - `isPassed()`: đạt khi GPA ≥ 2.0.
  - `academicClassification()`: ≥ 3.6 là Excellent, ≥ 3.2 là Very Good, ≥ 2.5 là Good, ≥ 2.0 là Average, còn lại là Weak.
  - `displayInfo()`: in đầy đủ thông tin.
- **main:** tạo 5 sinh viên bằng 3 constructor khác nhau.

## Cách biên dịch và chạy
```
javac -encoding UTF-8 -d out src/*.java
java -cp out Exercise1
```
Thay `Exercise1` bằng tên bài muốn chạy.
