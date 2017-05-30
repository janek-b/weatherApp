package com.example.guest.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherapp.Constants;
import com.example.guest.weatherapp.R;
import com.example.guest.weatherapp.models.Forecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private ArrayList<Forecast> mForecast = new ArrayList<>();
    private Context mContext;

    public ForecastAdapter(Context context, ArrayList<Forecast> forecasts) {
        mContext = context;
        mForecast = forecasts;
    }

    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ForecastViewHolder holder, int position) {
        holder.bindForecast(mForecast.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecast.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.forecastDate) TextView mForecastDate;
        @Bind(R.id.forecastCondition) TextView mForecastCondition;
        @Bind(R.id.forecastIcon) ImageView mForecastIcon;
        @Bind(R.id.forecastMax) TextView mForecastMax;
        @Bind(R.id.forecastMin) TextView mForecastMin;

        private Context mContext;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindForecast(Forecast weather) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(weather.getDate()*1000);
            mForecastDate.setText(date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
            mForecastCondition.setText(String.format("%s - %s",weather.getCondition().get("main"), weather.getCondition().get("description")));
            mForecastMax.setText(String.format("%.1f%s", weather.getMaxTemp(), (char) 0x00B0));
            mForecastMin.setText(String.format("%.1f%s", weather.getMinTemp(), (char) 0x00B0));
            String iconUrl = String.format("%s%s.png", Constants.ICON_BASE_URL, weather.getCondition().get("icon"));
            Picasso.with(mContext).load(iconUrl).into(mForecastIcon);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            Toast.makeText(mContext, mForecast.get(pos).getCondition().get("main"), Toast.LENGTH_SHORT).show();
        }
    }
}
