package com.it.sps.dto;

/**
 * Adjust fields to match your SMS gateway contract.
 * These names come from your earlier message.
 */
public class SmsDataProjectCosting {
    private String acct_number;   // optional
    private String alias;
    private String app_ref_key;
    private String appkey;
    private String cost_cnter;    // dept id (optional)
    private String expr_date;     // yyyy-MM-dd HH:mm:ss
    private String message;       // will include TEMP_ID
    private Integer pr_key;       // if required
    private String proc_date;     // yyyy-MM-dd HH:mm:ss
    private String proc_flag;     // e.g., "W"
    private Integer recno;        // 0
    private String ref_id;        // TEMP_ID goes here
    private String shdl_date;     // yyyy-MM-dd HH:mm:ss
    private String tele_no;       // recipient

    public String getAcct_number() { return acct_number; }
    public void setAcct_number(String acct_number) { this.acct_number = acct_number; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public String getApp_ref_key() { return app_ref_key; }
    public void setApp_ref_key(String app_ref_key) { this.app_ref_key = app_ref_key; }
    public String getAppkey() { return appkey; }
    public void setAppkey(String appkey) { this.appkey = appkey; }
    public String getCost_cnter() { return cost_cnter; }
    public void setCost_cnter(String cost_cnter) { this.cost_cnter = cost_cnter; }
    public String getExpr_date() { return expr_date; }
    public void setExpr_date(String expr_date) { this.expr_date = expr_date; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Integer getPr_key() { return pr_key; }
    public void setPr_key(Integer pr_key) { this.pr_key = pr_key; }
    public String getProc_date() { return proc_date; }
    public void setProc_date(String proc_date) { this.proc_date = proc_date; }
    public String getProc_flag() { return proc_flag; }
    public void setProc_flag(String proc_flag) { this.proc_flag = proc_flag; }
    public Integer getRecno() { return recno; }
    public void setRecno(Integer recno) { this.recno = recno; }
    public String getRef_id() { return ref_id; }
    public void setRef_id(String ref_id) { this.ref_id = ref_id; }
    public String getShdl_date() { return shdl_date; }
    public void setShdl_date(String shdl_date) { this.shdl_date = shdl_date; }
    public String getTele_no() { return tele_no; }
    public void setTele_no(String tele_no) { this.tele_no = tele_no; }
}
