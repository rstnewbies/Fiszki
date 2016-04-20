package eu.qm.fiszki.tutorial;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

import eu.qm.fiszki.R;

/**
 * Created by bgood on 2016-04-15.
 */
public class FivePage extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{};
    }
}
