package com.dinhnt.fpl_asm_androidui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinhnt.fpl_asm_androidui.R;
import com.dinhnt.fpl_asm_androidui.helper.QuanLyThuChiSQLite;
import com.dinhnt.fpl_asm_androidui.models.LoaiThuChi;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class LoaiKhoanThuAdapter extends BaseAdapter {

    ArrayList<LoaiThuChi> alThuChi = new ArrayList<>();
    Context context;
    QuanLyThuChiSQLite quanLyThuChi;

    public LoaiKhoanThuAdapter(ArrayList<LoaiThuChi> alThuChi, Context context, QuanLyThuChiSQLite quanLyThuChi) {
        this.alThuChi = alThuChi;
        this.context = context;
        this.quanLyThuChi = quanLyThuChi;
    }

    @Override
    public int getCount() {
        return alThuChi.size();
    }

    @Override
    public Object getItem(int i) {
        return alThuChi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alThuChi.get(i).getMaLoai();
    }

    class ViewOfItem{
        TextView txtLoaiKhoanThu;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewOfItem viewOfItem;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();


        if(view == null){
            view = inflater.inflate(R.layout.item_loai_khoan_thu, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.txtLoaiKhoanThu = view.findViewById(R.id.txtLoaiKhoanThu);
            viewOfItem.imgEdit = view.findViewById(R.id.imgEdit);
            viewOfItem.imgDelete = view.findViewById(R.id.imgDelete);

            view.setTag(viewOfItem);
        }else{
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.txtLoaiKhoanThu.setText(alThuChi.get(i).getTenLoai());

        //cập nhật thông tin
        viewOfItem.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogCapNhatLoaiKhoanThu(alThuChi.get(i).getMaLoai(), alThuChi.get(i).getTenLoai());
            }
        });

        //xóa
        viewOfItem.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quanLyThuChi.xoaLoaiKhoanThu(alThuChi.get(i).getMaLoai());
                loadDanhSach();
            }
        });
        return view;
    }

    private void createDialogCapNhatLoaiKhoanThu(int maLoai, String tenLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_cap_nhat_loai_khoan_thu, null);

        EditText edtTenLoaiKhoanThu = v.findViewById(R.id.edtTenLoaiKhoanThu);

        edtTenLoaiKhoanThu.setText(tenLoai);

        builder.setView(v);

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                quanLyThuChi.capNhatLoaiKhoanThu(maLoai, edtTenLoaiKhoanThu.getText().toString());
                loadDanhSach();
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

    private void loadDanhSach(){
        alThuChi.clear();
        alThuChi = quanLyThuChi.getAllLoaiKhoanThu();
        notifyDataSetChanged();
    }
}
