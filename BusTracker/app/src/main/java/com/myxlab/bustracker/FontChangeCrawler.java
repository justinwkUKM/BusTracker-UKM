package com.myxlab.bustracker;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MyXLab on 1/6/2017.
 */
public class FontChangeCrawler {
    private Typeface typeface;

    /**
     * Instantiates a new Font change crawler.
     *
     * @param typeface the typeface
     */
    public FontChangeCrawler(Typeface typeface) {
        this.typeface = typeface;
    }

    /**
     * Instantiates a new Font change crawler.
     *
     * @param assets             the assets
     * @param assetsFontFileName the assets font file name
     */
    public FontChangeCrawler(AssetManager assets, String assetsFontFileName) {
        typeface = Typeface.createFromAsset(assets, assetsFontFileName);
    }

    /**
     * Replace fonts.
     *
     * @param viewTree the view tree
     */
    public void replaceFonts(ViewGroup viewTree) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);
            if (child instanceof ViewGroup) {
                // recursive call
                replaceFonts((ViewGroup) child);
            } else if (child instanceof TextView) {
                // base case
                ((TextView) child).setTypeface(typeface);
            }
        }
    }
}
