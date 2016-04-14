package eu.qm.fiszki.tutorial;

/**
 * Created by bgood on 2016-04-14.
 */
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

import eu.qm.fiszki.R;

public class SecondPage extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.tutorial_page_second;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{

        };
    }
}