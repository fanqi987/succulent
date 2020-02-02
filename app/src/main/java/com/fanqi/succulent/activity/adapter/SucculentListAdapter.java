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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListTabItemLayoutBinding;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.SettingsUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.SucculentListTabItemViewModel;
import com.fanqi.succulent.viewmodel.listener.SucculentItemClickedCallback;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.samlss.broccoli.Broccoli;

public class SucculentListAdapter extends RecyclerView.Adapter<SucculentListAdapter.ViewHolder>
        implements ViewModelCallback {

    private Fragment mFragment;
    private Context mContext;
    private Broccoli mBroccoli;
    private SucculentListTabItemLayoutBinding mBinding;
    private List<Succulent> mSucculentList;
    private Map<Integer,String> mImageUrls;
    private NetworkUtil mNetworkUtil;
    private SucculentItemClickedCallback mItemClickedCallback;

    public SucculentListAdapter(Fragment fragment, Broccoli broccoli) {
        mBroccoli = broccoli;
        mFragment = fragment;
        mContext = mFragment.getContext();
        mSucculentList = new ArrayList<>();
        mImageUrls=new HashMap<>();
        mNetworkUtil = new NetworkUtil();
        mNetworkUtil.setViewModelCallback(this);
        mBroccoli.show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.succulent_list_tab_item_layout, parent, false);
        SucculentListTabItemViewModel model = new SucculentListTabItemViewModel();
        mBinding.setModel(model);

        View v = mBinding.getRoot();
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.view = v;
        viewHolder.textView = mBinding.succulentListTabItemTextView;
        //todo 需要设置imageView的高度
        viewHolder.imageView = mBinding.succulentListTabItemImage;
        ViewGroup.LayoutParams layoutParams = viewHolder.imageView.getLayoutParams();
        layoutParams.height = (int) (SettingsUtil.getDisplayMetrics(mFragment.getActivity())
                .widthPixels / 2 * 0.8);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setOnClickListener(new ListItemOnClickListener(position));
        mBroccoli.addPlaceholders(holder.imageView);;
        holder.textView.setText(mSucculentList.get(position).getName());
        if (mImageUrls.get(position)!=null) {
            Glide.with(mContext).load(mImageUrls.get(position)).into(holder.imageView);
        } else {
            mNetworkUtil.requestGetSingleImage(mSucculentList.get(position).getPage_name(),holder,position);
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
        ViewHolder viewHolder = (ViewHolder) object.getSerializable(Constant.ViewModel.VIEW_HOLDER);
        String url = object.getString(Constant.ViewModel.IMAGE);
        int position = object.getInt(Constant.ViewModel.LIST_POSITION);
        mImageUrls.put(position,url);
        mFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(mContext).load(url).into(viewHolder.imageView);
            }
        });
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
