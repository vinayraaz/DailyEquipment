package com.dailyequipment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dailyequipment.NotWorking_Equipment_Activity;
import com.dailyequipment.R;
import com.dailyequipment.parshingModel.EquipmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 30-Mar-18.
 */

public class EquipmentRecyclerAdapter extends RecyclerView.Adapter<EquipmentRecyclerAdapter.EquipmentViewHolder> {
    private Context context;
    List<String> Select_Equ_Nmae = new ArrayList<>();
    List<String> Select_Equ_Name_ID = new ArrayList<>();
    int listSize;

    public EquipmentRecyclerAdapter(Context context, List<String> select_equ_name, List<String> select_equ_name_id) {
        this.context = context;
        this.Select_Equ_Nmae = select_equ_name;
        this.Select_Equ_Name_ID = select_equ_name_id;
        listSize = this.Select_Equ_Name_ID.size();
        notifyDataSetChanged();
    }


    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_equipment_list_details, parent, false);
        EquipmentViewHolder contactViewHolder = new EquipmentViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(EquipmentViewHolder holder, final int position) {

        holder.EquipmentName.setText(Select_Equ_Nmae.get(position).toString());
        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_Equ_Nmae.remove(position);
                Select_Equ_Name_ID.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return Select_Equ_Nmae.size();
    }

    public class EquipmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView EquipmentName;
        public ImageView Cancel;

        public EquipmentViewHolder(View itemView) {
            super(itemView);
            EquipmentName = (TextView) itemView.findViewById(R.id.equipment_name);
            Cancel = (ImageView) itemView.findViewById(R.id.image_cancel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
