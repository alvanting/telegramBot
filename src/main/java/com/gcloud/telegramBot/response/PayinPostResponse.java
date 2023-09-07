package com.gcloud.telegramBot.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayinPostResponse {
    private String version;
    private String agentId;
    private String agentOrderId;
    private String jnetOrderId;
    private BigDecimal payAmt;
    private String payResult;
    private String payMessage;
    private String sign;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentOrderId() {
        return agentOrderId;
    }

    public void setAgentOrderId(String agentOrderId) {
        this.agentOrderId = agentOrderId;
    }

    public String getJnetOrderId() {
        return jnetOrderId;
    }

    public void setJnetOrderId(String jnetOrderId) {
        this.jnetOrderId = jnetOrderId;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
