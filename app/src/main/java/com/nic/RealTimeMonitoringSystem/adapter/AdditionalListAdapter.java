package com.nic.RealTimeMonitoringSystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.activity.CameraScreen;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterAdditionalListBinding;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdditionalListAdapter extends RecyclerView.Adapter<AdditionalListAdapter.MyViewHolder> implements Filterable {
    private List<RealTimeMonitoringSystem> AdditionalListValues;
    private List<RealTimeMonitoringSystem> AdditionalListValuesFiltered;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;

    private LayoutInflater layoutInflater;

    public AdditionalListAdapter(Context context, List<RealTimeMonitoringSystem> WorkListValues) {
        this.context = context;
        this.AdditionalListValues = WorkListValues;
        this.AdditionalListValuesFiltered = WorkListValues;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AdapterAdditionalListBinding adapterAdditionalListBinding;

        public MyViewHolder(AdapterAdditionalListBinding Binding) {
            super(Binding.getRoot());
            adapterAdditionalListBinding = Binding;
        }
    }

    @Override
    public AdditionalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AdapterAdditionalListBinding adapterAdditionalListBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_additional_list, viewGroup, false);
        return new AdditionalListAdapter.MyViewHolder(adapterAdditionalListBinding);
    }

    @Override
    public void onBindViewHolder(AdditionalListAdapter.MyViewHolder holder, final int position) {

       // holder.adapterAdditionalListBinding.workid.setText(String.valueOf(AdditionalListValuesFiltered.get(position).getWorkId()));
        if(AdditionalListValuesFiltered.get(position).getCdName().isEmpty()){
            holder.adapterAdditionalListBinding.tvCdWorkNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterAdditionalListBinding.tvCdWorkNameLayout.setVisibility(View.VISIBLE);
            holder.adapterAdditionalListBinding.tvCdWorkName.setText(AdditionalListValuesFiltered.get(position).getCdName());
        }

        if(AdditionalListValuesFiltered.get(position).getChainageMeter().isEmpty()){
            holder.adapterAdditionalListBinding.tvChainageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterAdditionalListBinding.tvChainageLayout.setVisibility(View.VISIBLE);
            holder.adapterAdditionalListBinding.tvChainage.setText(AdditionalListValuesFiltered.get(position).getChainageMeter());
        }

        if(AdditionalListValuesFiltered.get(position).getWorkStageName().isEmpty()){
            holder.adapterAdditionalListBinding.tvWorkstageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterAdditionalListBinding.tvWorkstageLayout.setVisibility(View.VISIBLE);
            holder.adapterAdditionalListBinding.tvWorkstage.setText(AdditionalListValuesFiltered.get(position).getWorkStageName());
        }


        holder.adapterAdditionalListBinding.additioanlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraScreen(position);
            }
        });
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    AdditionalListValuesFiltered = AdditionalListValues;
                } else {
                    List<RealTimeMonitoringSystem> filteredList = new ArrayList<>();
                    for (RealTimeMonitoringSystem row : AdditionalListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getWorkId().toString().toLowerCase().contains(charString.toLowerCase()) || row.getBeneficiaryName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    AdditionalListValuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = AdditionalListValuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                AdditionalListValuesFiltered = (ArrayList<RealTimeMonitoringSystem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void openCameraScreen(int pos) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, CameraScreen.class);
        intent.putExtra(AppConstant.TYPE_OF_WORK,AppConstant.ADDITIONAL_WORK);
        intent.putExtra(AppConstant.WORK_ID,AdditionalListValuesFiltered.get(pos).getWorkId());
        intent.putExtra(AppConstant.CD_CODE,String.valueOf(AdditionalListValuesFiltered.get(pos).getCdCode()));
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return AdditionalListValuesFiltered == null ? 0 : AdditionalListValuesFiltered.size();
    }
}

