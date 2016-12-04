package eu.qm.fiszki.tutorial.page;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

import eu.qm.fiszki.R;

/**
 * Created by bgood on 2016-04-15.
 */
public class FourPage extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.tutorial_page_four;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{
                new TransformItem(R.id.TutFourImage, true, 20),
        };
    }
}
