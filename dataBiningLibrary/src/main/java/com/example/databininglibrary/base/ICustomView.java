package com.example.databininglibrary.base;

public interface ICustomView<S extends BaseCustomViewModel > {
     void  setData(S data);
     void setActionListener(ICustomViewActionListener listener);

}
