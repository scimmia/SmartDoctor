package com.zljysoft.SmartDoctor.soaputil;

import org.ksoap2.serialization.PropertyInfo;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-24
 * Time: 上午9:14
 * To change this template use File | Settings | File Templates.
 */
public class SoapRequestStruct {
    private String serviceUrl;
    private String serviceNameSpace;
    private String methodName;
    private LinkedList<PropertyInfo> propertys;

    public SoapRequestStruct() {
        propertys = new LinkedList<PropertyInfo>();
    }
    public void addProperty(String name,String value){
        if (propertys == null){
            propertys = new LinkedList<PropertyInfo>();
        }
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName(name);
        propertyInfo.setValue(value);
        propertys.add(propertyInfo);
    }
    public SoapRequestStruct(String serviceUrl, String serviceNameSpace, String methodName, LinkedList<PropertyInfo> propertys) {
        this.serviceUrl = serviceUrl;
        this.serviceNameSpace = serviceNameSpace;
        this.methodName = methodName;
        this.propertys = propertys;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceNameSpace() {
        return serviceNameSpace;
    }

    public void setServiceNameSpace(String serviceNameSpace) {
        this.serviceNameSpace = serviceNameSpace;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LinkedList<PropertyInfo> getPropertys() {
        return propertys;
    }

    public void setPropertys(LinkedList<PropertyInfo> propertys) {
        this.propertys = propertys;
    }
}
