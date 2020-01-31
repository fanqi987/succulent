package com.fanqi.succulent.network.page;

import com.fanqi.succulent.util.constant.Constant;

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
        Elements elements = document.select(PagesHtmlConstant.IMAGE_DATA_CHOOSER);
        urls.add(Constant.baseUrlBaiduDataImg +
                elements.first().attr(PagesHtmlConstant.IMAGE_DATA_ATTR));
        return urls;
    }


    public static List<String> resolveImageUrls(Document document) {
        List<String> urls = new ArrayList<>();
        Elements elements = document.select(PagesHtmlConstant.IMAGE_DATA_CHOOSER);
        for (Element element : elements) {
            urls.add(Constant.baseUrlBaiduDataImg +
                    element.attr(PagesHtmlConstant.IMAGE_DATA_ATTR));
        }
        return urls;
    }

    public static String resolveItemSummary(Document document) {
        Elements elements = document.select(PagesHtmlConstant.INFOS_SUMMARY_CHOOSER);
        return elements.first().text();
    }
}
