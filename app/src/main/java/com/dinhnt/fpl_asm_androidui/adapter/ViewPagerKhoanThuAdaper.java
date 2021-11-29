package com.dinhnt.fpl_asm_androidui.adapter;

import com.dinhnt.fpl_asm_androidui.fragment.khoan_thu.KhoanThuTabFragment;
import com.dinhnt.fpl_asm_androidui.fragment.khoan_thu.LoaiKhoanThuTabFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerKhoanThuAdaper extends FragmentStateAdapter {
    public ViewPagerKhoanThuAdaper(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new KhoanThuTabFragment();
            case 1:
                return new LoaiKhoanThuTabFragment();
            default:
                return new KhoanThuTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
