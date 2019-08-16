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
import com.nic.RealTimeMonitoringSystem.activity.FullImageActivity;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.dataBase.dbData;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterAdditionalListBinding;
import com.nic.RealTimeMonitoringSystem.databinding.AdapterWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;
import com.nic.RealTimeMonitoringSystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdditionalListAdapter extends RecyclerView.Adapter<AdditionalListAdapter.MyViewHolder> implements Filterable {
    private List<RealTimeMonitoringSystem> AdditionalListValues;
    private List<RealTimeMonitoringSystem> AdditionalListValuesFiltered;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private final dbData dbData;
    PrefManager prefManager;
    public final String dcode,bcode,pvcode;

    private LayoutInflater layoutInflater;

    public AdditionalListAdapter(Context context, List<RealTimeMonitoringSystem> WorkListValues,dbData dbData) {
        this.context = context;
        prefManager = new PrefManager(context);
        this.AdditionalListValues = WorkListValues;
        this.AdditionalListValuesFiltered = WorkListValues;
        this.dbData = dbData;
        dcode = prefManager.getDistrictCode();
        bcode = prefManager.getBlockCode();
        pvcode = prefManager.getPvCode();

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


        holder.adapterAdditionalListBinding.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraScreen(position);
            }
        });

        final String work_id = String.valueOf(AdditionalListValuesFiltered.get(position).getWorkId());
        final String cd_work_no = String.valueOf(AdditionalListValuesFiltered.get(position).getCdWorkNo());

        ArrayList<RealTimeMonitoringSystem> imageOffline = dbData.selectImage(dcode,bcode,pvcode,work_id,AppConstant.ADDITIONAL_WORK,cd_work_no);

        if(imageOffline.size() > 0) {
            holder.adapterAdditionalListBinding.viewOfflineImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapterAdditionalListBinding.viewOfflineImage.setVisibility(View.GONE);
        }

        holder.adapterAdditionalListBinding.viewOfflineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOfflineImages(work_id,cd_work_no,AppConstant.ADDITIONAL_WORK,"Offline");
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
        intent.putExtra(AppConstant.WORK_GROUP_ID,String.valueOf(AdditionalListValuesFiltered.get(pos).getWorkGroupID()));
        intent.putExtra(AppConstant.CD_WORK_NO,String.valueOf(AdditionalListValuesFiltered.get(pos).getCdWorkNo()));
        intent.putExtra(AppConstant.WORK_ID,String.valueOf(AdditionalListValuesFiltered.get(pos).getWorkId()));
        intent.putExtra(AppConstant.CD_CODE,String.valueOf(AdditionalListValuesFiltered.get(pos).getCdCode()));
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return AdditionalListValuesFiltered == null ? 0 : AdditionalListValuesFiltered.size();
    }

    public void viewOfflineImages(String work_id,String cd_work_no,String type_of_work,String OnOffType) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, FullImageActivity.class);
        intent.putExtra(AppConstant.WORK_ID,work_id);
        intent.putExtra(AppConstant.CD_WORK_NO,cd_work_no);
        intent.putExtra("OnOffType",OnOffType);

        if(OnOffType.equalsIgnoreCase("Offline")){
            intent.putExtra(AppConstant.TYPE_OF_WORK,type_of_work);
            activity.startActivity(intent);
        }
        else if(OnOffType.equalsIgnoreCase("Online")) {
            if(Utils.isOnline()){
                activity.startActivity(intent);
            }else {
                Utils.showAlert(activity,"Your Internet seems to be Offline.Images can be viewed only in Online mode.");
            }
        }


        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

