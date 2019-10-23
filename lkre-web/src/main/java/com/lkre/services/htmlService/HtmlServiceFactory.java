package com.lkre.services.htmlService;

public class HtmlServiceFactory {
    public static HtmlService createService() {
        return new UrlHtmlService();
    }
}
