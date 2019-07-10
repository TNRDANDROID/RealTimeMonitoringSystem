package com.nic.RealTimeMonitoringSystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.nic.RealTimeMonitoringSystem.activity.WorkListScreen;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;

import java.util.ArrayList;
import java.util.List;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.MyViewHolder> implements Filterable {
    private List<RealTimeMonitoringSystem> WorkListValues;
    private List<RealTimeMonitoringSystem> WorkListValuesFiltered;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;

    private LayoutInflater layoutInflater;

    public WorkListAdapter(Context context, List<RealTimeMonitoringSystem> WorkListValues) {
        this.context = context;
        this.WorkListValues = WorkListValues;
        this.WorkListValuesFiltered = WorkListValues;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AdapterWorkListBinding adapterWorkListBinding;

        public MyViewHolder(AdapterWorkListBinding Binding) {
            super(Binding.getRoot());
            adapterWorkListBinding = Binding;
        }
    }

    @Override
    public WorkListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AdapterWorkListBinding adapterWorkListBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_work_list, viewGroup, false);
        return new WorkListAdapter.MyViewHolder(adapterWorkListBinding);
    }

    @Override
    public void onBindViewHolder(WorkListAdapter.MyViewHolder holder, int position) {

        holder.adapterWorkListBinding.workid.setText(WorkListValuesFiltered.get(position).getWorkId() + " (" + WorkListValuesFiltered.get(position).getBeneficiaryName() + ")");
        holder.adapterWorkListBinding.tvSchemeGroupName.setText(WorkListValuesFiltered.get(position).getSchemeGroupName());
        holder.adapterWorkListBinding.tvScheme.setText(WorkListValuesFiltered.get(position).getSchemeName());
        holder.adapterWorkListBinding.tvFinancialYear.setText(WorkListValuesFiltered.get(position).getFinancialYear());
        holder.adapterWorkListBinding.tvAgencyName.setText(WorkListValuesFiltered.get(position).getAgencyName());
        holder.adapterWorkListBinding.tvWorkGroupName.setText(WorkListValuesFiltered.get(position).getWorkGroupNmae());
        holder.adapterWorkListBinding.tvWorkName.setText(WorkListValuesFiltered.get(position).getWorkName());
        holder.adapterWorkListBinding.tvBlock.setText(WorkListValuesFiltered.get(position).getBlockName());
        holder.adapterWorkListBinding.tvVillage.setText(WorkListValuesFiltered.get(position).getPvName());
        holder.adapterWorkListBinding.tvCurrentStage.setText(WorkListValuesFiltered.get(position).getCurrentStage());
        holder.adapterWorkListBinding.tvBeneficiaryFatherName.setText(WorkListValuesFiltered.get(position).getBeneficiaryFatherName());
        holder.adapterWorkListBinding.tvGender.setText(WorkListValuesFiltered.get(position).getGender());
        holder.adapterWorkListBinding.tvCommunity.setText(WorkListValuesFiltered.get(position).getCommunity());
        holder.adapterWorkListBinding.tvInitialAmount.setText(WorkListValuesFiltered.get(position).getIntialAmount());
        holder.adapterWorkListBinding.tvAmountSpentSoFar.setText(WorkListValuesFiltered.get(position).getAmountSpendSoFar());
        holder.adapterWorkListBinding.tvLastVisitedDate.setText(WorkListValuesFiltered.get(position).getLastVisitedDate());

        holder.adapterWorkListBinding.workLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraScreen();
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
                    WorkListValuesFiltered = WorkListValues;
                } else {
                    List<RealTimeMonitoringSystem> filteredList = new ArrayList<>();
                    for (RealTimeMonitoringSystem row : WorkListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getWorkId().toString().toLowerCase().contains(charString.toLowerCase()) || row.getBeneficiaryName().toLowerCase().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }

                    WorkListValuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = WorkListValuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                WorkListValuesFiltered = (ArrayList<RealTimeMonitoringSystem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void openCameraScreen() {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, CameraScreen.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return WorkListValuesFiltered == null ? 0 : WorkListValuesFiltered.size();
    }
}

