/*
 * Copyright 2015 Bartosz Lipinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.climbachiya.suggestquery.R;

import java.util.ArrayList;

import modals.QueryData;

public class QueryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<QueryData> queryList;
    Context mContext;
    private boolean test = false;

    public QueryAdapter(Context context, ArrayList<QueryData> queries) {
        this.queryList = queries;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_cell, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SampleViewHolder viewHolder = (SampleViewHolder) holder;

        final QueryData postItem = queryList.get(position);

        if (postItem.getSuggestion() != null) {
            viewHolder.txtData.setText(postItem.getSuggestion());
        }

    }

    @Override
    public int getItemCount() {
        return queryList.size() - (test ? 1 : 0);
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        private TextView txtData;

        public SampleViewHolder(View view) {
            super(view);
            txtData = (TextView) view.findViewById(R.id.text_data);
        }
    }
}