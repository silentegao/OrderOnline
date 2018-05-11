package com.example.rui.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleHolder;
import com.example.rui.myapplication.bean.OrdersInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderListAdapter extends BaseRecycleAdapter<OrdersInfo>
{
    
    int[] imgIDs = {R.drawable.star, R.drawable.pizza, R.drawable.ha};
    
    public interface DeleteOrderInterface
    {
        void delete(int position);
    }
    
    private DeleteOrderInterface deleteOrderInterface;
    
    public void setDeleteOrderInterface(DeleteOrderInterface deleteOrderInterface)
    {
        this.deleteOrderInterface = deleteOrderInterface;
    }
    
    public OrderListAdapter(Context context, List<OrdersInfo> list, int layoutId)
    {
        super(context, list, layoutId);
    }
    
    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, final int position, OrdersInfo ordersInfo)
    {
        baseRecycleHolder.setImageViewResourceId(R.id.item_img, ordersInfo.getImageUrl())
            .setTextViewText(R.id.item_title, ordersInfo.getGoodsTypeName())
            .setTextViewText(R.id.tv_number, "x" + ordersInfo.getGoodsAmount())
            .setTextViewText(R.id.tv_price, "总计: " + ordersInfo.getGoodsPrice() + " 元")
            .setTextViewText(R.id.tv_seat, "位置: " + ordersInfo.getSeatNo())
            .setTextViewText(R.id.tv_time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ordersInfo.getCreateTime()))
            .setTextViewText(R.id.tv_no, ordersInfo.getId() + "号");

        baseRecycleHolder.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Toast.makeText(context, "shanchu!"+position, Toast.LENGTH_SHORT).show();

                AlertDialog d = new AlertDialog.Builder(context).setTitle("确定删除该订单吗")
                    
                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            Log.e("infoD", "onClick: "+deleteOrderInterface);
                            if (deleteOrderInterface != null) {
                                deleteOrderInterface.delete(position);
                            }
                        }
                        
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            dialog.dismiss();
                        }
                        
                    })
                    .create();
                    
                d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                
                d.show();
                
            }
        });
    }
    
    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder)
    {
        
    }
}
