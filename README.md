
# 💧 WaterCheckBandung

**WaterCheckBandung** adalah aplikasi Android berbasis **Kotlin** yang digunakan untuk memantau kondisi air secara real-time di wilayah Bandung. Aplikasi ini dibangun menggunakan arsitektur **MVVM (Model-View-ViewModel)** agar struktur kode tetap terorganisir dan mudah di-maintain.

---

## 📱 Fitur Utama

- Menampilkan status kualitas air di Kota Bandung
- Menampilkan data dari tahun 2015-2019
- Menyimpan dan menampilkan histori Status air untuk Setiap User 
- Tampilan antarmuka yang bersih dan ramah pengguna
- Loading animasi untuk peningkatan UX
- Arsitektur MVVM modern dengan LiveData dan ViewModel

---

## 🛠️ Teknologi & Tools

- **Kotlin**  
- **MVVM Architecture**  
- **Retrofit** – komunikasi REST API  
- **Coroutines** – asynchronous processing  
- **LiveData & ViewModel** – untuk data yang sadar lifecycle  
- **Room** – database lokal (jika digunakan)  
- **RecyclerView** – untuk tampilan histori data  
- **Glide/Coil** – pemrosesan gambar  
- **Firebase** (jika tersedia dari file `google-services.json`)  
- **Material Design** – antarmuka modern

---

## 🏗️ Struktur Proyek (MVVM)

```
com.watercheckbdg/
│
├── adapter/              # Adapter RecyclerView
├── data/
│   ├── model/            # Model data (e.g., SensorData, HistoryEntry)
│   └── network/          # Retrofit API Service
├── di/                   # Dependency Injection (Hilt)
├── repository/           # Pengambil data dari network/local
├── view/                 # Activity / Fragment (View)
├── viewmodel/            # ViewModel
├── utils/                # Helper classes
└── WaterCheckApp.kt      # Aplikasi utama (extends Application)
```

---

## 🧪 Cara Menjalankan Proyek

1. **Clone repositori**  
   ```bash
   git clone https://github.com/hilmy112233/AppAndroidWaterCheckBandung.git
   cd AppAndroidWaterCheckBandung
   ```

2. **Buka di Android Studio**  
   Pastikan Android Studio Anda mendukung Kotlin, AndroidX, dan Gradle Kotlin DSL (`.kts`).

3. **Tambahkan konfigurasi API/Firebase jika diperlukan**  
   Misalnya di file `local.properties` atau gunakan Firebase Console:
   ```properties
   API_KEY=your_api_key_here
   ```

4. **Build dan Jalankan Aplikasi**  
   Gunakan emulator atau perangkat Android untuk menjalankan aplikasi.

---

## 🤝 Kontribusi

Kontribusi sangat terbuka! Ikuti langkah-langkah berikut:

1. **Fork repositori**

2. **Buat branch fitur baru**  
   ```bash
   git checkout -b fitur-baru
   ```

3. **Commit perubahan**  
   ```bash
   git commit -am "Menambahkan fitur baru"
   ```

4. **Push ke branch**  
   ```bash
   git push origin fitur-baru
   ```

5. **Buat pull request**

---

## 📄 Lisensi

Proyek ini dilisensikan di bawah [MIT License](LICENSE).

---

## ✨ Pengembang

- **Hilmy Abdurrahman Darmawan** – [GitHub](https://github.com/hilmy112233)

---

## 📥 Cara Menginstal APK (via Itch.io)

Jika Anda tidak ingin membangun proyek secara manual, Anda dapat langsung mencoba aplikasi dengan mengunduh APK melalui halaman Itch.io berikut:

🔗 **[Download APK WaterCheckBandung via Itch.io](https://hilmy270301.itch.io/watercheckbandung)** 

### 📲 Langkah-langkah Instalasi:

1. Buka link Itch.io di atas melalui browser HP atau PC.
2. Klik tombol **Download** dan unduh file `WaterCheckBandung.apk`.
3. Jika mengunduh lewat PC, pindahkan file ke perangkat Android Anda.
4. Di perangkat Android:
   - Buka file `WaterCheckBandung.apk`.
   - Izinkan instalasi dari sumber tidak dikenal jika diminta.
   - Ikuti proses instalasi hingga selesai.
5. Jalankan aplikasi Water Check Bandung dan nikmati!
