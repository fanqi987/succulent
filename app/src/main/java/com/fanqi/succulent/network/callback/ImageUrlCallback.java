package com.fanqi.succulent.network.callback;

import java.util.List;

public interface ImageUrlCallback {
    void onResolvedImageUrls(List<String> urls);
    void onResolvedSingleImageUrl(List<String> urls, Object viewHolder);

}
