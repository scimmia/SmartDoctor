package com.zljysoft.SmartDoctor.soaputil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-24
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public class SoapClient {
    public static Object getSoapFromURL(SoapRequestStruct soapRequestStruct){
        Object result = null;
        SoapObject soapObject=new SoapObject(soapRequestStruct.getServiceNameSpace(),soapRequestStruct.getMethodName());
        if (soapRequestStruct.getPropertys()!=null){
            for (PropertyInfo propertyInfo : soapRequestStruct.getPropertys()){
                soapObject.addProperty(propertyInfo);
            }
        }
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;

        HttpTransportSE httpTranstation=new HttpTransportSE(soapRequestStruct.getServiceUrl());
        try {
            httpTranstation.call(soapRequestStruct.getServiceNameSpace()+soapRequestStruct.getMethodName(), envelope);
            if(envelope.getResponse()!=null){
                result = envelope.getResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}
