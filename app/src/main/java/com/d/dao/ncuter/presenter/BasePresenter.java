package com.d.dao.ncuter.presenter;

import com.d.dao.ncuter.ui.view.BaseView;
import com.d.dao.ncuter.utils.ReservoirUtils;

/**
 * Created by dao on 7/3/16.
 */
public interface BasePresenter {

    void attachView(BaseView view);
    void detachView(BaseView view);

}
