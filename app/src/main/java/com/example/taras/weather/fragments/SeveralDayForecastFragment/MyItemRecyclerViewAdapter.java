package com.example.taras.weather.fragments.SeveralDayForecastFragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taras.weather.CalendarMethods;
import com.example.taras.weather.MainActivity;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.fragments.SeveralDayForecastFragment.SeveralDayForecastFragment.OnListFragmentInteractionListener;
import com.example.taras.weather.R;
import com.example.taras.weather.settings.values.UnitsFormat;
import com.example.taras.weather.settings.values.models.Unit;

import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private String UNIT;
    private final List<Repo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Repo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.several_days_fragment_item, parent, false);
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
        holder.sDFCityname.setText(mValues.get(position).getCityName());
        holder.sDFDescription.setText(mValues.get(position).getDescription());
        holder.sDFTemperature.setText(Double.toString(mValues.get(position).getTemperature()) + UNIT);
        holder.sDFHumidity.setText(Long.toString(mValues.get(position).getHumidity()) + "%");
        holder.sDFPressure.setText(Double.toString(mValues.get(position).getPressure()) + " hPa");
        holder.sDFWindSpeed.setText(Double.toString(mValues.get(position).getWindSpeed()) + " " + " meter/sec");
        holder.sDFClouds.setText(Long.toString(mValues.get(position).getClouds()) + "%" );
        holder.sDFTimeForecast.setText(CalendarMethods.getDayOfWeek(mValues.get(position).getForecastDate()) + "\n"
                + CalendarMethods.getDate(mValues.get(position).getForecastDate()));
        holder.sDFLastUpdate.setText("Last update: " +
                CalendarMethods.getTime2400(mValues.get(position).getLastUpdate()) + "   "
                + CalendarMethods.getDate(mValues.get(position).getLastUpdate()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView sDFCityname, sDFDescription, sDFTemperature, sDFTimeForecast, sDFHumidity,
                sDFPressure, sDFWindSpeed, sDFClouds, sDFLastUpdate;
        public Repo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sDFCityname = (TextView) view.findViewById(R.id.SDFCityname);
            sDFDescription = (TextView) view.findViewById(R.id.SDFDescription);
            sDFTemperature = (TextView) view.findViewById(R.id.SDFTemperature);
            sDFHumidity = (TextView) view.findViewById(R.id.SDFHumidity);
            sDFPressure = (TextView) view.findViewById(R.id.SDFPressure);
            sDFWindSpeed = (TextView) view.findViewById(R.id.SDFWindSpeed);
            sDFClouds = (TextView) view.findViewById(R.id.SDFClouds);
            sDFTimeForecast = (TextView) view.findViewById(R.id.SDFTimeForecast);
            sDFLastUpdate = (TextView) view.findViewById(R.id.SDFLastUpdate);
        }

    }
}
