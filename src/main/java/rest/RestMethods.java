package rest;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestMethods {

    public static void setBaseUrl(String URL) {
        if (!URL.isEmpty()) {
            RestAssured.baseURI = URL;
        }
    }

    public static RequestSpecification authorization(IAuth Icontext) {
        RequestSpecification reqSpec;
        if (Icontext == null) {
            reqSpec = RestAssured.given();
        } else {
            reqSpec = Icontext.auth();
        }
        return reqSpec;
    }

    public static RequestSpecification init(RequestContext context) {


        setBaseUrl(context.baseURL);
        RequestSpecification reqSpec = authorization(context.auth);
        if (context.contentTypeCharset != null && !context.contentTypeCharset) {
            EncoderConfig EC = new EncoderConfig();
            reqSpec.config(RestAssured.config().encoderConfig(EC.appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        }
        if (!context.pathParams.isEmpty()) {
            reqSpec.pathParams(context.pathParams);
        }
        if (!context.formParams.isEmpty()) {
            reqSpec.formParams(context.formParams);
        }
        if (!context.queryParams.isEmpty()) {
            reqSpec.queryParams(context.queryParams);
        }
        if (context.requestContentType != null && !context.requestContentType.equals("")) {
            reqSpec.contentType(context.requestContentType.getContentType());
        }
        if (context.requestHeaderParams != null) {
            for (Map.Entry<String, Object> header : context.requestHeaderParams.entrySet()) {
                if (header.getValue() != null) {
                    reqSpec.header(header.getKey(), header.getValue());
                }
            }
        }

        
        if (!context.headers.isEmpty()) {
            for (Map.Entry<String, Object> header : context.headers.entrySet()) {
                reqSpec.headers(header.getKey(), header.getValue());
            }
        }
        if (!context.params.isEmpty()) {
            for (Map.Entry<String, Object> paramMap : context.params.entrySet()) {
                reqSpec.param(paramMap.getKey(), paramMap.getValue());
            }
        }
        if (context.reqHeader != null && !context.reqHeader.equals("")) {
            reqSpec.header(new Header("Authorization: ", context.reqHeader));
        }

        return reqSpec;
    }

    public static Response GET(RequestContext context) {
        Response resp = null;
        RequestSpecification reqSpec = init(context);
        if (context.URI != null && !context.URI.equals("")) {
            resp = reqSpec.get(context.URI);
        }
        return resp;
    }

    public static Response POST(RequestContext context) {
        Response resp = null;
        RequestSpecification reqSpec = init(context);

        if (context.fileBody != null) {
            reqSpec.body(context.fileBody);
        }
        if (context.requestBodyString != null) {
            reqSpec.body(context.requestBodyString); 
        }
        if (!context.multiParts.isEmpty()) {
            for (Map.Entry<String, Object> multi : context.multiParts.entrySet()) {
                reqSpec.multiPart(multi.getKey(), multi.getValue());
            }
        }
        if (context.URI != null && !context.URI.equals("")) {
            resp = reqSpec.post(context.URI);
        } else {
            resp = reqSpec.post();
        }
        return resp;
    }


}
