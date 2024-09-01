package com.admin.mapper;

import com.admin.entity.Product;
import com.admin.entity.Warehouse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarehouseProductMapper {

    // 根据产品id查询所在的所有仓库
    List<Warehouse> listWarehouse(Integer productId);

    // 根据仓库id查询包含的所有产品
    List<Product> listProduct(Integer warehouseId);

    // 添加一条库存记录
    @Insert("insert into tb_warehouse_product(wid, pid, count) values (#{wid}, #{pid}, #{count})")
    Integer insert(
            @Param("wid") int wid,
            @Param("pid") int pid,
            @Param("count") int count
    );

    // 更新原有库存记录
    @Update("update tb_warehouse_product set count=#{count} where wid=#{wid} and pid=#{pid}")
    Integer update(
            @Param("wid") int wid,
            @Param("pid") int pid,
            @Param("count") int count
    );
}
