package br.ufrpe.bsi.mpoo.petspeed.cliente.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufrpe.bsi.mpoo.petspeed.R;
import br.ufrpe.bsi.mpoo.petspeed.infra.gui.TabsAdapter;

public class FiltroMedicosTabViewFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_medicos_tab_view, container, false);

        TabsAdapter adapter = setTabsAdapter();
        ViewPager viewPager = setViewPager(view, adapter);
        setTabLayout(view, viewPager);

        return view;
    }

    private ViewPager setViewPager(View view, TabsAdapter adapter) {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        return viewPager;
    }

    private TabsAdapter setTabsAdapter() {
        TabsAdapter adapter = new TabsAdapter(getChildFragmentManager());
        adapter.add(new FiltroByCidadeBuscaMedicoFragment(), "CIDADE");
        adapter.add(new FiltroByEstadoBuscaMedicoFragment(), "ESTADO");
        adapter.add(new FiltroBrasilBuscaMedicoFragment(), "BRASIL");
        return adapter;
    }

    private void setTabLayout(View view, ViewPager viewPager) {
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
