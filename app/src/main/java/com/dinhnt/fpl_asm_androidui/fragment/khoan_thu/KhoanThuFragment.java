package com.dinhnt.fpl_asm_androidui.fragment.khoan_thu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinhnt.fpl_asm_androidui.R;
import com.dinhnt.fpl_asm_androidui.adapter.ViewPagerKhoanThuAdaper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class KhoanThuFragment extends Fragment {

    ViewPager2 viewPagerKhoanThu;
    TabLayout tabLayoutKhoanThu;
    ViewPagerKhoanThuAdaper adaper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);

        viewPagerKhoanThu = view.findViewById(R.id.viewPagerKhoanThu);
        tabLayoutKhoanThu = view.findViewById(R.id.tabLayoutKhoanThu);

        adaper = new ViewPagerKhoanThuAdaper(getActivity());

        viewPagerKhoanThu.setAdapter(adaper);

        new TabLayoutMediator(tabLayoutKhoanThu, viewPagerKhoanThu, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Khoản thu");
                        break;
                    case 1:
                        tab.setText("Loại thu");
                        break;
                }
            }
        }).attach();

        return view;
    }
}
