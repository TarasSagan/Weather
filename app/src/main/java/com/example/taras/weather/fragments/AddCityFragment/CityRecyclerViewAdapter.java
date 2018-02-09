package com.example.taras.weather.fragments.AddCityFragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.taras.weather.MainActivity;
import com.example.taras.weather.R;
import com.example.taras.weather.settings.values.UnitsFormat;
import com.example.taras.weather.settings.values.models.Unit;

import java.util.List;
import java.util.Map;


public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {
    private String UNIT;
    private  List<ModelCitySearch> mValues;
    private final AddCityFragment.AddCityListListener mListener;

    public CityRecyclerViewAdapter(List<ModelCitySearch>  items, AddCityFragment.AddCityListListener listener) {
        mValues = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_add_city_item, parent, false);
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
        holder.tvAddCityName.setText(mValues.get(position).getCityName());
        holder.tvAddCityCountry.setText(mValues.get(position).getCityCountry());
        holder.tvAddCityTemp.setText(Double.toString(mValues.get(position).getTemp()) + UNIT);
        holder.tvAddCityDescription.setText(mValues.get(position).getDescription());

        holder.tvAddCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.addCityCallback(holder.mItem);
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
        public final TextView tvAddCityTemp;
        public final TextView tvAddCityName;
        public final TextView tvAddCityCountry;
        public final TextView tvAddCityDescription;
        public final Button tvAddCityButton;
        public ModelCitySearch mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvAddCityTemp = (TextView) view.findViewById(R.id.tvAddCityTemp);
            tvAddCityName = (TextView) view.findViewById(R.id.tvAddCityName);
            tvAddCityCountry = (TextView) view.findViewById(R.id.tvAddCityCountry);
            tvAddCityDescription = (TextView) view.findViewById(R.id.tvAddCityDescription);
            tvAddCityButton = (Button) view.findViewById(R.id.tvAddCityButton);
        }
    }
}
