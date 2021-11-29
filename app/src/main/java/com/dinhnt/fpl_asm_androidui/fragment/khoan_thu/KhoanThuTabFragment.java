package com.dinhnt.fpl_asm_androidui.fragment.khoan_thu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dinhnt.fpl_asm_androidui.R;
import com.dinhnt.fpl_asm_androidui.adapter.LoaiKhoanThuAdapter;
import com.dinhnt.fpl_asm_androidui.helper.QuanLyThuChiSQLite;
import com.dinhnt.fpl_asm_androidui.models.LoaiThuChi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class KhoanThuTabFragment extends Fragment {

    FloatingActionButton floatThemKhoanThu;
    ArrayList<LoaiThuChi> alLoaiThu;
    QuanLyThuChiSQLite quanLyThuChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoan_thu_tab, container, false);

        floatThemKhoanThu = view.findViewById(R.id.floatThemKhoanThu);

        alLoaiThu = new ArrayList<>();

        quanLyThuChi = new QuanLyThuChiSQLite(getActivity());

        floatThemKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogThemKhoanThu();
            }
        });

        return view;
    }

    private void createDialogThemKhoanThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_khoan_thu, null);

        //....
        Spinner spnLoaiKhoanThu = v.findViewById(R.id.spnLoaiKhoanThu);

        getDataLoaiThu(spnLoaiKhoanThu);

        builder.setView(v);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //...code

                HashMap<String, String>  hashMap = (HashMap<String, String>) spnLoaiKhoanThu.getSelectedItem();
                Toast.makeText(getActivity(), "Mã loại: " + hashMap.get("maLoai"), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataLoaiThu(Spinner sp) {
        alLoaiThu.clear();
        alLoaiThu = quanLyThuChi.getAllLoaiKhoanThu();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (LoaiThuChi loaiThuChi : alLoaiThu) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("maLoai", String.valueOf(loaiThuChi.getMaLoai()));
            hashMap.put("tenLoai", loaiThuChi.getTenLoai());
            list.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                list, R.layout.item_spinner_loai_khoan_thu,
                new String[]{"tenLoai"}, new int[]{R.id.txtLoaiKhoanThu});
        sp.setAdapter(adapter);

    }
}
