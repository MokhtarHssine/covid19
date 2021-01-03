package com.projet.dsi32g5_covid19.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.projet.dsi32g5_covid19.R;

import java.util.ArrayList;
import java.util.List;

public class CenterFragment extends Fragment {
    String[] titles;
    String[] locals;
    List<RowItem> rowItems;
    ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_center, container, false);

        rowItems = new ArrayList<RowItem>();
        titles = getResources().getStringArray(R.array.Titles);
        locals = getResources().getStringArray(R.array.locals);
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(titles[i], locals[i]);
            rowItems.add(item);
        }
        listView = view.findViewById(R.id.item);
        CustomAdapter adapter = new CustomAdapter(this.getActivity(), rowItems);
        listView.setAdapter(adapter);
        return view;
    }

}
