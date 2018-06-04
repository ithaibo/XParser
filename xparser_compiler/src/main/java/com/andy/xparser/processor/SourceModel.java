package com.andy.xparser.processor;

import com.squareup.javapoet.MethodSpec;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
class SourceModel {
    String dataName;

    String actSimpleName;

    String fieldClassName;

    MethodSpec.Builder method;
}
