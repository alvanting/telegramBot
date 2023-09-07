package com.gcloud.telegramBot.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcloud.telegramBot.md5.MD5Util;
import com.gcloud.telegramBot.request.BalancePostRequest;
import com.gcloud.telegramBot.request.PayinPostRequest;
import com.gcloud.telegramBot.request.PayoutPostRequest;
import com.gcloud.telegramBot.response.BalancePostResponse;
import com.gcloud.telegramBot.response.PayinPostResponse;
import com.gcloud.telegramBot.response.PayoutPostResponse;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.stream.Collectors;

public class GLOPayBot extends TelegramLongPollingBot {
    private boolean botStarted = false;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Chat ID: " + update.getMessage().getChatId());
        System.out.println("User ID: " + update.getMessage().getFrom().getId());
        System.out.println("Name: " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
        System.out.println("Text: " + update.getMessage().getText());
        System.out.println();

        String command = update.getMessage().getText();
        String[] commandParts = command.split(" ");

        //if (update.getMessage().getFrom().getId() != 6239303541L && update.getMessage().getFrom().getId() != 729156731L) {
        if (command.equals("/start@GL0PayBot") || command.equals("/start")) {
            botStarted = true;

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText("启动 GLOPayBot");

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (!botStarted) {
            String message = "请通过发送 /start@GL0PayBot 或 /start 启动机器人";

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (command.equals("/end@GL0PayBot") || command.equals("/end")) {
            botStarted = false;

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText("结束 GLOPayBot");

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (commandParts[0].equals("/payin@GL0PayBot") || commandParts[0].equals("/payin")) {
            if (commandParts.length > 1) {
                String payoutParam = commandParts[1];
                PayinPostResponse message;
                try {
                    message = callPayinPostApi(payoutParam);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (message.getVersion() == null) {
                    SendMessage response = new SendMessage();
                    response.setChatId(update.getMessage().getChatId().toString());
                    response.setText("请核实订单号");

                    try {
                        execute(response);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage response = new SendMessage();
                    response.setChatId(update.getMessage().getChatId().toString());
                    try {
                        message.setSign(MD5Util.md5Encode(message.getVersion() + "|" + message.getAgentId() + "|" + message.getAgentOrderId() + "|" + message.getJnetOrderId() + "|" + message.getPayAmt() + "|" + message.getPayResult() + "|91738ecdb3f02865e988f39263587ad5"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    response.setText("商户订单号: " + message.getAgentOrderId() +
                            "\n我方订单号: " + message.getJnetOrderId() +
                            "\n实际支付金额: " + message.getPayAmt() +
                            "\n订单结果: " + message.getPayResult());
                    try {
                        execute(response);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String message = "请输入 /payin {你的订单号}";

                SendMessage response = new SendMessage();
                response.setChatId(update.getMessage().getChatId().toString());
                response.setText(message);

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (commandParts[0].equals("/payout@GL0PayBot") || commandParts[0].equals("/payout")) {
            if (commandParts.length > 1) {
                String payoutParam = commandParts[1];
                PayoutPostResponse message;
                try {
                    message = callPayoutPostApi(payoutParam);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (message.getVersion() == null) {
                    SendMessage response = new SendMessage();
                    response.setChatId(update.getMessage().getChatId().toString());
                    response.setText("请核实订单号");

                    try {
                        execute(response);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage response = new SendMessage();
                    response.setChatId(update.getMessage().getChatId().toString());
                    try {
                        message.setSign(MD5Util.md5Encode(message.getVersion() + "|" + message.getAgentId() + "|" + message.getAgentOrderId() + "|" + message.getJnetOrderId() + "|" + message.getAmount() + "|" + message.getPayeeResult() + "|" + message.getPayeeName() + "|" + message.getPayeeAccount() + "|91738ecdb3f02865e988f39263587ad5"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    response.setText("商户订单号: " + message.getAgentOrderId() +
                            "\n付款金额: " + message.getAmount() +
                            "\n订单结果: " + message.getPayeeResult() +
                            "\n收款人姓名: " + message.getPayeeName() +
                            "\n收款人账号: " + message.getPayeeAccount());
                    try {
                        execute(response);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String message = "请输入 /payout {你的订单号}";

                SendMessage response = new SendMessage();
                response.setChatId(update.getMessage().getChatId().toString());
                response.setText(message);

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (command.equals("/balance@GL0PayBot") || command.equals("/balance")) {
            BalancePostResponse message;
            try {
                message = callBalancePostApi();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!message.getRetCode().equals("0000")) {
                SendMessage response = new SendMessage();
                response.setChatId(update.getMessage().getChatId().toString());
                response.setText("请核实订单号");

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage response = new SendMessage();
                response.setChatId(update.getMessage().getChatId().toString());
                response.setText("可用余额: " + message.getAvailableBalance() + "\n余额: " + message.getBalance());
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String message = "请输入正确指令";

            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
//        }else {
//            String message = "用户无法使用机器人";
//
//            SendMessage response = new SendMessage();
//            response.setChatId(update.getMessage().getChatId().toString());
//            response.setText(message);
//
//            try {
//                execute(response);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public PayinPostResponse callPayinPostApi(String agentOrderId) throws Exception {
        PayinPostRequest request = new PayinPostRequest();

        String apiUrl = "https://ndP1DAb.easy-game.vip/gateway/query/";
        request.setVersion("1.0");
        request.setAgentId("test03");
        request.setAgentOrderId(agentOrderId);
        request.setSign(MD5Util.md5Encode(request.getVersion() + "|" + request.getAgentId() + "|" + request.getAgentOrderId() + "|91738ecdb3f02865e988f39263587ad5"));

        PayinPostResponse response = new PayinPostResponse();

        // 构建带参数的 URL
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?version=").append(URLEncoder.encode(request.getVersion(), "UTF-8"));
        urlBuilder.append("&agentId=").append(URLEncoder.encode(request.getAgentId(), "UTF-8"));
        urlBuilder.append("&agentOrderId=").append(URLEncoder.encode(request.getAgentOrderId(), "UTF-8"));
        urlBuilder.append("&sign=").append(URLEncoder.encode(request.getSign(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // 发送请求主体
        connection.setDoOutput(true);

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String responseBody = sb.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            responseBody = responseBody.trim();
            if (responseBody.startsWith("0")) {
                // 处理订单号以零开头的情况
                response.setAgentOrderId(responseBody);  // 将订单号作为字符串存储到响应对象中
                response.setPayMessage("请核实订单号");  // 提示用户核实订单号
            } else {
                response = objectMapper.readValue(responseBody, PayinPostResponse.class);
            }
        } else {
            System.err.println("API request failed with status code: " + statusCode);
        }

        connection.disconnect();

        return response;
    }

    public PayoutPostResponse callPayoutPostApi(String agentOrderId) throws Exception {
        PayoutPostRequest request = new PayoutPostRequest();

        String apiUrl = "https://ndP1DAb.easy-game.vip/withdraw/query/";
        request.setVersion("1.0");
        request.setAgentId("test03");
        request.setAgentOrderId(agentOrderId);
        request.setSign(MD5Util.md5Encode(request.getVersion() + "|" + request.getAgentId() + "|" + request.getAgentOrderId() + "|91738ecdb3f02865e988f39263587ad5"));

        PayoutPostResponse response = new PayoutPostResponse();

        // 构建带参数的 URL
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?version=").append(URLEncoder.encode(request.getVersion(), "UTF-8"));
        urlBuilder.append("&agentId=").append(URLEncoder.encode(request.getAgentId(), "UTF-8"));
        urlBuilder.append("&agentOrderId=").append(URLEncoder.encode(request.getAgentOrderId(), "UTF-8"));
        urlBuilder.append("&sign=").append(URLEncoder.encode(request.getSign(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // 发送请求主体
        connection.setDoOutput(true);

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String responseBody = sb.toString();

            // 解析响应体为 BalancePostResponse 对象
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(responseBody, PayoutPostResponse.class);
        } else {
            System.err.println("API request failed with status code: " + statusCode);
        }

        connection.disconnect();

        return response;
    }

    public BalancePostResponse callBalancePostApi() throws Exception {
        BalancePostRequest request = new BalancePostRequest();
        Random random = new Random();
        String randomNum = random.ints(16, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        String apiUrl = "https://ndP1DAb.easy-game.vip/withdraw/balanceQuery/";
        request.setVersion("1.0");
        request.setAgentId("test03");
        request.setRandom(randomNum);
        request.setSign(MD5Util.md5Encode(request.getVersion() + "|" + request.getAgentId() + "|" + request.getRandom() + "|91738ecdb3f02865e988f39263587ad5"));

        BalancePostResponse response = new BalancePostResponse();

        // 构建带参数的 URL
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?version=").append(URLEncoder.encode(request.getVersion(), "UTF-8"));
        urlBuilder.append("&agentId=").append(URLEncoder.encode(request.getAgentId(), "UTF-8"));
        urlBuilder.append("&random=").append(URLEncoder.encode(request.getRandom(), "UTF-8"));
        urlBuilder.append("&sign=").append(URLEncoder.encode(request.getSign(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // 发送请求主体
        connection.setDoOutput(true);

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            String responseBody = sb.toString();

            // 解析响应体为 BalancePostResponse 对象
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(responseBody, BalancePostResponse.class);
        } else {
            System.err.println("API request failed with status code: " + statusCode);
        }

        connection.disconnect();

        return response;
    }

    @Override
    public String getBotUsername() {
        return "GL0PayBot";
    }

    @Override
    public String getBotToken() {
        return "6513993761:AAETF3n0r4oMx11zQcGnPgefkKQhweWQHIc";
    }
}
