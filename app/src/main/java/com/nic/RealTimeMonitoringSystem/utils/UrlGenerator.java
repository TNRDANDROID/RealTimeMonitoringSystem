package com.nic.RealTimeMonitoringSystem.utils;


/**
 * Created by Achanthi Sundar  on 21/03/16.
 */
public class UrlGenerator {



    public static String getLoginUrl() {
//        return "https://tnrd.gov.in/project/webservices_forms/login_service/login_services.php";
        return "http://10.163.19.140/rdweb/project/webservices_forms/login_service/login_services.php";
    }

    public static String getServicesListUrl() {
//        return "https://tnrd.gov.in/project/webservices_forms/master_services/master_services.php";
        return "http://10.163.19.140/rdweb/project/webservices_forms/master_services/master_services.php";
    }

    public static String getWorkListUrl() {
//        return "https://tnrd.gov.in/project/webservices_forms/pmay/pmay_services.php";
        return "http://10.163.19.140/rdweb/project/webservices_forms/work_monitoring/work_monitoring_services_test.php";
    }


    public static String getTnrdHostName() {
//        return "tnrd.gov.in";
        return "10.163.19.140";
    }



}
