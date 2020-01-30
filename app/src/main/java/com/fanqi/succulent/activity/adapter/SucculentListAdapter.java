package com.fanqi.succulent.activity.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListItemLayoutBinding;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.SucculentListItemViewModel;
import com.fanqi.succulent.viewmodel.listener.SucculentItemClickedCallback;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.samlss.broccoli.Broccoli;

public class SucculentListAdapter extends RecyclerView.Adapter<SucculentListAdapter.ViewHolder>
        implements ViewModelCallback {

    private Context mContext;
    private Broccoli mBroccoli;
    private SucculentListItemLayoutBinding mBinding;
    private List<Succulent> mSucculentList;
    private NetworkUtil mNetworkUtil;
    private SucculentItemClickedCallback mItemClickedCallback;

    public SucculentListAdapter(Context context, Broccoli broccoli) {
        mBroccoli = broccoli;
        mContext = context;
        mSucculentList = new ArrayList<>();
        mNetworkUtil = new NetworkUtil();
        mNetworkUtil.setViewModelCallback(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.succulent_list_item_layout, parent, false);
        SucculentListItemViewModel model = new SucculentListItemViewModel();
        mBinding.setModel(model);

        View v = mBinding.getRoot();
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.view = v;
        viewHolder.textView = mBinding.succulentListItemTextView;
        //todo 需要设置imageView的高度
        viewHolder.imageView = mBinding.succulentListItemImage;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setOnClickListener(new ListItemOnClickListener(position));
        mBroccoli.addPlaceholders(holder.imageView);
        mBroccoli.show();
        holder.textView.setText(mSucculentList.get(position).getName());
        if (!TextUtils.isEmpty(holder.url)) {
            Glide.with(mContext).load(holder.url).into(holder.imageView);
        } else {
            mNetworkUtil.requestGetSingleImage(mSucculentList.get(position).getPage_name(), holder);
        }
    }

    @Override
    public int getItemCount() {
        return mSucculentList.size();
    }

    public void setData(List<Succulent> succulentList) {
        mSucculentList = succulentList;
    }


    @Override
    public void onSuccessed(Bundle object) {
        String url = object.getString(Constant.ViewModel.IMAGE);
        ViewHolder viewHolder = (ViewHolder) object.getSerializable(Constant.ViewModel.VIEW_HOLDER);
        viewHolder.url = url;
        Glide.with(mContext).load(url).into(viewHolder.imageView);
        mBroccoli.removePlaceholder(viewHolder.imageView);
    }

    @Override
    public void onFailed(Throwable e) {
    }

    public void setItemClickedCallback(SucculentItemClickedCallback itemClickedCallback) {
        mItemClickedCallback = itemClickedCallback;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        public TextView textView;
        public ImageView imageView;
        public View view;
        public String url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }


    class ListItemOnClickListener implements View.OnClickListener {
        private int mPosition;

        public ListItemOnClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mItemClickedCallback.onClick(v, mSucculentList.get(mPosition));
        }
    }
}
