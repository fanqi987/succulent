package com.fanqi.succulent.network.page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析图片地址
 */
public class PageResolver {

    public static List<String> resolveImageUrl(Document document) {
        List<String> urls = new ArrayList<>();
        Elements elements = document.select(PagesHtmlConstant.IMAGE_URL_CHOOSER);
        urls.add(elements.first().attr("src").split(PagesHtmlConstant.IMAGE_URL_SPLITER)[0]);
        return urls;
    }


    public static List<String> resolveImageUrls(Document document) {
        List<String> urls = new ArrayList<>();
        Elements elements = document.select(PagesHtmlConstant.IMAGE_URL_CHOOSER);
        for (Element element : elements) {
            urls.add(element.attr("src").split(PagesHtmlConstant.IMAGE_URL_SPLITER)[0]);
        }
        return urls;
    }

    public static String resolveItemSummary(Document document) {
        Elements elements = document.select(PagesHtmlConstant.INFOS_SUMMARY_CHOOSER);
        return elements.first().text();
    }
}
