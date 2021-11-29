package com.dinhnt.fpl_asm_androidui.fragment.khoan_thu;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.dinhnt.fpl_asm_androidui.R;
import com.dinhnt.fpl_asm_androidui.adapter.LoaiKhoanThuAdapter;
import com.dinhnt.fpl_asm_androidui.helper.QuanLyThuChiSQLite;
import com.dinhnt.fpl_asm_androidui.models.LoaiThuChi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class LoaiKhoanThuTabFragment extends Fragment {

    ListView listLoaiKhoanThu;
    FloatingActionButton floatThemLoaiKhoanThu;
    QuanLyThuChiSQLite quanLyThuChi;
    LoaiKhoanThuAdapter adapter;
    ArrayList<LoaiThuChi> alLoaiThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_khoan_thu_tab, container, false);

        listLoaiKhoanThu = view.findViewById(R.id.listLoaiKhoanThu);
        floatThemLoaiKhoanThu = view.findViewById(R.id.floatThemLoaiKhoanThu);

        quanLyThuChi = new QuanLyThuChiSQLite(getActivity());
        alLoaiThu = new ArrayList<>();

        getDataLoaiThu();

        floatThemLoaiKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogThemLoaiKhoanThu();
            }
        });

        return view;
    }

    private void createDialogThemLoaiKhoanThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_loai_khoan_thu, null);

        EditText edtTenLoaiKhoanThu = v.findViewById(R.id.edtTenLoaiKhoanThu);

        builder.setView(v);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                quanLyThuChi.taoLoaiKhoanThu(edtTenLoaiKhoanThu.getText().toString());
                getDataLoaiThu();
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

    private void getDataLoaiThu() {
        alLoaiThu.clear();
        alLoaiThu = quanLyThuChi.getAllLoaiKhoanThu();
        adapter = new LoaiKhoanThuAdapter(alLoaiThu, getActivity(), quanLyThuChi);
        listLoaiKhoanThu.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
