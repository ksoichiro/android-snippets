package com.snippet.androidformenhancer;

import com.androidformenhancer.annotation.MaxLength;
import com.androidformenhancer.annotation.Multibyte;
import com.androidformenhancer.annotation.Required;
import com.androidformenhancer.annotation.Widget;

/**
 * Created by ksoichiro on 2014/06/03.
 */
public class DefaultForm {

    @Required
    @Multibyte
    @MaxLength(20)
    @Widget(id = R.id.textfield_name, nameResId = R.string.form_default_name)
    public String name;

}
