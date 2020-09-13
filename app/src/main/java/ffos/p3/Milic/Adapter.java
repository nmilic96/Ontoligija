package ffos.p3.Milic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ontoligija.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ResourceViewHolder> {
    private Context mContext;
    private ArrayList<Resource> mResourceList;

    public Adapter(Context context, ArrayList<Resource> resourceList) {
        mContext = context;
        mResourceList = resourceList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.resource_item, parent, false);
        return new ResourceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource currentResource = mResourceList.get(position);
        String resourceName = currentResource.getResourceName();
        String resourceType = currentResource.getResourceType();
        String resourceData = currentResource.getmResourceData();

        holder.mTextViewName.setText(resourceName);
        holder.mTextViewType.setText(resourceType);
        holder.mTextViewData.setText(resourceData);
    }

    @Override
    public int getItemCount() {
        return mResourceList.size();
    }

    public class ResourceViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName;
        public TextView mTextViewType;
        public TextView mTextViewData;
        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.text_view_resource_name);
            mTextViewType = itemView.findViewById(R.id.text_view_resource_type);
            mTextViewData = itemView.findViewById(R.id.text_view_resource_data);
        }
    }
}
