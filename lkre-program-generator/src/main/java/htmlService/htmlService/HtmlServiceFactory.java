package htmlService.htmlService;

public class HtmlServiceFactory {
    public static HtmlService createService() {
        return new UrlHtmlService();
    }
}
