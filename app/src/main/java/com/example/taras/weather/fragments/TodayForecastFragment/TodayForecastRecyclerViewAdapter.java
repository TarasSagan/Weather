package com.example.taras.weather.fragments.TodayForecastFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taras.weather.CalendarMethods;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.R;

import com.example.taras.weather.fragments.TodayForecastFragment.TodayForecastFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TodayForecastRecyclerViewAdapter extends RecyclerView.Adapter<TodayForecastRecyclerViewAdapter.ViewHolder> {

    private final List<Repo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TodayForecastRecyclerViewAdapter(List<Repo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_today_forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tFCityname.setText(mValues.get(position).getCityName());
        holder.tFDescription.setText(mValues.get(position).getDescription());
        holder.tFTemperature.setText(Double.toString(mValues.get(position).getTemperature()));
        holder.tFHumidity.setText(Long.toString(mValues.get(position).getHumidity()) + " %");
        holder.tFPressure.setText(Double.toString(mValues.get(position).getPressure()) + " hPa");
        holder.tFWindSpeed.setText(Double.toString(mValues.get(position).getWindSpeed()) + " " + " meter/sec");
        holder.tFClouds.setText(Long.toString(mValues.get(position).getClouds()) + "%" );
        holder.tFTimeForecast.setText("Forecast valid to \n" + CalendarMethods.getTime2400(mValues.get(position).getForecastDate()));
        holder.tFLastUpdate.setText("Last update: " +
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
        public final TextView tFCityname, tFDescription, tFTemperature, tFTimeForecast, tFHumidity,
        tFPressure, tFWindSpeed, tFClouds, tFLastUpdate;
        public Repo mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tFCityname = (TextView) view.findViewById(R.id.TFCityname);
            tFDescription = (TextView) view.findViewById(R.id.TFDescription);
            tFTemperature = (TextView) view.findViewById(R.id.TFTemperature);
            tFHumidity = (TextView) view.findViewById(R.id.TFHumidity);
            tFPressure = (TextView) view.findViewById(R.id.TFPressure);
            tFWindSpeed = (TextView) view.findViewById(R.id.TFWindSpeed);
            tFClouds = (TextView) view.findViewById(R.id.TFClouds);
            tFTimeForecast = (TextView) view.findViewById(R.id.TFTimeForecast);
            tFLastUpdate = (TextView) view.findViewById(R.id.TFLastUpdate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" +  "'";
        }
    }
}
