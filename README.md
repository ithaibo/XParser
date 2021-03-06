# 功能
 - 将JSON字符串解析为Bundle

# 解析规则
| 解析后的数据类型(java) | JOSN(Query参数)类型 | Bundle API |
| -- | -- | -- |
| byte |  | putByte |
| short |  |  putShort |
| int | int | putInt |
| long | long | putLong |
| float | float | putFloat |
| double | double | putDouble |
| String | String | putString |
| boolean | boolean | putBoolean |
| \<T\> | JSONObject | putString |

## JSONArray会按照数组中第一个元素的类型进行解析（数组中所有的元素类型必须一致）



针对如下的数据的解析
``` json
{
    "int":312,
    "double":2.3,
    "boolean":true,
    "string":"this is string",
    "intList":[1,2,4,5],
    "objArray":[
      {
        "id":3424,
        "title": "title1"
      },
      {
        "id":453,
        "title": "title2"
      }
    ],
    "arrayArray":[
      [12, 4, 5],
      [23, 4, 5, 1]
    ],
    "user": {
      "name":"Andy"
    }
}
```

 1. 将整个JSONObject解析为一个Bundle；
 2. 对第一层里面的每项数据进行解析，这里需要注意的是对于复杂类型，如上面的“user”的解析，需要知道其对应的Class。如何能知道该Class？该Class预先对每项数据的Class进行存储，然后在解析的时候获取出来。
 3. 上面的JSON解析中，除了基本类型及字符串，其余的全部以JSON字符串的性质通过bundle.putString(key, jsonStr)保存。这是因为ARouter在处理里类型的时候，只有四种情况：基本类型，String, parcable, Object(json序列化)


根据 path + key 获取对应的数据Class
