package com.zljysoft.SmartDoctor.soaputil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-21
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class DeptFromHIS {
    String aid;

    @Override
    public String toString() {
        return "DeptFromHIS{" +
                "aid='" + aid + '\'' +
                ", typ='" + typ + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sk='" + sk + '\'' +
                ", pr='" + pr + '\'' +
                ", id2='" + id2 + '\'' +
                ", ext='" + ext + '\'' +
                ", miCode='" + miCode + '\'' +
                ", note='" + note + '\'' +
                ", attr='" + attr + '\'' +
                ", deptype='" + deptype + '\'' +
                ", fmny='" + fmny + '\'' +
                ", act='" + act + '\'' +
                ", dis='" + dis + '\'' +
                ", up='" + up + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    String typ;
    String id;
    String name;
    String sk;
    String pr;
    String id2;
    String ext;
    String miCode;
    String note;
    String attr;
    String deptype;
    String fmny;
    String act;
    String dis;
    String up;
    String uid;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMiCode() {
        return miCode;
    }

    public void setMiCode(String miCode) {
        this.miCode = miCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getDeptype() {
        return deptype;
    }

    public void setDeptype(String deptype) {
        this.deptype = deptype;
    }

    public String getFmny() {
        return fmny;
    }

    public void setFmny(String fmny) {
        this.fmny = fmny;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
