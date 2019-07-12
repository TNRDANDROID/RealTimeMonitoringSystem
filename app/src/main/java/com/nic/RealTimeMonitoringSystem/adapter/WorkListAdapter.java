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
import com.nic.RealTimeMonitoringSystem.activity.WorkListScreen;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.utils.Utils;

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
    public void onBindViewHolder(WorkListAdapter.MyViewHolder holder, final int position) {

        String beneficary_name = WorkListValuesFiltered.get(position).getBeneficiaryName();
        Log.d("beneficary_name",beneficary_name);

        if(!beneficary_name.isEmpty()) {
            beneficary_name = " (" + WorkListValuesFiltered.get(position).getBeneficiaryName() + ")";
        }
        else {
            beneficary_name = "";
        }

        holder.adapterWorkListBinding.workid.setText(WorkListValuesFiltered.get(position).getWorkId() + beneficary_name);
        if(WorkListValuesFiltered.get(position).getSchemeGroupName().isEmpty()){
            holder.adapterWorkListBinding.tvSchemeGroupNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvSchemeGroupNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvSchemeGroupName.setText(WorkListValuesFiltered.get(position).getSchemeGroupName());
        }

        if(WorkListValuesFiltered.get(position).getSchemeName().isEmpty()){
            holder.adapterWorkListBinding.tvSchemeLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvSchemeLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvScheme.setText(WorkListValuesFiltered.get(position).getSchemeName());
        }

        if(WorkListValuesFiltered.get(position).getFinancialYear().isEmpty()){
            holder.adapterWorkListBinding.tvFinancialYearLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvFinancialYearLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvFinancialYear.setText(WorkListValuesFiltered.get(position).getFinancialYear());
        }

        if(WorkListValuesFiltered.get(position).getAgencyName().isEmpty()){
            holder.adapterWorkListBinding.tvAgencyNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvAgencyNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvAgencyName.setText(WorkListValuesFiltered.get(position).getAgencyName());
        }

        if(WorkListValuesFiltered.get(position).getWorkGroupNmae().isEmpty()){
            holder.adapterWorkListBinding.tvWorkGroupNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvWorkGroupNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvWorkGroupName.setText(WorkListValuesFiltered.get(position).getWorkGroupNmae());
        }

        if(WorkListValuesFiltered.get(position).getWorkName().isEmpty()){
            holder.adapterWorkListBinding.tvWorkNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvWorkNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvWorkName.setText(WorkListValuesFiltered.get(position).getWorkName());
        }

        if(WorkListValuesFiltered.get(position).getBlockName().isEmpty()){
            holder.adapterWorkListBinding.tvBlockLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvBlockLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvBlock.setText(WorkListValuesFiltered.get(position).getBlockName());
        }

        if(WorkListValuesFiltered.get(position).getPvName().isEmpty()){
            holder.adapterWorkListBinding.tvVillageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvVillageLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvVillage.setText(WorkListValuesFiltered.get(position).getPvName());
        }

        if(WorkListValuesFiltered.get(position).getStageName().isEmpty()){
            holder.adapterWorkListBinding.tvCurrentStageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvCurrentStageLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvCurrentStage.setText(WorkListValuesFiltered.get(position).getStageName());
        }

        if(WorkListValuesFiltered.get(position).getBeneficiaryFatherName().isEmpty()){
            holder.adapterWorkListBinding.tvBeneficiaryFatherNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvBeneficiaryFatherNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvBeneficiaryFatherName.setText(WorkListValuesFiltered.get(position).getBeneficiaryFatherName());
        }

        if(WorkListValuesFiltered.get(position).getGender().isEmpty()){
            holder.adapterWorkListBinding.tvGenderLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvGenderLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvGender.setText(WorkListValuesFiltered.get(position).getGender());
        }

        if(WorkListValuesFiltered.get(position).getCommunity().isEmpty()){
            holder.adapterWorkListBinding.tvCommunityLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvCommunityLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvCommunity.setText(WorkListValuesFiltered.get(position).getCommunity());
        }

        if(WorkListValuesFiltered.get(position).getIntialAmount() == null){
            holder.adapterWorkListBinding.tvInitialAmountLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvInitialAmountLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvInitialAmount.setText(WorkListValuesFiltered.get(position).getIntialAmount());
        }

        if(WorkListValuesFiltered.get(position).getAmountSpendSoFar().isEmpty()){
            holder.adapterWorkListBinding.tvAmountSpentSoFarLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvAmountSpentSoFarLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvAmountSpentSoFar.setText(WorkListValuesFiltered.get(position).getAmountSpendSoFar());
        }

        if(WorkListValuesFiltered.get(position).getLastVisitedDate().isEmpty()){
            holder.adapterWorkListBinding.tvLastVisitedDateLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvLastVisitedDateLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvLastVisitedDate.setText(Utils.formatDateReverse(WorkListValuesFiltered.get(position).getLastVisitedDate()));

        }


        if(WorkListValuesFiltered.get(position).getCdProtWorkYn().equalsIgnoreCase("Y")){
            holder.adapterWorkListBinding.belowSpace.setVisibility(View.GONE);
            holder.adapterWorkListBinding.viewAdditionalWorks.setVisibility(View.VISIBLE);
        }else {
            holder.adapterWorkListBinding.belowSpace.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.viewAdditionalWorks.setVisibility(View.GONE);
        }


        holder.adapterWorkListBinding.workLayout.setOnClickListener(new View.OnClickListener() {
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
                    WorkListValuesFiltered = WorkListValues;
                } else {
                    List<RealTimeMonitoringSystem> filteredList = new ArrayList<>();
                    for (RealTimeMonitoringSystem row : WorkListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getWorkId().toString().toLowerCase().contains(charString.toLowerCase()) || row.getBeneficiaryName().toLowerCase().contains(charString.toLowerCase())) {
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

    public void openCameraScreen(int pos) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, CameraScreen.class);
        intent.putExtra(AppConstant.TYPE_OF_WORK,AppConstant.MAIN_WORK);
        intent.putExtra(AppConstant.WORK_GROUP_ID,WorkListValuesFiltered.get(pos).getWorkGroupID());
        intent.putExtra(AppConstant.WORK_TYPE_ID,WorkListValuesFiltered.get(pos).getWorkTypeID());
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return WorkListValuesFiltered == null ? 0 : WorkListValuesFiltered.size();
    }
}

