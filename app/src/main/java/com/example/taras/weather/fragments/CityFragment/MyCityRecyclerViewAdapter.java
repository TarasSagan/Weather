package com.example.taras.weather.fragments.CityFragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.taras.weather.MainActivity;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.fragments.CityFragment.CityFragment.OnListFragmentInteractionListener;
import com.example.taras.weather.R;
import com.example.taras.weather.settings.values.UnitsFormat;
import com.example.taras.weather.settings.values.models.Unit;

import java.util.List;
import java.util.Map;

public class MyCityRecyclerViewAdapter extends RecyclerView.Adapter<MyCityRecyclerViewAdapter.ViewHolder> {
    private String UNIT;
    private final List<Repo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCityRecyclerViewAdapter(List<Repo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city, parent, false);
        UNIT = getCurrentUnit();
        return new ViewHolder(view);
    }
    private String getCurrentUnit(){
        Map<String, String> curentSettings = MainActivity.getSettingsController().getSettings();
        UnitsFormat unitsFormat = new UnitsFormat();
        List<Unit> list = unitsFormat.getListUnits();
        for(Unit unit : list){
            if(TextUtils.equals(curentSettings.get("units"), unit.getValue())){
                return unit.getDescription();
            }
        }return "";
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.CFCityName.setText(mValues.get(position).getCityName());
        holder.CFDescription.setText(mValues.get(position).getDescription());
        holder.CFTemperature.setText(Double.toString(mValues.get(position).getTemperature()) + UNIT);

        holder.CFDelete.setOnClickListener(v -> mListener.onDeleteCallbackCityFragment(holder.mItem));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClickCallbackCityFragment(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView CFCityName;
        public final TextView CFDescription;
        public final TextView CFTemperature;
        public final Button CFDelete;
        public Repo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            CFDelete = (Button) view.findViewById(R.id.buttonDelete);
            CFCityName = (TextView) view.findViewById(R.id.CFCityName);
            CFDescription = (TextView) view.findViewById(R.id.CFDescription);
            CFTemperature = (TextView) view.findViewById(R.id.CFTemperature);
        }

    }
}
