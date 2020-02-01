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
        return resolve(document, PagesHtmlConstant.INFOS_SUMMARY_CHOOSER);

    }

    public static String resolveItemFamily(Document document) {
        return resolve(document, PagesHtmlConstant.FAMILY_CHOOSER);

    }

    public static String resolveItemGenera(Document document) {
        return resolve(document, PagesHtmlConstant.GENUS_CHOOSER);

    }

    public static String resolveItemName(Document document) {
        return resolve(document, PagesHtmlConstant.NAME_CHOOSER);
    }

    public static String resolve(Document document, String chooser) {
        Elements elements = document.select(chooser);
        return elements.first().text();
    }
}
