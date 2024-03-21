package com.pab.kalkulator;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtAngkaPertama = findViewById(R.id.edt1);
        final EditText edtAngkaKedua = findViewById(R.id.edt2);
        final TextView txtHasil = findViewById(R.id.hasilhitung);
        final TextView txtHistory = findViewById(R.id.historyText);

        Button btnTambah = findViewById(R.id.btnTambah);
        Button btnKurang = findViewById(R.id.btnKurang);
        Button btnKali = findViewById(R.id.btnKali);
        Button btnBagi = findViewById(R.id.btnBagi);
        Button btnExit = findViewById(R.id.btnExit);
        Button btnClear = findViewById(R.id.btnClear);

        historyList = new ArrayList<>();

        // Memuat animasi scale
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.button_scale);

        // Mengaitkan animasi dengan setiap tombol
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitung('+', edtAngkaPertama, edtAngkaKedua, txtHasil);
                view.startAnimation(animScale); // Memulai animasi saat tombol ditekan
            }
        });

        btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitung('-', edtAngkaPertama, edtAngkaKedua, txtHasil);
                view.startAnimation(animScale);
            }
        });

        btnKali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitung('*', edtAngkaPertama, edtAngkaKedua, txtHasil);
                view.startAnimation(animScale);
            }
        });

        btnBagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitung('/', edtAngkaPertama, edtAngkaKedua, txtHasil);
                view.startAnimation(animScale);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Mengakhiri aktivitas aplikasi (keluar dari aplikasi)
                view.startAnimation(animScale);
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtAngkaPertama.setText("");
                edtAngkaKedua.setText("");
                txtHasil.setText("");
                // Menghapus riwayat perhitungan
                historyList.clear();
                updateHistory();
                view.startAnimation(animScale);
            }
        });
    }

    private void hitung(char operator, EditText edtAngkaPertama, EditText edtAngkaKedua, TextView txtHasil) {
        int angka1 = Integer.parseInt(edtAngkaPertama.getText().toString());
        int angka2 = Integer.parseInt(edtAngkaKedua.getText().toString());

        int hasil;

        switch (operator) {
            case '+':
                hasil = angka1 + angka2;
                break;
            case '-':
                hasil = angka1 - angka2;
                break;
            case '*':
                hasil = angka1 * angka2;
                break;
            case '/':
                if (angka2 == 0) {
                    txtHasil.setText("Tidak bisa dibagi dengan 0");
                    return;
                }
                hasil = angka1 / angka2;
                break;
            default:
                hasil = 0;
                break;
        }

        String hasilString = String.valueOf(hasil);
        txtHasil.setText(hasilString);

        // Tambahkan hasil akhir ke dalam history
        String operasi = "Hasil perhitungan";
        String historyString = operasi + ": " + angka1 + " " + operator + " " + angka2 + " = " + hasil;
        historyList.add(historyString);
        updateHistory();

        // Menampilkan hasil akhir di Toast
        Toast.makeText(MainActivity.this, "Hasil: " + hasil, Toast.LENGTH_SHORT).show();
    }

    private void updateHistory() {
        StringBuilder history = new StringBuilder();
        int total = 0; // Menyimpan total hasil keseluruhan

        for (String operation : historyList) {
            history.append("<font color='#FF6347'>").append(operation).append("</font>").append("<br>");

            // Memperoleh hasil perhitungan dari string operation
            String[] parts = operation.split(" ");
            int hasil = Integer.parseInt(parts[4]);

            // Menambahkan hasil perhitungan ke total
            total += hasil;
        }

        // Menambahkan total ke history
        history.append("<br><b>Total:</b> ").append(total);

        TextView txtHistory = findViewById(R.id.historyText);
        txtHistory.setText(Html.fromHtml(history.toString()));
    }

}
