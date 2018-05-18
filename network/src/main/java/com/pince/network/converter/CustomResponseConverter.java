package com.pince.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.pince.network.ServerConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);
            String codeKey = ServerConfig.getInstance().getCodeKey();
            if (json.has(codeKey)) {
                int code = json.optInt(codeKey);
                if (code == ServerConfig.getInstance().getCodeValid()) {
                    String dataKey = ServerConfig.getInstance().getDataKey();
                    if (json.has(dataKey)) {
                        Object data = json.opt(dataKey);
                        if (data instanceof JSONObject) {
                            // body = gson.toJson(dataObject);
                            JSONObject jsonObjectData = (JSONObject)data;
                            if (jsonObjectData.length() == 0) {
                                //正常返回没有数据的接口时，使用String进行接收
                                return (T) "";
                            }
                        } else if (data instanceof JSONArray) {
                            JSONArray jsonArrayData = (JSONArray) data;
                            if (jsonArrayData.length() == 0) {
                            }
                        }
                        return adapter.fromJson(data.toString());
                    } else {//如果接口没有返回data字段，则返回默认的json
                        return adapter.fromJson(body);
                    }
                } else {
                    String messageKey = ServerConfig.getInstance().getMsgKey();
                    StringBuilder exceptionValue = new StringBuilder()
                            .append(code)
                            .append(",")
                            .append(json.has(messageKey) ? json.optString(messageKey) : "");
                    throw new RuntimeException(exceptionValue.toString());
                }
            } else {
                return adapter.fromJson(body);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
