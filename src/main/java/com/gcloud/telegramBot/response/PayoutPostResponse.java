package com.gcloud.telegramBot.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutPostResponse {
    private String version;
    private String agentId;
    private String agentOrderId;
    private String jnetOrderId;
    private BigDecimal amount;
    private String payeeResult;
    private String payeeName;
    private String payeeAccount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayeeResult() {
        return payeeResult;
    }

    public void setPayeeResult(String payeeResult) {
        this.payeeResult = payeeResult;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
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
