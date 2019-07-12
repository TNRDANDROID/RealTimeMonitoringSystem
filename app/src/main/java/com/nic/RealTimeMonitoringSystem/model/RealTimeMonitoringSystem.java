package com.nic.RealTimeMonitoringSystem.model;

import android.graphics.Bitmap;

/**
 * Created by AchanthiSundar on 01-11-2017.
 */

public class RealTimeMonitoringSystem {

    private String distictCode;
    private String districtName;

    private String blockCode;
    private String SchemeGroupName;
    private String SchemeName;
    private String Description;
    private String Latitude;
    private String selectedBlockCode;

    private String FinancialYear;
    private String AgencyName;
    private String WorkGroupNmae;
    private String WorkName;
    private String PvCode;
    private String PvName;

    private String blockName;
    private String Gender;
    private Integer CurrentStage;

    private String Name;
    private String BeneficiaryName;
    private String workGroupID;
    private String workTypeID;

    public String getBeneficiaryFatherName() {
        return BeneficiaryFatherName;
    }

    public void setBeneficiaryFatherName(String beneficiaryFatherName) {
        BeneficiaryFatherName = beneficiaryFatherName;
    }

    private String BeneficiaryFatherName;
    private String Community;
    private String IntialAmount;

    private String AmountSpendSoFar;
    private String LastVisitedDate;

    private Integer pendingActivity;
    public String workStageName;
    private String workStageCode;
    private String workStageOrder;
    private Integer schemeSequentialID;

    private Integer schemeGroupID;
    private Integer schemeID;
    private Integer asValue;
    private String stageName;
    private String workTypeName;
    private String ynCompleted;
    private String cdProtWorkYn;
    private Integer stateCode;

    public String getYnCompleted() {
        return ynCompleted;
    }

    public void setYnCompleted(String ynCompleted) {
        this.ynCompleted = ynCompleted;
    }

    public String getCdProtWorkYn() {
        return cdProtWorkYn;
    }

    public void setCdProtWorkYn(String cdProtWorkYn) {
        this.cdProtWorkYn = cdProtWorkYn;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public Integer getCurrentStage() {
        return CurrentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        CurrentStage = currentStage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getAsValue() {
        return asValue;
    }

    public void setAsValue(Integer asValue) {
        this.asValue = asValue;
    }

    public Integer getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(Integer schemeID) {
        this.schemeID = schemeID;
    }

    public Integer getSchemeGroupID() {
        return schemeGroupID;
    }

    public void setSchemeGroupID(Integer schemeGroupID) {
        this.schemeGroupID = schemeGroupID;
    }

    public Integer getSchemeSequentialID() {
        return schemeSequentialID;
    }

    public void setSchemeSequentialID(Integer schemeSequentialID) {
        this.schemeSequentialID = schemeSequentialID;
    }

    private Integer noOfPhotos;

    public String getSchemeGroupName() {
        return SchemeGroupName;
    }

    public void setSchemeGroupName(String schemeGroupName) {
        SchemeGroupName = schemeGroupName;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getFinancialYear() {
        return FinancialYear;
    }

    public void setFinancialYear(String financialYear) {
        FinancialYear = financialYear;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String agencyName) {
        AgencyName = agencyName;
    }

    public String getWorkGroupNmae() {
        return WorkGroupNmae;
    }

    public void setWorkGroupNmae(String workGroupNmae) {
        WorkGroupNmae = workGroupNmae;
    }

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBeneficiaryName() {
        return BeneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        BeneficiaryName = beneficiaryName;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getIntialAmount() {
        return IntialAmount;
    }

    public void setIntialAmount(String intialAmount) {
        IntialAmount = intialAmount;
    }

    public String getAmountSpendSoFar() {
        return AmountSpendSoFar;
    }

    public void setAmountSpendSoFar(String amountSpendSoFar) {
        AmountSpendSoFar = amountSpendSoFar;
    }

    public String getLastVisitedDate() {
        return LastVisitedDate;
    }

    public void setLastVisitedDate(String lastVisitedDate) {
        LastVisitedDate = lastVisitedDate;
    }

    public Integer getWorkId() {
        return WorkId;
    }

    public void setWorkId(Integer workId) {
        WorkId = workId;
    }

    private Integer WorkId;

    private String type;
    private String imageRemark;
    private String dateTime;
    private String imageAvailable;



    public String getImageAvailable() {
        return imageAvailable;
    }

    public void setImageAvailable(String imageAvailable) {
        this.imageAvailable = imageAvailable;
    }

    public String getType() {
        return type;
    }

    public void setType(String pointType) {
        this.type = pointType;
    }

    public String getImageRemark() {
        return imageRemark;
    }

    public void setImageRemark(String imageRemark) {
        this.imageRemark = imageRemark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }



    public Integer getNoOfPhotos() {
        return noOfPhotos;
    }

    public void setNoOfPhotos(Integer noOfPhotos) {
        this.noOfPhotos = noOfPhotos;
    }


    public Integer getPendingActivity() {
        return pendingActivity;
    }

    public void setPendingActivity(Integer pendingActivity) {
        this.pendingActivity = pendingActivity;
    }




    public String getPvName() {
        return PvName;
    }

    public void setPvName(String pvName) {
        PvName = pvName;
    }



    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getDistictCode() {
        return distictCode;
    }

    public void setDistictCode(String distictCode) {
        this.distictCode = distictCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSelectedBlockCode() {
        return selectedBlockCode;
    }

    public void setSelectedBlockCode(String selectedBlockCode) {
        this.selectedBlockCode = selectedBlockCode;
    }




    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    private String Longitude;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    private Bitmap Image;



    public String getPvCode() {
        return PvCode;
    }

    public void setPvCode(String pvCode) {
        this.PvCode = pvCode;
    }

    public void setWorkStageName(String workStageName) {
        this.workStageName = workStageName;
    }

    public String getWorkStageName() {
        return workStageName;
    }

    public void setWorkGroupID(String workGroupID) {
        this.workGroupID = workGroupID;
    }

    public String getWorkGroupID() {
        return workGroupID;
    }

    public void setWorkTypeID(String workTypeID) {
        this.workTypeID = workTypeID;
    }

    public String getWorkTypeID() {
        return workTypeID;
    }

    public void setWorkStageCode(String workStageCode) {
        this.workStageCode = workStageCode;
    }

    public String getWorkStageCode() {
        return workStageCode;
    }

    public void setWorkStageOrder(String workStageOrder) {
        this.workStageOrder = workStageOrder;
    }

    public String getWorkStageOrder() {
        return workStageOrder;
    }


}