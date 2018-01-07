package com.github.hintofbasil.hodl;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.github.hintofbasil.hodl.database.DbHelper;
import com.github.hintofbasil.hodl.database.objects.CoinSummary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by will on 07/01/18.
 */

@RunWith(RobolectricTestRunner.class)
public class CoinDetailsActivityTest {

    @Test
    public void testBasicLoad() {

        initImageLoader();

        List<CoinSummary> coins = createCoins();
        Intent intent = new Intent();
        intent.putExtra("coinSummary", coins.get(0));

        ActivityController<CoinDetailsActivity> controller = Robolectric.buildActivity(CoinDetailsActivity.class, intent).create().start();
        CoinDetailsActivity activity = controller.get();

        assertNotEquals(activity.findViewById(R.id.coin_search_box), null);
    }

    private void initImageLoader() {
        Robolectric.buildActivity(MainActivity.class).create().get().initImageLoader();
    }

    private List<CoinSummary> createCoins() {
        List<CoinSummary> coins = new ArrayList<>();


        DbHelper dbHelper = new DbHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CoinSummary coin = new CoinSummary(
                "BTC",
                "Bitcoin",
                "bitcoin",
                1,
                true,
                new BigDecimal("14860.2"),
                new BigDecimal("1.5")
        );

        coin.addToDatabase(db);
        coins.add(coin);

        return coins;
    }

}
