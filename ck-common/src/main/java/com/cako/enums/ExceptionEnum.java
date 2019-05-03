package com.cako.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {
    PRICE_CANNOT_BE_NALL(400,"价格不能为空"),
    CATEGORY_NOT_FOND(404,"商品类别为找到"),

    FILE_UPLOAD_ERROR(500,"文件上传失败"),
    FILE_TYPE_INVALID(400,"文件类型不匹配"),

    BRAND_NOT_FOUND(404,"品牌未找到"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPDATA_BRAND_ERROR(500,"品牌跟新失败"),
    DELETE_BRAND_ERROR(500,"删除品牌失败"),

    GROUP_NOT_FOND(404,"商品规格组不存在"),
    GROUP_SAVE_ERROR(500,"商品规格组新增失败"),
    GROUP_DELETE_ERROR(500,"商品组删除失败"),
    GROUP_UPDATE_ERROR(500,"商品规格组跟新失败"),

    PARAM_NOT_FOND(404,"商品规格参数不存在"),
    PARAM_SAVE_ERROR(500,"商品规格参数新增失败"),
    PARAM_DELETE_ERROR(500,"商品规格参数删除失败"),
    PARAM_UPDATE_ERROR(500,"商品规格参数更新失败"),

    ;
    private int code;
    private String msg;
}
