package com.nic.RealTimeMonitoringSystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterVillageListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;

import java.util.ArrayList;
import java.util.List;

public class VillageListAdapter extends RecyclerView.Adapter<VillageListAdapter.MyViewHolder> implements Filterable {
    private List<RealTimeMonitoringSystem> villageListValues;
    private List<RealTimeMonitoringSystem> villageValuesFiltered;
   private String letter;
   private Context context;
   private ColorGenerator generator = ColorGenerator.MATERIAL;

    private LayoutInflater layoutInflater;

    public VillageListAdapter(Context context, List<RealTimeMonitoringSystem> villageListValues) {
        this.context = context;
        this.villageListValues = villageListValues;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AdapterVillageListBinding adapterVillageListBinding;

        public MyViewHolder(AdapterVillageListBinding Binding) {
            super(Binding.getRoot());
            adapterVillageListBinding = Binding;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AdapterVillageListBinding adapterVillageListBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_village_list, viewGroup, false);
        return new MyViewHolder(adapterVillageListBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.adapterVillageListBinding.villageName.setText(villageValuesFiltered.get(position).getVillageListPvName());
        letter = String.valueOf(villageValuesFiltered.get(position).getVillageListPvName().charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.adapterVillageListBinding.villageFirstLetter.setImageDrawable(drawable);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    villageValuesFiltered = villageListValues;
                } else {
                    List<RealTimeMonitoringSystem> filteredList = new ArrayList<>();
                    for (RealTimeMonitoringSystem row : villageListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getVillageListPvName().toLowerCase().contains(charString.toLowerCase()) || row.getVillageListPvName().toLowerCase().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }

                    villageValuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = villageValuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                villageValuesFiltered = (ArrayList<RealTimeMonitoringSystem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return villageValuesFiltered == null ? 0 : villageValuesFiltered.size();
    }
}
