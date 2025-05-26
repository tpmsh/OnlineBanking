package com.tpmsh.helpers;

public class HTML {

    public static String htmlEmailTemplate(String token, String code) {
        String url = "http://127.0.0.1:8070/verify?token=" + token + "&code=" + code;
        String emailTemplate = "<html><body>" +
                "<h1>Welcome!</h1>" +
                "<p>Please click on the link below to verify your account:</p>" +
                "<a href='" + url + "'>Verify Your Account</a>" +
                "</body></html>";;
        return emailTemplate;
    }
}

