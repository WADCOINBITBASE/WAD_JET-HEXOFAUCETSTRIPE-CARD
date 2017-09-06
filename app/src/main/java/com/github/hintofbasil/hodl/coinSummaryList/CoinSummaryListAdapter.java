package com.github.hintofbasil.hodl.coinSummaryList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.hintofbasil.hodl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by will on 8/16/17.
 */

public class CoinSummaryListAdapter extends ArrayAdapter<CoinSummary> {

    int resource;
    ImageLoader imageLoader;
    View parent;
    View firstChild;

    public CoinSummaryListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        imageLoader = ImageLoader.getInstance();
    }

    public CoinSummaryListAdapter(Context context, int resource, CoinSummary[] objects, View parent) {
        super(context, resource, objects);
        this.resource = resource;
        imageLoader = ImageLoader.getInstance();
        this.parent = parent;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CoinSummary summary = getItem(position);

        View v = convertView;
        if(v == null) {
            v = LayoutInflater.from(getContext()).inflate(this.resource, null);
        }

        ImageView coinImageView = (ImageView) v.findViewById(R.id.coin_image);
        imageLoader.displayImage(summary.getImageURL(128), coinImageView);

        TextView coinNameView = (TextView) v.findViewById(R.id.coin_name);
        coinNameView.setText(summary.getName());

        TextView tickerSymbol = (TextView)v.findViewById(R.id.coin_ticker_symbol);
        tickerSymbol.setText(
                String.format("(%s)",
                        summary.getSymbol()
                )
        );

        TextView price = (TextView)v.findViewById(R.id.coin_price_usd);
        if (price != null && !price.equals("")) {
            price.setText(String.format("$%s", summary.getPriceUSD(true).toString()));
        } else {
            String text = getContext().getString(R.string.price_missing);
            price.setText(text);
        }

        if (summary.getQuantity().signum() == 1) {
            TextView quantityAndOwnedValueView = (TextView) v.findViewById(R.id.coin_quantity_and_owned_value);
            quantityAndOwnedValueView.setText(
                    String.format("%s ($%s)",
                            summary.getQuantity().toString(),
                            summary.getOwnedValue(true).toString()
                    )
            );
        }

        int[] paddings = {
                getContext().getResources().getDimensionPixelSize(
                    R.dimen.homepage_list_element_padding_horizontal
                ),
                0,
                getContext().getResources().getDimensionPixelSize(
                    R.dimen.homepage_list_element_padding_horizontal
                ),
                0
        };

        if (position == getCount() - 1 && this.firstChild != null) {
            int viewHeight = firstChild.getHeight();
            int lastPadding = getContext().getResources().getDimensionPixelSize(
                    R.dimen.homepage_list_element_last_padding_bottom
            );
            if (getCount() * viewHeight > parent.getHeight() - lastPadding) {
                paddings[3] = lastPadding;
            }
        }

        v.setPadding(
                paddings[0],
                paddings[1],
                paddings[2],
                paddings[3]
        );

        if (position == 0) {
            this.firstChild = v;
        }

        return v;
    }
}
