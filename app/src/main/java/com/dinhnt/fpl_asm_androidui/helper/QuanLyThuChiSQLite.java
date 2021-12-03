package com.dinhnt.fpl_asm_androidui.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dinhnt.fpl_asm_androidui.models.LoaiThuChi;

import java.util.ArrayList;

public class QuanLyThuChiSQLite extends SQLiteOpenHelper {

    SQLiteDatabase db = this.getReadableDatabase();

    private static final String DATABASE_NAME = "quanlythuchi";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PHANLOAI = "phanloai";
    private static final String KEY_MALOAI = "maloai";
    private static final String KEY_TENLOAI = "tenloai";
    private static final String KEY_TRANGTHAI = "trangthai";

    private static final String TABLE_GIAODICH = "giaodich";
    private static final String KEY_MAGD = "magd";
    private static final String KEY_TIEUDE = "tieude";
    private static final String KEY_NGAY = "ngay";
    private static final String KEY_TIEN = "tien";
    private static final String KEY_MOTA = "mota";

    public QuanLyThuChiSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPhanLoai = "create table " + TABLE_PHANLOAI +
                "(" +
                KEY_MALOAI + " integer primary key autoincrement, " +
                KEY_TENLOAI + " text, " +
                KEY_TRANGTHAI + " text" +
                ")";
        db.execSQL(sqlPhanLoai);

        //tương tự cho table giao dịch
        String sqlGiaoDich = "create table " + TABLE_GIAODICH +
                "(" +
                KEY_MAGD + " integer primary key autoincrement, " +
                KEY_TIEUDE + " text, " +
                KEY_NGAY + " text," +
                KEY_TIEN + " int," +
                KEY_MOTA + " text," +
                KEY_MALOAI + " text" +
                ")";
        db.execSQL(sqlGiaoDich);

        //thêm data mẫu cho thống kê
        String phanLoai = "INSERT INTO " + TABLE_PHANLOAI + " ("
                + KEY_MALOAI + ", " + KEY_TENLOAI + ", "
                + KEY_TRANGTHAI + ") Values ('1', 'lương', 'thu'),('2', 'thưởng tết', 'thu'), " +
                "('3', 'ăn uống', 'chi'), ('4', 'mua sắm', 'chi')";
        db.execSQL(phanLoai);

        String giaoDich = "INSERT INTO " + TABLE_GIAODICH + " ("
                + KEY_TIEUDE + ", " + KEY_NGAY + ", "
                + KEY_TIEN + ", " + KEY_MOTA + ", "
                + KEY_MALOAI + ") Values ('1', '03/12/2021', '10000', 'ăn trưa', '3'), " +
                "('2', '03/12/2021', '15000', 'mua quần áo', '4'), " +
                "('3', '03/12/2021', '200000', 'lương t12', '1'), " +
                "('4', '03/12/2021', '20000', 'thưởng tết 2022', '2')";
        db.execSQL(giaoDich);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PHANLOAI);
        onCreate(db);

        //tương tự cho table giao dịch
    }

    //lấy danh sách loại khoản thu
    public ArrayList<LoaiThuChi> getAllLoaiKhoanThu() {
        Cursor cursor = db.rawQuery("select * from " + TABLE_PHANLOAI
                + " where " + KEY_TRANGTHAI + " = 'thu'", null);

        ArrayList<LoaiThuChi> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiThuChi(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;

    }

    //lấy thông tin loại khoản thu
    public LoaiThuChi getThongTinLoaiKhoanThu(int maLoai) {
        Cursor cursor = db.rawQuery("select * from " + TABLE_PHANLOAI
                + " where " + KEY_MALOAI + " = " + maLoai, null);
        return new LoaiThuChi(cursor.getInt(0), cursor.getString(1));

    }

    //Tạo loại khoản thu mới
    public void taoLoaiKhoanThu(String tenLoai) {
        ContentValues values = new ContentValues();
        values.put(KEY_TENLOAI, tenLoai);
        values.put(KEY_TRANGTHAI, "thu");

        db.insert(TABLE_PHANLOAI, null, values);
    }

    //Cập nhật loại khoản thu
    public void capNhatLoaiKhoanThu(int maLoai, String tenLoai) {
        ContentValues values = new ContentValues();
        values.put(KEY_TENLOAI, tenLoai);
        values.put(KEY_TRANGTHAI, "thu");

        db.update(TABLE_PHANLOAI, values, "maloai=?", new String[]{String.valueOf(maLoai)});
    }

    //Xóa loại khoản thu
    public void xoaLoaiKhoanThu(int maLoai) {
        db.delete(TABLE_PHANLOAI, "maloai=?", new String[]{String.valueOf(maLoai)});
    }

    //lấy thông tin tổng khoản thu/khoản chi (thông kê
    public float[] getThongTinThuChi() {
        int thu = 0, chi = 0;

        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = db.rawQuery("select sum(" + KEY_TIEN + ") from " + TABLE_GIAODICH
                + " where " + KEY_MALOAI + " in (select " + KEY_MALOAI + " from " + TABLE_PHANLOAI
                                    + " where " + KEY_TRANGTHAI + " = 'thu') ", null);

        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }

        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = db.rawQuery("select sum(" + KEY_TIEN + ") from " + TABLE_GIAODICH
                + " where " + KEY_MALOAI + " in (select " + KEY_MALOAI + " from " + TABLE_PHANLOAI
                                        + " where " + KEY_TRANGTHAI + " = 'chi') ", null);

        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }

        float[] ketQua = new float[]{thu, chi};
        return ketQua;

    }

}
