package org.codeman.core.generate.vo;

import com.aliyun.alimt20181012.Client;
import com.aliyun.alimt20181012.models.TranslateGeneralRequest;
import com.aliyun.alimt20181012.models.TranslateGeneralResponse;
import com.aliyun.tea.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;

import java.util.Arrays;
import java.util.List;

/**
 * @author hdgaadd
 * created on 2022/11/28
 */
public class AlibabaTranslate {

    public static void main(String[] args_) throws Exception {
        List<String> args = Arrays.asList(args_);
        Client client = AlibabaTranslate.createClient("accessKeyId", "accessKeySecret");
        TranslateGeneralRequest translateGeneralRequest = new TranslateGeneralRequest()
                .setFormatType("text")
                .setSourceLanguage("zh")
                .setTargetLanguage("en")
                .setSourceText("测试")
                .setScene("general");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            TranslateGeneralResponse response = client.translateGeneralWithOptions(translateGeneralRequest, runtime);
            System.out.println(Common.toJSONString(response));
        } catch (TeaException error) {
            error.printStackTrace();
        } catch (Exception _error) {
            _error.printStackTrace();
        }
    }

    /**
     * 使用AK&SK初始化账号Client
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // AccessKey ID
                .setAccessKeyId(accessKeyId)
                // AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问域名
        config.endpoint = "mt.cn-hangzhou.aliyuncs.com";
        return new Client(config);
    }

}