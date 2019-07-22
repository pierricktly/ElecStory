package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.text.NumberFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("LongLogTag")
public class RecyclerViewAdapterFactory extends RecyclerView.Adapter<RecyclerViewAdapterFactory.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<Factory> mFactory;
    private Context mContext;
    private Activity activity;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private SharedPreferences sharedPreferences;
    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    public RecyclerViewAdapterFactory(Context context, ArrayList<Factory> mFactorys, Activity activitys) {
        mFactory = mFactorys;
        mContext = context;
        activity = activitys;
        sharedPreferences = mContext.getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.factory_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Database db = new Database(mContext);

        if(mFactory.get(position).getFactoryLevel() > 0) {
            holder.test.setCardBackgroundColor(Color.WHITE);
        }

        holder.imageFactorys.setImageResource(mFactory.get(position).getSkin());

        holder.nameFactorys.setText(mFactory.get(position).getName());

        holder.lvlFactorys.setText(String.valueOf(mFactory.get(position).getFactoryLevel()));

        holder.buttonInfoFactorys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final UpgradeFactoryPopup upgradeFactorypopups = new UpgradeFactoryPopup(activity);
                upgradeFactorypopups.setNameObjectSale(mFactory.get(position).getName() + "'s Info");
                if(mFactory.get(position).getNbObject() < 1) {
                    upgradeFactorypopups.getUpgradeButton().setText("Buy");
                    upgradeFactorypopups.setMessageSale(
                            "Actual Level : " + mFactory.get(position).getFactoryLevel() +
                            "\nUpdate Cost : " + numberFormat.format(mFactory.get(position).getUpgradeCost()) +
                            "\nBuy Price : " + numberFormat.format(mFactory.get(position).getPriceFactory()) +
                            "\nSell Price : " + numberFormat.format(mFactory.get(position).getPriceFactory() / 2) +
                            "\nEnergy Gen : " + numberFormat.format(mFactory.get(position).getEnergyProd()) + "/s" +
                            "\nOperating Cost : " + numberFormat.format(mFactory.get(position).getOperatingCost()) + "/m" +
                            "\nEnvironment Tax : " + numberFormat.format(mFactory.get(position).getPollutionTax()) + "/m");
                } else {
                    upgradeFactorypopups.getUpgradeButton().setText("Update");
                    upgradeFactorypopups.setMessageSale(
                            "Actual Level : " + mFactory.get(position).getFactoryLevel() +
                            "\nUpdate Cost : " + numberFormat.format(mFactory.get(position).getUpgradeCost()) +
                            "\nSell Price : " + numberFormat.format(mFactory.get(position).getPriceFactory() / 2) +
                            "\nEnergy Gen : " + numberFormat.format(mFactory.get(position).getEnergyProd()) + "/s" +
                            "\nOperating Cost : " + numberFormat.format(mFactory.get(position).getOperatingCost()) + "/m" +
                            "\nEnvironment Tax : " + numberFormat.format(mFactory.get(position).getPollutionTax()) + "/m");
                }

                upgradeFactorypopups.getUpgradeButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upgradeFactory(db, position);
                        upgradeFactorypopups.dismiss();
                    }
                });

                upgradeFactorypopups.getClose().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upgradeFactorypopups.dismiss();
                    }
                });
                upgradeFactorypopups.build();
            }
        });

        holder.imageFactorys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upgradeFactory(db, position)) {
                    holder.lvlFactorys.setText(String.valueOf(mFactory.get(position).getFactoryLevel()));
                    holder.test.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return mFactory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageFactorys;
        TextView nameFactorys;
        TextView lvlFactorys;
        Button buttonInfoFactorys;

        CardView test;

        public ViewHolder(View itemView) {
            super(itemView);
            imageFactorys = itemView.findViewById(R.id.imageFactorys);
            nameFactorys = itemView.findViewById(R.id.FactorysName);
            lvlFactorys = itemView.findViewById(R.id.LevelFactorys);
            buttonInfoFactorys = itemView.findViewById(R.id.infoFactorys);
            test = itemView.findViewById(R.id.CardViewColor);
        }
    }

    public boolean upgradeFactory(Database db, int position){
        if(mFactory.get(position).getFactoryLevel() > 0) {
            if (sharedPreferences.getInt(PREFS_COIN, 0) >= mFactory.get(position).getUpgradeCost()) {
                mFactory.get(position).Upgrade(mFactory.get(position), db, activity);
                Toast.makeText(mContext, mFactory.get(position).getName() + " has been upgrade!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(mContext, "Not enough money!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (sharedPreferences.getInt(PREFS_COIN, 0) >= mFactory.get(position).getPriceFactory()) {
                mFactory.get(position).Upgrade(mFactory.get(position), db, activity);
                Toast.makeText(mContext, mFactory.get(position).getName() + " has been buying!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(mContext, "Not enough money!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
