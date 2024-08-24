package com.admin.mapper;

import com.admin.entity.Product;
import com.admin.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseProductMapper {

    // 根据产品id查询所在的所有仓库
    List<Warehouse> listWarehouse(Integer productId);

    // 根据仓库id查询包含的所有产品
    List<Product> listProduct(Integer warehouseId);

    // 添加一条库存记录
    Integer insert(
            @Param("wid") int wid,
            @Param("pid") int pid,
            @Param("count") int count
    );

    // 更新原有库存记录
    Integer update(
            @Param("wid") int wid,
            @Param("pid") int pid,
            @Param("count") int count
    );
}
